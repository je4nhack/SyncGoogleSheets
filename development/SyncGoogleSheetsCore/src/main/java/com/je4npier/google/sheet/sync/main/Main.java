package com.je4npier.google.sheet.sync.main;

import java.util.HashMap;
import java.util.Map;

import com.je4npier.google.sheet.sync.controller.LoadDataController;

public class Main {
	
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
			throw new Exception("Se debe indicar al menos el parámetro que indica la ruta del archivo que contiene los datos del proceso.");
		}else {
			params = new HashMap<String, String>();
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
			
			if(params.get(LABEL_SPREADSHEET_ID) == null) throw new Exception("Se debe especificar un spreadsheet id");
			String spreadsheetId = params.get(LABEL_SPREADSHEET_ID);
			
			if(params.get(LABEL_SHEET_NAME) == null) throw new Exception("Se debe especificar un nombre de una hoja del spreadsheet");
			String sheetName = params.get(LABEL_SHEET_NAME);
			
			int numRow = 1;
			if(params.get(LABEL_SHEET_HEADER_ROW) != null)
				numRow = Integer.parseInt(params.get(LABEL_SHEET_HEADER_ROW));
			
			String columnName = "A";
			if(params.get(LABEL_SHEET_HEADER_COLUMN) != null)
				columnName = params.get(LABEL_SHEET_HEADER_COLUMN);
			
			if(params.get(LABEL_DATABASE_NAME) == null) throw new Exception("Se debe especificar que base de datos se utilizará");
			String dbName = params.get(LABEL_DATABASE_NAME);
			
			if(params.get(LABEL_DATABASE_STRING_CONNECTION) == null) throw new Exception("Se debe especificar un string de conexión");
			String url = params.get(LABEL_DATABASE_STRING_CONNECTION);
			
			if(params.get(LABEL_DATABASE_SCHEMA) == null)
				throw new Exception("Se debe especificar un nombre de usuario para la conexión a la base de datos");
			String user = params.get(LABEL_DATABASE_SCHEMA);
			
			String password = params.get(LABEL_DATABASE_SCHEMA_PASSWORD);
			
			if(params.get(LABEL_SQL_QUERY) == null) throw new Exception("Se debe especificar el nombre de la tabla o consulta SQL");
			String tableName = params.get(LABEL_SQL_QUERY);
			
			boolean needTruncate = false;
			if(params.get(LABEL_TRUNCATE) != null)
				needTruncate = Boolean.parseBoolean(params.get(LABEL_TRUNCATE));
			
			int numCommit = 4000;
			if(params.get(LABEL_COMMIT_SIZE) != null)
				numCommit = Integer.parseInt(params.get(LABEL_COMMIT_SIZE));
			
			int retriesNumber = 2;
			if(params.get(LABEL_RETRIES_NUMBER) != null)
				retriesNumber = Integer.parseInt(params.get(LABEL_RETRIES_NUMBER));
			
			LoadDataController loadDataController =
					new LoadDataController(spreadsheetId, sheetName, numRow, columnName, dbName, url, user, password, tableName);
			
			loadDataController.execProcess(needTruncate, numCommit, retriesNumber);
		}
	}

}
