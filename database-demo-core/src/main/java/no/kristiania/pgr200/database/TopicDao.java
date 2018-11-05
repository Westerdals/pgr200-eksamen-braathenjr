package no.kristiania.pgr200.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.sql.DataSource;

public class TopicDao {

	private DataSource dataSource;

	public TopicDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void createTableIfNotExists() throws SQLException {
		try (Connection conn = dataSource.getConnection()) {
			conn.createStatement().executeUpdate(
					"create table if not exists TOPICS (TOPICTITLE varchar not null)");
		}
		
	}

	public void insertTopic(Topic topic) throws SQLException {
		try (Connection conn = dataSource.getConnection()) {

			String sql = "insert into TOPICS (TOPICTITLE) values (?)";

			try (PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, topic.getTopicTitle());
				statement.executeUpdate();

				try (ResultSet rs = statement.getGeneratedKeys()) {
					rs.next();

					topic.setTopicId(rs.getLong(1));

				}
			}
		}
	}

	public Topic retrieve(long id) throws SQLException {
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement statement = conn.prepareStatement("select * from topics where id = ?")) {
				statement.setLong(1, id);
				try (ResultSet rs = statement.executeQuery()) {
					if (rs.next()) {
						return mapToTopic(rs);

					}
					return null;
				}

			}
		}
	}

	private Topic mapToTopic(ResultSet rs) throws SQLException {
		Topic topic = new Topic();
		topic.setTopicId(rs.getLong("id"));
		topic.setTopicTitle(rs.getString("topicTitle"));
		return topic;
	}


}
