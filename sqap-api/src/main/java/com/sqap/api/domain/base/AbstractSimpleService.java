package com.sqap.api.domain.base;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractSimpleService <R> implements SimpleService<R> {

    private final R repository;

    @Autowired
    public AbstractSimpleService(R repository) {
        this.repository = repository;
    }

    @Override
    public R getRepository() {
        return repository;
    }
}
