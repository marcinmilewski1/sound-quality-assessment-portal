package com.sqap.api.domain.audio.test.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import com.sqap.api.domain.audio.test.single.TestEntity;
import com.sqap.api.domain.base.AuditedEntity;
import com.sqap.api.domain.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "TEST_GROUPS")
@Getter
@NoArgsConstructor
public class TestGroupEntity extends AuditedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER", nullable = false)
    @JsonIgnore
//    @LazyToOne(LazyToOneOption.PROXY)
    protected UserEntity owner;

    @Column(name = "DESCRIPTION", length = 1000)
    @Setter
    protected String description;

    @Column(name = "NAME")
    @Setter
    protected String name;

    @Column(name = "FINISHED", nullable = false)
    @Setter
    protected boolean finished = false;

    @Column(name = "CREATOR_STATEMENT_ACCEPTED")
    @Setter
    protected boolean creatorStatementAccepted = false;

    @Column(name = "ANONYMOUSE_ALLOWED")
    @Setter
    protected boolean anonymouseAllowed = false;

    @Column(name = "RESEARCH_PURPOSE", length = 1000)
    @Setter
    protected String researchPurpose;

    @Column(name ="ESTIMATED_DURATION_TIME")
    @Setter
    protected Integer estimatedDurationTime;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected Set<TestEntity> tests = Sets.newHashSet();

    @SuppressWarnings("deprecation") // binding this to parent
    public boolean addTest(TestEntity testEntity) {
        testEntity.setTestGroupEntity(this);
        return tests.add(testEntity);
    }

    public boolean removeTest(TestEntity testEntity) {
        return tests.remove(testEntity);
    }

    @SuppressWarnings("deprecation")
    public TestGroupEntity(String name, String description, TestGroupCharacteristics testGroupCharacteristics, Set<TestEntity> tests) {
        this.name = name;
        this.description = description;
        this.creatorStatementAccepted = testGroupCharacteristics.isCreatorStatementAccepted();
        this.anonymouseAllowed = testGroupCharacteristics.isAnonymouseAllowed();
        this.researchPurpose = testGroupCharacteristics.getResearchPurpose();
        this.estimatedDurationTime = testGroupCharacteristics.getEstimatedDurationTime();
        this.tests = tests;
        this.tests.forEach(testEntity -> testEntity.setTestGroupEntity(this));
    }

    @Deprecated
    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }
}

