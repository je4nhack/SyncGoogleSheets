package com.je4npier.google.sheet.sync.controller;

public abstract class RunnableProcess implements Runnable {
	private ControllerWriteLog controllerWriteLog;
	
	public RunnableProcess(ControllerWriteLog controllerWriteLog) {
		this.controllerWriteLog = controllerWriteLog;
	}
	
	public RunnableProcess(ControllerWriteLog controllerWriteLog, WriteLog writeLog) {
		this.controllerWriteLog = controllerWriteLog;
		this.controllerWriteLog.setWriteLog(writeLog);
	}

	public void setWriteLog(WriteLog writeLog) {
		this.controllerWriteLog.setWriteLog(writeLog);
	}
}
