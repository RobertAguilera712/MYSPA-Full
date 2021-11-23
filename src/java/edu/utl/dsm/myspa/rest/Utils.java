package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;

public class Utils {
	public final static Gson GSON = new Gson();
	public final static String JSON = "{\"%s\": \"%s\"}";
	public final static String ERROR = String.format(JSON, "error", "Hubo un error en la %s %s");
}
