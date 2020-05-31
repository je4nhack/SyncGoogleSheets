package com.je4npier.google.sheet.sync.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.je4npier.google.sheet.sync.dao.GoogleSheetsDao;

public class GoogleSheetsController extends ControllerWriteLog{
	
	private GoogleSheetsDao gsDao;
	
	public GoogleSheetsController(String spreadsheetId, String sheetName, int numRow, String columnName) throws GeneralSecurityException, IOException {
		
		Credential credential = GoogleCredentialsController.getCredentialsGoogleShets();
		gsDao = new GoogleSheetsDao(
				credential,
        		spreadsheetId,
        		sheetName,
        		numRow,
        		columnName);
	}
    
    public List<List<Object>> getPreviewData() throws IOException{        
        return gsDao.readRows(10);
    }
    
    public void truncateSheet(int numCommit){
    	try {
    		int numRowsToDelete = gsDao.getNumRowsGrid() - gsDao.getHeaderRow();
            
            addLog("Inicio de truncado");
            
            if(numRowsToDelete > 0) {
            	int numIterate = numRowsToDelete / numCommit;            
                int numRowsRemaining = numRowsToDelete % numCommit;
                
                for(int i = 0; i < numIterate; i++) {
                	gsDao.deleteRangeRows(1, numCommit);
                	int percent = 100 * (i + 1) / (numIterate + 1);
                	addLog(percent + "% eliminado");
                }
                if(numRowsRemaining > 0) gsDao.deleteRangeRows(1, numRowsRemaining);
                addLog("100% eliminado");
            }else {
            	addLog("Sin filas que eliminar");
            }
            
            addLog("Truncado finalizado con éxito");
		} catch (Exception e) {
			addLog("Error en el truncado, el detalle: " + e.getMessage());
		}
    }
    
    public void appendDummy() throws IOException{
    	gsDao.appendRowDummy("DUMMY");
    }
	
    public void appendDataBaseRows() throws IOException{
    	gsDao.appendRowDummy("DUMMY");
    }
    
    public GoogleSheetsDao getGoogleSheetsDao() {
    	return gsDao;
    }
}
