package dataAccess.generalManager;

import java.sql.Date;
import java.util.List;

import approvalReports.priceApprovalReports.PriceSubmission;

public interface GeneralManagerDAO {

	 Report getCityReport(Date startDate, Date endDate, String cityName);

	 List<Report> getSystemReport(Date startDate, Date endDate);
	 
	 List<Report> getReportsOnUser(Date startDate, Date endDate, String username);

	 List<PriceSubmission> getPriceSubmissions();

	 void approveCityPrice(int cityId, List<Double> prices, boolean approve);


}
