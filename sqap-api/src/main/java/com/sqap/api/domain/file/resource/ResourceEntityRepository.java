package com.sqap.api.domain.file.resource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface ResourceEntityRepository extends CrudRepository<ResourceEntity, Long> {

}
