package maps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the information that describes a map in the client application
 * domain.
 *
 */

public final class Map implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String descriptionString;
	private float width;
	private float height;
	private Coordinates offset;
	/**
	 * map doesn't contain price. only city does
	 */
	private double price;
	private List<Site> mapSites;
	private List<Tour> mapTours;

	/**
	 * Creates a new Map instance with no offset, description, sites, tour and price
	 * 
	 * @param width  The width size of the map in real-world meters
	 * @param height The height size of the map in real-world meters
	 */
	public Map(float width, float height) {
		// By default the name and description are null, offset (0,0)
		this(null, null, width, height, new Coordinates());
	}

	public Map(String name, String description, float width, float height, Coordinates offset) {
		// default id -1
		this.id = -1;
		this.name = name;
		this.descriptionString = description;
		this.width = width;
		this.height = height;
		this.offset = offset;
		this.mapTours = new ArrayList<>();
		this.mapSites = new ArrayList<>();
	}

	/**
	 * Creates a new Map instance
	 * 
	 * @param id          The ID number associated with this map. Used for
	 *                    identification in the database and helps to find the map
	 *                    image files
	 * @param description The description string of the map
	 * @param width       The width size of the map in real-world meters
	 * @param height      The height size of the map in real-world meters
	 * @param offset      The location of the top left pixel of the map compared to
	 *                    the default which is zero (no offset)
	 * @throws IllegalArgumentException Thrown when id, width or height are not
	 *                                  positive numbers
	 */

	public Map(int id, String name, String description, float width, float height, Coordinates offset, double price,
			List<Site> sites, List<Tour> tours) throws IllegalArgumentException {
		if (id <= 0)
			throw new IllegalArgumentException("id has to be a positive number");
		if (width <= 0.0f)
			throw new IllegalArgumentException("width has to be a positive number");
		if (height <= 0.0f)
			throw new IllegalArgumentException("height has to be a positive number");

		this.id = id;
		this.name = name;
		this.descriptionString = description;
		this.width = width;
		this.height = height;
		this.offset = offset;
		this.price = price;
		this.mapSites = sites;
		this.mapTours = tours;
	}

	/**
	 * Map ID getter
	 * 
	 * @return The unique map ID used to represent it in the Database and when
	 *         looking for the image file
	 */
	public int getId() {
		return id;
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

	/**
	 * Width getter
	 * 
	 * @return The width in meters of the map
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Height getter
	 * 
	 * @return the height in meters of the map
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Map offset getter
	 * 
	 * @return the coordinates of the top left pixel of the map. By default this is
	 *         zero, but used to describe maps that don't completely overlap
	 */

	public Coordinates getOffset() {
		return offset;
	}

	public List<Site> getSites() {
		return mapSites;
	}

	public List<Site> setSites(List<Site> sites) {
		return sites;
	}

	public void addSite(Site site) {
		mapSites.add(site);
	}

	public List<Tour> getTours() {
		return mapTours;
	}

	public double getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addTour(Tour tour) {
		mapTours.add(tour);
	}
}
