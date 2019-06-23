package dataAccess.contentManager;

import java.util.List;

import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.mapApprovalReports.MapSubmission;
import approvalReports.sitesApprovalReports.SiteSubmission;
import approvalReports.tourApprovalReports.TourSubmission;
import dataAccess.customer.PurchaseHistory;
import maps.City;
import users.User;

public interface ContentManagerDAO {

	List<City> getCitiesAddEdits();
	List<City> getCitiesUpdateEdits();
	List<City> getCitiesDeleteEdits();

	void editCityPrice(int cityId, double newPrice);
	List<SiteSubmission> getSiteSubmissions();
	List<MapSubmission> getMapSubmissions();
	List<TourSubmission> getTourSubmissions();

	void actionCityEdit(CitySubmission citySubmission, boolean action);
	List<User> actionMapEdit(MapSubmission mapSubmission, boolean action);
	void actionTourEdit(TourSubmission tourSubmission, boolean action);
	void actionSiteEdit(SiteSubmission siteSubmission, boolean action);
	
	void changeCityPrices(int cityId, List<Double> prices);
	List<PurchaseHistory> getPurchaseHistory();
	City getCity(int cityId);
	
}
