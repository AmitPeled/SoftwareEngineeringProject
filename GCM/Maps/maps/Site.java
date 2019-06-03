package maps;

import java.io.Serializable;

public class Site implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String descriptionString;
	private Coordinates locationCoordinates;

	public Site(int id, Coordinates locationCoordinates) {
		if (id < 0)
			throw new IllegalArgumentException("id");

		this.id = id;
		this.locationCoordinates = locationCoordinates;
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

	public Coordinates getCoordinates() {
		return this.locationCoordinates;
	}
}
