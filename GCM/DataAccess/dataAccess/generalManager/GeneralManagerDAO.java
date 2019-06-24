package dataAccess.generalManager;

import java.sql.Date;
import java.util.List;

import approvalReports.priceApprovalReports.PriceSubmission;
import users.UserReport;

public interface GeneralManagerDAO {

	 List<Report> getSystemReport(java.sql.Date startDate, java.sql.Date endDate);

	 UserReport getReportOnUser(Date startDate, Date endDate, String username);

	 List<PriceSubmission> getPriceSubmissions();

	 void approveCityPrice(int cityId, List<Double> prices, boolean approve);

	 Report getCityReport(java.sql.Date startDate, java.sql.Date endDate, String cityName);

}
