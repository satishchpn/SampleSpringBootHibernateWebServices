/*
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
	public static boolean isValidJson(String jsonInString ) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(jsonInString);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static String convertObjectToJsonString(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return jsonString;
	}
	public static String convertObjectToPrettyJsonString(Object obj){
		if(obj==null)
			return null;
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonGenerationException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return jsonString;
	}
	
	@SuppressWarnings({"rawtypes","unchecked"})
	public static Object convertJsonStringToObject(String jsonString, Class cls) throws Exception{
		Object obj=null;
		if(!isValidJson(jsonString))
			return null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			obj= mapper.readValue(jsonString, cls);
			//	System.out.println(s);
			String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(s);
			System.out.println(prettyStaff1);

		} catch (JsonGenerationException e) {
			throw e;
		} catch (JsonMappingException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return obj;
	}
	
	public static  String convertMapToJsonString(Map<? extends Serializable,? extends Object> map){
		ObjectMapper objectMapper=new ObjectMapper();
	
		try {
			return objectMapper.writeValueAsString(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		HashMap<String, String> h=new HashMap<String, String>();
		h.put("fff", "ssfsfsf");
		System.out.println(convertMapToJsonString(h));
	}
}
*/