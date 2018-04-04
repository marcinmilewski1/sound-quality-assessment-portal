package com.sqap.base

import com.sqap.SqapApiApplication
import com.sqap.api.domain.audio.test.base.TestGroupEntity
import com.sqap.api.domain.audio.test.group.TestGroupRepository
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.support.Repositories
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
@Transactional
@ContextConfiguration(classes = SqapApiApplication)
class RepositoriesIT extends Specification {

    @Autowired
    ListableBeanFactory beanFactory


    def "Should inject ListableBeanFactory"() {
        expect:
        beanFactory != null
    }

    def "Repositories test"() {
        given:
        Repositories repositories = new Repositories(beanFactory)

        when:
        def size = repositories.asList().size()
        then:
        size > 0

        when:
        def repo = repositories.getRepositoryFor(TestGroupEntity.class)
        then:
        repo instanceof TestGroupRepository
    }
}