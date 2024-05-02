package com.velikanovdev.platform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.velikanovdev.platform.dto.UserCredentials;
import com.velikanovdev.platform.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository usersRepository;

    @Test
    @DisplayName("Successfully register a new account with valid credentials")
    void testRegisterWithValidCredentialsShouldSucceed() throws Exception {
        // Arrange
        UserCredentials userCredentials = new UserCredentials("test", "test");

        // Act and Assert
        mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(userCredentials)))
                .andExpect(status().isCreated());
        assert(usersRepository.findByUsername(userCredentials.username()).isPresent());
    }

    @Test
    @DisplayName("Fail to register a new account with an existing username")
    void testRegisterWithExistingUsernameShouldFail() throws Exception {
        // Arrange
        UserCredentials userCredentials = new UserCredentials("admin", "admin");
        
        // Act and Assert
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userCredentials)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Fail to register a new account with invalid credentials")
    void testRegisterWithInvalidCredentialsShouldFail() throws Exception {
        // Arrange
        UserCredentials userCredentials = new UserCredentials("test1", "11");

        // Act and Assert
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userCredentials)))
                .andExpect(status().isBadRequest());
        assertTrue(usersRepository.findByUsername(userCredentials.username()).isEmpty());
    }

    @Test
    @DisplayName("Successfully login with admin credentials")
    void testLoginWithAdminCredentialsShouldSucceed() throws Exception {
        // Arrange
        UserCredentials userCredentials = new UserCredentials("admin", "admin");

        // Act and Assert
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userCredentials)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @DisplayName("Fail to login with a non-existent username")
    void testLoginWithNonExistentUsernameShouldFail() throws Exception {
        // Arrange
        UserCredentials userCredentials = new UserCredentials("notExisting", "notExisting");

        // Act and Assert
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userCredentials)))
                .andExpect(status().isBadRequest());

        assertTrue(usersRepository.findByUsername("notExisting").isEmpty());
    }

    @Test
    @DisplayName("Successfully retrieve list of users with a valid token and role 'ADMIN'")
    void testGetUsersWithValidTokenAndRoleAdminShouldSucceed() throws Exception {
        // Arrange - Perform login to obtain the token
        UserCredentials credentials = new UserCredentials("admin", "admin");
        MvcResult loginResult = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(credentials)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();  // Get the result to extract the token

        String responseString = loginResult.getResponse().getContentAsString();
        String token = JsonPath.parse(responseString).read("$.token");  // Assume token is in the JSON path "$.token"

        // Act and Assert - Use the token to access the /users endpoint
        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + token)  // Include the token as a Bearer token
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(0)))); // Check if the response is an array
    }

    @Test
    @DisplayName("Fail to get list of users with valid token and role 'USER'")
    void testGetUsersWithValidTokenAndRoleUserShouldFail() throws Exception {
        // Arrange
        UserCredentials credentials = new UserCredentials("testUsername", "1234");
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(credentials)))
                .andExpect(status().isCreated());

        MvcResult loginResult = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(credentials)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();  // Get the result to extract the token

        String responseString = loginResult.getResponse().getContentAsString();
        String token = JsonPath.parse(responseString).read("$.token");  // Assume token is in the JSON path "$.token"

        // Act and Assert - Use the token to access the /users endpoint
        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + token)  // Include the token as a Bearer token
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    // Helper method to convert objects to JSON string
    static String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}