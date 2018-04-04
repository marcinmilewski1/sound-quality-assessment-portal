package com.sqap.api.domain.file;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private FileStorageService storageService;

    @Test
    public void classPathResourceTest() throws Exception {
        ClassPathResource resource = new ClassPathResource("/test/testFile.txt", getClass());
        assertThat(resource.exists(), is(true));
    }

    @Test
    @WithMockUser(username="tester",roles={"USER"})
    @Ignore // TODO
    public void shouldUploadFile() throws Exception {
        ClassPathResource resource = new ClassPathResource("/test/testFile.txt", getClass());

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("file", resource);
        ResponseEntity<String> response = this.restTemplate.postForEntity("/files", map, String.class);

//        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        then(storageService).should().addFile((any(String.class)), any(MultipartFile.class));
    }

}
