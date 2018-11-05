package no.kristiania.pgr200.database;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HttpPath {

	public String path;

	private HttpQuery query;
	
	public HttpPath(String uri) throws UnsupportedEncodingException {
		if(getQuestPos(uri) == -1){
			path = uri;
		}else {
			this.path = uri.substring(0, getQuestPos(uri));
			this.query = new HttpQuery(uri.substring(getQuestPos(uri) +1));
			
			String[] parameterPairs = query.toString().split("&");
			for(String pair : parameterPairs) {  
				query.addParameter(pair.substring(0, getEqualsPos(pair)),  URLDecoder.decode(pair.substring(getEqualsPos(pair) +1), "UTF-8"));
			}
			
		}
	}

	private int getEqualsPos(String pair) {
		return pair.indexOf("=");
	}

	public String getPath() {
		return path;
	}


	public HttpQuery getQuery() {
		if(query == null) return null;
		return query;
	}
	
	public String getQueryString() {
		if(query == null) return null;
		return query.toString();
	}
	
	private int getQuestPos(String uri) {
		return uri.indexOf("?");
	}

	public String getParameter(String paramName) {
		return query.getParameter(paramName);
	}


}