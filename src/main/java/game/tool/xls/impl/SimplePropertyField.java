package game.tool.xls.impl;

import java.beans.PropertyDescriptor;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import game.tool.xls.field.AbstractPropertyField;

public class SimplePropertyField extends AbstractPropertyField {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Class clazz;

	public SimplePropertyField(String fieldName, Class clazz) {
		this.fieldName = fieldName;
		this.clazz = clazz;
	}

	@Override
	protected Object parser(String value) {
		try {
			PropertyDescriptor prop = new PropertyDescriptor(fieldName, clazz);
			String type = prop.getPropertyType().getSimpleName();
			if (value.equals("") && !type.equals("String")) {
				return null;
			}
			
			switch(type){
				case "String"	: return value;
				case "int"		: return Integer.valueOf(value);
				case "boolean"	: return boolValue(value);
				case "Date"		: return dateValue(value);
				case "byte"		: return Byte.valueOf(value);
				case "short"	: return Short.valueOf(value);
				case "long"		: return Long.valueOf(value);
				case "double"	: return Double.valueOf(value);
				case "float"	: return Float.valueOf(value);
				default 		: return value;
			}
		} catch (Exception ex) {
			return null;
		}
	}
	
	private Date dateValue(String value) throws ParseException{
		return dateFormat.parse(value);
	}
	
	private Boolean boolValue(String value){
		return value.trim().equalsIgnoreCase("1") || value.trim().equalsIgnoreCase("true");
	}

}
