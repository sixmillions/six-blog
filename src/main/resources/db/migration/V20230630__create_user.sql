-- 文章基本信息表
create table tt_post
(
    id               bigserial primary key,
    post_type        smallint    default 0,
    disallow_comment boolean     default false,
    edit_time        timestamp   default now(),
    last_edit_time   timestamp   default now(),
    likes            int         default 0,
    meta_description varchar(1024),
    meta_keywords    varchar(1024),
    secret           varchar(1024),
    slug             varchar(250),
    status           smallint    default 0,
    summary          varchar,
    thumbnail        varchar(1024),
    title            varchar(1024),
    top_priority     smallint    default 0,
    visits           int         default 0,
    word_count       int         default 0,
    deleted          smallint    default 0,
    created_by       varchar(50) default 'sys',
    created_at       timestamp   default now(),
    last_modified_by varchar(50) default 'sys',
    last_modified_at timestamp   default now()
);

-- 注释
COMMENT
ON TABLE tt_post IS '文章信息表';
COMMENT
ON COLUMN tt_post.id is '文章信息id：自增、主键';
COMMENT
ON COLUMN tt_post.post_type is '文章类型（0：文章；1：memos）';
COMMENT
ON COLUMN tt_post.disallow_comment is '文章不允许评论（true：不允许评论；false：允许评论）';
COMMENT
ON COLUMN tt_post.edit_time is '文章创建时间';
COMMENT
ON COLUMN tt_post.last_edit_time is '文章最后编辑时间';
COMMENT
ON COLUMN tt_post.likes is '点赞数';
COMMENT
ON COLUMN tt_post.meta_description is '元信息';
COMMENT
ON COLUMN tt_post.meta_keywords is '关键字';
COMMENT
ON COLUMN tt_post.secret is '文章访问密钥';
COMMENT
ON COLUMN tt_post.slug is '简短url，唯一标识符';
COMMENT
ON COLUMN tt_post.status is '文章状态（0：草稿；1：发布）';
COMMENT
ON COLUMN tt_post.summary is '文章摘要';
COMMENT
ON COLUMN tt_post.thumbnail is '文章缩略图/封面';
COMMENT
ON COLUMN tt_post.title is '文章标题';
COMMENT
ON COLUMN tt_post.top_priority is '文章优先级（数字越大，优先级越高）';
COMMENT
ON COLUMN tt_post.visits is '浏览量';
COMMENT
ON COLUMN tt_post.word_count is '文章字数';
COMMENT
ON COLUMN tt_post.deleted IS '逻辑删除（0 未删除、1 删除）';
COMMENT
ON COLUMN tt_post.created_by IS '创建人：默认sys';
COMMENT
ON COLUMN tt_post.created_at IS '创建时间：默认当前时间';
COMMENT
ON COLUMN tt_post.last_modified_by IS '更新人：默认sys';
COMMENT
ON COLUMN tt_post.last_modified_at IS '更新时间：默认当前时间';

-- 初始数据
INSERT INTO tt_post (slug, summary, thumbnail, title, word_count)
VALUES ('markdown-test', 'markdown语法测试', 'https://s.sixmillions.cn/img/cover/markdown.png',
        'Markdown语法测试', 1585);

---

--文章内容表
-- TODO

--文章历史内容表
-- TODO

--评论表
-- TODO

--标签表
-- TODO

--文章标签表
-- TODO

--分类表
-- TODO

--文章分类表
-- TODO

--访问记录表
-- TODO

--系统配置表
-- TODO

--字典表
-- TODO

--菜单表
-- TODO

--黑名单
-- TODO

---

-- 用户表
create table tm_user
(
    id                         bigserial primary key,
    user_name                  varchar(50)  not null unique,
    user_email                 varchar(50)  not null unique,
    user_password              varchar(500) not null,
    phone_number               varchar(20),
    nickname                   varchar(50),
    avatar                     varchar(200),
    description                varchar,
    is_enabled                 boolean     default true,
    is_account_non_expired     boolean     default true,
    is_account_non_locked      boolean     default true,
    is_credentials_non_expired boolean     default true,
    deleted                    smallint    default 0,
    created_by                 varchar(50) default 'sys',
    created_at                 timestamp   default now(),
    last_modified_by           varchar(50) default 'sys',
    last_modified_at           timestamp   default now()
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
ON COLUMN tm_user.phone_number IS '手机号';
COMMENT
ON COLUMN tm_user.nickname IS '昵称';
COMMENT
ON COLUMN tm_user.avatar IS '头像';
COMMENT
ON COLUMN tm_user.description IS '简介';
COMMENT
ON COLUMN tm_user.is_enabled IS '账号是否启用（true 启用、false 未启用）';
COMMENT
ON COLUMN tm_user.is_account_non_expired IS '账号是否未过期（true 未过期、false 已过期）';
COMMENT
ON COLUMN tm_user.is_account_non_locked IS '账号是否未被锁定（true 未被锁定、false 已锁定）';
COMMENT
ON COLUMN tm_user.is_credentials_non_expired IS '账号密码是否未过期（true 未过期、false 已过期）';
COMMENT
ON COLUMN tm_user.deleted IS '逻辑删除（0 未删除、1 删除）';
COMMENT
ON COLUMN tm_user.created_by IS '创建人：默认sys';
COMMENT
ON COLUMN tm_user.created_at IS '创建时间：默认当前时间';
COMMENT
ON COLUMN tm_user.last_modified_by IS '更新人：默认sys';
COMMENT
ON COLUMN tm_user.last_modified_at IS '更新时间：默认当前时间';

-- 初始数据
INSERT INTO tm_user (user_name, user_email, user_password, nickname, description)
VALUES ('admin', 'admin@mail.com', '$2a$10$7qRHUr4HbgyG9DRntejPJe/Nt.zbl95a0TxDv7CGnC7Pj8tGNU88y',
        '管理员', '账号密码 admin/admin');
INSERT INTO tm_user (user_name, user_email, user_password, nickname, description)
VALUES ('user', 'user@mail.com', '$2a$10$hXtG7ArUl50r8CX8K06KzOD8GBBOE.U9EHdRdaggVVw0fyCpnL2lS',
        '普通用户', '账号密码 user/user');

---

-- 角色表
create table tm_role
(
    id               bigserial primary key,
    role_code        varchar(50) not null unique,
    role_name        varchar(50) not null,
    avatar           varchar(200),
    description      varchar,
    deleted          smallint    default 0,
    created_by       varchar(50) default 'sys',
    created_at       timestamp   default now(),
    last_modified_by varchar(50) default 'sys',
    last_modified_at timestamp   default now()
);

-- 注释
COMMENT
ON TABLE tm_role IS '角色表';
COMMENT
ON COLUMN tm_role.id IS '角色id：主键、自增';
COMMENT
ON COLUMN tm_role.role_code IS '角色code：必填且唯一';
COMMENT
ON COLUMN tm_role.role_name IS '角色名：必填';
COMMENT
ON COLUMN tm_role.avatar IS '角色头像';
COMMENT
ON COLUMN tm_role.description IS '角色简介';
COMMENT
ON COLUMN tm_role.deleted IS '逻辑删除（0 未删除、1 删除）';
COMMENT
ON COLUMN tm_role.created_by IS '创建人：默认sys';
COMMENT
ON COLUMN tm_role.created_at IS '创建时间：默认当前时间';
COMMENT
ON COLUMN tm_role.last_modified_by IS '更新人：默认sys';
COMMENT
ON COLUMN tm_role.last_modified_at IS '更新时间：默认当前时间';

-- 初始数据
INSERT INTO tm_role (role_code, role_name, description)
VALUES ('ROLE_ADMIN', '管理员', '管理员拥有较高的权限');
INSERT INTO tm_role (role_code, role_name, description)
VALUES ('ROLE_USER', '用户', '用户拥有基本的权限');

--

-- 用户与角色的关系表
create table tr_user_role
(
    id               bigserial primary key,
    user_id          bigint not null,
    role_id          bigint not null,
    deleted          smallint    default 0,
    created_by       varchar(50) default 'sys',
    created_at       timestamp   default now(),
    last_modified_by varchar(50) default 'sys',
    last_modified_at timestamp   default now()
);

-- 注释
COMMENT
ON TABLE tr_user_role IS '用户与角色的关系表';
COMMENT
ON COLUMN tr_user_role.id IS '关系id：主键、自增';
COMMENT
ON COLUMN tr_user_role.user_id IS '用户id';
COMMENT
ON COLUMN tr_user_role.role_id IS '角色id';
COMMENT
ON COLUMN tr_user_role.deleted IS '逻辑删除（0 未删除、1 删除）';
COMMENT
ON COLUMN tr_user_role.created_by IS '创建人：默认sys';
COMMENT
ON COLUMN tr_user_role.created_at IS '创建时间：默认当前时间';
COMMENT
ON COLUMN tr_user_role.last_modified_by IS '更新人：默认sys';
COMMENT
ON COLUMN tr_user_role.last_modified_at IS '更新时间：默认当前时间';

-- 初始数据
INSERT INTO tr_user_role (user_id, role_id)
VALUES (1, 1);
INSERT INTO tr_user_role (user_id, role_id)
VALUES (1, 2);
INSERT INTO tr_user_role (user_id, role_id)
VALUES (1, 2);

---

-- 文件表
CREATE TABLE tt_file
(
    id                 bigserial primary key,
    file_url           varchar(100) not null,
    file_name          varchar(100),
    file_original_name varchar(100),
    bucket_name        varchar(30),
    file_path          varchar(100),
    file_size          int         default 0,
    media_type         varchar(30),
    suffix             varchar(30),
    img_height         int,
    img_width          int,
    deleted            smallint    default 0,
    created_by         varchar(50) default 'sys',
    created_at         timestamp   default now(),
    last_modified_by   varchar(50) default 'sys',
    last_modified_at   timestamp   default now()
);

-- 注释
COMMENT
ON TABLE tt_file IS '文件表';
COMMENT
ON COLUMN tt_file.id IS '文件id：主键、自增';
COMMENT
ON COLUMN tt_file.file_url IS '文件url：必填';
COMMENT
ON COLUMN tt_file.file_name IS '新文件名';
COMMENT
ON COLUMN tt_file.file_original_name IS '原始文件名';
COMMENT
ON COLUMN tt_file.bucket_name IS '存储桶名称';
COMMENT
ON COLUMN tt_file.file_path IS 'oss中的路径+新文件名';
COMMENT
ON COLUMN tt_file.file_size IS '文件大小(kb)：默认0';
COMMENT
ON COLUMN tt_file.media_type IS '文件类型';
COMMENT
ON COLUMN tt_file.suffix IS '文件后缀名';
COMMENT
ON COLUMN tt_file.img_height IS '图片高（文件是图片时）';
COMMENT
ON COLUMN tt_file.img_width IS '图片高（文件是图片时）';
COMMENT
ON COLUMN tt_file.deleted IS '逻辑删除（0 未删除、1 删除）';
COMMENT
ON COLUMN tt_file.created_by IS '创建人：默认sys';
COMMENT
ON COLUMN tt_file.created_at IS '创建时间：默认当前时间';
COMMENT
ON COLUMN tt_file.last_modified_by IS '更新人：默认sys';
COMMENT
ON COLUMN tt_file.last_modified_at IS '更新时间：默认当前时间';

-- 初始数据
INSERT INTO public.tt_file
(file_url, file_name, file_original_name, bucket_name, file_path,
 file_size, media_type, suffix, img_height, img_width)
VALUES ('https://s.sixmillions.cn/img/logo/logo.png', 'logo.png', 'logo.png', 'img', 'logo/logo.png',
        7, 'image/png', 'png', 192, 192);

