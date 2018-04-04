package com.sqap.api.domain.audio.test.result.mushra;

import com.sqap.api.domain.audio.test.result.TestResultEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "MUSHRA_TEST_RESULTS")
@Getter
@PrimaryKeyJoinColumn(name="ID")
@NoArgsConstructor
public class MushraTestResultEntity extends TestResultEntity {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_result_id")
    private Set<MushraTestSampleResultEntity> sampleResults;

    public MushraTestResultEntity(Integer iteration, Set<MushraTestSampleResultEntity> sampleResults) {
        super(iteration);
        this.sampleResults = sampleResults;
    }
}
