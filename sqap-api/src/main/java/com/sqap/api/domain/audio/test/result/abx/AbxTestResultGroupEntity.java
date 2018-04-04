package com.sqap.api.domain.audio.test.result.abx;

import com.sqap.api.domain.audio.test.result.TestResultGroupEntity;
import com.sqap.api.domain.audio.test.result.dto.PrecedingQuestions;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "ABX_GROUP_RESULTS")
@Getter
@PrimaryKeyJoinColumn(name="ID")
@NoArgsConstructor
public class AbxTestResultGroupEntity extends TestResultGroupEntity<AbxTestResultEntity> {
    public AbxTestResultGroupEntity(PrecedingQuestions precedingQuestions, Set<AbxTestResultEntity> results) {
        super(precedingQuestions, results);
    }
}
