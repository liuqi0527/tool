package game.tool.xls.field;

import java.util.List;

import jxl.Sheet;

public interface FieldReader {

	public List<PropertyField> readField(Sheet sheet, Class clazz);
	
	public int getDataRowIndex();
}
