package com.sqap.api.config;

import com.sqap.api.domain.audio.test.base.TestGroupEntity;
import com.sqap.api.domain.audio.test.result.TestResultEntity;
import com.sqap.api.domain.audio.test.sample.TestSampleEntity;
import com.sqap.api.domain.audio.test.single.TestEntity;
import com.sqap.api.domain.base.BaseEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class SpringDataRestCustomization extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//        super.configureRepositoryRestConfiguration(config);
//        config.exposeIdsFor(BaseEntity.class);
        config.exposeIdsFor(BaseEntity.class);
        config.exposeIdsFor(TestGroupEntity.class);
        config.exposeIdsFor(TestEntity.class);
        config.exposeIdsFor(TestSampleEntity.class);
        config.exposeIdsFor(TestResultEntity.class);
//        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
//        provider.addIncludeFilter(new AssignableTypeFilter(BaseEntity.class));
//        Set<BeanDefinition> components = provider.findCandidateComponents(this.getClass().getPackage().getName());
//        List<Class<?>> classes = new ArrayList<>();
//
//        components.forEach(component -> {
//            try {
//                classes.add(Class.forName(component.getBeanClassName()));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//        config.exposeIdsFor(classes.toArray(new Class[classes.size()]));
    }

    @Bean
    public SpelAwareProxyProjectionFactory projectionFactory() {
        return new SpelAwareProxyProjectionFactory();
    }
}