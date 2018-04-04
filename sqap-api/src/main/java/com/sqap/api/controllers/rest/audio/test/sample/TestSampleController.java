package com.sqap.api.controllers.rest.audio.test.sample;

import com.sqap.api.domain.audio.test.sample.TestSampleRepository;
import com.sqap.api.domain.audio.test.single.TestRepository;
import com.sqap.api.domain.file.resource.ResourceEntity;
import com.sqap.api.domain.file.resource.ResourceEntityRepository;
import com.sqap.api.domain.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class TestSampleController {

    private final UserService userService;
    private final TestSampleRepository testSampleRepository;
    private final ResourceEntityRepository resourceEntityRepository;

    @Autowired
    public TestSampleController(UserService userService, TestRepository testRepository, TestSampleRepository testSampleRepository, ResourceEntityRepository resourceEntityRepository) {
        this.userService = userService;
        this.testSampleRepository = testSampleRepository;
        this.resourceEntityRepository = resourceEntityRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/samples/{sampleId}/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getFile(@PathVariable Long sampleId) {
        ResourceEntity resourceEntity = testSampleRepository.getFile(sampleId);
        byte[] content = resourceEntity.getContent();
        String fileName = testSampleRepository.findOne(sampleId).getFileName();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("audio", FilenameUtils.getExtension(fileName)));
        header.setContentLength(content.length);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName.replace(" ", "_"));
        return new ResponseEntity<byte[]>(content, header, HttpStatus.OK);
    }
}
