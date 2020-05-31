package com.je4npier.google.sheet.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.je4npier.google.sheet.sync.util.DBUtil;

public abstract class DataBaseDao {
	
	private Statement statement;
	private String sql;
	private DBUtil dbUtil;
	
	public DataBaseDao(DBUtil dbUtil, String tableName) throws SQLException, Exception {
		this.dbUtil = dbUtil;
		this.statement = dbUtil.getConnection().createStatement();
		this.sql = makeSql(tableName);
	}
	
	public abstract String makeSql(String tableName, Integer numRows);
	
	public String makeSql(String tableName) {
		return makeSql(tableName, null);
	}
	
	public ResultSet getResultSet(Integer numRows) throws SQLException {
		return statement.executeQuery(makeSql(this.sql, numRows));
	}
	
	public ResultSet getResultSet() throws SQLException {
		return statement.executeQuery(this.sql);
	}
	
	public void closeConnection() throws SQLException {
		this.statement.close();
		this.dbUtil.closeConnection();
	}
	
	public void getPreviewDataset() throws SQLException {
		statement.close();
		this.dbUtil.closeConnection();
	}
}