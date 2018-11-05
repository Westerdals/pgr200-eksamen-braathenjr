package no.kristiania.pgr200.database;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class HttpResponse {

	private int statusCode;
	private InputStream inputStream;
	private String body;
	private HashMap<String, String> headers	= new HashMap<>();
	
	public HttpResponse(InputStream inputStream) throws IOException{
		this.inputStream = inputStream;
		String statusLine = readLine();
		
		String[] parts = statusLine.toString().split(" ");
		this.statusCode = Integer.parseInt(parts[1]);
		
		String headerLine;
		while((headerLine = readLine()) != null) {
			if(headerLine.isEmpty()) break;
			int colonPos = headerLine.indexOf(':');
			headers.put(headerLine.substring(0, colonPos).trim(),
					headerLine.substring(colonPos + 1).trim());
			}
		this.body = readLine();
	}
	
	public int getStatusCode() {

		return statusCode;
	}
	
	public String getBody() {

		return body;
	}

	public String getHeader(String headerName) {

		return headers.get(headerName);
	}
	public String readLine() throws IOException{
		StringBuilder line = new StringBuilder();
		
		int c;
		while ((c = inputStream.read()) != -1) {
			if(c == '\r') {
				c = inputStream.read();
				assert c == '\n';
				break;
			}
			line.append(((char)c));
		}
		return line.toString();
	}
	

}