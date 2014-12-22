package com.linju.android_property.database;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import android.content.ContentValues;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseAppDbHelper<T> {

	/** 新增一条记录 */
	synchronized public int create(T po) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(po.getClass());
			return dao.create(po);
		} catch (SQLException e) {
		} finally {
			if (db != null)
				db.close();
		}
		return -1;
	}

	/**
	 * 是否存在满足条件的记录
	 * @param po
	 * @param where
	 * @return
	 */
	synchronized public boolean exists(T po, Map<String, Object> where) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(po.getClass());
			if (dao.queryForFieldValues(where).size() > 0) {
				return true;
			}
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return false;
	}

	/**
	 * 不存在则创建
	 * @param po
	 * @param where
	 * @return
	 */
	synchronized public int createIfNotExists(T po, Map<String, Object> where) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(po.getClass());
			if (dao.queryForFieldValues(where).size() < 1) {
				return dao.create(po);
			}
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return -1;
	}

	/** 查询一条记录 */
	synchronized public List<T> queryForEq(Class<T> c, String fieldName, Object value) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			return dao.queryForEq(fieldName, value);
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return new ArrayList<T>();
	}
	
	/** 查询一个记录 */
	synchronized public T queryObjForEq(Class<T> c, String fieldName, Object value) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			List<T> ts = dao.queryForEq(fieldName, value);
			if(ts.size()>0){
				return ts.get(0); 
			}
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return null;
	}

	/** 删除一条记录 */
	synchronized public int remove(T po) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(po.getClass());
			return dao.delete(po);
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return -1;
	}

	/**
	 * 根据特定条件更新特定字段
	 * 
	 * @param c
	 * @param values
	 * @param columnName where字段
	 * @param value where值
	 * @return
	 */
	synchronized public int update(Class<T> c, ContentValues values, String columnName, Object value) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			UpdateBuilder<T, Long> updateBuilder = dao.updateBuilder();
			updateBuilder.where().eq(columnName, value);
			
			Set<Entry<String, Object>> valueSet = values.valueSet();
			Iterator<Entry<String, Object>> it = valueSet.iterator();
			while(it.hasNext()){
				Entry<String, Object> valueEntry = it.next();
				updateBuilder.updateColumnValue(valueEntry.getKey(), valueEntry.getValue());
			}
			
//			for (String key : values.keySet()) {
//				updateBuilder.updateColumnValue(key, values.get(key));
//			}
			return updateBuilder.update();
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return -1;
	}

	/** 更新一条记录 */
	synchronized public int update(T po) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {

			Dao dao = db.getDao(po.getClass());
			return dao.update(po);
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return -1;
	}
	
	/** 创建或更新一条记录，如果指定id则更新 */
	synchronized public int createOrUpdate(T po){
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {

			Dao dao = db.getDao(po.getClass());
			CreateOrUpdateStatus cous = dao.createOrUpdate(po);
			return cous.getNumLinesChanged();
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return -1;
	}

	/** 查询所有记录 */
	synchronized public List<T> queryForAll(Class<T> c) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			return dao.queryForAll();
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return new ArrayList<T>();
	}
	
	/****************************************************************************************************************
	 */
	/**
	 * 用 ACS 表示按正序排序(即:从小到大排序)
	 */
	public static final String ORDER_BY_ASC = " ASC";
	/**
	 * 用 DESC 表示按倒序排序(即:从大到小排序) 
	 */
	public static final String ORDER_BY_DESC = " DESC";
	
	/**
	 * 满足特定条件并排序 
	 * @param c
	 * @param columnName1	
	 * @param value1
	 * @param columnName2
	 * @param value2
	 * @param columnName
	 * @param value
	 * @return
	 */
	synchronized public List<T> queryFor2EqOrderBy(Class<T> c, String columnName1, Object value1, String columnName2, Integer value2, String columnName, Object value){
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			QueryBuilder queryBuilder = dao.queryBuilder();
			queryBuilder.where().eq(columnName1, value1).and().eq(columnName2, value2);
			queryBuilder.orderByRaw(columnName+value);
			return queryBuilder.query();
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return new ArrayList<T>();
	}
	
	
	/**
	 * 查询满足两个条件的数据
	 * @param c
	 * @param columnName1	
	 * @param value1
	 * @param columnName2
	 * @param value2
	 * @param columnName
	 * @param value
	 * @return
	 */
	synchronized public T queryFor2Eq(Class<T> c, String columnName1, Object value1, String columnName2, Object value2){
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			QueryBuilder queryBuilder = dao.queryBuilder();
			queryBuilder.where().eq(columnName1, value1).and().eq(columnName2, value2);
			List<T> ts = queryBuilder.query();
			if(ts.size() >0){
				return ts.get(0); 
			}
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return null;
	}
	//获取满足两个条件的list
	synchronized public List<T> queryFor2EqList(Class<T> c, String columnName1, Object value1, String columnName2, Object value2){
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			QueryBuilder queryBuilder = dao.queryBuilder();
			queryBuilder.where().eq(columnName1, value1).and().eq(columnName2, value2);
			return queryBuilder.query();
			
				  
			
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return null;
	}
	
	/**
	 * 模糊查询
	 * @param c
	 * @param columnName1
	 * @param value1
	 * @param columnName2
	 * @param value2
	 * @return
	 */
	synchronized public List<T> queryFor2Like(Class<T> c, String columnName1, Object value1, String columnName2, Object value2){
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			QueryBuilder queryBuilder = dao.queryBuilder();
			queryBuilder.where().like(columnName1, "%"+value1+"%").or().like(columnName2, value2+"%");
			return queryBuilder.query();
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return new ArrayList<T>();
	}
	
	synchronized public List<T> queryOrderBy(Class<T> c, String columnName, Object value){
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			QueryBuilder queryBuilder = dao.queryBuilder();
			queryBuilder.orderByRaw(columnName+value);
			return queryBuilder.query();
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return new ArrayList<T>();
	}
	
	/**
	 * 删除满足指定条件的记录
	 * @param po
	 * @param columnName
	 * @param value
	 * @return
	 */
	synchronized public int remove(Class<T> c, String columnName, Object value) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			DeleteBuilder del = dao.deleteBuilder();
			del.where().eq(columnName, value);
			return del.delete();
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return -1;
	}
	
	
	/**
	 * “两种条件同时满足”和“两种条件互换后同时满足”</br>
	 * 中必须有一项成立的查询</br>
	 * 并且排序后限制一项</br>
	 * <p>
	 * Where where = queryBuilder.where();					</br>
	 * where.or(											</br>
	 *	 where.and(</br>
	 *		 where.eq(Account.NAME_FIELD_NAME, "foo"),		</br> 
	 *		 where.eq(Account.PASSWORD_FIELD_NAME, "_secret")</br>
	 *	 ),													</br>
	 *	 where.and(											</br>
	 *		 where.eq(Account.NAME_FIELD_NAME, "bar"), 		</br>
	 *		 where.eq(Account.PASSWORD_FIELD_NAME, "qwerty")</br>
	 *	 )													</br>
	 * );													</br>
	 *</br>
	 *This produces the following approximate SQL:			</br>
	 *SELECT * FROM account WHERE ((name = 'foo' AND passwd = '_secret') OR (name = 'bar' AND passwd = 'qwerty'))</br>
	 * </p>
	 * @param c
	 * @param columnName1
	 * @param value1
	 * @param columnName2
	 * @param value2
	 * @param columnName
	 * @param value
	 * @return
	 */
	synchronized public List<T> queryForOrEqAndEqOrderByLimit(Class<T> c,
			String columnName1, Object value1, String columnName2,
			Object value2, String columnName, Object value,int limit) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			QueryBuilder queryBuilder = dao.queryBuilder();
			Where where = queryBuilder.where();
			where.or(
				where.and(
					where.eq(columnName1, value1),
					where.eq(columnName2, value2)
						),
				where.and(
					where.eq(columnName1, value2),
					where.eq(columnName2, value1)
						)
					);
			queryBuilder.orderByRaw(columnName + value).limit(limit);
			return queryBuilder.query();
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return new ArrayList<T>();
	}
	
	
	/**
	 * 根据特定条件更新特定字段
	 * 
	 * @param c
	 * @param values
	 * @param columnName
	 *            where字段
	 * @param value
	 *            where值
	 * @return
	 */
	synchronized public int updatelist(Class<T> c, ContentValues values,
			String columnName, Object value,String columnName2, Object value2) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			UpdateBuilder<T, Long> updateBuilder = dao.updateBuilder();
			updateBuilder.where().eq(columnName, value).and().eq(columnName2, value2);

			Set<Entry<String, Object>> valueSet = values.valueSet();
			Iterator<Entry<String, Object>> it = valueSet.iterator();
			while (it.hasNext()) {
				Entry<String, Object> valueEntry = it.next();
				updateBuilder.updateColumnValue(valueEntry.getKey(),
						valueEntry.getValue());
			}

			// for (String key : values.keySet()) {
			// updateBuilder.updateColumnValue(key, values.get(key));
			// }
			return updateBuilder.update();
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return -1;
	}
	
	/** 查询满足不等于条件的记录并排序</br>
	 *  where fieldName <> value
	 */
	synchronized public List<T> queryForNeOrderBy(Class<T> c, String fieldName,
			Object value, String columnName, String order) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			QueryBuilder queryBuilder = dao.queryBuilder();
			queryBuilder.where().ne(fieldName, value);
			queryBuilder.orderByRaw(columnName + order);
			return queryBuilder.query();
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return null;
	}
	
	/**
	 * 根据特定条件查询  三种条件都满足
	 * @param c
	 * @param columnName1
	 * @param value1
	 * @param columnName2
	 * @param value2
	 * @param columnName
	 * @param value
	 * @return
	 */
	synchronized public List<T> queryFor2EqOrderBy2(Class<T> c,
			String columnName1, Object value1, String columnName2,
			Object value2, String columnName, Object value) {
		BaseSQLiteHelperOrm db = new BaseAppSQLiteHelperOrm();
		try {
			Dao dao = db.getDao(c);
			QueryBuilder queryBuilder = dao.queryBuilder();
			queryBuilder.where().eq(columnName1, value1).and()
			.eq(columnName2, value2).and().eq(columnName, value);
			return queryBuilder.query();
		} catch (SQLException e) {
			 
		} finally {
			if (db != null)
				db.close();
		}
		return new ArrayList<T>();
	}
}
