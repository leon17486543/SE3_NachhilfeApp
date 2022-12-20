package com.SE3_NachhilfeApp;

import com.SE3_NachhilfeApp.Contract.Contract;
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
public class TestContract {

    private static final String address = "/api/v1/contract";
    private static final String asciiDocPath = "{class-name}/{method-name}/";

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    //Expected Contract as of "createContract.sql"
    UUID id= UUID.fromString("d0d8dcf7-53ba-4f3c-93ce-1c94b3014649");
    UUID tutorID = UUID.fromString("3a1c6b7b-4ae4-4a0e-9e9a-e0d3a6efafe6");
    UUID schoolerID = UUID.fromString("5dc6e298-142c-4608-9f1b-36d575d72195");
    UUID subjectID = UUID.fromString("ce649564-862d-40e5-9730-a5d5693083cd");
    boolean deleted = false;
    Contract contract = new Contract(id,tutorID,schoolerID,subjectID,deleted);

    FieldDescriptor[] fieldDescriptors = new FieldDescriptor []{
            fieldWithPath("id").optional().type(JsonFieldType.STRING).description("ID of Member; UUID as String; Will be Autoset on creation"),
            fieldWithPath("tutorID").optional().type(JsonFieldType.STRING).description("Id of teaching Member; UUID as String"),
            fieldWithPath("schoolerID").optional().type(JsonFieldType.STRING).description("Id of learning Member; UUID as String"),
            fieldWithPath("subjectID").optional().type(JsonFieldType.STRING).description("Id of Subject; UUID as String"),
            fieldWithPath("deleted").optional().type(JsonFieldType.BOOLEAN).description("Is the Contract deleted")
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
        String expected = "Contract{" +
                "id=" + id +
                ", tutorID='" + tutorID + '\'' +
                ", schoolerID='" + schoolerID + '\'' +
                ", subjectID='" + subjectID + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';

        assertEquals(expected, contract.toString());
    }

    @Test
    void testGetAndSet(){
        UUID newId = UUID.fromString("fa8f6c16-a725-45d2-8d12-83c33bb69890");
        UUID newTutorId = UUID.fromString("e61b1639-add3-4506-a3b5-dc20a7f121ef");
        UUID newSchoolerId = UUID.fromString("05be84c2-4a07-45b1-937d-6ca5e310d21d");
        UUID newSubjectId = UUID.fromString("9e86a717-105c-46f9-afd3-f1cca48dea2f");
        boolean newDeleted = true;

        contract.setId(newId);
        contract.setTutorID(newTutorId);
        contract.setSchoolerID(newSchoolerId);
        contract.setSubjectID(newSubjectId);
        contract.setDeleted(newDeleted);

        assertEquals(newId, contract.getId());
        assertEquals(newTutorId, contract.getTutorID());
        assertEquals(newSchoolerId, contract.getSchoolerID());
        assertEquals(newSubjectId, contract.getSubjectID());
        assertEquals(newDeleted, contract.isDeleted());
    }

    @Test
    @Sql("createContract.sql")
    void testGetAll() throws Exception{
        //Prepare Expected
        List<Contract> expected = new ArrayList<>();
        expected.add(contract);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Contract> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Contract>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createContract.sql")
    void testGetById() throws Exception{
        //Prepare Expected
        Contract expected = contract;

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{contractId}", id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Contract actual = objectMapper.readValue(jsonBody, Contract.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createContract.sql")
    void testGetByTutor() throws Exception{
        //Prepare Expected
        List<Contract> expected = new ArrayList<>();
        expected.add(contract);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byTutor/{tutorId}", tutorID))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Contract> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Contract>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createContract.sql")
    void testGetBySchooler() throws Exception{
        //Prepare Expected
        List<Contract> expected = new ArrayList<>();
        expected.add(contract);

        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/bySchooler/{schoolerId}", schoolerID))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath, relaxedResponseFields(fieldDescriptors)))
                .andReturn();

        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        List<Contract> actual = objectMapper.readValue(jsonBody, new TypeReference<List<Contract>>(){});

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testAddNew() throws Exception{

        mockMvc.perform(post(address+"/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"+
                        "\"tutorID\": \""+ tutorID + "\"," +
                        "\"schoolerID\": \""+ schoolerID + "\","+
                        "\"subjectID\": \""+ subjectID + "\","+
                        "\"deleted\": "+ deleted +
                        "}"))
                .andDo(document(asciiDocPath, relaxedRequestFields(fieldDescriptors)))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @Sql("createContract.sql")
    void testDeleteById() throws Exception{
        //Prepare Expected
        Contract expected = contract;
        expected.setDeleted(true);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(delete(address+"/delete/{contractId}",id))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{contractId}", id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Contract actual = objectMapper.readValue(jsonBody, Contract.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @Sql("createContract.sql")
    void testUpdateById() throws Exception{
        //Prepare Expected
        UUID newSubject = UUID.fromString("0e471f0f-caa5-4b87-b93d-99a63468d7a8");
        Contract expected = contract;
        expected.setSubjectID(newSubject);

        //DELETE
        //Mock HTTP Request
        mockMvc.perform(put(address+"/update/"+id+"?subjectId="+newSubject))
                .andExpect(status().is2xxSuccessful())
                .andDo(document(asciiDocPath))
                .andReturn();

        //GET to verify
        //Mock HTTP Request
        MvcResult res = mockMvc.perform(get(address+"/byId/{contractId}", id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        //Convert Json-Body to Member List
        String jsonBody = res.getResponse().getContentAsString();
        Contract actual = objectMapper.readValue(jsonBody, Contract.class);

        //Assertion
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
