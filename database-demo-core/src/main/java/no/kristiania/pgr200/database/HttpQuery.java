package no.kristiania.pgr200.database;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;




public class HttpQuery {

	private String query;
	private Map<String, String> parameters = new HashMap<>();

	public HttpQuery() {
		
	}
	public HttpQuery(String query) {
		this.query = query;
	}

	public HttpQuery addParameter(String name, String value) {
		if (value != null) {
		parameters.put(name, value);
		}
		return this;
	}
	
	public String getParameter(String paramName) {
		
		return parameters.get(paramName);
	}
	
	@Override
	public String toString() {
		if(query != null) {
			return query;
		}
		StringBuilder result = new StringBuilder();
		for (Entry<String, String> parameter : parameters.entrySet()) {
			if(result.length() > 0) {
				result.append("&");
			}
			result.append(urlEncode(parameter.getKey()) + "=" + urlEncode(parameter.getValue()));
		}
		return result.toString();
			
	}
	private String urlEncode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		}catch (UnsupportedEncodingException e) {
			throw new RuntimeException("skjer ikke", e);
		}	
	}
}