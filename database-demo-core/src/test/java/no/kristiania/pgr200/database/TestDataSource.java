package no.kristiania.pgr200.database;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

public class TestDataSource {
	
	public static DataSource createTestDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
		dataSource.setUser("sa");

		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.migrate();

		return dataSource;
	}

}
