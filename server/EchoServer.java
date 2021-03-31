package server;

import java.io.*;
import java.net.BindException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Iterator;
import entities.MyFile;
import ocsf.server.*;

public class EchoServer extends AbstractServer {
	// Class variables *************************************************
	@SuppressWarnings("unused")
	private static Connection mySql = null;
	public ServerMainController servermaincontroller;

	// The default port to listen on.
	final public static int DEFAULT_PORT = 5555;
	// Constructors ****************************************************

	/*
	 * Constructs an instance of the echo server.
	 * 
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port) {
		super(port);
		// serverQueryIdentification = new serverQueryIdentification();
	}

	public void callConnectTodDB(ServerMainController SMC)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// save the appearance of the connection
		this.servermaincontroller = SMC;
		EchoServer.mySql = servermaincontroller.Connection.connection;
		System.out.println("Connected to server");
	}

	public void updateConnected(ConnectionToClient client) {
		servermaincontroller.clientConnected("Client connected: " + client.toString());
	}

	public void updateDisconnected(ConnectionToClient client) {
		servermaincontroller.clientDisconnected("Client disconnected: " + client.toString());
	}

	/*
	 * This method handles any messages received from the client.
	 * 
	 * @param msg The message received from the client.
	 * 
	 * @param client The connection from which the message originated.
	 */
	@SuppressWarnings("null")
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String temp = null, tempp = null;
		String stringFromClient[] = new String[30];
		ArrayList<String> returnDB = new ArrayList<>(); // information taken from the database
		ArrayList<String> aList = new ArrayList<>();
		ArrayList<String> engineersTreatsNextPhase = new ArrayList<>();
		ArrayList<String> engineersID = new ArrayList<>();

		if (msg instanceof MyFile) {
			stringFromClient[0] = "10";
		} else {
			String TypeWithQuery = (String) msg;
			stringFromClient = TypeWithQuery.split("#");
		}

		/**
		 * stringFromClient///// 0 = option , 1 = first parameter , 2 = second
		 * parameter, etc etc etc ....
		 */
		switch (stringFromClient[0]) {
		case "1": // case 1 is the login
			String query1 = "SELECT * FROM icm.user WHERE UserName='" + stringFromClient[1] + "' AND Password='"
					+ stringFromClient[2] + "';";
			String query1_1 = "UPDATE icm.user SET LoginStatus='1' WHERE UserName ='" + stringFromClient[1] + "';";
			returnDB = MySqlQuery.loginToUser(query1, query1_1, mySql);

			if (!returnDB.contains("false"))
				updateConnected(client);
			returnDB.add("1");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "2":
			/**
			 * case 2 loading requests of the specific user into combo box to user home page
			 */
			String query2 = "SELECT * FROM icm.request WHERE ApplicantID='" + stringFromClient[1] + "';";
			returnDB = MySqlQuery.allRequestsOfUser(query2, mySql);
			returnDB.add("2");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "3":
			/** case 3 is loading specific request information after search in combo box */
			returnDB = MySqlQuery.informationOfSpecificRequest(stringFromClient[1], mySql);
			returnDB.add("3");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "4":
			/** case 4 is loading user requests to close information */
			String query4 = "SELECT RNumber FROM icm.requests_to_close RTC WHERE RTC.ApprovedByUser='0';";
			aList = MySqlQuery.findRequestAs_(query4, mySql);
			returnDB = MySqlQuery.findRequestFromRequestTableToClose(aList, stringFromClient[1], mySql);
			returnDB.add("4");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "5":
			/** case 5 is update logout status in DB page */
			String query5 = "UPDATE icm.user SET LoginStatus='0' WHERE UserID ='" + stringFromClient[1] + "';";
			try {
				returnDB.add(MySqlQuery.updateUserLoginStatusInDB(query5, mySql));
				if (!returnDB.contains("false"))
					updateDisconnected(client);
				returnDB.add("5");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "6":
			/** case 6 reason of closure request (Completed/Denied) page */
			String query6 = "SELECT * FROM icm.requests_to_close WHERE RNumber='" + stringFromClient[1] + "';";
			returnDB.add(MySqlQuery.reasonOfRequestClosure(query6, mySql));
			returnDB.add("6");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "7":
			/**
			 * case 7: 1.update approvedByUser to 1; 2.check if approvedByInspector =1
			 * (yes=> 3.change request status to closed; no=>nothing); 4.return request
			 * status;
			 */

			String query7_1 = "UPDATE icm.requests_to_close SET ApprovedByUser='1' WHERE RNumber='"
					+ stringFromClient[1] + "';";
			String query7_2 = "SELECT ApprovedByInspector FROM icm.requests_to_close WHERE RNumber='"
					+ stringFromClient[1] + "';";
			String query7_3 = "UPDATE icm.request SET Status = 'Closed' WHERE RNumber ='" + stringFromClient[1] + "';";
			String query7_4 = "SELECT Status FROM icm.request WHERE RNumber ='" + stringFromClient[1] + "';";

			returnDB.add(
					MySqlQuery.approveToCloseRequestByUserAndInspector(query7_1, query7_2, query7_3, query7_4, mySql));

			String query7_5 = "UPDATE icm.requests_to_close SET ClosedDate = '" + LocalDate.now() + "' WHERE RNumber='"
					+ stringFromClient[1] + "';";
			MySqlQuery.updateDetails(query7_5, mySql);

			returnDB.add("7");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "8":
			/** case 8: return permission of all engineers in the system */
			String query8 = "SELECT * FROM icm.permissions WHERE EngineerID='" + stringFromClient[1] + "';";
			returnDB = MySqlQuery.engineerPermissionsInSystem(query8, mySql);
			returnDB.add("8");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "9":
			/** case 9: create new request */
			String query9_1 = "INSERT INTO icm.request (ApplicantID, InformationSystemName, ExistingDescription, ChangeDescription, Reason, Comments, Status) VALUES ('"
					+ stringFromClient[1] + "','" + stringFromClient[2] + "','" + stringFromClient[3] + "','"
					+ stringFromClient[4] + "','" + stringFromClient[5] + "','" + stringFromClient[6] + "','Active');";
			String query9_2 = "SELECT RNumber FROM icm.request WHERE ApplicantID='" + stringFromClient[1]
					+ "' AND InformationSystemName='" + stringFromClient[2] + "' AND ExistingDescription= '"
					+ stringFromClient[3] + "' AND ChangeDescription='" + stringFromClient[4] + "' AND Reason='"
					+ stringFromClient[5] + "' AND Comments='" + stringFromClient[6] + "';";
			returnDB = MySqlQuery.createNewRequest(query9_1, query9_2, mySql);
			returnDB.add("9");

			// create new row in active_status_tbl
			MySqlQuery.enterActiveStatus(returnDB.get(1), mySql);

			// find ITdepartmentManagerID
			engineersID.addAll(MySqlQuery.getAllEngineerIDByPermission("ITdepartmentManager", mySql));
			// find InspectorID
			engineersID.addAll(MySqlQuery.getAllEngineerIDByPermission("Inspector", mySql));
			// choose AssessorID
			engineersID.addAll(MySqlQuery.getAllEngineerIDByPermission("Assessor", mySql));

			// create a random nomination of assessor
			Random random = new Random();
			int random1 = 2;
			random1 = random.nextInt(engineersID.size());

			// create new row in table request_role
			String query9_3 = "INSERT INTO icm.request_role (RNumber,ITdepartmentManagerID,InspectorID,AssessorID) VALUES ('"
					+ returnDB.get(1) + "','" + engineersID.get(0) + "','" + engineersID.get(1) + "','"
					+ engineersID.get(random1) + "');";
			MySqlQuery.updateDetails(query9_3, mySql);

			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "10": // File coming with the open request
			System.out.println("test10");
			int fileSize = ((MyFile) msg).getSize();
			System.out.println("Message received: " + msg + " from " + client);
			System.out.println("length " + fileSize);
			MyFile newMsg = (MyFile) msg;
			String path = "C:\\temp\\";
			System.out.println(((MyFile) msg).ToString());
			try {
				File newFile = new File(path + "" + newMsg.getFileName());
				byte[] mybytearray = newMsg.getMybytearray();
				FileOutputStream fos = new FileOutputStream(newFile);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bos.write(mybytearray, 0, newMsg.getSize());
				bos.flush();
				fos.flush();

				// save the file in DB
				String query10 = "INSERT INTO icm.file (RNumber,Type,Data,FileName,FolderPath) VALUES ('"
						+ newMsg.getRnumber() + "','" + newMsg.getType() + "','" + mybytearray + "','"
						+ newMsg.getFileName() + "',?);";
				PreparedStatement ps = mySql.prepareStatement(query10);

				ps.setString(1, path);
				int x = ps.executeUpdate();
				if (x > 0)
					returnDB.add("true");
				else
					returnDB.add("false");

				returnDB.add(((MyFile) msg).getFileName());
				returnDB.add("10");
				client.sendToClient(returnDB);

			} catch (Exception e) {
				System.out.println("Error send (Files)msg) to Client");
			}
			break;

		case "11":
			/** case 11: return the information system name */
			returnDB = MySqlQuery.returnInformationSystemNames(mySql);
			returnDB.add("11");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "12":
			/** case 12: return the information system name */
			returnDB = MySqlQuery.returnInformationSystemNames(mySql);
			returnDB.add("12");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "13":
			/**
			 * case 13: return the requests of specific assessor(assessor id got from the
			 * user)
			 */
			String query13 = "SELECT RNumber FROM icm.request_role RR WHERE RR.AssessorID='" + stringFromClient[1]
					+ "';";
			aList = MySqlQuery.findRequestAs_(query13, mySql);
			returnDB = MySqlQuery.findRequestFromRequestTable(aList, "Assessment", mySql);
			returnDB.add("13");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "14":
			/**
			 * case 14: return the requests of specific chairman(chairman id got from the
			 * user)
			 */
			String query14 = "SELECT RNumber FROM icm.request_role RR WHERE RR.ChairmanID='" + stringFromClient[1]
					+ "';";
			aList = MySqlQuery.findRequestAs_(query14, mySql);
			returnDB = MySqlQuery.findRequestFromRequestTable(aList, "Decision", mySql);
			returnDB.add("14");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "15":
			/**
			 * case 15: return the requests of specific
			 * MemberOfCommittee(MemberOfCommitteeid got from the user)
			 */
			String query15 = "SELECT RNumber FROM icm.request_role RR WHERE RR.CommitteeMember2ID='"
					+ stringFromClient[1] + "' OR RR.CommitteeMember3ID='" + stringFromClient[1] + "';";
			aList = MySqlQuery.findRequestAs_(query15, mySql);
			returnDB = MySqlQuery.findRequestFromRequestTable(aList, "Decision", mySql);
			returnDB.add("15");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "16":
			/**
			 * case 16: return the requests of specific ExecutionLeader (ExecutionLeader id
			 * got from the user)
			 */
			String query16 = "SELECT RNumber FROM icm.request_role RR WHERE RR.ExecutionLeaderID='"
					+ stringFromClient[1] + "';";
			aList = MySqlQuery.findRequestAs_(query16, mySql);
			returnDB = MySqlQuery.findRequestFromRequestTable(aList, "Execution", mySql);
			returnDB.add("16");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "17":
			/**
			 * case 17 return the requests of specific Examiner (Examiner id got from the
			 * user)
			 */
			String query17 = "SELECT RNumber FROM icm.request_role RR WHERE RR.ExaminerID ='" + stringFromClient[1]
					+ "';";
			aList = MySqlQuery.findRequestAs_(query17, mySql);
			returnDB = MySqlQuery.findRequestFromRequestTable(aList, "Examination", mySql);
			returnDB.add("17");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "18":
			/** case 18: return the InformationSystemName from request table */

			returnDB = MySqlQuery.returnInformationSystemNames(mySql);
			returnDB.add("18");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "19":
			/** case 19: get all assessors id,full name,department */
			engineersID = MySqlQuery.getAllEngineerIDByPermission("Assessor", mySql);
			engineersID.remove(stringFromClient[1]);
			returnDB = MySqlQuery.getEngineersInfo(engineersID, mySql);
			returnDB.add("19");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "20":
			/**
			 * case 20: for execution leader check if there is a failure report, phase
			 * Execution Duration Estimation, Execution Time Extension
			 */
			String query20_1 = "SELECT FailureDescription FROM icm.test_failure WHERE RNumber ='" + stringFromClient[1]
					+ "';";
			String query20_2 = "SELECT AskPhaseDuration,AskPhaseExtension,DurationApprovedByInspector,ExtensionApprovedByInspector FROM icm.phase WHERE RNumber ='"
					+ stringFromClient[1] + "' AND PhaseName = 'Execution';";
			returnDB = MySqlQuery.checkForExecutionLeader(query20_1, query20_2, mySql);
			returnDB.add("20");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "21":
			/**
			 * case 21: execution leader get Evaluation Report (String location, String
			 * description, String advantages, string constraints and risks, String
			 * execution duration, 21)
			 */
			String query21 = "SELECT ChangeLocation,ChangeDescription,ChangeAdvantages,ChangeConstraintsAndRisks,ExecutionDuration FROM icm.evaluation_report WHERE RNumber ='"
					+ stringFromClient[1] + "';";
			returnDB = MySqlQuery.getEvaluationReport(query21, mySql);
			returnDB.add("21");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "22": /**
					 * case 22: execution leader finish his work on request and sends it to the next
					 * phase examination
					 */

			// find the engineer that will treat the next phase examination
			String query22_1 = "SELECT ExaminerID FROM icm.request_role WHERE RNumber = '" + stringFromClient[1] + "';";
			engineersTreatsNextPhase.add(MySqlQuery.findWhoTreatsNextPhase(query22_1, "ExaminerID", mySql));

			returnDB = MySqlQuery.goToNextPhase(stringFromClient[1], engineersTreatsNextPhase.toString(), "Examination",
					mySql);
			// set time duration of examination (this date+7 days)
			MySqlQuery.autoSetDurationDateInPhaseTable(stringFromClient[1], "Examination", 7, mySql);
			returnDB.add("22");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "23":
			/** case 23: execution leader sends time duration for inspectors approval */
			returnDB = MySqlQuery.sendTimeDurationForApproval(stringFromClient[1], "Execution", stringFromClient[2],
					mySql);
			returnDB.add("23");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "24":
			/** case 24: execution leader sends time extension for inspectors approval */
			returnDB = MySqlQuery.sendTimeExtensionForApproval(stringFromClient[1], "Execution", stringFromClient[2],
					stringFromClient[3], mySql);
			returnDB.add("24");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "25":
			/**
			 * case 25: examiner checks if he already asked for time extension for a
			 * specific request, and wants to see automatic phase duration
			 * (0/1(Extension),AskPhaseExtension,date,0/1/2(ExtensionApprovedByInspector),25)
			 */
			String query25 = "SELECT AskPhaseExtension,ConfirmedPhaseDuration,ExtensionApprovedByInspector FROM icm.phase WHERE RNumber = '"
					+ stringFromClient[1] + "' AND PhaseName = 'Examination';";

			returnDB = MySqlQuery.checkForExaminerOrChairman(query25, mySql);
			returnDB.add("25");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "26":
			/** case 26: Examiner sends time extension for inspectors approval */
			returnDB = MySqlQuery.sendTimeExtensionForApproval(stringFromClient[1], "Examination", stringFromClient[2],
					stringFromClient[3], mySql);
			returnDB.add("26");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "27":
			/** case 27: add new test failure => Execution phase */

			String addFailure = MySqlQuery.addNewFailureReport(stringFromClient[1], stringFromClient[2], mySql);

			if (addFailure.equals("true")) { // check if update succeeded
				// find the engineer that will treat the next phase examination
				String query27 = "SELECT ExecutionLeaderID FROM icm.request_role WHERE RNumber = '"
						+ stringFromClient[1] + "';";
				engineersTreatsNextPhase.add(MySqlQuery.findWhoTreatsNextPhase(query27, "ExecutionLeaderID", mySql));

				returnDB = MySqlQuery.goToNextPhase(stringFromClient[1], engineersTreatsNextPhase.toString(),
						"Execution", mySql);

			} else {
				returnDB.add("false");
			}

			returnDB.add("27");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "28":/**
					 * case 28 examiner finish his work on request and sends it to the next phase
					 * closure and create new row in table request_to_close
					 */

			// find the engineer that will treat the next phase examination
			String query28_1 = "SELECT InspectorID FROM icm.request_role WHERE RNumber = '" + stringFromClient[1]
					+ "';";
			engineersTreatsNextPhase.add(MySqlQuery.findWhoTreatsNextPhase(query28_1, "InspectorID", mySql));

			returnDB = MySqlQuery.goToNextPhase(stringFromClient[1], engineersTreatsNextPhase.toString(), "Closure",
					mySql);
			// set time duration of closure (this date)
			MySqlQuery.autoSetDurationDateInPhaseTable(stringFromClient[1], "Closure", 0, mySql);
			MySqlQuery.insertNewRequestToClose(stringFromClient[1], "Completed", mySql);
			returnDB.add("28");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "29":
			/**
			 * case 29: get all engineers with their permissions from icm.permissions table
			 */
			String query29 = "SELECT EngineerID,Inspector,Assessor,Chairman,CommitteeMember,Examiner,ExecutionLeader FROM icm.permissions;";

			returnDB = MySqlQuery.engineersPermissions(query29, mySql);
			returnDB.add("29");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "30":
			/** case 30: get all engineers users in the system from icm.user */
			String query30 = "SELECT UserID,UserName,Password,FullName,Email,UserDepartment,UserRole FROM icm.user WHERE Engineer='1';";
			returnDB = MySqlQuery.allEngineersdetailsUser(query30, mySql);
			returnDB.add("30");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "31":
			/** case 31: insert new evaluation report and step forward to next phase */
			returnDB = MySqlQuery.insertNewEvaluationReport(stringFromClient[1], stringFromClient[2],
					stringFromClient[3], stringFromClient[4], stringFromClient[5], stringFromClient[6], mySql);

			returnDB.add("31");

			// create the next phase - decision
			String query31_1 = "SELECT ChairmanID FROM icm.request_role WHERE RNumber = '" + stringFromClient[1] + "';";
			engineersTreatsNextPhase.add(MySqlQuery.findWhoTreatsNextPhase(query31_1, "ChairmanID", mySql));

			String query31_2 = "SELECT CommitteeMember2ID FROM icm.request_role WHERE RNumber = '" + stringFromClient[1]
					+ "';";
			engineersTreatsNextPhase.add(MySqlQuery.findWhoTreatsNextPhase(query31_2, "CommitteeMember2ID", mySql));

			String query31_3 = "SELECT CommitteeMember3ID FROM icm.request_role WHERE RNumber = '" + stringFromClient[1]
					+ "';";
			engineersTreatsNextPhase.add(MySqlQuery.findWhoTreatsNextPhase(query31_3, "CommitteeMember3ID", mySql));

			aList = MySqlQuery.goToNextPhase(stringFromClient[1], engineersTreatsNextPhase.toString(), "Decision",
					mySql);

			// set time duration of examination (this date+7 days)
			MySqlQuery.autoSetDurationDateInPhaseTable(stringFromClient[1], "Decision", 7, mySql);

			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "32":
			/**
			 * case 32: return the confirmed phase duration and check if asked for time
			 * extension in this phase (0/1,ExtensionAskedDate,confirm phase
			 * duration,0/1/2,32)
			 */
			String query32 = "SELECT AskPhaseExtension,ConfirmedPhaseDuration,ExtensionApprovedByInspector FROM icm.phase WHERE RNumber = '"
					+ stringFromClient[1] + "' AND PhaseName = 'Decision';";

			returnDB = MySqlQuery.checkForExaminerOrChairman(query32, mySql);
			returnDB.add("32");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "33":
			/** case 32: Chairman ask for time extension */
			returnDB = MySqlQuery.sendTimeExtensionForApproval(stringFromClient[1], "Decision", stringFromClient[2],
					stringFromClient[3], mySql);
			returnDB.add("33");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "34":
			/**
			 * case 34: committee member or chairman get Evaluation Report (String location,
			 * String description, String advantages, string constraints and risks, String
			 * execution duration, 34)
			 */
			String query34 = "SELECT ChangeLocation,ChangeDescription,ChangeAdvantages,ChangeConstraintsAndRisks,ExecutionDuration FROM icm.evaluation_report WHERE RNumber ='"
					+ stringFromClient[1] + "';";
			returnDB = MySqlQuery.getEvaluationReport(query34, mySql);
			returnDB.add("34");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "35":
			/**
			 * case 35: IT Department Manager Home page - View only access Tab (RNumber,
			 * ApplicantID, InformationSystemName, ExistingDescription, ChangeDescription,
			 * Reason, Comments, TreatedBy, Status, CurrentPhase, StartDate, FinishDate, ……
			 * more requests this way……..,35);
			 */

			String query35 = "SELECT * FROM icm.request ;";
			returnDB = MySqlQuery.getOriginalRequestsInformation(query35, mySql);
			returnDB.add("35");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "36":
			/** get examiners for nomination (committee members) */
			returnDB = MySqlQuery.prepareExaminersForNomination(stringFromClient[1], mySql);
			returnDB.add("36");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "37":// get all RNumbers of specific information system name
			String query37 = "SELECT RNumber FROM icm.request WHERE InformationSystemName='" + stringFromClient[1]
					+ "' AND Status ='Active';";
			returnDB = MySqlQuery.rnumbersOfSpecificISN(query37, mySql);
			returnDB.add("37");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "38":
			/**
			 * case 38: get all roles (except IT department manage and inspector) of
			 * specific rnumber
			 */
			returnDB = MySqlQuery.allRolesOfSpecificRNumber(stringFromClient[1], mySql);
			returnDB.add("38");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "39":
			/** looking for missing roles of specific RNumber */
			String query39 = "SELECT AssessorID,ChairmanID,CommitteeMember2ID,CommitteeMember3ID,ExaminerID,ExecutionLeaderID FROM icm.request_role WHERE RNumber='"
					+ stringFromClient[1] + "' ;";
			returnDB = MySqlQuery.missingRolesOfSpecificRNumber(query39, mySql);
			returnDB.remove("Assessor");
			returnDB.remove("Examiner");
			returnDB.add("39");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "40":
			/**
			 * case 40 all engineers details from request table except IT department manager
			 */
			temp = MySqlQuery.findCommitteMem2(stringFromClient[1], mySql);// in case we have already committee member2
			tempp = MySqlQuery.findCommitteMem3(stringFromClient[1], mySql);// in case we have already committee member3

			engineersID = MySqlQuery.getAllEngineerIDByPermission(stringFromClient[2], mySql);
			engineersID.remove(temp);
			engineersID.remove(tempp);
			returnDB = MySqlQuery.getEngineersInfo(engineersID, mySql);
			returnDB.add("40");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "41":
			/**
			 * case 41: chairman asks for more information from assessor the request goes
			 * back to assessment phase ask for more info from assessor
			 */
			String updateExtraInfo = MySqlQuery.askForExtraEvaluationInformation(stringFromClient[1],
					stringFromClient[2], mySql);

			if (updateExtraInfo.equals("true")) {// if the update was successful=> go to next phase

				// find the engineer that will treat the next phase assessment
				String query41 = "SELECT AssessorID FROM icm.request_role WHERE RNumber = '" + stringFromClient[1]
						+ "';";
				engineersTreatsNextPhase.add(MySqlQuery.findWhoTreatsNextPhase(query41, "AssessorID", mySql));

				returnDB = MySqlQuery.goToNextPhase(stringFromClient[1], engineersTreatsNextPhase.toString(),
						"Assessment", mySql);
			} else {
				returnDB.add("false");
			}
			returnDB.add("41");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "42": /** case 42: chairman denies the request =>Closure */

			// String addRequestToClose =
			MySqlQuery.insertNewRequestToClose(stringFromClient[1], "Denied", mySql);

			// if(addRequestToClose.equals("true")) {//if the update was successful=> go to
			// next phase

			// find the engineer that will treat the next phase assessment
			String query42 = "SELECT InspectorID FROM icm.request_role WHERE RNumber = '" + stringFromClient[1] + "';";
			engineersTreatsNextPhase.add(MySqlQuery.findWhoTreatsNextPhase(query42, "InspectorID", mySql));

			returnDB = MySqlQuery.goToNextPhase(stringFromClient[1], engineersTreatsNextPhase.toString(), "Closure",
					mySql);
			MySqlQuery.autoSetDurationDateInPhaseTable(stringFromClient[1], "Closure", 0, mySql);
			// }else {
			// returnDB.add("false");
			// }
			returnDB.add("42");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "43":
			/** case 43: assessor checks */
			String query43_1 = "SELECT AskPhaseDuration,DurationApprovedByInspector,ConfirmedPhaseDuration FROM icm.phase WHERE RNumber = '"
					+ stringFromClient[1] + "' AND PhaseName = 'Assessment';";

			String query43_2 = "SELECT WaitingForTreatment FROM icm.evaluation_report WHERE RNumber = '"
					+ stringFromClient[1] + "';";

			returnDB = MySqlQuery.checkForAssessor(query43_1, query43_2, mySql);
			returnDB.add("43");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "44":
			/** case 44: chairman nominates an examiner => Execution phase */

			try {
				if (MySqlQuery.addNewNomination(stringFromClient[1], "Examiner", stringFromClient[2], mySql)
						.equals("succeeded")) {
					// find the engineer that will treat the next phase assessment
					String query44 = "SELECT ExecutionLeaderID FROM icm.request_role WHERE RNumber = '"
							+ stringFromClient[1] + "';";
					engineersTreatsNextPhase
							.add(MySqlQuery.findWhoTreatsNextPhase(query44, "ExecutionLeaderID", mySql));

					returnDB = MySqlQuery.goToNextPhase(stringFromClient[1], engineersTreatsNextPhase.toString(),
							"Execution", mySql);
				} else {
					returnDB.add("false");
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			returnDB.add("44");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "45":
			/** case 45: committee member get ConfirmedPhaseDuration */

			returnDB.add(MySqlQuery.committeeMemberChecks(stringFromClient[1], mySql));
			returnDB.add("45");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "46":
			/**
			 * case 46: assessor checks
			 * (true/false(canAskPhaseExtension),ConfirmedPhaseDuration,46)
			 */

			String query46 = "SELECT AskPhaseExtension,ConfirmedPhaseDuration FROM icm.phase WHERE RNumber = '"
					+ stringFromClient[1] + "';";
			returnDB = MySqlQuery.checkForAssessor2(query46, mySql);
			returnDB.add("46");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "47":
			/** case 47: inspector nominates */
			try {
				returnDB.add(MySqlQuery.addNewNomination(stringFromClient[1], stringFromClient[2], stringFromClient[3],
						mySql));
				returnDB.add("47");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "48":
			/** case 48: Assessor Home page -send time duration for approval */

			returnDB = MySqlQuery.sendTimeDurationForApproval(stringFromClient[1], "Assessment", stringFromClient[2],
					mySql);
			returnDB.add("48");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "49": /**
					 * case 49: all engineers details from request table except IT department
					 * manager and the specific role
					 */
			// seek for engineers to remove
			// seek for engineer that is now nominated to the role in the request
			aList = MySqlQuery.findEngineerInRole(stringFromClient[1], stringFromClient[2], mySql);

			if (stringFromClient[2].equals("Examiner")) {
				engineersID.add(MySqlQuery.findCommitteMem2(stringFromClient[1], mySql));
				engineersID.add(MySqlQuery.findCommitteMem3(stringFromClient[1], mySql));
			} else {
				// find all engineers with role permissions
				engineersID = MySqlQuery.getAllEngineerIDByPermission(stringFromClient[2], mySql);
			}

			engineersID.removeAll(aList);

			returnDB = MySqlQuery.getEngineersInfo(engineersID, mySql);
			if (returnDB.isEmpty())
				returnDB.add("false");
			returnDB.add("49");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "50":
			/** case 50: change nomination in request phase-inspector */
			if (stringFromClient[2].equals("CommitteeMember")) {
				temp = MySqlQuery.findCommitteMem2(stringFromClient[1], mySql);
				tempp = MySqlQuery.findCommitteMem3(stringFromClient[1], mySql);

				System.out.println("teeeeeeeee" + temp + tempp);

				String query50 = "UPDATE icm.request_role SET CommitteeMember2ID = '" + stringFromClient[3]
						+ "' WHERE RNumber = '" + stringFromClient[1] + "';";
				String query50_1 = "UPDATE icm.request_role SET CommitteeMember3ID = '" + stringFromClient[3]
						+ "' WHERE RNumber = '" + stringFromClient[1] + "';";

				if (temp.equals(stringFromClient[4])) {
					MySqlQuery.updateDetails(query50, mySql);
					returnDB.add("succeeded");
				}
				else if (tempp.equals(stringFromClient[4])) {
					MySqlQuery.updateDetails(query50_1, mySql);
					returnDB.add("succeeded");
				}
				/*
				 * engineersID.add(MySqlQuery.findCommitteMem2(stringFromClient[1], mySql)); //
				 * get committee member 2
				 * engineersID.add(MySqlQuery.findCommitteMem3(stringFromClient[1], mySql)); //
				 * get committee member 3 engineersID.remove(stringFromClient[4]); // remove
				 * member that inspector wants to change
				 * 
				 * String query50 =
				 * "UPDATE icm.request_role SET CommitteeMember2ID = NULL, CommitteeMember3ID = NULL WHERE RNumber = '"
				 * + stringFromClient[1] + "';"; MySqlQuery.updateDetails(query50, mySql); if
				 * (!engineersID.get(0).equals("false")) {
				 * MySqlQuery.addNewNomination(stringFromClient[1], stringFromClient[2],
				 * engineersID.get(0), mySql); }
				 */
			} else
				try {
					returnDB.add(MySqlQuery.addNewNomination(stringFromClient[1], stringFromClient[2],
							stringFromClient[3], mySql));
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			

			try {
				returnDB.add("50");
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "51":
			/** case 51: inspector gets all closed requests information */
			String query51 = "SELECT RNumber,ApplicantID,InformationSystemName FROM icm.request WHERE Status= 'Closed';";

			returnDB = MySqlQuery.getClosedRequestsInformation(query51, mySql);
			returnDB.add("51");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "52":
			/** case 52: inspector gets all active requests information */
			String query52 = "SELECT RNumber,ApplicantID,InformationSystemName,CurrentPhase FROM icm.request WHERE Status= 'Active';";

			returnDB = MySqlQuery.getActiveRequestsInformation(query52, mySql);
			returnDB.add("52");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "53":
			/**
			 * case 53: inspector freezes a process check if the process is in status Active
			 */
			if (MySqlQuery.getStatus(stringFromClient[1], mySql).equals("Active")) {
				returnDB = MySqlQuery.setStatus(stringFromClient[1], "Suspended", mySql);
				// set exit active status
				MySqlQuery.exitActive(stringFromClient[1], mySql);
				// create new row in suspended_status_tbl
				MySqlQuery.enterSuspended(stringFromClient[1], mySql);
			} else {
				returnDB.add("failed");
			}
			returnDB.add("53");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "54":
			/** case 54: show request details to assessor */
			aList = MySqlQuery.informationOfSpecificRequest(stringFromClient[1], mySql);

			for (int i = 2; i < 7; i++)
				returnDB.add(aList.get(i));
			returnDB.add("54");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "55":
			/**
			 * case 55: check for extra information in evaluation_report table, return true
			 * if yes else no
			 */
			String query55 = "SELECT ExtraInformation FROM icm.evaluation_report WHERE RNumber='" + stringFromClient[1]
					+ "';";
			returnDB = MySqlQuery.checkForExtraInfo(query55, mySql);
			returnDB.add("55");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "56":
			/**
			 * case 56: return all open new request details (no matter if active/close) of
			 * specific rnumber
			 */
			String query56 = "SELECT * FROM icm.request WHERE RNumber='" + stringFromClient[1] + "';";
			returnDB = MySqlQuery.getAllDetailsOfRequest(query56, mySql);
			returnDB.add("56");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "57":
			/** case 57: get all RNumbers of specific information system name */
			String query57 = "SELECT RNumber FROM icm.request WHERE InformationSystemName='" + stringFromClient[1]
					+ "' AND Status ='Active';";
			returnDB = MySqlQuery.rnumbersOfSpecificISN(query57, mySql);
			returnDB.add("57");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "58":
			/** case 58: return the current phases and the finish date of the rnumber */
			String query58 = "SELECT CurrentPhase,FinishDate FROM icm.request WHERE RNumber = '" + stringFromClient[1]
					+ "';";
			returnDB = MySqlQuery.getCurrentPhaseANDFinishDate(query58, mySql);
			returnDB.add("58");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "59":
			/** case 59: check if evaluation report is exist in table */
			returnDB = MySqlQuery.check4EvaluationReport(stringFromClient[1], mySql);
			returnDB.add("59");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "60":
			/**
			 * case 60: get the extra information in evaluation_report of specific rnumber
			 */
			String query60 = "SELECT ExtraInformation FROM icm.evaluation_report WHERE RNumber='" + stringFromClient[1]
					+ "';";
			returnDB = MySqlQuery.getForExtraInfo(query60, mySql);
			returnDB.add("60");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "61":
			/**
			 * case 61: ask for phase extension, stringFromClient[1]=rnumber
			 * stringFromClient[2]=AskPhaseExtension(date)
			 * stringFromClient[3]=ExtensionReason
			 */
			returnDB = MySqlQuery.sendTimeExtensionForApproval(stringFromClient[1], "Assessment", stringFromClient[2],
					stringFromClient[3], mySql);
			returnDB.add("61");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "62":
			/**
			 * case 62: return all active requests
			 * details:RNumber,ApplicantID,InformationSystemName,CurrentPhase
			 */
			String query62 = "SELECT RNumber,ApplicantID,InformationSystemName,CurrentPhase FROM icm.request WHERE Status= 'Active';";

			returnDB = MySqlQuery.getActiveRequestsInformation(query62, mySql);
			returnDB.add("62");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "63":
			/**
			 * case 63: return all active requests
			 * details:RNumber,ApplicantID,InformationSystemName,CurrentPhase
			 */
			String query63 = "SELECT RNumber,ApplicantID,InformationSystemName,CurrentPhase FROM icm.request WHERE Status= 'Suspended';";

			returnDB = MySqlQuery.getActiveRequestsInformation(query63, mySql);// this is not really active request just
																				// the name-avoid duplicate of code
			returnDB.add("63");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "64":
			/** case 64: change Estimated Finish Date By Inspector */
			String query64 = "SELECT * FROM icm.finish_date_update WHERE RNumber= '" + stringFromClient[1] + "';";
			String query64_1, query64_2;

			aList = MySqlQuery.checkFinishDateUpdate(query64, mySql);
			if (aList.contains("false"))// when contains "false" there is now rnumber in icm.finish_date_update table
				query64_1 = "INSERT INTO icm.finish_date_update (RNumber,InspectorName,UpdateDescription,NewFinishDate,UpdateDate) VALUES ('"
						+ stringFromClient[1] + "','" + stringFromClient[2] + "','" + stringFromClient[3] + "','"
						+ stringFromClient[4] + "','" + stringFromClient[5] + "' );";
			else
				query64_1 = "UPDATE icm.finish_date_update SET InspectorName = '" + stringFromClient[2]
						+ "',UpdateDescription = '" + stringFromClient[3] + "',NewFinishDate = '" + stringFromClient[4]
						+ "',UpdateDate = '" + stringFromClient[5] + "' WHERE RNumber = '" + stringFromClient[1] + "';";

			query64_2 = "UPDATE icm.request SET FinishDate = '" + stringFromClient[4] + "' WHERE RNumber = '"
					+ stringFromClient[1] + "';";

			if (MySqlQuery.updateDetails(query64_1, mySql).equals("true")
					&& MySqlQuery.updateDetails(query64_2, mySql).equals("true")) {

				returnDB.add("succeeded");
			} else {
				returnDB.add("failed");
			}
			returnDB.add("64");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "65":
			/** case 65: IT unfreeze process */

			String query65 = "UPDATE icm.request SET Status ='Active' WHERE RNumber =  '" + stringFromClient[1] + "';";

			returnDB.add(MySqlQuery.updateDetails(query65, mySql));
			// set exit suspended status
			MySqlQuery.exitSuspended(stringFromClient[1], mySql);
			// create new row in active_status_tbl
			MySqlQuery.enterActiveStatus(stringFromClient[1], mySql);
			returnDB.add("65");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "66":
			/** case 66: update engineer permission from icm.permissions table */
			String query66 = "UPDATE icm.permissions SET " + stringFromClient[2] + "= '1' WHERE EngineerID ='"
					+ stringFromClient[1] + "';";

			String query66_1 = "UPDATE icm.permissions SET Examiner = '1' WHERE EngineerID ='" + stringFromClient[1]
					+ "';";
			returnDB.add(MySqlQuery.updateDetails(query66, mySql));// updateDetailsInPhase
			if (stringFromClient[2].equals("CommitteeMember"))
				MySqlQuery.updateDetails(query66_1, mySql);
			returnDB.add("66");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "67":
			/**
			 * case 67: update engineer permission from icm.permissions table if there is
			 * not active request in same permission
			 */

			String query67_1 = "UPDATE icm.permissions SET " + stringFromClient[2] + "= '0' WHERE EngineerID ='"
					+ stringFromClient[1] + "';";
			String query67_2 = "UPDATE icm.permissions SET Examiner = '0' WHERE EngineerID ='" + stringFromClient[1]
					+ "';";

			returnDB = MySqlQuery.getActiveRequestsANDCheckTheRole(stringFromClient[1], stringFromClient[2], mySql);
			if (returnDB.isEmpty()) {// if contains true so there is no active request of the specific user with
										// the specific permission
				returnDB.add(MySqlQuery.updateDetails(query67_1, mySql));// you can remove his permission

				if (stringFromClient[2].equals("CommitteeMember"))
					MySqlQuery.updateDetails(query67_2, mySql);
			}
			returnDB.add("67");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "68":
			/**
			 * case 68: return all the rnumbers that waiting for inspector approval for
			 * phase duration
			 */
			String query68 = "SELECT RNumber,PhaseName,AskPhaseDuration FROM icm.phase WHERE DurationApprovedByInspector = 'Empty' AND WaitingForApproval = 'DurationApproval';";
			returnDB = MySqlQuery.getAskPhaseDurationWait4Inspector(query68, mySql);
			returnDB.add("68");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "69":
			/**
			 * case 69: return all the rnumbers that in closing stage and wait for inspector
			 * approval
			 */
			String query69 = "SELECT RNumber,CompletedOrDenied FROM icm.requests_to_close WHERE ApprovedByInspector = '0' ;";
			returnDB = MySqlQuery.getCloseRequestWaiting4Inspector(query69, mySql);
			returnDB.add("69");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "70":
			/**
			 * case 70: show to inspector all assessor automatic nominations for approval
			 */
			String query70 = "SELECT RNumber,AssessorID FROM icm.request_role WHERE WaitingToApproveAssessor = '1';";
			returnDB = MySqlQuery.getAutoAssessorNominationsInfo(query70, mySql);

			returnDB.add("70");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "71":
			/**
			 * case 71: close request when inspector & user approved to close & set closure
			 * phase in request table
			 */
			String query71 = "UPDATE icm.requests_to_close SET ClosedDate ='" + LocalDate.now() + "' WHERE RNumber ='"
					+ stringFromClient[1] + "';";
			String query71_1 = "UPDATE icm.requests_to_close SET ApprovedByInspector = '1' WHERE RNumber ='"
					+ stringFromClient[1] + "';";
			String query71_2 = "UPDATE icm.request SET Status = 'Closed' WHERE RNumber ='" + stringFromClient[1]
					+ "' ;";
			String query71_3 = "SELECT ApprovedByUser FROM icm.requests_to_close WHERE RNumber ='" + stringFromClient[1]
					+ "';";

			temp = MySqlQuery.updateDetails(query71_1, mySql);
			tempp = MySqlQuery.checkApprovedByUser(query71_3, mySql);

			if (temp.equals("true"))
				returnDB.add("succeeded");

			if (temp.equals("true") && tempp.equals("true")) {
				MySqlQuery.updateDetails(query71_2, mySql);
				MySqlQuery.updateDetails(query71, mySql);
				MySqlQuery.exitActive(stringFromClient[1], mySql);
			}

			returnDB.add("71");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "72":
			/**
			 * case 72: return all the rnumbers that waiting for inspector approval for
			 * phase extension
			 */
			String query72 = "SELECT RNumber,PhaseName,AskPhaseExtension FROM icm.phase WHERE ExtensionApprovedByInspector = 'Empty' AND WaitingForApproval = 'ExtensionApproval';";
			returnDB = MySqlQuery.getAskPhaseExtensionWait4Inspector(query72, mySql);
			returnDB.add("72");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "73":
			/** case 73: update rnumber DurationApprovedByInspector to accepted */
			String query73 = "UPDATE icm.phase SET DurationApprovedByInspector = 'Accepted',ConfirmedPhaseDuration = '"
					+ stringFromClient[3] + "' WHERE RNumber ='" + stringFromClient[1] + "' AND PhaseName='"
					+ stringFromClient[4] + "' ;";
			String query73_1 = "UPDATE icm.request FinishDate = '" + stringFromClient[3] + "';";
			MySqlQuery.updateDetails(query73_1, mySql);

			if (MySqlQuery.updateDetails(query73, mySql).equals("true"))
				returnDB.add("succeeded");
			else
				returnDB.add("failed");
			returnDB.add("73");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "74":
			/** case 74: update rnumber DurationApprovedByInspector to denied */
			String query74 = "UPDATE icm.phase SET DurationApprovedByInspector = 'Denied',WaitingForApproval = 'Empty' WHERE RNumber ='"
					+ stringFromClient[1] + "' AND PhaseName='" + stringFromClient[3] + "' ;";
			if (MySqlQuery.updateDetails(query74, mySql).equals("true"))
				returnDB.add("succeeded");
			else
				returnDB.add("failed");
			returnDB.add("74");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "75":
			/** case 75: update rnumber ExtensionApprovedByInspector to accepted */
			String query75 = "UPDATE icm.phase SET ExtensionApprovedByInspector = 'Accepted',ConfirmedPhaseDuration = '"
					+ stringFromClient[3] + "' WHERE RNumber ='" + stringFromClient[1] + "' AND PhaseName='"
					+ stringFromClient[4] + "' ;";
			String query75_1 = "UPDATE icm.request FinishDate = '" + stringFromClient[3] + "';";
			MySqlQuery.updateDetails(query75_1, mySql);

			if (MySqlQuery.updateDetails(query75, mySql).equals("true"))
				returnDB.add("succeeded");
			else
				returnDB.add("failed");
			returnDB.add("75");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "76":
			/** case 76: update rnumber ExtensionApprovedByInspector to denied */
			String query76 = "UPDATE icm.phase SET ExtensionApprovedByInspector = 'Denied' WHERE RNumber ='"
					+ stringFromClient[1] + "' AND PhaseName='" + stringFromClient[3] + "' ;";
			if (MySqlQuery.updateDetails(query76, mySql).equals("true"))
				returnDB.add("succeeded");
			else
				returnDB.add("failed");
			returnDB.add("76");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "77":
			/** case 77: update assessor by inspector */
			String query77 = "UPDATE icm.request_role SET WaitingToApproveAssessor = '0' WHERE RNumber ='"
					+ stringFromClient[1] + "'  ;";
			if (MySqlQuery.updateDetails(query77, mySql).equals("true"))
				returnDB.add("succeeded");

			String query77_1 = "SELECT AssessorID FROM icm.request_role WHERE RNumber = '" + stringFromClient[1] + "';";
			engineersTreatsNextPhase.add(MySqlQuery.findWhoTreatsNextPhase(query77_1, "AssessorID", mySql));

			returnDB = MySqlQuery.goToNextPhase(stringFromClient[1], engineersTreatsNextPhase.toString(), "Assessment",
					mySql);

			returnDB.add("77");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "78":
			/**
			 * case 78: update assessor by inspector and nominate assessor id in request
			 * role table
			 */
			String query78 = "UPDATE icm.request_role SET AssessorID = '" + stringFromClient[2]
					+ "',WaitingToApproveAssessor = '0' WHERE RNumber ='" + stringFromClient[1] + "' ;";
			if (MySqlQuery.updateDetails(query78, mySql).equals("true"))
				returnDB.add("succeeded");

			String query78_1 = "SELECT AssessorID FROM icm.request_role WHERE RNumber = '" + stringFromClient[1] + "';";
			engineersTreatsNextPhase.add(MySqlQuery.findWhoTreatsNextPhase(query78_1, "AssessorID", mySql));

			returnDB = MySqlQuery.goToNextPhase(stringFromClient[1], engineersTreatsNextPhase.toString(), "Assessment",
					mySql);

			returnDB.add("78");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "79":
			/** case 79: Activity report */
			returnDB = MySqlQuery.getActivityReport(stringFromClient[1], stringFromClient[2], stringFromClient[3],
					mySql);

			returnDB.add("79");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "80":
			/** case 80: get all RNumbers of specific information system name */
			String query80 = "SELECT RNumber FROM icm.request WHERE InformationSystemName='" + stringFromClient[1]
					+ "' AND Status ='Active';";
			returnDB = MySqlQuery.rnumbersOfSpecificISN(query80, mySql);
			returnDB.add("80");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "81":
			/** case 81: looking for missing roles of specific RNumber */
			String query81 = "SELECT InspectorID,ChairmanID,CommitteeMember2ID,CommitteeMember3ID FROM icm.request_role WHERE RNumber='"
					+ stringFromClient[1] + "' ;";
			returnDB = MySqlQuery.missingRolesOfSpecificRNumberForIT(query81, mySql);
			returnDB.add("81");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "82":
			/** Performance report */
			returnDB.add(Integer.toString(MySqlQuery.getPerformanceReport(mySql)));

			returnDB.add("82");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "83":
			/**
			 * case 83: get all roles (Chairman, Inspector and CommitteeMember) of specific
			 * rnumber
			 */
			returnDB = MySqlQuery.ITRolesOfSpecificRNumber(stringFromClient[1], mySql);
			returnDB.add("83");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "84":
			/** case 84: all engineers details from request table */
			temp = MySqlQuery.findCommitteMem2(stringFromClient[1], mySql);// in case we have already committee member2
			tempp = MySqlQuery.findCommitteMem3(stringFromClient[1], mySql);// in case we have already committee member3

			engineersID = MySqlQuery.getAllEngineerIDByPermission(stringFromClient[2], mySql);
			if (temp != null)
				engineersID.remove(temp);
			if (tempp != null)
				engineersID.remove(tempp);
			returnDB = MySqlQuery.getEngineersInfo(engineersID, mySql);
			returnDB.add("84");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "85":
			/** case 85: inspector nominates */
			try {
				returnDB.add(MySqlQuery.addNewNomination(stringFromClient[1], stringFromClient[2], stringFromClient[3],
						mySql));
				returnDB.add("85");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "86":
			/** case 86: all engineers details from request table */
			aList = MySqlQuery.findEngineerInRole(stringFromClient[1], stringFromClient[2], mySql);

			if (stringFromClient[2].equals("Examiner")) {
				engineersID.add(MySqlQuery.findCommitteMem2(stringFromClient[1], mySql));
				engineersID.add(MySqlQuery.findCommitteMem3(stringFromClient[1], mySql));
			} else {
				// find all engineers with role permissions
				engineersID = MySqlQuery.getAllEngineerIDByPermission(stringFromClient[2], mySql);
			}
			engineersID.removeAll(aList);

			returnDB = MySqlQuery.getEngineersInfo(engineersID, mySql);
			if (returnDB.isEmpty())
				returnDB.add("false");
			returnDB.add("86");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "87":
			/** case 87: change nomination in request phase-IT DEPT MANAGER */
			try {
				if (stringFromClient[2].equals("CommitteeMember")) {
					engineersID.add(MySqlQuery.findCommitteMem2(stringFromClient[1], mySql)); // get committee member 2
					engineersID.add(MySqlQuery.findCommitteMem3(stringFromClient[1], mySql)); // get committee member 3
					engineersID.remove(stringFromClient[4]); // remove member that IT wants to change

					String query87 = "UPDATE icm.request_role SET CommitteeMember2ID=NULL, CommitteeMember3ID=NULL WHERE RNumber = '"
							+ stringFromClient[1] + "';";

					MySqlQuery.updateDetails(query87, mySql);
					MySqlQuery.addNewNomination(stringFromClient[1], stringFromClient[2], engineersID.get(0), mySql);

				}
				returnDB.add(MySqlQuery.addNewNomination(stringFromClient[1], stringFromClient[2], stringFromClient[3],
						mySql));
				returnDB.add("87");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "88":
			/** Delays report */
			returnDB = MySqlQuery.getDelaysReport(stringFromClient[1], mySql);

			returnDB.add("88");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "89":
			/** case 89: check if exist admin to information system */
			String query89 = "SELECT * FROM icm.engineer_responsible_for_information_system;";
			returnDB = MySqlQuery.checkInformationSystemAdmin(query89, mySql);
			System.out.println(returnDB + "return");
			if (returnDB.isEmpty())
				returnDB.add("false");
			returnDB.add("89");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "90":
			/** case 90:get all engineers in system */
			String query90 = "SELECT UserID,FullName,UserDepartment FROM icm.user WHERE Engineer = '1';";
			returnDB = MySqlQuery.getEngineers(query90, mySql);
			returnDB.add("90");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "91":
			/** case 91: update admin in information system by IT */
			String query91 = "UPDATE icm.engineer_responsible_for_information_system SET " + stringFromClient[1]
					+ " = '" + stringFromClient[2] + "';";
			if (MySqlQuery.updateDetails(query91, mySql).equals("true"))
				returnDB.add("succeeded");

			returnDB.add("91");
			try {
				client.sendToClient(returnDB);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "100":
			/**
			 * case 100: return the requests of specific assessor
			 */
			String query100 = "SELECT RNumber FROM icm.request_role RR WHERE RR.AssessorID='" + stringFromClient[1]
					+ "';";
			aList = MySqlQuery.findRequestAs_(query100, mySql);
			aList = MySqlQuery.onlyActiveRequests(aList, "Assessment", mySql);
			returnDB = MySqlQuery.findConfirmedPhaseDuration(aList, "Assessment", mySql);
			if (returnDB.isEmpty())
				returnDB.add("false");
			returnDB.add("100");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "101":
			/**
			 * case 101: return the requests of specific chairman
			 */
			String query101 = "SELECT RNumber FROM icm.request_role RR WHERE RR.ChairmanID='" + stringFromClient[1]
					+ "';";
			aList = MySqlQuery.findRequestAs_(query101, mySql);
			aList = MySqlQuery.onlyActiveRequests(aList, "Decision", mySql);
			returnDB = MySqlQuery.findConfirmedPhaseDuration(aList, "Decision", mySql);
			if (returnDB.isEmpty())
				returnDB.add("false");
			returnDB.add("101");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "102":
			/**
			 * case 102: return the requests of specific execution leader
			 */
			String query102 = "SELECT RNumber FROM icm.request_role RR WHERE RR.ExecutionLeaderID='"
					+ stringFromClient[1] + "';";
			aList = MySqlQuery.findRequestAs_(query102, mySql);
			aList = MySqlQuery.onlyActiveRequests(aList, "Execution", mySql);
			returnDB = MySqlQuery.findConfirmedPhaseDuration(aList, "Execution", mySql);
			if (returnDB.isEmpty())
				returnDB.add("false");
			returnDB.add("102");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "103":
			/**
			 * case 103: return the requests of specific examiner
			 */
			String query103 = "SELECT RNumber FROM icm.request_role RR WHERE RR.ExaminerID='" + stringFromClient[1]
					+ "';";
			aList = MySqlQuery.findRequestAs_(query103, mySql);
			aList = MySqlQuery.onlyActiveRequests(aList, "Examination", mySql);
			returnDB = MySqlQuery.findConfirmedPhaseDuration(aList, "Examination", mySql);
			if (returnDB.isEmpty())
				returnDB.add("false");
			returnDB.add("103");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "104":
			/**
			 * case 101: return the requests of specific committee mem
			 */
			String query104 = "SELECT RNumber FROM icm.request_role RR WHERE RR.CommitteeMember2ID='"
					+ stringFromClient[1] + "' OR  RR.CommitteeMember3ID='" + stringFromClient[1] + "' ;";
			aList = MySqlQuery.findRequestAs_(query104, mySql);
			aList = MySqlQuery.onlyActiveRequests(aList, "Decision", mySql);
			returnDB = MySqlQuery.findConfirmedPhaseDuration(aList, "Decision", mySql);
			if (returnDB.isEmpty())
				returnDB.add("false");
			returnDB.add("104");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		//////////

		case "105":
			/**
			 * case 105: return the requests for IT dep manager
			 */
			String query105 = "SELECT RNumber,CurrentPhase FROM icm.request WHERE Status ='Active';";

			aList = MySqlQuery.findRequestAs4105(query105, mySql);
			returnDB = MySqlQuery.findConfirmedPhaseDuration4it(aList, mySql);

			if (returnDB.isEmpty())
				returnDB.add("false");
			returnDB.add("105");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "106":
			/**
			 * case 106: return the requests for inspector.
			 */
			String query106 = "SELECT RNumber,CurrentPhase FROM icm.request WHERE Status ='Active';";

			aList = MySqlQuery.findRequestAs4105(query106, mySql);
			returnDB = MySqlQuery.findConfirmedPhaseDuration4it(aList, mySql);

			if (returnDB.isEmpty())
				returnDB.add("false");
			returnDB.add("106");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case "107":
			/**
			 * case 107: return the requests for IT dep manager thats in extension!
			 */
			String query107 = "SELECT RNumber,CurrentPhase FROM icm.request WHERE Status ='Active';";

			aList = MySqlQuery.findRequestAs4105(query107, mySql);
			returnDB = MySqlQuery.findExtension4it(aList, mySql);

			if (returnDB.isEmpty())
				returnDB.add("false");
			returnDB.add("107");

			try {
				client.sendToClient(returnDB);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		}

	}

	/*
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/*
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server stopped.");
	}

	// Class methods ***************************************************

	/*
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 * 
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 * is entered.
	 */
	public void main(String[] args) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		try {

			this.listen(); // Start listening for connections

		} catch (BindException bex) {

		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

}
