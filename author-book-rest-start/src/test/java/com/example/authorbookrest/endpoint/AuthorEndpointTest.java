package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.AuthorBookRestApplication;
import com.example.authorbookrest.dto.Gender;
import com.example.authorbookrest.dto.SaveAuthorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AuthorBookRestApplication.class)
@AutoConfigureMockMvc
class AuthorEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    void createAuthor() throws Exception {
        SaveAuthorDto saveAuthorDto = SaveAuthorDto.builder()
                .name("aaa")
                .surname("aaa")
                .age(345)
                .gender(Gender.MALE)
                .build();
        String json = mapper.writeValueAsString(saveAuthorDto);
        mockMvc.perform(post("http://localhost:8080/v1/authors")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.firstName", Matchers.equalTo("aaa")));
    }

    @Test
    void getById() {
    }
}