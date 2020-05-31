package com.je4npier.google.sheet.sync.controller;

public class ControllerWriteLog {
	private WriteLog writeLog = null;
	
	protected void addLog(String log) {
		if(writeLog == null) System.out.println(log);
		else writeLog.addLog(log);
	}

	public void setWriteLog(WriteLog writeLog) {
		this.writeLog = writeLog;
	}

	public WriteLog getWriteLog() {
		return writeLog;
	}
}
