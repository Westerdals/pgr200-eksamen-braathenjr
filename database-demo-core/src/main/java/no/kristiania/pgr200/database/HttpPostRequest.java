package no.kristiania.pgr200.database;

import java.util.HashMap;
import java.util.Map;

public class HttpPostRequest {
	
	private String hostname;
	private int port;
	private String path;
	private HttpQuery formQuery = new HttpQuery();
	
	public HttpPostRequest(String hostname, int port, String path) {
		this.hostname = hostname;
		this.port = port;
		this.path = path;
	}

	public String getBody() {
		return formQuery.toString();
	}

	public String getRequestUrl() {
		
		return "http://" + hostname + ":" + port + path;
	}
	
	
	public HttpQuery getFormQuery() {
		return formQuery;
	}
	
	public Map<String, String> getHeaders(){
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Length", String.valueOf(getBody().length()));
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		return headers;
		
	}
}
