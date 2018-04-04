package com.sqap.api.domain.audio.test.sample;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sqap.api.domain.base.AuditedEntity;
import com.sqap.api.domain.file.resource.ResourceEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TEST_SAMPLES")
@Getter
@NoArgsConstructor
public class TestSampleEntity extends AuditedEntity{

    @Column(name = "KEY", nullable = false)
    private String key;

    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "RESOURCE_FK")
    @JsonIgnore
    private ResourceEntity resource;

//    @Column(name = "RESOURCE_FK", insertable = false, updatable = false)
//    private Long resource_fk;

    public TestSampleEntity(String key, String fileName, byte[] content) {
        this.key = key;
        this.fileName = fileName;
        this.resource = new ResourceEntity(content);
    }
}
