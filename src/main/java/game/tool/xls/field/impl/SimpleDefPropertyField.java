package game.tool.xls.field.impl;

public class SimpleDefPropertyField extends SimplePropertyField {
	private String type;
	
	
	public SimpleDefPropertyField(String fieldName, String type){
		this.fieldName = fieldName;
		this.type = type;
	}
	
	@Override
	protected Object parser(String value) {
		return resolve(type, value);
	}

}
