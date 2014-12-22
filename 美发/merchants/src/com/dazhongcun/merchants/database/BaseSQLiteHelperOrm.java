package com.dazhongcun.merchants.database;

import android.database.sqlite.SQLiteDatabase;

import com.dazhongcun.merchants.application.AppApplication;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public abstract class BaseSQLiteHelperOrm extends OrmLiteSqliteOpenHelper {

	private static final int DATABASE_VERSION = 4; 
	public ConnectionSource connectionSource;

	public BaseSQLiteHelperOrm() {
		// TODO Auto-generated constructor stub
		super(AppApplication.getContext(), AppApplication.DATABASE_NAME,
				null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		// TODO Auto-generated method stub
		this.connectionSource = connectionSource;
		createTable();
	}

	public abstract void createTable();

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int arg2, int arg3) {
		// TODO Auto-generated method stub
		this.connectionSource = connectionSource;
		dropTable();
		onCreate(db, connectionSource);
	}

	public abstract void dropTable();
}
