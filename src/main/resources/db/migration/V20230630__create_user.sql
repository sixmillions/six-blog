-- 用户表
create table tm_user
(
    id               bigserial primary key,
    user_name        varchar(50)  not null unique,
    user_email       varchar(50)  not null unique,
    user_password    varchar(500) not null,
    roles            varchar(100),
    phone_number     varchar(20),
    nickname         varchar(50),
    avatar           varchar(200),
    description      varchar,
    created_by       varchar(50) default 'sys',
    created_at       timestamp   default now(),
    last_modified_by varchar(50) default 'sys',
    last_modified_at timestamp   default now()
);
-- 注释
COMMENT
ON TABLE tm_user IS '用户表';
COMMENT
ON COLUMN tm_user.id IS '用户id：主键、自增';
 COMMENT
ON COLUMN tm_user.user_name IS '用户名：必填且唯一';
COMMENT
ON COLUMN tm_user.user_email IS '用户邮箱：必填且唯一';
COMMENT
ON COLUMN tm_user.user_password IS '用户密码：必填、加密';
COMMENT
ON COLUMN tm_user.roles IS '角色：多个逗号分隔';
COMMENT
ON COLUMN tm_user.phone_number IS '手机号';
COMMENT
ON COLUMN tm_user.nickname IS '昵称';
COMMENT
ON COLUMN tm_user.avatar IS '头像';
COMMENT
ON COLUMN tm_user.description IS '简介';
COMMENT
ON COLUMN tm_user.created_by IS '创建人：默认sys';
COMMENT
ON COLUMN tm_user.created_at IS '创建时间：默认当前时间';
COMMENT
ON COLUMN tm_user.last_modified_by IS '更新人：默认sys';
COMMENT
ON COLUMN tm_user.last_modified_at IS '更新时间：默认当前时间';

-- 初始数据
INSERT INTO tm_user (user_name, user_email, user_password, roles, nickname, description)
VALUES ('admin', 'admin@mail.com', '$2a$10$7qRHUr4HbgyG9DRntejPJe/Nt.zbl95a0TxDv7CGnC7Pj8tGNU88y',
        'ROLE_ADMIN,ROLE_USER', '管理员', '账号密码 admin/admin');
INSERT INTO tm_user (user_name, user_email, user_password, roles, nickname, description)
VALUES ('user', 'user@mail.com', '$2a$10$hXtG7ArUl50r8CX8K06KzOD8GBBOE.U9EHdRdaggVVw0fyCpnL2lS',
        'ROLE_USER', '普通用户', '账号密码 user/user');

