package com.je4npier.google.sheet.sync.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;
import com.je4npier.google.sheet.sync.model.Parameters;

public class ParametersController {
	public static final String INIT_FILE_NAME = "/init.json";
	
	public static Parameters readParameters(String url) throws FileNotFoundException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(url));
		Parameters parameters = new Gson().fromJson(bufferedReader, Parameters.class);
		
		return parameters;
	}
	
	public static void saveParameters(Parameters parameters, String url) throws IOException {
		Writer writer = new FileWriter(url);
		new Gson().toJson(parameters, writer);
		
		writer.close();
	}

}
