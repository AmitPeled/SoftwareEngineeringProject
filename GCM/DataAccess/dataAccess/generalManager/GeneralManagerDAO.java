package dataAccess.generalManager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import maps.City;
import maps.Map;
import maps.Site;
import maps.Tour;

public interface GeneralManagerDAO {
	List<Map> getPriceEdits();

	void discardMapPriceEdit(Map map);

	void approveMapPriceEdit(Map map);
		
	Report getCityReport(Date startDate, Date endDate, int cityId);
	
	Report getSystemReport(Date startDate, Date endDate);
	List<Map> getMapsObjectAddedTo(Object object) ;
	List<City> getCitiesObjectAddedTo(Object object) ;
	List<Tour> getToursObjectAddedTo(Object object);
}
