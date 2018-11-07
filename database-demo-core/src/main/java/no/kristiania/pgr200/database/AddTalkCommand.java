package no.kristiania.pgr200.database;

public class AddTalkCommand implements Command{
	private String title;
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HttpPostRequest createRequest() {
		return null;
	}

	public HttpPostRequest createRequest(String hostname, int port) {
		return new HttpPostRequest(hostname, port, "/api/talk");
		
	}
}
