package com.sqap.api.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import com.sqap.api.domain.audio.test.base.Sex;
import com.sqap.api.domain.audio.test.base.TestGroupEntity;
import com.sqap.api.domain.base.AuditedEntity;
import com.sqap.api.domain.user.role.RoleEntity;
import com.sqap.api.domain.user.role.RoleType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor
public class UserEntity extends AuditedEntity {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "registerStatementConfirmed")
    private Boolean registerStatementConfirmed;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RoleEntity> roles = Sets.newHashSet();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<TestGroupEntity> ownTests = Sets.newHashSet();

    @Column(name = "SEX")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "IS_HEARING_DEFECT")
    private Boolean isHearingDefect;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="USERS_PARTICIPATED_TESTS",
            joinColumns = {@JoinColumn(name="USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="TEST_GROUP_ID", referencedColumnName = "ID")}
            )
    private Set<TestGroupEntity> participated = Sets.newHashSet();

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name="USERS_PARTICIPATED_NOT_FINISHED_TESTS",
//            joinColumns = {@JoinColumn(name="USER_ID", referencedColumnName = "ID")},
//            inverseJoinColumns = {@JoinColumn(name="TEST_GROUP_ID", referencedColumnName = "ID")}
//            )
//    private Set<TestGroupEntity> participatedNotFinished = Sets.newHashSet();

    public boolean addRole(RoleType roleType) {
        if (roles.stream().map(r -> r.getRole()).anyMatch(s -> s.equals(roleType))) {
            RoleEntity role = new RoleEntity(this, roleType);
            return roles.add(role);
        }
        else {
            return false;
        }
    }

    public void addParticipated(TestGroupEntity testGroupEntity) {
        this.participated.add(testGroupEntity);
    }

    public boolean containsRole(RoleType roleType) {
        return roles.stream().map(RoleEntity::getRole).anyMatch(s -> s.equals(roleType));
    }

    @SuppressWarnings("deprecation")
    public boolean addTestGroup(TestGroupEntity testGroupEntity) {
        testGroupEntity.setOwner(this);
        return this.ownTests.add(testGroupEntity);
    }

    public boolean removeTestGroup(TestGroupEntity testGroupEntity) {
        return this.ownTests.remove(testGroupEntity);
    }

    public UserEntity(String username, String password, Boolean enabled, Set<RoleType> roles, Boolean registerStatementConfirmed, PersonalData personalData) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.registerStatementConfirmed = registerStatementConfirmed;
        this.sex = personalData.getSex();
        this.isHearingDefect = personalData.getIsHearingDefect();
        this.age = personalData.getAge();
        this.roles.addAll(roles.stream().map(r -> new RoleEntity(this, r)).collect(Collectors.toSet()));
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}

