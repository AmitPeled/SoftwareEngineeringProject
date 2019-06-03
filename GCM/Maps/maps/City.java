/**
 * 
 */
package maps;

import java.io.Serializable;

//import java.util.SortedSet;

/**
 * Contains references (by ID) to the maps and sites that are associated to the City
 *
 */
public final class City implements  Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	//private SortedSet<Integer> maps;
	//private SortedSet<Integer> sites
	
	public City(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() { return this.id; }
	public String getName() { return this.name; }
}
