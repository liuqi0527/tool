package com.server.tool.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localize {
	private static ResourceBundle resource;
	
	static {
		Locale locale = new Locale("zh", "CN");
		resource = ResourceBundle.getBundle("locale/locale", locale);
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(getString("", 18, "liuqi"));
	}
	
	public static String getString(String key){
		return resource.getString(key);
	}
	
	public static String getString(String key, Object... args){
		String value = resource.getString(key);
		if(Util.isEmpty(value)){
			return "";
		}
		
		return String.format(value, args);
	}
}
