package no.kristiania.pgr200.database;

public class Topic {

	private String topicTitle;
	private long topicId;

	public long getTopicId() {
		return topicId;
	}
	
	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;

	}

	public String getTopicTitle() {
		return topicTitle;
	}

}
