package com.sqap.api.domain.audio.test.group;

import com.sqap.api.domain.base.AbstractSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestGroupServiceImpl extends AbstractSimpleService<TestGroupRepository>
        implements TestGroupService {

    @Autowired
    public TestGroupServiceImpl(TestGroupRepository repository) {
        super(repository);
    }
}
