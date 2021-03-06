
-- 数据库存在则删除
drop database if exists `iwami`;

create database `iwami`;

use iwami;

-- app启动表
create table onstart (
	userid bigint(20) not null default -1 comment "用户id",
	cell_phone bigint(20) not null default -1 comment "手机号",
	uuid varchar(50) not null comment "设备id",
	gps varchar(50) not null comment "用户gps位置",
	alias varchar(50) not null comment "android jpush推送id",
	type tinyint(3) not null default 0 comment "启动类型: 0冷启动,1激活",
	version varchar(10) not null comment "客户端版本号",
	add_time datetime not null comment "客户端上传时间",
	lastmod_time datetime not null comment "服务器添加时间"
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 攻略列表焦点图
create table  strategy_images (
	id bigint(20) not null auto_increment  comment "自增id",
	strategy_id bigint(20) default 0 comment "攻略id",
	rank int(10)  not null comment  "顺序",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "是否删除",
	icon_url varchar(1024) not null  comment "焦点图",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 攻略列表
create table  strategy_list (
	id bigint(20) not null auto_increment comment "自增id",
	name varchar(255) not null comment "标题",
	subname varchar(255) comment "子标题",
	intr varchar(255) comment "简介",
	rank int(10)  not null comment  "顺序",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "是否删除",
	icon_small varchar(1024) not null  comment "Icon小图",
	icon_big varchar(1024) not null  comment "Icon大图",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 浏览，好评
create table  strategy_rate (
	strategy_id bigint(20) not null  comment "攻略id",
	skim int(10) not null default 0 comment "浏览数",
	rate int(10) not null default 0 comment "好评数",
	isdel tinyint(3) not null default 0 comment "是否删除",
	index srid (strategy_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


	

-- 点赞表
create table  rate_info (
	strategy_id bigint(20) not null  comment "攻略id",
	uuid varchar(50) not null comment "设备id",
	lastmod_time datetime not null comment "上次修改时间",
	isdel tinyint(3) not null default 0 comment "是否删除",
	unique index riid (strategy_id,uuid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 攻略表
create table  strategy_info (
	id bigint(20) not null auto_increment comment "自增id",
	strategy_id bigint(20) not null  comment "攻略id",
	rank int(10)  not null comment  "顺序",
	title varchar(255) not null comment "关数",
	content varchar(2048) not null comment "评论内容",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "是否删除",
	url varchar(1024) not null  comment "评论图片",
	primary key(id),
	index siid (strategy_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 话术表
create table  tips (
	id bigint(20) not null auto_increment comment "自增id",
	type tinyint(3) not null comment "话术类型：0挖米广告词,1没有符合要求的任务提示，2没有礼品提示，3分享内容（微博，微信，朋友圈），4短信接收手机号",
	content varchar(255) not null comment "话术内容",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "是否删除",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `tips` (type, content, lastmod_time, lastmod_userid)
VALUES (0,'快来挖米吧！',now(),0),
(4,'18610933194',now(),0),
(2,'4月10日，一大波礼物即将上线，电话卡、Q币卡、小米手机、iPhone手机，敬请期待！',now(),0),
(1,'没有任务了，请稍后再刷新~',now(),0),
(3,'爱挖米真是个神一样的软件，不但下载APP全免费，积分还可以兑换鸡腿、电话卡……甚至金条！100%有奖！猛戳下载：http://www.iwami.cn',now(),0);


-- 米粒任务列表
create table task (
	id bigint(20) not null auto_increment comment "自增id",
	name varchar(255) not null comment "appname",
	rank int(10)  not null comment  "顺序",
	size decimal(6,2) not null default 0  comment "大小,单位为KB保留两位小数",
	intr varchar(255) comment "简介",
	appintr varchar(255) comment "app简介",
	packagename varchar(255) comment "app包名称",
	prize int(10) default 0 not null  comment "米粒数",
	type int(10) default 0 not null comment "任务类型，用二进制表示，从低位开始，第一位代表是否普通任务，第二位代表是否宝箱任务,第三位代表是否分享任务，第四位代表是否金榜任务，第五位代表是否线下任务",
	background tinyint(3) not null default 0 comment "是否后台运行: 0不需要,1需要",
	time int(10) default 0 not null  comment "运行时间,单位秒",
	register tinyint(3) not null default 0 comment "是否需要注册: 0不需要,1需要",
	reputation int(10) default 0 not null comment "好评数",
	star   int(10) default 0 not null comment "星级数",
	start_time datetime not null comment "开始时间",
	end_time datetime comment "结束时间",
	current_prize int(10) not null default 0 comment "当前已抢米粒数",
	max_prize int(10) not null default -1 comment "最大可抢米粒数",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除，2是仅后端显示",
	icon_gray  varchar(1024)  comment "灰色图片",
	icon_small varchar(1024)  comment "Icon小图",
	icon_big varchar(1024)  comment "Icon大图",
	url varchar(1024) comment "APK url",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `task` (name, rank, intr, appintr, prize, type, start_time, lastmod_time, lastmod_userid, icon_small, icon_big)
VALUES ('微博分享',1,'测试微博分享','微博分享',31,4,now(),now(),0,'http://yy.iwami.cn/file/1395069977225.aaa.png','http://yy.iwami.cn/file/1395069977225.aaa.png'),
('微信分享',2,'微信分享','微信分享',35,4,now(),now(),0,'http://yy.iwami.cn/file/1395069977225.aaa.png','http://yy.iwami.cn/file/1395069977225.aaa.png'),
('朋友圈',0,'朋友圈','朋友圈',34,4,now(),now(),0,'http://yy.iwami.cn/file/1395069977225.aaa.png','http://yy.iwami.cn/file/1395069977225.aaa.png');

-- 挖米表
create table wami (
	id bigint(20) not null auto_increment comment "自增id",
	userid bigint(20) not null comment "用户id",
    task_id bigint(20) not null  comment "米粒任务id",
    type tinyint(3) not null comment "挖米类型: -1 未开始/可抢 0 抢米任务开始，1下载开始，2下载完成，3安装完成，4启动运行，5关闭/任务完成，6开始运行，7结束运行",
    prize int(10) default 0 not null  comment "米粒数",
    channel varchar(50) comment "渠道",
    add_time datetime not null comment "客户端上传时间",
	lastmod_time datetime not null comment "服务器添加时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除，2是仅后端显示",
	primary key(id),
	index wkey1 (userid),
	index wkey2 (task_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 礼品列表
create table present (
	id bigint(20) not null auto_increment comment "自增id",
	name varchar(255) not null comment "礼品名字",
	prize int(10) not null default 0 comment "所需米粒数",
	count int(10) not null default -1 comment "最低提现米粒",
	rank int(10)  not null comment  "顺序",
	type tinyint(3) not null comment "类型：0线上快递，1线上手机充值卡，2线上支付宝，3线上银行卡，4线下, 5抽奖",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
  channel varchar(50) comment "渠道",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除，2是仅后端显示",
	icon_small varchar(1024) not null  comment "Icon小图",
	icon_big varchar(1024)  comment "Icon大图",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `present` (name, prize, count, rank, type, lastmod_time, lastmod_userid, icon_small)
VALUES ('支付宝提现',10,10,0,2,now(),0,'http://pic.iwami.cn/lipin/zhifubao.png'),
('银行卡提现',5,10,0,3,now(),0,'http://pic.iwami.cn/lipin/bankcard.jpg'),
('抽奖',1,-1,0,5,now(),0,'http://yy.iwami.cn/file/1395069977225.aaa.png');


-- 礼品兑换表
create table exchange (
	id bigint(20) not null auto_increment comment "自增id",
	userid bigint(20) not null comment "用户id",
	presentid bigint(20) not null comment "礼品id，当为赠送时为被赠送方用户id",
	present_name  varchar(255) not null comment "被赠送礼品的用户名字",
	present_prize  int(10) not null comment "单个礼品所需米粒数,赠送米粒时数量为-1",
	present_type tinyint(3) not null comment "类型：0线上快递，1线上手机充值卡，2线上支付宝，3线上银行卡，4线下, 5抽奖,6赠送",
	count int(10) not null comment "礼品数量",
	prize int(10) not null comment "全部米粒数目",
	status tinyint(3) not null default 0 comment "状态：0新建，1余额不足，2扣除米粒成功，3兑换成功",
	cell_phone  bigint(20)  comment "手机号,当present_type=1或0时，必须有值",
	alipay_account varchar(255) comment "支付宝账号,当present_type=2时，必须有值",
	bank_name varchar(255) comment "银行卡开户行,当present_type=3时，必须有值",
	bank_account varchar(255) comment "银行卡开户姓名,当present_type=3时，必须有值",
	bank_no  bigint(20) comment "银行账号,当present_type=3时，必须有值",
	address varchar(255) comment "线上快递地址，当present_type=0时，必须有值",
	name  varchar(255) comment "线上快递名字，当present_type=0时，必须有值",
	express_name varchar(50) comment "线上快递名称",
	express_no varchar(50) comment "线上快递单号",
  channel varchar(50) comment "渠道",
	add_time datetime not null comment "添加时间",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除，2是仅后端显示",
	primary key(id),
	index ekey1 (userid),
	index ekey2 (presentid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户表
create table user (
	id bigint(20) not null auto_increment comment "自增id",
	current_prize int(10) not null comment "用户拥有米粒数目",
	exchange_prize int(10) not null comment "用户已经兑换米粒数目",
	last_cell_phone_1  bigint(20)  comment "上次充值手机号",
	last_alipay_account varchar(255) comment "上次支付宝账号",
	last_bank_name varchar(255) comment "上次银行卡开户行",
	last_bank_account varchar(255) comment "上次银行卡开户姓名",
	last_bank_no  bigint(20) comment "上次银行账号",
	last_address varchar(255) comment "上次线上快递地址",
	last_cell_phone_2  bigint(20)  comment "上次线上快递手机号",
	last_name  varchar(255) comment "上次线上快递名字，当present_type=0时，必须有值",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除，2是仅后端显示",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into user(id, current_prize, exchange_prize, lastmod_time, lastmod_userid, isdel)
values(1, 0, 0, now(), 0, 3);

-- 用户信息表
create table user_info (
	userid bigint(20) not null comment "用户id",
	name varchar(255) not null comment "用户名",
	uuid varchar(50) not null comment "设备id",
	alias varchar(50) not null comment "android jpush推送id",
	cell_phone bigint(20) not null comment "手机号",
	age tinyint(3) default 0 comment "年龄",
	gender tinyint(3) default 0 comment "性别：0帅哥，1美女",
	job varchar(30) comment "职业",
	address varchar(255) comment "地址",
	add_time datetime not null comment "注册时间",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除，2是仅后端显示，3生效管理员，4无效管理员",
	primary key(userid),
	index uiid (cell_phone)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into user_info(userid, name, uuid, alias, cell_phone, add_time, lastmod_time, lastmod_userid, isdel)
values(1, 'admin', 'admin', 'admin', 0, now(), now(), 0, 3);

-- 用户信息表
create table user_role (
	userid bigint(20) not null comment "用户id",
	name varchar(255) not null comment "登陆名",
	password varchar(255) not null comment "密码",
	role bigint(20) not null comment "权限,第一位：用户管理，第二位：兑换-银行卡提现，第三位：兑换-支付宝提现，第四位：兑换-手机充值卡，第五位：兑换-线上礼品，第六位：兑换-线下礼品，第七位：兑换-抽奖，第8位：其他所有权限",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0，1",
	primary key(userid),
	unique index urname(name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into user_role(userid, name, password, role, lastmod_time, lastmod_userid)
values(1, 'admin', 'admin', 268435455, now(), 0);

-- 验证码表
create table code (
	userid bigint(20) not null default 0 comment "用户id",
	cell_phone bigint(20) not null comment "手机号",
	code varchar(30) not null comment "验证码",
	add_time datetime not null comment "验证码生成时间",
	index uid (userid),
	index cpid (cell_phone)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 联系信息表
create table contact (
	id int(10) not null auto_increment comment "自增id",
	phone1 varchar(20) not null comment "客服电话",
	email1 varchar(30) not null  comment "服务邮箱",
	domain varchar(30) not null  comment "官网地址",
	qq bigint(20) not null  comment "客服qq",
	qqgroup varchar(255) comment "qq群",
	phone2 varchar(20) not null comment "业务电话",
	email2 varchar(30) not null  comment "业务邮箱",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除，2是仅后端显示",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `contact` (phone1, email1, domain, qq, qqgroup, phone2, email2, lastmod_time, lastmod_userid)
VALUES ('010-62675888','service@iwami.cn','www.iwami.cn',1865545221,'338866688(可加)\r\n102613421(可加)\r\n88490652  (已满)\r\n316211268(已满)\r\n25465656（可加）','18610261342','hezuo@iwami.cn',now(),0);


-- 抽奖表
create table luck_rule (
	id int(10) not null auto_increment comment "自增id",
	index_lev tinyint(3) not null comment "几等奖",
	gift  varchar(50) not null comment "抽奖内容",
	prob int(10) not null comment "抽奖概率",
	count int(10) not null comment "每天最大数量",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除，2是仅后端显示",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `luck_rule`(index_lev,gift,prob,`count`,lastmod_time,lastmod_userid) 
VALUES (1,'一等奖',1,1,now(),0),
(2,'二等奖',50,5,now(),0),
(3,'三等奖',500,20,now(),0),
(4,'四等奖',10000,1000,now(),0);

-- apk 下载地址
create table apk(
	id bigint(20) not null auto_increment comment "自增id",
	version varchar(10) not null comment "版本号",
	url varchar(255) not null comment "下载地址",
	`force` tinyint(3) default 0 not null comment "是否强制升级:0否，1是",
	`desc` varchar(255) comment "升级特性",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `apk`(version, url, `force`, `desc`, lastmod_time, lastmod_userid) 
VALUES ('1.0.0','http://115.28.17.121/iwami.apk',0,'爱挖米APK',now(),0);

-- 兑换分享记录表
create table share (
	id bigint(20) not null auto_increment comment "自增id",
	userid bigint(20) not null comment "用户id",
	type tinyint(3) not null comment "0-受赠、1-线下兑换、2-电话卡、3-支付宝、4-银行卡、5-抽奖、6-线上实物兑换、7-关于页面",
	target tinyint(3) not null comment "0：微信好友/1：微信朋友圈/2：新浪微博",
	msg varchar(1024) not null comment "分享内容",
	lastmod_time datetime not null comment "上次修改时间",
	index lhid (userid),
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 宝箱出现配置表
create table treasure_config (
	id bigint(20) not null auto_increment comment "自增id",
	days tinyint(3) not null comment "N天",
	count tinyint(3) not null comment "连续M次",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `treasure_config` (days, count, lastmod_time, lastmod_userid)
VALUES (1,9,now(),0);


-- task finish sms notification
create table task_notification(
	id int(10) not null auto_increment comment "自增id",
	task_id bigint(20) not null  comment "米粒任务id",
	cell_phone bigint(20) not null default -1 comment "手机号",
	sms varchar(255) comment "短信内容",
	status tinyint(3) not null default 0 comment "状态：0新建，1已发短信", 
	add_time datetime not null comment "插入时间",
	lastmod_time datetime not null comment "上次修改时间",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--push
create table push(
	id bigint(20) not null auto_increment comment "自增id",
	`interval` bigint(20) not null default 1000 comment "间隔时间，以毫秒为单位",
	msg varchar(1024) comment "短信内容",
	status tinyint(3) not null default 0 comment "状态：0新建，1暂停，2停止，3恢复，4推送成功，5短信发送成功", 
	cell_phone varchar(255) comment "手机号",
	add_time datetime not null comment "推送开始时间",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table push_task(
	id bigint(20) not null auto_increment comment "自增id",
	push_id bigint(20) not null comment "push id",
	userid bigint(20) not null comment "用户id",
	alias varchar(50) not null comment "android jpush推送id",
	status tinyint(3) not null default 0 comment "状态：0新建，1成功，2失败", 
	add_time datetime not null comment "推送开始时间",
	lastmod_time datetime comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	index ptid (push_id),
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- login
create table admin_login(
	userid bigint(20) not null comment "用户id",
	add_time datetime not null comment "登陆时间",
	index aluid (userid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- report
create table request_log(
	userid bigint(20) not null comment "用户id",
	type tinyint(3) not null comment "请求类型：1攻略列表，2攻略详情，3榜单列表，4任务列表，5红包列表，6APP下载，7登录",
	msg varchar(255) null comment "url parameter",
	add_time datetime not null comment "时间",
	index rctime(add_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;