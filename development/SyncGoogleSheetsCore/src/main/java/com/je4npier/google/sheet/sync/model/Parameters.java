package com.je4npier.google.sheet.sync.model;

public class Parameters {
	private Boolean useProxy;
	private String proxyHost;
	private String proxyPort;
	private String proxyUserName;
	private String proxyPassword;
	
	private String dbName;
	private String dbStringConnection;
	private String dbUserName;
	private String dbPassword;
	private String dbQuery;
	
	private String spreadsheetId;
	private String spreadsheetSheetName;
	private Integer spreadsheetRowHeader;
	private String spreadsheetColumnHeader;
	
	private Boolean truncateTable;
	private Boolean ignoreNewColumns;
	private Boolean ignoreNotFoundColumns;
	private Integer commitRows;
	private Integer attempsNumber;
	
	public Boolean getUseProxy() {
		return useProxy;
	}
	public void setUseProxy(Boolean useProxy) {
		this.useProxy = useProxy;
	}
	public String getProxyHost() {
		return proxyHost;
	}
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}
	public String getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}
	public String getProxyUserName() {
		return proxyUserName;
	}
	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}
	public String getProxyPassword() {
		return proxyPassword;
	}
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbStringConnection() {
		return dbStringConnection;
	}
	public void setDbStringConnection(String dbStringConnection) {
		this.dbStringConnection = dbStringConnection;
	}
	public String getDbUserName() {
		return dbUserName;
	}
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getDbQuery() {
		return dbQuery;
	}
	public void setDbQuery(String dbQuery) {
		this.dbQuery = dbQuery;
	}
	public String getSpreadsheetId() {
		return spreadsheetId;
	}
	public void setSpreadsheetId(String spreadsheetId) {
		this.spreadsheetId = spreadsheetId;
	}
	public String getSpreadsheetSheetName() {
		return spreadsheetSheetName;
	}
	public void setSpreadsheetSheetName(String spreadsheetSheetName) {
		this.spreadsheetSheetName = spreadsheetSheetName;
	}
	public Integer getSpreadsheetRowHeader() {
		return spreadsheetRowHeader;
	}
	public void setSpreadsheetRowHeader(Integer spreadsheetRowHeader) {
		this.spreadsheetRowHeader = spreadsheetRowHeader;
	}
	public String getSpreadsheetColumnHeader() {
		return spreadsheetColumnHeader;
	}
	public void setSpreadsheetColumnHeader(String spreadsheetColumnHeader) {
		this.spreadsheetColumnHeader = spreadsheetColumnHeader;
	}
	public Boolean getTruncateTable() {
		return truncateTable;
	}
	public void setTruncateTable(Boolean truncateTable) {
		this.truncateTable = truncateTable;
	}
	public Boolean getIgnoreNewColumns() {
		return ignoreNewColumns;
	}
	public void setIgnoreNewColumns(Boolean ignoreNewColumns) {
		this.ignoreNewColumns = ignoreNewColumns;
	}
	public Boolean getIgnoreNotFoundColumns() {
		return ignoreNotFoundColumns;
	}
	public void setIgnoreNotFoundColumns(Boolean ignoreNotFoundColumns) {
		this.ignoreNotFoundColumns = ignoreNotFoundColumns;
	}
	public Integer getCommitRows() {
		return commitRows;
	}
	public void setCommitRows(Integer commitRows) {
		this.commitRows = commitRows;
	}
	public Integer getAttempsNumber() {
		return attempsNumber;
	}
	public void setAttempsNumber(Integer attempsNumber) {
		this.attempsNumber = attempsNumber;
	}
}
