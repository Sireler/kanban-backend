CREATE TABLE IF NOT EXISTS users (
    id bigserial primary key,
    username varchar(80) NOT NULL UNIQUE,
    first_name varchar(80) NOT NULL,
    last_name varchar(80) NOT NULL,
    email varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    created_at timestamp default now(),
    updated_at timestamp default now()
);

CREATE TABLE IF NOT EXISTS roles (
    id bigserial primary key,
    name varchar(80) NOT NULL,
    created_at timestamp default NULL,
    updated_at timestamp default NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id bigserial NOT NULL,
    role_id bigserial NOT NULL
);

CREATE TABLE IF NOT EXISTS workspaces (
    id bigserial primary key,
    name varchar(60) NOT NULL,
    user_id bigserial NOT NULL,
    created_at timestamp default NULL,
    updated_at timestamp default NULL
);

CREATE TABLE IF NOT EXISTS workspace_users (
    workspace_id bigserial NOT NULL,
    user_id bigserial NOT NULL
);

CREATE TABLE IF NOT EXISTS lists (
    id bigserial primary key,
    workspace_id bigserial NOT NULL,
    title varchar(100) NOT NULL,
    created_at timestamp default NULL,
    updated_at timestamp default NULL
);

CREATE TABLE IF NOT EXISTS cards (
    id bigserial primary key,
    list_id bigserial NOT NULL,
    body text NOT NULL,
    created_at timestamp default NULL,
    updated_at timestamp default NULL
);