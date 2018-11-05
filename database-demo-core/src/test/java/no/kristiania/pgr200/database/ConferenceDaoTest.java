package no.kristiania.pgr200.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.Random;

import org.junit.Test;

public class ConferenceDaoTest {

     @Test
	public void shouldInsertConferenceTalks() throws SQLException {
		ConferenceTalkDao talkDao = new ConferenceTalkDao(TestDataSource.createTestDataSource());
		
		talkDao.createTableIfNotExists();
		ConferenceTalk talk = sampleTalk();
		talkDao.insertTalk(talk);
		assertThat(talkDao.listAll()).contains(talk);
	}
	
//	@Test
//	public void shouldFindTalkById() throws SQLException {
//		ConferenceTalkDao talkDao = new ConferenceTalkDao(createTestDataSource());
//		
//		talkDao.createTableIfNotExists();
//		ConferenceTalk talk = sampleTalk();
//		talk
//		
//	}

	private ConferenceTalk sampleTalk() {
		ConferenceTalk talk = new ConferenceTalk();
		talk.setTitle(pickOne(new String[] { "JavaZone, MobileExpo, Blizzcon" }));
		talk.setDescription(pickOne(new String[] { "fun and games, learn how to not suck, for everyone" }));
		return talk;
	}

	private String pickOne(String[] alternatives) {
		return alternatives[random.nextInt(alternatives.length)];
	}

	private static Random random = new Random();

}
