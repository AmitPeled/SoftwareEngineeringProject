package maps;

import java.io.Serializable;

/**
 * Represents map-local coordinates values can't be changed
 */
public final class Coordinates implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final float EPSILON = 0.0001f;
	public final float x;
	public final float y;

	public Coordinates(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a Coordinates object with (0,0) value
	 */
	public Coordinates() {
		this(0f, 0f);
	}

	/**
	 * Copies another Coordinates object
	 * @param other The reference object that is being copied
	 */
	public Coordinates(Coordinates other) {
		this.x = other.x;
		this.y = other.y;
	}

	/**
	 * Checks coordinates values equality (not reference equality). The values are floats and their proximity is being compared with an epsilon. 
	 * @param other the coordinates to be compared
	 * @return true if x & y values are equal for both objects 
	 */
	public Boolean equals(Coordinates other) {
		return Math.abs(this.x - other.x) < EPSILON && Math.abs(this.y - other.y) < EPSILON;
	}
}
