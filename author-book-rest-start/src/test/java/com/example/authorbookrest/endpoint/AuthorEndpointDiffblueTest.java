package com.example.authorbookrest.endpoint;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.authorbookrest.dto.AuthorResponseDto;
import com.example.authorbookrest.dto.Gender;
import com.example.authorbookrest.dto.PagingResponseDto;
import com.example.authorbookrest.dto.SaveAuthorDto;
import com.example.authorbookrest.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthorEndpoint.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AuthorEndpointDiffblueTest {
    @Autowired
    private AuthorEndpoint authorEndpoint;

    @MockBean
    private AuthorService authorService;

    /**
     * Method under test: {@link AuthorEndpoint#getAll(int, int, String, String)}
     */
    @Test
    void testGetAll() throws Exception {
        // Arrange
        PagingResponseDto buildResult = PagingResponseDto.builder().data("Data").page(1).size(3).totalElements(1L).build();
        when(authorService.getAll(Mockito.<Pageable>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/authors");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(authorEndpoint)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"data\":\"Data\",\"size\":3,\"page\":1,\"totalElements\":1}"));
    }

    /**
     * Method under test: {@link AuthorEndpoint#getById(int)}
     */
    @Test
    void testGetById() throws Exception {
        // Arrange
        AuthorResponseDto buildResult = AuthorResponseDto.builder()
                .age(1)
                .firstName("Jane")
                .gender(Gender.MALE)
                .id(1)
                .surname("Doe")
                .build();
        when(authorService.getById(anyInt())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/authors/{id}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(authorEndpoint)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"firstName\":\"Jane\",\"surname\":\"Doe\",\"gender\":\"MALE\",\"age\":1}"));
    }

    /**
     * Method under test: {@link AuthorEndpoint#update(int, SaveAuthorDto)}
     */
    @Test
    void testUpdate() throws Exception {
        // Arrange
        AuthorResponseDto buildResult = AuthorResponseDto.builder()
                .age(1)
                .firstName("Jane")
                .gender(Gender.MALE)
                .id(1)
                .surname("Doe")
                .build();
        when(authorService.update(anyInt(), Mockito.<SaveAuthorDto>any())).thenReturn(buildResult);
        AuthorResponseDto buildResult2 = AuthorResponseDto.builder()
                .age(1)
                .firstName("Jane")
                .gender(Gender.MALE)
                .id(1)
                .surname("Doe")
                .build();
        when(authorService.getById(anyInt())).thenReturn(buildResult2);

        SaveAuthorDto saveAuthorDto = new SaveAuthorDto();
        saveAuthorDto.setAge(1);
        saveAuthorDto.setGender(Gender.MALE);
        saveAuthorDto.setName("Name");
        saveAuthorDto.setSurname("Doe");
        String content = (new ObjectMapper()).writeValueAsString(saveAuthorDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/authors/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(authorEndpoint)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"firstName\":\"Jane\",\"surname\":\"Doe\",\"gender\":\"MALE\",\"age\":1}"));
    }

    /**
     * Method under test: {@link AuthorEndpoint#createAuthor(SaveAuthorDto)}
     */
    @Test
    void testCreateAuthor() throws Exception {
        // Arrange
        AuthorResponseDto buildResult = AuthorResponseDto.builder()
                .age(1)
                .firstName("Jane")
                .gender(Gender.MALE)
                .id(1)
                .surname("Doe")
                .build();
        when(authorService.create(Mockito.<SaveAuthorDto>any())).thenReturn(buildResult);

        SaveAuthorDto saveAuthorDto = new SaveAuthorDto();
        saveAuthorDto.setAge(1);
        saveAuthorDto.setGender(Gender.MALE);
        saveAuthorDto.setName("Name");
        saveAuthorDto.setSurname("Doe");
        String content = (new ObjectMapper()).writeValueAsString(saveAuthorDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(authorEndpoint)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"firstName\":\"Jane\",\"surname\":\"Doe\",\"gender\":\"MALE\",\"age\":1}"));
    }

    /**
     * Method under test: {@link AuthorEndpoint#deleteById(int)}
     */
    @Test
    void testDeleteById() throws Exception {
        // Arrange
        doNothing().when(authorService).deleteById(anyInt());
        AuthorResponseDto buildResult = AuthorResponseDto.builder()
                .age(1)
                .firstName("Jane")
                .gender(Gender.MALE)
                .id(1)
                .surname("Doe")
                .build();
        when(authorService.getById(anyInt())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/authors/{id}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(authorEndpoint)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
