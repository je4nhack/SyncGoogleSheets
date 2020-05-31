package com.je4npier.google.sheet.sync.dao;

import com.je4npier.google.sheet.sync.util.DBUtil;

public class SqlDataBaseDao extends DataBaseDao {
	
	public SqlDataBaseDao(DBUtil dbUtil, String tableName) throws Exception {
		super(dbUtil, tableName);
	}

	@Override
	public String makeSql(String tableName, Integer numRows) {
		String sql = tableName;
		if(!tableName.toUpperCase().contains("SELECT "))
			sql = "SELECT * FROM " + tableName;
		
		if(numRows != null && numRows > 0)
			sql = "SELECT TOP " + numRows + " * FROM (" + sql + ") T";
		
		return sql;
	}

	
}