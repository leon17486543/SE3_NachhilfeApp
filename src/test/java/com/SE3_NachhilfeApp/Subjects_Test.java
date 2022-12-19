package com.SE3_NachhilfeApp;

import com.SE3_NachhilfeApp.Subjects.Subject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.datasource.url=jdbc:h2:mem:testdb"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class Subjects_Test {

    //CaN noT BE auTOWieReD mimimimii -> unimportand
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    //Expected Subject as of "createSubject.sql"
    UUID id= UUID.fromString("fdc502b5-fb9e-4adc-95ca-5391b8b4995d");
    String name= "Mathe";
    boolean deleted= false;
    Subject subject = new Subject(id, name, deleted);

    @Test
    void toString_Test(){
        String expected = "Subjects{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';

        assertEquals(expected, subject.toString());
    }

    @Test
    void getAndSet_Test(){
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
    void getAll_Test() throws Exception{
        //Prepare Expected
        List<Subject> expected = new ArrayList<>();
        expected.add(subject);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get("/api/v1/subjects"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        //Convert Json-Body to Subject List
        String jsonBody = res.getResponse().getContentAsString();
        List<Subject> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Subject>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createSubject.sql")
    void getById_Test() throws Exception{
        //Prepare Expected
        Subject expected = subject;

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
    void addNew_Test() throws Exception{
        //Subject values to add
        UUID idToAdd = UUID.fromString("bbf975fe-7f80-11ed-a1eb-0242ac120002");
        String nameToAdd = "Englisch";
        boolean deletedToAdd = false;

        mockMvc.perform(post("/api/v1/subjects/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"+
                        "\"id\": \""+ idToAdd + "\","+
                        "\"name\": \""+ nameToAdd + "\","+
                        "\"deleted\": \""+ deletedToAdd + "\""+
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
                                    "\"deleted\": \""+ deletedToAdd + "\""+
                                    "}"))
                    .andExpect(status().is2xxSuccessful());
        }catch (Exception e){
            failed = true;
        }

        assertThat(failed).isTrue();

    }

    @Test
    @Sql("createSubject.sql")
    void deleteById_Test() throws Exception{
        //Prepare Expected
        Subject expected = new Subject(id, name, true);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(delete("/api/v1/subjects/delete/"+id))
                .andExpect(status().is2xxSuccessful())
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
    void updateById_Test() throws Exception{
        //Prepare Expected
        String newName = "Deutsch";
        Subject expected = new Subject(id, newName, deleted);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(put("/api/v1/subjects/update/"+id+"?name="+newName))
                .andExpect(status().is2xxSuccessful())
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
