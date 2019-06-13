package maps;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Tour implements Serializable {
	private int id;
	private String descriptionString;
	private List<Site> sites;
	private List<Integer> sitesTimeToVisit;

	public Tour(int id, String description, List<Site> tourSites, List<Integer> sitesTimeToVisit) {
		if (id <= 0)
			throw new IllegalArgumentException("id has to be a positive number");
		this.id = id;
		this.descriptionString = description;
		this.setSitesTimeToVisit(sitesTimeToVisit);
		this.sites = tourSites;
	}

	public Tour(String description) {
		this.id = -1;
		this.descriptionString = description;
	}

	public int getId() {
		return this.id;
	}

	public String getDescription() {
		return this.descriptionString;
	}

	public List<Site> getSites() {
		return this.sites;
	}

	public List<Integer> getSitesTimeToVisit() {
		return sitesTimeToVisit;
	}

	public void setSitesTimeToVisit(List<Integer> sitesTimeToVisit) {
		this.sitesTimeToVisit = sitesTimeToVisit;
	}

}
