package com.velikanovdev.platform.controller;


import com.velikanovdev.platform.dto.RatingRequestDto;
import com.velikanovdev.platform.dto.SnippetCodeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.velikanovdev.platform.controller.UserControllerTest.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Mock security context
    public void setUp() throws Exception {
        SnippetCodeDto snippetCodeDto = new SnippetCodeDto(null, "Test snippet");

        mockMvc.perform(post("/snippet/new")
                        .content(asJsonString(snippetCodeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Successfully add a new rating")
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Mock security context
    public void testAddRatingShouldSucceed() throws Exception {
        // Arrange
        RatingRequestDto ratingRequestDto = new RatingRequestDto(3, "admin", 1L);

        // Act and Assert - Use the token to access the /users endpoint
        mockMvc.perform(post("/rating/add")
                        .content(asJsonString(ratingRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Successfully get ratings by snippet ID")
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Mock security context
    public void testGetAllRatingsBySnippetIDShouldSucceed() throws Exception {
        // Arrange
        RatingRequestDto ratingRequestDto = new RatingRequestDto(3, "admin", 1L);

        // Act and Assert - Use the token to access the /users endpoint
        mockMvc.perform(get("/rating/snippet/1")
                        .content(asJsonString(ratingRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}