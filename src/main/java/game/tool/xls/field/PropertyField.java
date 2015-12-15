package game.tool.xls.field;

import jxl.Cell;

/**
 * 字段解析类
 */
public interface PropertyField {
	
	/**
	 * 初始化解析类
	 * @param fieldName  字段名字
	 * @param fieldType  字段类型
	 * @param clazz 	  模板类
	 */
	void initField(String fieldName, String fieldType,Class clazz);
	
	/**
	 * 解析一格数据，并赋值给实例对象
	 * @param instance
	 * @param cell
	 */
	void setValue(Object instance, Cell cell);
}
