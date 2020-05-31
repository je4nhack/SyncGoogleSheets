package com.je4npier.google.sheet.sync.controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.je4npier.google.sheet.sync.dao.DataBaseDao;
import com.je4npier.google.sheet.sync.dao.GoogleSheetsDao;

public class LoadDataController extends ControllerWriteLog{
	
	private GoogleSheetsController gsController;
	private DataBaseDao dataBaseDao;
	
	public LoadDataController(
			String spreadsheetId, String sheetName, int numRow, String columnName,
			String dbName, String url, String user, String password, String tableName) throws Exception {
		
		gsController = new GoogleSheetsController(spreadsheetId, sheetName, numRow, columnName);
		
		dataBaseDao = DataBaseController.getDao(dbName, url, user, password, tableName);
		//Adaptar para que el DAO de la BD se autogenere si no existe conexión pues
		//en el método execProcess, al finalizar se cierra la conexión
	}
	
	public void execProcess(boolean needTruncate, int numCommit, int numRetries) {
		GoogleSheetsDao googleSheetsDao = gsController.getGoogleSheetsDao();
		
		if(needTruncate) {
			gsController.setWriteLog(this.getWriteLog());
			gsController.truncateSheet(numCommit);
		}
		
		try {
			ResultSet resultSet = dataBaseDao.getResultSet();
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			
			Map<String, Integer> mapColumnsDataBase = new HashMap<String, Integer>();
			for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
				mapColumnsDataBase.put(resultSetMetaData.getColumnName(i), i);
			
			List<String> columnsGoogleSheet = googleSheetsDao.getColumnNames();
			
			addLog("Insertando datos");
			
			long numRowsInserted = 0;
			
			List<List<Object>> dataset = new ArrayList<List<Object>>();
			while (resultSet.next()) {
				List<Object> row = new ArrayList<Object>();
				for (String columnGS : columnsGoogleSheet) {
					Object value = null;
					if(mapColumnsDataBase.containsKey(columnGS)) {
						value = resultSet.getObject(mapColumnsDataBase.get(columnGS));
					}
					if(value == null) row.add("");
					else row.add(value);
				}
				dataset.add(row);
				
				if(dataset.size() == numCommit) {
					
					int appendRetries = 0;
					boolean insertEnd = false;
					while(appendRetries <= numRetries && !insertEnd) {
						if(appendRetries > 0) addLog("Intento " + appendRetries + "de " + numRetries);
						appendRetries += 1;
						try {
							googleSheetsDao.appendRows(dataset);
							insertEnd = true;
						} catch (Exception e) {
							addLog("Fallo al agregar datos, el detalle: " + e.getMessage());
						}
					}					
					
					dataset.clear();
					numRowsInserted += numCommit;
					addLog("Filas insertadas: " + numRowsInserted);
				}
			}
			
			if(dataset.size() > 0) {
				googleSheetsDao.appendRows(dataset);
				numRowsInserted += dataset.size();
				addLog("Filas insertadas: " + numRowsInserted);
			}
			
			addLog("Proceso completado con éxito");
			
			dataBaseDao.closeConnection();
		} catch (Exception e) {
			addLog("Error en el proceso, el detalle: " + e.getMessage());
		}
		
	}
	
}
