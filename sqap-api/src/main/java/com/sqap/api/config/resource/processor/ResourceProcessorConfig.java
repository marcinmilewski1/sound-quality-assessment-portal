package com.sqap.api.config.resource.processor;

import com.sqap.api.domain.audio.test.group.projections.TestGroupGeneralProjection;
import com.sqap.api.domain.audio.test.sample.TestSampleEntity;
import com.sqap.api.domain.audio.test.single.TestEntity;
import com.sqap.api.domain.audio.test.single.projections.TestEntityAllProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

@Configuration
public class ResourceProcessorConfig {

    @Autowired
    private EntityLinks entityLinks;

    @Bean
    public ResourceProcessor<Resource<TestSampleEntity>> testSampleEntityProcessor() {
        return new ResourceProcessor<Resource<TestSampleEntity>>() {
            @Override
            public Resource<TestSampleEntity> process(Resource<TestSampleEntity> resource) {
                if (resource != null && resource.getId() != null && resource.getId().getHref() != null) {
                    final String href = resource.getId().getHref();
                    final Link getFilesLink = new Link(href + "/file").withRel("file");
                    resource.add(getFilesLink);
                }
                return resource;

//                Method getFileMethod;
//                try {
//                    getFileMethod = TestSampleController.class.getMethod("getFile", Long.class);    // temp because of stackoverflow on methodOn
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                    throw new RuntimeException(e);
//                }
//                Link link = ControllerLinkBuilder.linkTo(getFileMethod, resource.getId()).withRel("file");
//                resource.add(link);
//                return resource;

//                resource.add(new Link("http://localhost:8082/samples/getFile", "getFile"));
//                LinkBuilder linkBuilder = entityLinks.linkForSingleResource(TestSampleEntity.class, resource.getContent().getId());
//                linkBuilder.
//                resource.add(entityLinks.linkToSingleResource(TestSampleEntity.class, resource.getContent().getId()).withRel("getFile"));
            }
        };
    }


    @Bean
    public ResourceProcessor<Resource<TestEntity>> testEntityProcessor() {
        return new ResourceProcessor<Resource<TestEntity>>() {
            @Override
            public Resource<TestEntity> process(Resource<TestEntity> resource) {
                if (resource != null && resource.getId() != null && resource.getId().getHref() != null) {
                    final String href = resource.getId().getHref();
                    final Link addResultLink = new Link(href + "/addResult").withRel("addResult");
                    resource.add(addResultLink);

                    final Link getResultsFormattedLink = new Link(href + "/getResultsFormatted").withRel("getResultsFormatted");
                    resource.add(getResultsFormattedLink);
                }
                return resource;
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<TestEntityAllProjection>> testGroupGeneralProjectionProcessor() {
        return new ResourceProcessor<Resource<TestEntityAllProjection>>() {
            @Override
            public Resource<TestEntityAllProjection> process(Resource<TestEntityAllProjection> resource) {
                if (resource != null && resource.getId() != null && resource.getId().getHref() != null) {
                    final String href = resource.getId().getHref();
                    final Link addResultLink = new Link(href + "/addResult").withRel("addResult");
                    resource.add(addResultLink);

                    final Link getResultsFormattedLink = new Link(href + "/getResultsFormatted").withRel("getResultsFormatted");
                    resource.add(getResultsFormattedLink);
                }
                return resource;
            }
        };

    }

}
