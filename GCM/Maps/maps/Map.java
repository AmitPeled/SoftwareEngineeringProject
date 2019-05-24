package maps;

/**
 * Contains all the information that describes a map in the client application domain.
 *
 */

public final class Map {
	
	private int id;
	
	/**
	 * Creates a new Map instance
	 * @param id The ID number associated with this map. Used for identification in the database 
	 *  and helps to find the map image files
	 * @throws IllegalArgumentException Thrown when id is not a positive number
	 */
	public Map(int id) throws IllegalArgumentException {
		if(id <= 0) throw new IllegalArgumentException("id has to be a positive number");
		
		this.id = id;
	}
	
	public int getId() { return id; }
}
