package com.dazhongcun.merchants.database;

import java.sql.SQLException;

import com.dazhongcun.merchants.entity.Downloads;
import com.dazhongcun.merchants.entity.UserEntity;
import com.j256.ormlite.table.TableUtils;


public class BaseAppSQLiteHelperOrm extends BaseSQLiteHelperOrm {

	@Override
	public void createTable() {
		// TODO Auto-generated method stub
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		try {
			TableUtils.createTable(connectionSource, UserEntity.class);
			TableUtils.createTable(connectionSource, Downloads.class);
		} catch (SQLException e) {
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		}
	}
	

	@Override
	public void dropTable() {
		// TODO Auto-generated method stub
		try {
			TableUtils.dropTable(connectionSource, UserEntity.class, true);
			TableUtils.dropTable(connectionSource, Downloads.class, true);
		} catch (SQLException e) {
		}
	}

}
