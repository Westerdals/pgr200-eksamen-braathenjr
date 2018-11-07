package no.kristiania.pgr200.database;



import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpRequest {

	private String path;
	
	private Socket socket;
	private OutputStream outputStream;

	private String hostname;
	private HttpQuery query;
	
	public HttpRequest(String hostname,int port, String path) throws IOException {
		this.path = path;
		this.hostname = hostname;
		socket = new Socket(hostname, port);
		outputStream = socket.getOutputStream();
		
	}
	public HttpResponse execute() throws IOException{
			writeLine("GET " + path + " HTTP/1.1");
			writeLine(("Host: " + hostname));
			writeLine("Connection: close");
			writeLine("");
			outputStream.flush();
			
		
		return new HttpResponse(socket.getInputStream());
	}
//	public HttpResponse postExecute() throws IOException{
//		writeLine("POST " + path + " HTTP/1.1");
//		writeLine(("Host: " + hostname));
//		writeLine(("Content-Type: application/x-www-form-urlencoded\r\n"));
//		writeLine(("Content-Length: " + query.toString().length() + "\r\n"));
//		writeLine("Connection: close");
//		writeLine("");
//		outputStream.flush();
//		
//	
//	return new HttpResponse(socket.getInputStream());
//}
	
	public void writeLine(String line) throws IOException{
		outputStream.write((line + "\r\n").getBytes());
	}

}
