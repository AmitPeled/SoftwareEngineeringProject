package reports.resultItems;

import javafx.fxml.FXML;

public class WorkerItem{
	@FXML // fx:id="id"
    private int id;
	@FXML // fx:id="name"
    private String firstName;
	@FXML // fx:id="phone"
    private String lastName;
	@FXML // fx:id="email"
    private String employeeId;
	@FXML // fx:id="phone"
    private String email;
	@FXML // fx:id="email"
    private String jobDescription;
		
	public int id() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmployeeId() {
        return employeeId;
    }
    public String getEmail() {
        return email;
    }
    public String getJobDescription() {
        return jobDescription;
    }

    public WorkerItem(int id, String firstName, String lastName, String employeeId, String email, String jobDescription) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
        this.email = email;
        this.jobDescription = jobDescription;
    }
}