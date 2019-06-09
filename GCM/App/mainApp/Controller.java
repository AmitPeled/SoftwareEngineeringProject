package mainApp;

public interface Controller {
	/**
	 * Sets the GcmClient dependency
	 * @param gcmClient The reference to the GcmClient object that's used for high-level
	 * operations (for example switching scenes)
	 */
	void setClient(GcmClient gcmClient);

}