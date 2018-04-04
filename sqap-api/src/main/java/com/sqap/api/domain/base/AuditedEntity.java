package com.sqap.api.domain.base;

import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;


@Audited
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@NoArgsConstructor
public class AuditedEntity extends BaseEntity {

    @CreatedDate
    protected LocalDateTime createdDate;

    @CreatedBy
    protected String createdBy;

    @LastModifiedDate
    protected LocalDateTime updatedDate;

    @LastModifiedBy
    protected String updatedBy;

    @Version
    protected Long version;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }


    public Long getVersion() {
        return version;
    }
}
