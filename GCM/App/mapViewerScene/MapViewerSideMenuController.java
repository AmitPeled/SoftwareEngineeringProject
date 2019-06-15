package mapViewerScene;

import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import maps.Site;
import maps.Tour;

public class MapViewerSideMenuController implements Initializable {
	@FXML
	public ListView<String> sitesListView;
	
	@FXML
	public ListView<String> toursListView;

	private Dictionary<String,Tour> toursDictionary;

	private Dictionary<String,Site> sitesDictionary;

	private ArrayList<String> tourStrings;

	private ArrayList<String> sitesStrings;
	
	public MapViewerSideMenuController(List<Site> sites,List<Tour> tours) {
		sitesStrings = new ArrayList<String>();
		tourStrings = new ArrayList<String>();
		
		for (Tour tour : tours) {
			toursDictionary.put(tour.getDescription(), tour);
			tourStrings.add(tour.getDescription());
		}
		for(Site site: sites) {
			sitesDictionary.put(site.getName(), site);
			sitesStrings.add(site.getName());
		}
		

	}
	
	public Site getSelectedSite() {
		String selected = sitesListView.getSelectionModel().getSelectedItem();
		return sitesDictionary.get(selected);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		toursListView.setItems(FXCollections.observableArrayList(tourStrings));
		sitesListView.setItems(FXCollections.observableArrayList(sitesStrings));
	}
}
