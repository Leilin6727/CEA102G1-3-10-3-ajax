package jdbc.util.CompositeQuery;

import java.util.Map;
import java.util.Set;


public class jdbcUtil_CompositeQuery_Art {
	

	public static String get_aCondition_From_Art(String columnName, String value) {
		//宣告一空字串指令
		String aCondition = null;
		//串接指令
		if("artTitle".equals(columnName)) {			
			aCondition = "ART_TITLE" + " like " + "'%" + value + "%'";
		}else if ("artTime".equals(columnName)) {
			aCondition = "ART_TIME" + " like " + "'" + value + "%'";
		}else if ("artAuthor".equals(columnName)) {
			aCondition = "MEM_NAME like "+"'%"+ value +"%'";
		}else if ("artMovType".equals(columnName)) {
			aCondition = "MOV_TYPE = "+"'"+ value +"'";
		}
		
		return aCondition + " ";
	}
	
	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			System.out.println("key:"+key);
			System.out.println("value:"+value);
			if(value != null && value.trim().length() != 0 && !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_From_Art(key, value.trim());
				
				whereCondition.append("and " + aCondition);
			
			System.out.println("查詢欄位數：" + count);
			}
		}
		
		return whereCondition.toString();
	}
	
}
