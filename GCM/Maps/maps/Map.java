package maps;

import java.io.Serializable;

/**
 * Contains all the information that describes a map in the client application
 * domain.
 *
 */

public final class Map implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private float width;
	private float height;
	private Coordinates offset;
//	private SortedSet<Integer> siteIds;

	/**
	 * Creates a new Map instance with no offset
	 * 
	 * @param id     The ID number associated with this map. Used for identification
	 *               in the database and helps to find the map image files
	 * @param width  The width size of the map in real-world meters
	 * @param height The height size of the map in real-world meters
	 * @throws IllegalArgumentException Thrown when id, width or height are not
	 *                                  positive numbers
	 */
	public Map(int id, float width, float height) throws IllegalArgumentException {
		// By default the offset (0,0)
		this(id, width, height, new Coordinates());
	}

	/**
	 * Creates a new Map instance with no offset
	 * 
	 * @param id     The ID number associated with this map. Used for identification
	 *               in the database and helps to find the map image files
	 * @param width  The width size of the map in real-world meters
	 * @param height The height size of the map in real-world meters
	 * @param offset The location of the top left pixel of the map compared to the
	 *               default which is zero (no offset)
	 * @throws IllegalArgumentException Thrown when id, width or height are not
	 *                                  positive numbers
	 */
	public Map(int id, float width, float height, Coordinates offset) throws IllegalArgumentException {
		if (id <= 0)
			throw new IllegalArgumentException("id has to be a positive number");
		if (width <= 0.0f)
			throw new IllegalArgumentException("width has to be a positive number");
		if (height <= 0.0f)
			throw new IllegalArgumentException("height has to be a positive number");

		this.id = id;
		this.width = width;
		this.height = height;
		this.offset = offset;
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
}
