create table if not exists chart
(
    id         bigint            not null comment '主键'
        primary key,
    goal       text              null comment '分析目标',
    chartData  text              null comment '图表数据',
    chartType  tinyint           null comment '图表类型 0 雷达等',
    genChart   text              null comment '生成的图表数据',
    genResult  text              null comment '生成的分析结论',
    userId     varchar(512)      null comment '创建用户id',
    createTime datetime          null comment '创建时间',
    updateTime datetime          null comment '更新时间',
    deleted    tinyint default 0 null comment '是否删除 0未删除 1已删除',
    name       varchar(128)      null comment '图表名称'
);

create table if not exists dict_data
(
    id          bigint auto_increment comment '字典编码'
        primary key,
    dictLabel   varchar(100) default ''  null comment '字典标签',
    dictValue   varchar(100) default ''  null comment '字典键值',
    dictType    varchar(100) default ''  null comment '字典类型',
    defaultFlag char         default 'N' null comment '是否默认（Y是 N否）',
    status      char         default '0' null comment '状态（0正常 1停用）',
    dictSort    int          default 0   null comment '字典排序',
    createBy    varchar(64)  default ''  null comment '创建者',
    createTime  datetime                 null comment '创建时间',
    updateBy    varchar(64)  default ''  null comment '更新者',
    updateTime  datetime                 null comment '更新时间',
    remark      varchar(500)             null comment '备注'
)
    comment '字典数据表';

create table if not exists dict_type
(
    id         bigint auto_increment comment '字典主键'
        primary key,
    dictName   varchar(100) default ''  null comment '字典名称',
    dictType   varchar(100) default ''  null comment '字典类型',
    status     char         default '0' null comment '状态（0正常 1停用）',
    createBy   varchar(64)  default ''  null comment '创建者',
    createTime datetime                 null comment '创建时间',
    updateBy   varchar(64)  default ''  null comment '更新者',
    updateTime datetime                 null comment '更新时间',
    remark     varchar(500)             null comment '备注'
)
    comment '字典类型表';

create table if not exists interface_info
(
    id             bigint auto_increment comment '主键'
        primary key,
    name           varchar(256)                       not null comment '名称',
    description    varchar(256)                       null comment '描述',
    url            varchar(512)                       not null comment '接口地址',
    requestHeader  text                               null comment '请求头',
    responseHeader text                               null comment '响应头',
    status         int      default 0                 not null comment '接口状态（0-关闭，1-开启）',
    method         varchar(256)                       not null comment '请求类型',
    userId         bigint                             not null comment '创建人',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted        tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)',
    invokeTotal    bigint                             null comment '接口总调用次数',
    requestParam   varchar(1024)                      null comment '请求参数',
    responseParam  varchar(1024)                      null comment '返回参数'
)
    comment '接口信息';

create table if not exists invoke_record
(
    id             varchar(512)              not null comment 'invokeId'
        primary key,
    parentId       varchar(512)  default '0' null comment 'parentId',
    requestUrl     varchar(512)              null comment '请求路径',
    requestMethod  varchar(20)               null comment '请求方法',
    requestParam   varchar(2048) default ''  null comment '请求参数',
    requestHeader  varchar(2048) default ''  null comment '请求头',
    requestBody    varchar(2048) default ''  null comment '请求体',
    responseHeader varchar(2048) default ''  null comment '响应头',
    responseBody   varchar(2048) default ''  null comment '响应体',
    createTime     datetime                  null comment '创建时间',
    updateBy       varchar(64)   default ''  null comment '更新者',
    updateTime     datetime                  null comment '更新时间',
    remark         varchar(500)              null comment '备注',
    title          varchar(255)  default ''  null comment '标题',
    type           char          default '0' null comment '菜单类型(0 目录 1 文件)',
    userId         varchar(512)              not null comment '用户ID',
    createBy       varchar(64)               null comment '创建人'
)
    comment '接口调用记录表（仿postman）';

create table if not exists qrtz_calendars
(
    SCHED_NAME    varchar(120) not null,
    CALENDAR_NAME varchar(190) not null,
    CALENDAR      blob         not null,
    primary key (SCHED_NAME, CALENDAR_NAME)
);

create table if not exists qrtz_fired_triggers
(
    SCHED_NAME        varchar(120) not null,
    ENTRY_ID          varchar(95)  not null,
    TRIGGER_NAME      varchar(190) not null,
    TRIGGER_GROUP     varchar(190) not null,
    INSTANCE_NAME     varchar(190) not null,
    FIRED_TIME        bigint       not null,
    SCHED_TIME        bigint       not null,
    PRIORITY          int          not null,
    STATE             varchar(16)  not null,
    JOB_NAME          varchar(190) null,
    JOB_GROUP         varchar(190) null,
    IS_NONCONCURRENT  varchar(1)   null,
    REQUESTS_RECOVERY varchar(1)   null,
    primary key (SCHED_NAME, ENTRY_ID)
);

create index IDX_QRTZ_FT_INST_JOB_REQ_RCVRY
    on qrtz_fired_triggers (SCHED_NAME, INSTANCE_NAME, REQUESTS_RECOVERY);

create index IDX_QRTZ_FT_JG
    on qrtz_fired_triggers (SCHED_NAME, JOB_GROUP);

create index IDX_QRTZ_FT_J_G
    on qrtz_fired_triggers (SCHED_NAME, JOB_NAME, JOB_GROUP);

create index IDX_QRTZ_FT_TG
    on qrtz_fired_triggers (SCHED_NAME, TRIGGER_GROUP);

create index IDX_QRTZ_FT_TRIG_INST_NAME
    on qrtz_fired_triggers (SCHED_NAME, INSTANCE_NAME);

create index IDX_QRTZ_FT_T_G
    on qrtz_fired_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

create table if not exists qrtz_job_details
(
    SCHED_NAME        varchar(120) not null,
    JOB_NAME          varchar(190) not null,
    JOB_GROUP         varchar(190) not null,
    DESCRIPTION       varchar(250) null,
    JOB_CLASS_NAME    varchar(250) not null,
    IS_DURABLE        varchar(1)   not null,
    IS_NONCONCURRENT  varchar(1)   not null,
    IS_UPDATE_DATA    varchar(1)   not null,
    REQUESTS_RECOVERY varchar(1)   not null,
    JOB_DATA          blob         null,
    primary key (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

create index IDX_QRTZ_J_GRP
    on qrtz_job_details (SCHED_NAME, JOB_GROUP);

create index IDX_QRTZ_J_REQ_RECOVERY
    on qrtz_job_details (SCHED_NAME, REQUESTS_RECOVERY);

create table if not exists qrtz_locks
(
    SCHED_NAME varchar(120) not null,
    LOCK_NAME  varchar(40)  not null,
    primary key (SCHED_NAME, LOCK_NAME)
);

create table if not exists qrtz_paused_trigger_grps
(
    SCHED_NAME    varchar(120) not null,
    TRIGGER_GROUP varchar(190) not null,
    primary key (SCHED_NAME, TRIGGER_GROUP)
);

create table if not exists qrtz_scheduler_state
(
    SCHED_NAME        varchar(120) not null,
    INSTANCE_NAME     varchar(190) not null,
    LAST_CHECKIN_TIME bigint       not null,
    CHECKIN_INTERVAL  bigint       not null,
    primary key (SCHED_NAME, INSTANCE_NAME)
);

create table if not exists qrtz_triggers
(
    SCHED_NAME     varchar(120) not null,
    TRIGGER_NAME   varchar(190) not null,
    TRIGGER_GROUP  varchar(190) not null,
    JOB_NAME       varchar(190) not null,
    JOB_GROUP      varchar(190) not null,
    DESCRIPTION    varchar(250) null,
    NEXT_FIRE_TIME bigint       null,
    PREV_FIRE_TIME bigint       null,
    PRIORITY       int          null,
    TRIGGER_STATE  varchar(16)  not null,
    TRIGGER_TYPE   varchar(8)   not null,
    START_TIME     bigint       not null,
    END_TIME       bigint       null,
    CALENDAR_NAME  varchar(190) null,
    MISFIRE_INSTR  smallint     null,
    JOB_DATA       blob         null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_triggers_ibfk_1
        foreign key (SCHED_NAME, JOB_NAME, JOB_GROUP) references qrtz_job_details (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

create table if not exists qrtz_blob_triggers
(
    SCHED_NAME    varchar(120) not null,
    TRIGGER_NAME  varchar(190) not null,
    TRIGGER_GROUP varchar(190) not null,
    BLOB_DATA     blob         null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_blob_triggers_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create index SCHED_NAME
    on qrtz_blob_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

create table if not exists qrtz_cron_triggers
(
    SCHED_NAME      varchar(120) not null,
    TRIGGER_NAME    varchar(190) not null,
    TRIGGER_GROUP   varchar(190) not null,
    CRON_EXPRESSION varchar(120) not null,
    TIME_ZONE_ID    varchar(80)  null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_cron_triggers_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table if not exists qrtz_simple_triggers
(
    SCHED_NAME      varchar(120) not null,
    TRIGGER_NAME    varchar(190) not null,
    TRIGGER_GROUP   varchar(190) not null,
    REPEAT_COUNT    bigint       not null,
    REPEAT_INTERVAL bigint       not null,
    TIMES_TRIGGERED bigint       not null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_simple_triggers_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table if not exists qrtz_simprop_triggers
(
    SCHED_NAME    varchar(120)   not null,
    TRIGGER_NAME  varchar(190)   not null,
    TRIGGER_GROUP varchar(190)   not null,
    STR_PROP_1    varchar(512)   null,
    STR_PROP_2    varchar(512)   null,
    STR_PROP_3    varchar(512)   null,
    INT_PROP_1    int            null,
    INT_PROP_2    int            null,
    LONG_PROP_1   bigint         null,
    LONG_PROP_2   bigint         null,
    DEC_PROP_1    decimal(13, 4) null,
    DEC_PROP_2    decimal(13, 4) null,
    BOOL_PROP_1   varchar(1)     null,
    BOOL_PROP_2   varchar(1)     null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_simprop_triggers_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create index IDX_QRTZ_T_C
    on qrtz_triggers (SCHED_NAME, CALENDAR_NAME);

create index IDX_QRTZ_T_G
    on qrtz_triggers (SCHED_NAME, TRIGGER_GROUP);

create index IDX_QRTZ_T_J
    on qrtz_triggers (SCHED_NAME, JOB_NAME, JOB_GROUP);

create index IDX_QRTZ_T_JG
    on qrtz_triggers (SCHED_NAME, JOB_GROUP);

create index IDX_QRTZ_T_NEXT_FIRE_TIME
    on qrtz_triggers (SCHED_NAME, NEXT_FIRE_TIME);

create index IDX_QRTZ_T_NFT_MISFIRE
    on qrtz_triggers (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME);

create index IDX_QRTZ_T_NFT_ST
    on qrtz_triggers (SCHED_NAME, TRIGGER_STATE, NEXT_FIRE_TIME);

create index IDX_QRTZ_T_NFT_ST_MISFIRE
    on qrtz_triggers (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_STATE);

create index IDX_QRTZ_T_NFT_ST_MISFIRE_GRP
    on qrtz_triggers (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_GROUP, TRIGGER_STATE);

create index IDX_QRTZ_T_N_G_STATE
    on qrtz_triggers (SCHED_NAME, TRIGGER_GROUP, TRIGGER_STATE);

create index IDX_QRTZ_T_N_STATE
    on qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_STATE);

create index IDX_QRTZ_T_STATE
    on qrtz_triggers (SCHED_NAME, TRIGGER_STATE);

create table if not exists quotes
(
    id         bigint auto_increment comment 'id'
        primary key,
    content    text                               null comment '内容',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted    tinyint  default 0                 not null comment '是否删除'
)
    comment '毒鸡汤' collate = utf8mb4_unicode_ci;

create index idx_userId
    on quotes (id);

create table if not exists sys_config
(
    id          int auto_increment comment '参数主键'
        primary key,
    configKey   varchar(100) default ''  null comment '参数键名',
    configValue text                     null comment '参数键值',
    configType  char         default 'N' null comment '系统内置（Y是 N否）',
    createBy    varchar(64)  default ''  null comment '创建者',
    createTime  datetime                 null comment '创建时间',
    updateBy    varchar(64)  default ''  null comment '更新者',
    updateTime  datetime                 null comment '更新时间',
    remark      varchar(500)             null comment '备注'
)
    comment '参数配置表';

create table if not exists sys_job
(
    id             bigint auto_increment comment '任务ID'
        primary key,
    jobName        varchar(64)  default ''        not null comment '任务名称',
    jobGroup       varchar(64)  default 'DEFAULT' not null comment '任务分组(DEFAULT,SYSTEM)',
    invokeTarget   varchar(500)                   not null comment '调用方法',
    cronExpression varchar(255) default ''        null comment 'cron执行表达式',
    misfirePolicy  tinyint      default 3         null comment '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
    concurrent     tinyint      default 1         null comment '是否并发执行（0允许 1禁止）',
    status         tinyint      default 0         null comment '状态（0正常 1暂停）',
    createBy       varchar(64)  default ''        null comment '创建人',
    createTime     datetime                       null comment '创建时间',
    updateBy       varchar(64)  default ''        null comment '更新人',
    updateTime     datetime                       null comment '更新时间',
    remark         varchar(500) default ''        null comment '备注信息'
)
    comment '定时任务调度表';

create table if not exists sys_job_log
(
    id           bigint auto_increment comment '任务日志ID'
        primary key,
    jobId        bigint            not null comment '任务ID',
    jobName      varchar(64)       not null comment '任务名称',
    jobGroup     varchar(64)       not null comment '任务组名',
    invokeTarget varchar(500)      not null comment '调用目标字符串',
    job_message  varchar(500)      null comment '日志信息',
    status       tinyint default 0 null comment '执行状态（0正常 1失败）',
    createTime   datetime          null comment '创建时间'
)
    comment '定时任务调度日志表';

create table if not exists todo
(
    id         bigint auto_increment comment 'id'
        primary key,
    taskName   varchar(512)                       not null comment '任务名称',
    isDone     char     default '0'               null comment '0 未完成 1 完成',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted    tinyint  default 0                 not null comment '是否删除'
)
    comment 'todo待做' collate = utf8mb4_unicode_ci;

create table if not exists user
(
    id           varchar(512)                           not null comment 'id'
        primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      tinyint      default 0                 not null comment '是否删除',
    userStatus   tinyint      default 0                 null comment '0 正常、1 BAN',
    accessKey    varchar(512)                           null comment 'accessKey',
    secretKey    varchar(512)                           null comment 'secretKey'
)
    comment '用户' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on user (unionId);

create table if not exists user_interface_info
(
    id              bigint auto_increment comment 'id'
        primary key,
    userId          varchar(512)                       null comment '调用用户 id',
    interfaceInfoId varchar(512)                       null comment '接口 id',
    totalNum        int      default 0                 null comment '总调用次数',
    leftNum         int      default 0                 null comment '剩余调用次数',
    status          int      default 0                 null comment '0-正常， 1-禁用',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted         tinyint  default 0                 not null comment '是否删除'
)
    comment '用户调用接口关系表';

