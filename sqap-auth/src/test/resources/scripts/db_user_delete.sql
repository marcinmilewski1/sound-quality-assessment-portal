BEGIN;
DELETE FROM user_roles
WHERE id = -1;

DELETE FROM users
WHERE id = -1;
COMMIT;;