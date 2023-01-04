package com.SE3_NachhilfeApp;

import com.SE3_NachhilfeApp.Offer.Offer;
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
public class TestOffer {

    private static final String address = "/api/v1/offer";
    private static final String asciiDocPath = "{class-name}/{method-name}/";

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    //Expected Contract as of "createContract.sql"
    UUID id= UUID.fromString("4edd654f-341b-4e54-918b-c9f8dba722d4");
    UUID subjectId = UUID.fromString("64eb9588-8259-4c0c-8d4e-0bccf459cf7d");
    UUID memberID = UUID.fromString("c6d2c09b-481c-43e8-8911-bc0ac5a1ce52");
    boolean deleted = false;
    Offer offer = new Offer(id,subjectId,memberID,deleted);

    FieldDescriptor[] fieldDescriptors = new FieldDescriptor []{
            fieldWithPath("id").optional().type(JsonFieldType.STRING).description("ID of Offer; UUID as String; Will be Autoset on creation"),
            fieldWithPath("subjectID").optional().type(JsonFieldType.STRING).description("Id of Subject; UUID as String"),
            fieldWithPath("memberID").optional().type(JsonFieldType.STRING).description("Id of offering User; UUID as String"),
            fieldWithPath("deleted").optional().type(JsonFieldType.BOOLEAN).description("Is the Offer deleted")
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
        String expected = "Offer{" +
                "id=" + id +
                ", subjectID='" + subjectId + '\'' +
                ", memberID='" + memberID + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';

        assertEquals(expected, offer.toString());
    }

    @Test
    void testGetAndSet(){
        UUID newId = UUID.fromString("fa8f6c16-a725-45d2-8d12-83c33bb69890");
        UUID newMemberId = UUID.fromString("05be84c2-4a07-45b1-937d-6ca5e310d21d");
        UUID newSubjectId = UUID.fromString("9e86a717-105c-46f9-afd3-f1cca48dea2f");
        boolean newDeleted = true;

        offer.setId(newId);
        offer.setMemberID(newMemberId);
        offer.setSubjectID(newSubjectId);
        offer.setDeleted(newDeleted);

        assertEquals(newId, offer.getId());
        assertEquals(newMemberId, offer.getMemberID());
        assertEquals(newSubjectId, offer.getSubjectID());
        assertEquals(newDeleted, offer.isDeleted());
    }

    @Test
    @Sql("createOffer.sql")
    void testGetAll() throws Exception{
        //Prepare Expected
        List<Offer> expected = new ArrayList<>();
        expected.add(offer);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Offer> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Offer>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createOffer.sql")
    void testGetById() throws Exception{
        //Prepare Expected
        Offer expected = offer;

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{offerId}", id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Offer actual = objectMapper.readValue(jsonBody, Offer.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createOffer.sql")
    void testGetByMember() throws Exception{
        //Prepare Expected
        List<Offer> expected = new ArrayList<>();
        expected.add(offer);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byUser/{offerId}", memberID))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Offer> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Offer>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createOffer.sql")
    void testGetBySubject() throws Exception{
        //Prepare Expected
        List<Offer> expected = new ArrayList<>();
        expected.add(offer);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/bySubject/{subjectId}", subjectId))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Offer> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Offer>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testAddNew() throws Exception{

        mockMvc.perform(post(address+"/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"+
                        "\"subjectID\": \""+ subjectId + "\"," +
                        "\"memberID\": \""+ memberID + "\","+
                        "\"deleted\": "+ deleted +
                        "}"))
                .andDo(document(asciiDocPath, relaxedRequestFields(fieldDescriptors)))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @Sql("createOffer.sql")
    void testDeleteById() throws Exception{
        //Prepare Expected
        Offer expected = offer;
        expected.setDeleted(true);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(delete(address+"/deleteById/{offerId}",id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{offerId}", id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Offer actual = objectMapper.readValue(jsonBody, Offer.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createOffer.sql")
    void testDeleteByMember() throws Exception{
        //Prepare Expected
        Offer expected = offer;
        expected.setDeleted(true);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(delete(address+"/deleteByUser/{userId}",memberID))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{offerId}", id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Offer actual = objectMapper.readValue(jsonBody, Offer.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createOffer.sql")
    void testUpdateById() throws Exception{
        //Prepare Expected
        UUID newSubject = UUID.fromString("0e471f0f-caa5-4b87-b93d-99a63468d7a8");
        Offer expected = offer;
        expected.setSubjectID(newSubject);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(put(address+"/update/"+id+"?subjectId="+newSubject))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{cofferId}", id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Offer actual = objectMapper.readValue(jsonBody, Offer.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
