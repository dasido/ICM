package entities;

import javafx.beans.property.SimpleStringProperty;

public class InfoTable {
	private SimpleStringProperty requestNumber, informationSystemName, descriptionOfExistingSituation, userID, fullName, userDepartment, role, applicantID, currentPhase;
	private SimpleStringProperty engineerID, inspectorPermission, assessorPermission, chairmanPermission, memberOfCommitteePermission, executionLeaderPermission, examinerPermission;
	
	public InfoTable(String requestNumber, String informationSystemName)
	{
		this.requestNumber = new SimpleStringProperty(requestNumber);
		this.informationSystemName = new SimpleStringProperty(informationSystemName);
	}
	
	public InfoTable(String requestNumber, String informationSystemName, String descriptionOfExistingSituation)
	{
		this.requestNumber = new SimpleStringProperty(requestNumber);
		this.informationSystemName = new SimpleStringProperty(informationSystemName);
		this.descriptionOfExistingSituation = new SimpleStringProperty(descriptionOfExistingSituation);
	}
	
	public InfoTable(String requestNumber, String applicantID, String informationSystemName, String currentPhase)
	{
		this.requestNumber = new SimpleStringProperty(requestNumber);
		this.applicantID = new SimpleStringProperty(applicantID);
		this.informationSystemName = new SimpleStringProperty(informationSystemName);
		this.currentPhase = new SimpleStringProperty(currentPhase);
	}
	
	public InfoTable(String requestNumber, String userID, String fullName, String userDepartment, String role)
	{
		this.requestNumber = new SimpleStringProperty(requestNumber);
		this.userID = new SimpleStringProperty(userID);
		this.fullName = new SimpleStringProperty(fullName);
		this.userDepartment = new SimpleStringProperty(userDepartment);
		this.role = new SimpleStringProperty(role);
	}
	
	public InfoTable(String engineerID, String inspectorPermission, String assessorPermission, String chairmanPermission, String memberOfCommitteePermission, String executionLeaderPermission, String examinerPermission)
	{
		this.engineerID = new SimpleStringProperty(engineerID);
		this.inspectorPermission = new SimpleStringProperty(inspectorPermission);
		this.assessorPermission = new SimpleStringProperty(assessorPermission);
		this.chairmanPermission = new SimpleStringProperty(chairmanPermission);
		this.memberOfCommitteePermission = new SimpleStringProperty(memberOfCommitteePermission);
		this.examinerPermission = new SimpleStringProperty(examinerPermission);
		this.executionLeaderPermission = new SimpleStringProperty(executionLeaderPermission);
		
	}
	
	public String getRequestNumber()
	{
		return requestNumber.get();
	}
	
	public void setRequestNumber(SimpleStringProperty requestNumber)
	{
		this.requestNumber = requestNumber;
	}
	
	public String getInformationSystemName()
	{
		return informationSystemName.get();
	}
	
	public void setInformationSystemName(SimpleStringProperty informationSystemName)
	{
		this.informationSystemName = informationSystemName;
	}
	
	public String getDescriptionOfExistingSituation()
	{
		return descriptionOfExistingSituation.get();
	}
	
	public void setDescriptionOfExistingSituation(SimpleStringProperty descriptionOfExistingSituation)
	{
		this.descriptionOfExistingSituation = descriptionOfExistingSituation;
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
	
	public String getRole()
	{
		return role.get();
	}
	
	public void setRole(SimpleStringProperty role)
	{
		this.role = role;
	}
	
	public String getApplicantID()
	{
		return applicantID.get();
	}
	
	public void setApplicantID(SimpleStringProperty applicantID)
	{
		this.applicantID = applicantID;
	}
	
	public String getCurrentPhase()
	{
		return currentPhase.get();
	}
	
	public void setCurrentPhase(SimpleStringProperty currentPhase)
	{
		this.currentPhase = currentPhase;
	}
	
	public String getEngineerID()
	{
		return engineerID.get();
	}
	
	public void setEngineerID(SimpleStringProperty engineerID)
	{
		this.engineerID = engineerID;
	}
	
	public String getInspectorPermission()
	{
		return inspectorPermission.get();
	}
	
	public void setInspectorPermission(SimpleStringProperty inspectorPermission)
	{
		this.inspectorPermission = inspectorPermission;
	}
	
	public String getAssessorPermission()
	{
		return assessorPermission.get();
	}
	
	public void setAssessorPermission(SimpleStringProperty assessorPermission)
	{
		this.assessorPermission = assessorPermission;
	}
	
	public String getChairmanPermission()
	{
		return chairmanPermission.get();
	}
	
	public void setChairmanPermission(SimpleStringProperty chairmanPermission)
	{
		this.chairmanPermission = chairmanPermission;
	}
	
	public String getMemberOfCommitteePermission()
	{
		return memberOfCommitteePermission.get();
	}
	
	public void setMemberOfCommitteePermission(SimpleStringProperty memberOfCommitteePermission)
	{
		this.memberOfCommitteePermission = memberOfCommitteePermission;
	}
	
	public String getExecutionLeaderPermission()
	{
		return executionLeaderPermission.get();
	}
	
	public void setExecutionLeaderPermission(SimpleStringProperty executionLeaderPermission)
	{
		this.executionLeaderPermission = executionLeaderPermission;
	}
	
	public String getExaminerPermission()
	{
		return examinerPermission.get();
	}
	
	public void setExaminerPermission(SimpleStringProperty examinerPermission)
	{
		this.examinerPermission = examinerPermission;
	}
}
