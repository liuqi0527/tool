package com.server.tool.util;

import java.util.Collection;
import java.util.Map;

public class Util {

	public static boolean isEmpty(Collection collection){
		return collection == null || collection.isEmpty();
	}
	
	public static boolean isEmpty(Map map){
		return map == null || map.isEmpty();
	}
	
	public static boolean isEmpty(String str){
		return str == null || str.isEmpty();
	}
}
