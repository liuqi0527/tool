package game.tool.xls.field;

import game.tool.clazz.ReflectUtils;
import jxl.Cell;

public abstract class AbstractPropertyField implements PropertyField {
	protected String fieldName;
	
	public void setValue(Object instance, Cell cell){
		String content = cell.getContents();
		setFieldValue(instance, parser(content));
	}
	
	abstract protected Object parser(String value);
	
	protected void setFieldValue(Object instance, Object value){
		//解析出来null，则不设置值，使用默认零值
		if(value == null){
			return;
		}
		
		try {
			ReflectUtils.setFieldValue(instance, getFieldName(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
