package com.sqap.api.domain.audio.test.result.mushra;

import com.sqap.api.domain.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "MUSHRA_TEST_SAMPLE_RESULTS")
@Getter
@PrimaryKeyJoinColumn(name="ID")
@NoArgsConstructor
@AllArgsConstructor
public class MushraTestSampleResultEntity extends BaseEntity{
    private String key;
    private Integer value;
}
