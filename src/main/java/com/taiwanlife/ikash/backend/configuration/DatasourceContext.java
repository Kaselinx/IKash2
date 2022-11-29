package com.taiwanlife.ikash.backend.configuration;

public class DatasourceContext {
	  private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
	    public static void setDatasource(String key){
	        threadLocal.set(key);
	    }
	    public static String getDatasource(){
	        return threadLocal.get();
	    }
}
