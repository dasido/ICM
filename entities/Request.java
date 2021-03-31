package entities;

public class Request {
	private String rnumber;
	private String applicantName;
	private String informationSystemName;
	private String exsistingDescription;
	private String changeDescription;
	private String reason;
	private String comments;
	private String treatedBy;
	private String status;
	private String currentPhase;
	private String startDate;
	private String finishDate;
	
	
	
	public Request (String rnumber,String applicantName, String informationSystemName, String exsistingDescription, String changeDescription, String reason, String comments, String treatedBy, String status, String currentPhase, String startDate, String finishDate) {
		this.rnumber=rnumber;
		this.applicantName=applicantName;
		this.informationSystemName=informationSystemName;
		this.exsistingDescription=exsistingDescription;
		this.changeDescription=changeDescription;
		this.reason = reason;
		this.comments = comments;
		this.treatedBy=treatedBy;
		this.status=status;
		this.currentPhase = currentPhase;
		this.startDate = startDate;
		this.finishDate = finishDate;
	}

	public String getRNumber() {
		return rnumber;
	}

	public void setRNumber(String rnumber) {
		this.rnumber = rnumber;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getInformationSystemName() {
		return informationSystemName;
	}

	public void setInformationSystemName(String informationSystemName) {
		this.informationSystemName = informationSystemName;
	}
	
	public String getExsistingDescription() {
		return exsistingDescription;
	}

	public void setExsistingDescription(String exsistingDescription) {
		this.exsistingDescription = exsistingDescription;
	}

	public String getChangeDescription() {
		return changeDescription;
	}

	public void setChangeDescription(String changeDescription) {
		this.changeDescription = changeDescription;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getTreatedBy() {
		return treatedBy;
	}

	public void setTreatedBy(String treatedBy) {
		this.treatedBy = treatedBy;
	}
	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(String currentPhase) {
		this.currentPhase = currentPhase;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	
	public String toString(){
		return String.format("\nrnumber: %s applicantName: %s informationSystemName: %s exsistingDescription: %s changeDescription: %s reason: %s comments: %s treatedBy: %s status: %s currentPhase: %s startDate: %s finishDate: %s",rnumber,applicantName,informationSystemName,exsistingDescription,changeDescription,reason,comments,treatedBy,status,currentPhase,startDate,finishDate);
	}
	
}