package maps;

/**
 * Contains all the information that describes a map in the client application domain.
 *
 */

public final class Map {
	
	private int id;
	private float width;
	private float height;
	
	/**
	 * Creates a new Map instance
	 * @param id The ID number associated with this map. Used for identification in the database 
	 *  and helps to find the map image files
	 * @throws IllegalArgumentException Thrown when id, width or height are not positive numbers
	 */
	public Map(int id, float width, float height) throws IllegalArgumentException {
		if(id <= 0) throw new IllegalArgumentException("id has to be a positive number");
		if(width <= 0.0f) throw new IllegalArgumentException("width has to be a positive number");
		if(height <= 0.0f) throw new IllegalArgumentException("height has to be a positive number");
		
		this.id = id;
		this.width = width;
		this.height = height;
	}
	
	public int getId() { return id; }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
}
