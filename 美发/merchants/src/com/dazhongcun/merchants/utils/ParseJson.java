package com.dazhongcun.merchants.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dazhongcun.merchants.entity.MakeEntity;
import com.dazhongcun.merchants.entity.Status;
import com.dazhongcun.merchants.entity.UserEntity;


public class ParseJson {

	
	//错误状态解析
	public static Status getStatus(String jsonStr){
		Status st = new Status();
		try {
			JSONObject object = new JSONObject(jsonStr);
			st.setStatus(object.optString(Status.JSON_STATUS));
			st.setCode(object.optString(Status.JSON_CODE));
			st.setMsg(object.optString(Status.JSON_MSG));
			st.setTime(object.optString(Status.JSON_TIME));
			return st;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 解析登录后用户的信息
	 * @param jsonStr
	 * @return
	 */
	public static UserEntity get_loginJSON(String jsonStr) {
		UserEntity bean = new UserEntity();
		try {
			JSONObject json = new JSONObject(jsonStr);
			String code = json.optString(Status.JSON_STATUS);
			if("0".equals(code)){
				JSONObject obj = json.getJSONObject(UserEntity.JSON_MAP);
				bean.setU_id(obj.optLong(UserEntity.JSON_ID));
				bean.setAddress(obj.optString(UserEntity.JSON_ADDRESS));
				bean.setDesc(obj.optString(UserEntity.JSON_DESCRIPTION));
				bean.setEmail(obj.optString(UserEntity.JSON_EMAIL));
				bean.setGender(obj.optString(UserEntity.JSON_GENDER));
				bean.setImagePath(obj.optString(UserEntity.JSON_IMGPATH));
				bean.setMoble(obj.optString(UserEntity.JSON_MOBILE));
				bean.setName(obj.optString(UserEntity.JSON_NAME));
				bean.setStageName(obj.optString(UserEntity.JSON_STAGENAME));
				bean.setTelePhone(obj.optString(UserEntity.JSON_TELEPHONE));
				bean.setTokenId(obj.optString(UserEntity.JSON_TOKENID));
				
				
				bean.setPositionname(obj.optString(UserEntity.JSON_POSITIONNAME));
				bean.setPraise(obj.optString(UserEntity.JSON_PRAISE));  	//点赞    
				bean.setServicefraction(obj.optString(UserEntity.JSON_SERVICEFRACTION));  //服务评分
				
				bean.setWorks(obj.optString(UserEntity.JSON_WORKS));   	//作品集 
				bean.setShopname(obj.optString(UserEntity.JSON_SHOPNAME));   	//
				bean.setSpecialty(obj.optString(UserEntity.JSON_SPECIALTY));  //特点
				bean.setHonor(obj.optString(UserEntity.JSON_HONOR));  //荣誉
				
				bean.setStoreid(obj.optString(UserEntity.JSON_STORE_ID));  //荣誉
				return bean;
			}else{
				
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;

	}
	
	//预约列表
	public static List<MakeEntity> getMakeEntityList(String jsonStr){
		List<MakeEntity> lists= new ArrayList<MakeEntity>();
		try {
			JSONObject json = new JSONObject(jsonStr);
			String code = json.optString(Status.JSON_STATUS);
			if("0".equals(code)){
				JSONArray objarr = json.getJSONArray(UserEntity.JSON_MAP);
				for(int i = 0 ;i<objarr.length();i++){
					JSONObject obj = objarr.getJSONObject(i);
					MakeEntity entity = new MakeEntity();
					entity.setCreateDate(obj.optString(MakeEntity.MAKE_CREATDATE));
					entity.setId(obj.optString(MakeEntity.MAKE_ID));
					entity.setMobile(obj.optString(MakeEntity.MAKE_MOBILE));
					entity.setOutdate(obj.optString(MakeEntity.MAKE_OUTPATIENTDATE));
					entity.setOutTime(obj.optString(MakeEntity.MAKE_OUTPATIENTTIME));
					entity.setOutType(obj.optString(MakeEntity.MAKE_OUTPATIENTTYPE));
					entity.setStyListName(obj.optString(MakeEntity.MAKE_STYLISTNAME));
					entity.setStyListId(obj.optString(MakeEntity.MAKE_STYLISTID));
					entity.setUserName(obj.optString(MakeEntity.MAKE_USERNAME));
					entity.setUserID(obj.optString(MakeEntity.MAKE_USERID));
					entity.setStatusName(obj.optString(MakeEntity.MAKE_STATUSNAME));
					entity.setLoadtype(MakeEntity.ITEM);
					lists.add(entity);
				}
				
				return lists;
			}else{
				
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	

}
