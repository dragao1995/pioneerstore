package com.pioneer.app.comm;

public class Const {

	// cache的对象.
	// user
	public static String SA_USERID = "userId";

	public static String SA_USERNAME = "userName";

	public static String SA_USERVO = "userVo";

	// 普通用户权限
	public static String SA_purview_normal = "1";

	// 管理员用户权限
	public static String SA_purview_admin = "5";

	public static String SA_super_admin = "8";

	// public static String CACHE_GROUP_USER="cache_group_user";
	public static String CACHE_EXPERTS = "cache_expert";

	// type
	// public static String CACHE_GROUP_TYPE="cache_group_type";
	public static String CACHE_AllType = "all_type";

	public static String CACHE_AllType_expert = "all_type_expert";

	public static String CACHE_PARS_MAP = "pars_map";

	// 基础信息模块用到的常量 后面的字符串值和数据库对应
	public static String AUTO_GRADE_TIME = "autoGradeTime";

	public static String ANSWER_GRADE = "answerGrade";

	public static String QUESTION_GRADE = "questionGrade";

	public static String INITIALIZE_GRADE = "initGrade";

	public static String DEDUCT_GRADE = "deductGrade";

	// public static String HIGHCLASS_GRADE="highClassGrade";

	//	
	// question status
	// 新问题状态(未回答的问题).
	public static String QUESTION_NO_ANSWER = "0";

	// 已回答问题
	public static String QUESTION_ANSWER = "1";

	// 删除问题
	public static String QUESTION_DELETED = "2";

	// 锁定问题(已评分的问题)
	public static String QUESTION_LOCKED = "3";

	// 推荐问题标志
	public static String QUESTION_STATUS_RECOMMEND = "1";

	// 非推荐问题标志
	public static String QUESTION_STATUS_NO_RECOMMEND = "0";

	// session中保存的对象.
	public static String SESSION_USER_ID = "user_id";

	/**
	 * 获取指派或升级为专家的查询串
	 */
	public static final String EXPERTS_SPECIFIED = "STATUS='1'";

	public static final String EXPERTS_UPGRADED = "STATUS='0'";

	public static final String AND_COLUMN_ID = " and TYPE_ID=";

	/**
	 * 被指派的标识(专家的类型区别)
	 */
	// 管理员指定专家标志
	public static final String TYPE_SPECIFIED = "1";

	// 系统自动升级专家标志
	public static final String TYPE_UPGRADED = "0";

	// 推荐栏目标志
	public static String TYPE_STATUS_RECOMMEND = "1";

	// 非推荐栏目标志
	public static String TYPE_STATUS_NO_RECOMMEND = "0";

	/**
	 * <desc>描述:信息类型标示</desc>
	 */
	// 用户反馈信息
	public static final String MESSAGE_FEEDBAK = "0";

	// 系统公告信息
	public static final String MESSAGE_AFFICHE = "1";

	// 系统公告的活动信息
	public static final String MESSAGE_ACTIVITY = "2";

	// 近期信息状态
	public static final String MESSAGE_STATUS_NEAR = "0";

	// 过期信息状态
	public static final String MESSAGE_STATUS_STALE = "1";

	public static final String ORDER_BY_USERNAME_ASC = " order by user_name asc";

	/**
	 * <desc>描述:系统异常日志</desc>
	 */
	public static final String SYSTEM_LOG_EXCEPTION = "exception";

	/**
	 * <desc>描述:用户操作日志</desc>
	 */
	public static final String SYSTEM_LOG_ACTION = "action";

	/**
	 * 
	 */
	public static final String QUS_BY_COLUMN_ID = "TYPE_ID=";

	public static final String QUS_BY_SERIAL_ID = "ID=";

	public static final String AND = " and ";

	public static final String[] QUESTION_STATUS = new String[] { "未回答", "已回答",
			"已删除", "已评分" };

	public static final String CURENT_CACULATOR_CLASS = "NormalCalculateLevel";
}
