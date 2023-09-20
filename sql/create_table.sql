CREATE TABLE `dict_type`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `dictName`   varchar(100) DEFAULT '' COMMENT '字典名称',
    `dictType`   varchar(100) DEFAULT '' COMMENT '字典类型',
    `status`     char(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `createBy`   varchar(64)  DEFAULT '' COMMENT '创建者',
    `createTime` datetime     DEFAULT NULL COMMENT '创建时间',
    `updateBy`   varchar(64)  DEFAULT '' COMMENT '更新者',
    `updateTime` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`     varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) COMMENT ='字典类型表';

CREATE TABLE `dict_data`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    `dictLabel`   varchar(100) DEFAULT '' COMMENT '字典标签',
    `dictValue`   varchar(100) DEFAULT '' COMMENT '字典键值',
    `dictType`    varchar(100) DEFAULT '' COMMENT '字典类型',
    `defaultFlag` char(1)      DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
    `status`      char(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `dictSort`    int(4)       DEFAULT 0 COMMENT '字典排序',
    `createBy`    varchar(64)  DEFAULT '' COMMENT '创建者',
    `createTime`  datetime     DEFAULT NULL COMMENT '创建时间',
    `updateBy`    varchar(64)  DEFAULT '' COMMENT '更新者',
    `updateTime`  datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) COMMENT ='字典数据表';


-- 接口信息
create table if not exists `interface_info`
(
    `id`             bigint                             not null auto_increment comment '主键' primary key,
    `name`           varchar(256)                       not null comment '名称',
    `description`    varchar(256)                       null comment '描述',
    `url`            varchar(512)                       not null comment '接口地址',
    `requestHeader`  text                               null comment '请求头',
    `responseHeader` text                               null comment '响应头',
    `status`         int      default 0                 not null comment '接口状态（0-关闭，1-开启）',
    `method`         varchar(256)                       not null comment '请求类型',
    `userId`         bigint                             not null comment '创建人',
    `createTime`     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime`     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete`       tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息';

insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('许擎宇', '薛聪健', 'www.cary-king.net', '潘博涛', '谭聪健', 0, '石炫明', 9500534531);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('陆弘文', '白志强', 'www.leslee-kuhn.net', '潘懿轩', '马鸿涛', 0, '陈峻熙', 3982575846);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('毛建辉', '罗文', 'www.rosaria-kilback.io', '冯子默', '彭哲瀚', 0, '赵远航', 121776355);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('彭雨泽', '蔡煜祺', 'www.norris-bergstrom.biz', '董思源', '田晓博', 0, '潘擎宇', 740);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('傅志强', '陈梓晨', 'www.jordan-reinger.com', '金志强', '熊锦程', 0, '邓睿渊', 35542559);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('吕黎昕', '孔越彬', 'www.fe-okon.info', '万伟宸', '林昊然', 0, '孟荣轩', 1445);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('夏雪松', '许子骞', 'www.lashawna-legros.co', '蔡昊然', '胡鹏涛', 0, '钟立辉', 34075514);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('严钰轩', '阎志泽', 'www.kay-funk.biz', '莫皓轩', '郭黎昕', 0, '龚天宇', 70956);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('萧嘉懿', '曹熠彤', 'www.margarette-lindgren.biz', '田泽洋', '邓睿渊', 0, '梁志强', 98);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('杜驰', '冯思源', 'www.vashti-auer.org', '黎健柏', '武博文', 0, '李伟宸', 9);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('史金鑫', '蔡鹏涛', 'www.diann-keebler.org', '徐烨霖', '阎建辉', 0, '李烨伟', 125);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('林炫明', '贾旭尧', 'www.dotty-kuvalis.io', '梁雨泽', '龙伟泽', 0, '许智渊', 79998);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('何钰轩', '赖智宸', 'www.andy-adams.net', '崔思淼', '白鸿煊', 0, '邵振家', 7167482751);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('魏志强', '于立诚', 'www.ione-aufderhar.biz', '朱懿轩', '万智渊', 0, '唐昊强', 741098);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('严君浩', '金胤祥', 'www.duane-boyle.org', '雷昊焱', '侯思聪', 0, '郝思', 580514);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('姚皓轩', '金鹏', 'www.lyda-klein.biz', '杜昊强', '邵志泽', 0, '冯鸿涛', 6546);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('廖驰', '沈泽洋', 'www.consuelo-sipes.info', '彭昊然', '邓耀杰', 0, '周彬', 7761037);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('赖智渊', '邓志泽', 'www.emerson-mann.co', '熊明哲', '贺哲瀚', 0, '田鹏', 381422);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('许涛', '陆致远', 'www.vella-ankunding.name', '贾哲瀚', '莫昊焱', 0, '袁越彬', 4218096);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`,
                              `userId`)
values ('吕峻熙', '沈鹏飞', 'www.shari-reichel.org', '郭鸿煊', '覃烨霖', 0, '熊黎昕', 493);
