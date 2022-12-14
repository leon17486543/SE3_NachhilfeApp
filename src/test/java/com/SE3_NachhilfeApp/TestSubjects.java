package com.SE3_NachhilfeApp;

import com.SE3_NachhilfeApp.Subjects.Subject;
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
public class TestSubjects {

    private static final String asciiDocPath = "{class-name}/{method-name}/";

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    //Expected Subject as of "createSubject.sql"
    UUID id= UUID.fromString("fdc502b5-fb9e-4adc-95ca-5391b8b4995d");
    String name= "Mathe";
    boolean deleted= false;
    Subject subject = new Subject(id, name, deleted);

    FieldDescriptor[] fieldDescriptors = new FieldDescriptor []{
            fieldWithPath("id").optional().type(JsonFieldType.STRING).description("ID of Subject; UUID as String; Will be Autoset on creation"),
            fieldWithPath("name").optional().type(JsonFieldType.STRING).description("Name of Subject"),
            fieldWithPath("deleted").optional().type(JsonFieldType.BOOLEAN).description("Is the Subject deleted")
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
        String expected = "Subjects{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';

        assertEquals(expected, subject.toString());
    }

    @Test
    void testGetAndSet(){
        UUID newId = UUID.fromString("0750b1bd-558e-4d74-ae6b-3efe5b594cb4");
        String newName = "Deutsch";
        boolean newDeleted = true;

        subject.setId(newId);
        subject.setName(newName);
        subject.setDeleted(newDeleted);

        assertEquals(newId, subject.getId());
        assertEquals(newName, subject.getName());
        assertEquals(newDeleted, subject.isDeleted());
    }


    @Test
    @Sql("createSubject.sql")
    void testGetAll() throws Exception{
        //Prepare Expected
        List<Subject> expected = new ArrayList<>();
        expected.add(subject);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get("/api/v1/subjects"))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        List<Subject> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Subject>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createSubject.sql")
    void testGetById() throws Exception{
        //Prepare Expected
        Subject expected = subject;

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get("/api/v1/subjects/byId/{subjectId}", id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        Subject actual = objectMapper.readValue(jsonBody, Subject.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testAddNewDev() throws Exception{
        //Subject values to add
        UUID idToAdd = UUID.fromString("bbf975fe-7f80-11ed-a1eb-0242ac120002");
        String nameToAdd = "Englisch";
        boolean deletedToAdd = false;

        mockMvc.perform(post("/api/v1/subjects/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"+
                        "\"id\": \""+ idToAdd + "\","+
                        "\"name\": \""+ nameToAdd + "\","+
                        "\"deleted\": "+ deletedToAdd +
                        "}"))
                .andExpect(status().is2xxSuccessful());

        //Adding Subjects with the same name should fail
        boolean failed = false;
        try{
            mockMvc.perform(post("/api/v1/subjects/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{"+
                                    "\"id\": \""+ idToAdd + "\","+
                                    "\"name\": \""+ nameToAdd + "\","+
                                    "\"deleted\": "+ deletedToAdd +
                                    "}"))
                    .andExpect(status().is2xxSuccessful());
        }catch (Exception e){
            failed = true;
        }

        assertThat(failed).isTrue();

    }

    @Test
    void testAddNew() throws Exception{
        //Subject values to add
        //UUID idToAdd = UUID.fromString("bbf975fe-7f80-11ed-a1eb-0242ac120002");
        String nameToAdd = "Englisch";
        boolean deletedToAdd = false;

        mockMvc.perform(post("/api/v1/subjects/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"+
                                "\"name\": \""+ nameToAdd + "\","+
                                "\"deleted\": "+ deletedToAdd +
                                "}"))
                .andDo(document(asciiDocPath, relaxedRequestFields(fieldDescriptors)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Sql("createSubject.sql")
    void testDeleteById() throws Exception{
        //Prepare Expected
        Subject expected = new Subject(id, name, true);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(delete("/api/v1/subjects/delete/{id}",id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get("/api/v1/subjects/byId/"+id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        Subject actual = objectMapper.readValue(jsonBody, Subject.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createSubject.sql")
    void testUpdateById() throws Exception{
        //Prepare Expected
        String newName = "Deutsch";
        Subject expected = new Subject(id, newName, deleted);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(put("/api/v1/subjects/update/"+id+"?name="+newName))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get("/api/v1/subjects/byId/"+id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        Subject actual = objectMapper.readValue(jsonBody, Subject.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
