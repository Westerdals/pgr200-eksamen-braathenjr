package no.kristiania.pgr200.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

public class HttpClientTest {

    @Test
    public void shouldExecuteRequest() throws Exception {
        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldReadOtherStatusCodes() throws IOException {
        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, 
        		"/echo?" + new HttpQuery().addParameter("status", "404"));
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(404);
    }
    
    @Test
    public void shouldReadBody() throws IOException {
        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, 
        		"/echo?" + new HttpQuery().addParameter("body", "Hello World"));
        HttpResponse response = request.execute();

        assertThat(response.getBody())
        .isEqualTo("Hello World");
    }
	
    @Test
    public void shouldReadResponseHeaders() throws IOException {
        HttpQuery query = new HttpQuery()
        		.addParameter("status", "307")
        		.addParameter("Location", "http://www.google.com");       
        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, 
        		"/echo?" + query);
        
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(307);
        assertThat(response.getHeader("Location")).isEqualTo("http://www.google.com");
    }
    
    
    
    
	@Test
	public void shouldRequiredArgumentsFromCommandLine() {
		String[] args = new String[] {
				"add", "-title", "Hello World", "-description", "How are you doing"
		};
		AddTalkCommand expectedCommand = new AddTalkCommand();
		expectedCommand.setTitle("Hello World");
		expectedCommand.setDescription("How are you doing");
		
		Program program = new Program();
		assertThat(expectedCommand)
			.isEqualToComparingFieldByField(program.parseCommand(args));
	}
//	@Test
//	public void shouldGenerateHttpRequestForAddTalkCommand() {
//		String[] args = new String[] {
//				"add", "-title", "Hello World", "-description", "How are you doing"
//		};
//		AddTalkCommand expectedCommand = new AddTalkCommand();
//		expectedCommand.setTitle("Hello World");
//		expectedCommand.setDescription("How are you doing");
//		HttpPostRequest request = expectedCommand.createRequest("localhost", 8080);
//		assertThat(request.getRequestUrl())
//		.isEqualTo("http://localhost:8080/api/talk");
//		assertThat(request.getBody())
//		.isEqualTo("title=Hello+World&description=How+are+you+doing");
//	}

}