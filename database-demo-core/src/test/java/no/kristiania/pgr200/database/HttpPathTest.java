package no.kristiania.pgr200.database;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class HttpPathTest {

    @Test
    public void shouldSeparatePathAndQuery() throws UnsupportedEncodingException {
        HttpPath path = new HttpPath("/urlecho?status=200");
        assertThat(path.getPath()).isEqualTo("/urlecho");
        assertThat(path.getQuery().toString()).isEqualTo("status=200");
    }

    @Test
    public void shouldReturnQueryNullWhenNoQuery() throws UnsupportedEncodingException {
        HttpPath path = new HttpPath("/myfile");
        assertThat(path.getPath()).isEqualTo("/myfile");
        assertThat(path.getQuery()).isNull();
    }
    
    @Test
    public void shoudlReadParameter() throws UnsupportedEncodingException {
    	HttpPath path = new HttpPath("/urlecho?status=404&name=Martin");
    	assertThat(path.getParameter("status")).isEqualTo("404");
    	assertThat(path.getParameter("name")).isEqualTo("Martin");
    	
    	//   ?status=200&age=20      parameter1-  status:    200     parameter2-  age:   20
    }
    
    @Test
    public void shoudlReadParameterWithEncoding() throws UnsupportedEncodingException {
    	HttpPath path = new HttpPath("/urlecho?hostname=http%3A%2F%2Fvg.no");
    	assertThat(path.getParameter("hostname")).isEqualTo("http://vg.no");
    	
    }
    @Test
    public void shoudlReadParameterWithQuery() throws UnsupportedEncodingException {
    	HttpPath path = new HttpPath("/urlecho?hostname=http%3A%2F%2Fvg.no");
    	assertThat(new HttpQuery().addParameter("name", "martin").toString()).isEqualTo("name=martin");
    	assertThat(path.getParameter("hostname")).isEqualTo("http://vg.no");
    	
    }

}