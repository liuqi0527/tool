package game.tool.xls.impl;

import java.util.ArrayList;
import java.util.List;

import game.tool.xls.field.FieldReader;
import game.tool.xls.field.PropertyField;
import jxl.Cell;
import jxl.Sheet;

public class SimpleFieldReader implements FieldReader {
	private int dataRowIndex = 2;
	
	@Override
	public List<PropertyField> readField(Sheet sheet, Class clazz) {
		List<PropertyField> result = new ArrayList<>();
		try{
			for(int column = 0; column <= sheet.getColumns(); column++){
				Cell cell = sheet.getCell(column, 0);
				SimplePropertyField field = new SimplePropertyField(cell.getContents(), clazz);
				result.add(field);
			}
		}catch(Exception e){
			
		}
		return result;
	}

	@Override
	public int getDataRowIndex() {
		return dataRowIndex;
	}

}
