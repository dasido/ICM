package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import client.EchoClient;
import entities.InfoTable;
import entities.Request;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AssessorHomepageController implements Initializable {
	private EchoClient mainClient;
	private String rNumber;
	private String ApprovedDate;
	Vector<Request> requestsVectorAssessor = new Vector<>();
	

	@FXML
	private TableView<InfoTable> assessorTable; // screen data

	@FXML
	private TableColumn<InfoTable, String> requestNumberColumn;// screen date

	@FXML
	private TableColumn<InfoTable, String> informationSystemNameColumn;// screen date

	@FXML
	private Button btnAskForTimeExtention;// open time extension fxml

	@FXML
	private Button btnShowExtraInformation;// open the extra info for request (for execution leader ) fxml

	@FXML
	private Button btnOpenEvaluationReport;// button that will open new evaluation report to fill

	@FXML
	private Tab meaningEvaluation;// assessor sigle tab

	@FXML
	private Button sendDateForApproval;// button to send the date from the datepicker to the server(and next to the
										// inspector)

	@FXML
	private DatePicker datePicker;// place to pick a date for estimated duration time

	@FXML
	private Text txtPhaseEstimated;// text on the screen

	@FXML
	private Button btnBack;// back to field choice

	@FXML
	private Text txtSituation;// messages to the assessor

	@FXML
	private Button btnRefreshTable;

	@FXML
	private Button btnShowDetails;
	
	@FXML
	private TextArea textFieldRequestsInDelay;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializer();
	}
/**
 * method to be called instead of initialize open the screen automatically
 */
	public void initializer() {
		txtPhaseEstimated.setVisible(false);// buttons and texts visibility
		btnShowDetails.setVisible(false);
		sendDateForApproval.setVisible(false);
		datePicker.setVisible(false);
		btnAskForTimeExtention.setVisible(false);
		btnOpenEvaluationReport.setVisible(false);
		txtSituation.setVisible(false);// only
		btnShowExtraInformation.setVisible(false);// show all the date choose options(might be disabled )
		requestNumberColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("requestNumber"));// set the
																												// table
																												// fields
		informationSystemNameColumn
				.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("informationSystemName"));
		mainClient = loginWindowController.mainClient;// put the echoclient as main client
		mainClient.handleMessageFromClientUI("13#" + loginWindowController.user.getuserID());// get who is the user that
		mainClient.handleMessageFromClientUI("100#"	+ loginWindowController.user.getuserID());																		// connected
		UserHomePageController.userOrAssessorOrIt = "Assessor";// set that we are connected now as assessor
	}


    /**
     * method to access the private rnumber 
     * @return
     */
	public String getRNumber() {// return what is the rnumber that we are picked in the table
		return rNumber;
	}

	/**
	 *  send to the inspector the estimated finish date as case 48
	 * @param event
	 * @throws Exception
	 */
	public void sendDateOfEstimatedFinishToInspector(ActionEvent event) throws Exception {
		LocalDate chosenDate = datePicker.getValue();// get the time that written in the datepicker
		if (chosenDate == null && rNumber == null)// check if we didnt picked a date and rnumber
		{// error message that no request has picked from table and no date picked from
			// the datepicker
			txtSituation.setText("Please choose a valid date and a request from the table first");// print to the
																									// assessor
			txtSituation.setVisible(true);
		} else if (rNumber == null) {// error message that no request has picked from table
			txtSituation.setText("Please choose a request from the table first");
			txtSituation.setVisible(true);
		} else if (chosenDate == null || !currentDateChecker(chosenDate.toString()))// check to errors that can happen
																					// in pick date
		{// error message that no date was picked from datepicker
			txtSituation.setText("Please choose a valid date");
			txtSituation.setVisible(true);
		} else {// in this case a date and request was picked
			txtSituation.setText("");// no error message need to be shown to the user(assessor)
			txtSituation.setVisible(false);// return to the default situation of the page
			refreshTable();

			System.out.println("sent message to the server: " + "48#" + chosenDate.toString());// print to the screen
																								// the message
			mainClient.handleMessageFromClientUI("48#" + rNumber + "#" + chosenDate.toString());// send the server to
																								// the inspector
																								// approval
		}
	}

	/**
	 * open the fxml and attach the controller
	 * @param event
	 */
	public void openNewEvaluationReportByAssessor(ActionEvent event) {// opens new evaluation
		try {
			// btnShowDetails.getScene().getWindow().hide(); // loading the new evaluation
			// fxml and controller procedure
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/NewEvaluationReportByAssessor.fxml").openStream());

			NewEvaluationReportByAssessorController newEvaluationReportByAssessorController = loader.getController();// set
																														// the
																														// controller
																														// of
																														// new
																														// evaluation
			mainClient.setNewEvaluationReportByAssessorController(newEvaluationReportByAssessorController);// set the
																											// controller
																											// in the
																											// echoclient

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	
	/**
	 *  After the user clicked Go to work button as Assessor, it opens the Assessor
	 * HomePage and sets the table with all the requests he need to take care of
	 * @param msgFromServer
	 */
	public void getAssessorRequestsDetailsToSet(ArrayList<String> msgFromServer) {// request details of the assessor
																					// charging to the table from the
																					// server

		int i;
		ObservableList<InfoTable> data = FXCollections.observableArrayList();
		System.out.println("return to Assessor Table:" + msgFromServer.toString());

		for (i = 0; i < msgFromServer.size() - 1; i = i + 12) {// charge the requests to the table as requests list
			Request r = new Request(msgFromServer.get(i), msgFromServer.get(i + 1), msgFromServer.get(i + 2),
					msgFromServer.get(i + 3), msgFromServer.get(i + 4), msgFromServer.get(i + 5),
					msgFromServer.get(i + 6), msgFromServer.get(i + 7), msgFromServer.get(i + 8),
					msgFromServer.get(i + 9), msgFromServer.get(i + 10), msgFromServer.get(i + 11));
			requestsVectorAssessor.add(r);
			data.add(new InfoTable(msgFromServer.get(i), msgFromServer.get(i + 2)));
		}

		assessorTable.setItems(data);
		System.out.println("requestsVector of Assessor is: " + requestsVectorAssessor.toString());
		assessorTable.setOnMousePressed(new EventHandler<MouseEvent>() {// set mouse click in the table to make an
																		// action
			@Override
			public void handle(MouseEvent event) {
				if ((assessorTable.getSelectionModel().getSelectedItem()) != null) {
					txtPhaseEstimated.setVisible(true);
					sendDateForApproval.setVisible(true);
					btnShowDetails.setVisible(true);
					datePicker.setVisible(true);
					rNumber = assessorTable.getSelectionModel().getSelectedItem().getRequestNumber();// rnumber is
																										// picked by
																										// click on the
																										// table
					// send to server
					System.out.println("message sent to the server  " + "43#" + rNumber);
					mainClient.handleMessageFromClientUI("43#" + rNumber);// check if the execution duration has by
																			// sending the rnumber to the server
																			// approved
				} else// no rnumber has picked anoucment to the assessor
					mainClient.getloginWindowController().okMsgCaller("Please press on a specific request");
			}
		});
	}

	/**
	 * refresh the assessor table
	 */
	public void refreshTable() {// clear the table
		initializer();
	}


	/**
	 * 	 method that receive the decision of the inspector to the execution duration
	 *that been sent by the assessor,returned from case 43
	 * @param msgFromServer
	 */
	public void executionDurationApprovedOrDeclined(ArrayList<String> msgFromServer) {
		btnAskForTimeExtention.setVisible(false);
													
		ApprovedDate = msgFromServer.get(1);// time received from server(even if not accepted)

		btnShowExtraInformation.setVisible(false);// case 2 selections has been made
		// and without exit in the middle

		if (msgFromServer.get(0).equals("true"))// the time duration approved
		{
			setEstimatedTimeApproved();
			mainClient.handleMessageFromClientUI("46#" + rNumber);// check if we are 3 days before final day in order to
																	// ask for extension	
		}
		mainClient.handleMessageFromClientUI("55#" + rNumber);// check if extra information was added(in order to
		// open the extra info button
		if (msgFromServer.get(0).equals("false")) {// time duration declined,error message has printed and the time
													// choose is shown
			txtSituation.setText("Please estimate again,The estimated duration that was declined:" + ApprovedDate);// help
																													// the
																													// assessor
																													// not
																													// to
			setEstimatedTimeDeclined();// set the fields to be shown
			System.out.println("duration estimated for request number " + rNumber + "has declined by assessor");// print
																												// the
																												// request
																												// number
																												// that
																												// need
																												// to be
		}
		if (msgFromServer.get(0).equals(" "))// case that no decision has been made about assessment estimation time

			if (msgFromServer.get(1).equals(" ")) {// " " is a sign that no desicion has been made about the time
													// estimation
				setEstimatedNotSentYet();
				System.out.println("the assessor didnt yet send an estimation for request " + rNumber);

			} else {// the assessor put time but it doesnt yet approved yet
				setEstimationSentButNotApprovedOrDeclined();
				System.out.println(
						"the assessor estimated the time for request " + rNumber + "waiting for inspector approval");

			}
	}
/**
 * open the extension fxml and attach to the controller
 * @param event
 */
	public void askForTimeExtension(ActionEvent event) {// pressing on the ask for extension open the time extension
														// screen with it fuctionality

		Platform.runLater(() -> {
			try {

				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxmls/TimeExtensionRequest.fxml").openStream());
				TimeExtensionController timeExtensionController = loader.getController();
				timeExtensionController.setUser("Assessor");
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
	}

	
	/**
	 * decide if to open the extension button ,use the info that received in case 46
	 * @param msgFromServer
	 */
	public void getFinalDayOfAssessment(ArrayList<String> msgFromServer) {// if extension has received
		if (msgFromServer.get(0).equals("true") && daysBetweenCalc(msgFromServer.get(1))) {
			btnAskForTimeExtention.setVisible(true);
		} else
			btnAskForTimeExtention.setVisible(false);

	}

	/**
	 * get the message that the message successfully arrived to the server
	 * @param msgFromServer
	 */
	public void getExtensionFromServer(ArrayList<String> msgFromServer) {// if extension has received
		if (msgFromServer.get(0).equals("true")) {
			mainClient.getloginWindowController().okMsgCaller("Extension successfully sent");
			btnAskForTimeExtention.setVisible(false);
		}
	}

	/**
	 * compare betwin the date of today to other date string
	 * @param date2
	 * @return
	 */
	public boolean daysBetweenCalc(String date2) {// func that calc if to open the extension button

		LocalDate date = LocalDate.parse(date2);
		if (ChronoUnit.DAYS.between(LocalDate.now(), date) <= 3)
			return true;
		return false;
	}

	/**
	 * method to return to fieldchoice screen in case back was pressed in the assessor homepage
	 * @param event
	 */
	public void backToFieldChoice(ActionEvent event) {// back to prevoius screen
		try {
			btnBack.getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/FieldChoice.fxml").openStream());

			FieldChoiceController fieldChoiceController = loader.getController();
			mainClient.setFieldChoiceController(fieldChoiceController);

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * answer for if the time duration estimation has arrived to the server
	 * @param msgFromServer
	 */
	public void DecisionAboutTimeDuration(ArrayList<String> msgFromServer) {// the return to if the time duration is
																			// correctly sent
		if (msgFromServer.get(0).equals("true")) {// successfully sent
			System.out.println("time duration of assessment successfully sent");
			mainClient.getloginWindowController().okMsgCaller("time estimation successfully sent to approval");
			refreshTable();// clear the buttons of the request that has chosen
		} else {
			System.out.println("Error while sending time estimation to the server");
			mainClient.getloginWindowController().okMsgCaller("Something went wrong,please estimate again");// error
																											// message
																											// to the
																											// assessor
			refreshTable();// clear the buttons of the request that has chosen
		}

	}

	/**
	 * open the changed request fxml and attach to the controller
	 * @param event
	 */
	public void openChangeRequestForAssessor(ActionEvent event) {
			try {
				// btnShowDetails.getScene().getWindow().hide(); // hiding primary window and
				// open new screen but not close the previous
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxmls/OpenChangeRequest.fxml").openStream());

				OpenChangeRequestController openChangeRequestController = loader.getController();
				mainClient.setOpenChangeRequestController(openChangeRequestController);
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	}

	/**
	 * msg from the server to know if to enable the show extra time button
	 * @param msgFromServer
	 */
	public void isThereExtraInfo(ArrayList<String> msgFromServer) {// this method is the answer if to open extra
																	// information button
		if (msgFromServer.get(0).equals("true"))//if there is an extra information
			btnShowExtraInformation.setVisible(true);
		else
			btnShowExtraInformation.setVisible(false);
	}

	/**
	 * open the show extra info fxml and attach to the controller
	 * @param event
	 */
	public void ShowExtraInformation(ActionEvent event) {// open the extra info screen but not close the prevoius
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader
					.load(getClass().getResource("/fxmls/ExtraInfoForRequestForTheCommittee.fxml").openStream());

			ExtraInfoForRequestForTheCommitteeController extraInfoForRequestForTheCommitteeController = loader	.getController();
			mainClient.setExtraInfoForRequestForTheCommitteeController(extraInfoForRequestForTheCommitteeController);

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
	}

	/**
	 * receivethe msg from the server about the extension succeed or not
	 * @param msgFromServer
	 */
	public void getExtensionApproval(ArrayList<String> msgFromServer) {// answer about request of extension
		if (msgFromServer.get(0).equals("true"))
			mainClient.getloginWindowController().okMsgCaller("Extension request succesfully sent");
		else
			mainClient.getloginWindowController().okMsgCaller("Error while sending extension request");

	}

	/**
	 * check for the datepicker that we didnt choose a date that passed
	 * @param date2
	 * @return
	 */
	public boolean currentDateChecker(String date2) {// check that chosen date(in datepicker is in from today on
		LocalDate date = LocalDate.parse(date2);
		if (ChronoUnit.DAYS.between(LocalDate.now(), date) >= 0)
			return true;
		return false;
	}
	


	/**
	 * getter to the private approveDate 
	 * @return
	 */
	public String getApprovedDate() {// return the approved date of a request
		return ApprovedDate;
	}

	/**
	 * set the fxml fields when when duration estimation has declined 
	 */
	private void setEstimatedTimeDeclined() {//set the fields of the fxml to fit that time declined
		btnShowExtraInformation.setVisible(false); // rechoose
		btnAskForTimeExtention.setVisible(false); // the // declined
		btnOpenEvaluationReport.setVisible(false); // date
		txtSituation.setVisible(true);// ask for another estimation
		datePicker.setVisible(true);// open the functionality of rechoose a date
		sendDateForApproval.setVisible(true);
		txtPhaseEstimated.setVisible(true);
	}
/**
 * set the fxml fields when when duration estimation has approved
 */
	private void setEstimatedTimeApproved() {//set the fields of the fxml to fit that time approved
		btnShowExtraInformation.setVisible(false);// will be checked in case 55 next lines
		txtPhaseEstimated.setVisible(false);// no need to add the date again(this thing is handled in returned case
											// also)
		datePicker.setVisible(false);// no need to add the date again(this thing is handled in returned case also)
		txtSituation.setText("Duration approved by the inspector,current finish of the phase: " + ApprovedDate);// show
																												// to
																												// the
																												// assessor
																												// the
																												// current
		                                                                                                        // finish date
		txtSituation.setVisible(true);// show to the assessor the current dinish date
		System.out.println("assessment time was approved by inspector to the request number" + rNumber);// print to
																										// the print

		btnOpenEvaluationReport.setVisible(true);// now it possible to make an eveluation
		sendDateForApproval.setVisible(false);// no need to add the date again
	}
/**
 * set the fxml fields when when duration estimation has not yet sended
 */
	private void setEstimatedNotSentYet() {//set the fields of the fxml to fit that time estimation wasnt sent yet
		btnShowExtraInformation.setVisible(false);// nothing to be seen except the choose date fields
		btnOpenEvaluationReport.setVisible(false);
		btnAskForTimeExtention.setVisible(false);
		txtSituation.setText("Please choose a date for estimated assessment completion." );
		txtSituation.setVisible(true);// show the message
		txtPhaseEstimated.setVisible(true);
		datePicker.setVisible(true);
		sendDateForApproval.setVisible(true);
	}
/**
 * set the fxml fields when when duration estimation has set but no response has accepted
 */
	private void setEstimationSentButNotApprovedOrDeclined() {//set the fields of the fxml to fit that time sent but not yet approved or declined
		btnShowExtraInformation.setVisible(false);// 3 fields that we dont want to show
		txtPhaseEstimated.setVisible(false);//
		btnOpenEvaluationReport.setVisible(false);/////////////////////////////////////////////////////////////////////////
		txtSituation.setText("Estimated time is: "+ApprovedDate+" waiting for inspector approval." );
		txtSituation.setVisible(true);// show the message
		datePicker.setVisible(false);// hide the date picking fields
		sendDateForApproval.setVisible(false);
		txtPhaseEstimated.setVisible(false);
	}
	
	
	public void getAssessorRequestsDelay(ArrayList<String> msgFromServer) {
		String requests="";
		if(!msgFromServer.get(0).equals("false")) {
			for(int i=0;i<msgFromServer.size();i=i+2) {
				
				if(!currentDateChecker(msgFromServer.get(i+1))&&!msgFromServer.get(i+1).equals(null))
					{
					LocalDate date = LocalDate.parse(msgFromServer.get(i+1));
					long temp = ChronoUnit.DAYS.between(date, LocalDate.now());
					requests= requests+("Request number "+msgFromServer.get(i)+" is in "+String.valueOf(temp)+" days exception and must be done.\n");
					}
			
			}
			textFieldRequestsInDelay.setText(requests);
		}
	}
}

/*
 * 	LocalDate date = LocalDate.parse(date2);
		if (ChronoUnit.DAYS.between(LocalDate.now(), date) >= 0)
		*/
