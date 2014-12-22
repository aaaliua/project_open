package com.linju.android_property.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.linju.android_property.entity.Building_info_Bean;
import com.linju.android_property.entity.ComplaintBean;
import com.linju.android_property.entity.Content_menu;
import com.linju.android_property.entity.Content_menu_child;
import com.linju.android_property.entity.GetEmployeeBean;
import com.linju.android_property.entity.ImageBean;
import com.linju.android_property.entity.Login_Bean;
import com.linju.android_property.entity.Notice_Manage_Bean;
import com.linju.android_property.entity.Property_Fee_Bean;
import com.linju.android_property.entity.Property_fee_Histroy_Bean;
import com.linju.android_property.entity.Talk_repair_Bean;

public class ParseJson {

	/**
	 * 解析登录后用户的信息
	 * @param jsonStr
	 * @return
	 */
	public static Login_Bean get_loginJSON(String jsonStr) {
		Login_Bean bean = new Login_Bean();
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			bean.setUser_id(jsonObject.optString(Login_Bean.USER_ID));
			bean.setSubdistrict_address_id(jsonObject.optString(Login_Bean.USER_SUBDISTRICTADDRESSID));
			bean.setSubdistrict_address_name(jsonObject.optString(Login_Bean.USER_SUBDISTRICTADDRESSNAME));
			bean.setKlass(jsonObject.optString(Login_Bean.USER_KLASS));
			bean.setLogin_name(jsonObject.optString(Login_Bean.USER_LOGINNAME));
			bean.setName(jsonObject.optString(Login_Bean.USER_NAME));
			bean.setTel(jsonObject.optString(Login_Bean.USER_TEL));
			bean.setEmail(jsonObject.optString(Login_Bean.USER_EMAIL));
			bean.setDepartment(jsonObject.optString(Login_Bean.USER_DEPARTMENT));
			bean.setPosition_id(jsonObject.optString(Login_Bean.USER_POSITION_ID));
			return bean;
		} catch (JSONException e) {
			e.printStackTrace();
			
		}
		return bean;

	}
	
	/**
	 * 获取权限列表
	 * @param string
	 * @return
	 * @throws JSONException
	 */
	public static String[] get_role_JSON(String string) {

		try {
			JSONObject json = new JSONObject(string);
			String response = json.getString("response");
			if ("error".equals(response)) {
	
				return null;
			} else if ("success".equals(response)) {
	
				JSONArray jsonArray = json.getJSONArray("result");
				String str = jsonArray.toString();
				str = str.replaceAll("\"", "");
				str = str.substring(1, str.length() - 1);
	
				String[] newsplitstr = str.split(",");
	
				return newsplitstr;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	
	
/**
 * 报修信息
 * @param jsonStr
 * @return
 */
	public static ArrayList<Talk_repair_Bean> Get_Repair_JSON(String jsonStr) {

		ArrayList<Talk_repair_Bean> list = null;

		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			System.out.println("jsonStrjsonStr===============" + jsonStr);
			Object response = jsonObject.get("response");

			System.out.println("responseresponse" + response);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			// System.out.println("jsonArrayjsonArray" + jsonArray);
			list = new ArrayList<Talk_repair_Bean>();

			for (int i = 0; i < jsonArray.length(); i++) {

				Talk_repair_Bean bean = new Talk_repair_Bean();
				// id，number，title，content，house_number，user_name，department，created_at，images:{id,
				// image_url
				JSONObject object = jsonArray.getJSONObject(i);
				Object id = object.getString("id");
				Object title = object.getString("title");
				Object content = object.getString("content");
				Object number = object.getString("number");
				Object house_number = object.getString("house_number");
				Object created_at = object.getString("created_at");
				Object user_name = object.getString("user_name");
				Object department = object.getString("department");
				Object call = object.getString("call");
				Object status = object.getString("status");

				System.out.println("object" + object);
				System.out.println("id" + id);
				System.out.println("title" + title);
				System.out.println("content" + content);
				System.out.println("number" + number);
				System.out.println("created_at" + created_at);
				System.out.println("house_number" + house_number);

				bean.setId(id.toString());
				bean.setTitle(title.toString());
				bean.setContent(content.toString());
				bean.setNumber(number.toString());
				bean.setHouse_number(house_number.toString());
				bean.setDepartment(department.toString());
				bean.setUser_name(user_name.toString());
				bean.setCreated_at(created_at.toString());
				bean.setCall(call.toString());
				bean.setStatus(status.toString());

				JSONArray jsonArray2 = object.getJSONArray("images");

				System.out.println("jsonArray2" + jsonArray2);

				ArrayList<ImageBean> bean2 = new ArrayList<ImageBean>();
				for (int j = 0; j < jsonArray2.length(); j++) {

					ImageBean b = new ImageBean();

					JSONObject object2 = jsonArray2.getJSONObject(j);

					// Object image_id=object2.getString("image_id");
					Object image_url = object2.getString("image_url");

					System.out.println("image_urlimage_urlimage_url"
							+ image_url);

					b.setImage(image_url.toString());
					bean2.add(b);
					System.out
							.println("bean2bean2bean2===============" + bean2);

				}

				bean.setImages(bean2);
				list.add(bean);
				System.out.println("beanbean=====================" + bean);

				System.out.println("解析中list11111111111111111===" + list);
			}

			// return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("解析中list222222222222222===" + list);
		return list;

	}
	
	/**
	 * 
	 * 获取部门、职位的对象
	 * 
	 * @param jsonstr
	 * @return
	 */
	public static ArrayList<GetEmployeeBean> getEmployeeData(String jsonstr) {
		ArrayList<GetEmployeeBean> list = new ArrayList<GetEmployeeBean>();
		try {
			JSONObject object = new JSONObject(jsonstr);
			String response = object.getString("response");
			JSONArray jsonArray = object.getJSONArray("result");
			for (int i = 0; i < jsonArray.length(); i++) {
				GetEmployeeBean bean = new GetEmployeeBean();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				bean.setId(jsonObject.getString("id").toString());
				bean.setName(jsonObject.getString("name").toString());
				list.add(bean);

			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	

	/**
	 * 投诉列表
	 * @param jsonStr
	 * @return
	 */
	public static ArrayList<ComplaintBean> GetComplaintJSON(String jsonStr) {

		ArrayList<ComplaintBean> list = null;

		try {

			JSONObject jsonObject = new JSONObject(jsonStr);
			Object response = jsonObject.get("response");
			if ("error".equals(response)) {
				return null;
			} else if ("success".equals(response)) {
				System.out.println("responseresponse" + response);
				JSONArray jsonArray = jsonObject.getJSONArray("result");
				System.out.println("jsonArrayjsonArray" + jsonArray);
				list = new ArrayList<ComplaintBean>();

				for (int i = 0; i < jsonArray.length(); i++) {

					ComplaintBean bean = new ComplaintBean();

					// {id，title，content，created_at,status,feedback,images{id,
					// image}})
					JSONObject object = jsonArray.getJSONObject(i);
					Object id = object.getString("id");
					Object title = object.getString("title");
					Object content = object.getString("content");
					Object created_at = object.getString("created_at");
					Object status = object.getString("status");
					Object feedback = object.getString("feedback");

					System.out.println("object" + object);
					System.out.println("id" + id);
					System.out.println("title" + title);
					System.out.println("content" + content);
					System.out.println("created_at" + created_at);
					System.out.println("status" + status);
					System.out.println("feedback" + feedback);

					bean.setId(id.toString());
					bean.setTitle(title.toString());
					bean.setContent(content.toString());
					bean.setCreated_at(created_at.toString());
					bean.setFeedback(feedback.toString());
					bean.setStatus(status.toString());

					JSONArray jsonArray2 = object.getJSONArray("images");

					System.out.println("jsonArray2" + jsonArray2);

					ArrayList<ImageBean> bean2 = new ArrayList<ImageBean>();
					for (int j = 0; j < jsonArray2.length(); j++) {

						ImageBean I = new ImageBean();

						JSONObject object2 = jsonArray2.getJSONObject(j);

						Object image = object2.getString("image");
						Object image_id = object2.getString("id");

						System.out.println("image_urlimage_urlimage_url"
								+ image);

						I.setImage(image.toString());
						I.setId(image_id.toString());

						bean2.add(I);

					}
					bean.setImages(bean2);
					list.add(bean);

					System.out.println("解析中list===" + list);
				}
				return list;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	
	
	/**
	 * 公告
	 * 
	 * @param jsonStr
	 * @return
	 */

	public static ArrayList<Notice_Manage_Bean> get_notice_list(String jsonStr) {

		ArrayList<Notice_Manage_Bean> list = null;

		try {

			JSONObject jsonObject = new JSONObject(jsonStr);
			Object response = jsonObject.get("response");

			System.out.println("responseresponse" + response);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			System.out.println("jsonArrayjsonArray" + jsonArray);
			list = new ArrayList<Notice_Manage_Bean>();

			for (int i = 0; i < jsonArray.length(); i++) {

				Notice_Manage_Bean bean = new Notice_Manage_Bean();

				// id，title, content，updated_at，good_count，bad_count，images：{id，
				// image_url}
				JSONObject object = jsonArray.getJSONObject(i);
				Object id = object.getString("id");
				Object title = object.getString("title");
				Object content = object.getString("content");
				Object good_count = object.getString("good_count");
				Object bad_count = object.getString("bad_count");
				Object updated_at = object.getString("updated_at");

				System.out.println("object" + object);
				System.out.println("id" + id);
				System.out.println("title" + title);
				System.out.println("content" + content);
				System.out.println("good_count" + good_count);
				System.out.println("updated_at" + updated_at);
				System.out.println("bad_count" + bad_count);

				bean.setId(id.toString());
				bean.setTitle(title.toString());
				bean.setContent(content.toString());
				bean.setUpdated_at(updated_at.toString());
				bean.setGood_count(good_count.toString());
				bean.setBad_count(bad_count.toString());

				JSONArray jsonArray2 = object.getJSONArray("images");

				System.out.println("jsonArray2" + jsonArray2);

				ArrayList<ImageBean> bean2 = new ArrayList<ImageBean>();
				for (int j = 0; j < jsonArray2.length(); j++) {

					ImageBean image = new ImageBean();

					JSONObject object2 = jsonArray2.getJSONObject(j);

					Object image_url = object2.getString("image_url");
					Object image_id = object2.getString("id");

					System.out.println("image_urlimage_urlimage_url"
							+ image_url);

					image.setImage(image_url.toString());
					image.setId(image_id.toString());

					bean2.add(image);

				}
				bean.setImages(bean2);
				list.add(bean);

				System.out.println("解析中list===" + list);
			}

			// return list;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	/**
	 * 解析功能模块
	 */
	public static List<Content_menu> getMenuList(String json){
		List<Content_menu> lists = null;
		try {
			JSONObject map = new JSONObject(json);
			JSONArray array = map.getJSONArray(Content_menu.JSON_MAP);
			lists = new ArrayList<Content_menu>();
			for(int i = 0 ;i<array.length();i++){
				Content_menu menu = new Content_menu();
				JSONObject obj = array.getJSONObject(i);
				menu.setContentID(obj.optString(Content_menu.CONTENT_ID));
				menu.setContentTitle(obj.optString(Content_menu.CONTENT_TITLE));
				//解析二级数组
				JSONArray child = obj.getJSONArray(Content_menu.CONTENT_MENU);
				List<Content_menu_child> childMenu = new ArrayList<Content_menu_child>();
				for(int j = 0 ;j<child.length();j++){
					Content_menu_child child2 = new Content_menu_child();
					JSONObject childobj = child.getJSONObject(j);
					child2.setType_id(childobj.optString(Content_menu_child.CONTENT_TYPE_ID));
					child2.setType_name(childobj.optString(Content_menu_child.CONTENT_TYPE_NAME));
					child2.setType_type(childobj.optString(Content_menu_child.CONTENT_TYPE_TYPE));
					childMenu.add(child2); 
				}
				menu.setMenus(childMenu);
				lists.add(menu);
			}
		} catch (JSONException e) {
			lists = null;
		}
		
		return lists;
	}
	
	
	/**
	 * 楼宇列表
	 * @param string
	 * @return
	 */
		public static List<Building_info_Bean> GetBuildingJSON(String string) {

			ArrayList<Building_info_Bean> list = null;
			try {
				JSONObject json = new JSONObject(string);
				String response = json.getString("response");
				if ("error".equals(response)) {
					return null;
				} else if ("success".equals(response)) {
					JSONArray jsonArray = json.getJSONArray("result");
					list = new ArrayList<Building_info_Bean>();
					for (int i = 0; i < jsonArray.length(); i++) {
						Building_info_Bean bean = new Building_info_Bean();
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						bean.setId(jsonObject.getString("id"));
						bean.setName(jsonObject.getString("name"));
						bean.setDescription(jsonObject.getString("description"));
						bean.setHouse_count(jsonObject.getString("house_count"));
						bean.setHouse_klass(jsonObject.getString("house_klass"));
						bean.setHouse_klass_id(jsonObject.getString("house_klass_id"));
//						bean.setFirst_layer_price(jsonObject
//								.getString("first_layer_price"));
//						bean.setPrice(jsonObject.getString("price"));
//						bean.setDevice_money(jsonObject.getString("device_money"));
						list.add(bean);
					}
					return list;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		
		

		/**
		 * 
		 * 物业费
		 * 
		 * @param string
		 * @return
		 * @throws JSONException
		 */

		public static List<Property_Fee_Bean> property_feeJSON(String string) {

			List<Property_Fee_Bean> list = null;
			try {
				JSONObject json = new JSONObject(string);
				Object response = json.getString("response");
				if ("error".equals(response)) {
					return null;
				} else if ("success".equals(response)) {

					JSONArray jsonArray = json.getJSONArray("result");
					list = new ArrayList<Property_Fee_Bean>();
					for (int i = 0; i < jsonArray.length(); i++) {
						Property_Fee_Bean bean = new Property_Fee_Bean();
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						bean.setId(jsonObject.getString("id"));
						bean.setName(jsonObject.getString("name"));
						bean.setTel(jsonObject.getString("tel"));
						bean.setBuilding_name(jsonObject.getString("building_name"));
						bean.setCar_numbers(jsonObject.getString("car_numbers"));
						bean.setHouse_number(jsonObject.getString("house_number"));
						bean.setMoney_parking_tate(jsonObject
								.getString("money_parking_tate"));
						bean.setMoney_property(jsonObject
								.getString("money_property"));
						bean.setParking_tate_numbers(jsonObject
								.getString("parking_tate_numbers"));
						list.add(bean);
					}
					System.out.println("list===========================" + list);
					return list;
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * 历史记录
		 * 
		 * @param string
		 * @return
		 * @throws JSONException
		 */
		public static List<Property_fee_Histroy_Bean> property_fee_histroyJSON(String string) {

			List<Property_fee_Histroy_Bean> list = null;
			try {
				JSONObject json = new JSONObject(string);
				Object response = json.getString("response");
				if ("error".equals(response)) {
					return null;
				} else if ("success".equals(response)) {
					JSONArray jsonArray = json.getJSONArray("result");
					list = new ArrayList<Property_fee_Histroy_Bean>();
					for (int i = 0; i < jsonArray.length(); i++) {
						Property_fee_Histroy_Bean bean = new Property_fee_Histroy_Bean();
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						bean.setId(jsonObject.getString("id"));
						bean.setBuy_type_name(jsonObject.getString("buy_type_name"));
						bean.setCreated_at(jsonObject.getString("created_at"));
						bean.setData(jsonObject.getString("data"));
						bean.setIs_pay_off(jsonObject.getString("is_pay_off"));
						bean.setMoney(jsonObject.getString("money"));
						bean.setPayment_type_name(jsonObject
								.getString("payment_type_name"));
						list.add(bean);
					}
					return list;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

}
