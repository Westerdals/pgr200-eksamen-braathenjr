package no.kristiania.pgr200.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

public class ConferenceTalkDao {

	private DataSource dataSource;

	public ConferenceTalkDao(DataSource dataSource) {
		this.dataSource = dataSource;

	}

	public void createTableIfNotExists() throws SQLException {
		try (Connection conn = dataSource.getConnection()) {
			conn.createStatement().executeUpdate(
					"create table if not exists CONFERENCE_TALK (TITLE varchar not null, DESCRIPTION text)");
		}
	}

	public void insertTalk(ConferenceTalk talk) throws SQLException {

		try (Connection conn = dataSource.getConnection()) {

			String sql = "insert into CONFERENCE_TALK (TITLE, DESCRIPTION) values (?, ?)";

			try (PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, talk.getTitle());
				statement.setString(2, talk.getDescription());

				statement.executeUpdate();

				try (ResultSet rs = statement.getGeneratedKeys()) {
					rs.next();

					talk.setId(rs.getLong(1));
				}

			}

		}

	}

	public List<ConferenceTalk> listAll() throws SQLException {
		try (Connection conn = dataSource.getConnection()) {

			String sql = "select * from conference_talk";

			try (PreparedStatement statement = conn.prepareStatement(sql)) {
				try (ResultSet rs = statement.executeQuery()) {
					List<ConferenceTalk> result = new ArrayList<>();
					while (rs.next()) {
						ConferenceTalk talk = new ConferenceTalk();
						talk.setId(rs.getLong("id"));
						talk.setTitle(rs.getString("title"));
						talk.setDescription(rs.getString("description"));

						result.add(talk);
					}
					return result;
				}
			}
		}
	}

	public List<ConferenceTalk> listSpecificTalk() throws SQLException {
		ConferenceTalk talk = new ConferenceTalk();
		Scanner sc = new Scanner(System.in);
		try (Connection conn = dataSource.getConnection()) {

			System.out.println("What is the TITLE of the conference you are looking for?");
			String talkTitle2 = sc.nextLine().toLowerCase();
			//hvis den ikke finnes, print ut error message

			String sql = "select * from conference_talk where title = ?";

			try (PreparedStatement statement = conn.prepareStatement(sql)) {
				statement.setString(1, talkTitle2);
				try (ResultSet rs = statement.executeQuery()) {
					List<ConferenceTalk> result = new ArrayList<>();
					while (rs.next()) {
						talk.setTitle(rs.getString("title"));
						talk.setId(rs.getLong("id"));
						talk.setDescription(rs.getString("description"));

						result.add(talk);
					}
					sc.close();
					return result;
				}
			}
		}

	}
	
	

	/*
	 * public void deleteTalk() throws SQLException { try(Connection conn =
	 * dataSource.getConnection()) {
	 * 
	 * String sql = "delete from CONFERENCE_TALK where blablabla" } }
	 */
}