INSERT INTO roles(name) SELECT ('ROLE_USER')
    WHERE NOT EXISTS (SELECT * FROM roles);

INSERT INTO roles(name) SELECT ('ROLE_ADMIN')
    WHERE (SELECT count(*) FROM roles) = 1;