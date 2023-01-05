package com.SE3_NachhilfeApp;

import com.SE3_NachhilfeApp.Submission.Submission;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.datasource.url=jdbc:h2:mem:testdb"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class TestSubmission {

    private static final String address = "/api/v1/submission";
    private static final String asciiDocPath = "{class-name}/{method-name}/";

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    //Expected Submission as of "createSubmission.sql"
    UUID id= UUID.fromString("a4f1859f-6fe5-4909-8bdb-22430689cc18");
    LocalDate submissionDate = LocalDate.parse("24/12/2025",  DateTimeFormatter.ofPattern("d/MM/yyyy"));
    boolean deleted = false;
    Submission submission = new Submission(id, submissionDate, deleted);

    FieldDescriptor[] fieldDescriptors = new FieldDescriptor []{
            fieldWithPath("id").optional().type(JsonFieldType.STRING).description("ID of Submission; UUID as String; Will be Autoset on creation"),
            fieldWithPath("submisssionDate").optional().type(JsonFieldType.STRING).description("Date of submission"),
            fieldWithPath("deleted").optional().type(JsonFieldType.BOOLEAN).description("Is the Submission deleted")
    };

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        objectMapper.findAndRegisterModules();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(
                        documentationConfiguration(restDocumentation)
                            .snippets()
                            .withDefaults(HttpDocumentation.httpRequest(),
                                    HttpDocumentation.httpResponse()
                            )
                )
                .build();
    }

    @Test
    void testToString(){
        String expected = "Submission{" +
                "id=" + id +
                ", submissionDate='" + submissionDate + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';

        assertEquals(expected, submission.toString());
    }

    @Test
    void testGetAndSet(){
        UUID newId= UUID.fromString("fd484480-ad19-4998-acc3-51721072fa26");
        LocalDate newSubmissionDate = LocalDate.parse("25/12/2025",  DateTimeFormatter.ofPattern("d/MM/yyyy"));
        boolean newDeleted = true;

        submission.setId(newId);
        submission.setSubmissionDate(newSubmissionDate);
        submission.setDeleted(newDeleted);

        assertEquals(newId, submission.getId());
        assertEquals(newSubmissionDate, submission.getSubmissionDate());
        assertEquals(newDeleted, submission.isDeleted());
    }

    @Test
    @Sql("createSubmission.sql")
    void testGetAll() throws Exception{
        //Prepare Expected
        List<Submission> expected = new ArrayList<>();
        expected.add(submission);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Submission> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Submission>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createSubmission.sql")
    void testGetById() throws Exception{
        //Prepare Expected
        Submission expected = submission;

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{submissionId}", id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Submission actual = objectMapper.readValue(jsonBody, Submission.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testAddNew() throws Exception{

        mockMvc.perform(post(address+"/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"+
                        "\"submissionDate\": \""+ submissionDate + "\"," +
                        "\"deleted\": "+ deleted +
                        "}"))
                .andDo(document(asciiDocPath, relaxedRequestFields(fieldDescriptors)))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @Sql("createSubmission.sql")
    void testDeleteById() throws Exception{
        //Prepare Expected
        Submission expected = submission;
        expected.setDeleted(true);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(delete(address+"/delete/{submissionId}",id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{submissionId}", id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Submission actual = objectMapper.readValue(jsonBody, Submission.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createSubmission.sql")
    void testUpdateById() throws Exception{
        //Prepare Expected
        LocalDate newSubmissionDate = LocalDate.parse("25/12/2025",  DateTimeFormatter.ofPattern("d/MM/yyyy"));
        Submission expected = submission;
        expected.setSubmissionDate(newSubmissionDate);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(put(address+"/update/"+id+"?submissionDate="+newSubmissionDate))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{submissionId}", id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Submission actual = objectMapper.readValue(jsonBody, Submission.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
