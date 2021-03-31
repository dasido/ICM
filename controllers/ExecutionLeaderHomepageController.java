package controllers;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;
import java.time.temporal.ChronoUnit;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDate;

public class ExecutionLeaderHomepageController implements Initializable{

	private EchoClient mainClient;
	private String RNumber;
	private String TestFailureReport;
	private String approvedDate;
	Vector<Request> requestsVectorExecutionLeader = new Vector<>();
	
	@FXML 
	private TableView<InfoTable> executionLeaderTable;
	
	@FXML
	private TableColumn<InfoTable, String> requestNumberColumn;
	
	@FXML
	private TableColumn<InfoTable, String> informationSystemNameColumn;
	
	@FXML
	private TableColumn<InfoTable, String> descriptionOfExistingSituationColumn;
	
	@FXML
	private Button btnAskForTimeExtention;
	
	@FXML
	private Button btnShowFailureReport;
	
	@FXML
	private Button btnBack;
	
	@FXML
	private Button refreshBtn;
	
	@FXML
	private Button btnView;
	
	@FXML
	private DatePicker date;
	
	@FXML
	private Text dateText;
	
	@FXML
	private Button btnFinish;
	
	@FXML
	private Text finishtext;
	
	@FXML
	private Button btnSendForApproval;
	
	@FXML
	private Text textValidDate;
	
	@FXML
	private Text textEstimation;
	
	@FXML
	private TextArea textFieldRequestsInDelay;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializer();
	}
	
	/**
	 * initialize all the fxmls features.
	 */
	public void initializer() {
		requestNumberColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("requestNumber"));
		informationSystemNameColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("informationSystemName"));
		descriptionOfExistingSituationColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("descriptionOfExistingSituation"));
		mainClient = loginWindowController.mainClient;
		mainClient.handleMessageFromClientUI("16#" + loginWindowController.user.getuserID());
		btnAskForTimeExtention.setVisible(false);
		btnShowFailureReport.setVisible(false);
		btnView.setVisible(false);
		date.setVisible(false);
		dateText.setVisible(false);
		btnSendForApproval.setVisible(false);
		btnFinish.setVisible(false);
		finishtext.setVisible(false);
		textValidDate.setVisible(false);
		textEstimation.setVisible(false);
		mainClient.handleMessageFromClientUI("102#"+loginWindowController.user.getuserID());

	}
	
	/**
	 * After the user clicked Go to work button as Execution Leader, it opens the Execution Leader HomePage and sets the table with all the requests he need to take care of.
	 * @param msgFromServer
	 */
	public void getExecutionLeaderRequestsDetailsToSet(ArrayList<String> msgFromServer)
	{
		int i;
		ObservableList<InfoTable> data = FXCollections.observableArrayList();
		System.out.println("return to Execution Leader Table:"+msgFromServer.toString());

		for (i=0;i< msgFromServer.size()-1;i=i+12){
			Request r= new Request(msgFromServer.get(i),msgFromServer.get(i+1),msgFromServer.get(i+2),msgFromServer.get(i+3),msgFromServer.get(i+4),msgFromServer.get(i+5),msgFromServer.get(i+6),msgFromServer.get(i+7),msgFromServer.get(i+8),msgFromServer.get(i+9),msgFromServer.get(i+10),msgFromServer.get(i+11));
			requestsVectorExecutionLeader.add(r);
			data.add(new InfoTable(msgFromServer.get(i), msgFromServer.get(i+2), msgFromServer.get(i+3)));
		} 

		executionLeaderTable.setItems(data);
		System.out.println("requestsVector of Execution Leader is: "+requestsVectorExecutionLeader.toString());
		
		
		executionLeaderTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				if ((executionLeaderTable.getSelectionModel().getSelectedItem())!=null) {
				RNumber = executionLeaderTable.getSelectionModel().getSelectedItem().getRequestNumber();
				System.out.println(RNumber);
				mainClient.handleMessageFromClientUI("20#" + RNumber);
				btnView.setVisible(true);
				}
				else
					mainClient.getloginWindowController().okMsgCaller("Please Choose from the existing requests");
			}
		});
	}
	
	 /**
	  * send request from the server to get the evaluation report.
	  */
	public void viewEvaluationReport(ActionEvent event) {
		mainClient.handleMessageFromClientUI("21#" + RNumber);
	}
	
	/**
	 * after choosing a request this function checks all the fxmls of this page to open or not.
	 * @param msgFromServer
	 */
	public void CheackingRNumber(ArrayList<String> msgFromServer) {
		System.out.println(LocalDate.now().toString());
		if(msgFromServer.get(0).equals("1"))
		{
			TestFailureReport = msgFromServer.get(1);
			btnShowFailureReport.setVisible(true);
		}
		if(msgFromServer.get(2).equals("1"))
		{	
			if(msgFromServer.get(6).equals("1")) {
			btnFinish.setVisible(true);
			finishtext.setVisible(true);
			dateText.setVisible(true);
			dateText.setText("Execution duration estimation was alredy set for " + msgFromServer.get(3)+" " +"and approved by the inspector");	
			if(msgFromServer.get(4).equals("0") && (daysBetweenCalc(msgFromServer.get(3)))) 
			{ // && if  the date is 3 days and below from estimation
				btnAskForTimeExtention.setVisible(true);
				approvedDate=msgFromServer.get(3);
			}
			if(msgFromServer.get(4).equals("1") && msgFromServer.get(7).equals("0"))
			{
				textEstimation.setVisible(true);
				textEstimation.setText("Time extension already asked to " + msgFromServer.get(5)+" waiting for inspector approval.");
			}
			if(msgFromServer.get(4).equals("1") && msgFromServer.get(7).equals("1"))
			{
				textEstimation.setVisible(true);
				textEstimation.setText("Time extension already asked to " + msgFromServer.get(5)+" and approved by the inspector.");
			}
			if(msgFromServer.get(4).equals("1") && msgFromServer.get(7).equals("2"))
			{
				textEstimation.setVisible(true);
				textEstimation.setText("Time extension already asked to " + msgFromServer.get(5)+" and rejected by the inspector.");
			}
			}
			
			if(msgFromServer.get(6).equals("0")) {
				dateText.setVisible(true);
				dateText.setText("Execution duration estimation was alredy set for " + msgFromServer.get(3)+" and waiting fot inspector approval");
			}
			if(msgFromServer.get(6).equals("2"))
			{
				finishtext.setVisible(true);
				finishtext.setText("The inspector declined your estimation at "+ msgFromServer.get(3)+" Please estimate again.");
				date.setVisible(true);
				dateText.setVisible(true);
				dateText.setText("Execution duration estimation:");
				btnSendForApproval.setVisible(true);
			}
		}
		else {
			date.setVisible(true);
			dateText.setVisible(true);
			btnSendForApproval.setVisible(true);
			 }
	}
	
	/**
	 * back to field choise controller.
	 * @param event
	 */
	public void backToFieldChoise(ActionEvent event)
	{
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
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
	}
	
	/**
	 * send to server finish work on the request.
	 */
	public void finishBtnFunc(ActionEvent event) 
	{
		mainClient.handleMessageFromClientUI("22#" + RNumber);
	}
	
	/**
	 * get approved from the server that the request now handle to the next treat. 
	 * @param msgFromServer
	 */
	public void afterFisnishbtn(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true")) {
		mainClient.getloginWindowController().okMsgCaller("Finish successfully");
		initializer();
		}
	}
	
	/**
	 * opens the evaluation report window.
	 * @param msgFromServer
	 */
	public void getEvaluationReportFromServer(ArrayList<String> msgFromServer){
		
		
		Platform.runLater(
				  () -> {
		try {
			
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/EvaluationReport.fxml").openStream());
			EvaluationReportController evaluationReportController = loader.getController();	
			evaluationReportController.setUpFieldsForExecutionLeader(msgFromServer.get(0), msgFromServer.get(1), msgFromServer.get(2), msgFromServer.get(3), msgFromServer.get(4));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
	  });
	}
	
	/*
	 * send finish work duration estimation about the  to server.
	 */
	public void sendForApproval(ActionEvent event) {
		if(date.getValue()==null)
			textValidDate.setVisible(true);
		else
			if(!currentDateChecker(date.getValue().toString()))
			{
			textValidDate.setVisible(true);
			textValidDate.setText("Please select a date from today and on.");
			}
		else
		mainClient.handleMessageFromClientUI("23#"+RNumber +"#"+date.getValue().toString());
	}
	
	/**
	 * gets approved about finishing working on a request
	 */
	public void approvalSent(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true"))
			mainClient.getloginWindowController().okMsgCaller("The request successfully sent to inspector.");
		else
			mainClient.getloginWindowController().okMsgCaller("Something went wrong, Please try again.");
	}
	
	/**
	 * refreshes the execution leader table.
	 */
	public void refreshTable() {
		initializer();
	}

	/**
	 * opens and view failure report window.
	 * @param event
	 */
	public void viewFailureReport(ActionEvent event) {
		Platform.runLater(
				  () -> {
		try {
			
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/NewTestFailureReportByExaminer.fxml").openStream());
			TestFailureReportController testFailureReportController = loader.getController();	
			testFailureReportController.testForExecutionLeader(TestFailureReport);
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
	  });
	}

	/**
	 * opens the time extension window.
	 * @param event
	 */
	public void askForTimeExtension(ActionEvent event) {
		
	Platform.runLater(
			  () -> {
	try {
		
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxmls/TimeExtensionRequest.fxml").openStream());
		TimeExtensionController timeExtensionController = loader.getController();	
		timeExtensionController.setUser("Execution");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		}
		catch (Exception e2) {
			e2.printStackTrace();
					}
			  });
	}

	/**
	 * gets approval from the server about the time extension
	 * @param msgFromServer
	 */
	public void getExtensionFromServer(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true")) {
			mainClient.getloginWindowController().okMsgCaller("Extension successfully sent");
			btnAskForTimeExtention.setVisible(false);
		}
	}

	/**
	 * request number getter
	 * @return
	 */
	public String getRNumber() {
		return RNumber;
	}

	/**
	 * approved working date getter.
	 * @return
	 */
	public String getApprovedDate() {
		return approvedDate;
	}
	
	/*
	 * checks 3 days between 2 days
	 */
	public boolean daysBetweenCalc(String date2) {
		LocalDate date = LocalDate.parse(date2);
	if(ChronoUnit.DAYS.between(LocalDate.now(), date)<=3) 
			return true;
	return false;
	}
	
	/*
	 * checks if the date is from today and on.
	 */
	public boolean currentDateChecker(String date2) {
		LocalDate date = LocalDate.parse(date2);
		if(ChronoUnit.DAYS.between(LocalDate.now(), date)>=0) 
			return true;
	return false;	
	}
	
	public void getExecutionLeaderRequestsDelay(ArrayList<String> msgFromServer) {
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
