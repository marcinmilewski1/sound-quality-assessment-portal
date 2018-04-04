package com.sqap.api.controllers.rest.user;

import com.sqap.api.domain.user.PersonalData;
import com.sqap.api.domain.user.UserDto;
import com.sqap.api.domain.user.UserEntity;
import com.sqap.api.domain.user.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

@RepositoryRestController
@RequestMapping(path = "/users")
@Slf4j
public class UserRestController {

    private final UserServiceImpl userService;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    @Autowired
    public UserRestController(UserServiceImpl userService, PagedResourcesAssembler pagedResourcesAssembler) {
        this.userService = userService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserEntity> create(@RequestBody UserDto userDto) {
        UserEntity user = userService.save(userDto);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/personal")
    @ResponseBody
    public ResponseEntity<PersonalData> getPersonalData() {
        UserEntity requester = userService.findRequester();
        return ResponseEntity.ok(new PersonalData(requester.getSex(), requester.getAge(), requester.getIsHearingDefect()));
    }

//    @RequestMapping("/user")
//    public Principal user(Principal user) {
//        return user;
//    }
}
