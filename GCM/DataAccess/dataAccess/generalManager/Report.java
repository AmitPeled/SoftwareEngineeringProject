package dataAccess.generalManager;

public class Report {

	private int cityId;
	private String cityName;
	private int oneTimePurchase;
	private int subscribes;
	private int resubscribers;
	private int viewsNum;
	private int downloads;

	public Report(int cityId, String cityName, int oneTimePurchase, int subscribes, int resubscribers, int viewsNum,
			int downloads) {
		this.cityId = cityId;
		this.cityName = cityName;
		this.oneTimePurchase = oneTimePurchase;
		this.subscribes = subscribes;
		this.resubscribers = resubscribers;
		this.viewsNum = viewsNum;
		this.downloads = downloads;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getOneTimePurchase() {
		return oneTimePurchase;
	}

	public void setOneTimePurchase(int oneTimePurchase) {
		this.oneTimePurchase = oneTimePurchase;
	}

	public int getSubscribes() {
		return subscribes;
	}

	public void setSubscribes(int subscribes) {
		this.subscribes = subscribes;
	}

	public int getResubscribers() {
		return resubscribers;
	}

	public void setResubscribers(int resubscribers) {
		this.resubscribers = resubscribers;
	}

	public int getViewsNum() {
		return viewsNum;
	}

	public void setViewsNum(int viewsNum) {
		this.viewsNum = viewsNum;
	}

	public int getDownloads() {
		return downloads;
	}

	public void setDownloads(int downloads) {
		this.downloads = downloads;
	}

	public void print() {

		System.out.println("cityId = " + this.cityId + " cityName = " + this.cityName + " oneTimePurchase = "
				+ this.oneTimePurchase + " subscribes = " + this.subscribes + " resubscribers = " + this.resubscribers
				+ " viewsNum = " + this.viewsNum + " downloads = " + this.downloads);

	}

}
