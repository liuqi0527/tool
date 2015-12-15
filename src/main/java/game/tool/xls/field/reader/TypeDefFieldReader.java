package game.tool.xls.field.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.tool.util.Util;
import game.tool.xls.field.PropertyField;
import game.tool.xls.field.impl.EmptyPropertyField;
import game.tool.xls.field.impl.SimpleDefPropertyField;
import jxl.Cell;
import jxl.Sheet;

/**
 *第一行字段名<br>
 *第二行字段类型<br>
 *第三行注释<br>
 *第四行开始数据
 */
public class TypeDefFieldReader implements FieldReader {
	private final static Logger logger = LoggerFactory.getLogger(TypeDefFieldReader.class);
	private List<String> simpleTypes = Arrays.asList("string", "int", "boolean", "byte", "short", "short", "long", "double", "float", "date");

	@Override
	public List<PropertyField> readField(Sheet sheet, Class clazz) {
		List<PropertyField> result = new ArrayList<>();
		int column = 0;
		try{
			for(column = 0; column < sheet.getColumns(); column++){
				Cell nameCell = sheet.getCell(column, 0);
				Cell typeCell = sheet.getCell(column, 1);
				
				PropertyField field = EmptyPropertyField.instance();
				if(!check(nameCell, typeCell)){
					logger.error("sheet={}, column={}, name or type is not define ", sheet.getName(), column);
				}else if(simpleTypes.contains(typeCell.getContents())){
					field = getField(nameCell, typeCell, clazz);
				}
				result.add(field);
			}
		}catch(Exception e){
			logger.error("column=" + column, e);
		}
		return result;
	}
	
	private PropertyField getField(Cell nameCell, Cell typeCell, Class clazz){
		if(simpleTypes.contains(typeCell.getContents())){
			return new SimpleDefPropertyField(nameCell.getContents(), typeCell.getContents());
		}
		
		return EmptyPropertyField.instance();
	}

	private boolean check(Cell nameCell, Cell typeCell){
		if(nameCell == null || Util.isEmpty(nameCell.getContents())){
			return false; 
		}
		if(typeCell == null || Util.isEmpty(typeCell.getContents())){
			return false;
		}
		
		return true;
	}
	
	@Override
	public int getDataRowIndex() {
		return 3;
	}

}
