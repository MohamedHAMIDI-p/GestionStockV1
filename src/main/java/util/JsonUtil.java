package util;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JsonUtil {
	
	private static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
	
	private static final Gson GSON = new GsonBuilder()
			.disableHtmlEscaping()
			.setDateFormat(FORMAT_DATE)
			.create();
	
	private static final Gson GSON_PRETTY= new GsonBuilder()
			.setPrettyPrinting()
			.disableHtmlEscaping()
			.setDateFormat(FORMAT_DATE)
			.create();
	
	private JsonUtil() {
		
	}

	public static String toJson(Object objet) {
		return GSON.toJson(objet);
	}
	
	public static String toPrettyJson(Object objet) {
		return GSON_PRETTY.toJson(objet);
	}
	
	public static void repondreJson(HttpServletResponse resp,int status,String json) throws IOException
	{
		resp.setStatus(status);
		resp.setContentType("application/json;charset=UTF-8");
		resp.getWriter().write(json);
	}
	
	public static String erreurJson(String message,String codeErreur) {
		Map<String, Object> erreur= new LinkedHashMap<String, Object>();
		erreur.put("erreur", message);
		if(codeErreur !=null) {
			erreur.put("CodeErreur", codeErreur);
		}
		return JsonUtil.toPrettyJson(erreur);
	}
}
