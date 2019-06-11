package dataAccess.users;

public class PurchaseDetails {
	String creditCard;
	String cardExpireDate;
	String cardThreeControlDigits; 
	String cardOwnerIdString;
	public PurchaseDetails(String creditCard, String cardExpireDate, String cardThreeControlDigits, String cardOwnerIdString){
		this.creditCard = creditCard;
		this.cardExpireDate = cardExpireDate;
		this.cardThreeControlDigits = cardThreeControlDigits;
		this.cardOwnerIdString = cardOwnerIdString;
	}
}
