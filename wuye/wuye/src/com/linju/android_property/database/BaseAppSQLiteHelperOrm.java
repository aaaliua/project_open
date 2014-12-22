package com.linju.android_property.database;

import java.sql.SQLException;

import com.j256.ormlite.table.TableUtils;
import com.linju.android_property.entity.Login_Bean;


public class BaseAppSQLiteHelperOrm extends BaseSQLiteHelperOrm {

	@Override
	public void createTable() {
		// TODO Auto-generated method stub
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		try {
			TableUtils.createTable(connectionSource, Login_Bean.class);
		} catch (SQLException e) {
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		}
	}
	

	@Override
	public void dropTable() {
		// TODO Auto-generated method stub
		try {
			TableUtils.dropTable(connectionSource, Login_Bean.class, true);
		} catch (SQLException e) {
		}
	}

}
