package no.kristiania.pgr200.database;

import java.util.Objects;

public class ConferenceTalk {

	private String title, description;
	private long id;

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


	public void setId(long id) {
		this.id = id;

	}
	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return  " ID: " + id + " | " + " title: " + title + " | " + "  description: " + description + "\n";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ConferenceTalk)) {
			return false;
		}
		ConferenceTalk other = (ConferenceTalk) o;
		return Objects.equals(title, other.title) && Objects.equals(description, other.description);
				
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, description);
	}

}
