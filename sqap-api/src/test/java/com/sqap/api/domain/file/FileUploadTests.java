package com.sqap.api.domain.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqap.base.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Path;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FileUploadTests extends MockMvcTest {

    @SpyBean
    private FileStorageService storageService;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc =   MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(print())
                .build();
    }

    @Test
    @WithMockUser(username="tester")
    public void shouldSaveUploadedFile() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", "test.txt", "text/plain", "Spring Framework".getBytes());
        MvcResult mvcResult = this.mockMvc.perform(fileUpload("/files").file(multipartFile))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(content().json().)
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        verify(storageService).addFile("tester", multipartFile);

        UUID uuid = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UUID.class);
        Path file = storageService.get(uuid);
        assertThat(file.getFileName().toString(), is("test.txt"));
    }

    @Test
    @WithAnonymousUser
    public void shouldNotUploadWhenAnonymousUser() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", "test.txt", "text/plain", "Spring Framework".getBytes());
        this.mockMvc.perform(fileUpload("/files").file(multipartFile))
                .andExpect(status().isUnauthorized());
    }


}
