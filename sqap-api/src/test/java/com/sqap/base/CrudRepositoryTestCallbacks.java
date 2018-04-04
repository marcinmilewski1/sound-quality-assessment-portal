package com.sqap.base;

import com.sqap.api.domain.base.BaseEntity;
import org.springframework.data.repository.CrudRepository;


public interface CrudRepositoryTestCallbacks<E extends BaseEntity, R extends CrudRepository> {
    default void _afterSave(E entity) {};
    default void _afterFindOne(E entity) {};
    void _update(E entity);
    default void _afterUpdate(E entity) {}
}
