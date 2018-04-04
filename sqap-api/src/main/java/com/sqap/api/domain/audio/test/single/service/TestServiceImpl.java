package com.sqap.api.domain.audio.test.single.service;

import com.sqap.api.domain.audio.test.base.TestType;
import com.sqap.api.domain.audio.test.result.TestResultGroupEntity;
import com.sqap.api.domain.audio.test.result.abx.AbxFormattedResult;
import com.sqap.api.domain.audio.test.result.dto.TestResultCreator;
import com.sqap.api.domain.audio.test.result.dto.TestGroupResultDto;
import com.sqap.api.domain.audio.test.result.mushra.MushraFormattedResult;
import com.sqap.api.domain.audio.test.single.TestEntity;
import com.sqap.api.domain.audio.test.single.TestRepository;
import com.sqap.api.domain.audio.test.single.abx.AbxTestRepository;
import com.sqap.api.domain.audio.test.single.mushra.MushraTestRepository;
import com.sqap.api.domain.base.AbstractSimpleService;
import org.apache.commons.math3.distribution.BinomialDistribution;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class TestServiceImpl extends AbstractSimpleService<TestRepository>
        implements TestService {

    private final AbxTestRepository abxTestRepository;
    private final MushraTestRepository mushraTestRepository;

    public TestServiceImpl(TestRepository repository, AbxTestRepository abxTestRepository, MushraTestRepository mushraTestRepository) {
        super(repository);
        this.abxTestRepository = abxTestRepository;
        this.mushraTestRepository = mushraTestRepository;
    }

    @Override
    @Transactional
    public TestEntity addTestGroupResult(Long testId, TestGroupResultDto testGroupResultDto) {
        TestResultGroupEntity testGroupResultEntity = TestResultCreator.create(testGroupResultDto);
        TestEntity testEntity = getRepository().findOne(testId);
        testEntity.addTestResultGroup(testGroupResultEntity);
        return getRepository().save(testEntity);
    }

    @Override
    public AbxFormattedResult getAbxResultsFormatted(Long testId, TestType testType) {
        if (TestType.ABX.equals(testType)) {
            List<Object[]> queryResult = this.abxTestRepository.getResultsFormatted(testId);
            if (null == queryResult || queryResult.size() == 0) return null;
            AbxFormattedResult abxFormattedResult = queryResult.stream().map(qr -> new AbxFormattedResult((BigInteger)qr[0], (BigInteger) qr[1], (BigInteger) qr[2], (BigInteger) qr[3],
                    (BigInteger) qr[4], (BigInteger) qr[5], (BigInteger) qr[6], 0d))
                    .collect(Collectors.toList()).get(0);
            BinomialDistribution binomialDistribution = new BinomialDistribution(abxFormattedResult.getAnswers().intValue(), 0.5);
            double pValue = 1.0 - binomialDistribution.cumulativeProbability((abxFormattedResult.getCorrect_answers().intValue() - 1));
            abxFormattedResult.setPValue(pValue);
            return abxFormattedResult;
        } else {
            return null;
        }
    }

    @Override
    public List<MushraFormattedResult> getMushraResultsFormatted(Long testId, TestType testType) {
        if (TestType.MUSHRA.equals(testType)) {
            List<Object[]> queryResult = this.mushraTestRepository.getResultsFormatted(testId);
            return queryResult.stream()
                    .map(qr -> new MushraFormattedResult(qr[0], qr[1], qr[2], qr[3]
                            , qr[4], qr[5], qr[6], qr[7])).collect(Collectors.toList());
        } else {
            return null;
        }
    }

}
