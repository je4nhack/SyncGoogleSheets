package com.je4npier.google.sheet.sync.controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.je4npier.google.sheet.sync.dao.DataBaseDao;
import com.je4npier.google.sheet.sync.dao.OracleDataBaseDao;
import com.je4npier.google.sheet.sync.dao.SqlDataBaseDao;
import com.je4npier.google.sheet.sync.util.DBUtil;

public class DataBaseController extends ControllerWriteLog{
	public static Map<String, String> mapDriver;
	static {
		mapDriver = new HashMap<>();
		mapDriver.put("Oracle", "oracle.jdbc.driver.OracleDriver");
		mapDriver.put("SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
	}
	
	public static DataBaseDao getDao(
			String dbName, String url, String user, String password, String tableName) throws Exception {
		
		DBUtil dbUtil = new DBUtil(mapDriver.get(dbName), url, user, password);
		
		DataBaseDao dao = null;
		switch (dbName) {
			case "Oracle":
				dao = new OracleDataBaseDao(dbUtil, tableName);
				break;
	
			case "SQL Server":
				dao = new SqlDataBaseDao(dbUtil, tableName);
				break;
		}
		
		return dao;
	}
	
	public static List<List<Object>> getPreviewDataset(
			String dbName, String url, String user, String password, String tableName) throws Exception {
		
		final int numRowsPrview = 10;
		
		DataBaseDao dao = getDao(dbName, url, user, password, tableName);
		
		ResultSet resultSet = dao.getResultSet(numRowsPrview);
		
		List<List<Object>> result = new ArrayList<List<Object>>();
		
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		
		List<Object> columns = new ArrayList<Object>();
		for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
			columns.add(resultSetMetaData.getColumnName(i));
		}
		result.add(columns);
		
		while (resultSet.next()) {
			List<Object> row = new ArrayList<Object>();
			for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
				row.add(resultSet.getObject(i));
			}
			result.add(row);
		}
		
		dao.closeConnection();
		
		return result;
	}
	
	
}
