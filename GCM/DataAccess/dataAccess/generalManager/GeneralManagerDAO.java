package dataAccess.generalManager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import approvalReports.priceApprovalReports.PriceSubmission;
import maps.Map;

public interface GeneralManagerDAO {

	Report getCityReport(Date startDate, Date endDate, int cityId);

	List<Report> getSystemReport(Date startDate, Date endDate);

	List<PriceSubmission> getPriceSubmissions() throws SQLException;

	void approveCityPrice(int cityId, List<Double> prices, boolean approve) throws SQLException;
}
