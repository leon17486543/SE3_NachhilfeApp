package com.SE3_NachhilfeApp;



import com.SE3_NachhilfeApp.Workload.Workload;
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
public class TestWorkload {

    private static final String address = "/api/v1/workload";
    private static final String asciiDocPath = "{class-name}/{method-name}/";

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    //Expected Workload as of "createWorkload.sql"
    UUID id= UUID.fromString("c67a2a13-d5a3-4cea-9c4e-39a74254cf81");
    UUID assignmentID = UUID.fromString("fe8bf4ec-e0b7-4c5f-a984-3e6f129d1d83");
    UUID schoolerID = UUID.fromString("fa5d0996-5342-412f-9d08-0aaf19f9992c");
    UUID tutorID = UUID.fromString("fad1a979-e168-476a-a080-10931b221ec8");
    UUID submissionID= UUID.fromString("124719b7-d374-4353-ad31-3d804cb69b56");
    LocalDate dueDate = LocalDate.parse("24/12/2025",  DateTimeFormatter.ofPattern("d/MM/yyyy"));
    boolean deleted= false;

    Workload workload = new Workload(id, assignmentID, schoolerID, tutorID, submissionID, dueDate, deleted);


    FieldDescriptor[] fieldDescriptors = new FieldDescriptor []{
            fieldWithPath("id").optional().type(JsonFieldType.STRING).description("ID of Workload; UUID as String; Will be Autoset on creation"),
            fieldWithPath("assignmentID").optional().type(JsonFieldType.STRING).description("UUID of corresponding assignment"),
            fieldWithPath("schoolerID").optional().type(JsonFieldType.STRING).description("UUID of student"),
            fieldWithPath("tutorID").optional().type(JsonFieldType.STRING).description("Name of teacher"),
            fieldWithPath("submissionID").optional().type(JsonFieldType.STRING).description("UUID of submission"),
            fieldWithPath("dueDate").optional().type(JsonFieldType.STRING).description("Due date"),
            fieldWithPath("deleted").optional().type(JsonFieldType.BOOLEAN).description("Is the Workload deleted")
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
        String expected = "Workload{" +
                "id=" + id +
                ", assignmentID='" + assignmentID + '\'' +
                ", schoolerID='" + schoolerID + '\'' +
                ", tutorID='" + tutorID + '\'' +
                ", submissionID='" + submissionID + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';

        assertEquals(expected, workload.toString());
    }

    @Test
    void testGetAndSet(){
        UUID newId= UUID.fromString("c67a2a14-d5a3-4cea-9c4e-39a74254cf81");
        UUID newAssignmentID = UUID.fromString("fa8bf4ec-e0b7-4c5f-a984-3e6f129d1d83");
        UUID newSchoolerID = UUID.fromString("fa5e0996-5342-412f-9d08-0aaf19f9992c");
        UUID newTutorID = UUID.fromString("fad1a679-e168-476a-a080-10931b221ec8");
        UUID newSubmissionID= UUID.fromString("124519b7-d374-4353-ad31-3d804cb69b56");
        LocalDate newDueDate = LocalDate.parse("25/12/2025",  DateTimeFormatter.ofPattern("d/MM/yyyy"));
        boolean newDeleted= false;

        workload.setId(newId);
        workload.setAssignmentID(newAssignmentID);
        workload.setSchoolerID(newSchoolerID);
        workload.setTutorID(newTutorID);
        workload.setSubmissionID(newSubmissionID);
        workload.setDueDate(newDueDate);
        workload.setDeleted(newDeleted);

        assertEquals(newId, workload.getId());
        assertEquals(newAssignmentID, workload.getAssignmentID());
        assertEquals(newSchoolerID, workload.getSchoolerID());
        assertEquals(newTutorID, workload.getTutorID());
        assertEquals(newSubmissionID, workload.getSubmissionID());
        assertEquals(newDueDate, workload.getDueDate());
        assertEquals(newDeleted, workload.isDeleted());
    }

    @Test
    @Sql("createWorkload.sql")
    void testGetAll() throws Exception{
        //Prepare Expected
        List<Workload> expected = new ArrayList<>();
        expected.add(workload);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        List<Workload> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Workload>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createWorkload.sql")
    void testGetById() throws Exception{
        //Prepare Expected
        Workload expected = workload;

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{workloadId}", id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        Workload actual = objectMapper.readValue(jsonBody, Workload.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createWorkload.sql")
    void testGetByTutor() throws Exception{
        //Prepare Expected
        List<Workload> expected = new ArrayList<>();
        expected.add(workload);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byTutor/{tutorId}", tutorID))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Workload> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Workload>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createWorkload.sql")
    void testGetBySchooler() throws Exception{
        //Prepare Expected
        List<Workload> expected = new ArrayList<>();
        expected.add(workload);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/bySchooler/{schoolerId}", schoolerID))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Workload> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Workload>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testAddNew() throws Exception{

        mockMvc.perform(post(address+"/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"+
                                "\"assignmentID\": \""+ assignmentID + "\","+
                                "\"schoolerID\": \""+ schoolerID + "\","+
                                "\"submissionID\": \""+ submissionID + "\","+
                                "\"tutorID\": \""+ tutorID + "\","+
                                "\"dueDate\": \""+ dueDate + "\","+
                                "\"deleted\": "+ deleted +
                                "}"))
                .andDo(document(asciiDocPath, relaxedRequestFields(fieldDescriptors)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Sql("createWorkload.sql")
    void testDeleteById() throws Exception{
        //Prepare Expected
        Workload expected = workload;
        workload.setDeleted(true);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(delete(address+"/delete/{id}",id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{id}",id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        Workload actual = objectMapper.readValue(jsonBody, Workload.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createWorkload.sql")
    void testUpdateById() throws Exception{
        //Prepare Expected
        LocalDate newDueDate = LocalDate.parse("25/12/2025",  DateTimeFormatter.ofPattern("d/MM/yyyy"));

        Workload expected = workload;
        expected.setDueDate(newDueDate);

        //UPDATE
        //Mock HTTP Request
        mockMvc.perform(put(address+"/update/"+id+"?dueDate="+newDueDate))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/"+id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        Workload actual = objectMapper.readValue(jsonBody, Workload.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
