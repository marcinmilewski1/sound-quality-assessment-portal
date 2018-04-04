package com.sqap.api.domain.audio.test.single;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sqap.api.domain.audio.test.base.TestGroupEntity;
import com.sqap.api.domain.audio.test.result.TestResultGroupEntity;
import com.sqap.api.domain.audio.test.sample.TestSampleEntity;
import com.sqap.api.domain.base.AuditedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "AUDIO_TESTS")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor
public abstract class TestEntity extends AuditedEntity {

    @Column(name = "ORDER_NUMBER", nullable = false)
    protected Integer orderNumber;

    @Column(name = "name", nullable = false)
    @Setter
    protected String name;

    @Column(name = "description", length = 1000)
    @Setter
    protected String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_group", nullable = false)
    @JsonIgnore
    protected TestGroupEntity testGroupEntity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    Set<TestSampleEntity> samples;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "TEST_ID")
    Set<TestResultGroupEntity> testResultGroups;

    @Column(name = "REPETITIONS_NUMBER")
    private Integer repetitionsNumber;

    public TestEntity(Integer orderNumber, String name, String description, Set<TestSampleEntity> testSampleEntitySet, Integer repetitionsNumber) {
        this.orderNumber = orderNumber;
        this.name = name;
        this.description = description;
        this.samples = testSampleEntitySet;
        this.repetitionsNumber = repetitionsNumber;
    }

    public boolean addTestResultGroup(TestResultGroupEntity testResultGroupEntity) {
        return this.testResultGroups.add(testResultGroupEntity);
    }

    @Deprecated
    public void setTestGroupEntity(TestGroupEntity testGroupEntity) {
        if (this.testGroupEntity != null) {
            throw new IllegalArgumentException("forbidden");
        }
        this.testGroupEntity = testGroupEntity;
    }
}