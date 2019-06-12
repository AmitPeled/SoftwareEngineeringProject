package maps;

import java.io.Serializable;

public class Site implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String descriptionString;
	private String name;
	private String siteType;
	private boolean isAccessibleForDisabled;
	private Coordinates locationCoordinates;

	public Site(int id, String name, String description, String type, Boolean isAccessibleForDisabled,
			Coordinates locationCoordinates) {
		if (id <= 0)
			throw new IllegalArgumentException("id has to be a positive number");
		this.id = id;
		this.name = name;
		this.descriptionString = description;
		this.setSiteType(type);
		this.setAccessibleForDisabled(isAccessibleForDisabled);
		this.locationCoordinates = locationCoordinates;
	}

	/**
	 * default no description string, type and false handicappedAccess
	 */
	public Site(int id, String name, Coordinates locationCoordinates) {
		this(id, name, null, null, false, locationCoordinates);
	}

	public Site(String name, String description, String type, Boolean isAccessibleForDisabled,
			Coordinates locationCoordinates) {
		this.id = -1;
		this.name = name;
		this.descriptionString = description;
		this.locationCoordinates = locationCoordinates;
		this.isAccessibleForDisabled = isAccessibleForDisabled;
		this.siteType = type;

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

	public boolean isAccessibleForDisabled() {
		return isAccessibleForDisabled;
	}

	public void setAccessibleForDisabled(boolean isAccessibleForDisabled) {
		this.isAccessibleForDisabled = isAccessibleForDisabled;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}
}
