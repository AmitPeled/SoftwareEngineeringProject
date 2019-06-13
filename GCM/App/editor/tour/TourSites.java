package editor.tour;

import java.util.ArrayList;
import java.util.List;


public class TourSites {
	List<String> sitesList;
	List<String> timeEstimationsList;
	
	public TourSites() {
		sitesList = new ArrayList<String>();
		timeEstimationsList = new ArrayList<String>();
	}
	public TourSites(List<String> sites, List<String> timeEstimations) {
		this.sitesList = sites;
		this.timeEstimationsList = timeEstimations;
	}
	public void addSites(List<String> newSites, List<String> timeEstimations){
		for (String site : newSites) {
			if(!sitesList.contains(site)) {
				sitesList.add(site);
				timeEstimationsList.add(timeEstimations.get(newSites.indexOf(site)));
			}
		}
	}
	public boolean checkIfSiteExist(String site) {
		if(sitesList.contains(site)){
			return true;
		}
		return false;
	}
	public List<String> getSites() {
		return sitesList;
	}
	public List<String> getTimeEstimations() {
		return timeEstimationsList;
	}
	public String getTimeEstimationByIndex(int index) {
		return timeEstimationsList.get(index);
	}
}
