package database.execution;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dataAccess.generalManager.Report;
import maps.Map;

public interface IGcmGenetalManager {
	List<Map> getPriceEdits();

	void discardMapPriceEdit(Map map);

	void approveMapPriceEdit(Map map);

	Report getCityReport(Date startDate, Date endDate, int cityId);

	Report getSystemReport(Date startDate, Date endDate);
	
	List<Report> getAllcitiesReport() throws SQLException;
	Report getOneCityReport(String CityName) throws SQLException;
}
