package com.sqap.api.domain.base;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericRepositoryService {

    @Autowired
    @Lazy
    private List<JpaTypedRepository> repos;

    public GenericRepositoryService() {

    }

    public JpaTypedRepository getRepository(String targetType) {
        return repos.stream().filter(r -> r.getTargetType().equals(targetType)).findFirst().orElseThrow(RuntimeException::new);
    }

    public Object findById(String targetType, Long id) {
        return getRepository(targetType).findOne(id);
    }

//    public Object save(Class entityClass) {
//        return getRepository(entityClass).save(entity);
//    }
//
//    public Object findAll(Class entityClass) {
//        return getRepository(entityClass).findAll();
//    }
//
//    public void delete(Class entityClass) {
//        getRepository(entityClass).delete(entity);
//    }


}
