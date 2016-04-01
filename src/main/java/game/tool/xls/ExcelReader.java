package game.tool.xls;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import game.tool.xls.property.field.PropertyField;
import game.tool.xls.property.reader.FieldReader;
import game.tool.xls.property.reader.TypeDefFieldReader;
import game.tool.xls.template.ArmyTitleTemplate;
import game.tool.xls.template.KeySupport;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelReader {
	private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);
	private static FieldReader fieldReader = new TypeDefFieldReader();
	
	public static void main(String[] args){
		ExcelReader reader = new ExcelReader();
		Map<Integer, ArmyTitleTemplate> maps = reader.readToMap("E:/workspace/tool/src/main/java/game/tool/xls/template/army-title.xls", "title", ArmyTitleTemplate.class);
		for(ArmyTitleTemplate temp : maps.values()){
			System.out.println(temp.getId_i() + ": " + JSON.toJSONString(temp));
		}
		
//		List<ArmyTitleTemplate> list = reader.readToList("E:/workspace/tool/src/main/java/game/tool/xls/template/army-title.xls", "title", ArmyTitleTemplate.class);
//		for(ArmyTitleTemplate temp : list){
//			System.out.println(temp.getId_i() + ": " + JSON.toJSONString(temp));
//		}
	}

	
	public <K, V extends KeySupport<K>> Map<K, V> readToMap(String fileName, String sheetName, Class<V> clazz) {
		Map<K, V> result = new HashMap<>();
		readFile(fileName, sheetName, clazz, value -> result.put(value.getKey(), value));
		return result;
	}
	

	public <V> List<V> readToList(String fileName, String sheetName, Class<V> clazz) {
		List<V> result = new ArrayList<>();
		readFile(fileName, sheetName, clazz, value -> result.add(value));
		return result;
	}
	
	
	private <V> void readFile(String fileName, String sheetName,Class<V> clazz, Consumer<V> consumer){
		Workbook book = null;
		int column = 0;
		int row = 0;
		try {
			book = Workbook.getWorkbook(new File(fileName));
			Sheet sheet = book.getSheet(sheetName);
			
			List<PropertyField> fields = fieldReader.readField(sheet, clazz);
			for (row = fieldReader.getDataRowIndex(); row < sheet.getRows(); row++) {
				V instance = clazz.newInstance();
				for (column = 0; column < sheet.getColumns(); column++) {
					Cell cell = sheet.getCell(column, row);
					PropertyField field = fields.get(column);
					field.setValue(instance, cell);
				}
				consumer.accept(instance);
			}
		} catch (Exception e) {
			logger.error(String.format("read excel(%s, %s)-(%dr, %dc)error", fileName, sheetName, row, column), e);
		} finally {
			if (book != null) {
				book.close();
			}
		}
	}
	
	
}
