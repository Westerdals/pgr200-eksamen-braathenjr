package no.kristiania.pgr200.database;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private int port;
    private int actualPort;
    private ConferenceTalk talk;

    public HttpServer(int port) {
        this.port = port;
    }
    
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        this.actualPort = serverSocket.getLocalPort();
        new Thread(() ->  serverThread(serverSocket)).start();
    }

    public void serverThread(ServerSocket serverSocket) {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();

                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();

                String line = readNextLine(input);
                                
                String uri = line.split(" ")[1];
                
                HttpPath path = new HttpPath(uri);
                
                while (!line.isEmpty()) {
                	//spam
                    //System.out.println(line);
                    line = readNextLine(input);
                }
                
                
                String body = "Hello world!\r\n";
//                path.getPath().equals("/api/talks");
                
//                get -> /api/talks -> returner en liste med talks i body.
//                new HttpQuery().getParameter("title")
//                new ConferenceTalkDao(null).insertTalk(talk);
//                
//                String body = new Program().processArgument(args);
//                
//                switch(args) { "list" : return listTalks(); }

                boolean hasQuery = path.getQuery() == null ? false : true;
                
                if(hasQuery && path.getQuery().getParameter("body") != null) {
                	body = path.getQuery().getParameter("body");
                }

                if(hasQuery && path.getQuery().getParameter("status") != null) {
    				output.write(("HTTP/1.1 " + path.getQuery().getParameter("status")+ " Ok\r\n").getBytes());
                }else {
    				output.write(("HTTP/1.1 " + "200" + " Ok\r\n").getBytes());
                }
                output.write("X-Server-Name: Kristiania Web Server\r\n".getBytes());
                output.write("Connection: close\r\n".getBytes());
                if(hasQuery && path.getQuery().getParameter("Location") != null) {
    				output.write(("Location: " + path.getQuery().getParameter("Location")+ "\r\n").getBytes());
                }
                output.write("Content-Type: text/plain\r\n".getBytes());
                output.write(("Content-Length: " + body.length() + "\r\n").getBytes());
                output.write("\r\n".getBytes());
                output.write(body.getBytes());
                

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readNextLine(InputStream input) throws IOException {
        StringBuilder nextLine = new StringBuilder();
        int c;
        while ((c = input.read()) != -1) {
            if (c == '\r') {
            	input.mark(1);
                input.read();
                break;
            }
            nextLine.append((char) c);
        }
        return nextLine.toString();
    }

    public int getPort() {
        return actualPort;
    }
}
