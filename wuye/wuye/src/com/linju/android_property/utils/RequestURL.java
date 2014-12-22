package com.linju.android_property.utils;

public class RequestURL {

	public static final String mDomain = "http://staging.dazhongcun.com"; /* staging */
	public static final String mDomaintest = "http://www.dazhongcun.com"; /* staging */

	/**
	 * 登陆(POST) 必要参数:{loginname(用户名), password(密码)}
	 * 
	 */
	public static final String LOGIN_USER = mDomain + "/v1/property/login";
	/**
	 * 权限获取（get） （当user的klass为property时不发送请求） 需要参数:{ user_id(用户id)}
	 */
	public static final String GET_ROLE = mDomain
			+ "/v1/property/get_user_role";
	/**
	 * 获取部门、职位信息(get) **需要参数:**{subdistrict_address_id(小区ID),type(1为部门，2为职位)，
	 * property_department_id（部门ID，type为2时必须）} 成功返回值:** {response: "success",
	 * result{id，name}} 失败返回值:** {response: "success", result{error}}
	 */
	public static final String GET_POSITION = mDomain
			+ "/v1/property/get_departments_and_position";
	/**
	 * **需要参数:**subdistrict_address_id
	 * 可选参数:**offset，limit，key_word(标题搜索)，status（0:未处理 1:处理中 2:已完成 3:已取消）
	 * 成功返回值:** {response:
	 * "success“，result：{id，number，title，content，house_number，user_name，department，created_at，status，call，imag
	 * e s : { i d , image_url}}) 失败返回值:** {response: "error"，result：error}
	 */
	public static final String GET_REPAIR_LIST = mDomain
			+ "/v1/property/repair_list";

	/**
	 * ### 指派任务的用户列表（get） url:** /v1/property/get_targer_users
	 * 需要参数:**subdistrict_address_id 可选参数:**
	 * department_id(部门ID，如果传了部门ID，只能指给部门里面的人) 成功返回值:** {response:
	 * "success“，result：{name, id}) 失败返回值:** {response: "error"，result：error}
	 */
	public static final String REPAIR_DISTRIBUTED = mDomain
			+ "/v1/property/get_targer_users";

	/**
	 * ### 投诉列表（get） url:** /v1/property/complaint_list
	 * 需要参数:**subdistrict_address_id 可选参数:**,offest，limit 成功返回值:** {response:
	 * "success“，result：{id，title，content，created_at,status,feedback,images{id,
	 * image}}) 失败返回值:** {response: "error"，result：error}
	 */
	public static final String GET_COMPLAINT_LIST = mDomain
			+ "/v1/property/complaint_list";
	/**
	 * 获取公告列表 参数:subdistrictaddressid from(1是业主 2 是物业) 是否有分页: 有 成功返回值:
	 * {response: "success“，result：id，title,
	 * content，updatedat，goodcount，badcount，images：{id， imageurl}} 提醒返回值: {
	 * response: "alert", alert} 错误返回值: { response: "error", result: {error}}
	 */
	public static final String GET_POST_LIST = mDomain + "/v1/notice_list";
	/**
	 * ### 楼宇列表（get） url:** /v1/property/building_list
	 * 需要参数:**{subdistrict_address_id} 可选参数:** offset，limit，name（搜索用） 成功返回值:**
	 * {response: "success“，result：id，name，description，house_count（房屋数）}
	 * 失败返回值:** {response: "error"，result：error}
	 */
	public static final String GET_BUILDING_LIST = mDomain
			+ "/v1/property/building_list";

	/**
	 * ### 楼宇类型的获取（get） url:** /v1/property/get_house_klass_options 需要参数:**
	 * 可选参数:** 成功返回值:** {response: "success“，result：[id, name]）} 失败返回值:**
	 * {response: "error"}
	 */
	public static final String GET_HOUSE_KLASS = mDomain
			+ "/v1/property/get_house_klass_options";

	/**
	 * ### 楼宇详细（get） url:** /v1/property/building_info
	 * 需要参数:**{subdistrict_address_id，building_id（楼宇id）} 可选参数:** 成功返回值:**
	 * {response:
	 * "success“，result：id，name，description，house_count（房屋数）,house_klass}
	 * 失败返回值:** {response: "error"，result：error}
	 */
	public static final String GET_BUILDING_Info = mDomain
			+ "/v1/property/building_info";
	/**
	 * ## 楼宇 添加（POST） url:** /v1/property/add_building
	 * 需要参数:**{subdistrict_address_id}
	 * 可选参数:**name(如果修改就传过来)，description,house_klass_id 成功返回值:** {response:
	 * "success“} 失败返回值:** {response: "error"，result：error}
	 */
	public static final String ADD_BUILDING_Info = mDomain
			+ "/v1/property/add_building";

	/**
	 * ### 楼宇 修改（POST） url:** /v1/property/update_building
	 * 需要参数:**{subdistrict_address_id，building_id（楼宇id）}
	 * 可选参数:**name(如果修改就传过来)，description,house_klass_id 成功返回值:** {response:
	 * "success“} 失败返回值:** {response: "error"，result：error}
	 * 
	 */
	public static final String UPDATE_BUILDING_INFO = mDomain
			+ "/v1/property/update_building";

	/**
	 * ### 楼宇 删除（POST） url:** /v1/property/delete_building
	 * 需要参数:**{subdistrict_address_id，building_id（楼宇id）} 可选参数:** 成功返回值:**
	 * {response: "success“} 失败返回值:** {response: "error"，result：error}
	 */
	public static final String DELETE_BUILDING_INFO = mDomain
			+ "/v1/property/delete_building";

	/**
	 * ### 房产缴费的列表（get） url:** /v1/property/estate_info_payment_list
	 * 需要参数:**subdistrict_address_id 可选参数:**,offest，limit 成功返回值:** {response:
	 * "success“，result：{id，building_name，house_number，name，tel，parking_tate_numbers，car_numbers，money_property，money_parking_tate
	 * } ) 失败返回值:** {response: "error"，result：error}
	 */
	public static final String GET_PROPERTY_LIST = mDomain
			+ "/v1/property/estate_info_payment_list";

	/**
	 * ### 物业费历史缴费记录（get） url:** /v1/property/payment_histroy_list
	 * 需要参数:**subdistrict_address_id，estate_info_id
	 * 可选参数:**,offest，limit，year，month 成功返回值:** {response:
	 * "success“，result：{id，data，payment_type_name，money，buy_type_name，is_pay_off，created_at}
	 * ) 失败返回值:** {response: "error"，result：error}
	 */
	public static final String GET_PROPERTY_HOSTROY_LIST = mDomain
			+ "/v1/property/payment_histroy_list";
}
