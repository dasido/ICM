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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MemberOfCommitteeOrChairmanHomepageController implements Initializable{

	private EchoClient mainClient;
	Vector<Request> requestsVectorChairman = new Vector<>();
	Vector<Request> requestsVectorMemberOfCommittee = new Vector<>();
	Vector<String> examinersVector = new Vector<>();
	private String RNumber;
	private String approvedDate;
	
	@FXML
	private TabPane tpMemberOfCommittee;
	
	@FXML
	private TableView<InfoTable> chairmanTable;
	
	@FXML
	private TableView<InfoTable> memberOfCommitteeTable;
	
	@FXML
	private TableColumn<InfoTable, String> requestNumberColumn;
	
	@FXML
	private TableColumn<InfoTable, String> informationSystemNameColumn;
	
	@FXML
	private TableColumn<InfoTable, String> requestNumberColumn2;
	
	@FXML
	private TableColumn<InfoTable, String> informationSystemNameColumn2;
	
	@FXML
	private Tab tabMemberOfCommittee;
	
	@FXML
	private Tab tabChairman;
	
	@FXML
	private Button btnReview1;
	
	@FXML
	private Button btnReview2;
	
	@FXML
	private Button btnBack1;
	
	@FXML
	private Button btnBack2;
	
	@FXML
	private Button btnAskForTimeExtension;
	
	@FXML
	private Button btnDecline;
	
	@FXML
	private Text textDecision;
	
	@FXML
	private Button btnSave;
	
	@FXML
	private Text textAdhoc;
	
	@FXML
	private Button btnAskForMoreInfo;
	
	@FXML
	private Text textWorkTime;
	
	@FXML
	private Text textWorkTime2;
	
	@FXML
	private Text textExtension;
	
	@FXML
	private Button btnRefresh1;
	
	@FXML
	private Button btnRefresh2;
	
	@FXML
	private TextArea textFieldRequestsInDelay1;
	
	@FXML
	private TextArea textFieldRequestsInDelay2;
	
	@FXML
	private ComboBox<String> comboBoxMembers;
	ObservableList<String> list;
	ArrayList<String> ExaminerList = new ArrayList<>(); 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializer();	
	}
	/*
	* Initialize as a function that we can easy call.
	* Sets all the fxmls.
	*/
	public void initializer() {
		requestNumberColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("requestNumber"));
		informationSystemNameColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("informationSystemName"));
		requestNumberColumn2.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("requestNumber"));
		informationSystemNameColumn2.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("informationSystemName"));
		mainClient = loginWindowController.mainClient;
		if(FieldChoiceController.userRoleChoice == "Chairman")
		{
			tpMemberOfCommittee.getSelectionModel().select(tabChairman);
			mainClient.handleMessageFromClientUI("14#" + loginWindowController.user.getuserID());
			tabMemberOfCommittee.setDisable(true);
			btnAskForTimeExtension.setVisible(false);
			btnDecline.setVisible(false);
			btnReview1.setVisible(false);
			textDecision.setVisible(false);
			comboBoxMembers.setVisible(false);
			textAdhoc.setVisible(false);
			btnSave.setVisible(false);
			btnAskForMoreInfo.setVisible(false);
			textWorkTime.setVisible(false);
			textExtension.setVisible(false);
			mainClient.handleMessageFromClientUI("101#"+loginWindowController.user.getuserID());
		}
		
		else 
		{
			tpMemberOfCommittee.getSelectionModel().select(tabMemberOfCommittee);
			mainClient.handleMessageFromClientUI("15#" + loginWindowController.user.getuserID());
			tabChairman.setDisable(true);
			btnReview2.setVisible(false);
			textWorkTime2.setVisible(false);
			mainClient.handleMessageFromClientUI("104#"+loginWindowController.user.getuserID());
		}
	}
	/*
	*After the user clicked Go to work button as Chairman, it opens the Chairman_MemberOfCommittee HomePage and sets the table at the Chairman tab with all the requests he need to take care of
	*/
	public void getChairmanRequestsDetailsToSet(ArrayList<String> msgFromServer)
	{
		int i;
		ObservableList<InfoTable> data = FXCollections.observableArrayList();
		System.out.println("return to Chairman Table:"+msgFromServer.toString());

		for (i=0;i< msgFromServer.size()-1;i=i+12){
			Request r= new Request(msgFromServer.get(i),msgFromServer.get(i+1),msgFromServer.get(i+2),msgFromServer.get(i+3),msgFromServer.get(i+4),msgFromServer.get(i+5),msgFromServer.get(i+6),msgFromServer.get(i+7),msgFromServer.get(i+8),msgFromServer.get(i+9),msgFromServer.get(i+10),msgFromServer.get(i+11));
			requestsVectorChairman.add(r);
			data.add(new InfoTable(msgFromServer.get(i), msgFromServer.get(i+2)));
		} 

		chairmanTable.setItems(data);
		System.out.println("requestsVector of Chairman is: "+requestsVectorChairman.toString());
		chairmanTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				if ((chairmanTable.getSelectionModel().getSelectedItem())!=null) {
				RNumber = chairmanTable.getSelectionModel().getSelectedItem().getRequestNumber();
				System.out.println(RNumber);
				btnReview1.setVisible(true);
				}
				else
					mainClient.getloginWindowController().okMsgCaller("Please Choose from the existing requests");
			}
		});
		
	}
	/*
	*After the user clicked Go to work button as Member Of Committee, it opens the Chairman_MemberOfCommittee HomePage and sets the table at the Member Of Committee tab with all the requests he need to take care of
	*/
	public void getMemberOfCommitteeRequestsDetailsToSet(ArrayList<String> msgFromServer)
	{
		int i;
		ObservableList<InfoTable> data = FXCollections.observableArrayList();
		System.out.println("return to Member Of Committee Table:"+msgFromServer.toString());

		for (i=0;i< msgFromServer.size()-1;i=i+12){
			Request r= new Request(msgFromServer.get(i),msgFromServer.get(i+1),msgFromServer.get(i+2),msgFromServer.get(i+3),msgFromServer.get(i+4),msgFromServer.get(i+5),msgFromServer.get(i+6),msgFromServer.get(i+7),msgFromServer.get(i+8),msgFromServer.get(i+9),msgFromServer.get(i+10),msgFromServer.get(i+11));
			requestsVectorMemberOfCommittee.add(r);
			data.add(new InfoTable(msgFromServer.get(i), msgFromServer.get(i+2)));
		} 
		
		memberOfCommitteeTable.setItems(data);
		System.out.println("requestsVector of Member Of Committee is: "+requestsVectorMemberOfCommittee.toString());
		memberOfCommitteeTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				if ((memberOfCommitteeTable.getSelectionModel().getSelectedItem())!=null) {
				RNumber = memberOfCommitteeTable.getSelectionModel().getSelectedItem().getRequestNumber();
				System.out.println(RNumber);
				btnReview2.setVisible(true);
				mainClient.handleMessageFromClientUI("45#"+RNumber);
				}
				else
					mainClient.getloginWindowController().okMsgCaller("Please Choose from the existing requests");
			}
		});
	
	}
	/*
	*Click back to return to field choice screen.
	*/
	public void backToFieldChoise(ActionEvent event)
	{
		try {
			btnBack1.getScene().getWindow().hide(); // hiding primary window
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
	/*
	*Click on button view evaluation and open a new evaluation window.
	*/
	public void viewEvaluationReport(ActionEvent event) {
		mainClient.handleMessageFromClientUI("34#" + RNumber);
		if(FieldChoiceController.userRoleChoice == "Chairman") {
			mainClient.handleMessageFromClientUI("32#" + RNumber);
			getExaminerComboBox();
			btnAskForMoreInfo.setVisible(true);
			btnDecline.setVisible(true);
		}
	}
	/*
	 * Checks from the server which buttons to set visible.
	 */
	public void getChairmanButtons(ArrayList<String> msgFromServer) {
		textWorkTime.setVisible(true);
		textWorkTime.setText("You need to finish work on request number "+RNumber+" until "+msgFromServer.get(2));
		if(msgFromServer.get(0).equals("0") && daysBetweenCalc(msgFromServer.get(2)))
		{
			btnAskForTimeExtension.setVisible(true);
			approvedDate=msgFromServer.get(2);
		}
		else if(msgFromServer.get(0).equals("1"))
		{
			textExtension.setVisible(true);
			if(msgFromServer.get(3).equals("0"))
				textExtension.setText("Exstension already ask for "+msgFromServer.get(1)+" and wating for inspetor approval.");
			if(msgFromServer.get(3).equals("1"))
			{
				textWorkTime.setText("You need to finish work on request number "+RNumber+" until "+msgFromServer.get(1));
				textExtension.setText("Exstension already ask for "+msgFromServer.get(1)+" and approved by inspector.");
			}
			if(msgFromServer.get(3).equals("2"))
				textExtension.setText("Exstension already ask for "+msgFromServer.get(1)+" and rejected by inspetor.");
		}
	}
	
	/*
	 * Gets from the server the evaluation details.
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
	 * Function that gets a date and checks if there is a 3 days or less between now and the date.
	 */
	public boolean daysBetweenCalc(String date2) {
		LocalDate date = LocalDate.parse(date2);
	if(ChronoUnit.DAYS.between(LocalDate.now(), date)<=3) 
			return true;
	return false;
		}

	//Send to server request of getting examiner names.
	public void getExaminerComboBox() {
		mainClient.handleMessageFromClientUI("36#"+RNumber);
		btnSave.setVisible(true);
		comboBoxMembers.setVisible(true);
		textAdhoc.setVisible(true);
	}

	/*
	 * Ask for time extension - opens a new time extension window.
	 */
	public void askForTimeExtension(ActionEvent event) {
		
		Platform.runLater(
				  () -> {
		try {
			
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/TimeExtensionRequest.fxml").openStream());
			TimeExtensionController timeExtensionController = loader.getController();	
			timeExtensionController.setUser("Chairman");
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
	 * Gets approve from the server that time extension asking did work.
	 */
	public void getExtensionFromServer(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true")) {
			mainClient.getloginWindowController().okMsgCaller("Extension successfully sent");
			btnAskForTimeExtension.setVisible(false);
		}
	}

	/*
	 * Gets the names of the examiner and put them into combo box.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initializableExaminerComboBox(ArrayList<String> msgFromServer){

		
		if (msgFromServer.get(0).equals("false")) {
			mainClient.getloginWindowController().okMsgCaller("there is no active examiner in the system");
		}
		else
		{
			int i;
			ArrayList<String> ExaminerList1 = new ArrayList<>(); 

			System.out.println("return to CBX with:"+msgFromServer.toString());

			for (i=1;i< msgFromServer.size();i+=2)
				examinersVector.add(msgFromServer.get(i));
			for (i=0;i< msgFromServer.size();i+=2){
				ExaminerList1.add(msgFromServer.get(i));
				list = FXCollections.observableArrayList(ExaminerList1);

			} 
			ExaminerList=ExaminerList1;
			System.out.println("Examinervector in CBX:"+examinersVector.toString());	
			comboBoxMembers.setItems((ObservableList) list);
		}
	
	}

	/*
	 * Button to gets more info about the evaluation report.
	 */
	public void askForMoreInformation(ActionEvent event) {
		
		Platform.runLater(
				  () -> {
		try {
			
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/ExtraInfoForRequest.fxml").openStream());
			ExtraInfoController extraInfoController = loader.getController();
			mainClient.setExtraInfoController(extraInfoController);
			extraInfoController.setForChairman();
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
	 * After click on decline the request, the Chairman sends to server request to decline the change request.
	 */
	public void declineRequest(ActionEvent event) {
		mainClient.handleMessageFromClientUI("42#"+RNumber);
	}
	
	/*
	 * Gets approval from the server about decline the request.
	 */
	public void getDeclineApproval(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true"))
		{
			mainClient.getloginWindowController().okMsgCaller("Successfully declined request number "+RNumber+".");
			initializer();
		}
		else
			mainClient.getloginWindowController().okMsgCaller("request number "+RNumber+ "Didn't decline, Please Try again.");

	}
	
	/*
	 * Choose 1 name from the combo box and sends his ID to the server.
	 */
	public void adHocNominateExaminer(ActionEvent event) { 
		if(comboBoxMembers.getValue()!=null)
		{
		int i = ExaminerList.indexOf(comboBoxMembers.getValue().toString());
		System.out.println(ExaminerList.toString());
		System.out.println(comboBoxMembers.getValue().toString());
		mainClient.handleMessageFromClientUI("44#"+RNumber+"#"+examinersVector.elementAt(i));
		}
		else mainClient.getloginWindowController().okMsgCaller("Please Choose examiner first, Then save and finish.");
			
	}
	
	/*
	 * Gets approval from the server about the examiner Nominate sent ID.
	 */
	public void getNominateApproval(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true"))
		{
			mainClient.getloginWindowController().okMsgCaller("Ad-hoc nomination successfully completed");
			initializer();
		}
		else 			mainClient.getloginWindowController().okMsgCaller("Ad-hoc nomination didn't approved, please try again.");

	}
	
	/*
	 * Getter of the chosen RNumber.
	 */
	public String getRNumber() {
		return RNumber;
	}

	/*
	 * Getter of the approved working date.
	 */
	public  String getApprovedDate() {
		return approvedDate;
	}

	/*
	 * Gets the 7 days after the request take place in the decision phase.
	 */
	public void getWorkDate(ArrayList<String> msgFromServer)
	{
		textWorkTime2.setVisible(true);
		textWorkTime2.setText("You need to finish work on request number "+RNumber+" until "+msgFromServer.get(0));	
	}

	/*
	 * Refresh request table.
	 */
	public	void refreshTable() {
		initializer();
	}
	
	public void getChairManRequestsDelay(ArrayList<String> msgFromServer) {
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
			textFieldRequestsInDelay1.setText(requests);
		}
	}
	
	
	public void getCommitteeRequestsDelay(ArrayList<String> msgFromServer) {
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
			textFieldRequestsInDelay2.setText(requests);
		}
	}
	
	public boolean currentDateChecker(String date2) {// check that chosen date(in datepicker is in from today on
		LocalDate date = LocalDate.parse(date2);
		if (ChronoUnit.DAYS.between(LocalDate.now(), date) >= 0)
			return true;
		return false;
	}
}

