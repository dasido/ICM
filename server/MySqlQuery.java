package server;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.ListIterator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("serial")
public class MySqlQuery implements Serializable {
	static Statement stm;

	/**
	 * func: login to user and get all his information case 1
	 * 
	 * @param query1
	 * @param query1_1
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> loginToUser(String query1, String query1_1, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query1);

				if (rst.next()) {
					returnDB.add(rst.getString("UserID"));
					returnDB.add(rst.getString("UserName"));
					returnDB.add(rst.getString("Password"));
					returnDB.add(rst.getString("FullName"));
					returnDB.add(rst.getString("Email"));
					returnDB.add(rst.getString("UserDepartment"));
					returnDB.add(rst.getString("UserRole"));
					returnDB.add(rst.getString("Engineer"));
					returnDB.add(rst.getString("LoginStatus"));

					if (returnDB.get(8).equals(1)) {
						returnDB.clear();
						returnDB.add("loged_in");
						return returnDB;
					}

					updateUserLoginStatusInDB(query1_1, mySql);

				} else {
					returnDB.add("false");
				}
			} catch (Exception e) {
			}
		}
		System.out.println("loginToUser" + returnDB);

		return returnDB;
	}

	/**
	 * func: update user login status case 5
	 * 
	 * @param query1_1
	 * @param mySql
	 * @return false/true
	 * @throws SQLException
	 */
	public static String updateUserLoginStatusInDB(String query1_1, Connection mySql) throws SQLException {
		stm = (Statement) mySql.createStatement();
		int x = ((java.sql.Statement) stm).executeUpdate(query1_1);
		if (x > 0) {
			System.out.println("updated");
			return "updated";
		} else {
			System.out.println("false");
			return "false";
		}
	}

	/**
	 * func: get all requests of specific user case 2
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> allRequestsOfUser(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				while (rst.next()) {
					returnDB.add(rst.getString("RNumber"));
					returnDB.add(rst.getString("ApplicantID"));
					returnDB.add(rst.getString("InformationSystemName"));
					returnDB.add(rst.getString("ExistingDescription"));
					returnDB.add(rst.getString("ChangeDescription"));
					returnDB.add(rst.getString("Reason"));
					returnDB.add(rst.getString("Comments"));
					returnDB.add(rst.getString("TreatedBy"));
					returnDB.add(rst.getString("Status"));
					returnDB.add(rst.getString("CurrentPhase"));
					returnDB.add(rst.getString("StartDate"));
					returnDB.add(rst.getString("FinishDate"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get information of specific request case 3,54
	 * 
	 * @param rnumber
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> informationOfSpecificRequest(String rnumber, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		String query = "SELECT * FROM icm.request WHERE RNumber='" + rnumber + "';";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next()) {
					returnDB.add(rst.getString("RNumber"));// 0
					returnDB.add(rst.getString("ApplicantID"));// 1
					returnDB.add(rst.getString("InformationSystemName"));// 2*
					returnDB.add(rst.getString("ExistingDescription"));// 3*
					returnDB.add(rst.getString("ChangeDescription"));// 4*
					returnDB.add(rst.getString("Reason"));// 5*
					returnDB.add(rst.getString("Comments"));// 6*
					returnDB.add(rst.getString("TreatedBy"));// 7
					returnDB.add(rst.getString("Status"));// 8
					returnDB.add(rst.getString("CurrentPhase"));// 9
					returnDB.add(rst.getString("StartDate"));// 10
					returnDB.add(rst.getString("FinishDate"));// 11
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: find specific request in date base case 4
	 * 
	 * @param aList
	 * @param applicantID
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> findRequestFromRequestTableToClose(ArrayList<String> aList, String applicantID,
			Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		ListIterator<String> aListIterator = aList.listIterator();

		if (mySql != null) {
			try {
				while (aListIterator.hasNext()) {
					String query = "SELECT * FROM icm.request R WHERE R.ApplicantID='" + applicantID
							+ "' AND R.RNumber='" + aListIterator.next() + "' ;";
					stm = mySql.createStatement();
					ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

					if (rst.next()) {
						returnDB.add(rst.getString("RNumber"));
						returnDB.add(rst.getString("ApplicantID"));
						returnDB.add(rst.getString("InformationSystemName"));
						returnDB.add(rst.getString("ExistingDescription"));
						returnDB.add(rst.getString("ChangeDescription"));
						returnDB.add(rst.getString("Reason"));
						returnDB.add(rst.getString("Comments"));
						returnDB.add(rst.getString("TreatedBy"));
						returnDB.add(rst.getString("Status"));
						returnDB.add(rst.getString("CurrentPhase"));
						returnDB.add(rst.getString("StartDate"));
						returnDB.add(rst.getString("FinishDate"));
					}
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: reason of request closure update complete OR denied case 6
	 * 
	 * @param query
	 * @param mySql
	 * @return true/false
	 */
	public static String reasonOfRequestClosure(String query, Connection mySql) {

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (rst.next())
					return rst.getString("CompletedOrDenied");
			} catch (Exception e) {
			}
		}
		return "false";
	}

	/**
	 * func: check if approved to close request by inspector and if true return
	 * current status or false case 7
	 * 
	 * @param query1
	 * @param query2
	 * @param query3
	 * @param query4
	 * @param mySql
	 * @return true/false
	 */
	public static String approveToCloseRequestByUserAndInspector(String query1, String query2, String query3,
			String query4, Connection mySql) {

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				((java.sql.Statement) stm).executeUpdate(query1); // update

				stm = mySql.createStatement();
				ResultSet rst2 = ((java.sql.Statement) stm).executeQuery(query2);

				if (rst2.next()) {
					String result2 = rst2.getString("ApprovedByInspector");
					if (result2.equals("1")) {
						stm = mySql.createStatement();
						((java.sql.Statement) stm).executeUpdate(query3); // update
					}
				}
				stm = mySql.createStatement();
				ResultSet rst4 = ((java.sql.Statement) stm).executeQuery(query4);

				if (rst4.next())
					return rst4.getString("Status");
			} catch (Exception e) {
			}
		}
		return "false";
	}

	/**
	 * func: get engineer permissions in system case 8
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> engineerPermissionsInSystem(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (rst.next()) {
					returnDB.add(rst.getString("ITdepartmentManager"));
					returnDB.add(rst.getString("Inspector"));
					returnDB.add(rst.getString("Assessor"));
					returnDB.add(rst.getString("Chairman"));
					returnDB.add(rst.getString("CommitteeMember"));
					returnDB.add(rst.getString("Examiner"));
					returnDB.add(rst.getString("ExecutionLeader"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: create new request in data base case 9
	 * 
	 * @param query1
	 * @param query2
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> createNewRequest(String query1, String query2, Connection mySql) {

		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				if (rnumberExist(query2, mySql).equals("null")) {

					stm = (Statement) mySql.createStatement();
					int x = ((java.sql.Statement) stm).executeUpdate(query1); // update

					if (x > 0) {
						System.out.println("updated");
						returnDB.add("true");
						returnDB.add(rnumberExist(query2, mySql)); // get RNumber
						return returnDB;
					}
				}
			} catch (Exception e) {
			}

		}
		returnDB.add("false");
		return returnDB;
	}

	/**
	 * check if request number exists case 9
	 * 
	 * @param query
	 * @param mySql
	 * @return rnumber
	 */
	public static String rnumberExist(String query, Connection mySql) {

		try {
			stm = (Statement) mySql.createStatement();
			ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);
			if (rst.next()) {
				return rst.getString("RNumber");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // get RNumber

		return "null";
	}

	/**
	 * func: return all of the RNumbers for the specific role
	 * 
	 * @param query
	 * @param mySql
	 * @return aList
	 */
	public static ArrayList<String> findRequestAs_(String query, Connection mySql) {
		ArrayList<String> aList = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					aList.add("false");
				}
				rst.previous();

				while (rst.next()) {
					aList.add(rst.getString("RNumber"));
				}
			} catch (Exception e) {
			}
		}
		return aList;
	}

	/////////////////////////////////////////////////////
	// enter active status
	public static String enterActiveStatus(String RNumber, Connection mySql) {
		String result = null;
		LocalDate date = LocalDate.now();
		String query = "INSERT INTO icm.active_status_tbl (RNumber,EnterActive) VALUES ('" + RNumber + "','" + date
				+ "');";

		result = updateDetails(query, mySql);
		return result;
	}

	// exit active status
	public static String exitActive(String RNumber, Connection mySql) {
		String result = null;
		LocalDate date = LocalDate.now();
		String query = "UPDATE icm.active_status_tbl SET ExitActive = '" + date + "' WHERE RNumber = '" + RNumber
				+ "' AND ExitActive IS NULL;";

		result = updateDetails(query, mySql);
		return result;
	}

	// enter suspended status
	public static String enterSuspended(String RNumber, Connection mySql) {
		String result = null;
		LocalDate date = LocalDate.now();
		String query = "INSERT INTO icm.suspended_status_tbl (RNumber,EnterSuspended) VALUES ('" + RNumber + "','"
				+ date + "');";

		result = updateDetails(query, mySql);
		return result;
	}

	// exit active status
	public static String exitSuspended(String RNumber, Connection mySql) {
		String result = null;
		LocalDate date = LocalDate.now();
		String query = "UPDATE icm.suspended_status_tbl SET ExitSuspended = '" + date + "' WHERE RNumber = '" + RNumber
				+ "' AND ExitSuspended IS NULL;";

		result = updateDetails(query, mySql);
		return result;
	}
	///////////////////////////////////////////////////////

	/**
	 * func: return the requests of specific role cases 4,14,15,16,17
	 * 
	 * @param aList
	 * @param phase
	 * @param mySql
	 * @return
	 */
	public static ArrayList<String> findRequestFromRequestTable(ArrayList<String> aList, String phase,
			Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		ListIterator<String> aListIterator = aList.listIterator();

		if (mySql != null) {
			try {
				while (aListIterator.hasNext()) {
					String query = "SELECT * FROM icm.request R WHERE R.CurrentPhase='" + phase
							+ "' AND R.Status='Active' AND R.RNumber='" + aListIterator.next() + "';";
					stm = mySql.createStatement();
					ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

					if (rst.next()) {
						returnDB.add(rst.getString("RNumber"));
						returnDB.add(rst.getString("ApplicantID"));
						returnDB.add(rst.getString("InformationSystemName"));
						returnDB.add(rst.getString("ExistingDescription"));
						returnDB.add(rst.getString("ChangeDescription"));
						returnDB.add(rst.getString("Reason"));
						returnDB.add(rst.getString("Comments"));
						returnDB.add(rst.getString("TreatedBy"));
						returnDB.add(rst.getString("Status"));
						returnDB.add(rst.getString("CurrentPhase"));
						returnDB.add(rst.getString("StartDate"));
						returnDB.add(rst.getString("FinishDate"));
					}
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: return select information system name cases 11,12,18
	 * 
	 * @param mySql
	 * @return
	 */
	public static ArrayList<String> returnInformationSystemNames(Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		String query = "SELECT InformationSystemName FROM icm.request;";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				while (rst.next()) {
					if (!(returnDB.contains(rst.getString("InformationSystemName"))))
						returnDB.add(rst.getString("InformationSystemName"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: return engineers from user table cases 19,40,49,86
	 * 
	 * @param egineersID
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getEngineersInfo(ArrayList<String> egineersID, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		ListIterator<String> iter = egineersID.listIterator();
		String userID = null;

		if (mySql != null) {
			try {
				while (iter.hasNext()) {
					userID = iter.next();
					String query = "SELECT FullName,UserDepartment FROM icm.user WHERE UserID='" + userID + "';";
					stm = mySql.createStatement();
					ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

					if (!rst.next()) {
						returnDB.add("false");
					}
					rst.previous();

					if (rst.next()) {
						returnDB.add(userID);
						returnDB.add(rst.getString("FullName"));
						returnDB.add(rst.getString("UserDepartment"));
					}
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: check for execution leader if there is a failure case:20
	 * 
	 * @param query1
	 * @param query2
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> checkForExecutionLeader(String query1, String query2, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst1 = ((java.sql.Statement) stm).executeQuery(query1); // seek for failure report

				if (!rst1.next()) {
					returnDB.add("0");
					returnDB.add("null");
				} else {
					returnDB.add("1");
					returnDB.add(rst1.getString("FailureDescription"));
				}

				stm = mySql.createStatement();
				ResultSet rst2 = ((java.sql.Statement) stm).executeQuery(query2);

				if (rst2.next()) {
					if (rst2.getString("AskPhaseDuration") == null) { // seek for Execution Duration
						returnDB.add("0"); // there no time duration yet
						returnDB.add("null");
					} else {
						returnDB.add("1"); // there is time duration
						returnDB.add(rst2.getString("AskPhaseDuration"));
					}

					if (rst2.getString("AskPhaseExtension") == null) { // seek for Execution Extension
						returnDB.add("0"); // there no time extension
						returnDB.add("null");
					} else {
						returnDB.add("1"); // there is time extension
						returnDB.add(rst2.getString("AskPhaseExtension"));
					}

					if (rst2.getString("DurationApprovedByInspector").equals("Empty")) { // seek for Duration approval
						returnDB.add("0"); // duration is not confirmed by inspector
					} else if (rst2.getString("DurationApprovedByInspector").equals("Accepted")) {
						returnDB.add("1"); // duration is confirmed by inspector
					} else if (rst2.getString("DurationApprovedByInspector").equals("Denied")) {
						returnDB.add("2"); // duration is denied by inspector
					}

					if (rst2.getString("ExtensionApprovedByInspector").equals("Empty")) { // seek for Extension approval
						returnDB.add("0"); // Extension is not confirmed by inspector
					} else if (rst2.getString("ExtensionApprovedByInspector").equals("Accepted")) {
						returnDB.add("1"); // Extension is confirmed by inspector
					} else if (rst2.getString("ExtensionApprovedByInspector").equals("Denied")) {
						returnDB.add("2"); // Extension is denied by inspector
					}

				}

			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: execution leader get evaluation report case 21
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getEvaluationReport(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next()) {
					returnDB.add(rst.getString("ChangeLocation"));
					returnDB.add(rst.getString("ChangeDescription"));
					returnDB.add(rst.getString("ChangeAdvantages"));
					returnDB.add(rst.getString("ChangeConstraintsAndRisks"));
					returnDB.add(rst.getString("ExecutionDuration"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: step up to the next phase cases 22,28
	 * 
	 * @param RNumber
	 * @param treatedBy
	 * @param nextPhase
	 * @param mySql
	 * @return
	 */
	public static ArrayList<String> goToNextPhase(String RNumber, String treatedBy, String nextPhase,
			Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		String treatedBy1 = treatedBy.substring(1, treatedBy.length() - 1);
		String query1 = "UPDATE icm.request SET TreatedBy = '" + treatedBy1 + "' , CurrentPhase = '" + nextPhase
				+ "' WHERE RNumber ='" + RNumber + "';";
		String query2 = "INSERT INTO icm.phase (RNumber,PhaseName) VALUES ('" + RNumber + "','" + nextPhase + "');";

		// check if the request already was in this next phase
		if (requestPhaseExsistInPhaseTable(RNumber, nextPhase, mySql)) {
			returnDB = notFirstTimeGoingToThisPhase(RNumber, nextPhase, query1, mySql);
		}

		else {
			if (mySql != null) {
				try {
					// set new current phase name for the request in request table
					stm = mySql.createStatement();
					int x1 = ((java.sql.Statement) stm).executeUpdate(query1); // update

					// create new row for request specific phase
					stm = mySql.createStatement();
					int x2 = ((java.sql.Statement) stm).executeUpdate(query2); // update

					if (x1 > 0 && x2 > 0) {
						System.out.println("updated");
						returnDB.add("true");

					} else {
						System.out.println("false");
						returnDB.add("false");
					}
				} catch (Exception e) {
				}
			}
		}
		return returnDB;
	}

	/**
	 * func: find who will treat the next phase( for each engineer)
	 * 
	 * @param query
	 * @param treater
	 * @param mySql
	 * @return result
	 */
	public static String findWhoTreatsNextPhase(String query, String treater, Connection mySql) {
		String result = null;

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (rst.next()) {
					result = rst.getString(treater);
				}

			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * func: check if the request already have been in this phase
	 * 
	 * @param RNumber
	 * @param nextPhase
	 * @param mySql
	 * @return true/false
	 */
	public static boolean requestPhaseExsistInPhaseTable(String RNumber, String nextPhase, Connection mySql) {
		String query = "SELECT ConfirmedPhaseDuration FROM icm.phase WHERE RNumber ='" + RNumber + "' AND PhaseName = '"
				+ nextPhase + "';";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (rst.next()) {
					return true;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	/**
	 * func: if isnt the first time this request goes to this phase -> update
	 * existing row
	 * 
	 * @param RNumber
	 * @param nextPhase
	 * @param query1
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> notFirstTimeGoingToThisPhase(String RNumber, String nextPhase, String query1,
			Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		String query2 = "UPDATE icm.phase SET AskPhaseDuration = NULL , ConfirmedPhaseDuration = NULL , WaitingForApproval = 'Empty' , DurationApprovedByInspector='Empty' WHERE RNumber = '"
				+ RNumber + "' AND PhaseName = '" + nextPhase + "';";

		if (mySql != null) {
			try {
				// PreparedStatement ps = mySql.prepareStatement(query2);

				// ps.setNull(1, 91); // 91 is int of sql type date
				// ps.setNull(2, 91);

				// set new current phase name for the request in request table
				stm = mySql.createStatement();
				int x1 = ((java.sql.Statement) stm).executeUpdate(query1); // update

				stm = mySql.createStatement();
				int x2 = ((java.sql.Statement) stm).executeUpdate(query2); // update

				// int x2 = ps.executeUpdate(); // update

				if (x1 > 0 && x2 > 0) {
					System.out.println("updated");
					returnDB.add("true");

				} else {
					System.out.println("false");
					returnDB.add("false");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnDB;
	}

	/**
	 * func: auto set phase time duration
	 * 
	 * @param RNumber
	 * @param phaseName
	 * @param plusDays
	 * @param mySql
	 * @return true/false
	 */
	public static boolean autoSetDurationDateInPhaseTable(String RNumber, String phaseName, long plusDays,
			Connection mySql) {
		int x1 = 0;
		LocalDate date = LocalDate.now();
		date = date.plusDays(plusDays);

		String query1 = "UPDATE icm.request FinishDate = '" + date + "' WHERE RNumber = '" + RNumber + "';";
		updateDetails(query1, mySql);

		String query = "UPDATE icm.phase SET ConfirmedPhaseDuration = '" + date + "' WHERE RNumber = '" + RNumber
				+ "' AND PhaseName = '" + phaseName + "';";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				x1 = ((java.sql.Statement) stm).executeUpdate(query); // update

			} catch (Exception e) {
			}
		}
		if (x1 > 0) {
			System.out.println("updated");
			return true;

		} else {
			System.out.println("false");
			return false;
		}
	}

	/**
	 * func: send time duration for approval cases 23,48
	 * 
	 * @param RNumber
	 * @param phaseName
	 * @param askPhaseDuration
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> sendTimeDurationForApproval(String RNumber, String phaseName,
			String askPhaseDuration, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		String query = "UPDATE icm.phase SET AskPhaseDuration = '" + askPhaseDuration
				+ "' , WaitingForApproval= 'DurationApproval' , DurationApprovedByInspector = 'Empty' WHERE RNumber ='"
				+ RNumber + "' AND PhaseName= '" + phaseName + "';";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				int x = ((java.sql.Statement) stm).executeUpdate(query); // update

				if (x > 0) {
					System.out.println("updated");
					returnDB.add("true");

				} else {
					System.out.println("false");
					returnDB.add("false");
				}

			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: send time extension for approval cases 24,26
	 * 
	 * @param RNumber
	 * @param phaseName
	 * @param askPhaseExtension
	 * @param extensionReason
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> sendTimeExtensionForApproval(String RNumber, String phaseName,
			String askPhaseExtension, String extensionReason, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		String query = "UPDATE icm.phase SET AskPhaseExtension = '" + askPhaseExtension
				+ "' , WaitingForApproval= 'ExtensionApproval' , ExtensionApprovedByInspector='Empty' , ExtensionReason = '"
				+ extensionReason + "' WHERE RNumber ='" + RNumber + "'AND PhaseName= '" + phaseName + "';";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				int x = ((java.sql.Statement) stm).executeUpdate(query); // update

				if (x > 0) {
					System.out.println("updated");
					returnDB.add("true");

				} else {
					System.out.println("false");
					returnDB.add("false");
				}

			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: check for examiner OR chairman cases 25,32
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> checkForExaminerOrChairman(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query); // seek for time extension

				if (rst.next()) {
					if (rst.getString("AskPhaseExtension") == null) { // seek for Extension Duration
						returnDB.add("0"); // there no time extension yet
						returnDB.add("null");
					} else {
						returnDB.add("1"); // there is time extension
						returnDB.add(rst.getString("AskPhaseExtension"));
					}

					if (rst.getString("ConfirmedPhaseDuration") == null) { // seek for automatic confirmed Duration
						returnDB.add("null"); // there no time duration yet
					} else {
						returnDB.add(rst.getString("ConfirmedPhaseDuration")); // there is time duration
					}

					if (rst.getString("ExtensionApprovedByInspector").equals("Empty")) { // seek for Extension approval
						returnDB.add("0"); // Extension is not confirmed by inspector
					} else if (rst.getString("ExtensionApprovedByInspector").equals("Accepted")) {
						returnDB.add("1"); // Extension is confirmed by inspector
					} else if (rst.getString("ExtensionApprovedByInspector").equals("Denied")) {
						returnDB.add("2"); // Extension is denied by inspector
					}

				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: add new failure report case 27
	 * 
	 * @param RNumber
	 * @param failureDescription
	 * @param mySql
	 * @return result
	 */
	public static String addNewFailureReport(String RNumber, String failureDescription, Connection mySql) {
		String result = null;
		String query = null;

		// check if there is a failure report already
		if (failureReportExistInTable(RNumber, mySql)) {
			query = "UPDATE icm.test_failure SET FailureDescription='" + failureDescription + "' WHERE RNumber = '"
					+ RNumber + "';";
		} else { // there no failure report yet
			query = "INSERT INTO icm.test_failure (RNumber,FailureDescription) VALUES ('" + RNumber + "','"
					+ failureDescription + "');";
		}
		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				int x = ((java.sql.Statement) stm).executeUpdate(query); // update

				if (x > 0) {
					System.out.println("updated");
					result = "true";
				} else {
					result = "false";
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * func: check if failure report already exist in table
	 * 
	 * @param RNumber
	 * @param mySql
	 * @return true/false
	 */
	public static boolean failureReportExistInTable(String RNumber, Connection mySql) {
		String query = "SELECT FailureDescription FROM icm.test_failure WHERE RNumber ='" + RNumber + "';";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (rst.next()) {
					return true;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	/**
	 * func: insert new request to close
	 * 
	 * @param RNumber
	 * @param reason
	 * @param mySql
	 */
	public static void insertNewRequestToClose(String RNumber, String reason, Connection mySql) {

		String query = "INSERT INTO icm.requests_to_close (RNumber,CompletedOrDenied) VALUES ('" + RNumber + "','"
				+ reason + "');";

		if (mySql != null) {
			try {
				// create new row in request to close
				stm = mySql.createStatement();
				int x = ((java.sql.Statement) stm).executeUpdate(query); // update

				if (x > 0) {
					System.out.println("updated");
				} else {
					System.out.println("false");
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * func: return all engineers permissions except the IT department manager case
	 * 29
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> engineersPermissions(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				// EngineerID,Inspector,Assessor,Chairman,CommitteeMember,Examiner,ExecutionLeader
				while (rst.next()) {
					returnDB.add(rst.getString("EngineerID"));
					returnDB.add(rst.getString("Inspector"));
					returnDB.add(rst.getString("Assessor"));
					returnDB.add(rst.getString("Chairman"));
					returnDB.add(rst.getString("CommitteeMember"));
					returnDB.add(rst.getString("Examiner"));
					returnDB.add(rst.getString("ExecutionLeader"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get all engineer ID by permission cases 19,40,49
	 * 
	 * @param permission
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getAllEngineerIDByPermission(String permission, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		String query = null;

		switch (permission) {

		case "ITdepartmentManager":
			query = "SELECT EngineerID FROM icm.permissions WHERE ITdepartmentManager ='1';";
			break;

		case "Inspector":
			query = "SELECT EngineerID FROM icm.permissions WHERE Inspector ='1';";
			break;

		case "Assessor":
			query = "SELECT EngineerID FROM icm.permissions WHERE Assessor ='1';";
			break;

		case "Chairman":
			query = "SELECT EngineerID FROM icm.permissions WHERE Chairman ='1';";
			break;

		case "CommitteeMember":
			query = "SELECT EngineerID FROM icm.permissions WHERE CommitteeMember ='1';";
			break;

		case "ExecutionLeader":
			query = "SELECT EngineerID FROM icm.permissions WHERE ExecutionLeader ='1';";
			break;

		}

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				// all EngineerID from icm.permissions
				while (rst.next()) {
					returnDB.add(rst.getString("EngineerID"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get all the details of engineers user in the system case 30
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> allEngineersdetailsUser(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				// UserID,UserName,Password,FullName,Email,UserDepartment,UserRole
				while (rst.next()) {
					returnDB.add(rst.getString("UserID"));
					returnDB.add(rst.getString("UserName"));
					returnDB.add(rst.getString("Password"));
					returnDB.add(rst.getString("FullName"));
					returnDB.add(rst.getString("Email"));
					returnDB.add(rst.getString("UserDepartment"));
					returnDB.add(rst.getString("UserRole"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: insert new evaluation report case 31
	 * 
	 * @param RNumber
	 * @param ChangeLocation
	 * @param ChangeDescription
	 * @param ChangeAdvantages
	 * @param ChangeConstraintsAndRisks
	 * @param ExecutionDuration
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> insertNewEvaluationReport(String RNumber, String ChangeLocation,
			String ChangeDescription, String ChangeAdvantages, String ChangeConstraintsAndRisks,
			String ExecutionDuration, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		String query = "INSERT INTO icm.evaluation_report (RNumber,ChangeLocation,ChangeDescription,ChangeAdvantages,ChangeConstraintsAndRisks,ExecutionDuration) VALUES ('"
				+ RNumber + "','" + ChangeLocation + "','" + ChangeDescription + "','" + ChangeAdvantages + "','"
				+ ChangeConstraintsAndRisks + "','" + ExecutionDuration + "');";

		if (check4EvaluationReport(RNumber, mySql).contains("true")) {
			query = "UPDATE icm.evaluation_report " + "SET ChangeLocation='" + ChangeLocation + "',ChangeDescription='"
					+ ChangeDescription + "'," + "ChangeAdvantages='" + ChangeAdvantages
					+ "',ChangeConstraintsAndRisks='" + ChangeConstraintsAndRisks + "',ExecutionDuration='"
					+ ExecutionDuration + "',WaitingForTreatment='0'  WHERE RNumber = '" + RNumber + "';";
		}

		if (mySql != null) {
			try {
				// create new row in request to close
				stm = mySql.createStatement();
				int x = ((java.sql.Statement) stm).executeUpdate(query); // update

				if (x > 0) {
					System.out.println("updated");
					returnDB.add("true");
				} else {
					System.out.println("false");
					returnDB.add("false");
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get original requests information case 35
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getOriginalRequestsInformation(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query); // seek for all requests information

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				while (rst.next()) {
					returnDB.add(rst.getString("RNumber"));
					returnDB.add(rst.getString("ApplicantID"));
					returnDB.add(rst.getString("InformationSystemName"));
					returnDB.add(rst.getString("ExistingDescription"));
					returnDB.add(rst.getString("ChangeDescription"));
					returnDB.add(rst.getString("Reason"));
					returnDB.add(rst.getString("Comments"));
					returnDB.add(rst.getString("TreatedBy"));
					returnDB.add(rst.getString("Status"));
					returnDB.add(rst.getString("CurrentPhase"));
					returnDB.add(rst.getString("StartDate"));
					returnDB.add(rst.getString("FinishDate"));

				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get committee members and return userid + username in returnDB case 36
	 * 
	 * @param rnumber
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> prepareExaminersForNomination(String rnumber, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		ArrayList<String> engineersID = new ArrayList<>();

		engineersID.add(MySqlQuery.findCommitteMem2(rnumber, mySql));
		engineersID.add(MySqlQuery.findCommitteMem3(rnumber, mySql));

		ListIterator<String> iter = engineersID.listIterator();

		while (iter.hasNext()) {
			String temp = iter.next();
			returnDB.add(fullNameFromUser(temp, mySql));
			returnDB.add(temp);
		}
		return returnDB;
	}

	/**
	 * func: return the user name of specific engineer ID
	 * 
	 * @param id
	 * @param mySql
	 * @return username
	 */
	public static String userNameFromUser(String id, Connection mySql) {

		String fullname = null;
		String query = "SELECT UserName FROM icm.user WHERE UserID='" + id + "';";

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					fullname = "false";
				}
				rst.previous();

				if (rst.next()) {
					fullname = rst.getString("UserName");
				}
			} catch (Exception e) {
			}
		}
		return fullname;
	}

	/**
	 * func: return the full name of specific engineerID
	 * 
	 * @param id
	 * @param mySql
	 * @return fullname
	 */
	public static String fullNameFromUser(String id, Connection mySql) {

		String fullname = null;
		String query = "SELECT FullName FROM icm.user WHERE UserID='" + id + "';";

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					fullname = "false";
				}
				rst.previous();

				if (rst.next()) {
					fullname = rst.getString("FullName");
				}
			} catch (Exception e) {
			}
		}
		return fullname;
	}

	/**
	 * func: get rnumbers of specific information system
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> rnumbersOfSpecificISN(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				while (rst.next()) {
					returnDB.add(rst.getString("RNumber"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get all the roles in system and return user id + full name + ISN name
	 * 
	 * @param RNumber
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> allRolesOfSpecificRNumber(String RNumber, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		int flag = 0;

		String query = "SELECT AssessorID,ChairmanID,CommitteeMember2ID,CommitteeMember3ID,ExaminerID,ExecutionLeaderID FROM icm.request_role WHERE RNumber='"
				+ RNumber + "' ;";

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next()) {
					if (rst.getString("AssessorID") != null) {
						returnDB.add(rst.getString("AssessorID"));
						returnDB.add(fullNameFromUser(rst.getString("AssessorID"), mySql));
						returnDB.add(userDepartmentFromUser(rst.getString("AssessorID"), mySql));
						returnDB.add("Assessor");
						flag = 1;
					}

					if (rst.getString("ChairmanID") != null) {
						returnDB.add(rst.getString("ChairmanID"));
						returnDB.add(fullNameFromUser(rst.getString("ChairmanID"), mySql));
						returnDB.add(userDepartmentFromUser(rst.getString("ChairmanID"), mySql));
						returnDB.add("Chairman");
						flag = 1;
					}

					if (rst.getString("CommitteeMember2ID") != null) {
						returnDB.add(rst.getString("CommitteeMember2ID"));
						returnDB.add(fullNameFromUser(rst.getString("CommitteeMember2ID"), mySql));
						returnDB.add(userDepartmentFromUser(rst.getString("CommitteeMember2ID"), mySql));
						returnDB.add("CommitteeMember");
						flag = 1;
					}

					if (rst.getString("CommitteeMember3ID") != null) {
						returnDB.add(rst.getString("CommitteeMember3ID"));
						returnDB.add(fullNameFromUser(rst.getString("CommitteeMember3ID"), mySql));
						returnDB.add(userDepartmentFromUser(rst.getString("CommitteeMember3ID"), mySql));
						returnDB.add("CommitteeMember");
						flag = 1;
					}

					if (rst.getString("ExaminerID") != null) {
						returnDB.add(rst.getString("ExaminerID"));
						returnDB.add(fullNameFromUser(rst.getString("ExaminerID"), mySql));
						returnDB.add(userDepartmentFromUser(rst.getString("ExaminerID"), mySql));
						returnDB.add("Examiner");
						flag = 1;

					}

					if (rst.getString("ExecutionLeaderID") != null) {
						returnDB.add(rst.getString("ExecutionLeaderID"));
						returnDB.add(fullNameFromUser(rst.getString("ExecutionLeaderID"), mySql));
						returnDB.add(userDepartmentFromUser(rst.getString("ExecutionLeaderID"), mySql));
						returnDB.add("ExecutionLeader");
						flag = 1;
					}

					if (flag == 0)
						returnDB.add("false");

				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: find specific role in icm.request_role table case 38 84
	 * 
	 * @param rnumber
	 * @param role
	 * @param mySql
	 * @return returnDB
	 */
	public static String findHIM(String rnumber, String role, Connection mySql) {
		String returnDB = null;

		String query = "SELECT " + role + " FROM icm.request_role WHERE RNumber= '" + rnumber + "' ;";

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB = "false";
				}
				rst.previous();

				if (rst.next()) {
					switch (role) {
					case "InspectorID":
						if (rst.getString("InspectorID") != null)
							returnDB = rst.getString("InspectorID");
						break;

					case "AssessorID":
						if (rst.getString("AssessorID") != null)
							returnDB = rst.getString("AssessorID");
						break;

					case "ChairmanID":
						if (rst.getString("ChairmanID") != null)
							returnDB = rst.getString("ChairmanID");
						break;

					case "CommitteeMember2ID":
						if (rst.getString("CommitteeMember2ID") != null)
							returnDB = rst.getString("CommitteeMember2ID");
						break;

					case "CommitteeMember3ID":
						if (rst.getString("CommitteeMember3ID") != null)
							returnDB = rst.getString("CommitteeMember3ID");
						break;

					case "ExaminerID":
						if (rst.getString("ExaminerID") != null)
							returnDB = rst.getString("ExaminerID");
						break;

					case "ExecutionLeaderID":
						if (rst.getString("ExecutionLeaderID") != null)
							returnDB = rst.getString("ExecutionLeaderID");
						break;
					}
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: return the user department of specific engineerID
	 * 
	 * @param id
	 * @param mySql
	 * @return userDept
	 */
	public static String userDepartmentFromUser(String id, Connection mySql) {

		String userDept = null;
		String query = "SELECT UserDepartment FROM icm.user WHERE UserID='" + id + "';";

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					userDept = "false";
				}
				rst.previous();

				if (rst.next()) {
					userDept = rst.getString("UserDepartment");
				}
			} catch (Exception e) {
			}
		}
		return userDept;
	}

	/**
	 * func: looking for missing roles of specific RNumber case 39
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> missingRolesOfSpecificRNumber(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (rst.next()) {
					if (rst.getString("AssessorID") == null)
						returnDB.add("Assessor");

					if (rst.getString("ChairmanID") == null)
						returnDB.add("Chairman");

					if (rst.getString("CommitteeMember2ID") == null || rst.getString("CommitteeMember3ID") == null)
						returnDB.add("CommitteeMember");

					if (rst.getString("ExaminerID") == null)
						returnDB.add("Examiner");

					if (rst.getString("ExecutionLeaderID") == null)
						returnDB.add("ExecutionLeader");

				}

				if (returnDB.isEmpty())
					returnDB.add("false");

			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get committe member2 case 40
	 * 
	 * @param RNumber
	 * @param mySql
	 * @return returnDB
	 */
	public static String findCommitteMem2(String RNumber, Connection mySql) {
		String returnDB = null;

		String query = "SELECT CommitteeMember2ID FROM icm.request_role WHERE RNumber= '" + RNumber + "' ;";

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB = "false";
				}
				rst.previous();

				if (rst.next()) {
					if (rst.getString("CommitteeMember2ID") != null)
						returnDB = rst.getString("CommitteeMember2ID");
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get committe member3 case 40
	 * 
	 * @param RNumber
	 * @param mySql
	 * @return returnDB
	 */
	public static String findCommitteMem3(String RNumber, Connection mySql) {
		String returnDB = null;

		String query = "SELECT CommitteeMember3ID FROM icm.request_role WHERE RNumber= '" + RNumber + "' ;";

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB = "false";
				}
				rst.previous();

				if (rst.next()) {
					if (rst.getString("CommitteeMember3ID") != null)
						returnDB = rst.getString("CommitteeMember3ID");
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: chairman asks for more information from assessor case 41
	 * 
	 * @param RNumber
	 * @param extraInfo
	 * @param mySql
	 * @return result
	 */
	public static String askForExtraEvaluationInformation(String RNumber, String extraInfo, Connection mySql) {
		String result = "";
		String query = "UPDATE icm.evaluation_report SET ExtraInformation = '" + extraInfo
				+ "' , WaitingForTreatment = '1' WHERE RNumber = '" + RNumber + "';";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				int x = ((java.sql.Statement) stm).executeUpdate(query); // update

				if (x > 0) {
					System.out.println("updated");
					result = "true";
				} else {
					System.out.println("false");
					result = "false";
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * func: assessor run his tests case 43
	 * 
	 * @param query1
	 * @param query2
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> checkForAssessor(String query1, String query2, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst1 = ((java.sql.Statement) stm).executeQuery(query1); // seek for time extension

				if (!rst1.next()) {
					returnDB.add("false");
				}
				rst1.previous();

				if (rst1.next()) {
					if (rst1.getString("DurationApprovedByInspector").equals("Empty")) { // seek for duration approval
						returnDB.add(" "); // duration is not confirmed by inspector
					} else if (rst1.getString("DurationApprovedByInspector").equals("Accepted")) {
						returnDB.add("true"); // duration is confirmed by inspector
					} else if (rst1.getString("DurationApprovedByInspector").equals("Denied")) {
						returnDB.add("false"); // duration is denied by inspector
					}

					if (rst1.getString("ConfirmedPhaseDuration") != null) // seek for asked phase duration
						returnDB.add(rst1.getString("ConfirmedPhaseDuration"));
					else if (rst1.getString("AskPhaseDuration") == null)
						returnDB.add(" ");
					else
						returnDB.add(rst1.getString("AskPhaseDuration"));

					stm = mySql.createStatement();
					ResultSet rst2 = ((java.sql.Statement) stm).executeQuery(query2); // seek for waiting for treatment
																						// evaluation report

					if (!rst2.next()) {
						returnDB.add("false");
					}
					rst2.previous();

					if (rst2.next()) {
						if (rst2.getString("WaitingForTreatment").equals("1"))
							returnDB.add("true");
						else
							returnDB.add("false");
					}
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: assessor run his tests case 46
	 * 
	 * @param query1
	 * @param query2
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> checkForAssessor2(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query); // seek for time extension

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next()) {
					if (rst.getString("AskPhaseExtension") == null) { // seek for Extension Duration
						returnDB.add("true"); // there no time extension yet, so he can ask
					} else {
						returnDB.add("false"); // there is time extension, so he can't ask
					}

					if (rst.getString("ConfirmedPhaseDuration") == null) { // seek for confirmed Duration
						returnDB.add("null"); // there no time duration yet
					} else {
						returnDB.add(rst.getString("ConfirmedPhaseDuration")); // there is time duration
					}
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: add new nomination for request cases 44,47,85
	 * 
	 * @param rnumber
	 * @param role
	 * @param userid
	 * @param mySql
	 * @return returnDB
	 * @throws SQLException
	 */
	public static String addNewNomination(String rnumber, String role, String userid, Connection mySql)
			throws SQLException {
		String returnDB = null, query = null;
		ArrayList<String> answerCM = new ArrayList<>();

		switch (role) {
		case "Assessor":
			query = "UPDATE icm.request_role SET AssessorID='" + userid + "' WHERE RNumber ='" + rnumber + "';";
			break;

		case "Chairman":
			query = "UPDATE icm.request_role SET ChairmanID='" + userid + "' WHERE RNumber ='" + rnumber + "';";
			break;

		case "CommitteeMember":
			answerCM = checkWhichCommitteMember(rnumber, mySql);
			if (answerCM.contains("2") && answerCM.contains("3"))
				query = "UPDATE icm.request_role SET CommitteeMember2ID='" + userid + "' WHERE RNumber ='" + rnumber
						+ "';";
			else if (answerCM.contains("2"))
				query = "UPDATE icm.request_role SET CommitteeMember2ID='" + userid + "' WHERE RNumber ='" + rnumber
						+ "';";
			else if (answerCM.contains("3"))
				query = "UPDATE icm.request_role SET CommitteeMember3ID='" + userid + "' WHERE RNumber ='" + rnumber
						+ "';";
			break;

		case "ExecutionLeader":
			query = "UPDATE icm.request_role SET ExecutionLeaderID='" + userid + "' WHERE RNumber ='" + rnumber + "';";
			break;

		case "Examiner":
			query = "UPDATE icm.request_role SET ExaminerID='" + userid + "' WHERE RNumber ='" + rnumber + "';";
			break;

		case "Inspector":
			query = "UPDATE icm.request_role SET InspectorID='" + userid + "' WHERE RNumber ='" + rnumber + "';";
			break;
		}

		if (updateDetails(query, mySql).equals("true"))
			returnDB = "succeeded";
		else
			returnDB = "failed";

		return returnDB;
	}

	/**
	 * func: update details(role for example) in database
	 * 
	 * @param query
	 * @param mySql
	 * @return result
	 */
	public static String updateDetails(String query, Connection mySql) {
		String result = null;

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				int x = ((java.sql.Statement) stm).executeUpdate(query);
				if (x > 0) {
					result = "true";
				} else {
					result = "false";
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * func: check which committee member is empty 2/3
	 * 
	 * @param rnumber
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> checkWhichCommitteMember(String rnumber, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		String query = "SELECT CommitteeMember2ID,CommitteeMember3ID FROM icm.request_role WHERE RNumber='" + rnumber
				+ "' ;";

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next()) {
					if (rst.getString("CommitteeMember2ID") == null) // empty space in committee member 2
						returnDB.add("2");
					if (rst.getString("CommitteeMember3ID") == null) // empty space in committee member 3
						returnDB.add("3");
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: committee member get confirmed phase duration case 45
	 * 
	 * @param RNumber
	 * @param mySql
	 * @return result
	 */
	public static String committeeMemberChecks(String RNumber, Connection mySql) {
		String result = null;

		String query = "SELECT ConfirmedPhaseDuration FROM icm.phase WHERE RNumber = '" + RNumber + "';";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					result = "false";
				}
				rst.previous();

				if (rst.next()) {
					if (rst.getString("ConfirmedPhaseDuration") == null) { // seek for automatic confirmed Duration
						result = "null"; // there no time duration yet
					} else {
						result = rst.getString("ConfirmedPhaseDuration"); // there is time duration
					}
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * func:get engineer in role x cases 49,84 in case 84 added inspector and
	 * committee member cases!
	 * 
	 * @param rnumber
	 * @param role
	 * @param mySql
	 * @return
	 */
	public static ArrayList<String> findEngineerInRole(String rnumber, String role, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		String query = null;

		switch (role) {
		case "Assessor":
			query = "SELECT AssessorID FROM icm.request_role WHERE RNumber ='" + rnumber + "';";
			break;

		case "Chairman":
			query = "SELECT ChairmanID FROM icm.request_role WHERE RNumber ='" + rnumber + "';";
			break;

		case "Inspector":
			query = "SELECT InspectorID FROM icm.request_role WHERE RNumber ='" + rnumber + "';";
			break;

		case "CommitteeMember":
			query = "SELECT CommitteeMember2ID,CommitteeMember3ID FROM icm.request_role WHERE RNumber ='" + rnumber
					+ "';";
			break;

		case "ExecutionLeader":
			query = "SELECT ExecutionLeaderID FROM icm.request_role WHERE RNumber ='" + rnumber + "';";
			break;

		case "Examiner":
			query = "SELECT ExaminerID FROM icm.request_role WHERE RNumber ='" + rnumber + "';";
			break;
		}

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next()) {

					switch (role) {
					case "Assessor":
						returnDB.add(rst.getString("AssessorID"));
						break;

					case "Chairman":
						returnDB.add(rst.getString("ChairmanID"));
						break;

					case "ExecutionLeader":
						returnDB.add(rst.getString("ExecutionLeaderID"));
						break;

					case "Inspector":
						returnDB.add(rst.getString("InspectorID"));
						break;

					case "Examiner":
						returnDB.add(rst.getString("ExaminerID"));
						break;

					case "CommitteeMember":
						if (rst.getString("CommitteeMember2ID") != null)
							returnDB.add(rst.getString("CommitteeMember2ID"));
						if (rst.getString("CommitteeMember3ID") != null)
							returnDB.add(rst.getString("CommitteeMember3ID"));
						break;
					}
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: inspector gets all closed requests information case 51
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getClosedRequestsInformation(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				while (rst.next()) {
					returnDB.add(rst.getString("RNumber"));
					returnDB.add(rst.getString("ApplicantID"));
					returnDB.add(rst.getString("InformationSystemName"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: inspector gets all active requests information case 52
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getActiveRequestsInformation(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				while (rst.next()) {
					returnDB.add(rst.getString("RNumber"));
					returnDB.add(rst.getString("ApplicantID"));
					returnDB.add(rst.getString("InformationSystemName"));
					returnDB.add(rst.getString("CurrentPhase"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get current status case 53
	 * 
	 * @param RNumber
	 * @param mySql
	 * @return result
	 */
	public static String getStatus(String RNumber, Connection mySql) {
		String result = null;

		String query = "SELECT Status FROM icm.request WHERE RNumber='" + RNumber + "';";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) { // there no request with this RNumber
					result = "false";
				}
				rst.previous();

				if (rst.next()) {
					result = rst.getString("Status");
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * func: set current status case 53
	 * 
	 * @param RNumber
	 * @param statusToSet
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> setStatus(String RNumber, String statusToSet, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		String query = "UPDATE icm.request SET Status = '" + statusToSet + "' WHERE RNumber = '" + RNumber + "';";

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				int x = ((java.sql.Statement) stm).executeUpdate(query);

				if (x > 0) {
					returnDB.add("succeeded");
				} else {
					returnDB.add("failed");
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: check if extra information exist in evaluation report table
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> checkForExtraInfo(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query); // seek for extra information

				if (rst.next()) {
					if (rst.getString("ExtraInformation") == null)
						returnDB.add("false"); // extra information isn't in evaluation report table
					else
						returnDB.add("true");// extra information is in evaluation report table
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get extra information exist in evaluation report table
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getForExtraInfo(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query); // seek for extra information

				if (!rst.next()) { // there no request with this RNumber
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next())
					returnDB.add(rst.getString("ExtraInformation"));
				else
					returnDB.add("null");

			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get all details of open new request
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getAllDetailsOfRequest(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (rst.next()) {
					returnDB.add(rst.getString("InformationSystemName"));
					returnDB.add(rst.getString("ExistingDescription"));
					returnDB.add(rst.getString("ChangeDescription"));
					returnDB.add(rst.getString("Reason"));
					returnDB.add(rst.getString("Comments"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: check if evaluation report is exist in table
	 * 
	 * @param RNumber
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> check4EvaluationReport(String RNumber, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		String query = "SELECT * FROM icm.evaluation_report WHERE RNumber= '" + RNumber + "';";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) { // there no request with this RNumber
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next()) {
					returnDB.add("true");
					returnDB.add(rst.getString("RNumber"));
					returnDB.add(rst.getString("ChangeLocation"));
					returnDB.add(rst.getString("ChangeDescription"));
					returnDB.add(rst.getString("ChangeAdvantages"));
					returnDB.add(rst.getString("ChangeConstraintsAndRisks"));
					returnDB.add(rst.getString("ExecutionDuration"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: return the current phases and the finish date of the rnumber
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getCurrentPhaseANDFinishDate(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) { // there no request with this RNumber
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next()) {
					returnDB.add(rst.getString("CurrentPhase"));
					if (rst.getString("FinishDate") != null)
						returnDB.add(rst.getString("FinishDate"));
					else
						returnDB.add("false");
				}

			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get rnumbers of engineer specific permission and check the status of
	 * each rnumber
	 * 
	 * @param userid
	 * @param permission
	 * @param mySql
	 * @return temp
	 */
	public static ArrayList<String> getActiveRequestsANDCheckTheRole(String userid, String permission,
			Connection mySql) {
		String query = null;
		ArrayList<String> returnDB = new ArrayList<>();
		ArrayList<String> temp = new ArrayList<>();

		switch (permission) {
		case "Inspector":
			query = "SELECT RNumber FROM icm.request_role WHERE InspectorID ='" + userid + "';";
			break;

		case "Assessor":
			query = "SELECT RNumber FROM icm.request_role WHERE AssessorID ='" + userid + "';";
			break;

		case "Chairman":
			query = "SELECT RNumber FROM icm.request_role WHERE ChairmanID ='" + userid + "';";
			break;

		case "CommitteeMember":
			query = "SELECT RNumber FROM icm.request_role WHERE CommitteeMember2ID ='" + userid
					+ "' OR CommitteeMember3ID ='" + userid + "';";
			break;

		case "ExecutionLeader":
			query = "SELECT RNumber FROM icm.request_role WHERE ExecutionLeaderID ='" + userid + "';";
			break;

		case "Examiner":
			query = "SELECT RNumber FROM icm.request_role WHERE ExaminerID ='" + userid + "';";
			break;
		}

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst1 = ((java.sql.Statement) stm).executeQuery(query);

				while (rst1.next())
					returnDB.add(rst1.getString("RNumber"));

				ListIterator<String> aListIterator = returnDB.listIterator();

				while (aListIterator.hasNext()) {
					String query_1 = "SELECT RNumber FROM icm.request WHERE RNumber='" + aListIterator.next()
							+ "' AND Status = 'Active' ;";

					stm = mySql.createStatement();
					ResultSet rst2 = ((java.sql.Statement) stm).executeQuery(query_1);

					if (rst2.next()) // check if the rnumber is active
						temp.add(rst2.getString("RNumber"));

				}
			} catch (Exception e) {
			}
		}

		if (temp.isEmpty()) {
			return temp;
		}
		temp.add("false");
		return temp;
	}

	/**
	 * func: return all the rnumbers that waiting for inspector approval for phase
	 * duration case 68
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getAskPhaseDurationWait4Inspector(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) { // there no request with this RNumber
					returnDB.add("false");
				}
				rst.previous();

				while (rst.next()) {
					returnDB.add(rst.getString("RNumber"));
					returnDB.add(rst.getString("PhaseName"));
					returnDB.add(rst.getString("AskPhaseDuration"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: return all the rnumbers that in closing stage and wait for inspector
	 * approval case 69
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getCloseRequestWaiting4Inspector(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) { // there no request with this RNumber
					returnDB.add("false");
				}
				rst.previous();

				while (rst.next()) {
					returnDB.add(rst.getString("RNumber"));
					returnDB.add(rst.getString("CompletedOrDenied"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: show to inspector all assessor automatic nominations for approval case
	 * 70
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getAutoAssessorNominationsInfo(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				while (rst.next()) {
					returnDB.add(rst.getString("RNumber"));
					returnDB.add(rst.getString("AssessorID"));
					returnDB.add(fullNameFromUser(rst.getString("AssessorID"), mySql));
					returnDB.add(userDepartmentFromUser(rst.getString("AssessorID"), mySql));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func:get AskPhaseExtension thats wait for inspector to approve case 72
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getAskPhaseExtensionWait4Inspector(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) { // there no request with this RNumber
					returnDB.add("false");
				}
				rst.previous();

				while (rst.next()) {
					returnDB.add(rst.getString("RNumber"));
					returnDB.add(rst.getString("PhaseName"));
					returnDB.add(rst.getString("AskPhaseExtension"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get missiong role in RNumber (IT func)
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> missingRolesOfSpecificRNumberForIT(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next()) {
					if (rst.getString("InspectorID") == null)
						returnDB.add("Inspector");

					if (rst.getString("ChairmanID") == null)
						returnDB.add("Chairman");

					if (rst.getString("CommitteeMember2ID") == null || rst.getString("CommitteeMember3ID") == null)
						returnDB.add("CommitteeMember");
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: get IT roles of specific rnumber case 83
	 * 
	 * @param RNumber
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> ITRolesOfSpecificRNumber(String rnumber, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		int flag = 0;

		String query = "SELECT InspectorID,ChairmanID,CommitteeMember2ID,CommitteeMember3ID FROM icm.request_role WHERE RNumber='"
				+ rnumber + "' ;";

		if (mySql != null) {
			try {
				stm = (Statement) mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next()) {
					if (rst.getString("InspectorID") != null) {
						returnDB.add(rst.getString("InspectorID"));
						returnDB.add(fullNameFromUser(rst.getString("InspectorID"), mySql));
						returnDB.add(userDepartmentFromUser(rst.getString("InspectorID"), mySql));
						returnDB.add("Inspector");
						flag = 1;
					}

					if (rst.getString("ChairmanID") != null) {
						returnDB.add(rst.getString("ChairmanID"));
						returnDB.add(fullNameFromUser(rst.getString("ChairmanID"), mySql));
						returnDB.add(userDepartmentFromUser(rst.getString("ChairmanID"), mySql));
						returnDB.add("Chairman");
						flag = 1;
					}

					if (rst.getString("CommitteeMember2ID") != null) {
						returnDB.add(rst.getString("CommitteeMember2ID"));
						returnDB.add(fullNameFromUser(rst.getString("CommitteeMember2ID"), mySql));
						returnDB.add(userDepartmentFromUser(rst.getString("CommitteeMember2ID"), mySql));
						returnDB.add("CommitteeMember");
						flag = 1;
					}

					if (rst.getString("CommitteeMember3ID") != null) {
						returnDB.add(rst.getString("CommitteeMember3ID"));
						returnDB.add(fullNameFromUser(rst.getString("CommitteeMember3ID"), mySql));
						returnDB.add(userDepartmentFromUser(rst.getString("CommitteeMember3ID"), mySql));
						returnDB.add("CommitteeMember");
						flag = 1;
					}

					if (flag == 0)
						returnDB.add("false");

				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: check if rnumber exsist in finish_date_update table case 64
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> checkFinishDateUpdate(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next()) {
					returnDB.add(rst.getString("InspectorName"));
					returnDB.add(rst.getString("UpdateDescription"));
					returnDB.add(rst.getString("UpdateDate"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: check if user approve to close case 71
	 * 
	 * @param query
	 * @param mySql
	 * @return answer
	 */
	public static String checkApprovedByUser(String query, Connection mySql) {
		String answer = "false";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					answer = "false";
				}
				rst.previous();

				if (rst.next()) {
					if (rst.getInt("ApprovedByUser") == 1)
						answer = "true";
				}
			} catch (Exception e) {
			}
		}
		return answer;
	}

	/**
	 * func: check if exist admin to information system case 89
	 * 
	 * @param query
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> checkInformationSystemAdmin(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				if (rst.next()) {
					if (rst.getString("Moodle") == null)
						returnDB.add("Moodle");
					if (rst.getString("Library") == null)
						returnDB.add("Library");
					if (rst.getString("ClassComputers") == null)
						returnDB.add("ClassComputers");
					if (rst.getString("Laboratory") == null)
						returnDB.add("Laboratory");
					if (rst.getString("FarmComputers") == null)
						returnDB.add("FarmComputers");
					if (rst.getString("CollegeWebsite") == null)
						returnDB.add("CollegeWebsite");
				}

			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: return engineers from user table cases 90
	 * 
	 * @param egineersID
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> getEngineers(String query, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					returnDB.add("false");
				}
				rst.previous();

				while (rst.next()) {
					returnDB.add(rst.getString("UserID"));
					returnDB.add(rst.getString("FullName"));
					returnDB.add(rst.getString("UserDepartment"));
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	public static ArrayList<String> findConfirmedPhaseDuration4it(ArrayList<String> aList, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		ListIterator<String> aListIterator = aList.listIterator();

		if (mySql != null) {
			try {
				while (aListIterator.hasNext()) {
					String query = "SELECT * FROM icm.phase R WHERE R.RNumber='" + aListIterator.next()
							+ "' AND R.PhaseName='" + aListIterator.next() + "' ;";

					stm = mySql.createStatement();
					ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

					if (rst.next()) {
						returnDB.add(rst.getString("RNumber"));
						returnDB.add(rst.getString("PhaseName"));
						if (rst.getString("ConfirmedPhaseDuration") != null)
							returnDB.add(rst.getString("ConfirmedPhaseDuration"));
						else
							returnDB.add("null");
					}

				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func:
	 * 
	 * @param query
	 * @param mySql
	 * @return aList
	 */
	public static ArrayList<String> findRequestAs4105(String query, Connection mySql) {
		ArrayList<String> aList = new ArrayList<>();

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

				if (!rst.next()) {
					aList.add("false");
				}
				rst.previous();

				while (rst.next()) {
					aList.add(rst.getString("RNumber"));
					aList.add(rst.getString("CurrentPhase"));
				}
			} catch (Exception e) {
			}
		}
		return aList;
	}

	public static ArrayList<String> findExtension4it(ArrayList<String> aList, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		ListIterator<String> aListIterator = aList.listIterator();

		if (mySql != null) {
			try {
				while (aListIterator.hasNext()) {
					String query = "SELECT * FROM icm.phase R WHERE R.RNumber='" + aListIterator.next()
							+ "' AND R.ExtensionApprovedByInspector='Accepted' ;";

					stm = mySql.createStatement();
					ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

					if (rst.next()) {
						returnDB.add(rst.getString("RNumber"));
						returnDB.add(rst.getString("PhaseName"));
						returnDB.add(rst.getString("ConfirmedPhaseDuration"));
					}
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	//////////////////////////////////////// case 79
	/**
	 * case 79 Activity report
	 */
	public static ArrayList<String> getActivityReport(String option, String date1, String date2, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		ArrayList<String> subgroupsNames = new ArrayList<>();
		ArrayList<String> frequencyDistribution = new ArrayList<>();
		Double median;
		Double SD;
		int saveSN = 0;
		int daysBetween = (int) daysBetweenCalc(date1, date2);
		int subgroupsNumber = subgroupsNumberCalc(daysBetween);
		int numberOfvaluesInSubgroup = daysBetween / subgroupsNumber;
		int tail = 0;
		tail = daysBetween % subgroupsNumber;

		StringBuilder b = new StringBuilder();
		LocalDate startDate = LocalDate.parse(date1);
		int[] allDaysResultArray = new int[daysBetween];

		switch (option) {
		case "Closed":
			allDaysResultArray = countClosedRequestsInRange(date1, date2, mySql);
			break;

		case "Denied":
			allDaysResultArray = countDeniedRequestsInRange(date1, date2, mySql);
			break;

		case "Active":
			allDaysResultArray = countActiveRequestsInRange(date1, date2, mySql);
			break;

		case "Suspended":
			allDaysResultArray = countSuspendedRequestsInRange(date1, date2, mySql);
			break;
		}

		// build the returnDB
		// (subgroupsNumber, names for subgroups by their
		// dates,calculated frequency distribution,median,standard deviation)

		// frequency distribution
		// add the subgroupsNumber to returnDB
		if (tail != 0) {
			saveSN = subgroupsNumber + 1;
			returnDB.add(String.valueOf(subgroupsNumber + 1));
		} else {
			saveSN = subgroupsNumber;
			returnDB.add(String.valueOf(subgroupsNumber));
		}

		// add the names for subgroups by their dates
		for (int i = 0; i < subgroupsNumber; i++) {
			b.append(startDate.plusDays(i * numberOfvaluesInSubgroup));
			b.append(" - ");
			b.append(startDate.plusDays((i + 1) * numberOfvaluesInSubgroup - 1));
			returnDB.add(b.toString());
			b.delete(0, b.length());
		}
		if (tail != 0) {
			b.append(startDate.plusDays(numberOfvaluesInSubgroup * subgroupsNumber));
			b.append(" - ");
			b.append(startDate.plusDays(daysBetween));
			returnDB.add(b.toString());
			b.delete(0, b.length() - 1);
		}

		// save subgroups names
		subgroupsNames.addAll(returnDB);
		subgroupsNames.remove(0);

		// add the calculated distribution to returnDB
		frequencyDistribution = FrequencyDistributionCalc(allDaysResultArray, daysBetween);
		returnDB.addAll(frequencyDistribution);

		// median
		median = MedianCalc(allDaysResultArray);
		returnDB.add(Double.toString(median));

		// standard deviation
		SD = StandardDeviationCalc(allDaysResultArray);
		returnDB.add(Double.toString(SD));

		String query = "INSERT INTO icm.activity_report "
				+ "(Option,FromDate,ToDate,SubgroupsNumber,SubgroupsNames,FrequencyDistribution,Median,StandardDeviation) "
				+ "VALUES ('" + option + "','" + date1 + "','" + date2 + "','" + subgroupsNumber + "',"
				+ subgroupsNames.toString() + "," + frequencyDistribution.toString() + ",'" + median + "','" + SD + "');";
		updateDetails(query, mySql);

		return returnDB;
	}

	public static int[] countClosedRequestsInRange(String date1, String date2, Connection mySql) {
		LocalDate startDate = LocalDate.parse(date1);
		LocalDate checkDate;

		int daysBetween = (int) daysBetweenCalc(date1, date2);

		int[] closedRequestsCounter = new int[daysBetween];
		int counter = 0;

		for (int i = 0; i < daysBetween; i++) {
			checkDate = startDate.plusDays(i);

			String query = "SELECT RNumber FROM icm.requests_to_close WHERE ClosedDate = '" + checkDate
					+ "' AND CompletedOrDenied='Completed';";

			if (mySql != null) {
				try {
					stm = mySql.createStatement();
					ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

					while (rst.next()) {
						counter = counter + 1;
					}
					closedRequestsCounter[i] = counter;
					counter = 0;
				} catch (Exception e) {
				}
			}
		}
		return closedRequestsCounter;
	}

	public static int[] countDeniedRequestsInRange(String date1, String date2, Connection mySql) {
		LocalDate startDate = LocalDate.parse(date1);
		LocalDate checkDate;

		int daysBetween = (int) daysBetweenCalc(date1, date2);

		int[] deniedRequestsCounter = new int[daysBetween];
		int counter = 0;

		for (int i = 0; i < daysBetween; i++) {
			checkDate = startDate.plusDays(i);

			String query = "SELECT RNumber FROM icm.requests_to_close WHERE ClosedDate = '" + checkDate
					+ "' AND CompletedOrDenied='Denied';";

			if (mySql != null) {
				try {
					stm = mySql.createStatement();
					ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

					while (rst.next()) {
						counter = counter + 1;
					}
					deniedRequestsCounter[i] = counter;
					counter = 0;
				} catch (Exception e) {
				}
			}
		}
		return deniedRequestsCounter;
	}

	public static int[] countActiveRequestsInRange(String date1, String date2, Connection mySql) {
		LocalDate startDate = LocalDate.parse(date1);
		LocalDate checkDate;
		LocalDate enterActive;
		LocalDate exitActive;

		int daysBetween = (int) daysBetweenCalc(date1, date2);

		int[] activeRequestsCounter = new int[daysBetween];
		int counter = 0;

		for (int i = 0; i < daysBetween; i++) {
			checkDate = startDate.plusDays(i);

			String query = "SELECT EnterActive,ExitActive FROM icm.active_status_tbl;";

			if (mySql != null) {
				try {
					stm = mySql.createStatement();
					ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

					while (rst.next()) {
						enterActive = rst.getDate("EnterActive").toLocalDate();
						if (rst.getDate("ExitActive") != null) {
							exitActive = rst.getDate("ExitActive").toLocalDate();
							if (checkDate.isAfter(enterActive) && checkDate.isBefore(exitActive)
									|| checkDate.isEqual(enterActive))
								counter = counter + 1;
						} else {
							if (checkDate.isAfter(enterActive) || checkDate.isEqual(enterActive))
								counter = counter + 1;
						}
					}
					activeRequestsCounter[i] = counter;
					counter = 0;
				} catch (Exception e) {
				}
			}
		}
		return activeRequestsCounter;
	}

	public static int[] countSuspendedRequestsInRange(String date1, String date2, Connection mySql) {
		LocalDate startDate = LocalDate.parse(date1);
		LocalDate checkDate;
		LocalDate enterSuspended;
		LocalDate exitSuspended;

		int daysBetween = (int) daysBetweenCalc(date1, date2);

		int[] suspendedRequestsCounter = new int[daysBetween];
		int counter = 0;

		for (int i = 0; i < daysBetween; i++) {
			checkDate = startDate.plusDays(i);

			String query = "SELECT EnterSuspended,ExitSuspended FROM icm.suspended_status_tbl;";

			if (mySql != null) {
				try {
					stm = mySql.createStatement();
					ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

					while (rst.next()) {
						enterSuspended = rst.getDate("EnterSuspended").toLocalDate();
						if (rst.getDate("ExitSuspended") != null) {
							exitSuspended = rst.getDate("ExitSuspended").toLocalDate();
							if (checkDate.isAfter(enterSuspended) && checkDate.isBefore(exitSuspended)
									|| checkDate.isEqual(enterSuspended))
								counter = counter + 1;
						}
						else
						{
						if (checkDate.isAfter(enterSuspended)|| checkDate.isEqual(enterSuspended))
							counter = counter + 1;
					 		}	
						}
					suspendedRequestsCounter[i] = counter;
					counter = 0;
				} catch (Exception e) {
				}
			}
		}
		return suspendedRequestsCounter;
	}

	public static long daysBetweenCalc(String date1, String date2) {
		LocalDate tempdate1 = LocalDate.parse(date1);
		LocalDate tempdate2 = LocalDate.parse(date2);
		return ChronoUnit.DAYS.between(tempdate1, tempdate2);
	}

	public static int subgroupsNumberCalc(long daysBetween) {
		if (daysBetween <= 7)
			return (int) daysBetween; // days
		if (7 < daysBetween && daysBetween <= 31)
			return 4; // weeks
		if (31 < daysBetween && daysBetween <= 365)
			return 12; // months

		return 10;
	}

	public static double MedianCalc(int[] array) {
		int[] numArray = new int[array.length];

		// copy the values of array for sorting
		for (int i = 0; i < numArray.length; i++) {
			numArray[i] = array[i];
		}

		Arrays.sort(numArray);
		double median;
		if (numArray.length % 2 == 0)
			median = ((double) numArray[numArray.length / 2] + (double) numArray[numArray.length / 2 - 1]) / 2;
		else
			median = (double) numArray[numArray.length / 2];
		return median;
	}

	public static double StandardDeviationCalc(int[] array) {
		double SD = 0;
		// calculating mean.
		double total = 0;

		for (int i = 0; i < array.length; i++) {
			total += array[i]; // this is the calculation for summing up all the values
		}

		double mean = total / array.length;
		total = 0;

		// calculating Standard Deviation

		for (int i = 0; i < array.length; i++) {
			total += Math.pow((array[i] - mean), 2);
		}

		SD = Math.sqrt(total / array.length);

		return SD;
	}

	public static ArrayList<String> FrequencyDistributionCalc(int[] array, int daysBetween) {
		ArrayList<String> newArray = new ArrayList<>();
		int total = 0;
		int subgroupsNumber = subgroupsNumberCalc(daysBetween);
		int numberOfvaluesInSubgroup = daysBetween / subgroupsNumber;
		int tail = 0;
		tail = daysBetween % subgroupsNumber;

		for (int i = 0; i < subgroupsNumber; i++) {
			for (int j = i * numberOfvaluesInSubgroup; j < (i + 1) * numberOfvaluesInSubgroup; j++) {
				total += array[j];
			}
			newArray.add(Integer.toString(total));
			total = 0;
		}

		if (tail != 0) {
			for (int i = numberOfvaluesInSubgroup * subgroupsNumber; i < daysBetween; i++) {

				total += array[i];
			}
			newArray.add(Integer.toString(total));
			total = 0;
		}

		return newArray;
	}

	/**
	 * case 88 Delays report
	 */
	public static ArrayList<String> getDelaysReport(String ISname, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		int[] delaysCounter;
		Double median;
		Double SD;

		delaysCounter = countDelays(ISname, mySql);

		// add to returnDB the delay amounts by days
		for (int i = 0; i < 10; i++) {
			returnDB.add(Integer.toString(delaysCounter[i]));
		}

		// add the median
		median = MedianCalc(delaysCounter);
		returnDB.add(Double.toString(median));

		// add SD
		SD = StandardDeviationCalc(delaysCounter);
		returnDB.add(Double.toString(SD));

		String query = "INSERT INTO icm.delays_report (delaysCounter1,delaysCounter2,delaysCounter3,"
				+ "delaysCounter4,delaysCounter5,delaysCounter6,delaysCounter7,delaysCounter8,delaysCounter9,delaysCounter10plus)"
				+ " VALUES('" + delaysCounter[0] + "','" + delaysCounter[1] + "','" + delaysCounter[2] + "','"
				+ delaysCounter[3] + "'," + "'" + delaysCounter[4] + "','" + delaysCounter[5] + "','" + delaysCounter[6]
				+ "','" + delaysCounter[7] + "'," + "'" + delaysCounter[8] + "','" + delaysCounter[9] + "','" + median
				+ "','" + SD + "');";
		updateDetails(query, mySql);
		return returnDB;
	}

	public static int[] countDelays(String ISname, Connection mySql) {
		LocalDate estimatedDate = null;
		LocalDate realDate = null;

		int RNumber = 0;
		int delay = 0;
		int[] delaysCounter = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		String query1 = "SELECT RNumber,ClosedDate FROM icm.requests_to_close;";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst1 = ((java.sql.Statement) stm).executeQuery(query1);

				while (rst1.next()) { // while we have real closed requests
					RNumber = rst1.getInt(("RNumber"));
					if(rst1.getDate("ClosedDate")!=null) {
						
					
					realDate = rst1.getDate("ClosedDate").toLocalDate();

					String query2 = "SELECT FinishDate FROM icm.request WHERE RNumber = '" + RNumber
							+ "' AND InformationSystemName = '" + ISname + "';";

					stm = mySql.createStatement();
					ResultSet rst2 = ((java.sql.Statement) stm).executeQuery(query2);

					if (rst2.next()) {
						estimatedDate = rst2.getDate("FinishDate").toLocalDate();
					}
					if (realDate.isAfter(estimatedDate)) {
						delay = (int) daysBetweenCalc(estimatedDate.toString(), realDate.toString());
						if (delay < 10) {
							delaysCounter[delay - 1] = delaysCounter[delay - 1] + 1;
						} else {
							delaysCounter[9] = delaysCounter[9] + 1;
						}
					}
				}
				}
			} catch (Exception e) {
			}
		}
		return delaysCounter;
	}

	/**
	 * case 82 Performance report
	 */
	public static int getPerformanceReport(Connection mySql) {
		LocalDate assessorsDate;
		LocalDate newDate = null;
		int RNumber;
		int counter = 0;

		String query1 = "SELECT RNumber,ExecutionDuration FROM icm.evaluation_report;";

		if (mySql != null) {
			try {
				stm = mySql.createStatement();
				ResultSet rst1 = ((java.sql.Statement) stm).executeQuery(query1);

				while (rst1.next()) {
					RNumber = rst1.getInt(("RNumber"));
					assessorsDate = rst1.getDate("ExecutionDuration").toLocalDate();

					String query2 = "SELECT FinishDate FROM icm.request WHERE RNumber = '" + RNumber + "';";

					stm = mySql.createStatement();
					ResultSet rst2 = ((java.sql.Statement) stm).executeQuery(query2);

					if (rst2.next()) {
						newDate = rst2.getDate("FinishDate").toLocalDate();

						if (newDate.isAfter(assessorsDate))
							counter = counter + (int) daysBetweenCalc(assessorsDate.toString(), newDate.toString());
					}
				}
			} catch (Exception e) {
			}
		}

		String query3 = "INSERT INTO icm.performance_report (addedPerformance) VALUES ('" + counter + "');";
		updateDetails(query3, mySql);

		return counter;
	}

	/**
	 * func: return the requests of specific role cases 4,14,15,16,17
	 * 
	 * @param aList
	 * @param phase
	 * @param mySql
	 * @return
	 */
	public static ArrayList<String> findConfirmedPhaseDuration(ArrayList<String> aList, String phase,
			Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		ListIterator<String> aListIterator = aList.listIterator();

		if (mySql != null) {
			try {
				while (aListIterator.hasNext()) {
					String query = "SELECT * FROM icm.phase R WHERE R.PhaseName='" + phase + "' AND R.RNumber='"
							+ aListIterator.next() + "';";
					stm = mySql.createStatement();
					ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

					if (rst.next()) {
						returnDB.add(rst.getString("RNumber"));
						if (rst.getString("ConfirmedPhaseDuration") != null)
							returnDB.add(rst.getString("ConfirmedPhaseDuration"));
						else
							returnDB.add("null");
					}

				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}

	/**
	 * func: return only the active requests
	 * 
	 * @param aList
	 * @param mySql
	 * @return returnDB
	 */
	public static ArrayList<String> onlyActiveRequests(ArrayList<String> aList, String phase, Connection mySql) {
		ArrayList<String> returnDB = new ArrayList<>();
		ListIterator<String> aListIterator = aList.listIterator();

		if (mySql != null) {
			try {
				while (aListIterator.hasNext()) {
					String query = "SELECT * FROM icm.request R WHERE R.CurrentPhase='" + phase
							+ "' AND R.Status='Active' AND R.RNumber='" + aListIterator.next() + "';";
					stm = mySql.createStatement();
					ResultSet rst = ((java.sql.Statement) stm).executeQuery(query);

					if (rst.next()) {
						returnDB.add(rst.getString("RNumber"));
					}
				}
			} catch (Exception e) {
			}
		}
		return returnDB;
	}
}
