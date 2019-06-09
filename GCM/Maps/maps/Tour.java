package maps;

import java.util.List;

public class Tour {
	private int id;
	private String descriptionString;
	private int suggestedTimeToVisit; // in hours
	private List<Site> tourSites;

	public Tour(int id, String description, int suggestedTimeToVisit, List<Site> tourSites) {
		if (id <= 0)
			throw new IllegalArgumentException("id has to be a positive number");
		this.id = id;
		this.descriptionString = description;
		this.suggestedTimeToVisit = suggestedTimeToVisit;
		this.tourSites = tourSites;
	}

	public int getId() {
		return this.id;
	}

	public String getDescription() {
		return this.descriptionString;
	}

	public int getSuggestedTimeToVisit() {
		return this.suggestedTimeToVisit;
	}

	public List<Site> getSites() {
		return this.tourSites;
	}

}
