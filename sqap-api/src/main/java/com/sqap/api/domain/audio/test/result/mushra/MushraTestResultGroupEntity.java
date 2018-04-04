package com.sqap.api.domain.audio.test.result.mushra;

import com.sqap.api.domain.audio.test.result.TestResultGroupEntity;
import com.sqap.api.domain.audio.test.result.dto.PrecedingQuestions;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "MUSHRA_GROUP_RESULTS")
@Getter
@PrimaryKeyJoinColumn(name="ID")
@NoArgsConstructor
public class MushraTestResultGroupEntity extends TestResultGroupEntity<MushraTestResultEntity> {
    public MushraTestResultGroupEntity(PrecedingQuestions precedingQuestions, Set<MushraTestResultEntity> results) {
        super(precedingQuestions, results);
    }
}
