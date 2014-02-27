
-- 数据库存在则删除
drop database if exists `iwami`;

create database `iwami`;

use iwami;

-- app启动表
create table  onstart (
	userid bigint(20) not null default -1 comment "用户id",
	cell_phone bigint(20) not null default -1 comment "手机号",
	uuid varchar(50) not null comment "设备id",
	gps varchar(50) not null comment "用户gps位置",
	alias varchar(50) not null comment "android jpush推送id",
	type tinyint(3) not null default 0 comment "启动类型: 0手工启动,1新抢米任务推送,2任务完成，3本周小结，4本月小结，5运营消息，6赠送米粒",
	version varchar(10) not null comment "客户端版本号",
	add_time datetime not null comment "客户端上传时间",
	lastmod_time datetime not null comment "服务器添加时间"
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 攻略列表焦点图
create table  strategy_images (
	id bigint(20) not null auto_increment  comment "自增id",
	rank int(10)  not null comment  "顺序",
	icon_url varchar(255) not null  comment "焦点图",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "是否删除",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 攻略列表
create table  strategy_list (
	id bigint(20) not null auto_increment comment "自增id",
	name varchar(255) not null comment "标题",
	subname varchar(255) not null comment "子标题",
	intr varchar(255) comment "简介",
	rank int(10)  not null comment  "顺序",
	icon_small varchar(255) not null  comment "Icon小图",
	icon_big varchar(255) not null  comment "Icon大图",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "是否删除",
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
	url varchar(255) not null  comment "评论图片",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "是否删除",
	primary key(id),
	index siid (strategy_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 话术表
create table  tips (
	id bigint(20) not null auto_increment comment "自增id",
	type tinyint(3) not null comment "话术类型：0挖米广告词,1任务抢光提示",
	content varchar(255) not null comment "话术内容",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "是否删除",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 米粒任务列表
create table task (
	id bigint(20) not null auto_increment comment "自增id",
	name varchar(255) not null comment "appname",
	rank int(10)  not null comment  "顺序",
	size decimal(6,2) not null default 0  comment "大小,单位为KB保留两位小数",
	intr varchar(255) comment "简介",
	appintr varchar(255) comment "app简介",
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
	icon_gray  varchar(255)  comment "灰色图片",
	icon_small varchar(255)  comment "Icon小图",
	icon_big varchar(255)  comment "Icon大图",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除，2是仅后端显示",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 挖米表
create table wami (
	id bigint(20) not null auto_increment comment "自增id",
	userid bigint(20) not null comment "用户id",
    task_id bigint(20) not null  comment "米粒任务id",
    type tinyint(3) not null comment "挖米类型: -1 未开始/可抢 0 抢米任务开始，1下载开始，2下载完成，3安装完成，4启动运行，5关闭/任务完成",
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
	icon_small varchar(255) not null  comment "Icon小图",
	icon_big varchar(255)  comment "Icon大图",
	lastmod_time datetime not null comment "上次修改时间",
	lastmod_userid bigint(20) not null comment "上次修改人",
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除，2是仅后端显示",
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



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
	isdel tinyint(3) not null default 0 comment "0是前端展示,1是删除，2是仅后端显示",
	primary key(userid),
	index uiid (cell_phone)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;




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

-- 兑换分享记录表
create table share (
	id bigint(20) not null auto_increment comment "自增id",
	userid bigint(20) not null comment "用户id",
	type tinyint(3) not null comment "0：实物兑换/1：银行卡提现/2：支付宝提现/3：手机充值/4：抽奖/5：低调炫耀",
	target tinyint(3) not null comment "0：微信好友/1：微信朋友圈/2：新浪微博",
	msg varchar(255) not null comment "分享内容",
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

-- 为了执行完成全部sql语句