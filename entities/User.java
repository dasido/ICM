package entities;

public class User {
	private String userID;
	private String userName;
	private String password;
	private String fullName;
	private String eMail;
	private String userDepartmrnt;
	private String userRole;
	private String engineer;
	private String logInStatus;
	

	
	
	public User (String userID,String userName, String password,String fullName,String eMail, String userDepartmrnt,String userRole,String engineer,String logInStatus) {
		this.userID=userID; 
		this.userName=userName;
		this.password=password;
		this.fullName=fullName;
		this.eMail=eMail;
		this.userDepartmrnt=userDepartmrnt;
		this.userRole=userRole;
		this.engineer=engineer;
		this.logInStatus=logInStatus;
	}

	public String getuserID() {
		return userID;
	}

	public void setuserID(String userID) {
		this.userID = userID;
	}

	public String getuserName() {
		return userName;
	}

	public void setuserName(String userName) {
		this.userName = userName;
	}

	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public String getfullName() {
		return fullName;
	}

	public void setfullName(String fullName) {
		this.fullName = fullName;
	}

	public String geteMail() {
		return this.eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getuserDepartmrnt() {
		return userDepartmrnt;
	}

	public void setuserDepartmrnt(String userDepartmrnt) {
		this.userDepartmrnt = userDepartmrnt;
	}
		
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getEngineer() {
		return engineer;
	}

	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}

	public String getLogInStatus() {
		return logInStatus;
	}

	public void setLogInStatus(String logInStatus) {
		this.logInStatus = logInStatus;
	}

	public String toString(){
		return String.format("Currently User Logged in:\nFullName: %s\nUserID: %s\nE-mail: %s\nUser Departmrnt: %s\nUser Role: %s\n",fullName,userID,eMail,userDepartmrnt,userRole);
	}
}