package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.dto.CommentRequestDto;
import com.velikanovdev.platform.dto.CommentResponseDto;
import com.velikanovdev.platform.dto.SnippetCodeDto;
import com.velikanovdev.platform.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.velikanovdev.platform.controller.UserControllerTest.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @BeforeEach
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Mock security context
    public void setUp() throws Exception {
        CommentRequestDto commentRequestDto = new CommentRequestDto(1L, "admin", "test comment");
        CommentResponseDto commentResponseDto = new CommentResponseDto(1L, "admin", "test comment", null);

        // Mock the add operation
        when(commentService.addComment(commentRequestDto)).thenReturn(commentResponseDto);
        // Mock the delete operation
        when(commentService.deleteComment(1L)).thenReturn(commentResponseDto);

        SnippetCodeDto snippetCodeDto = new SnippetCodeDto(null, "Test snippet");

        mockMvc.perform(post("/snippet/new")
                        .content(asJsonString(snippetCodeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Successfully add a new comment")
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Mock security context
    void addNewCommentShouldSucceed() throws Exception {
        // Arrange
        CommentRequestDto commentRequestDto = new CommentRequestDto(1L, "admin", "test comment");

        // Act and Assert - Use the token to access the /users endpoint
        mockMvc.perform(post("/comment/add")
                        .content(asJsonString(commentRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.text").value("test comment"));
    }

    @Test
    @DisplayName("Successfully delete comment by comment ID")
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Mock security context
    void testDeleteCommentByCommentIdShouldSucceed() throws Exception {
        // Act and Assert - Use the token to access the /users endpoint
        mockMvc.perform(delete("/comment/delete/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.text").value("test comment"));
    }

}