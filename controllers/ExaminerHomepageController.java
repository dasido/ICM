package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import org.omg.CORBA.INITIALIZE;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExaminerHomepageController implements Initializable{
	
	
	private EchoClient mainClient;
	private String RNumber;
	private String approvedDate;
	
	Vector<Request> requestsVectorExaminer = new Vector<>();

	@FXML 
	private TableView<InfoTable> examinerTable;
	
	@FXML
	private TableColumn<InfoTable, String> requestNumberColumn;
	
	@FXML
	private TableColumn<InfoTable, String> informationSystemNameColumn;
	
	@FXML
	private TableColumn<InfoTable, String> descriptionBeforeChangeColumn;
	
	@FXML
	private Button btnBack;
	
	@FXML
	private Button btnAskForTimeExtention;
	
	@FXML
	private Button refreshBtn;
	
	@FXML
	private Button btnAddFailureReport;
	
	@FXML
	private Button btnApproveTest;
	
	
	@FXML
	private Text textWorkTime;

	
	@FXML
	private Text textExtension;
	
	@FXML
	private TextArea textFieldRequestsInDelay;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializer();
	}
	
	/**
	 * {@link INITIALIZE} all the buttons and other fxmls.
	 */
	public void initializer() {
		requestNumberColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("requestNumber"));
		informationSystemNameColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("informationSystemName"));
		descriptionBeforeChangeColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("descriptionOfExistingSituation"));
		mainClient = loginWindowController.mainClient;
		mainClient.handleMessageFromClientUI("17#" + loginWindowController.user.getuserID());
		btnAskForTimeExtention.setVisible(false);
		btnAddFailureReport.setVisible(false);
		btnApproveTest.setVisible(false);
		textWorkTime.setVisible(false);
		textExtension.setVisible(false);
		mainClient.handleMessageFromClientUI("103#"+loginWindowController.user.getuserID());
	}
	/*
	 *	After the user clicked Go to work button as Examiner, it opens the Examiner HomePage and sets the table with all the requests he need to take care of.
	 */
	public void getExaminerRequestsDetailsToSet(ArrayList<String> msgFromServer)
	{
		int i;
		ObservableList<InfoTable> data = FXCollections.observableArrayList();
		System.out.println("return to Examiner Table:"+msgFromServer.toString());

		for (i=0;i< msgFromServer.size()-1;i=i+12){
			Request r= new Request(msgFromServer.get(i),msgFromServer.get(i+1),msgFromServer.get(i+2),msgFromServer.get(i+3),msgFromServer.get(i+4),msgFromServer.get(i+5),msgFromServer.get(i+6),msgFromServer.get(i+7),msgFromServer.get(i+8),msgFromServer.get(i+9),msgFromServer.get(i+10),msgFromServer.get(i+11));
			requestsVectorExaminer.add(r);
			data.add(new InfoTable(msgFromServer.get(i), msgFromServer.get(i+2), msgFromServer.get(i+3)));
		} 
		
		examinerTable.setItems(data);
		System.out.println("requestsVector of Examiner is: "+requestsVectorExaminer.toString());
		examinerTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				if ((examinerTable.getSelectionModel().getSelectedItem())!=null) {
				RNumber = examinerTable.getSelectionModel().getSelectedItem().getRequestNumber();
				System.out.println(RNumber);
				btnAddFailureReport.setVisible(true);
				btnApproveTest.setVisible(true);
				mainClient.handleMessageFromClientUI("25#" + RNumber);
				}
				else
					mainClient.getloginWindowController().okMsgCaller("Please Choose from the existing requests");
			}
		});
		
	}

	/**
	 * after choosing a request this function checks all the fxmls of this page to open or not.
	 * @param msgFromServer
	 */
	public void CheackingRNumber(ArrayList<String> msgFromServer) {	
			textWorkTime.setVisible(true);
			textWorkTime.setText("You need to finish work on request number "+RNumber+" until "+msgFromServer.get(2));
			
		if(msgFromServer.get(0).equals("0") && daysBetweenCalc(msgFromServer.get(2))) // && if 3 days or less from the 7 days that examiner has to work
		{
			btnAskForTimeExtention.setVisible(true);
			approvedDate=msgFromServer.get(2);
		}
		else if(msgFromServer.get(0).equals("1"))
		{
			textExtension.setVisible(true);
			if(msgFromServer.get(3).equals("0"))
				textExtension.setText("Exstension already ask for "+msgFromServer.get(1)+" and wating for inspetor approval.");
			if(msgFromServer.get(3).equals("1"))
			{
				textExtension.setText("Exstension already ask for "+msgFromServer.get(1)+" and approved by inspector.");
				textWorkTime.setText("You need to finish work on request number "+RNumber+" until "+msgFromServer.get(1));

			}
				if(msgFromServer.get(3).equals("2"))
				textExtension.setText("Exstension already ask for "+msgFromServer.get(1)+" and rejected by inspetor.");
		
		}
	}
	
	/**
	 * refresh the tables page
	 */
	public void refreshTable() {
		initializer();
	}

	/*
	 * back to field choice controller.
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
	 * ask for time extension as a examiner.
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
			timeExtensionController.setUser("Examiner");
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
	 * request number getter.
	 * @return
	 */
	public String getRNumber() {
		return RNumber;
	}

	/**
	 * approved work date getter.
	 * @return
	 */
	public String getApprovedDate() {
		return approvedDate;
	}
	
	/**
	 * checks extension approval from the server
	 * @param msgFromServer
	 */
	public void getExtensionFromServer(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true")) {
			mainClient.getloginWindowController().okMsgCaller("Extension successfully sent");
			btnAskForTimeExtention.setVisible(false);
		}
	}

	/**
	 * opens new failure report window.
	 * @param event
	 */
	public void addNewFailureReport(ActionEvent event) {
		Platform.runLater(
				  () -> {
		try {
			
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/NewTestFailureReportByExaminer.fxml").openStream());
			TestFailureReportController testFailureReportController = loader.getController();	
			testFailureReportController.testForExaminer();
			
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
	 * checks test failure approval from the server
	 * @param msgFromServer
	 */
	public void getTestApproval(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true")) {
			mainClient.getloginWindowController().okMsgCaller("Test failure successfully sent to server");
			initializer();
												}
		else
			mainClient.getloginWindowController().okMsgCaller("Something went wrong, Please Trey again");
	}

/**
 * button approve function.
 * @param event
 */
	public void approveFunc(ActionEvent event) {
		mainClient.handleMessageFromClientUI("28#"+RNumber);
	}

	/**
	 * gets approval for send test approval.
	 * @param msgFromServer
	 */
	public void getTestSentApproval(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true")) {
			mainClient.getloginWindowController().okMsgCaller("Test approval successfully sent");
			initializer();
				}
		else
			mainClient.getloginWindowController().okMsgCaller("Something went wrong with test approval");
	}

	/**
	 * checking if there at least 3 days between days.
	 * @param date2
	 * @return
	 */
	public boolean daysBetweenCalc(String date2) {
		LocalDate date = LocalDate.parse(date2);
	if(ChronoUnit.DAYS.between(LocalDate.now(), date)<=3) 
			return true;
	return false;
	}
	
	public boolean currentDateChecker(String date2) {// check that chosen date(in datepicker is in from today on
		LocalDate date = LocalDate.parse(date2);
		if (ChronoUnit.DAYS.between(LocalDate.now(), date) >= 0)
			return true;
		return false;
	}
	
	
	public void getExaminerRequestsDelay(ArrayList<String> msgFromServer) {
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



