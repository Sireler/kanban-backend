INSERT INTO roles(name) SELECT ('ROLE_USER')
    WHERE NOT EXISTS (SELECT * FROM roles);

INSERT INTO roles(name) SELECT ('ROLE_ADMIN')
    WHERE (SELECT count(*) FROM roles) = 1;

INSERT INTO users(username, first_name, last_name, email, password)
    VALUES ('user', 'test', 'test', 'test@mail.com', 'password');
INSERT INTO user_roles(user_id, role_id)
    VALUES (1, 1);