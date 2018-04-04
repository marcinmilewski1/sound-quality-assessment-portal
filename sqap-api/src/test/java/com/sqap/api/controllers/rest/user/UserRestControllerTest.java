package com.sqap.api.controllers.rest.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqap.api.domain.audio.test.base.Sex;
import com.sqap.api.domain.user.UserDto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
//@AutoConfigureRestDocs("target/generated-snippets")
public class UserRestControllerTest {

    @Autowired
    private WebApplicationContext context;


    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");


    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Before
    public void setup() {
        this.mockMvc =   MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(print())
                .build();
    }

    @Test
    public void createUser() throws Exception {

        UserDto userDto = new UserDto("testUser", "pass", "pass", "test@x.com", true, false, Sex.MALE, 18);

        this.mockMvc.perform(
                post("/users/").contentType(MediaType.APPLICATION_JSON).content(
                        this.objectMapper.writeValueAsString(userDto)
                )
        ).andExpect(status().isOk());
    }
}