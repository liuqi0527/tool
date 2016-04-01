package game.tool.xls.property.field.impl;

public class TypeDefPropertyField extends SimplePropertyField {
	private String type;
	
	
	public TypeDefPropertyField(String fieldName, String type){
		this.fieldName = fieldName;
		this.type = type;
	}
	
	@Override
	protected Object parser(String value) {
		return resolve(type, value);
	}

}
