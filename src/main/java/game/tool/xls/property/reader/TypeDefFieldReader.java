package game.tool.xls.property.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.tool.util.Util;
import game.tool.xls.property.field.EmptyPropertyField;
import game.tool.xls.property.field.PropertyField;
import game.tool.xls.property.field.impl.TypeDefPropertyField;
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
				if(isEmpty(nameCell) || isEmpty(typeCell)){
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
			return new TypeDefPropertyField(nameCell.getContents(), typeCell.getContents());
		}
		
		return EmptyPropertyField.instance();
	}

	private boolean isEmpty(Cell cell){
		return cell == null || Util.isEmpty(cell.getContents());
	}
	
	@Override
	public int getDataRowIndex() {
		return 3;
	}

}
