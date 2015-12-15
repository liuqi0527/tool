package game.tool.xls;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import game.tool.xls.field.FieldReader;
import game.tool.xls.field.PropertyField;
import game.tool.xls.impl.SimpleFieldReader;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelReader {
	private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);
	private static FieldReader fieldReader = new SimpleFieldReader();
	
	public static void main(String[] args){
		ExcelReader reader = new ExcelReader();
		Map<Integer, ArmyTitleTemplate> maps = reader.readToMap("E:/workspace/tool/src/main/java/game/tool/xls/army-title.xls", "title", ArmyTitleTemplate.class);
		for(ArmyTitleTemplate temp : maps.values()){
			System.out.println(JSON.toJSONString(temp));
		}
	}
	
	public <K, V extends KeySupport<K>> Map<K, V> readToMap(String fileName, String sheetName, Class<V> clazz) {
		Workbook book = null;
		Map<K, V> result = new HashMap<>();
		
		try {
			book = Workbook.getWorkbook(new File(fileName));
			Sheet sheet = book.getSheet(sheetName);
			
			List<PropertyField> fields = fieldReader.readField(sheet, clazz);
			for (int row = fieldReader.getDataRowIndex(); row < sheet.getRows(); row++) {
				V instance = clazz.newInstance();
				for (int column = 0; column < sheet.getColumns(); column++) {
					Cell cell = sheet.getCell(column, row);
					PropertyField field = fields.get(column);
					field.setValue(instance, cell);
				}
				result.put(instance.getKey(), instance);
			}
		} catch (Exception e) {
			logger.error(String.format("read excel(%s, %s) error", fileName, sheetName), e);
		} finally {
			if (book != null) {
				book.close();
			}
		}

		return result;
	}


	public <K, V> Map<K, V> readToList(String file, String sheet, Class<?> clazz) {
		return null;
	}
}
