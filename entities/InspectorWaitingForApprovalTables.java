package entities;

import javafx.beans.property.SimpleStringProperty;

public class InspectorWaitingForApprovalTables {
	private SimpleStringProperty requestNumber, phaseName, date, reason, userID, fullName, userDepartment;
	
	public InspectorWaitingForApprovalTables(String requestNumber, String reason)
	{
		this.requestNumber = new SimpleStringProperty(requestNumber);
		this.reason = new SimpleStringProperty(reason);
	}
	
	public InspectorWaitingForApprovalTables(String requestNumber, String phaseName, String date)
	{
		this.requestNumber = new SimpleStringProperty(requestNumber);
		this.phaseName = new SimpleStringProperty(phaseName);
		this.date = new SimpleStringProperty(date);
	}
	
	public InspectorWaitingForApprovalTables(String requestNumber, String userID, String fullName, String userDepartment)
	{
		this.requestNumber = new SimpleStringProperty(requestNumber);
		this.userID = new SimpleStringProperty(userID);
		this.fullName = new SimpleStringProperty(fullName);
		this.userDepartment = new SimpleStringProperty(userDepartment);
	}
	
	public String getRequestNumber()
	{
		return requestNumber.get();
	}
	
	public void setRequestNumber(SimpleStringProperty requestNumber)
	{
		this.requestNumber = requestNumber;
	}
	
	public String getReason()
	{
		return reason.get();
	}
	
	public void setReason(SimpleStringProperty reason)
	{
		this.reason = reason;
	}
	
	public String getPhaseName()
	{
		return phaseName.get();
	}
	
	public void setPhaseName(SimpleStringProperty phaseName)
	{
		this.phaseName = phaseName;
	}
	
	public String getDate()
	{
		return date.get();
	}
	
	public void setDate(SimpleStringProperty date)
	{
		this.date = date;
	}
	
	public String getUserID()
	{
		return userID.get();
	}
	
	public void setUserID(SimpleStringProperty userID)
	{
		this.userID = userID;
	}
	
	public String getFullName()
	{
		return fullName.get();
	}
	
	public void setFullName(SimpleStringProperty fullName)
	{
		this.fullName = fullName;
	}
	
	public String getUserDepartment()
	{
		return userDepartment.get();
	}
	
	public void setUserDepartment(SimpleStringProperty userDepartment)
	{
		this.userDepartment = userDepartment;
	}
}
