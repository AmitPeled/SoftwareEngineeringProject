package maps;

import java.io.Serializable;

/**
 * Represents map-local coordinates values can't be changed
 */
public final class Coordinates implements Serializable {
	private static final long serialVersionUID = 1L;
	public final float x;
	public final float y;

	public Coordinates(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Coordinates() {
		this(0f, 0f);
	}

	public Boolean equals(Coordinates other) {
		return this.x == other.x && this.y == other.y;
	}
}
