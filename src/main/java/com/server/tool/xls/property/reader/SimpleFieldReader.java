package com.server.tool.xls.property.reader;

import java.util.ArrayList;
import java.util.List;

import com.server.tool.xls.property.field.EmptyPropertyField;
import com.server.tool.xls.property.field.impl.SimplePropertyField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.server.tool.util.Util;
import com.server.tool.xls.property.field.PropertyField;
import jxl.Cell;
import jxl.Sheet;

/**
 *第一行字段名<br>
 *第二行注释<br>
 *第三行开始是实际数据
 */
public class SimpleFieldReader implements FieldReader {
	private final static Logger logger = LoggerFactory.getLogger(SimpleFieldReader.class);
	
	@Override
	public List<PropertyField> readField(Sheet sheet, Class clazz) {
		List<PropertyField> result = new ArrayList<>();
		try{
			for(int column = 0; column < sheet.getColumns(); column++){
				Cell cell = sheet.getCell(column, 0);
				if(cell == null || Util.isEmpty(cell.getContents())){
					logger.error("sheet={}, column={}, name is not define ", sheet.getName(), column);
					result.add(EmptyPropertyField.instance());
					continue;
				}
				
				SimplePropertyField field = new SimplePropertyField(cell.getContents(), clazz);
				result.add(field);
			}
		}catch(Exception e){
			
		}
		return result;
	}

	
	@Override
	public int getDataRowIndex() {
		return 2;
	}

}
