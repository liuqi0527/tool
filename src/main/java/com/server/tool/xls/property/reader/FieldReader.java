package com.server.tool.xls.property.reader;

import java.util.List;

import com.server.tool.xls.property.field.PropertyField;
import jxl.Sheet;

/**
 * 读取excel表头
 */
public interface FieldReader {

	/**
	 * 读取表头
	 * @param sheet
	 * @param clazz
	 * @return
	 */
	public List<PropertyField> readField(Sheet sheet, Class clazz);
	
	/**
	 * 数据开始的行数
	 * @return
	 */
	public int getDataRowIndex();
}
