package com.SE3_NachhilfeApp;


import com.SE3_NachhilfeApp.Assignment.Assignment;
import com.SE3_NachhilfeApp.Offer.Offer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

import static org.assertj.core.api.Assertions.as;
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
public class TestAssignment {

    private static final String address = "/api/v1/assignments";
    private static final String asciiDocPath = "{class-name}/{method-name}/";

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    //Expected Subject as of "createSubject.sql"
    UUID id= UUID.fromString("67de3f07-f7ab-4359-a5a8-360827690e9b");
    UUID owner = UUID.fromString("dd5ea7ff-b60c-40fe-a1fa-120ec996b5b5");
    UUID subject = UUID.fromString("7de99560-eaf5-442e-a806-ba3bdbb7dd27");
    String name = "testAssignment";
    String description= "testAssignment Description";
    boolean deleted= false;
    Assignment assignment = new Assignment(id, owner, subject, name, description, deleted);

    FieldDescriptor[] fieldDescriptors = new FieldDescriptor []{
            fieldWithPath("id").optional().type(JsonFieldType.STRING).description("ID of Assignment; UUID as String; Will be Autoset on creation"),
            fieldWithPath("owner").optional().type(JsonFieldType.STRING).description("UUID of owner"),
            fieldWithPath("subject").optional().type(JsonFieldType.STRING).description("UUID of Subject"),
            fieldWithPath("name").optional().type(JsonFieldType.STRING).description("Name of Assignment"),
            fieldWithPath("description").optional().type(JsonFieldType.STRING).description("Description of Assignment"),
            fieldWithPath("deleted").optional().type(JsonFieldType.BOOLEAN).description("Is the Assignment deleted")
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
        String expected = "ASSIGNMENTS{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", subject='" + subject + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", description='" + deleted + '\'' +
                '}';

        assertEquals(expected, assignment.toString());
    }

    @Test
    void testGetAndSet(){
        UUID newId = UUID.fromString("0750b1bd-558e-4d74-ae6b-3efe5b594cb4");
        UUID newOwner = UUID.fromString("0750b1bd-558e-4d74-ae6b-3efe5b594cb4");;
        UUID newSubject = UUID.fromString("0750b1bd-558e-4d74-ae6b-3efe5b594cb4");;
        String newName = "new Name";
        String newDescription = "new Description";
        boolean newDeleted = true;

        assignment.setId(newId);
        assignment.setOwner(newOwner);
        assignment.setSubject(newSubject);
        assignment.setName(newName);
        assignment.setDescription(newDescription);
        assignment.setDeleted(newDeleted);

        assertEquals(newId, assignment.getId());
        assertEquals(newOwner, assignment.getOwner());
        assertEquals(newSubject, assignment.getSubject());
        assertEquals(newName, assignment.getName());
        assertEquals(newDescription, assignment.getDescription());
        assertEquals(newDeleted, assignment.isDeleted());
    }


    @Test
    @Sql("createAssignment.sql")
    void testGetAll() throws Exception{
        //Prepare Expected
        List<Assignment> expected = new ArrayList<>();
        expected.add(assignment);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        List<Assignment> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Assignment>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createAssignment.sql")
    void testGetById() throws Exception{
        //Prepare Expected
        Assignment expected = assignment;

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{subjectId}", id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        Assignment actual = objectMapper.readValue(jsonBody, Assignment.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createAssignment.sql")
    void testGetByOwner() throws Exception{
        //Prepare Expected
        List<Assignment> expected = new ArrayList<>();
        expected.add(assignment);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byOwner/{ownerId}", owner))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Assignment> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Assignment>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testAddNew() throws Exception{
        //Assignment values to add
        UUID ownerToAdd = UUID.fromString("bbf975fe-7f80-11ed-a1eb-0242ac120002");
        UUID subjectToAdd = UUID.fromString("bbf975fe-7f80-11ed-a1eb-0242ac120002");
        String nameToAdd = "name to add";
        String descriptionToAdd = "description to add";
        boolean deletedToAdd = false;

        mockMvc.perform(post(address+"/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"+
                                "\"ownerToAdd\": \""+ ownerToAdd + "\","+
                                "\"subjectToAdd\": \""+ subjectToAdd + "\","+
                                "\"nameToAdd\": \""+ nameToAdd + "\","+
                                "\"descriptionToAdd\": \""+ descriptionToAdd + "\","+
                                "\"deleted\": "+ deletedToAdd +
                                "}"))
                .andDo(document(asciiDocPath, relaxedRequestFields(fieldDescriptors)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Sql("createAssignment.sql")
    void testDeleteById() throws Exception{
        //Prepare Expected
        Assignment expected = assignment;
        assignment.setDeleted(true);

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
        Assignment actual = objectMapper.readValue(jsonBody, Assignment.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createAssignment.sql")
    void testDeleteByOwner() throws Exception{
        //Prepare Expected
        Assignment expected = assignment;

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(delete(address+"/deleteByOwner/{ownerId}",id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{ownerId}",id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        Assignment actual = objectMapper.readValue(jsonBody, Assignment.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createAssignment.sql")
    void testUpdateById() throws Exception{
        //Prepare Expected
        String newName = "new name";
        String newDescription = "newn Description";
        UUID newSubject = UUID.fromString("05c281cf-6456-499d-ba08-3657b1148809");

        Assignment expected = assignment;
        expected.setName(newName);
        expected.setDescription(newDescription);
        expected.setSubject(newSubject);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(put(address+"/update/"+id+"?name="+newName+"&description="+newDescription+"&subjectId="+newSubject))
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
        Assignment actual = objectMapper.readValue(jsonBody, Assignment.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
