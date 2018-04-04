package com.sqap.doc.descriptors;

import org.springframework.restdocs.payload.FieldDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class AuditedEntityDescriptor {
    public static final FieldDescriptor[] auditedEntity = new FieldDescriptor[] {
            fieldWithPath("id").description("Id"),
            fieldWithPath("author").description("Author of the auditedEntity"),
            fieldWithPath("uuid").description("Uuid"),
            fieldWithPath("createdDate").description("creation date"),
            fieldWithPath("createdBy").description("created by"),
            fieldWithPath("updatedDate").description("update date"),
            fieldWithPath("updatedBy").description("updated by"),
            fieldWithPath("version").description("version")
    };
}
