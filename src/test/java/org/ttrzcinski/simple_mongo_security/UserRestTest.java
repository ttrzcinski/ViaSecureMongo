package org.ttrzcinski.simple_mongo_security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository UserRepository;

    @BeforeEach
    public void deleteAllBeforeTests() throws Exception {
        UserRepository.deleteAll();
    }

    @Test
    public void shouldReturnRepositoryIndex() throws Exception {

        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(
                jsonPath("$._links.users").exists());
    }

    @Test
    public void shouldCreateEntity() throws Exception {

        mockMvc.perform(post("/users").content(
                "{\"username\": \"Tomasso\", \"roleName\":\"McCaine\"}")).andExpect(
                status().isCreated()).andExpect(
                header().string("Location", containsString("users/")));
    }

    @Test
    public void shouldRetrieveEntity() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/users").content(
                "{\"username\": \"Thomasso\", \"roleName\":\"McCaine\"}")).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
                jsonPath("$.username").value("Thomasso")).andExpect(
                jsonPath("$.roleName").value("McCaine"));
    }

    @Test
    public void shouldQueryEntity() throws Exception {

        mockMvc.perform(post("/users").content(
                "{ \"username\": \"Thomasso\", \"roleName\":\"McCaine\"}")).andExpect(
                status().isCreated());

        mockMvc.perform(
                get("/users/search/findByRoleName?name={name}", "McCaine")).andExpect(
                status().isOk()).andExpect(
                jsonPath("$._embedded.Users[0].username").value(
                        "Thomasso"));
    }

    @Test
    public void shouldUpdateEntity() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/users").content(
                "{\"username\": \"Thomasso\", \"roleName\":\"McCaine\"}")).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");

        mockMvc.perform(put(location).content(
                "{\"username\": \"Thomasso\", \"roleName\":\"McCaine\"}")).andExpect(
                status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
                jsonPath("$.username").value("Thomasso")).andExpect(
                jsonPath("$.roleName").value("McCaine"));
    }

    @Test
    public void shouldPartiallyUpdateEntity() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/users").content(
                "{\"username\": \"Thomasso\", \"roleName\":\"McCaine\"}")).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");

        mockMvc.perform(
                patch(location).content("{\"username\": \"Thomash\"}")).andExpect(
                status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
                jsonPath("$.username").value("Thomash")).andExpect(
                jsonPath("$.roleName").value("McCaine"));
    }

    @Test
    public void shouldDeleteEntity() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/users").content(
                "{ \"username\": \"Thomasso\", \"roleName\":\"McCaine\"}")).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(delete(location)).andExpect(status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isNotFound());
    }
}