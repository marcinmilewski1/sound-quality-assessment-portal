package com.sqap.api.domain.user.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sqap.api.domain.base.AuditedEntity;
import com.sqap.api.domain.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USER_ROLES")
@Getter
@NoArgsConstructor
public class RoleEntity extends AuditedEntity {

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    @Setter
    @JsonIgnore
    private UserEntity user;

    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public RoleEntity(UserEntity user, RoleType role) {
        this.user = user;
        this.role = role;
    }
}
