package com.sqap.api.domain.file.resource;

import com.sqap.api.domain.base.AuditedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "RESOURCES")
@Getter
@NoArgsConstructor
public class ResourceEntity extends AuditedEntity {

    @Column(name = "CONTENT", nullable = false)
//    @Basic(fetch = FetchType.LAZY, optional = false)
    @Lob
    private byte[] content;

    public ResourceEntity(byte[] content) {
        this.content = content;
    }
}
