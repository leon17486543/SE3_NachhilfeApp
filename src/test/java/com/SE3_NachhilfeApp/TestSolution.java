package com.SE3_NachhilfeApp;

import com.SE3_NachhilfeApp.Solution.Solution;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class TestSolution {

    private static final String address = "/api/v1/solution";
    private static final String asciiDocPath = "{class-name}/{method-name}/";

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    //Expected Contract as of "createSolution.sql"
    UUID id= UUID.fromString("fd484480-ad19-4998-acc3-51721072fa27");
    UUID taskID = UUID.fromString("540ae4c8-5929-4d7b-a055-7b5aacc8ad1f");
    UUID submissionID = UUID.fromString("c5922b55-853e-42c4-b20b-fb07bcc03496");
    String solutionText = "solution text";
    boolean deleted = false;
    Solution solution = new Solution(id, taskID, submissionID, solutionText, deleted);

    FieldDescriptor[] fieldDescriptors = new FieldDescriptor []{
            fieldWithPath("id").optional().type(JsonFieldType.STRING).description("ID of Solution; UUID as String; Will be Autoset on creation"),
            fieldWithPath("taskID").optional().type(JsonFieldType.STRING).description("Id of Task; UUID as String"),
            fieldWithPath("submissionID").optional().type(JsonFieldType.STRING).description("Id of submission; UUID as String"),
            fieldWithPath("solutionText").optional().type(JsonFieldType.STRING).description("user solution as String"),
            fieldWithPath("deleted").optional().type(JsonFieldType.BOOLEAN).description("Is the Solution deleted")
    };

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
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
        String expected = "Solution{" +
                    "id=" + id +
                    ", taskID='" + taskID + '\'' +
                    ", submissionID='" + submissionID + '\'' +
                    ", solutionText='" + solutionText + '\'' +
                    ", deleted='" + deleted + '\'' +
                    '}';

        assertEquals(expected, solution.toString());
    }

    @Test
    void testGetAndSet(){
        UUID newId= UUID.fromString("fd484480-ad19-4998-acc3-51721072fa26");
        UUID newTaskID = UUID.fromString("540ae4c8-5929-4d7b-a055-7b5aacc8ad2f");
        UUID newSubmissionID = UUID.fromString("c5922b55-853e-42c4-b20b-fb07bcc03495");
        String newSolutionText = "solution text";
        boolean newDeleted = true;

        solution.setId(newId);
        solution.setTaskID(newTaskID);
        solution.setSubmissionID(newSubmissionID);
        solution.setSolutionText(newSolutionText);
        solution.setDeleted(newDeleted);

        assertEquals(newId, solution.getId());
        assertEquals(newTaskID, solution.getTaskID());
        assertEquals(newSubmissionID, solution.getSubmissionID());
        assertEquals(newSolutionText, solution.getSolutionText());
        assertEquals(newDeleted, solution.isDeleted());
    }

    @Test
    @Sql("createSolution.sql")
    void testGetAll() throws Exception{
        //Prepare Expected
        List<Solution> expected = new ArrayList<>();
        expected.add(solution);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Solution> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Solution>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createSolution.sql")
    void testGetById() throws Exception{
        //Prepare Expected
        Solution expected = solution;

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{solutionId}", id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Solution actual = objectMapper.readValue(jsonBody, Solution.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testAddNew() throws Exception{

        mockMvc.perform(post(address+"/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"+
                        "\"taskID\": \""+ taskID + "\"," +
                        "\"submissionID\": \""+ submissionID + "\","+
                        "\"solutionText\": \""+ solutionText + "\","+
                        "\"deleted\": "+ deleted +
                        "}"))
                .andDo(document(asciiDocPath, relaxedRequestFields(fieldDescriptors)))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @Sql("createSolution.sql")
    void testDeleteById() throws Exception{
        //Prepare Expected
        Solution expected = solution;
        expected.setDeleted(true);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(delete(address+"/delete/{solutionId}",id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{solutionId}", id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Solution actual = objectMapper.readValue(jsonBody, Solution.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createSolution.sql")
    void testUpdateById() throws Exception{
        //Prepare Expected
        String newSolutionText = "new solution text";
        Solution expected = solution;
        expected.setSolutionText(newSolutionText);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(put(address+"/update/"+id+"?solutionText="+newSolutionText))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{solutionId}", id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Solution actual = objectMapper.readValue(jsonBody, Solution.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
