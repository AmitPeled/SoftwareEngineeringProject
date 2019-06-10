
package users;

import java.io.Serializable;
import java.util.Date;

/**
 * @author amit
 *
 */
public final class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public User(String firstName, String lastName, String email, String phoneNumber) {
		this(firstName, lastName, email, phoneNumber, null);

	}

	public User(String firstName, String lastName, String email, String phoneNumber, String username) {
		this(firstName, lastName, email, phoneNumber, null, 0, null, 0, 0);
	}

	public User(String firstName, String lastName, String email, String phoneNumber, String username, int numPurchases,
			Date membershipExpireDate, int purchasedMembershipPeriod, int numTimesRepurchasedMembership) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.setMembershipExpireDate(membershipExpireDate);
		this.setNumPurchases(numPurchases);
		this.purchasedMembershipPeriod = purchasedMembershipPeriod;
		this.numTimesRepurchasedMembership = numTimesRepurchasedMembership;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String setUsername(String username) {
		return this.username = username;
	}

	public Date getMembershipExpireDate() {
		return membershipExpireDate;
	}

	public void setMembershipExpireDate(Date membershipExpireDate) {
		this.membershipExpireDate = membershipExpireDate;
	}

	public int getNumPurchases() {
		return numPurchases;
	}

	public void setNumPurchases(int numPurchases) {
		this.numPurchases = numPurchases;
	}

	public int getPurchasedMembershipPeriod() {
		return purchasedMembershipPeriod;
	}

	public void setPurchasedMembershipPeriod(int purchasedMembershipPeriod) {
		this.purchasedMembershipPeriod = purchasedMembershipPeriod;
	}

	public int getNumTimesRepurchasedMembership() {
		return numTimesRepurchasedMembership;
	}

	public void setNumTimesRepurchasedMembership(int numTimesRepurchasedMembership) {
		this.numTimesRepurchasedMembership = numTimesRepurchasedMembership;
	}

	private String username;
	private String email;
	private String phoneNumber;
	private String firstName;
	private String lastName;
	private int numPurchases;
	private Date membershipExpireDate;
	private int purchasedMembershipPeriod;
	private int numTimesRepurchasedMembership;
}
