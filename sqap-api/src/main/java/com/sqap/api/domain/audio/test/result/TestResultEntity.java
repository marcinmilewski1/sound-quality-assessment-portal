package com.sqap.api.domain.audio.test.result;

import com.sqap.api.domain.base.AuditedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "TEST_RESULTS")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
public abstract class TestResultEntity extends AuditedEntity{
    private Integer iteration;

    public TestResultEntity(Integer iteration) {
        this.iteration = iteration;
    }

}
