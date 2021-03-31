package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.EchoClient;
import entities.InfoTable;
import entities.InspectorWaitingForApprovalTables;
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

public class InspectorHomepageController implements Initializable{

	private EchoClient mainClient;
	private ObservableList<String> setInformationSystemsList;
	private ObservableList<String> setInformationSystemsList2;
	@SuppressWarnings("unused")
	private ObservableList<String> setRolesList;
	private ObservableList<String> setRolesList2;
	private ObservableList<String> setEngineersList;
	private ObservableList<String> activeRequestList;
	private ObservableList<String> requestList;
	private ObservableList<String> engineersList;
	private ObservableList<String> engineersList2;
	private int nominationManagementTab = 0;
	private int requestManagementTab = 0;
	private int waitingForApprovalTab = 0;
	private String requestNumber;
	private String requestNumberSelectedFromTable = null;
	private String userIDSelectedFromTable = null;
	private String roleSelectedFromTable = null;
	public static String rNumber = null;
	
	@FXML
	private Pane okPain;
	
	@FXML 
	private TableView<InfoTable> inspectorTable;
	
	@FXML 
	private TableView<InspectorWaitingForApprovalTables> evaluationPhaseDurationTable;
	
	@FXML 
	private TableView<InspectorWaitingForApprovalTables> phaseExtensionTimeTable;
	
	@FXML 
	private TableView<InspectorWaitingForApprovalTables> closeARequestTable;
	
	@FXML 
	private TableView<InspectorWaitingForApprovalTables> assessorNominationTable;
	
	@FXML
	private TableColumn<InfoTable, String> requestNumberColumn;
	
	@FXML
	private TableColumn<InfoTable, String> userIDColumn;
	
	@FXML
	private TableColumn<InfoTable, String> fullNameColumn;
	
	@FXML
	private TableColumn<InfoTable, String> userDepartmentColumn;
	
	@FXML
	private TableColumn<InfoTable, String> roleColumn;
	
	@FXML 
	private TableView<InfoTable> inspectorTable2;
	
	@FXML
	private TableColumn<InfoTable, String> requestNumberColumn2;
	
	@FXML
	private TableColumn<InfoTable, String> applicantIDColumn;
	
	@FXML
	private TableColumn<InfoTable, String> informationSystemNameColumn;
	
	@FXML
	private TableColumn<InfoTable, String> currentPhaseColumn;
	
	@FXML
	private TableColumn<InspectorWaitingForApprovalTables, String> requestNumberColumn3;
	
	@FXML
	private TableColumn<InspectorWaitingForApprovalTables, String> phaseNameColumn;
	
	@FXML
	private TableColumn<InspectorWaitingForApprovalTables, String> durationTimeColumn;
	
	@FXML
	private TableColumn<InspectorWaitingForApprovalTables, String> requestNumberColumn4;
	
	@FXML
	private TableColumn<InspectorWaitingForApprovalTables, String> reasonColumn;
	
	@FXML
	private TableColumn<InspectorWaitingForApprovalTables, String> requestNumberColumn5;
	
	@FXML
	private TableColumn<InspectorWaitingForApprovalTables, String> userIDColumn2;
	
	@FXML
	private TableColumn<InspectorWaitingForApprovalTables, String> fullNameColumn2;
	
	@FXML
	private TableColumn<InspectorWaitingForApprovalTables, String> userDepartmentColumn2;
	
	@FXML
	private TableColumn<InspectorWaitingForApprovalTables, String> requestNumberColumn6;
	
	@FXML
	private TableColumn<InspectorWaitingForApprovalTables, String> phaseNameColumn2;
	
	@FXML
	private TableColumn<InspectorWaitingForApprovalTables, String> extensionTimeColumn;
	
	@FXML
	private TabPane tpInspector;
	
	@FXML
	private Tab tabNominationManagement;
	
	@FXML
	private Tab tabRequestManagement;
	
	@FXML
	private Tab tabWaitingForApproval;
	
	@FXML
	private ComboBox<String> informationSystemCBX;
	
	@FXML
	private ComboBox<String> informationSystemCBX2;
	
	@FXML
	private ComboBox<String> chooseRoleCBX;
	
	@FXML
	private ComboBox<String> engineersCBX;
	
	@FXML
	private ComboBox<String> activeRequestCBX;
	
	@FXML
	private ComboBox<String> chooseRequestCBX;
	
	@FXML
	private ComboBox<String> chooseEngineerCBX;
	
	@FXML
	private ComboBox<String> chooseEngineerCBX2;
	
	@FXML
	private Text txtInformationSystem;
	
	@FXML
	private Text txtInformationSystem2;
	
	@FXML
	private Text txtRequestNumber;
	
	@FXML
	private Text txtRole;
	
	@FXML
	private Text txtRole2;
	
	@FXML
	private Text txtEngineer;
	
	@FXML
	private Text txtEngineer2;
	
	@FXML
	private Text txtSuccessMsg;
	
	@FXML
	private Text txtDisplayActiveRequests;
	
	@FXML
	private Text txtDisplayClosedRequests;
	
	@FXML
	private Text txtFreezeProcess;
	
	@FXML
	private Text txtGotFrozen;
	
	@FXML
	private Text txtChooseRequest;
	
	@FXML
	private Text txtDurationTime;
	
	@FXML
	private Text txtCloseARequest;
	
	@FXML
	private Text txtExtensionTime;
	
	@FXML
	private Text txtAssessorNomination;
	
	@FXML
	private Text txtEngineer3;
	
	@FXML
	private Button btnNominate;
	
	@FXML
	private Button btnChange;
	
	@FXML
	private TextArea textFieldRequestsInDelay;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(
				()->{
		requestNumberColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("requestNumber"));
		userIDColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("userID"));
		fullNameColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("fullName"));
		userDepartmentColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("userDepartment"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("role"));
		tpInspector.getSelectionModel().select(tabNominationManagement);
		nominationManagementTab = 1;
		mainClient = loginWindowController.mainClient;
		tpInspector.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) 
			{
				//When Request Management tab clicked
				if(tabRequestManagement.isSelected() && requestManagementTab != 1)
				{
					tpInspector.getSelectionModel().select(tabRequestManagement);
					requestManagementTab = 1;
					nominationManagementTab = 0;
					waitingForApprovalTab = 0;
					informationSystemCBX2.setItems(null);
					requestNumberColumn2.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("requestNumber"));
					applicantIDColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("applicantID"));
					informationSystemNameColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("informationSystemName"));
					currentPhaseColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("currentPhase"));
					mainClient.handleMessageFromClientUI("18#");
					mainClient.handleMessageFromClientUI("106#");
				}
				
				//When Waiting for approval tab clicked
				else if(tabWaitingForApproval.isSelected() && waitingForApprovalTab != 1)
				{
					tpInspector.getSelectionModel().select(tabWaitingForApproval);
					waitingForApprovalTab = 1;
					nominationManagementTab = 0;
					requestManagementTab = 0;
					txtCloseARequest.setText("");
					txtDurationTime.setText("");
					txtExtensionTime.setText("");
					txtAssessorNomination.setText("");
					txtEngineer3.setText("");
					engineersCBX.setItems(null);
					requestNumberColumn3.setCellValueFactory(new PropertyValueFactory<InspectorWaitingForApprovalTables, String>("requestNumber"));
					phaseNameColumn.setCellValueFactory(new PropertyValueFactory<InspectorWaitingForApprovalTables, String>("phaseName"));
					durationTimeColumn.setCellValueFactory(new PropertyValueFactory<InspectorWaitingForApprovalTables, String>("date"));
					requestNumberColumn4.setCellValueFactory(new PropertyValueFactory<InspectorWaitingForApprovalTables, String>("requestNumber"));
					reasonColumn.setCellValueFactory(new PropertyValueFactory<InspectorWaitingForApprovalTables, String>("reason"));
					requestNumberColumn5.setCellValueFactory(new PropertyValueFactory<InspectorWaitingForApprovalTables, String>("requestNumber"));
					userIDColumn2.setCellValueFactory(new PropertyValueFactory<InspectorWaitingForApprovalTables, String>("userID"));
					fullNameColumn2.setCellValueFactory(new PropertyValueFactory<InspectorWaitingForApprovalTables, String>("fullName"));
					userDepartmentColumn2.setCellValueFactory(new PropertyValueFactory<InspectorWaitingForApprovalTables, String>("userDepartment"));
					requestNumberColumn6.setCellValueFactory(new PropertyValueFactory<InspectorWaitingForApprovalTables, String>("requestNumber"));
					phaseNameColumn2.setCellValueFactory(new PropertyValueFactory<InspectorWaitingForApprovalTables, String>("phaseName"));
					extensionTimeColumn.setCellValueFactory(new PropertyValueFactory<InspectorWaitingForApprovalTables, String>("date"));
					mainClient.handleMessageFromClientUI("68#");
					mainClient.handleMessageFromClientUI("69#");
					mainClient.handleMessageFromClientUI("70#");
					mainClient.handleMessageFromClientUI("72#");
				}
				
				//When Nomination Management tab clicked
				else if(tabNominationManagement.isSelected() && nominationManagementTab != 1)
				{
					tpInspector.getSelectionModel().select(tabNominationManagement);
					nominationManagementTab = 1;
					requestManagementTab = 0;
					waitingForApprovalTab = 0;
					txtRequestNumber.setText("");
					txtRole.setText("");
					txtRole2.setText("");
					inspectorTable.setItems(null);
					informationSystemCBX.setItems(null);
					activeRequestCBX.setItems(null);
					chooseRoleCBX.setItems(null);
					chooseEngineerCBX.setItems(null);
					chooseEngineerCBX2.setItems(null);
					activeRequestCBX.setVisible(false);
					chooseRoleCBX.setVisible(false);
					chooseEngineerCBX.setVisible(false);
					chooseEngineerCBX2.setVisible(false);
					btnNominate.setVisible(false);
					btnChange.setVisible(false);
					txtInformationSystem.setText("");
					txtEngineer.setText("");
					txtEngineer2.setText("");
					mainClient.handleMessageFromClientUI("12#");
				}
				
				//When non of the tabs selected
				else System.out.println("No tab selected");
			}
		});
		
		activeRequestCBX.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) 
			{
				if(informationSystemCBX.getValue() == null)
					txtInformationSystem.setText("Please choose information system!");
			}
		});
		
		chooseRoleCBX.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) 
			{
				if(activeRequestCBX.getValue() == null)
					txtRequestNumber.setText("Please choose a request number!");
			}
		});
		
		chooseEngineerCBX.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) 
			{
				if(chooseRoleCBX.getValue() == null)
					txtRole.setText("Please choose a role!");
			}
		});
		
		chooseEngineerCBX2.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) 
			{
				if(inspectorTable.getSelectionModel().getSelectedItem() == null)
					txtRole2.setText("Please choose an engineer with a role from table first!");
			}
		});
		
		//When the inspector table clicked and the user chose one of the rows in the table
		inspectorTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				if((inspectorTable.getSelectionModel().getSelectedItem())!=null) {
					txtRole2.setText("");
					roleSelectedFromTable = inspectorTable.getSelectionModel().getSelectedItem().getRole();
					if(activeRequestCBX.getValue() == null)
						txtRequestNumber.setText("Please choose a request number!");
					else
						mainClient.handleMessageFromClientUI("49#" + activeRequestCBX.getValue().toString() + "#" + roleSelectedFromTable);
						
					System.out.println(roleSelectedFromTable);
					
				}
			}
		});
		
		inspectorTable2.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				if((inspectorTable2.getSelectionModel().getSelectedItem())!=null) {
					requestNumberSelectedFromTable = inspectorTable2.getSelectionModel().getSelectedItem().getRequestNumber();
					System.out.println(requestNumberSelectedFromTable);
				}
			}
		});
		
		chooseRequestCBX.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) 
			{
				if(informationSystemCBX2.getValue() == null)
					txtInformationSystem2.setText("Please choose information system!");
			}
		});
		
		assessorNominationTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				txtCloseARequest.setText("");
				txtDurationTime.setText("");
				txtExtensionTime.setText("");
				txtAssessorNomination.setText("");
				txtEngineer3.setText("");
				if((assessorNominationTable.getSelectionModel().getSelectedItem())!=null) {
					mainClient.handleMessageFromClientUI("19#" + assessorNominationTable.getSelectionModel().getSelectedItem().getUserID());
				}
				else
					engineersCBX.setItems(null);
			}
		});
		
		engineersCBX.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) 
			{
				txtEngineer3.setText("");
				txtCloseARequest.setText("");
				txtDurationTime.setText("");
				txtExtensionTime.setText("");
				txtAssessorNomination.setText("");
				if((assessorNominationTable.getSelectionModel().getSelectedItem())==null)
					txtAssessorNomination.setText("Please choose request number for assessor approval or change from table first!");
			}
		});
		
		phaseExtensionTimeTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				txtCloseARequest.setText("");
				txtDurationTime.setText("");
				txtExtensionTime.setText("");
				txtAssessorNomination.setText("");
				txtEngineer3.setText("");
				engineersCBX.setItems(null);
			}
		});
		
		evaluationPhaseDurationTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				txtCloseARequest.setText("");
				txtDurationTime.setText("");
				txtExtensionTime.setText("");
				txtAssessorNomination.setText("");
				txtEngineer3.setText("");
				engineersCBX.setItems(null);
			}
		});
		
		closeARequestTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				txtCloseARequest.setText("");
				txtDurationTime.setText("");
				txtExtensionTime.setText("");
				txtAssessorNomination.setText("");
				txtEngineer3.setText("");
				engineersCBX.setItems(null);
			}
		});
		
		okPain.setVisible(false);
		activeRequestCBX.setVisible(false);
		chooseRoleCBX.setVisible(false);
		chooseEngineerCBX.setVisible(false);
		chooseEngineerCBX2.setVisible(false);
		btnNominate.setVisible(false);
		btnChange.setVisible(false);
		mainClient.handleMessageFromClientUI("12#");
				});
	}

	//Sets "Choose information system" ComboBox with all the names of the information systems in the system at the "Nomination Management" tab
	public void setInformationSystemsNamesToNominationManagementCBX(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test12");
			txtInformationSystem.setText("There are no information systems!");
		}
		else
		{
			int i;
			ArrayList<String> informationSystemsList = new ArrayList<>(); 

			System.out.println("return to Choose Information System CBX at Nomination Management Tab:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size();i++){
				informationSystemsList.add(msgFromServer.get(i));
				setInformationSystemsList = FXCollections.observableArrayList(informationSystemsList);

			} 
			System.out.println("Information Systems List in CBX at Nomination Management Tab:"+informationSystemsList.toString());	
			informationSystemCBX.setItems((ObservableList<String>) setInformationSystemsList);
		}
	}
	
	//Sets "Choose information system" ComboBox with all the names of the information systems in the system at the "Request Management" tab
	public void setInformationSystemsNamesToRequestManagementCBX(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test18");
		}
		else
		{
			int i;
			ArrayList<String> informationSystemsList = new ArrayList<>(); 

			System.out.println("return to Choose Information System CBX at Request Management Tab:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size();i++){
				informationSystemsList.add(msgFromServer.get(i));
				setInformationSystemsList2 = FXCollections.observableArrayList(informationSystemsList);
			} 
			System.out.println("Information Systems List in CBX at Request Management Tab:"+informationSystemsList.toString());	
			informationSystemCBX2.setItems((ObservableList<String>) setInformationSystemsList2);
		}
	}
	
	//Sets "Choose another engineer" ComboBox with all the engineers in the system that are not the IT Department Manager, at the "Waiting for approval" tab
	public void setEngineersToWaitingForApprovalCBX(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test19");
		}
		else
		{
			int i;
			ArrayList<String> engineersList = new ArrayList<>(); 

			System.out.println("return to Choose another engineer CBX at Waiting for approval Tab:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size()-1;i=i+3){
				if((assessorNominationTable.getSelectionModel().getSelectedItem())!=null)
				{
					if(!msgFromServer.get(i).equals(assessorNominationTable.getSelectionModel().getSelectedItem().getUserID()))
					{
						engineersList.add(msgFromServer.get(i) + " " + msgFromServer.get(i+1) + " " + msgFromServer.get(i+2));
						setEngineersList = FXCollections.observableArrayList(engineersList);
					}
				}
			} 
			System.out.println("Engineers List in CBX at Waiting for approval Tab:"+engineersList.toString());	
			engineersCBX.setItems((ObservableList<String>) setEngineersList);
		}
	}
	
	//Sets "Phase duration time evaluation table" with all the requests numbers and their phases names that need approval to the estimated duration time sent to the inspector
	public void setPhaseDurationTimeEvaluationTable(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test68");
			evaluationPhaseDurationTable.setItems(null);
			txtDurationTime.setText("There are no requests to take care of");
		}
		else
		{
			int i;
			ObservableList<InspectorWaitingForApprovalTables> data = FXCollections.observableArrayList();
			System.out.println("return to Evaluation phase duration time Table:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size()-1;i=i+3){
				data.add(new InspectorWaitingForApprovalTables(msgFromServer.get(i), msgFromServer.get(i+1), msgFromServer.get(i+2)));
			} 

			evaluationPhaseDurationTable.setItems(data);
		}
	}
	
	//Sets "Approval to close a request table" with all the requests numbers and their reasons to closure that need to be closed and waiting for approval of the Inspector
	public void setCloseARequestTable(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test69");
			closeARequestTable.setItems(null);
			txtCloseARequest.setText("There are no requests to take care of");
		}
		else
		{
			int i;
			ObservableList<InspectorWaitingForApprovalTables> data = FXCollections.observableArrayList();
			System.out.println("return to Approval to close a request Table:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size()-1;i=i+2){
				data.add(new InspectorWaitingForApprovalTables(msgFromServer.get(i), msgFromServer.get(i+1)));
			} 

			closeARequestTable.setItems(data);
		}
	}
	
	//Sets "Assessor nomination table" with all the requests numbers that got automatic nomination of Assessor and waiting for approval\change of the Inspector
	public void setAssessorNominationTable(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test70");
			assessorNominationTable.setItems(null);
			txtAssessorNomination.setText("There are no requests to take care of");
		}
		else
		{
			int i;
			ObservableList<InspectorWaitingForApprovalTables> data = FXCollections.observableArrayList();
			System.out.println("return to Assessor nomination Table:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size()-1;i=i+4){
				data.add(new InspectorWaitingForApprovalTables(msgFromServer.get(i), msgFromServer.get(i+1), msgFromServer.get(i+2), msgFromServer.get(i+3)));
			} 

			assessorNominationTable.setItems(data);
		}
	}
	
	//Sets "Phase extension time requests table" with all the requests numbers and their phases names that need approval to the extension time sent to the inspector
	public void setPhaseExtensionTimeTable(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test72");
			phaseExtensionTimeTable.setItems(null);
			txtExtensionTime.setText("There are no requests to take care of");
		}
		else
		{
			int i;
			ObservableList<InspectorWaitingForApprovalTables> data = FXCollections.observableArrayList();
			System.out.println("return to Phase extension time requests Table:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size()-1;i=i+3){
				data.add(new InspectorWaitingForApprovalTables(msgFromServer.get(i), msgFromServer.get(i+1), msgFromServer.get(i+2)));
			} 

			phaseExtensionTimeTable.setItems(data);
		}
	}
	
	//When "Refresh Table" button clicked to refresh Evaluation phase duration time Table
	public void refreshTableButtonClicked(ActionEvent event)
	{
		txtCloseARequest.setText("");
		txtDurationTime.setText("");
		txtExtensionTime.setText("");
		txtAssessorNomination.setText("");
		txtEngineer3.setText("");
		engineersCBX.setItems(null);
		mainClient.handleMessageFromClientUI("68#");
	}
	
	//When "Refresh Table" button clicked to refresh Approval to close a request Table
	public void refreshTableButtonClicked2(ActionEvent event)
	{
		txtCloseARequest.setText("");
		txtDurationTime.setText("");
		txtExtensionTime.setText("");
		txtAssessorNomination.setText("");
		txtEngineer3.setText("");
		engineersCBX.setItems(null);
		mainClient.handleMessageFromClientUI("69#");
	}
	
	//When "Refresh Table" button clicked to refresh Assessor nomination Table
	public void refreshTableButtonClicked3(ActionEvent event)
	{
		txtCloseARequest.setText("");
		txtDurationTime.setText("");
		txtExtensionTime.setText("");
		txtAssessorNomination.setText("");
		txtEngineer3.setText("");
		engineersCBX.setItems(null);
		mainClient.handleMessageFromClientUI("70#");
	}
	
	//When "Refresh Table" button clicked to refresh Phase extension time requests Table
	public void refreshTableButtonClicked4(ActionEvent event)
	{
		txtCloseARequest.setText("");
		txtDurationTime.setText("");
		txtExtensionTime.setText("");
		txtAssessorNomination.setText("");
		txtEngineer3.setText("");
		engineersCBX.setItems(null);
		mainClient.handleMessageFromClientUI("72#");
	}
	
	//When "Choose information system" ComboBox clicked and the user chose one of the information systems in the list at Nomination Management tab
	public void getInformationSystemNameFromCBX(ActionEvent event)
	{
		txtRequestNumber.setText("");
		txtRole.setText("");
		txtRole2.setText("");
		inspectorTable.setItems(null);
		activeRequestCBX.setItems(null);
		chooseRoleCBX.setItems(null);
		chooseEngineerCBX.setItems(null);
		chooseEngineerCBX2.setItems(null);
		activeRequestCBX.setVisible(false);
		chooseRoleCBX.setVisible(false);
		chooseEngineerCBX.setVisible(false);
		chooseEngineerCBX2.setVisible(false);
		btnNominate.setVisible(false);
		btnChange.setVisible(false);
		txtInformationSystem.setText("");
		txtEngineer.setText("");
		txtEngineer2.setText("");
		mainClient.handleMessageFromClientUI("37#" + informationSystemCBX.getValue().toString());		
	}
	
	//Sets "Choose active request" ComboBox according to the choice of the user at "Choose information system" ComboBox at Nomination Management tab
	public void setChooseActiveRequestCBX(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			txtInformationSystem.setText("There are no active requests in this information system!");
			System.out.println("test37");
		}
		else
		{
			int i;
			ArrayList<String> requestNumberList = new ArrayList<>(); 
			
			System.out.println("return to Choose active request CBX:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size();i++){
				requestNumberList.add(msgFromServer.get(i));
				activeRequestList = FXCollections.observableArrayList(requestNumberList);
			} 
			activeRequestCBX.setItems((ObservableList<String>) activeRequestList);
			activeRequestCBX.setVisible(true);
		}
	}
	
	//When "Choose active request" ComboBox clicked and the user chose one of the request numbers in the list
	public void getActiveRequestFromCBX(ActionEvent event)
	{
		txtRole.setText("");
		txtRole2.setText("");
		inspectorTable.setItems(null);
		chooseRoleCBX.setItems(null);
		chooseEngineerCBX.setItems(null);
		chooseEngineerCBX2.setItems(null);
		chooseRoleCBX.setVisible(false);
		chooseEngineerCBX.setVisible(false);
		chooseEngineerCBX2.setVisible(false);
		btnNominate.setVisible(false);
		btnChange.setVisible(false);
		txtRequestNumber.setText("");
		txtEngineer.setText("");
		txtEngineer2.setText("");
		if(activeRequestCBX.getValue() == null);
		else
			mainClient.handleMessageFromClientUI("39#" + activeRequestCBX.getValue().toString());		
	}
	
	//Sets "Choose a role" ComboBox(under "Add new nomination") according to the choice of the user at "Choose active request" ComboBox
	public void setChooseARoleCBX(ArrayList<String> msgFromServer)
	{
		chooseRoleCBX.setItems(null);
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test39");
			txtRequestNumber.setText("There are no roles left to nominate in this request!");
		}
		else
		{
			int i;
			ArrayList<String> rolesList = new ArrayList<>(); 
					
			System.out.println("return to Choose a role CBX:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size();i++){
				rolesList.add(msgFromServer.get(i));
				setRolesList2 = FXCollections.observableArrayList(rolesList);
			} 
			chooseRoleCBX.setItems((ObservableList<String>) setRolesList2);
			chooseRoleCBX.setVisible(true);
		}
		
		txtRole.setText("");
		txtRole2.setText("");
		txtEngineer.setText("");
		txtEngineer2.setText("");
		if(activeRequestCBX.getValue() == null)
			txtRequestNumber.setText("Please choose a request number!");
		else
		{
			requestNumber = activeRequestCBX.getValue().toString();
			mainClient.handleMessageFromClientUI("38#" + activeRequestCBX.getValue().toString());
		}
	}
	
	//Sets the Nomination Management table with all the details of users and their roles at a specific request
	public void getRolesDetailsOfRequestNumber(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test38");
			txtRequestNumber.setText("There are no nominations for this request yet!");
		}
		else
		{
			int i;
			ObservableList<InfoTable> data = FXCollections.observableArrayList();
			System.out.println("return to Nomination Management Table:"+msgFromServer.toString());

			data.add(new InfoTable(requestNumber, msgFromServer.get(0), msgFromServer.get(1), msgFromServer.get(2), msgFromServer.get(3)));
			for (i=4;i< msgFromServer.size()-1;i=i+4){
				data.add(new InfoTable("", msgFromServer.get(i), msgFromServer.get(i+1), msgFromServer.get(i+2), msgFromServer.get(i+3)));
			} 

			inspectorTable.setItems(data);
		}
	}
	
	//When "Choose a role" ComboBox clicked(under "Add new nomination") and the user chose one of the roles in the list
	public void getNewNominationRoleFromCBX(ActionEvent event)
	{
		chooseEngineerCBX.setItems(null);
		txtRole.setText("");
		txtRole2.setText("");
		txtEngineer.setText("");
		txtEngineer2.setText("");
		chooseEngineerCBX.setVisible(false);
		btnNominate.setVisible(false);
		if(chooseRoleCBX.getValue() == null);
		else
			mainClient.handleMessageFromClientUI("40#" + activeRequestCBX.getValue() + "#" + chooseRoleCBX.getValue());		
	}
	
	//Sets "Choose engineer" ComboBox according to the choice of the user at "Choose a role" ComboBox(under "Add new nomination")
	public void setChooseEngineerCBX(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			txtRole.setText("There are no Engineers that can be nominated to this role!");
			System.out.println("test40");
		}
		else
		{
			int i;
			ArrayList<String> engineerList = new ArrayList<>(); 
				
			System.out.println("return to Choose engineer CBX:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size()-1;i=i+3){
				engineerList.add(msgFromServer.get(i) + " " + msgFromServer.get(i+1) + " " + msgFromServer.get(i+2));
				engineersList = FXCollections.observableArrayList(engineerList);
			} 
			chooseEngineerCBX.setItems((ObservableList<String>) engineersList);
			chooseEngineerCBX.setVisible(true);
		}
	}
	
	
	//When "Choose engineer" ComboBox clicked and the user chose an Engineer
	public void chooseEngineerCBXClicked(ActionEvent event)
	{
		txtRole2.setText("");
		txtEngineer.setText("");
		txtEngineer2.setText("");
		btnNominate.setVisible(true);
	}
	
	//When "Nominate" button clicked and the user chose a request number, a role and an engineer to nominate him to a specific role at the specific request
	public void nominateButtonClicked(ActionEvent event)
	{
		txtRole2.setText("");
		txtEngineer2.setText("");
		if(chooseEngineerCBX.getValue() == null)
			txtEngineer.setText("Please choose Engineer!");
		else
		{
			String str = chooseEngineerCBX.getValue();
			String str2 = "";
			String ch = "" ;
			for(int i=0; i<str.length()-1; i++)
			{
				ch = str.substring(i, i+1);
				if(!(ch.equals(" ")))
					str2 = str2 + ch;
				else i = str.length()-1;
			}
			mainClient.handleMessageFromClientUI("47#" + activeRequestCBX.getValue().toString() + "#" + chooseRoleCBX.getValue().toString() + "#" + str2);
		}
	}
	
	//Gets a message of success from the server about the new nomination of a specific Engineer to a specific role at a specific request and pops-up a window with a message of success
	public void msgOfNewNomination(ArrayList<String> msgFromServer)
	{
		System.out.println("test 47 has " + msgFromServer.get(0));
		txtSuccessMsg.setText("Nominate succeeded!");
		okPain.setVisible(true);
	}
	
	//After the user clicked Nominate/Change and saw the message of success that popped-up and clicked "Ok" button it reopens the Inspector HomePage at Nomination Management tab
	public void okButtonClicked(ActionEvent event)
	{
		try {
			txtSuccessMsg.getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/InspectorHomepage.fxml").openStream());
				
			InspectorHomepageController inspectorHomepageController = loader.getController();	
			mainClient.setInspectorHomepageController(inspectorHomepageController);
				
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			}
			catch (Exception e3) {
				e3.printStackTrace();
			}
	}
		
	//Sets "Choose new engineer to set" ComboBox according to the choice of the user in the table at Nomination Management
	public void setChooseNewEngineerToSetCBX(ArrayList<String> msgFromServer)
	{
		Platform.runLater(
				()->{
		chooseEngineerCBX2.setItems(null);
		if (msgFromServer.get(0).equals("false")) {
			txtEngineer2.setText("There are no other engineers to choose!");
			System.out.println("test49");
		}
		else
		{
			int i;
			ArrayList<String> engineerList = new ArrayList<>(); 
						
			System.out.println("return to Choose new engineer to set CBX:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size()-1;i=i+3){
				engineerList.add(msgFromServer.get(i) + " " + msgFromServer.get(i+1) + " " + msgFromServer.get(i+2));
				engineersList2 = FXCollections.observableArrayList(engineerList);
			} 
			chooseEngineerCBX2.setItems((ObservableList<String>) engineersList2);
			System.out.println(chooseEngineerCBX2.getValue());
			chooseEngineerCBX2.setVisible(true);
		}
				});
	}
	
	//When "Choose new engineer to set" ComboBox clicked and the user chose an Engineer
	public void chooseNewEngineerToSetCBXClicked(ActionEvent event)
	{
		txtEngineer2.setText("");
		txtRole2.setText("");
		btnChange.setVisible(true);
	}
	
	//When "Change" button clicked and the user chose a request number, a role and an engineer to replace with another engineer on a specific role at the specific request
	public void changeButtonClicked(ActionEvent event)
	{
		txtEngineer2.setText("");
		if(chooseEngineerCBX2.getValue() == null)
			txtEngineer2.setText("Please choose Engineer!");
		else if(activeRequestCBX.getValue() == null)
			txtRequestNumber.setText("Please choose a request number!");
		else if((inspectorTable.getSelectionModel().getSelectedItem())!=null)
		{
			roleSelectedFromTable = inspectorTable.getSelectionModel().getSelectedItem().getRole();
			userIDSelectedFromTable = inspectorTable.getSelectionModel().getSelectedItem().getUserID();
			String str = chooseEngineerCBX2.getValue();
			String str2 = "";
			String ch = "" ;
			for(int i=0; i<str.length()-1; i++)
			{
				ch = str.substring(i, i+1);
				if(!(ch.equals(" ")))
					str2 = str2 + ch;
				else i = str.length()-1;
			}
			mainClient.handleMessageFromClientUI("50#" + activeRequestCBX.getValue().toString() + "#" + roleSelectedFromTable + "#" + str2 + "#" + userIDSelectedFromTable);
		}
	}
	
	//Gets a message of success from the server about the change of nomination of a specific Engineer to a specific role at a specific request
	public void msgOfChangeNomination(ArrayList<String> msgFromServer)
	{
		System.out.println("test 50 has " + msgFromServer.get(0));
		txtSuccessMsg.setText("Change succeeded!");
		okPain.setVisible(true);
	}
	
	//When "Display all active requests" button clicked
	public void displayAllActiveRequestsButtonClicked(ActionEvent event)
	{
		txtDisplayActiveRequests.setText("");
		txtDisplayClosedRequests.setText("");
		txtFreezeProcess.setText("");
		txtGotFrozen.setText("");
		mainClient.handleMessageFromClientUI("52#");
	}
	
	//After "Display all active requests" button clicked shows all the active requests in the system in the table at Request Management tab
	public void receiveAllActiveRequestsDetails(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test52");
			txtDisplayActiveRequests.setText("There is no active requests!");
		}
		else
		{
			int i;
			ObservableList<InfoTable> data = FXCollections.observableArrayList();
			System.out.println("return to Request Management Table(Active requests):"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size()-1;i=i+4){
				data.add(new InfoTable(msgFromServer.get(i), msgFromServer.get(i+1), msgFromServer.get(i+2), msgFromServer.get(i+3)));
			} 

			inspectorTable2.setItems(data);
		}
	}
	
	//When "Display all closed requests" button clicked
	public void displayAllClosedRequestsButtonClicked(ActionEvent event)
	{
		txtDisplayClosedRequests.setText("");
		txtDisplayActiveRequests.setText("");
		txtFreezeProcess.setText("");
		txtGotFrozen.setText("");
		mainClient.handleMessageFromClientUI("51#");
	}
		
	//After "Display all closed requests" button clicked shows all the closed requests in the system in the table at Request Management tab
	public void receiveAllClosedRequestsDetails(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test51");
			txtDisplayClosedRequests.setText("There is no closed requests!");
		}
		else
		{
			int i;
			ObservableList<InfoTable> data = FXCollections.observableArrayList();
			System.out.println("return to Request Management Table(Closed requests):"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size()-1;i=i+3){
				data.add(new InfoTable(msgFromServer.get(i), msgFromServer.get(i+1), msgFromServer.get(i+2), ""));
			} 

			inspectorTable2.setItems(data);
		}
	}
	
	//When "Freeze process" button clicked
	public void freezeProcessButtonClicked(ActionEvent event)
	{
		txtFreezeProcess.setText("");
		txtGotFrozen.setText("");
		txtDisplayClosedRequests.setText("");
		txtDisplayActiveRequests.setText("");
		if(inspectorTable2.getSelectionModel().getSelectedItem()!=null)
			mainClient.handleMessageFromClientUI("53#" + requestNumberSelectedFromTable);
		else
			txtFreezeProcess.setText("Choose active request from the table!");
	}
	
	//After "Freeze process" button clicked checks if the update succeeded or not and refresh table if it did
	public void checkProcessGotFrozen(ArrayList<String> msgFromServer)
	{
		if(msgFromServer.get(0).equals("succeeded"))
		{
			txtGotFrozen.setText("The process is now frozen");
			mainClient.handleMessageFromClientUI("52#");
		}
		else if(msgFromServer.get(0).equals("failed"))
			txtFreezeProcess.setText("Choose active request from the table!");
	}
	
	//When "Choose information system" ComboBox clicked and the user chose one of the information systems from the list at Request Management tab
	public void getInformationSystemNameFromCBX2(ActionEvent event)
	{
		chooseRequestCBX.setItems(null);
		txtInformationSystem2.setText("");
		txtFreezeProcess.setText("");
		txtGotFrozen.setText("");
		mainClient.handleMessageFromClientUI("57#" + informationSystemCBX2.getValue());	
	}
	
	//Sets "Choose request" ComboBox according to the choice of the user at "Choose information system" ComboBox at Request Management tab
	public void setChooseRequestCBX(ArrayList<String> msgFromServer)
	{
		chooseRequestCBX.setItems(null);
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test57");
		}
		else
		{
			int i;
			ArrayList<String> requestNumberList = new ArrayList<>(); 
				
			System.out.println("return to Choose request CBX:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size();i++){
				requestNumberList.add(msgFromServer.get(i));
				requestList = FXCollections.observableArrayList(requestNumberList);
			} 
			chooseRequestCBX.setItems((ObservableList<String>) requestList);
		}
	}
	
	//When "Choose request" ComboBox clicked and the user chose one of the requests numbers from the list at Request Management tab
	public void chooseRequestCBXClicked(ActionEvent event)
	{
		txtChooseRequest.setText("");
		txtFreezeProcess.setText("");
		txtGotFrozen.setText("");
	}
		
	//When "Open request details" button clicked
	public void openRequestDetailsButtonClicked(ActionEvent event)
	{
		txtChooseRequest.setText("");
		txtFreezeProcess.setText("");
		txtGotFrozen.setText("");
		if(chooseRequestCBX.getValue() == null)
			txtChooseRequest.setText("Please choose a request number!");
		else
		{
			rNumber = chooseRequestCBX.getValue();
			try {
				activeRequestCBX.getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxmls/ChangeEstimatedFinishDateByInspector.fxml").openStream());
				
				ChangeEstimatedFinishDateByInspectorController changeEstimatedFinishDateByInspectorController = loader.getController();	
				mainClient.setChangeEstimatedFinishDateByInspectorController(changeEstimatedFinishDateByInspectorController);
				
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
				}
				catch (Exception e4) {
					e4.printStackTrace();
				}
		}
	}
	
	//When "Approve" button clicked (under Approval to close a request) sends to the server approval of Inspector of closing a specific request
	public void approveButtonForClosingARequestClicked(ActionEvent event)
	{
		txtCloseARequest.setText("");
		txtDurationTime.setText("");
		txtExtensionTime.setText("");
		txtAssessorNomination.setText("");
		txtEngineer3.setText("");
		engineersCBX.setItems(null);
		if((closeARequestTable.getSelectionModel().getSelectedItem())!=null)
			mainClient.handleMessageFromClientUI("71#" + closeARequestTable.getSelectionModel().getSelectedItem().getRequestNumber());
		else
			txtCloseARequest.setText("Please choose request number to approve from table first!");
	}
	
	//After the Inspector approved a request to close gets a message of success
	public void approvalOFClosingARequestSucceeded(ArrayList<String> msgFromServer)
	{
		if(msgFromServer.get(0).equals("succeeded"))
		{
			System.out.println("test 71 has been " + msgFromServer.get(0));
			mainClient.getloginWindowController().okMsgCaller("Approve closing a request succeeded!");
			mainClient.handleMessageFromClientUI("69#");
		}
	}
	
	//When "Approve" button clicked (under Phase duration time evaluation) sends to the server approval of Inspector of a phase duration time in a specific request
	public void approveButtonForPhaseDurationTimeClicked(ActionEvent event)
	{
		txtCloseARequest.setText("");
		txtDurationTime.setText("");
		txtExtensionTime.setText("");
		txtAssessorNomination.setText("");
		txtEngineer3.setText("");
		engineersCBX.setItems(null);
		if((evaluationPhaseDurationTable.getSelectionModel().getSelectedItem())!=null)
			mainClient.handleMessageFromClientUI("73#" + evaluationPhaseDurationTable.getSelectionModel().getSelectedItem().getRequestNumber() + "#" + "Accepted" + "#" + evaluationPhaseDurationTable.getSelectionModel().getSelectedItem().getDate() + "#" + evaluationPhaseDurationTable.getSelectionModel().getSelectedItem().getPhaseName());
		else
			txtDurationTime.setText("Please choose request number to approve or decline from table first!");
	}
	
	//After the Inspector approved a phase duration time in a specific request gets a message of success
	public void approvalOFPhaseDurationTimeSucceeded(ArrayList<String> msgFromServer)
	{
		if(msgFromServer.get(0).equals("succeeded"))
		{
			System.out.println("test 73 has been " + msgFromServer.get(0));
			mainClient.getloginWindowController().okMsgCaller("Approve phase duration time succeeded!");
			mainClient.handleMessageFromClientUI("68#");
		}
	}
	
	//When "Decline" button clicked (under Phase duration time evaluation) sends to the server a declination of Inspector of a phase duration time in a specific request
	public void declineButtonForPhaseDurationTimeClicked(ActionEvent event)
	{
		txtCloseARequest.setText("");
		txtDurationTime.setText("");
		txtExtensionTime.setText("");
		txtAssessorNomination.setText("");
		txtEngineer3.setText("");
		engineersCBX.setItems(null);
		if((evaluationPhaseDurationTable.getSelectionModel().getSelectedItem())!=null)
			mainClient.handleMessageFromClientUI("74#" + evaluationPhaseDurationTable.getSelectionModel().getSelectedItem().getRequestNumber() + "#" + "Denied" + "#" + evaluationPhaseDurationTable.getSelectionModel().getSelectedItem().getPhaseName());
		else
			txtDurationTime.setText("Please choose request number to approve or decline from table first!");
	}
		
	//After the Inspector declined a phase duration time in a specific request gets a message of success
	public void declinationOFPhaseDurationTimeSucceeded(ArrayList<String> msgFromServer)
	{
		if(msgFromServer.get(0).equals("succeeded"))
		{
			System.out.println("test 74 has been " + msgFromServer.get(0));
			mainClient.getloginWindowController().okMsgCaller("Decline phase duration time succeeded!");
			mainClient.handleMessageFromClientUI("68#");
		}
	}
	
	//When "Approve" button clicked (under Phase extension time requests) sends to the server approval of Inspector of a phase extension time in a specific request
	public void approveButtonForPhaseExtensionTimeClicked(ActionEvent event)
	{
		txtCloseARequest.setText("");
		txtDurationTime.setText("");
		txtExtensionTime.setText("");
		txtAssessorNomination.setText("");
		txtEngineer3.setText("");
		engineersCBX.setItems(null);
		if((phaseExtensionTimeTable.getSelectionModel().getSelectedItem())!=null)
			mainClient.handleMessageFromClientUI("75#" + phaseExtensionTimeTable.getSelectionModel().getSelectedItem().getRequestNumber() + "#" + "Accepted" + "#" + phaseExtensionTimeTable.getSelectionModel().getSelectedItem().getDate() + "#" + phaseExtensionTimeTable.getSelectionModel().getSelectedItem().getPhaseName());
		else
			txtExtensionTime.setText("Please choose request number to approve or decline from table first!");
	}
		
	//After the Inspector approved a phase extension time in a specific request gets a message of success
	public void approvalOFPhaseExtensionTimeSucceeded(ArrayList<String> msgFromServer)
	{
		if(msgFromServer.get(0).equals("succeeded"))
		{
			System.out.println("test 75 has been " + msgFromServer.get(0));
			mainClient.getloginWindowController().okMsgCaller("Approve phase extension time succeeded!");
			mainClient.handleMessageFromClientUI("72#");
		}
	}
	
	//When "Decline" button clicked (under Phase extension time requests) sends to the server a declination of Inspector of a phase extension time in a specific request
	public void declineButtonForPhaseExtensionTimeClicked(ActionEvent event)
	{
		txtCloseARequest.setText("");
		txtDurationTime.setText("");
		txtExtensionTime.setText("");
		txtAssessorNomination.setText("");
		txtEngineer3.setText("");
		engineersCBX.setItems(null);
		if((phaseExtensionTimeTable.getSelectionModel().getSelectedItem())!=null)
			mainClient.handleMessageFromClientUI("76#" + phaseExtensionTimeTable.getSelectionModel().getSelectedItem().getRequestNumber() + "#" + "Denied" + "#" + phaseExtensionTimeTable.getSelectionModel().getSelectedItem().getPhaseName());
		else
			txtExtensionTime.setText("Please choose request number to approve or decline from table first!");
	}
			
	//After the Inspector declined a phase extension time in a specific request gets a message of success
	public void declinationOFPhaseExtensionTimeSucceeded(ArrayList<String> msgFromServer)
	{
		if(msgFromServer.get(0).equals("succeeded"))
		{
			System.out.println("test 76 has been " + msgFromServer.get(0));
			mainClient.getloginWindowController().okMsgCaller("Decline phase extension time succeeded!");
			mainClient.handleMessageFromClientUI("72#");
		}
	}

	//When "Approve" button clicked (under Assessor nomination) sends to the server approval of Inspector of automatic nomination of Assessor to a specific request
	public void approveButtonForAutomaticAssessorNominationClicked(ActionEvent event)
	{
		txtCloseARequest.setText("");
		txtDurationTime.setText("");
		txtExtensionTime.setText("");
		txtAssessorNomination.setText("");
		txtEngineer3.setText("");
		engineersCBX.setItems(null);
		if((assessorNominationTable.getSelectionModel().getSelectedItem())!=null)
			mainClient.handleMessageFromClientUI("77#" + assessorNominationTable.getSelectionModel().getSelectedItem().getRequestNumber());
		else
			txtAssessorNomination.setText("Please choose request number for assessor approval or change from table first!");
	}
		
	//After the Inspector approved an automatic nomination of Assessor to a specific request gets a message of success
	public void approvalOFAutomaticAssessorNominationSucceeded(ArrayList<String> msgFromServer)
	{
		if(msgFromServer.get(0).equals("succeeded"))
		{
			System.out.println("test 77 has been " + msgFromServer.get(0));
			mainClient.getloginWindowController().okMsgCaller("Approve automatic nomination of Assessor succeeded!");
			mainClient.handleMessageFromClientUI("70#");
		}
	}
	
	//When "Change" button clicked (under Assessor nomination) sends to the server a change that the Inspector made of Assessor nomination to a specific request
	public void changeButtonForAssessorNominationClicked(ActionEvent event)
	{
		txtCloseARequest.setText("");
		txtDurationTime.setText("");
		txtExtensionTime.setText("");
		txtAssessorNomination.setText("");
		txtEngineer3.setText("");
		if((assessorNominationTable.getSelectionModel().getSelectedItem())!=null)
		{
			if(engineersCBX.getValue() != null)
			{
				String str = engineersCBX.getValue();
				String str2 = "";
				String ch = "" ;
				for(int i=0; i<str.length()-1; i++)
				{
					ch = str.substring(i, i+1);
					if(!(ch.equals(" ")))
						str2 = str2 + ch;
					else i = str.length()-1;
				}
				mainClient.handleMessageFromClientUI("78#" + assessorNominationTable.getSelectionModel().getSelectedItem().getRequestNumber() + "#" + str2);
			}
			else txtEngineer3.setText("Please choose Engineer!");
		}
		else
			txtAssessorNomination.setText("Please choose request number for assessor approval or change from table first!");
	}
		
	//After the Inspector changed the Assessor nomination at a specific request gets a message of success
	public void changeOFAssessorNominationSucceeded(ArrayList<String> msgFromServer)
	{
		engineersCBX.setItems(null);
		if(msgFromServer.get(0).equals("succeeded"))
		{
			System.out.println("test 78 has been " + msgFromServer.get(0));
			mainClient.getloginWindowController().okMsgCaller("Change nomination of Assessor succeeded!");
			mainClient.handleMessageFromClientUI("70#");
		}
	}
	
	//When "Back" button clicked at one of the inspector's tabs goes back to FieldChoice page
	public void backButtonClicked(ActionEvent event)
	{
		try {
			activeRequestCBX.getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/FieldChoice.fxml").openStream());
			
			FieldChoiceController fieldChoiceController = loader.getController();	
			mainClient.setFieldChoiceController(fieldChoiceController);
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			}
			catch (Exception e4) {
				e4.printStackTrace();
			}
	}
	
	public void getInspectorRequestsDelay(ArrayList<String> msgFromServer) {
		String requests="";
		if(!msgFromServer.get(0).equals("false")) {
			for(int i=0;i<msgFromServer.size();i=i+3) {
				
				if(!currentDateChecker(msgFromServer.get(i+2))&&!msgFromServer.get(i+2).equals(null))
					{
					LocalDate date = LocalDate.parse(msgFromServer.get(i+2));
					long temp = ChronoUnit.DAYS.between(date, LocalDate.now());
					requests= requests+("Request number "+msgFromServer.get(i)+" at phase "+msgFromServer.get(i+1)+" is in "+String.valueOf(temp)+" days exception.\n");
					}
			
			}
			textFieldRequestsInDelay.setText(requests);
		}
	}
	
	public boolean currentDateChecker(String date2) {// check that chosen date(in datepicker is in from today on
		LocalDate date = LocalDate.parse(date2);
		if (ChronoUnit.DAYS.between(LocalDate.now(), date) >= 0)
			return true;
		return false;
	}
}
