package com.je4npier.google.sheet.sync.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private Connection connection = null;
    private String driver;
    private String url;
    private String user;
    private String password;
    
    public DBUtil(String driver, String url, String user, String password) throws Exception {
    	this.driver = driver;
    	this.url = url;
    	this.user = user;
    	this.password = password;
    	this.connection = this.newConnection();
    }
    
    public Connection getConnection() throws Exception {
    	if(!isConected()) connection = newConnection();
    	return connection;
    }
    
    public Connection newConnection() throws Exception {
    	Connection newConnection = null;
        
        Class.forName(this.driver);
        
        if (this.user == null || this.user.isEmpty()){
        	newConnection = DriverManager.getConnection(this.url);
        }else{
        	newConnection = DriverManager.getConnection(this.url, this.user, this.password);
        }
        
        return newConnection;
    }
    
    public void closeConnection() {
    	try {
    		this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public boolean isConected() {
    	boolean conected = false;
		try {
			conected = this.connection != null && !this.connection.isClosed();
		} catch (Exception e) {
			System.out.println("No existe conexión disponible");
		}
    	return conected;
    }
}