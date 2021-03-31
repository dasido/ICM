package client;

import java.io.IOException;
import java.util.ArrayList;

import controllers.loginWindowController;
import entities.MyFile;
import controllers.ApproveClosingARequestController;
import controllers.AssessorHomepageController;
import controllers.ChangeEstimatedFinishDateByInspectorController;
import controllers.ExaminerHomepageController;
import controllers.ExecutionLeaderHomepageController;
import controllers.ExtraInfoController;
import controllers.ExtraInfoForRequestForTheCommitteeController;
import controllers.FieldChoiceController;
import controllers.ITDepartmentManagerHomepageController;
import controllers.InspectorHomepageController;
import controllers.MemberOfCommitteeOrChairmanHomepageController;
import controllers.NewEvaluationReportByAssessorController;
import controllers.OkMsgController;
import controllers.OpenChangeRequestController;
import controllers.ReportActivityController;
import controllers.ReportPerformanceController;
import controllers.UserHomePageController;
import ocsf.client.AbstractClient;
import server.mysqlConnection;

public class EchoClient extends AbstractClient {
	private static EchoClient client = null;
	private loginWindowController loginWindowController;
	private UserHomePageController userHomePageController;
	private OpenChangeRequestController openChangeRequestController;
	private ApproveClosingARequestController approveClosingARequestController;
	private FieldChoiceController fieldChoiceController;
	private AssessorHomepageController assessorHomepageController;
	private MemberOfCommitteeOrChairmanHomepageController memberOfCommitteeOrChairmanHomepageController;
	private ExecutionLeaderHomepageController executionLeaderHomepageController;
	private ExaminerHomepageController examinerHomepageController;
	private InspectorHomepageController inspectorHomepageController;
	private ITDepartmentManagerHomepageController iTDepartmentManagerHomepageController;
	private OkMsgController okMsgController;
	private ChangeEstimatedFinishDateByInspectorController changeEstimatedFinishDateByInspectorController;
	private ExtraInfoController extraInfoController;
	private ReportActivityController reportActivityController;
	private ReportPerformanceController reportPerformanceController;
	private NewEvaluationReportByAssessorController newEvaluationReportByAssessorController;
	private ExtraInfoForRequestForTheCommitteeController extraInfoForRequestForTheCommitteeController;
	public EchoClient(String host, int port) throws IOException {
		super(host, port); // Call the superclass constructor
		openConnection();
		if (isConnected() == false) {
			throw new IOException("Could not connect to server.");
		}
		System.out.println("Connected");
		loginWindowController = null;
		userHomePageController = null;
	}

	//////////////// setter and getter of controllers/////////////

	public void setloginWindowController(loginWindowController loginWindowController) {
		this.loginWindowController = loginWindowController;
	}

	public loginWindowController getloginWindowController() {
		return loginWindowController;
	}
	
	public ExtraInfoForRequestForTheCommitteeController getExtraInfoForRequestForTheCommitteeController() {
		return extraInfoForRequestForTheCommitteeController;
	}

	public void setExtraInfoForRequestForTheCommitteeController(ExtraInfoForRequestForTheCommitteeController extraInfoForRequestForTheCommitteeController) {
		this.extraInfoForRequestForTheCommitteeController = extraInfoForRequestForTheCommitteeController;
	}



	public void setUserHomePageController(UserHomePageController userHomePageController) {
		this.userHomePageController = userHomePageController;
	}

	public UserHomePageController getUserHomePageController() {
		return userHomePageController;
	}
	
	public void setOpenChangeRequestController(OpenChangeRequestController openChangeRequestController) {
		this.openChangeRequestController = openChangeRequestController;
	}
	
	public OpenChangeRequestController getopenChangeRequestController() {
		return openChangeRequestController;
	}
	
	public void setApproveClosingARequestController(ApproveClosingARequestController approveClosingARequestController) {
		this.approveClosingARequestController = approveClosingARequestController;
	}
	
	public ApproveClosingARequestController getApproveClosingARequestController() {
		return approveClosingARequestController;
	}
	
	public void setFieldChoiceController(FieldChoiceController fieldChoiceController) {
		this.fieldChoiceController = fieldChoiceController;
	}
	
	public FieldChoiceController getFieldChoiceController() {
		return fieldChoiceController;
	}
	
	public void setAssessorHomepageController(AssessorHomepageController assessorHomepageController) {
		this.assessorHomepageController = assessorHomepageController;
	}
	
	public AssessorHomepageController getAssessorHomepageController() {
		return assessorHomepageController;
	}
	
	public void setMemberOfCommitteeOrChairmanHomepageController(MemberOfCommitteeOrChairmanHomepageController memberOfCommitteeOrChairmanHomepageController) {
		this.memberOfCommitteeOrChairmanHomepageController = memberOfCommitteeOrChairmanHomepageController;
	}
	
	public MemberOfCommitteeOrChairmanHomepageController getMemberOfCommitteeOrChairmanHomepageController() {
		return memberOfCommitteeOrChairmanHomepageController;
	}
	
	public void setExecutionLeaderHomepageController(ExecutionLeaderHomepageController executionLeaderHomepageController) {
		this.executionLeaderHomepageController = executionLeaderHomepageController;
	}
	
	public ExecutionLeaderHomepageController getExecutionLeaderHomepageController() {
		return executionLeaderHomepageController;
	}
	
	public void setExaminerHomepageController(ExaminerHomepageController examinerHomepageController) {
		this.examinerHomepageController = examinerHomepageController;
	}
	
	public ExaminerHomepageController getExaminerHomepageController() {
		return examinerHomepageController;
	}
	
	public void setInspectorHomepageController(InspectorHomepageController inspectorHomepageController) {
		this.inspectorHomepageController = inspectorHomepageController;
	}
	
	public InspectorHomepageController getInspectorHomepageController() {
		return inspectorHomepageController;
	}
	
	public OkMsgController getOkMsgController() {
		return okMsgController;
	}
	
	public ITDepartmentManagerHomepageController getiTDepartmentManagerHomepageController() {
		return iTDepartmentManagerHomepageController;
	}

	public void setiTDepartmentManagerHomepageController(ITDepartmentManagerHomepageController iTDepartmentManagerHomepageController) {
		this.iTDepartmentManagerHomepageController = iTDepartmentManagerHomepageController;
	}

	public void setOkMsgController(OkMsgController okMsgController) {
		this.okMsgController = okMsgController;
	}
	
	public ExtraInfoController getExtraInfoController() {
		return extraInfoController;
	}

	public void setExtraInfoController(ExtraInfoController extraInfoController) {
		this.extraInfoController = extraInfoController;
	}
	
	public ReportActivityController getReportActivityController() {
		return reportActivityController;
	}

	public void setReportActivityController(ReportActivityController reportActivityController) {
		this.reportActivityController = reportActivityController;
	}

	public ReportPerformanceController getReportPerformanceController() {
		return reportPerformanceController;
	}

	public void setReportPerformanceController(ReportPerformanceController reportPerformanceController) {
		this.reportPerformanceController = reportPerformanceController;
	}

	public NewEvaluationReportByAssessorController getNewEvaluationReportByAssessorController() {
		return newEvaluationReportByAssessorController;
	}

	public void setNewEvaluationReportByAssessorController(NewEvaluationReportByAssessorController newEvaluationReportByAssessorController) {
		this.newEvaluationReportByAssessorController = newEvaluationReportByAssessorController;
	}

	public ChangeEstimatedFinishDateByInspectorController getChangeEstimatedFinishDateByInspectorController() {
		return changeEstimatedFinishDateByInspectorController;
	}

	public void setChangeEstimatedFinishDateByInspectorController(ChangeEstimatedFinishDateByInspectorController changeEstimatedFinishDateByInspectorController) {
		this.changeEstimatedFinishDateByInspectorController = changeEstimatedFinishDateByInspectorController;
	}
	
	
	public static EchoClient GetClient() {
		if (client != null) {
			return client;
		}
		return null;
	}

	public static EchoClient GetClient(String host, int port) throws Exception {
		if (client == null) {
			client = new EchoClient(host, port);
		}
		return client;
	}

	// Instance methods ************************************************
	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	public void handleMessageFromServer(Object msg) {
		@SuppressWarnings("unchecked")
		ArrayList<String> returnDB = (ArrayList<String>) msg;
		/*
		if (msg instanceof MyFile)
			getopenChangeRequestController().getFile(msg);
			*/
		System.out.println("Message received from server: " + returnDB.toString());

		String command = returnDB.get(returnDB.size() - 1);
		returnDB.remove(returnDB.size() - 1);

		switch (command) {
		/**
		 * Log In - checks if UserName and Password is correct and if the User already logged in, if both not happening login to system.
		 */
		case "1":
			System.out.println("Message received from server case1: " + returnDB.toString());
			try {
				loginWindowController.checkLogIn(returnDB);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		
		/**
		 * User HomePage - set the User ComboBox with all the requests he opened
		 */
		case "2":
			System.out.println("Message received from server case2: " + returnDB.toString());
			try {
				userHomePageController.setUserCBX(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
		
		/**
		 * User HomePage - After the User chose a request and pressed search its showing the latest updates on this requests in the fields below
		 */
		case "3":
			System.out.println("Message received from server case3: " + returnDB.toString());
			try {
				userHomePageController.showUserRequestInfo(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
		
		/**
		 * Approve closing a request page - Sets the ComboBox with all the requests that the specific User still need to approve closing
		 */
		case "4":
			System.out.println("Message received from server case4: " + returnDB.toString());
			try {
				approveClosingARequestController.setApproveClosingARequestCBX(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
		/**
		 * User HomePage - After user clicked logout, it updates the user class login status
		 */
		case "5":
			System.out.println("Message received from server case5: " + returnDB.toString());
			try {
				userHomePageController.updateAfterLogout(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
		
		/**
		 * Approve closing a request page - Sets the Reason for closing TextField regards to the request the user chose in ComboBox
		 */
		case "6":
			System.out.println("Message received from server case6: " + returnDB.toString());
			try {
				approveClosingARequestController.getUserRequestDetails(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
		
		/**
		 * Approve closing a request page - After the user clicked Approve it updates the specific request status the user chose in ComboBox if needed
		 */
		case "7":
			System.out.println("Message received from server case7: " + returnDB.toString());
			try {
				approveClosingARequestController.updateUserRequestStatus(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
		
		/**
		 * Field Choice page - Sets the ComboBox according to the specific user roles permissions
		 */
		case "8":
			System.out.println("Message received from server case8: " + returnDB.toString());
			try {
				fieldChoiceController.setFieldChoiceCBXForUser(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
		
		/**
		 * get approval for open a change request.
		 */
		case "9":
			System.out.println("Message received from server case9: " + returnDB.toString());
			try {
				openChangeRequestController.sendApproval(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
		
		/**
		 * get approval for send file to server.
		 */
		case "10":
			System.out.println("Message received from server case10: " + returnDB.toString());
			try {
				openChangeRequestController.fileConfirmation(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
			/**
			 * ITDepartmentManager HomePage - Sets "Choose information system" ComboBox with all the names of the information systems in the system, and Sets Add new nomination - "Choose role" ComboBox + Sets Change nomination - "Choose role" ComboBox with all the roles the IT Department Manager can nominate at the "Nomination Management" tab
			 */
		case "11":
			System.out.println("Message received from server case11: " + returnDB.toString());
			try {
				iTDepartmentManagerHomepageController.setInformationSystemsNamesToNominationManagementCBX(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
			
			
		/**
		 * Inspector HomePage - Sets "Choose information system" ComboBox with all the names of the information systems in the system, and Sets New Nomination - "Choose role" ComboBox + Sets Change Nomination - "Choose role" ComboBox with all the roles the inspector can nominate at the "Nomination Management" tab
		 */
		case "12":
			System.out.println("Message received from server case12: " + returnDB.toString());
			try {
				inspectorHomepageController.setInformationSystemsNamesToNominationManagementCBX(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;	
			
		/**
		 * Assessor HomePage - Sets the table with all the requests the specific Assessor need to take care of
		 */
		case "13":
			System.out.println("Message received from server case13: " + returnDB.toString());
			try {
				assessorHomepageController.getAssessorRequestsDetailsToSet(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
		/**
		 * Chairman_MemberOfCommittee HomePage - Sets the table at the "Chairman" tab with all the requests the specific Chairman need to take care of
		 */
		case "14":
			System.out.println("Message received from server case14: " + returnDB.toString());
			try {
				memberOfCommitteeOrChairmanHomepageController.getChairmanRequestsDetailsToSet(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
		/**
		 * Chairman_MemberOfCommittee HomePage - Sets the table at the "Member Of Committee" tab with all the requests the specific Member Of Committee need to take care of
		 */
		case "15":
			System.out.println("Message received from server case15: " + returnDB.toString());
			try {
				memberOfCommitteeOrChairmanHomepageController.getMemberOfCommitteeRequestsDetailsToSet(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
		/**
		 * Execution Leader HomePage - Sets the table with all the requests the specific Execution Leader need to take care of
		 */
		case "16":
			System.out.println("Message received from server case16: " + returnDB.toString());
			try {
				executionLeaderHomepageController.getExecutionLeaderRequestsDetailsToSet(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
		
		/**
		 * Examiner HomePage - Sets the table with all the requests the specific Examiner need to take care of
		 */
		case "17":
			System.out.println("Message received from server case17: " + returnDB.toString());
			try {
				examinerHomepageController.getExaminerRequestsDetailsToSet(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
		/**
		 * Inspector HomePage - Sets "Choose information system" ComboBox with all the names of the information systems in the system at the "Request Management" tab
		 */
		case "18":
			System.out.println("Message received from server case18: " + returnDB.toString());
			try {
				inspectorHomepageController.setInformationSystemsNamesToRequestManagementCBX(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
		/**
		 * Inspector HomePage - Sets "Choose another engineer" ComboBox with all the engineers in the system that are not the IT Department Manager, at the "Waiting for approval" tab
		 */
		case "19":
			System.out.println("Message received from server case19: " + returnDB.toString());
			try {
				inspectorHomepageController.setEngineersToWaitingForApprovalCBX(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
			
			/**
			 * Execution Leader - Sends RNumber to get if RNumber has a TestFailure, evaluation report, already asked for extension and if sent for approval.
			 */
		case "20":
			System.out.println("Message received from server case20: " + returnDB.toString());
			try {
				executionLeaderHomepageController.CheackingRNumber(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
			/**Execution Leader - sends 21 by click on view evaluation report to get the evaluation report for this RNumber
			 * 
			 */
		case "21":
			System.out.println("Message received from server case21: " + returnDB.toString());
			try {
				executionLeaderHomepageController.getEvaluationReportFromServer(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
			/**Execution Leader - after click finish, the server return "true" if the request is now passed to the next handler.
			 * 
			 */
		case "22":
			System.out.println("Message received from server case22: " + returnDB.toString());
			try {
				executionLeaderHomepageController.afterFisnishbtn(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
			/**Execution Leader - after click send for approval, he wait to confirm from the server that inspector got the estimation request.
			 * 
			 */
		case "23":
			System.out.println("Message received from server case23: " + returnDB.toString());
			try {
				executionLeaderHomepageController.approvalSent(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
			/**
			 * Execution Leader - get approval for time extension
			 */

		case "24":
			System.out.println("Message received from server case24: " + returnDB.toString());
			try {
				executionLeaderHomepageController.getExtensionFromServer(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			

			//Examiner - Sends RNumber to get if RNumber already asked for extension and if sent for approval.
		case "25":
			System.out.println("Message received from server case25: " + returnDB.toString());
			try {
				examinerHomepageController.CheackingRNumber(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
			
			//examiner - gets approval for time extension from server
		case "26":
			System.out.println("Message received from server case26: " + returnDB.toString());
			try {
				examinerHomepageController.getExtensionFromServer(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
			
			// Examiner - gets approval for Test failure.
		case "27":
			System.out.println("Message received from server case27: " + returnDB.toString());
			try {
				examinerHomepageController.getTestApproval(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			
			// Examiner - gets approval for Test approve.
			case "28":
			System.out.println("Message received from server case28: " + returnDB.toString());
			try {
				examinerHomepageController.getTestSentApproval(returnDB);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
			

			//IT Department Manager HomePage - Sets the table at the Permissions Management tab with all the engineers in the system with their permissions + Sets "Choose permission to set" ComboBox + Sets "Choose permission to delete" ComboBox
			case "29":
				System.out.println("Message received from server case29: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.setPermissionsManagementTable(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//IT Department Manager HomePage - Sets Employees original information table at View only access tab
			case "30":
				System.out.println("Message received from server case30: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.setEmployeesOriginalInfoTable(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			/**Assessor opens new evaulation report and waits for approval.this is the approval/declined
			 * 
			 */
			case "31":
				System.out.println("Message received from server case31: " + returnDB.toString());
				try {
					newEvaluationReportByAssessorController.getApproveforEvaluation(returnDB);
					}
				catch (Exception e2) {
					e2.printStackTrace();
					}
				break;
			
			//Chairman  - sends 32 by click on view evaluation report to get the buttons init.
			case "32":
				System.out.println("Message received from server case32: " + returnDB.toString());
				try {
					memberOfCommitteeOrChairmanHomepageController.getChairmanButtons(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				//Chairman  - get approval for extension request.
			case "33":
				System.out.println("Message received from server case33: " + returnDB.toString());
				try {
					memberOfCommitteeOrChairmanHomepageController.getExtensionFromServer(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			
			//Chairman or committee - sends 34 by click on view evaluation report to get the evaluation report for this RNumber
			case "34":
				System.out.println("Message received from server case34: " + returnDB.toString());
				try {
					memberOfCommitteeOrChairmanHomepageController.getEvaluationReportFromServer(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
			case "35":
				System.out.println("Message received from server case35: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.setRequestsOriginalInfoTable(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;	
				
				//Chairman - load combo box of examiner from the server.
			case "36":
				System.out.println("Message received from server case36: " + returnDB.toString());
				try {
					memberOfCommitteeOrChairmanHomepageController.initializableExaminerComboBox(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
				//Inspector HomePage - Sets "Choose active request" ComboBox according to the choice of the user at "Choose information system" ComboBox
			case "37":
				System.out.println("Message received from server case37: " + returnDB.toString());
				try {
					inspectorHomepageController.setChooseActiveRequestCBX(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
			//Inspector HomePage - Sets the Nomination Management table with all the details of users and their roles at a specific request
			case "38":
				System.out.println("Message received from server case38: " + returnDB.toString());
				try {
					inspectorHomepageController.getRolesDetailsOfRequestNumber(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//Inspector HomePage - Sets "Choose a role" ComboBox(under "Add new nomination") according to the choice of the user at "Choose active request" ComboBox
			case "39":
				System.out.println("Message received from server case39: " + returnDB.toString());
				try {
					inspectorHomepageController.setChooseARoleCBX(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
			//Inspector HomePage - Sets "Choose engineer" ComboBox according to the choice of the user at "Choose a role" ComboBox(under "Add new nomination")
			case "40":
				System.out.println("Message received from server case40: " + returnDB.toString());
				try {
					inspectorHomepageController.setChooseEngineerCBX(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				
				//Chairman - Asks for time extension and get approval from server.
			case "41":
				System.out.println("Message received from server case41: " + returnDB.toString());
				try {
					extraInfoController.getAskMoreInfoApproval(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				//Chairman - decline the request and gets approval.
			case "42":
				System.out.println("Message received from server case41: " + returnDB.toString());
				try {
					memberOfCommitteeOrChairmanHomepageController.getDeclineApproval(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				/**assessor home page-give back the answer if  execution duration approved,
				 * the date and if extra info for the request exist(after request choose in assessor home page)
				 */
			case "43":
				System.out.println("Message received from server case43: " + returnDB.toString());
				try {
					assessorHomepageController.executionDurationApprovedOrDeclined(returnDB);
					}
				catch (Exception e2) {
					e2.printStackTrace();
					}
				break;
				
				
				//Chairman - choose examiner and gets approval from server.

			case "44":
				System.out.println("Message received from server case44: " + returnDB.toString());
				try {
					memberOfCommitteeOrChairmanHomepageController.getNominateApproval(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				//Member of committee - choose request number and gets emd of working time.

			case "45":
				System.out.println("Message received from server case45: " + returnDB.toString());
				try {
					memberOfCommitteeOrChairmanHomepageController.getWorkDate(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				/**
				 * return if to enable ask for extension button
				 */
			case "46"://assessor home page -receive a true or false about if extension happened to assessment phase ,date to compare to current day and this way knows if extension button is enable
				System.out.println("Message received from server case46: " + returnDB.toString());
				try {
					assessorHomepageController.getFinalDayOfAssessment(returnDB);
					}
				catch (Exception e2) {
					e2.printStackTrace();
					}
				break;
				
				/*
				 * Inspector HomePage - Gets a message of success from the server about the new nomination of a specific Engineer to a specific role at a specific request and pops-up a window with a message of success
				 */
			case "47":
				System.out.println("Message received from server case47: " + returnDB.toString());
				try {
					inspectorHomepageController.msgOfNewNomination(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;	
				
				/**
				 * return the decision about time estimation
				 */
			case "48"://assessor home page -receive a true or false about if extension happened to assessment phase ,date to compare to current day and this way knows if extension button is enable
				System.out.println("Message received from server case48: " + returnDB.toString());
				try {
					assessorHomepageController.DecisionAboutTimeDuration(returnDB);
					}
				catch (Exception e2) {
					e2.printStackTrace();
					}
				break;
			
				//Inspector HomePage - Sets "Choose new engineer to set" ComboBox according to the choice of the user in the table at Nomination Management
			case "49":
				System.out.println("Message received from server case49: " + returnDB.toString());
				try {
					inspectorHomepageController.setChooseNewEngineerToSetCBX(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//Inspector HomePage - Gets a message of success from the server about the change of nomination of a specific Engineer to a specific role at a specific request and refresh the table to view the update
			case "50":
				System.out.println("Message received from server case50: " + returnDB.toString());
				try {
					inspectorHomepageController.msgOfChangeNomination(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//Inspector HomePage - After "Display all closed requests" button clicked shows all the closed requests in the system in the table at Request Management tab
			case "51":
				System.out.println("Message received from server case51: " + returnDB.toString());
				try {
					inspectorHomepageController.receiveAllClosedRequestsDetails(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;		
				
			//Inspector HomePage - After "Display all active requests" button clicked shows all the active requests in the system in the table at Request Management tab
			case "52":
				System.out.println("Message received from server case52: " + returnDB.toString());
				try {
					inspectorHomepageController.receiveAllActiveRequestsDetails(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//Inspector HomePage - After "Freeze process" button clicked checks if the update succeeded or not and refresh table if it did
			case "53":
				System.out.println("Message received from server case53: " + returnDB.toString());
				try {
					inspectorHomepageController.checkProcessGotFrozen(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;	
				
				
				/**
				 * open change request-set the properties of chosen request to the open change request
				 */
			case "54"://set the assessor fields when he open change request to see
				System.out.println("Message received from server case54: " + returnDB.toString());//return thye fields of open new change request for assessor
				try {
					openChangeRequestController.setFieldsToAssessor(returnDB);
					}
				catch (Exception e2) {
					e2.printStackTrace();
					}
				break;	
			
				/**
				 * assessor home page-answer if to open the extra information button
				 */
			case "55":
				System.out.println("Message received from server case55: " + returnDB.toString());//return thye fields of open new change request for assessor
				try {
					assessorHomepageController.isThereExtraInfo(returnDB);
					}
				catch (Exception e2) {
					e2.printStackTrace();
					}
				break;
				
			//IT manager open a request details in the only view page	
			case "56":
				System.out.println("Message received from server case56: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.goToOpenChangeRequest(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
				//Inspector HomePage - Sets "Choose request" ComboBox according to the choice of the user at "Choose information system" ComboBox at Request Management tab
			case "57":
				System.out.println("Message received from server case57: " + returnDB.toString());
				try {
					inspectorHomepageController.setChooseRequestCBX(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
			//ChangeEstimatedFinishDateByInspector Page - After the user clicked "Open request details" button at Inspector HomePage at Request Management tab, it Sets the needed fields in ChangeEstimatedFinishDateByInspector page
			case "58":
				System.out.println("Message received from server case58: " + returnDB.toString());
				try {
					changeEstimatedFinishDateByInspectorController.getCurrentPhaseAndFinishDate(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;	
				
			/**
			 * newEvaluationReportByAssessorController-check if need to open evaluation page with the properties of exsisting request or not
			 */
			case "59":
				System.out.println("Message received from server case59: " + returnDB.toString());//return thye fields of open new change request for assessor
				try {
					newEvaluationReportByAssessorController.setFieldsEvaluation(returnDB);
					}
				catch (Exception e2) {
					e2.printStackTrace();
					}
				break;
				
				/**
				 * extraInfoForRequestForTheCommitteeController- receive the extra information if exist to  
				 */
			case "60"://reply if to open extra information button
				System.out.println("Message received from server case60: " + returnDB.toString());//return thye fields of open new change request for assessor
				try {
					extraInfoForRequestForTheCommitteeController.infoToSet(returnDB);
					}
				catch (Exception e2) {
					e2.printStackTrace();
					}
				break;
				/**
				 * assessor home page the assessor ask for extension,case 61 return that the request was saved in the server
				 */
			case "61"://reply if to open extra information button
				System.out.println("Message received from server case61: " + returnDB.toString());//return thye fields of open new change request for assessor
				try {
					assessorHomepageController.getExtensionApproval(returnDB);
					}
				catch (Exception e2) {
					e2.printStackTrace();
					}
				break;	
				
				
				//IT manager gets all the active request from the server - request management tab.	
			case "62":
				System.out.println("Message received from server case62: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.setActiveRequestTable(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				//IT manager gets all the Freeze request from the server - request management tab.	
			case "63":
				System.out.println("Message received from server case63: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.setFreezeRequestTable(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			
				//ChangeEstimatedFinishDateByInspector Page - After "Save" button clicked and the data saved, gets a success message from the server
			case "64":
				System.out.println("Message received from server case64: " + returnDB.toString());
				try {
					changeEstimatedFinishDateByInspectorController.saveSucceeded(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;	
				
				//IT manager sends request number he wants to active and gets here approvment.
			case "65":
				System.out.println("Message received from server case65: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.getApprovedToUnFreeze(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				//IT manager sends permission and user id to add permission to this.user
			case "66":
				System.out.println("Message received from server case66: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.getAddPermissionApproved(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				
				//IT manager sends permission and user id to delete permission to this.user
			case "67":
				System.out.println("Message received from server case67: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.getdeletePermissionApproved(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			
				//Inspector HomePage - Sets "Phase duration time evaluation table" with all the requests numbers and their phases names that need approval to the estimated duration time sent to the inspector
			case "68":
				System.out.println("Message received from server case68: " + returnDB.toString());
				try {
					inspectorHomepageController.setPhaseDurationTimeEvaluationTable(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
			//Inspector HomePage - Sets "Approval to close a request table" with all the requests numbers and their reasons to closure that need to be closed and waiting for approval of the Inspector
			case "69":
				System.out.println("Message received from server case69: " + returnDB.toString());
				try {
					inspectorHomepageController.setCloseARequestTable(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//Inspector HomePage - Sets "Assessor nomination table" with all the requests numbers that got automatic nomination of Assessor and waiting for approval\change of the Inspector
			case "70":
				System.out.println("Message received from server case70: " + returnDB.toString());
				try {
					inspectorHomepageController.setAssessorNominationTable(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
			//Inspector HomePage - After the Inspector approved a request to close gets a message of success
			case "71":
				System.out.println("Message received from server case71: " + returnDB.toString());
				try {
					inspectorHomepageController.approvalOFClosingARequestSucceeded(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
			//Inspector HomePage - Sets "Phase extension time requests table" with all the requests numbers and their phases names that need approval to the extension time sent to the inspector
			case "72":
				System.out.println("Message received from server case72: " + returnDB.toString());
				try {
					inspectorHomepageController.setPhaseExtensionTimeTable(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
			//Inspector HomePage - After the Inspector approved a phase duration time in a specific request gets a message of success
			case "73":
				System.out.println("Message received from server case73: " + returnDB.toString());
				try {
					inspectorHomepageController.approvalOFPhaseDurationTimeSucceeded(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//Inspector HomePage - After the Inspector declined a phase duration time in a specific request gets a message of success
			case "74":
				System.out.println("Message received from server case74: " + returnDB.toString());
				try {
					inspectorHomepageController.declinationOFPhaseDurationTimeSucceeded(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//Inspector HomePage - After the Inspector approved a phase extension time in a specific request gets a message of success
			case "75":
				System.out.println("Message received from server case75: " + returnDB.toString());
				try {
					inspectorHomepageController.approvalOFPhaseExtensionTimeSucceeded(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//Inspector HomePage - After the Inspector declined a phase extension time in a specific request gets a message of success
			case "76":
				System.out.println("Messasge received from server case76: " + returnDB.toString());
				try {
					inspectorHomepageController.declinationOFPhaseExtensionTimeSucceeded(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
			//Inspector HomePage - After the Inspector approved an automatic nomination of Assessor to a specific request gets a message of success
			case "77":
				System.out.println("Message received from server case77: " + returnDB.toString());
				try {
					inspectorHomepageController.approvalOFAutomaticAssessorNominationSucceeded(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//Inspector HomePage - After the Inspector changed the Assessor nomination at a specific request gets a message of success
			case "78":
				System.out.println("Message received from server case78: " + returnDB.toString());
				try {
					inspectorHomepageController.changeOFAssessorNominationSucceeded(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;	
				
				//IT manager sends permission and user id to delete permission to this.user
			case "79":
				System.out.println("Message received from server case79: " + returnDB.toString());
				try {
					reportActivityController.getActivityReportFromServer(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
				//IT Department Manager HomePage - Sets "Choose active request" ComboBox according to the choice of the user at "Choose information system" ComboBox
			case "80":
				System.out.println("Message received from server case80: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.setChooseActiveRequestCBX(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//IT Department Manager HomePage - Sets "Choose a role" ComboBox(under "Add new nomination") according to the choice of the user at "Choose active request" ComboBox
			case "81":
				System.out.println("Message received from server case81: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.setChooseARoleCBX(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				//IT manager sends permission and user id to delete permission to this.user
			case "82":
				System.out.println("Message received from server case79: " + returnDB.toString());
				try {
					reportPerformanceController.getDelayReport(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				//IT Department Manager HomePage - Sets the Nomination Management table with all the details of users and their roles at a specific request
			case "83":
				System.out.println("Message received from server case83: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.getRolesDetailsOfRequestNumber(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
					
			//IT Department Manager HomePage - Sets "Choose engineer" ComboBox according to the choice of the user at "Choose a role" ComboBox(under "Add new nomination")
			case "84":
				System.out.println("Message received from server case84: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.setChooseEngineerCBX(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//IT Department Manager HomePage - Gets a message of success from the server about the new nomination of a specific Engineer to a specific role at a specific request and pops-up a window with a message of success
			case "85":
				System.out.println("Message received from server case85: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.msgOfNewNomination(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
			//IT Department Manager HomePage - Sets "Choose new engineer to set" ComboBox according to the choice of the user in the table at Nomination Management
			case "86":
				System.out.println("Message received from server case86: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.setChooseNewEngineerToSetCBX(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//IT Department Manager HomePage - Gets a message of success from the server about the change of nomination of a specific Engineer to a specific role at a specific request
			case "87":
				System.out.println("Message received from server case87: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.msgOfChangeNomination(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				//IT manager sends permission and user id to delete permission to this.user
			case "88":
				System.out.println("Message received from server case79: " + returnDB.toString());
				try {
					reportPerformanceController.setFrequencyBarChart(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				//IT Department Manager HomePage - Sets "Choose information system" ComboBox with all the names of the information systems in the system that don't have nomination of responsible for maintenance yet at the "Nomination Management" tab
			case "89":
				System.out.println("Message received from server case89: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.getInformationSystemsToNominateResponsiblesForMaintenance(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
			//IT Department Manager HomePage - Sets "Choose engineer" ComboBox according to the choice of the user at "Choose information system" ComboBox(under "Add new nomination")
			case "90":
				System.out.println("Message received from server case90: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.setChooseEngineerCBX2(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
				//IT Department Manager HomePage - Gets a message of success from the server about the new nomination of a specific Engineer to a specific information system to be in charge of support and maintenance, and pops-up a window with a message of success
			case "91":
				System.out.println("Message received from server case91: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.msgOfNewResponsibleEngineerSet(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;	
				
				//Assessor HomePage - checks if there's any requests in delay
			case "100":
				System.out.println("Message received from server case100: " + returnDB.toString());
				try {
					assessorHomepageController.getAssessorRequestsDelay(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
				//Chairman HomePage - checks if there's any requests in delay.
			case "101":
				System.out.println("Message received from server case101: " + returnDB.toString());
				try {
					memberOfCommitteeOrChairmanHomepageController.getChairManRequestsDelay(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
				//Execution Leader HomePage - checks if there's any requests in delay.
			case "102":
				System.out.println("Message received from server case102: " + returnDB.toString());
				try {
					executionLeaderHomepageController.getExecutionLeaderRequestsDelay(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				//Examiner HomePage - checks if there's any requests in delay.
			case "103":
				System.out.println("Message received from server case103: " + returnDB.toString());
				try {
					examinerHomepageController.getExaminerRequestsDelay(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
		
				
				
				//Committee HomePage - checks if there's any requests in delay.
			case "104":
				System.out.println("Message received from server case104: " + returnDB.toString());
				try {
					memberOfCommitteeOrChairmanHomepageController.getCommitteeRequestsDelay(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			
				//IT - checks if there's any requests in delay.
			case "105":
				System.out.println("Message received from server case105: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.getITRequestsDelay(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				//Inspector - checks if there's any requests in delay.
			case "106":
				System.out.println("Message received from server case106: " + returnDB.toString());
				try {
					inspectorHomepageController.getInspectorRequestsDelay(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
				
				//IT - checks if there's any requests with extensions
			case "107":
				System.out.println("Message received from server case107: " + returnDB.toString());
				try {
					iTDepartmentManagerHomepageController.getITRequestsExtensions(returnDB);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
				break;	
		}
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */
	public void handleMessageFromClientUI(String message) {
		try {
			System.out.println("Sent msg: " + message + " To Server");
			sendToServer(message);
		} catch (IOException e) {
			// clientUI.display
			// ("Could not send message to server. Terminating client.");
			quit();
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}



	


	

}
