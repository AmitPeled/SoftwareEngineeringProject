package maps;

import java.io.Serializable;

public class Site implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String descriptionString;
	private String name;
	private Coordinates locationCoordinates;

	public Site(int id, String name, String description, Coordinates locationCoordinates) {
		if (id <= 0)
			throw new IllegalArgumentException("id has to be a positive number");
		this.id = id;
		this.name = name;
		this.descriptionString = description;
		this.locationCoordinates = locationCoordinates;
	}
	
	/**
	 * default no description string
	 */
	public Site(int id, String name, Coordinates locationCoordinates) {
		this(id, name, null, locationCoordinates);
	}

	/**
	 * Site ID getter
	 * 
	 * @return The unique site ID used to represent it in the Database
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Description setter
	 * 
	 * @param descriptionString the new description string
	 */
	public void setDescription(String descriptionString) {
		this.descriptionString = descriptionString;
	}

	/**
	 * Description getter
	 * 
	 * @return Site description string
	 */
	public String getDescription() {
		return this.descriptionString;
	}

	public String getName() {
		return this.name;
	}

	public Coordinates getCoordinates() {
		return this.locationCoordinates;
	}
}
