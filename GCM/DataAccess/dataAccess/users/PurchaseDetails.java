package dataAccess.users;

import java.io.Serializable;

public class PurchaseDetails implements Serializable{
	private static final long serialVersionUID = 1L;
	String creditCard;
	String cardExpireDate;//-> MM/YY
	String cvv;
	String cardOwnerIdString;
	String firstname;
	String lastname;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public PurchaseDetails(String creditCard, String cardExpireDate, String cvv, String cardOwnerIdString,
			String firstname, String lastname) {
		super();
		this.creditCard = creditCard;
		this.cardExpireDate = cardExpireDate;
		this.cvv = cvv;
		this.cardOwnerIdString = cardOwnerIdString;
		this.firstname = firstname;
		this.lastname = lastname;
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
