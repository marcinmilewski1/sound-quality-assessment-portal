package com.sqap.api.domain.audio.test.base;

import com.sqap.api.domain.audio.test.single.abx.AbxTestEntity;
import com.sqap.api.domain.audio.test.single.mushra.MushraTestEntity;
import com.sqap.api.domain.audio.test.sample.TestSampleDto;
import com.sqap.api.domain.audio.test.sample.TestSampleEntity;
import com.sqap.api.domain.file.FileStorageService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class TestGroupEntityFactoryImpl implements TestGroupEntityFactory {

    @Setter
    private FileStorageService storageService;

    @Autowired
    public TestGroupEntityFactoryImpl(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public TestGroupEntity create(TestGroupDto testGroupDto) {
        Set<AbxTestEntity> abxTestEntities = testGroupDto.getTests().getAbxTests().stream()
                .map(dto -> new AbxTestEntity(dto.getOrderNumber(), dto.getName(), dto.getDescription(), dto.getRepetitionNumber(), createTestSamplesEntity(dto.getSamples())))
                .collect(Collectors.toSet());

        Set<MushraTestEntity> mushraTestEntities = testGroupDto.getTests().getMushraTests().stream()
                .map(dto -> new MushraTestEntity(dto.getOrderNumber(), dto.getName(), dto.getDescription(), dto.getRepetitionNumber(), createTestSamplesEntity(dto.getSamples())))
                .collect(Collectors.toSet());

        return new TestGroupEntity(testGroupDto.getName(), testGroupDto.getDescription(),
                new TestGroupCharacteristics(testGroupDto.getFormalities().isGlobalRulesForCreatorAccepted(),
                        testGroupDto.getFormalities().isAllowAnonymouse(), testGroupDto.getFormalities().getPurposeOfResearch(), testGroupDto.getFormalities().getDurationTime()),
                Stream.concat(abxTestEntities.stream(), mushraTestEntities.stream())
                        .collect(Collectors.toSet()));
    }

    private Set<TestSampleEntity> createTestSamplesEntity(List<TestSampleDto> samples) {

        return samples.stream()
                .map(dto -> new TestSampleEntity(dto.getKey(),
                        storageService.getAsResource(UUID.fromString(dto.getFileDesc())).getFilename(),
                        storageService.getAsBytes(UUID.fromString(dto.getFileDesc()))))
                .collect(Collectors.toSet());
    }

}
