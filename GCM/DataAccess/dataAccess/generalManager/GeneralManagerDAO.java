package dataAccess.generalManager;

import java.sql.Date;
import java.util.List;

import approvalReports.priceApprovalReports.PriceSubmission;

public interface GeneralManagerDAO {

	 Report getCityReport(java.util.Date startDate, java.util.Date endDate, String cityName);

	 List<Report> getSystemReport(java.util.Date startDate, java.util.Date endDate);
	 
	 List<Report> getReportsOnUser(java.util.Date startDate, java.util.Date endDate, String username);

	 List<PriceSubmission> getPriceSubmissions();

	 void approveCityPrice(int cityId, List<Double> prices, boolean approve);


}
