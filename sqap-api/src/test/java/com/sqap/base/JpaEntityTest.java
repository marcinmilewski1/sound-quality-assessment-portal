package com.sqap.base;

import com.sqap.api.domain.base.BaseEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
public abstract class JpaEntityTest<E extends BaseEntity, R extends CrudRepository, ID extends Serializable> implements CrudRepositoryTestCallbacks<E, R> {

    @Autowired
    private CrudRepository<E, ID> repository;

    @Test
    @Transactional
    public final void saveTest() {
        E saved = save(getEntity());
        existenceAsserts(saved);
        _afterSave(saved);
    }

    @Test
    @Transactional
    public final void findOneTest() {
        E saved = save(getEntity());
        E selected = findOneById((ID) saved.getId());
        equalityAsserts(saved, selected);
    }

    @Test
    @Transactional
    public final void deleteTest() {
        E saved = save(getEntity());
        deleteById((ID) saved.getId());

        E selected = findOneById((ID) saved.getId());
        assertThat(selected, is(nullValue()));
    }

    @Test
    @Transactional
    public final void updateTest() {
        E initial = save(getEntity());
        E entity = (E) getRepository().findOne(initial.getId());
        _update(entity);
        entity = save(entity); // UPDATE on database
        _afterUpdate(entity);
    }

    protected void deleteById(ID id) {
        getRepository().delete(id);
    }

    protected E findOneById(ID id) {
        return (E) getRepository().findOne(id);
    }

    protected R getRepository() {
        return (R) this.repository;
    }

    public abstract E getEntity();

    protected void equalityAsserts(E entity1, E entity2) {
        assertThat(entity1, equalTo(entity2));
    }

    protected void existenceAsserts(E entity) {
        assertThat(entity.getId(), is(notNullValue()));
        assertThat(entity.getUuid(), is(notNullValue()));
    }

    protected E save(E entity) {
        return (E) getRepository().save(entity);
    }

}
