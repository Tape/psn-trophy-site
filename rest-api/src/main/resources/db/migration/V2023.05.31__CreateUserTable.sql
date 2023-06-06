create table if not exists users
(
    id              uuid      not null default gen_random_uuid(), -- Unique ID
    psn_name        text      not null,                           -- PSN account name
    password        text,                                         -- Allowed to be null until validated
    validation_code text,                                         -- Code for use validating the account
    created_at      timestamp not null default now(),
    updated_at      timestamp not null default now(),
    primary key (id),
    unique (psn_name)
);
