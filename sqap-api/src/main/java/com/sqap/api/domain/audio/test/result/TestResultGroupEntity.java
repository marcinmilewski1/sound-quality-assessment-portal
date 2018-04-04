package com.sqap.api.domain.audio.test.result;

import com.sqap.api.domain.audio.test.base.Sex;
import com.sqap.api.domain.audio.test.result.dto.PrecedingQuestions;
import com.sqap.api.domain.base.AuditedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "GROUP_RESULTS")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor
public abstract class TestResultGroupEntity<T extends TestResultEntity> extends AuditedEntity {

    @Column(name = "IS_HEARING_DEFECT")
    private Boolean isHearingDefect;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "SEX")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    public TestResultGroupEntity(PrecedingQuestions precedingQuestions, Set<T> results) {
        this.isHearingDefect = precedingQuestions.getIsHearingDefect();
        this.age = precedingQuestions.getAge();
        this.sex = precedingQuestions.getSex();
        this.testResults = results;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = TestResultEntity.class)
    @JoinColumn(name="TEST_GROUP_RESULT_ID")
    Set<T> testResults;

}
