package com.je4npier.google.sheet.sync.main;

import java.util.HashMap;
import java.util.Map;

import com.je4npier.google.sheet.sync.controller.LoadDataController;
import com.je4npier.google.sheet.sync.controller.ParametersController;
import com.je4npier.google.sheet.sync.model.Parameters;

public class Main {
	private final static String LABEL_PARAMETERS_FILE = "file";
	
	private final static String LABEL_PROXY_HOST = "proxyHost";
	private final static String LABEL_PROXY_PORT = "proxyPort";
	private final static String LABEL_PROXY_USER = "proxyUser";
	private final static String LABEL_PROXY_PASS = "proxyPass";
	
	private final static String LABEL_DATABASE_NAME = "dbName";
	private final static String LABEL_DATABASE_STRING_CONNECTION = "dbStringConnection";
	private final static String LABEL_DATABASE_SCHEMA = "dbSchema";
	private final static String LABEL_DATABASE_SCHEMA_PASSWORD = "dbSchemaPass";
	private final static String LABEL_SQL_QUERY = "sqlQuery";
	
	private final static String LABEL_SPREADSHEET_ID = "gsSpreadsheetId";
	private final static String LABEL_SHEET_NAME = "gsSheetName";
	private final static String LABEL_SHEET_HEADER_COLUMN = "gsSheetHeaderColumn";
	private final static String LABEL_SHEET_HEADER_ROW = "gsSheetHeaderRow";
	
	private final static String LABEL_TRUNCATE = "truncate";
	private final static String LABEL_COMMIT_SIZE = "commitSize";
	private final static String LABEL_RETRIES_NUMBER = "retriesNumber";
	
	private final static String LABEL_SEPARATE_PARAM = "=";

	public static void main(String[] args) throws Exception {
		
		Map<String, String> params;
		
		if(args.length == 0) {
			throw new Exception("Se debe indicar al menos el parámetro de la ruta del archivo que contiene los datos del proceso.");
		}else {
			params = new HashMap<String, String>();
			params.put(LABEL_PARAMETERS_FILE, null);
			
			params.put(LABEL_PROXY_HOST, null);
			params.put(LABEL_PROXY_PORT, null);
			params.put(LABEL_PROXY_USER, null);
			params.put(LABEL_PROXY_PASS, null);
			
			params.put(LABEL_DATABASE_NAME, null);
			params.put(LABEL_DATABASE_STRING_CONNECTION, null);
			params.put(LABEL_DATABASE_SCHEMA, null);
			params.put(LABEL_DATABASE_SCHEMA_PASSWORD, null);
			params.put(LABEL_SQL_QUERY, null);
			
			params.put(LABEL_SPREADSHEET_ID, null);
			params.put(LABEL_SHEET_NAME, null);
			params.put(LABEL_SHEET_HEADER_COLUMN, null);
			params.put(LABEL_SHEET_HEADER_ROW, null);
			
			params.put(LABEL_TRUNCATE, null);
			params.put(LABEL_COMMIT_SIZE, null);
			params.put(LABEL_RETRIES_NUMBER, null);
			
			//Read params
			for (String arg : args) {
				String[] param = arg.split(LABEL_SEPARATE_PARAM);
				if(params.containsKey(param[0]) && param.length == 2) {
					params.put(param[0], param[1]);
				}
			}
			
			
			//Read parameters
			String spreadsheetId = null;
			String sheetName = null;
			int numRow = 1;
			String columnName = "A";
			String dbName = null;
			String url = null;
			String user = null;
			String password = null;
			String tableName = null;
			boolean needTruncate = false;
			int numCommit = 4000;
			int retriesNumber = 2;
			
			if(params.get(LABEL_PARAMETERS_FILE) != null) {
				//Read values from file
				Parameters parameters = ParametersController.readParameters(params.get(LABEL_PARAMETERS_FILE));
				
				if(parameters.getUseProxy() != null && parameters.getUseProxy()) {
					System.setProperty("https.proxyHost", parameters.getProxyHost());
			        System.setProperty("https.proxyPort", parameters.getProxyPort());
			        System.setProperty("https.proxyUser", parameters.getProxyUserName());
			        System.setProperty("https.proxyPassword", parameters.getProxyPassword());
			        System.setProperty("com.google.api.client.should_use_proxy", "true");
				}
				
				if(parameters.getDbName() != null) dbName = parameters.getDbName();
				if(parameters.getDbStringConnection() != null) url = parameters.getDbStringConnection();
				if(parameters.getDbUserName() != null) user = parameters.getDbUserName();
				if(parameters.getDbPassword() != null) password = parameters.getDbPassword();
				if(parameters.getDbQuery() != null) tableName = parameters.getDbQuery();
				
				if(parameters.getSpreadsheetId() != null) spreadsheetId = parameters.getSpreadsheetId();
				if(parameters.getSpreadsheetSheetName() != null) sheetName = parameters.getSpreadsheetSheetName();
				if(parameters.getSpreadsheetRowHeader() != null) numRow = parameters.getSpreadsheetRowHeader();
				if(parameters.getSpreadsheetColumnHeader() != null) columnName = parameters.getSpreadsheetColumnHeader();
				
				if(parameters.getTruncateTable() != null) needTruncate = parameters.getTruncateTable();
				
				if(parameters.getCommitRows() != null) numCommit = parameters.getCommitRows();
				if(parameters.getAttempsNumber() != null) retriesNumber = parameters.getAttempsNumber();
			}
			
			if(params.get(LABEL_PROXY_HOST) != null && params.get(LABEL_PROXY_PORT) != null) {
				System.setProperty("https.proxyHost", params.get(LABEL_PROXY_HOST));
		        System.setProperty("https.proxyPort", params.get(LABEL_PROXY_PORT));
		        System.setProperty("https.proxyUser", params.get(LABEL_PROXY_USER));
		        System.setProperty("https.proxyPassword", params.get(LABEL_PROXY_PASS));
		        System.setProperty("com.google.api.client.should_use_proxy", "true");
			}
			
			if(params.get(LABEL_SPREADSHEET_ID) != null) spreadsheetId = params.get(LABEL_SPREADSHEET_ID);
			if(params.get(LABEL_SHEET_NAME) != null) sheetName = params.get(LABEL_SHEET_NAME);
			if(params.get(LABEL_SHEET_HEADER_ROW) != null) numRow = Integer.parseInt(params.get(LABEL_SHEET_HEADER_ROW));
			if(params.get(LABEL_SHEET_HEADER_COLUMN) != null) columnName = params.get(LABEL_SHEET_HEADER_COLUMN);
			
			if(params.get(LABEL_DATABASE_NAME) != null) dbName = params.get(LABEL_DATABASE_NAME);
			if(params.get(LABEL_DATABASE_STRING_CONNECTION) != null) url = params.get(LABEL_DATABASE_STRING_CONNECTION);
			if(params.get(LABEL_DATABASE_SCHEMA) != null) user = params.get(LABEL_DATABASE_SCHEMA);
			if(params.get(LABEL_DATABASE_SCHEMA_PASSWORD) != null) password = params.get(LABEL_DATABASE_SCHEMA_PASSWORD);
			if(params.get(LABEL_SQL_QUERY) != null) tableName = params.get(LABEL_SQL_QUERY);
			
			if(params.get(LABEL_TRUNCATE) != null) needTruncate = Boolean.parseBoolean(params.get(LABEL_TRUNCATE));
			if(params.get(LABEL_COMMIT_SIZE) != null) numCommit = Integer.parseInt(params.get(LABEL_COMMIT_SIZE));
			if(params.get(LABEL_RETRIES_NUMBER) != null) retriesNumber = Integer.parseInt(params.get(LABEL_RETRIES_NUMBER));
			
			//validations
			if(spreadsheetId == null) throw new Exception("Se debe especificar un spreadsheet id");
			if(sheetName == null) throw new Exception("Se debe especificar un nombre de una hoja del spreadsheet");
			if(dbName == null) throw new Exception("Se debe especificar que base de datos se utilizará");
			if(url == null) throw new Exception("Se debe especificar un string de conexión");
			if(tableName == null) throw new Exception("Se debe especificar el nombre de la tabla o consulta SQL");
			
			//Run process
			LoadDataController loadDataController =
					new LoadDataController(spreadsheetId, sheetName, numRow, columnName, dbName, url, user, password, tableName);
			
			loadDataController.execProcess(needTruncate, numCommit, retriesNumber);
		}
	}

}
