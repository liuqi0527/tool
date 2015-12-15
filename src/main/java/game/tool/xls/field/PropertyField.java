package game.tool.xls.field;

import jxl.Cell;

public interface PropertyField {
	
	void setFieldName(String name);
	
	String getFieldName();
	
	void setValue(Object instance, Cell cell);
}
