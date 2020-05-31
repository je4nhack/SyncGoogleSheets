package com.je4npier.google.sheet.sync.dao;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.DeleteDimensionRequest;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GoogleSheetsDao {
	
	private Credential credential;
	private String spreadsheetId;
	private String sheetName;
	private int headerRow;
	private String headerColumn;
	
	private Integer sheetId;
	
	private Sheets service;
	private Integer lastRow;
	private Integer numColumns;
	private String lastColumn;
	
	private Integer numRowsGrid;
	
	private List<String> columnNames;
	
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	public GoogleSheetsDao(
			Credential credential,
			String spreadsheetId,
			String sheetName,
			int headerRow,
			String headerColumn) throws GeneralSecurityException, IOException {
		this.credential = credential;
		this.service = new Sheets.Builder(credential.getTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
		
		this.spreadsheetId = spreadsheetId;
		this.sheetName = sheetName;
		this.headerRow = headerRow;
		this.headerColumn = headerColumn;
	}
	
	public void deleteRangeRows(int startRow, int endRow) throws IOException {
		List<Request> requests = new ArrayList<Request>();
		
		requests.add(new Request()
				.setDeleteDimension(new DeleteDimensionRequest().
						setRange(new DimensionRange()
								.setDimension("ROWS")
								.setSheetId(getSheetId())
								.setStartIndex(headerRow + startRow - 1)
								.setEndIndex(headerRow + endRow))));
		
		BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
		
		service.spreadsheets()
			.batchUpdate(spreadsheetId, body)
			.execute();
	}
	
	private void updateRowsStatistics() throws IOException {
		List<List<Object>> rows = new ArrayList<List<Object>>();
        rows.add(Arrays.asList(null, null, null));
        
        String range = sheetName + "!" + headerColumn + headerRow + ":" + getLastColumn() + headerRow;
        
        List<List<Object>> listDummy = new ArrayList<List<Object>>();
        listDummy.add(getDummyList(getNumColumns()));
        ValueRange body = new ValueRange().setValues(listDummy);
        
        AppendValuesResponse result = service.spreadsheets().values()
        		.append(spreadsheetId, range, body)
        		.setValueInputOption("RAW")
        		.execute();
        
        String textDelete = "'" + sheetName + "'" + "!" + headerColumn;
        
        this.lastRow = Integer.parseInt(
        		result.getUpdates().getUpdatedRange().substring(textDelete.length()));
        
        //this.numRows = lastRow - headerRow - 1;
	}
	
	private void updateColumnsStatistics() throws IOException {
		//Read header
        String range = sheetName + "!" + this.headerRow + ":" + this.headerRow;
        
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .setDateTimeRenderOption("SERIAL_NUMBER")
                .execute();
        
        this.lastColumn = response.getRange().replace(Integer.toString(this.headerRow), "").split(":")[1];
	}
	
	private void updateColumnNames() throws IOException {
		//Read header
		String range = sheetName + "!" + headerColumn + headerRow + ":" + getLastColumn() + headerRow;
        
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .setDateTimeRenderOption("SERIAL_NUMBER")
                .execute();
        
        this.numColumns = response.getValues().get(0).size();
        
        this.columnNames = new ArrayList<String>();
        for(Object value : response.getValues().get(0)) {
        	if(value!= null) this.columnNames.add(value.toString());
        	else this.columnNames.add(null);
        }
	}
	
	public List<List<Object>> readRows(int numRows) throws IOException {
		String range = sheetName + "!" + headerColumn + headerRow + ":" + getLastColumn() + (headerRow + numRows);
        
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .setDateTimeRenderOption("SERIAL_NUMBER")
                .execute();
        
        return response.getValues();
	}
	
	private void updateSheetData() throws IOException {
		Spreadsheet spreadsheet = service.spreadsheets().get(spreadsheetId).execute();
		for(Sheet sheet : spreadsheet.getSheets()) {
			if(sheet.getProperties().getTitle().equals(sheetName)) {
				this.sheetId = sheet.getProperties().getSheetId();
				this.numRowsGrid = sheet.getProperties().getGridProperties().getRowCount();
				
				break;
			}
		}
	}
	
	public void appendRows(List<List<Object>> dataset) throws IOException {
		List<List<Object>> rows = new ArrayList<List<Object>>();
        rows.add(Arrays.asList(null, null, null));
        
        String range = sheetName + "!" + headerColumn + headerRow + ":" + getLastColumn() + headerRow;
        
        ValueRange body = new ValueRange().setValues(dataset);
        
        service.spreadsheets().values()
        		.append(spreadsheetId, range, body)
        		.setValueInputOption("RAW")
        		.execute();
	}
	
	public void writeRows(List<List<Object>> dataset, int startIndex, int endIndex) throws IOException {
		List<List<Object>> rows = new ArrayList<List<Object>>();
        rows.add(Arrays.asList(null, null, null));
        
        String range = sheetName + "!" + headerColumn + startIndex + ":" + getLastColumn() + endIndex;
        ValueRange body = new ValueRange().setValues(dataset);
        
        service.spreadsheets().values()
        	.update(spreadsheetId, range, body)
        	.setValueInputOption("RAW")
        	.execute();
	}
	
	public void writeRows2(List<List<Object>> dataset, String range) throws IOException {
		List<List<Object>> rows = new ArrayList<List<Object>>();
        rows.add(Arrays.asList(null, null, null));
        
        ValueRange body = new ValueRange()
        		.setValues(dataset)
        		.setMajorDimension("COLUMNS");
        
        service.spreadsheets().values()
        	.update(spreadsheetId, range, body)
        	.setValueInputOption("RAW")
        	.execute();
	}
	
	public void appendRowDummy(Object value) throws IOException {
		List<List<Object>> rows = new ArrayList<List<Object>>();
        rows.add(Arrays.asList(null, null, null));
        
        String range = sheetName + "!" + headerColumn + headerRow + ":" + getLastColumn() + headerRow;
        
        List<List<Object>> listDummy = new ArrayList<List<Object>>();
        listDummy.add(getDummyList(getNumColumns(), value));
        ValueRange body = new ValueRange().setValues(listDummy);
        
        service.spreadsheets().values()
        		.append(spreadsheetId, range, body)
        		.setValueInputOption("RAW")
        		.execute();
	}
	
	private List<Object> getDummyList(int n, Object value){
		List<Object> result = new ArrayList<Object>();
		for(int i = 0; i < n; i++)result.add(value);
		return result;
	}
	
	private List<Object> getDummyList(int n){
		return getDummyList(n, null);
	}

	public String getSpreadsheetId() {
		return spreadsheetId;
	}

	public String getSheetName() {
		return sheetName;
	}

	public int getHeaderRow() {
		return headerRow;
	}

	public String getHeaderColumn() {
		return headerColumn;
	}
	
	/* Por revisar algunos errores
	public Integer getNumRows() throws IOException {
		if(numRows == null) updateRowsStatistics();
		return numRows;
	}
	*/

	public Integer getLastRow() throws IOException {
		if(lastRow == null) updateRowsStatistics();
		return lastRow;
	}

	public Integer getNumColumns() throws IOException {
		if(numColumns == null) updateColumnNames();
		return numColumns;
	}

	public String getLastColumn() throws IOException {
		if (lastColumn == null) updateColumnsStatistics();
		return lastColumn;
	}
	
	public List<String> getColumnNames() throws IOException {
		if (columnNames == null) updateColumnNames();
		return columnNames;
	}

	public Integer getSheetId() throws IOException {
		if(sheetId == null) updateSheetData();
		return sheetId;
	}

	public Integer getNumRowsGrid() throws IOException {
		if(numRowsGrid == null) updateSheetData();
		return numRowsGrid;
	}
	
	public Credential getCredential() {
		return credential;
	}
	
}
