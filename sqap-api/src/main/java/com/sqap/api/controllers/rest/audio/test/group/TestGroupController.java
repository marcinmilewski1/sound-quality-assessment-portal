package com.sqap.api.controllers.rest.audio.test.group;

import com.sqap.api.domain.audio.test.base.TestGroupDto;
import com.sqap.api.domain.audio.test.base.TestGroupEntity;
import com.sqap.api.domain.audio.test.base.TestGroupEntityFactory;
import com.sqap.api.domain.audio.test.group.TestGroupRepository;
import com.sqap.api.domain.audio.test.group.TestGroupService;
import com.sqap.api.domain.audio.test.group.projections.TestGroupGeneralProjection;
import com.sqap.api.domain.audio.test.single.TestRepository;
import com.sqap.api.domain.user.UserEntity;
import com.sqap.api.domain.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
@Slf4j
//@PreAuthorize(value = "hasRole('ROLE_USER')")
public class TestGroupController {

    private final UserService userService;
    private final TestGroupEntityFactory testGroupEntityFactory;
    private final TestGroupService testGroupService;
    private final TestRepository testRepository;
    private final PagedResourcesAssembler pagedResourcesAssembler;
    private final ProjectionFactory projectionFactory;

    @Autowired
    public TestGroupController(UserService userService, TestGroupEntityFactory
            testGroupEntityFactory, ProjectionFactory projectionFactory, TestGroupService testGroupService, TestRepository testRepository, PagedResourcesAssembler pagedResourcesAssembler) {
        this.userService = userService;
        this.testGroupEntityFactory = testGroupEntityFactory;
        this.testGroupService = testGroupService;
        this.testRepository = testRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.projectionFactory = projectionFactory;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/groups")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody @Valid TestGroupDto testDto) {
        UserEntity user = userService.findRequester();
        TestGroupEntity testGroupEntity = testGroupEntityFactory.create(testDto);
        testGroupEntity.setOwner(user);
        testGroupEntity = testGroupService.getRepository().save(testGroupEntity);
        user.addTestGroup(testGroupEntity);
        user = userService.getRepository().save(user);

        Resource<TestGroupEntity> resource = new Resource<>(testGroupEntity);
        resource.add(linkTo(methodOn(TestGroupController.class).create(testDto)).withSelfRel());
        return ResponseEntity.created(URI.create("/groups/" + testGroupEntity.getId())).body(testGroupEntity);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/groups/search/findOwn")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @ResponseBody
    public PagedResources<?> findOwn(Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        UserEntity user = userService.findRequester();
        Page<TestGroupEntity> tests = testGroupService.getRepository().findByOwnerId(user.getId(), pageable);
        Page<TestGroupGeneralProjection> projected = tests.map(p -> projectionFactory.createProjection(TestGroupGeneralProjection.class, p));
        return pagedResourcesAssembler.toResource(projected);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/groups/search/findNotParticipated")
    @ResponseBody
    public PagedResources<?> findNotParticipated(Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        UserEntity user = userService.findRequester();
        if (user == null) {
            // return anonymouse allowed
            return pagedResourcesAssembler.toResource(testGroupService.getRepository().findByAnonymouseAllowedTrue(pageable));
        }
        List<TestGroupEntity> tests = testGroupService.getRepository().findNotParticipatedAndNotFinished(user.getId());
        Page<TestGroupEntity> testsPage = new PageImpl<TestGroupEntity>(tests, pageable, tests.size());
        Page<TestGroupGeneralProjection> projected = testsPage.map(p -> projectionFactory.createProjection(TestGroupGeneralProjection.class, p));
        return pagedResourcesAssembler.toResource(projected);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/groups/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        UserEntity user = userService.findRequester();
        if (user.getUsername().equals(testGroupService.getRepository().findOne(id).getCreatedBy())) {
            this.testGroupService.getRepository().delete(id);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}

