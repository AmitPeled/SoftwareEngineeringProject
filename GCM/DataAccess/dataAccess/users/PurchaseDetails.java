package dataAccess.users;

public class PurchaseDetails {
	String creditCard;
	String cardExpireDate;
	String cvv;
	String cardOwnerIdString;

	public PurchaseDetails(String creditCard, String cardExpireDate, String cvv, String cardOwnerIdString) {
		this.creditCard = creditCard;
		this.cardExpireDate = cardExpireDate;
		this.cvv = cvv;
		this.cardOwnerIdString = cardOwnerIdString;

	}

	public String getCreditCard() {
		return creditCard;
	}

	public String getCardExpireDate() {
		return cardExpireDate;
	}

	public String getCvv() {
		return cvv;
	}

	public String getCardOwnerIdString() {
		return cardOwnerIdString;
	}

}
