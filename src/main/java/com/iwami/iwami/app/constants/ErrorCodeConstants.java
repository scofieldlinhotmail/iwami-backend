package com.iwami.iwami.app.constants;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodeConstants {
	
	public static final String STATUS_KEY = "status";
	public static final String MSG_KEY = "msg";

	public static final int STATUS_OK = 0;
	public static final int STATUS_PARAM_ERROR = 300;
	public static final int STATUS_ERROR = 400;
	
	public static final int STATUS_ERROR_STATUSUPLOAD_TASKID = 200041;	//TODO wami上传接口
	public static final int STATUS_ERROR_STATUSUPLOAD_TYPE = 200042;
	public static final int STATUS_ERROR_STATUSUPLOAD_USERID = 200043;
	public static final int STATUS_ERROR_STATUSUPLOAD_TIME = 200044;
	
	public static final int STATUS_ERROR_ONSTART_USERID = 100041;
	public static final int STATUS_ERROR_ONSTART_CELLPHONE = 100042;
	public static final int STATUS_ERROR_ONSTART_UUID = 100043;
	public static final int STATUS_ERROR_ONSTART_TYPE = 100044;
	public static final int STATUS_ERROR_ONSTART_TIME = 100045;
	
	public static final int STATUS_ERROR_STRATEGY_DETAIL_ID = 100061;
	public static final int STATUS_ERROR_STRATEGY_DETAIL_START = 100062;
	public static final int STATUS_ERROR_STRATEGY_DETAIL_STEP = 100063;
	public static final int STATUS_ERROR_STRATEGY_DETAIL_START1 = 100064;
	
	public static final int STATUS_ERROR_STRATEGY_RATE_ID = 100071;
	public static final int STATUS_ERROR_STRATEGY_RATE_UUID = 100072;
	public static final int STATUS_ERROR_STRATEGY_RATE_REPEAT = 100073;
	
	public static final int STATUS_ERROR_LUCKY_DRAW_USERID = 100191;
	public static final int STATUS_ERROR_LUCKY_DRAW_PRIZE = 100192;
	public static final int STATUS_ERROR_LUCKY_DRAW_COUNT = 100193;
	
	public static final int STATUS_ERROR_SEND_VERIFY_CODE_CELLPHONE = 100201;
	public static final int STATUS_ERROR_SEND_VERIFY_CODE_EXCEED_LIMIT = 100202;
	
	public static final int STATUS_ERROR_REGISTER_CELLPHONE = 100211;
	public static final int STATUS_ERROR_REGISTER_NAME = 100212;
	public static final int STATUS_ERROR_REGISTER_CODE = 100213;
	public static final int STATUS_ERROR_REGISTER_UUID = 100214;
	
	public static final int STATUS_ERROR_MODIFY_USERINFO_USERID = 100221;
	public static final int STATUS_ERROR_MODIFY_USERINFO_AGE = 100222;
	public static final int STATUS_ERROR_MODIFY_USERINFO_GENDER = 100223;
	public static final int STATUS_ERROR_MODIFY_USERINFO_JOB = 100224;
	public static final int STATUS_ERROR_MODIFY_USERINFO_ADDRESS = 100225;
	
	public static final int STATUS_ERROR_USERINFO_USERID = 100231;
	
	public static final int STATUS_ERROR_STRATEGY_NOT_EXISTS = 100061;
	public static final int STATUS_ERROR_STRATEGY_START = 100062;
	public static final int STATUS_ERROR_STRATEGY_STEP = 100063;
	
	public static final int STATUS_ERROR_RATE_STRATEGY_NOT_EXISTS = 100071;
	public static final int STATUS_ERROR_RATE_STRATEGY_UUID = 100072;
	public static final int STATUS_ERROR_RATE_STRATEGY_DUPLICATE = 100073;

	public static final int STATUS_WAMI_TASKID = 100041;
	public static final int STATUS_WAMI_TYPE = 100042;
	public static final int STATUS_WAMI_USERID = 100043;
	public static final int STATUS_WAMI_TIME = 100044;
	public static final int STATUS_TASK_WAMIED = 100045;
	public static final int STATUS_TASK_UNAVAILABLE = 100046;
	public static final int STATUS_TASK_FINISHED = 100047;
	public static final int STATUS_TASK_NOT_EXISTS = 100048;
	public static final int STATUS_WAMI_REPEAT_START = 100049;
	
	public static Map<Integer, String> ERROR_MSG_MAP = new HashMap<Integer, String>();
	
	static{
		ERROR_MSG_MAP.put(STATUS_ERROR_ONSTART_USERID, "用户id不合法");
		ERROR_MSG_MAP.put(STATUS_ERROR_ONSTART_CELLPHONE, "手机号不合法");
		ERROR_MSG_MAP.put(STATUS_ERROR_ONSTART_UUID, "设备号为不合法");
		ERROR_MSG_MAP.put(STATUS_ERROR_ONSTART_TYPE, "启动类型不合法");
		ERROR_MSG_MAP.put(STATUS_ERROR_ONSTART_TIME, "客户端时间不合法");

		ERROR_MSG_MAP.put(STATUS_ERROR_LUCKY_DRAW_USERID, "用户id不合法");
		ERROR_MSG_MAP.put(STATUS_ERROR_LUCKY_DRAW_PRIZE, "米粒数不足");
		ERROR_MSG_MAP.put(STATUS_ERROR_LUCKY_DRAW_COUNT, "超过可抽奖最大次数");

		ERROR_MSG_MAP.put(STATUS_ERROR_USERINFO_USERID, "用户id不合法");

		ERROR_MSG_MAP.put(STATUS_ERROR_SEND_VERIFY_CODE_CELLPHONE, "手机号不合法");
		ERROR_MSG_MAP.put(STATUS_ERROR_SEND_VERIFY_CODE_EXCEED_LIMIT, "验证码发送过于频繁");

		ERROR_MSG_MAP.put(STATUS_ERROR_REGISTER_CELLPHONE, "手机号不合法");
		ERROR_MSG_MAP.put(STATUS_ERROR_REGISTER_NAME, "姓名不能为空");
		ERROR_MSG_MAP.put(STATUS_ERROR_REGISTER_CODE, "验证码不正确");
		ERROR_MSG_MAP.put(STATUS_ERROR_REGISTER_UUID, "设备号不能为空");

		ERROR_MSG_MAP.put(STATUS_ERROR_MODIFY_USERINFO_USERID, "用户id不合法");
		ERROR_MSG_MAP.put(STATUS_ERROR_MODIFY_USERINFO_AGE, "年龄不合法");
		ERROR_MSG_MAP.put(STATUS_ERROR_MODIFY_USERINFO_GENDER, "性别不合法");
		ERROR_MSG_MAP.put(STATUS_ERROR_MODIFY_USERINFO_JOB, "职业不能为空");
		ERROR_MSG_MAP.put(STATUS_ERROR_MODIFY_USERINFO_ADDRESS, "地址不能为空");

		ERROR_MSG_MAP.put(STATUS_ERROR_STRATEGY_NOT_EXISTS, "攻略不存在");
		ERROR_MSG_MAP.put(STATUS_ERROR_STRATEGY_START, "start大于等于0");
		ERROR_MSG_MAP.put(STATUS_ERROR_STRATEGY_STEP, "step必须大于0");

		ERROR_MSG_MAP.put(STATUS_ERROR_RATE_STRATEGY_NOT_EXISTS, "攻略不存在");
		ERROR_MSG_MAP.put(STATUS_ERROR_RATE_STRATEGY_UUID, "uuid不能为空");
		ERROR_MSG_MAP.put(STATUS_ERROR_RATE_STRATEGY_DUPLICATE, "重复点赞");

		ERROR_MSG_MAP.put(STATUS_WAMI_USERID, "userid不存在");
		ERROR_MSG_MAP.put(STATUS_WAMI_TASKID, "taskid不存在");
		ERROR_MSG_MAP.put(STATUS_WAMI_TYPE, "type不对");
		ERROR_MSG_MAP.put(STATUS_WAMI_TIME, "上传时间不能为空");
		ERROR_MSG_MAP.put(STATUS_TASK_WAMIED, "已经挖过这个任务");
		ERROR_MSG_MAP.put(STATUS_TASK_UNAVAILABLE, "任务已经挖光");
		ERROR_MSG_MAP.put(STATUS_TASK_FINISHED, "任务已经结束");
		ERROR_MSG_MAP.put(STATUS_TASK_NOT_EXISTS, "任务不存在");
		ERROR_MSG_MAP.put(STATUS_WAMI_REPEAT_START, "不能重复挖这个任务");
	}
}
