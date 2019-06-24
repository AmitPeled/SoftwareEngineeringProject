package users;

import java.util.List;

import dataAccess.customer.PurchaseHistory;
import database.execution.UserType;
import queries.RequestState;

public class UserReport {
	private User user;
	private UserType userType;
	private List<PurchaseHistory> purchaseHistory;
	
	public UserReport(User user, UserType userType, List<PurchaseHistory> purchaseHistory){
		this.user = user;
		this.userType = userType;
		this.purchaseHistory = purchaseHistory;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserType() {
		return userType.toString();
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public List<PurchaseHistory> getPurchaseHistory() {
		return purchaseHistory;
	}

	public void setPurchaseHistory(List<PurchaseHistory> purchaseHistory) {
		this.purchaseHistory = purchaseHistory;
	}
}
