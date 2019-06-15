package dataAccess.generalManager;

import java.sql.Date;
import java.util.List;

import maps.Map;

public interface GeneralManagerDAO {
	List<Map> getPriceEdits();

	void discardMapPriceEdit(Map map);

	void approveMapPriceEdit(Map map);
		
	Report getCityReport(Date startDate, Date endDate, int cityId);
	
	Report getSystemReport(Date startDate, Date endDate);
	
}
