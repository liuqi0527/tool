package game.tool.xls.property.field.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import game.tool.xls.property.field.AbstractPropertyField;

/**
 *根据模板类推断类型 <br/>
 *不支持复杂类型
 */
public class SimplePropertyField extends AbstractPropertyField {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Class clazz;

	public SimplePropertyField() { }
	public SimplePropertyField(String fieldName, Class clazz) {
		this.initField(fieldName, null, clazz);
	}
	
	@Override
	public void initField(String fieldName, String fieldType, Class clazz) {
		this.fieldName = fieldName;
		this.clazz = clazz;
	}

	@Override
	protected Object parser(String value) {
		try {
//			PropertyDescriptor prop = new PropertyDescriptor(fieldName, clazz);
//		    String type = prop.getPropertyType().getSimpleName();
			Field field = clazz.getField(fieldName);
			String type = field.getType().getSimpleName();
			if (value.equals("") && !type.equals("String")) {
				return null;
			}
			
			return resolve(type, value);
		} catch (Exception ex) {
			return null;
		}
	}
	
	protected Object resolve(String type, String value){
		switch(type){
			case "string"	: return value;
			case "int"		: return Integer.valueOf(value);
			case "boolean"	: return boolValue(value);
			case "date"		: return dateValue(value);
			case "byte"		: return Byte.valueOf(value);
			case "short"	: return Short.valueOf(value);
			case "long"		: return Long.valueOf(value);
			case "double"	: return Double.valueOf(value);
			case "float"	: return Float.valueOf(value);
			default 		: return value;
		}
	}
	
	private Date dateValue(String value) {
		try{
			return dateFormat.parse(value);
		}catch(Exception e){
			return null;
		}
	}
	
	private Boolean boolValue(String value){
		return value.trim().equalsIgnoreCase("1") || value.trim().equalsIgnoreCase("true");
	}
}
