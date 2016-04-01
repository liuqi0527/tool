package game.tool.xls.property.field;

import game.tool.clazz.ReflectUtils;
import jxl.Cell;

public abstract class AbstractPropertyField implements PropertyField {
	protected String fieldName;

	public AbstractPropertyField() { }
	
	@Override
	public void setValue(Object instance, Cell cell) {
		String content = cell.getContents();
		Object value = parser(content);

		// 解析出来null，则不设置值，使用默认零值
		if (value == null) {
			return;
		}
		ReflectUtils.setFieldValue(instance, fieldName, value);
	}

	abstract protected Object parser(String value);
}
