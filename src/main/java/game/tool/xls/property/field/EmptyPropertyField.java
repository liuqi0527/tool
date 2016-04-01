package game.tool.xls.property.field;

/**
 *空类型，不处理
 */
public class EmptyPropertyField extends AbstractPropertyField {
	private static EmptyPropertyField instance = new EmptyPropertyField();
	
	public static EmptyPropertyField instance(){
		return instance;
	}
	
	@Override
	public void initField(String fieldName, String fieldType, Class clazz) {
	}

	@Override
	protected Object parser(String value) {
		return null;
	}
}
