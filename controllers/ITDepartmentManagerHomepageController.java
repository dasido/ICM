package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import client.EchoClient;
import entities.ITReadAccessTables;
import entities.InfoTable;
import entities.Request;
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

public class ITDepartmentManagerHomepageController implements Initializable{
	
	private EchoClient mainClient;
	Vector<Request> requestsVectorITDepartmentManager = new Vector<>();
	ObservableList<String> setInformationSystemsList;
	ObservableList<String> setInformationSystemsList2;
	ObservableList<String> setRolesList;
	ObservableList<String> setPermissionsList;
	ObservableList<String> deletePermissionsList;
	ObservableList<String> setReportsList;
	private ObservableList<String> activeRequestList;
	private ObservableList<String> setRolesList2;
	private ObservableList<String> engineersList;
	private ObservableList<String> engineersList2;
	private ObservableList<String> engineersList3;
	private int nominationManagementTab = 0;
	private int requestManagementTab = 0;
	private int permissionsManagementTab = 0;
	private int reportsTab = 0;
	private int viewOnlyAccessTab = 0;
	private String requestNumber;
	private String RNumber;
	private String RNumberToActive;
	private String permissionToDelete;
	private String roleSelectedFromTable = null;
	private String userIDSelectedFromTable = null;
	boolean flag = false;

	@FXML 
	private TableView<InfoTable> itDepartmentManagerTable;
	
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
	private TableView<InfoTable> itDepartmentManagerTable2;
	
	@FXML
	private TableColumn<InfoTable, String> requestNumberColumn2;
	
	@FXML
	private TableColumn<InfoTable, String> applicantIDColumn;
	
	@FXML
	private TableColumn<InfoTable, String> informationSystemNameColumn;
	
	@FXML
	private TableColumn<InfoTable, String> currentPhaseColumn;
	
	@FXML 
	private TableView<InfoTable> itDepartmentManagerTable21;
	
	@FXML
	private TableColumn<InfoTable, String> requestNumberColumn21;
	
	@FXML
	private TableColumn<InfoTable, String> applicantIDColumn21;
	
	@FXML
	private TableColumn<InfoTable, String> informationSystemNameColumn21;
	
	@FXML
	private TableColumn<InfoTable, String> currentPhaseColumn21;
	
	@FXML 
	private TableView<InfoTable> itDepartmentManagerTable3;
	
	@FXML
	private TableColumn<InfoTable, String> engineerIDColumn;
	
	@FXML
	private TableColumn<InfoTable, String> inspectorPermissionColumn;
	
	@FXML
	private TableColumn<InfoTable, String> assessorPermissionColumn;
	
	@FXML
	private TableColumn<InfoTable, String> chairmanPermissionColumn;
	
	@FXML
	private TableColumn<InfoTable, String> memberOfCommitteePermissionColumn;
	
	@FXML
	private TableColumn<InfoTable, String> examinerPermissionColumn;
	
	@FXML
	private TableColumn<InfoTable, String> executionLeaderPermissionColumn;
	
	@FXML 
	private TableView<ITReadAccessTables> itDepartmentManagerTable4;
	
	@FXML
	private TableColumn<ITReadAccessTables, String> userIDColumn2;
	
	@FXML
	private TableColumn<ITReadAccessTables, String> userNameColumn;
	
	@FXML
	private TableColumn<ITReadAccessTables, String> passwordColumn;
	
	@FXML
	private TableColumn<ITReadAccessTables, String> fullNameColumn2;
	
	@FXML
	private TableColumn<ITReadAccessTables, String> emailColumn;
	
	@FXML
	private TableColumn<ITReadAccessTables, String> userDepartmentColumn2;
	
	@FXML
	private TableColumn<ITReadAccessTables, String> userRoleColumn;
	
	@FXML 
	private TableView<ITReadAccessTables> itDepartmentManagerTable5;
	
	@FXML
	private TableColumn<ITReadAccessTables, String> requestNumberColumn3;
	
	@FXML
	private TableColumn<ITReadAccessTables, String> applicantIDColumn2;
	
	@FXML
	private TableColumn<ITReadAccessTables, String> informationSystemNameColumn2;
	
	@FXML
	private TabPane tpITDepartmentManager;
	
	@FXML
	private Tab tabNominationManagement;
	
	@FXML
	private Tab tabRequestManagement;
	
	@FXML
	private Tab tabPermissionsManagement;
	
	@FXML
	private Tab tabReports;
	
	@FXML
	private Tab tabViewOnlyAccess;
	////////////////////////// Nomination page CBXs.
	@FXML
	private ComboBox<String> informationSystemCBX;
	
	@FXML
	private ComboBox<String> informationSystemCBX2;
	
	@FXML
	private ComboBox<String> chooseRoleCBX;
	
	@FXML
	private ComboBox<String> activeRequestCBX;
	
	@FXML
	private ComboBox<String> chooseEngineerCBX;
	
	@FXML
	private ComboBox<String> chooseEngineerCBX2;
	
	@FXML
	private ComboBox<String> chooseEngineerCBX3;
	
	//////////////////////////
	@FXML
	private ComboBox<String> choosePermissionCBX;
	
	@FXML
	private ComboBox<String> choosePermissionCBX2;
	
	@FXML
	private ComboBox<String> chooseReportCBX;
	
	
	///////////////////////////
	
	@FXML
	private Button btnSet;
	
	@FXML
	private Button btnViewDetailsOfRequest;
	
	@FXML
	private Button btnBackNomination;
	
	@FXML
	private Button btnBackRequest;
	
	@FXML
	private Button btnBackPermissions;
	
	@FXML
	private Button btnBackReports;
	
	@FXML
	private Button btnBackViewOnly;
	
	@FXML
	private Button btnShowDetails;
	
	@FXML
	private Button btnUnFreezeProcess;
	
	@FXML
	private Button btnRefreshViewOnly;
	
	@FXML
	private Button btnRefreshManagmentTables;
	
	@FXML
	private Button btnAddPermssion;
	
	@FXML
	private Button btnDeletePermission;
	
	@FXML
	private Button btnrefreshPermissionsTable;
	
	@FXML
	private Button getDetailsReport;
	
	@FXML
	private Button okButton;
	
	@FXML
	private Text txtInformationSystem;
	
	@FXML
	private Text txtInformationSystem2;
	
	@FXML
	private Text txtRequestNumber;
	
	@FXML
	private Text txtView;
	
	@FXML
	private Text txtRole;
	
	@FXML
	private Text txtEngineer;
	
	@FXML
	private Text txtRole2;
	
	@FXML
	private Text txtEngineer2;
	
	@FXML
	private Text txtEngineer3;
	
	@FXML
	private Text txtSuccessMsg;
	
	@FXML
	private Pane okPain;
	
	@FXML
	private Text txtResponsiblesForMaintenance;
	
	@FXML
	private TextArea textFieldRequestsInDelay;
	
	@FXML
	private TextArea textFieldRequestExtension;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		UserHomePageController.userOrAssessorOrIt = "It";
		requestNumberColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("requestNumber"));
		userIDColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("userID"));
		fullNameColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("fullName"));
		userDepartmentColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("userDepartment"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("role"));
		tpITDepartmentManager.getSelectionModel().select(tabNominationManagement);
		nominationManagementTab = 1;
		mainClient = loginWindowController.mainClient;
		tpITDepartmentManager.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) 
			{
				//When Nomination Management tab clicked
				if(tabNominationManagement.isSelected() && nominationManagementTab != 1)
				{
					tpITDepartmentManager.getSelectionModel().select(tabNominationManagement);
					nominationManagementTab = 1;
					requestManagementTab = 0;
					permissionsManagementTab = 0;
					reportsTab = 0;
					viewOnlyAccessTab = 0;
					txtRequestNumber.setText("");
					txtRole.setText("");
					txtRole2.setText("");
					informationSystemCBX.setItems(null);
					informationSystemCBX2.setItems(null);
					activeRequestCBX.setItems(null);
					chooseRoleCBX.setItems(null);
					chooseEngineerCBX.setItems(null);
					chooseEngineerCBX2.setItems(null);
					chooseEngineerCBX3.setItems(null);
					txtInformationSystem.setText("");
					txtInformationSystem2.setText("");
					txtView.setText("");
					txtEngineer.setText("");
					txtEngineer2.setText("");
					txtEngineer3.setText("");
					mainClient.handleMessageFromClientUI("11#");
				}
				
				//When Request Management tab clicked
				else if(tabRequestManagement.isSelected() && requestManagementTab != 1)
				{
					tpITDepartmentManager.getSelectionModel().select(tabRequestManagement);
					nominationManagementTab = 0;
					requestManagementTab = 1;
					permissionsManagementTab = 0;
					reportsTab = 0;
					viewOnlyAccessTab = 0;
					requestNumberColumn2.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("requestNumber"));
					applicantIDColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("applicantID"));
					informationSystemNameColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("informationSystemName"));
					currentPhaseColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("currentPhase"));
					requestNumberColumn21.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("requestNumber"));
					applicantIDColumn21.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("applicantID"));
					informationSystemNameColumn21.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("informationSystemName"));
					currentPhaseColumn21.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("currentPhase"));
					mainClient.handleMessageFromClientUI("62#");//Actice request.
					mainClient.handleMessageFromClientUI("63#");//Freeze request.

				}
				
				//When Permissions Management tab clicked
				else if(tabPermissionsManagement.isSelected() && permissionsManagementTab != 1)
				{
					tpITDepartmentManager.getSelectionModel().select(tabPermissionsManagement);
					nominationManagementTab = 0;
					requestManagementTab = 0;
					permissionsManagementTab = 1;
					reportsTab = 0;
					viewOnlyAccessTab = 0;
					engineerIDColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("engineerID"));
					inspectorPermissionColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("inspectorPermission"));
					assessorPermissionColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("assessorPermission"));
					chairmanPermissionColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("chairmanPermission"));
					memberOfCommitteePermissionColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("memberOfCommitteePermission"));
					executionLeaderPermissionColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("executionLeaderPermission"));
					examinerPermissionColumn.setCellValueFactory(new PropertyValueFactory<InfoTable, String>("examinerPermission"));
					mainClient.handleMessageFromClientUI("29#");
				}
				
				//When Reports tab clicked
				else if(tabReports.isSelected() && reportsTab != 1)
				{
					tpITDepartmentManager.getSelectionModel().select(tabReports);
					nominationManagementTab = 0;
					requestManagementTab = 0;
					permissionsManagementTab = 0;
					reportsTab = 1;
					viewOnlyAccessTab = 0;
					setReportsCBX();
				}
				
				//When View only access tab clicked
				else if(tabViewOnlyAccess.isSelected() && viewOnlyAccessTab != 1)
				{
					tpITDepartmentManager.getSelectionModel().select(tabViewOnlyAccess);
					nominationManagementTab = 0;
					requestManagementTab = 0;
					permissionsManagementTab = 0;
					reportsTab = 0;
					viewOnlyAccessTab = 1;
					userIDColumn2.setCellValueFactory(new PropertyValueFactory<ITReadAccessTables, String>("userID"));
					userNameColumn.setCellValueFactory(new PropertyValueFactory<ITReadAccessTables, String>("userName"));
					passwordColumn.setCellValueFactory(new PropertyValueFactory<ITReadAccessTables, String>("password"));
					fullNameColumn2.setCellValueFactory(new PropertyValueFactory<ITReadAccessTables, String>("fullName"));
					emailColumn.setCellValueFactory(new PropertyValueFactory<ITReadAccessTables, String>("email"));
					userDepartmentColumn2.setCellValueFactory(new PropertyValueFactory<ITReadAccessTables, String>("userDepartment"));
					userRoleColumn.setCellValueFactory(new PropertyValueFactory<ITReadAccessTables, String>("userRole"));
					requestNumberColumn3.setCellValueFactory(new PropertyValueFactory<ITReadAccessTables, String>("requestNumber"));
					applicantIDColumn2.setCellValueFactory(new PropertyValueFactory<ITReadAccessTables, String>("applicantID"));
					informationSystemNameColumn2.setCellValueFactory(new PropertyValueFactory<ITReadAccessTables, String>("informationSystemName"));
					mainClient.handleMessageFromClientUI("30#");
					mainClient.handleMessageFromClientUI("105#");
					mainClient.handleMessageFromClientUI("107#");
				}
				
				//When non of the tabs selected
				else System.out.println("No tab selected");
			}
		});
		
		//When the IT Department Manager table clicked and the user chose one of the rows in the table
		itDepartmentManagerTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				if((itDepartmentManagerTable.getSelectionModel().getSelectedItem())!=null) {
					txtRole2.setText("");
					roleSelectedFromTable = itDepartmentManagerTable.getSelectionModel().getSelectedItem().getRole();
					if(activeRequestCBX.getValue() == null)
						txtRequestNumber.setText("Please choose a request number!");
					else
						mainClient.handleMessageFromClientUI("86#" + activeRequestCBX.getValue().toString() + "#" + roleSelectedFromTable);
					System.out.println(roleSelectedFromTable);
							
						}
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
				if(itDepartmentManagerTable.getSelectionModel().getSelectedItem() == null)
					txtRole2.setText("Please choose an engineer with a role from table first!");
			}
		});
		
		chooseEngineerCBX3.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) 
			{
				if(informationSystemCBX2.getValue() == null)
					txtInformationSystem2.setText("Please choose information system!");
			}
		});

		okPain.setVisible(false);
		informationSystemCBX2.setVisible(false);
		chooseEngineerCBX3.setVisible(false);
		btnSet.setVisible(false);
		txtResponsiblesForMaintenance.setVisible(false);
		mainClient.handleMessageFromClientUI("11#");
	}

	//Sets "Choose information system" ComboBox with all the names of the information systems in the system at the "Nomination Management" tab
	public void setInformationSystemsNamesToNominationManagementCBX(ArrayList<String> msgFromServer)
	{		
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test11");
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
		mainClient.handleMessageFromClientUI("89#");
	}
	
	//Sets the table at the Permissions Management tab with all the engineers in the system with their permissions + Sets "Choose permission to set" ComboBox + Sets "Choose permission to delete" ComboBox
	public void setPermissionsManagementTable(ArrayList<String> msgFromServer)
	{
		//ArrayList<String> permissionsList = new ArrayList<>();
			
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test29");
		}
		else
		{	
			int i;
			String inspector, assessor, chairman, memberOfCommittee, executionLeader, examiner;
			ObservableList<InfoTable> data = FXCollections.observableArrayList();
			System.out.println("return to Permissions Management Table:"+msgFromServer.toString());
			flag = false;
			for (i=0;i< msgFromServer.size()-1;i=i+7){
				if(msgFromServer.get(i+1).equals("1")) {
					flag = true;
					inspector = "Permitted";
					}
					else inspector = "";
				
				if(msgFromServer.get(i+2).equals("1")) assessor = "Permitted"; else assessor = "";
				if(msgFromServer.get(i+3).equals("1")) chairman = "Permitted"; else chairman = "";
				if(msgFromServer.get(i+4).equals("1")) memberOfCommittee = "Permitted"; else memberOfCommittee = "";
				if(msgFromServer.get(i+5).equals("1")) examiner = "Permitted"; else examiner = "";
				if(msgFromServer.get(i+6).equals("1")) executionLeader = "Permitted"; else executionLeader = "";
				data.add(new InfoTable(msgFromServer.get(i), inspector, assessor, chairman, memberOfCommittee, executionLeader, examiner));
			} 
			
			itDepartmentManagerTable3.setItems(data);	
		}
		itDepartmentManagerTable3.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				System.out.println("Bens Test");
				ArrayList<String> permissionsList1 = new ArrayList<>();
				ArrayList<String> permissionsList2 = new ArrayList<>();
				if ((itDepartmentManagerTable3.getSelectionModel().getSelectedItem())!=null) {
					if(itDepartmentManagerTable3.getSelectionModel().getSelectedItem().getInspectorPermission().equals("Permitted"))
						permissionsList1.add("Inspector");
					else if(flag==false)
						permissionsList2.add("Inspector");
					if(itDepartmentManagerTable3.getSelectionModel().getSelectedItem().getAssessorPermission().equals("Permitted"))
						permissionsList1.add("Assessor");
					else
						permissionsList2.add("Assessor");
					if(itDepartmentManagerTable3.getSelectionModel().getSelectedItem().getChairmanPermission().equals("Permitted"))
						permissionsList1.add("Chairman");
					else
						permissionsList2.add("Chairman");
					if(itDepartmentManagerTable3.getSelectionModel().getSelectedItem().getMemberOfCommitteePermission().equals("Permitted"))
						permissionsList1.add("CommitteeMember");
					else
						permissionsList2.add("CommitteeMember");
					if(itDepartmentManagerTable3.getSelectionModel().getSelectedItem().getExecutionLeaderPermission().equals("Permitted"))
						permissionsList1.add("ExecutionLeader");
					else
						permissionsList2.add("ExecutionLeader");
					if(itDepartmentManagerTable3.getSelectionModel().getSelectedItem().getExaminerPermission().equals("Permitted"))
						permissionsList1.add("Examiner");
					else
						permissionsList2.add("Examiner");
	
					setPermissionsList = FXCollections.observableArrayList(permissionsList1);
					deletePermissionsList = FXCollections.observableArrayList(permissionsList2);
					choosePermissionCBX.setItems((ObservableList<String>) setPermissionsList);
					choosePermissionCBX2.setItems((ObservableList<String>) deletePermissionsList);
				}
				else
					mainClient.getloginWindowController().okMsgCaller("Please Choose from the existing engineers");
			}
		});
	}
	
	/*
	 * Sets "Please choose a report" ComboBox with all the reports the IT Department Manager can view at the Reports tab
	 */
	public void setReportsCBX()
	{
		ArrayList<String> reportsList = new ArrayList<>();
		
		reportsList.add("Activity");
		reportsList.add("Performance & Delays");
		setReportsList = FXCollections.observableArrayList(reportsList);
		System.out.println("The reports list in CBX:"+reportsList.toString());
		chooseReportCBX.setItems((ObservableList<String>) setReportsList);
	}
	
	//Sets Employees original information table at View only access tab
	public void setEmployeesOriginalInfoTable(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test30");
		}
		else
		{
			int i;
			ObservableList<ITReadAccessTables> data = FXCollections.observableArrayList();
			System.out.println("return to IT Department Manager Table 4:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size()-1;i=i+7){
				data.add(new ITReadAccessTables(msgFromServer.get(i), msgFromServer.get(i+1), msgFromServer.get(i+2), msgFromServer.get(i+3), msgFromServer.get(i+4), msgFromServer.get(i+5), msgFromServer.get(i+6)));
			}
		
			itDepartmentManagerTable4.setItems(data);
			mainClient.handleMessageFromClientUI("35#");
		}
	}
	
	//Sets Requests original information table at View only access tab
	public void setRequestsOriginalInfoTable(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test35");
		}
		else
		{
			int i;
			ObservableList<ITReadAccessTables> data = FXCollections.observableArrayList();
			System.out.println("return to IT Department Manager Table 5:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size()-1;i=i+12){
				Request r= new Request(msgFromServer.get(i),msgFromServer.get(i+1),msgFromServer.get(i+2),msgFromServer.get(i+3),msgFromServer.get(i+4),msgFromServer.get(i+5),msgFromServer.get(i+6),msgFromServer.get(i+7),msgFromServer.get(i+8),msgFromServer.get(i+9),msgFromServer.get(i+10),msgFromServer.get(i+11));
				requestsVectorITDepartmentManager.add(r);
				data.add(new ITReadAccessTables(msgFromServer.get(i), msgFromServer.get(i+1), msgFromServer.get(i+2)));
			}
		
			itDepartmentManagerTable5.setItems(data);
			System.out.println("requestsVector of IT Department Manager is: "+requestsVectorITDepartmentManager.toString());
		}
			
			itDepartmentManagerTable5.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				if ((itDepartmentManagerTable5.getSelectionModel().getSelectedItem())!=null) {
				RNumber = itDepartmentManagerTable5.getSelectionModel().getSelectedItem().getRequestNumber();
				System.out.println(RNumber);

				}
				else
					mainClient.getloginWindowController().okMsgCaller("Please Choose from the existing requests");
			}
		});
	}

	//Sets the freeze table in the request management.
	public void setFreezeRequestTable(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("null")) System.out.println("No Freeze requests exist");
		else
		{
			int i;
			ObservableList<InfoTable> data = FXCollections.observableArrayList();
			for (i=0;i< msgFromServer.size()-1;i=i+4)
				data.add(new InfoTable(msgFromServer.get(i),msgFromServer.get(i+1),msgFromServer.get(i+2),msgFromServer.get(i+3)));
			
			itDepartmentManagerTable21.setItems(data);
			
			itDepartmentManagerTable21.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle (MouseEvent event) {
					if ((itDepartmentManagerTable21.getSelectionModel().getSelectedItem())!=null) {
					RNumberToActive = itDepartmentManagerTable21.getSelectionModel().getSelectedItem().getRequestNumber();
					System.out.println(RNumberToActive);
					}
					else
						mainClient.getloginWindowController().okMsgCaller("Please Choose from the existing requests");
				}
			});			
		}
		
	}
	
	//Sets the Active table in the request management.
	public void setActiveRequestTable(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("null")) System.out.println("No Actice requests exist");
		else
		{
			int i;
			ObservableList<InfoTable> data = FXCollections.observableArrayList();
			for (i=0;i< msgFromServer.size()-1;i=i+4)
				data.add(new InfoTable(msgFromServer.get(i),msgFromServer.get(i+1),msgFromServer.get(i+2),msgFromServer.get(i+3)));
			
			itDepartmentManagerTable2.setItems(data);
					
		}
	}
	
	//gets approved to freeze the selected request from server.
	public void getApprovedToUnFreeze(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true")) 
			{
			mainClient.getloginWindowController().okMsgCaller("Request number "+RNumberToActive +" successfully activated.");
			mainClient.handleMessageFromClientUI("62#");//Actice request.
			mainClient.handleMessageFromClientUI("63#");//Freeze request.
			}
			else 			
			mainClient.getloginWindowController().okMsgCaller("Request number "+RNumberToActive +" Still freeze, Please try agian.");

	}
	
	//back to field choice screen.
	public void backToFieldChoise(ActionEvent event)
	{
		try {
			btnBackNomination.getScene().getWindow().hide(); // hiding primary window
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

	//open the selected request change.
	public void showRequestDetails(ActionEvent event) {
		if(RNumber!=null) 
			mainClient.handleMessageFromClientUI("56#" + RNumber);
		else
			mainClient.getloginWindowController().okMsgCaller("Please choose request first!");		
	}

	//opens the page of the change request with the selected request.
	public void goToOpenChangeRequest(ArrayList<String> msgFromServer) {
		javafx.application.Platform.runLater(
				  () -> {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/OpenChangeRequest.fxml").openStream());
			
			OpenChangeRequestController openChangeRequestController = loader.getController();	
			mainClient.setOpenChangeRequestController(openChangeRequestController);
			openChangeRequestController.setUpRequestForIT(msgFromServer.get(0), msgFromServer.get(1), msgFromServer.get(2), msgFromServer.get(3), msgFromServer.get(4));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
				  });
	}

	//send to server the requests we want to unfreeze.
	public void ActiveRequest(ActionEvent event) {
		if(itDepartmentManagerTable21.getSelectionModel().getSelectedItem()!=null)
		mainClient.handleMessageFromClientUI("65#"+RNumberToActive);
		else mainClient.getloginWindowController().okMsgCaller("Please choose a request to unfreeze first.");
	}

	//refreshes view only page tables.
	public void refreshViewOnlyTables() {
		mainClient.handleMessageFromClientUI("30#");
	}

	//refreshes the request management tables.
	public void refreshManagmentTables() {
		mainClient.handleMessageFromClientUI("62#");//Actice request.
		mainClient.handleMessageFromClientUI("63#");//Freeze request.
	}

	//Refresh the permissions table.
	public void refreshPermissionsTable() {
		mainClient.handleMessageFromClientUI("29#");
	}
	
	//Send to server the permission we want to add.
	public void addUserPermission(ActionEvent event) {
		if(choosePermissionCBX2.getSelectionModel().getSelectedItem()!=null && itDepartmentManagerTable3.getSelectionModel().getSelectedItem()!=null)
			mainClient.handleMessageFromClientUI("66#"+ itDepartmentManagerTable3.getSelectionModel().getSelectedItem().getEngineerID()+"#"+choosePermissionCBX2.getSelectionModel().getSelectedItem());
		else
			mainClient.getloginWindowController().okMsgCaller("Please Choose User first and then choose permission to add.");
	}
	
	//Send to server the permission we want to delete.
	public void deleteUserPermission(ActionEvent event) {
		if(choosePermissionCBX.getSelectionModel().getSelectedItem()!=null && itDepartmentManagerTable3.getSelectionModel().getSelectedItem()!=null)
		{
			mainClient.handleMessageFromClientUI("67#"+ itDepartmentManagerTable3.getSelectionModel().getSelectedItem().getEngineerID()+"#"+choosePermissionCBX.getSelectionModel().getSelectedItem());
			permissionToDelete = choosePermissionCBX.getSelectionModel().getSelectedItem();
		}
		else
			mainClient.getloginWindowController().okMsgCaller("Please Choose User first and then choose permission to delete.");
	}

	//gets the approve from the server about adding the permission.
	public void getAddPermissionApproved(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true"))
		{
			mainClient.getloginWindowController().okMsgCaller("Permission successfully added to User.");
			mainClient.handleMessageFromClientUI("29#");
		}
		else mainClient.getloginWindowController().okMsgCaller("Something went wrong, Please try again.");
	}

	//gets the approve from the server about deleting the permission.
	public void getdeletePermissionApproved(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true"))
		{
			mainClient.getloginWindowController().okMsgCaller("Permission successfully deleted from User.");
			mainClient.handleMessageFromClientUI("29#");
		}
		else mainClient.getloginWindowController().okMsgCaller("The user has an open request as a "+permissionToDelete+", Can't delete permission.");
	}

	/*
	 * IT choose report which report to show.
	 */
	public void openChosenReport(ActionEvent event) {
		if(chooseReportCBX.getValue()!=null) {
			switch (chooseReportCBX.getValue().toString()) {
			case "Activity":
				openActivityReport();
				break;
			case "Performance & Delays":
				openPerformanceReport();
					break;
			}
		}
		else mainClient.getloginWindowController().okMsgCaller("Please Choose Report to view");
	}
	
	/*
	 * opens the activity report.
	 */
	public void openActivityReport() {
		javafx.application.Platform.runLater(
				  () -> {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/ReportActivity.fxml").openStream());
			
			ReportActivityController reportActivityController = loader.getController();	
			mainClient.setReportActivityController(reportActivityController);
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
	 * open the performance report.
	 */
	public void openPerformanceReport() {
		javafx.application.Platform.runLater(
				  () -> {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/ReportPerformance.fxml").openStream());
			
			ReportPerformanceController reportPerformanceController = loader.getController();	
			mainClient.setReportPerformanceController(reportPerformanceController);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
				  });
	}
	
	//When "Choose information system" ComboBox clicked and the user chose one of the information systems in the list at Nomination Management tab
	public void getInformationSystemNameFromCBX(ActionEvent event)
	{
		txtRequestNumber.setText("");
		txtRole.setText("");
		txtRole2.setText("");
		itDepartmentManagerTable.setItems(null);
		activeRequestCBX.setItems(null);
		chooseRoleCBX.setItems(null);
		chooseEngineerCBX.setItems(null);
		chooseEngineerCBX2.setItems(null);
		txtInformationSystem.setText("");
		txtInformationSystem2.setText("");
		txtView.setText("");
		txtEngineer.setText("");
		txtEngineer2.setText("");
		txtEngineer3.setText("");
		mainClient.handleMessageFromClientUI("80#" + informationSystemCBX.getValue().toString());		
	}
		
	//Sets "Choose active request" ComboBox according to the choice of the user at "Choose information system" ComboBox at Nomination Management tab
	public void setChooseActiveRequestCBX(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			txtRequestNumber.setText("There are no active requests in this information system!");
			System.out.println("test80");
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
		}
	}
	
	//When "Choose active request" ComboBox clicked and the user chose one of the request numbers in the list
	public void getActiveRequestFromCBX(ActionEvent event)
	{
		txtRole.setText("");
		txtRole2.setText("");
		itDepartmentManagerTable.setItems(null);
		chooseRoleCBX.setItems(null);
		chooseEngineerCBX.setItems(null);
		chooseEngineerCBX2.setItems(null);
		txtRequestNumber.setText("");
		txtView.setText("");
		txtEngineer.setText("");
		txtEngineer2.setText("");
		txtInformationSystem2.setText("");
		txtEngineer3.setText("");
		if(activeRequestCBX.getValue() == null);
		else
			mainClient.handleMessageFromClientUI("81#" + activeRequestCBX.getValue().toString());		
	}
		
	//Sets "Choose a role" ComboBox(under "Add new nomination") according to the choice of the user at "Choose active request" ComboBox
	public void setChooseARoleCBX(ArrayList<String> msgFromServer)
	{
		chooseRoleCBX.setItems(null);
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test81");
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
		}
	}
	
	//When "View" button clicked, gets the request number the user chose at the ComboBox - "Choose active request" 
	public void viewButtonClicked(ActionEvent event)
	{
		txtView.setText("");
		txtRole.setText("");
		txtRole2.setText("");
		txtEngineer.setText("");
		txtEngineer2.setText("");
		txtInformationSystem2.setText("");
		txtEngineer3.setText("");
		if(activeRequestCBX.getValue() == null)
			txtRequestNumber.setText("Please choose a request number!");
		else
		{
			requestNumber = activeRequestCBX.getValue().toString();
			mainClient.handleMessageFromClientUI("83#" + activeRequestCBX.getValue().toString());
		}
	}
	
	//Sets the Nomination Management table with all the details of users and their roles at a specific request
	public void getRolesDetailsOfRequestNumber(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test83");
			txtView.setText("There is no nominations for this request yet!");
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

			itDepartmentManagerTable.setItems(data);
		}
	}
	
	//When "Choose a role" ComboBox clicked(under "Add new nomination") and the user chose one of the roles in the list
	public void getNewNominationRoleFromCBX(ActionEvent event)
	{
		chooseEngineerCBX.setItems(null);
		txtView.setText("");
		txtRole.setText("");
		txtRole2.setText("");
		txtEngineer.setText("");
		txtEngineer2.setText("");
		txtInformationSystem2.setText("");
		txtEngineer3.setText("");
		if(chooseRoleCBX.getValue() == null);
		else
			mainClient.handleMessageFromClientUI("84#" + activeRequestCBX.getValue() + "#" + chooseRoleCBX.getValue());		
	}
	
	//Sets "Choose engineer" ComboBox according to the choice of the user at "Choose a role" ComboBox(under "Add new nomination")
	public void setChooseEngineerCBX(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test84");
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
		}
	}
	
	//When "Choose engineer" ComboBox clicked and the user chose an Engineer
	public void chooseEngineerCBXClicked(ActionEvent event)
	{
		txtRole2.setText("");
		txtEngineer.setText("");
		txtEngineer2.setText("");
		txtInformationSystem2.setText("");
		txtEngineer3.setText("");
	}
	
	//When "Nominate" button clicked and the user chose a request number, a role and an engineer to nominate him to a specific role at the specific request
	public void nominateButtonClicked(ActionEvent event)
	{
		txtView.setText("");
		txtRole2.setText("");
		txtEngineer2.setText("");
		txtInformationSystem2.setText("");
		txtEngineer3.setText("");
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
			mainClient.handleMessageFromClientUI("85#" + activeRequestCBX.getValue().toString() + "#" + chooseRoleCBX.getValue().toString() + "#" + str2);
		}
	}
	
	//Gets a message of success from the server about the new nomination of a specific Engineer to a specific role at a specific request and pops-up a window with a message of success
	public void msgOfNewNomination(ArrayList<String> msgFromServer)
	{
		System.out.println("test 85 has " + msgFromServer.get(0));
		txtSuccessMsg.setText("Nominate succeeded!");
		okPain.setVisible(true);
	}
	
	//After the user clicked Nominate/Change and saw the message of success that popped-up and clicked "Ok" button it reopens the IT Department Manager HomePage at Nomination Management tab
	public void okButtonClicked(ActionEvent event)
	{
		try {
			txtSuccessMsg.getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/ITdepartment_sManagerHomepage.fxml").openStream());

			ITDepartmentManagerHomepageController itDepartmentManagerHomepageController = loader.getController();	
			mainClient.setiTDepartmentManagerHomepageController(itDepartmentManagerHomepageController);
					
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
		chooseEngineerCBX2.setItems(null);
		if (msgFromServer.get(0).equals("false")) {
			txtEngineer2.setText("There are no other engineers to choose!");
			System.out.println("test86");
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
		}
	}
	
	//When "Choose new engineer to set" ComboBox clicked and the user chose an Engineer
	public void chooseNewEngineerToSetCBXClicked(ActionEvent event)
	{
		txtEngineer2.setText("");
		txtRole2.setText("");
		txtInformationSystem2.setText("");
		txtEngineer3.setText("");
	}
	
	//When "Change" button clicked and the user chose a request number, a role and an engineer to replace with another engineer on a specific role at the specific request
	public void changeButtonClicked(ActionEvent event)
	{
		txtView.setText("");
		txtEngineer2.setText("");
		txtInformationSystem2.setText("");
		txtEngineer3.setText("");
		if(chooseEngineerCBX2.getValue() == null)
			txtEngineer2.setText("Please choose Engineer!");
		else if(activeRequestCBX.getValue() == null)
			txtRequestNumber.setText("Please choose a request number!");
		else if((itDepartmentManagerTable.getSelectionModel().getSelectedItem())!=null)
		{
			roleSelectedFromTable = itDepartmentManagerTable.getSelectionModel().getSelectedItem().getRole();
			userIDSelectedFromTable = itDepartmentManagerTable.getSelectionModel().getSelectedItem().getUserID();
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
			mainClient.handleMessageFromClientUI("87#" + activeRequestCBX.getValue().toString() + "#" + roleSelectedFromTable + "#" + str2 + "#" + userIDSelectedFromTable);
		}
	}
		
	//Gets a message of success from the server about the change of nomination of a specific Engineer to a specific role at a specific request
	public void msgOfChangeNomination(ArrayList<String> msgFromServer)
	{
		System.out.println("test 87 has " + msgFromServer.get(0));
		txtSuccessMsg.setText("Change succeeded!");
		okPain.setVisible(true);
	}
	
	//Sets "Choose information system" ComboBox with all the names of the information systems in the system that don't have nomination of responsible for maintenance yet at the "Nomination Management" tab
	public void getInformationSystemsToNominateResponsiblesForMaintenance(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test89");
			informationSystemCBX2.setVisible(false);
			chooseEngineerCBX3.setVisible(false);
			btnSet.setVisible(false);
			txtResponsiblesForMaintenance.setVisible(false);
		}
		else
		{
			int i;
			ArrayList<String> informationSystemsList = new ArrayList<>(); 

			System.out.println("return to Choose Information System CBX2 at Nomination Management Tab:"+msgFromServer.toString());
			informationSystemCBX2.setVisible(true);
			chooseEngineerCBX3.setVisible(true);
			btnSet.setVisible(true);
			txtResponsiblesForMaintenance.setVisible(true);
			for (i=0;i< msgFromServer.size();i++){
				informationSystemsList.add(msgFromServer.get(i));
				setInformationSystemsList2 = FXCollections.observableArrayList(informationSystemsList);

			} 
			System.out.println("Information Systems List in CBX2 at Nomination Management Tab:"+informationSystemsList.toString());	
			informationSystemCBX2.setItems((ObservableList<String>) setInformationSystemsList2);	
		}
	}
	
	//When "Choose information system" ComboBox clicked and the user chose one of the information systems in the list to Nominate responsible at Nomination Management tab
	public void getInformationSystemNameFromCBX2(ActionEvent event)
	{
		txtInformationSystem2.setText("");
		mainClient.handleMessageFromClientUI("90#" + informationSystemCBX2.getValue().toString());
	}
	
	//Sets "Choose engineer" ComboBox according to the choice of the user at "Choose information system" ComboBox(under "Add new nomination")
	public void setChooseEngineerCBX2(ArrayList<String> msgFromServer)
	{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test90");
		}
		else
		{
			int i;
			ArrayList<String> engineerList = new ArrayList<>(); 
				
			System.out.println("return to Choose engineer CBX3:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size()-1;i=i+3){
				engineerList.add(msgFromServer.get(i) + " " + msgFromServer.get(i+1) + " " + msgFromServer.get(i+2));
				engineersList3 = FXCollections.observableArrayList(engineerList);
			} 
			chooseEngineerCBX3.setItems((ObservableList<String>) engineersList3);
		}
	}
	
	//When "Set" button clicked and the user chose information system, and an engineer to nominate him as responsible for support and maintenance in this information system
	public void setButtonClicked(ActionEvent event)
	{
		txtInformationSystem2.setText("");
		txtEngineer3.setText("");
		if(chooseEngineerCBX3.getValue() == null)
			txtEngineer3.setText("Please choose Engineer!");
		else
		{
			String str = chooseEngineerCBX3.getValue();
			String str2 = "";
			String ch = "" ;
			for(int i=0; i<str.length()-1; i++)
			{
				ch = str.substring(i, i+1);
				if(!(ch.equals(" ")))
					str2 = str2 + ch;
				else i = str.length()-1;
			}
			mainClient.handleMessageFromClientUI("91#" + informationSystemCBX2.getValue().toString() + "#" + str2);
		}
	}
		
	//Gets a message of success from the server about the new nomination of a specific Engineer to a specific information system to be in charge of support and maintenance, and pops-up a window with a message of success
	public void msgOfNewResponsibleEngineerSet(ArrayList<String> msgFromServer)
	{
		System.out.println("test 91 has " + msgFromServer.get(0));
		txtSuccessMsg.setText("Set succeeded!");
		okPain.setVisible(true);
	}
	
	
	public void getITRequestsDelay(ArrayList<String> msgFromServer) {
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
		}	textFieldRequestsInDelay.setEditable(false);
	}
	
	public boolean currentDateChecker(String date2) {// check that chosen date(in datepicker is in from today on
		LocalDate date = LocalDate.parse(date2);
		if (ChronoUnit.DAYS.between(LocalDate.now(), date) >= 0)
			return true;
		return false;
	}

	public void getITRequestsExtensions(ArrayList<String> msgFromServer) {
		String requests="";
		if(!msgFromServer.get(0).equals("false")) {
			for(int i=0;i<msgFromServer.size();i=i+3) {
					requests= requests+("Request number "+msgFromServer.get(i)+" at phase "+msgFromServer.get(i+1)+" got extension to "+msgFromServer.get(2)+".\n");
			}
			textFieldRequestExtension.setText(requests);
			textFieldRequestExtension.setEditable(false);
		}
	}
}
