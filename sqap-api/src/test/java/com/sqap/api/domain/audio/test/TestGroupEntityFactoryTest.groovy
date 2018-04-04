package com.sqap.api.domain.audio.test

import com.sqap.api.domain.audio.test.base.*
import com.sqap.api.domain.audio.test.sample.TestSampleDto
import com.sqap.api.domain.file.FileStorageService
import spock.lang.Specification


class TestGroupEntityFactoryTest extends Specification {

    def "should create"() {

        given:
        FileStorageService fileStorageService = Mock()
        TestGroupEntityFactory entityFactory = new TestGroupEntityFactoryImpl(fileStorageService)
        fileStorageService.getAsBytes(_) >> [1,2,3,4]

        when:
        def a = entityFactory.create(new TestGroupDto("test", "test", new TestsDto([new TestDto(1, "t", "t", [new TestSampleDto("1", UUID.randomUUID().toString())])], [])))

        then:
        1 * fileStorageService.getAsBytes(_)
        a != null
        a.getTests().size() == 1
    }

}
