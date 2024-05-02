package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.dto.SnippetCodeDto;
import com.velikanovdev.platform.dto.SnippetDto;
import com.velikanovdev.platform.service.SnippetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import java.util.Arrays;
import java.util.List;

import static com.velikanovdev.platform.controller.UserControllerTest.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class SnippetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SnippetService snippetService;  // Mock the SnippetService

    @BeforeEach
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Mock security context
    void setUp() throws Exception {
        // Prepare a mock snippet to return when updateSnippet is called
        SnippetCodeDto snippetToUpdate = new SnippetCodeDto(1L, "Updated snippet");
        SnippetDto savedSnippetDto = new SnippetDto(1L, "Updated snippet", null, null, null, null, null);
        SnippetDto deletedSnippet = new SnippetDto(2L, "Deleted snippet", null, null, null, null, null);

        // Mock the update operation
        when(snippetService.updateSnippet(snippetToUpdate)).thenReturn(savedSnippetDto);

        // Mock the delete operation
        when(snippetService.deleteSnippet(2L)).thenReturn(deletedSnippet);

        // Mock the snippetService.getLatest() to return a list of snippets
        List<SnippetDto> snippets = Arrays.asList(
                new SnippetDto(1L, "Sample Code", null, null, null, null, null),
                new SnippetDto(2L, "Another Sample Code", null, null, null, null, null)
        );

        when(snippetService.getLatest()).thenReturn(snippets);
    }

    @Test
    @DisplayName("Successfully add a new snippet")
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Mock security context
    void testAddSnippetShouldSucceed() throws Exception {
        // Arrange
        SnippetCodeDto snippetCodeDto = new SnippetCodeDto(null, "Test snippet");

        // Act and Assert - Use the token to access the /users endpoint
        mockMvc.perform(post("/snippet/new")
                        .content(asJsonString(snippetCodeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Successfully get a list of snippets")
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Mock security context
    void testGetAllSnippetsShouldSucceed() throws Exception {
        // Act and Assert
        mockMvc.perform(get("/snippet/latest")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))  // Expect 2 snippets in the list
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].code").value("Sample Code"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].code").value("Another Sample Code"));
    }

    @Test
    @DisplayName("Successfully update a snippet")
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Mock security context
    void testUpdateSnippetShouldSucceed() throws Exception {
        // Arrange
        SnippetCodeDto updatedSnippetCode = new SnippetCodeDto(1L, "Updated snippet");

        // Act and Assert
        mockMvc.perform(put("/snippet/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedSnippetCode))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.code").value("Updated snippet"));
    }

    @Test
    @DisplayName("Successfully delete all snippets with role 'ADMIN'")
    @WithMockUser(username="admin", roles = "ADMIN")
    void testDeleteAllSnippetsWithRoleAdminShouldSucceed() throws Exception {
        // Act and Assert
        mockMvc.perform(delete("/snippet/deleteAll"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Fail to delete all snippets with role 'USER'")
    @WithMockUser(username="user", roles = "USER")
    void testDeleteAllSnippetsWithRoleUserShouldFail() throws Exception {
        // Act and Assert
        mockMvc.perform(delete("/snippet/deleteAll"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Successfully delete snippet with role 'ADMIN'")
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Mock security context
    void testDeleteSnippetWithRoleAdminShouldSucceed() throws Exception {
        // Act and Assert
        mockMvc.perform(delete("/snippet/delete/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.code").value("Deleted snippet"));
    }

}