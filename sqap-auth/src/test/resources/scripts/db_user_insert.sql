BEGIN;
insert into users (id, uuid, username, password, enabled, version)
    VALUES (-1, '0000', 'test', 'pass', TRUE, 1);

insert into user_roles (id, uuid, username, role, version)
    VALUES (-1, '0000', 'test', 'ROLE_TEST', 1);
COMMIT;
