package com.sqap.api.config.resource.processor;

import com.sqap.api.controllers.rest.audio.test.group.TestGroupController;
import com.sqap.api.domain.audio.test.base.TestGroupEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
@Slf4j
public class SearchResourcesProcessor implements ResourceProcessor<RepositorySearchesResource>{
    @Override
    public RepositorySearchesResource process(RepositorySearchesResource repositorySearchesResource) {
        final String search = repositorySearchesResource.getId().getHref();
        if (search == null) {
            // todonext
        }
        else {
            if (repositorySearchesResource.getDomainType().equals(TestGroupEntity.class)) {
                repositorySearchesResource.add(ControllerLinkBuilder.linkTo(methodOn(TestGroupController.class).findOwn(null, null)).withRel("findOwn"));
                repositorySearchesResource.add(linkTo(methodOn(TestGroupController.class).findNotParticipated(null, null)).withRel("findNotParticipated"));
            }
        }
//        final Link findOwn = new Link(search + "/findOwn").withRel("findOwn");
//        repositorySearchesResource.add(findOwn);

        return repositorySearchesResource;
    }
}
