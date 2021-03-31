package entities;

import javafx.beans.property.SimpleStringProperty;

public class ITReadAccessTables {
	
	private SimpleStringProperty userID, userName, password, fullName, email, userDepartment, userRole;
	private SimpleStringProperty requestNumber, applicantID, informationSystemName;
	public ITReadAccessTables(String userID, String userName, String password, String fullName, String email, String userDepartment, String userRole)
	{
		this.userID = new SimpleStringProperty(userID);
		this.userName = new SimpleStringProperty(userName);
		this.password = new SimpleStringProperty(password);
		this.fullName = new SimpleStringProperty(fullName);
		this.email = new SimpleStringProperty(email);
		this.userDepartment = new SimpleStringProperty(userDepartment);
		this.userRole = new SimpleStringProperty(userRole);
	}
	
	public ITReadAccessTables(String requestNumber, String applicantID, String informationSystemName)
	{
		this.requestNumber = new SimpleStringProperty(requestNumber);
		this.applicantID = new SimpleStringProperty(applicantID);
		this.informationSystemName = new SimpleStringProperty(informationSystemName);
	}
	
	public String getUserID()
	{
		return userID.get();
	}
	
	public void setUserID(SimpleStringProperty userID)
	{
		this.userID = userID;
	}
	
	public String getUserName()
	{
		return userName.get();
	}
	
	public void setUserName(SimpleStringProperty userName)
	{
		this.userName = userName;
	}
	
	public String getPassword()
	{
		return password.get();
	}
	
	public void setPassword(SimpleStringProperty password)
	{
		this.password = password;
	}
	
	public String getFullName()
	{
		return fullName.get();
	}
	
	public void setFullName(SimpleStringProperty fullName)
	{
		this.fullName = fullName;
	}
	
	public String getEmail()
	{
		return email.get();
	}
	
	public void setEmail(SimpleStringProperty email)
	{
		this.email = email;
	}
	
	public String getUserDepartment()
	{
		return userDepartment.get();
	}
	
	public void setUserDepartment(SimpleStringProperty userDepartment)
	{
		this.userDepartment = userDepartment;
	}
	
	public String getUserRole()
	{
		return userRole.get();
	}
	
	public void setUserRole(SimpleStringProperty userRole)
	{
		this.userRole = userRole;
	}
	
	public String getRequestNumber()
	{
		return requestNumber.get();
	}
	
	public void setRequestNumber(SimpleStringProperty requestNumber)
	{
		this.requestNumber = requestNumber;
	}
	
	public String getApplicantID()
	{
		return applicantID.get();
	}
	
	public void setApplicantID(SimpleStringProperty applicantID)
	{
		this.applicantID = applicantID;
	}
	
	public String getInformationSystemName()
	{
		return informationSystemName.get();
	}
	
	public void setInformationSystemName(SimpleStringProperty informationSystemName)
	{
		this.informationSystemName = informationSystemName;
	}
}
