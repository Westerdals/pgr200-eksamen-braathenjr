package no.kristiania.pgr200.database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGPoolingDataSource;

public class DatabaseMain {

	private DataSource dataSource;
	private ConferenceTalkDao talkDao;
	private HttpRequest req;

	public DatabaseMain() throws SQLException, IOException {
		this.dataSource = createDataSource();
		this.talkDao = new ConferenceTalkDao(dataSource);
		this.talkDao.createTableIfNotExists();

	}

	public static DataSource createDataSource() throws FileNotFoundException, IOException {

		Properties prop = new Properties();
		try (FileReader reader = new FileReader("innlevering.properties")) {
			prop.load(reader);
		}

		String jdbcUrl = prop.getProperty("url");
		String user = prop.getProperty("user");
		String pwd = prop.getProperty("password");

		PGPoolingDataSource dataSource = new PGPoolingDataSource();
		dataSource.setUrl(jdbcUrl);
		dataSource.setUser(user);
		dataSource.setPassword(pwd);

		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.migrate();

		return dataSource;
	}

	public static void main(String[] args) throws SQLException, IOException {
		 HttpServer server = new HttpServer(10080);
	        server.start();
	        new DatabaseMain().run(args);

	}

	public void run(String[] args) throws SQLException {

		if (args.length == 0) {
			System.out.println("Here is a list of the arguments you can run as");
			listCommands();
			System.exit(1);
		}

		String command = args[0];

		if (command.equals("add")) {
			ConferenceTalk talk = userInput();
			userTalk(talk);

		} else if (command.equals("list")) {
			System.out.println(listTalks());

		} else if (command.equals("show")) {
			System.out.println(showTalk());

		} else if (command.equals("update")) {
			talkUpdate();
			
//		} else if (command.equals("delete")) {
//			talkDelete();

		} else if (command.equals("clear")) {
			Flyway flyway = new Flyway();
			flyway.setDataSource(dataSource);
			flyway.clean();
			System.out.println("Conferences successfully deleted!");
		} else {
			System.err.println("Unknown command");
		}

	}

//	private void talkDelete() {
//		// TODO Auto-generated method stub
//		
//	}

	private void listCommands() {
		System.out.println("add \n, list \n, show \n, update \n, clear");

	}

	private List<ConferenceTalk> showTalk() throws SQLException {
		return talkDao.listSpecificTalk();

	}

	public List<ConferenceTalk> listTalks() throws SQLException {
		return talkDao.listAll();
	}

	private void userTalk(ConferenceTalk talk) throws SQLException {
		talkDao.insertTalk(talk);
	}

	public ConferenceTalk userInput() {
		Scanner sc = new Scanner(System.in);
		ConferenceTalk talk = new ConferenceTalk();

		System.out.println("ENTER CONFERENCE TITLE");
		String talkTitle = sc.nextLine().toLowerCase();
		talk.setTitle(talkTitle);

		System.out.println("ENTER CONFERENCE DESCRIPTION");
		String talkDesc = sc.nextLine().toLowerCase();
		talk.setDescription(talkDesc);

//		System.out.println("ENTER CONFERENCE TOPIC");
//		String talkTopic = sc.nextLine().toLowerCase();
//		talk.setTopic(talkTopic);

		System.out.println("Your conference has been successfully added");
		sc.close();
		return talk;
	}

	public void talkUpdate() { // implementer metodene
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the ID of the conference you would like to update");
		String idResp = sc.nextLine();
		System.out.println("Do you want to update the title, description or topic?");
		String response = sc.nextLine().toLowerCase();
		switch (response) {
		case "title":
			System.out.println("title updated!");
			break;
		case "description":
			System.out.println("description updated!");
			break;
		case "topic":
			System.out.println("topic updated!");
			break;
		default:
			System.out.println("that column does not exist");
		}
		sc.close();
	}

}
