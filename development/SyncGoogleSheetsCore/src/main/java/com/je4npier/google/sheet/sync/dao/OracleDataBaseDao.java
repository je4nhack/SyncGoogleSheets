package com.je4npier.google.sheet.sync.dao;

import com.je4npier.google.sheet.sync.util.DBUtil;

public class OracleDataBaseDao extends DataBaseDao {
	
	public OracleDataBaseDao(DBUtil dbUtil, String tableName) throws Exception {
		super(dbUtil, tableName);
	}

	@Override
	public String makeSql(String tableName, Integer numRows) {
		String sql = "SELECT * FROM (" + tableName + ") T";
		
		if(numRows != null && numRows > 0)
			sql = "SELECT * FROM (" + sql + ") T WHERE ROWNUM <= " + numRows;
		
		return sql;
	}

	
}