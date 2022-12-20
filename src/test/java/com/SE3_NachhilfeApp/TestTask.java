package com.SE3_NachhilfeApp;



import com.SE3_NachhilfeApp.Task.Task;
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
public class TestTask {

    private static final String address = "/api/v1/tasks";
    private static final String asciiDocPath = "{class-name}/{method-name}/";

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    //Expected Subject as of "createSubject.sql"
    UUID id = UUID.fromString("5076b5f7-38e2-4467-8674-0b02e82ad928");
    UUID assignmentId = UUID.fromString("92b6cdfe-f422-4af0-aa18-ff1dde37d46e");
    String name = "name";
    String correctSolution = "solution";
    boolean deleted= false;
    Task task = new Task(id, assignmentId, name, correctSolution, deleted);

    FieldDescriptor[] fieldDescriptors = new FieldDescriptor []{
            fieldWithPath("id").optional().type(JsonFieldType.STRING).description("ID of Task; UUID as String; Will be Autoset on creation"),
            fieldWithPath("assignmentId").optional().type(JsonFieldType.STRING).description("UUID of parent Assignment"),
            fieldWithPath("name").optional().type(JsonFieldType.STRING).description("name of Task"),
            fieldWithPath("correctSolution").optional().type(JsonFieldType.STRING).description("Solution to Task"),
            fieldWithPath("deleted").optional().type(JsonFieldType.BOOLEAN).description("Is the Task deleted")
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
        String expected = "Task{" +
                "id=" + id +
                ", assignmentID='" + assignmentId + '\'' +
                ", name='" + name + '\'' +
                ", correctSolution='" + correctSolution + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';

        assertEquals(expected, task.toString());
    }

    @Test
    void testGetAndSet(){
        UUID newId = UUID.fromString("0750b1bd-558e-4d74-ae6b-3efe5b594cb4");
        UUID newAssignmentId = UUID.fromString("0750b1bd-558e-4d74-ae6b-3efe5b594cb4");
        String newName = "new Name";
        String newSolution = "new Solution";
        boolean newDeleted = true;

        task.setId(newId);
        task.setAssignmentID(newAssignmentId);
        task.setName(newName);
        task.setCorrectSolution(newSolution);
        task.setDeleted(newDeleted);

        assertEquals(newId, task.getId());
        assertEquals(newAssignmentId, task.getAssignmentID());
        assertEquals(newName, task.getName());
        assertEquals(newSolution, task.getCorrectSolution());
        assertEquals(newDeleted, task.isDeleted());
    }

    @Test
    @Sql("createTask.sql")
    void testGetAll() throws Exception{
        //Prepare Expected
        List<Task> expected = new ArrayList<>();
        expected.add(task);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        List<Task> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Task>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createTask.sql")
    void testGetById() throws Exception{
        //Prepare Expected
        Task expected = task;

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{taskId}", id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        Task actual = objectMapper.readValue(jsonBody, Task.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createTask.sql")
    void testGetByAssignment() throws Exception{
        //Prepare Expected
        List<Task> expected = new ArrayList<>();
        expected.add(task);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byAssignmentId/{assignmentId}", assignmentId))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Task> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Task>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testAddNew() throws Exception{
        mockMvc.perform(post(address+"/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"+
                                "\"assignmentID\": \""+ assignmentId + "\","+
                                "\"name\": \""+ name + "\","+
                                "\"correctSolution\": \""+ correctSolution + "\","+
                                "\"deleted\": "+ deleted +
                                "}"))
                .andDo(document(asciiDocPath, relaxedRequestFields(fieldDescriptors)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Sql("createTask.sql")
    void testDeleteById() throws Exception{
        //Prepare Expected
        Task expected = task;
        task.setDeleted(true);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(delete(address+"/deleteById/{id}",id))
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
        Task actual = objectMapper.readValue(jsonBody, Task.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createTask.sql")
    void testDeleteByAssignmentId() throws Exception{
        //Prepare Expected
        List<Task> expected = new ArrayList<>();
        task.setDeleted(true);
        expected.add(task);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(delete(address+"/deleteByAssignmentId/{assignmentId}",assignmentId))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byAssignmentId/{assignmentId}",assignmentId))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        List<Task> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Task>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createTask.sql")
    void testUpdateById() throws Exception{
        //Prepare Expected
        String newName = "new name";
        String newSolutuion = "new Solution";

        Task expected = task;
        expected.setName(newName);
        expected.setCorrectSolution(newSolutuion);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(put(address+"/update/"+id+"?name="+newName+"&correctSolution="+newSolutuion))
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
        Task actual = objectMapper.readValue(jsonBody, Task.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
