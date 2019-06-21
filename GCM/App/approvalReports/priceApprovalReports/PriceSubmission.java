package approvalReports.priceApprovalReports;

import java.io.Serializable;
import java.util.List;

public class PriceSubmission implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Double> pricesBefore;
	private List<Double> pricesAfter;
	private int cityId;
	private String cityName;

	public PriceSubmission(int cityId, String cityName, List<Double> pricesBefore, List<Double> pricesAfter) {
		if (pricesAfter == null || pricesAfter.size() != 7)
			throw new IllegalArgumentException("price submission must contain prices for all months");
		this.pricesAfter = pricesAfter;
		this.pricesBefore = pricesBefore;
		this.cityId = cityId;
		this.cityName = cityName;
	}

	public List<Double> getPricesBefore() {
		return pricesBefore;
	}

	public List<Double> getPricesAfter() {
		return pricesAfter;
	}

	public String getCiytName() {
		return cityName;
	}

	public int getCityId() {
		return cityId;
	}

}
