package com.sqap.api.domain.audio.test.single.service;

import com.sqap.api.domain.audio.test.base.TestType;
import com.sqap.api.domain.audio.test.result.abx.AbxFormattedResult;
import com.sqap.api.domain.audio.test.result.dto.TestGroupResultDto;
import com.sqap.api.domain.audio.test.result.mushra.MushraFormattedResult;
import com.sqap.api.domain.audio.test.single.TestEntity;
import com.sqap.api.domain.audio.test.single.TestRepository;
import com.sqap.api.domain.base.SimpleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TestService extends SimpleService<TestRepository> {
    @Transactional
    TestEntity addTestGroupResult(Long testId, TestGroupResultDto testGroupResultDto);

    AbxFormattedResult getAbxResultsFormatted(Long testId, TestType testType);

    List<MushraFormattedResult> getMushraResultsFormatted(Long testId, TestType testType);
}
