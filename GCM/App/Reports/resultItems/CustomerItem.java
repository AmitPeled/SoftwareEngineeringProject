package Reports.resultItems;

import java.util.List;

import javafx.fxml.FXML;

public class CustomerItem{
	@FXML // fx:id="id"
    private int id;
	@FXML // fx:id="name"
    private String name;
	@FXML // fx:id="phone"
    private String phone;
	@FXML // fx:id="email"
    private String email;
	
	private List<String> purchaseHistory;
	
	public int id() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public List<String> getPurchaseHistory() {
        return purchaseHistory;
    }
    
    public CustomerItem(int id, String name, String phone, String email, List<String> purchaseHistory) {
        super();
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.purchaseHistory = purchaseHistory;
    }
}