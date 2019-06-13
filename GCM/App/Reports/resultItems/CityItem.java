package Reports.resultItems;

import javafx.fxml.FXML;

public class CityItem {
	@FXML // fx:id="id"
    private int id;
	@FXML // fx:id="numberOfMaps"
    private String numberOfMaps;
	@FXML // fx:id="oneTimeSubscribers"
    private String oneTimeSubscribers;
	@FXML // fx:id="subscribers"
    private String subscribers;
	@FXML // fx:id="renewableSubscriptions"
    private String renewableSubscriptions;
	@FXML // fx:id="downloads"
    private String downloads;
	
	public int id() {
        return id;
    }
    public String getNumberOfMaps() {
        return numberOfMaps;
    }
    public String getDescription() {
        return oneTimeSubscribers;
    }
    public String getOneTimeSubscribers() {
        return oneTimeSubscribers;
    }
    public String getSubscribers() {
        return subscribers;
    }
    public String getRenewableSubscriptions() {
        return renewableSubscriptions;
    }
    public String getDownloads() {
        return downloads;
    }
    public CityItem(int id, String numberOfMaps, String oneTimeSubscribers, String subscribers, String renewableSubscriptions, String downloads) {
        super();
        this.id = id;
        this.numberOfMaps = numberOfMaps;
        this.oneTimeSubscribers = oneTimeSubscribers;
        this.subscribers = subscribers;
        this.renewableSubscriptions = renewableSubscriptions;
        this.downloads = downloads;
    }
}