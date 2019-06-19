package approvalReports.priceApprovalReports;

import java.util.List;

public class PriceSubmission {
	private List<Double> pricesBefore;
	private List<Double> pricesAfter;
	private int cityId;

	public PriceSubmission(int cityId, List<Double> pricesBefore, List<Double> pricesAfter) {
		if (pricesAfter == null || pricesAfter.size() != 7)
			throw new IllegalArgumentException("price submission must contain prices for all months");
		this.pricesAfter = pricesAfter;
		this.pricesBefore = pricesBefore;
		this.cityId = cityId;
	}

	public List<Double> getPricesBefore() {
		return pricesBefore;
	}

	public List<Double> getPricesAfter() {
		return pricesAfter;
	}

	public int getCityId() {
		return cityId;
	}

}
