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

}