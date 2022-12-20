package com.SE3_NachhilfeApp;

import com.SE3_NachhilfeApp.Member.Member;

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
public class TestMember {

    private static final String address = "/api/v1/user";
    private static final String asciiDocPath = "{class-name}/{method-name}/";

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    //Expected Member as of "createMember.sql"
    UUID id= UUID.fromString("f60e3209-7192-40c3-990c-0242c963bebc");
    String name= "Otto";
    boolean deleted= false;
    Member member = new Member(id, name, deleted);

    FieldDescriptor[] fieldDescriptors = new FieldDescriptor []{
            fieldWithPath("id").optional().type(JsonFieldType.STRING).description("ID of Member; UUID as String; FireBase ID must be set on creation"),
            fieldWithPath("name").optional().type(JsonFieldType.STRING).description("Name of Member"),
            fieldWithPath("deleted").optional().type(JsonFieldType.BOOLEAN).description("Is the Member deleted")
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
        String expected = "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';

        assertEquals(expected, member.toString());
    }

    @Test
    void testGetAndSet(){
        UUID newId = UUID.fromString("0750b1bd-558e-4d74-ae6b-3efe5b594cb4");
        String newName = "Deutsch";
        boolean newDeleted = true;

        member.setId(newId);
        member.setName(newName);
        member.setDeleted(newDeleted);

        assertEquals(newId, member.getId());
        assertEquals(newName, member.getName());
        assertEquals(newDeleted, member.isDeleted());
    }


    @Test
    @Sql("createMember.sql")
    void testGetAll() throws Exception{
        //Prepare Expected
        List<Member> expected = new ArrayList<>();
        expected.add(member);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Member> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Member>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createMember.sql")
    void testGetById() throws Exception{
        //Prepare Expected
        Member expected = member;

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{memberId}", id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Member actual = objectMapper.readValue(jsonBody, Member.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testAddNew() throws Exception{
        //Member values to add
        UUID idToAdd = UUID.fromString("fc0d2d95-3854-419d-9d62-ece3de02a6e9");
        String nameToAdd = "Hans";
        boolean deletedToAdd = false;

        mockMvc.perform(post(address+"/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"+
                        "\"id\": \""+ idToAdd + "\","+
                        "\"name\": \""+ nameToAdd + "\","+
                        "\"deleted\": "+ deletedToAdd +
                        "}"))
                .andDo(document(asciiDocPath, relaxedRequestFields(fieldDescriptors)))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @Sql("createMember.sql")
    void testDeleteById() throws Exception{
        //Prepare Expected
        Member expected = new Member(id, name, true);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(delete(address+"/delete/{memberId}",id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{memberId}", id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Member actual = objectMapper.readValue(jsonBody, Member.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createMember.sql")
    void testUpdateById() throws Exception{
        //Prepare Expected
        String newName = "Peter";
        Member expected = new Member(id, newName, deleted);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(put(address+"/update/{id}?name={newName}",id, newName))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{memberId}", id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Member actual = objectMapper.readValue(jsonBody, Member.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
