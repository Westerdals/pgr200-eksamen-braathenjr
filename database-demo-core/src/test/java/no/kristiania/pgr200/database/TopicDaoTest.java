package no.kristiania.pgr200.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.Random;

import org.junit.Test;

public class TopicDaoTest {
	
	@Test
	public void shouldCreateTopic() throws SQLException {
		TopicDao topicDao = new TopicDao(TestDataSource.createTestDataSource());
		
		topicDao.createTableIfNotExists();
		Topic topic = sampleTopic();
		topicDao.insertTopic(topic);
		assertThat(topic).hasNoNullFieldsOrProperties();
		assertThat(topicDao.retrieve(topic.getTopicId())).isEqualToComparingFieldByField(topic);
		
	}

	private Topic sampleTopic() {
		Topic topic = new Topic();
		topic.setTopicTitle(randomTitle());
		return topic;
	}

	private String randomTitle() {
		return pickOne(new String[] {"Gaming", "Learning 101", "Business & Ecnomy"});
	}

	private String pickOne(String[] alternatives) {
		return alternatives[random.nextInt(alternatives.length)];
	}
	
	private static Random random = new Random();

}
