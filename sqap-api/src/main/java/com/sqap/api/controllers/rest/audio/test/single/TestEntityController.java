package com.sqap.api.controllers.rest.audio.test.single;

import com.sqap.api.domain.audio.test.base.TestType;
import com.sqap.api.domain.audio.test.result.dto.TestGroupResultDto;
import com.sqap.api.domain.audio.test.single.service.TestService;
import com.sqap.api.domain.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RepositoryRestController
@Slf4j
public class TestEntityController  {

    private final TestService testService;
    private final PagedResourcesAssembler pagedResourcesAssembler;
    private final UserService userService;

    @Autowired
    public TestEntityController(TestService testService, PagedResourcesAssembler pagedResourcesAssembler, UserService userService) {
        this.testService = testService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, value =
            {"/abxTestEntities/{testId}/addResult",
            "/tests/{testId}/addResult"
            ,"mushraTestEntities/{testId}/addResult"})
    @Transactional
    @ResponseBody
    public ResponseEntity<?> addTestGroupResult(@PathVariable Long testId, @RequestBody @Valid TestGroupResultDto testGroupResultDto) {
        if (userService.findRequester() == null) {
            if (testService.getRepository().findOne(testId).getTestGroupEntity().isAnonymouseAllowed()) {
                return ResponseEntity.created(URI.create("/tests/" + testId + "/addResult")).body(this.testService.addTestGroupResult(testId, testGroupResultDto));
            } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
            return ResponseEntity.created(URI.create("/tests/" + testId + "/addResult")).body(this.testService.addTestGroupResult(testId, testGroupResultDto));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value =
            {"/abxTestEntities/{testId}/getResultsFormatted"})
//    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @ResponseBody
    public ResponseEntity<?> getAbxResultsFormatted(@PathVariable Long testId) {
        return ResponseEntity.ok(testService.getAbxResultsFormatted(testId, TestType.ABX));
    }

    @RequestMapping(method = RequestMethod.GET, value =
            {"/mushraTestEntities/{testId}/getResultsFormatted"})
//    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @ResponseBody
    public ResponseEntity<?> getMushraResultsFormatted(@PathVariable Long testId) {
        return ResponseEntity.ok(testService.getMushraResultsFormatted(testId, TestType.MUSHRA));
    }
}
