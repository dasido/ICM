package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.EchoClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FieldChoiceController implements Initializable{

	private EchoClient mainClient;
	ObservableList<String> list;
	public static String userRoleChoice;
	
	@FXML
	private Button btnBack;
	
	@FXML
	private ComboBox<String> fieldChoiceCBX;
	
	@FXML
	private Text txtError;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mainClient = loginWindowController.mainClient;
		mainClient.handleMessageFromClientUI("8#" + loginWindowController.user.getuserID());
	}
	/*
	*After the user clicked Senior Options, it sets the ComboBox "Choose senior role" with all the roles this specific user have
	*/
	public void setFieldChoiceCBXForUser(ArrayList<String> msgFromServer) {
		
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test8");
		}
		else
		{
			ArrayList<String> requestNumberList = new ArrayList<>(); 

			System.out.println("return to fieldChoiceCBX:"+msgFromServer.toString());

			if(msgFromServer.get(0).equals("1"))
				requestNumberList.add("ITdepartmentManager");
			if(msgFromServer.get(1).equals("1"))
				requestNumberList.add("Inspector");
			if(msgFromServer.get(2).equals("1"))
				requestNumberList.add("Assessor");
			if(msgFromServer.get(3).equals("1"))
				requestNumberList.add("Chairman");
			if(msgFromServer.get(4).equals("1"))
				requestNumberList.add("CommitteeMember");
			if(msgFromServer.get(5).equals("1"))
				requestNumberList.add("Examiner");
			if(msgFromServer.get(6).equals("1"))
				requestNumberList.add("ExecutionLeader");
			list = FXCollections.observableArrayList(requestNumberList);

			System.out.println("requestsvector in fieldChoiceCBX:"+requestNumberList.toString());	
			fieldChoiceCBX.setItems((ObservableList<String>) list);
		}
	}
	/*
	*If the user clicked Go to work button without choosing a role, it will pop-up a message for him and ask to choose a roll
	*If the user chose a roll it will open the relevant HomePage according to the roll he chose to work as
	*/
	public void goToWorkClicked(ActionEvent event)
	{
		System.out.println("searchFieldChoiceCBX: result: " + fieldChoiceCBX.getValue());
		if(fieldChoiceCBX.getValue()==null)
			txtError.setText("Please choose a senior role first!");
		
		else if(fieldChoiceCBX.getValue().equals("ITdepartmentManager"))
		{
			try {
				userRoleChoice = "ITdepartmentManager";
				btnBack.getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxmls/ITdepartment_sManagerHomepage.fxml").openStream());

				ITDepartmentManagerHomepageController iTDepartmentManagerHomepageController = loader.getController();	
				mainClient.setiTDepartmentManagerHomepageController(iTDepartmentManagerHomepageController);
					
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}		}
		
		else if(fieldChoiceCBX.getValue().equals("Inspector"))
		{
			try {
				userRoleChoice = "Inspector";
				btnBack.getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxmls/InspectorHomepage.fxml").openStream());

				InspectorHomepageController inspectorHomepageController = loader.getController();	
				mainClient.setInspectorHomepageController(inspectorHomepageController);
					
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
		}
		
		//If the user chose in the ComboBox of FieldChoice page to work as Assessor
		else if(fieldChoiceCBX.getValue().equals("Assessor"))
		{
			try {
				userRoleChoice = "Assessor";
				btnBack.getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxmls/AssessorHomepage.fxml").openStream());

				AssessorHomepageController assessorHomepageController = loader.getController();	
				mainClient.setAssessorHomepageController(assessorHomepageController);
					
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
				}
				catch (Exception e3) {
					e3.printStackTrace();
				}
		}
		
		//If the user chose in the ComboBox of FieldChoice page to work as Chairman or MemberOfCommittee
		else if(fieldChoiceCBX.getValue().equals("Chairman") || fieldChoiceCBX.getValue().equals("CommitteeMember"))
		{
			try {
				if(fieldChoiceCBX.getValue().equals("Chairman"))
					userRoleChoice = "Chairman";
				else 
					userRoleChoice = "CommitteeMember";
				btnBack.getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxmls/Chairman_MemberOfCommitteeInterface.fxml").openStream());

				MemberOfCommitteeOrChairmanHomepageController memberOfCommitteeOrChairmanHomepageController = loader.getController();	
				mainClient.setMemberOfCommitteeOrChairmanHomepageController(memberOfCommitteeOrChairmanHomepageController);
				
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
				}
				catch (Exception e4) {
					e4.printStackTrace();
				}
		}
		
		//If the user chose in the ComboBox of FieldChoice page to work as Execution Leader
		else if(fieldChoiceCBX.getValue().equals("ExecutionLeader"))
		{
			try {
				userRoleChoice = "ExecutionLeader";
				btnBack.getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxmls/ExecutionLeaderHomePage.fxml").openStream());

				ExecutionLeaderHomepageController executionLeaderHomepageController = loader.getController();	
				mainClient.setExecutionLeaderHomepageController(executionLeaderHomepageController);
					
				Scene scene = new Scene(root,770,650);
				primaryStage.setScene(scene);
				primaryStage.show();
				}
				catch (Exception e5) {
					e5.printStackTrace();
				}
		}
		
		//If the user chose in the ComboBox of FieldChoice page to work as Examiner
		else if(fieldChoiceCBX.getValue().equals("Examiner"))
		{
			try {
				userRoleChoice = "Examiner";
				btnBack.getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxmls/ExaminerHomepage.fxml").openStream());

				ExaminerHomepageController examinerHomepageController = loader.getController();	
				mainClient.setExaminerHomepageController(examinerHomepageController);
					
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
				}
				catch (Exception e6) {
					e6.printStackTrace();
				}
		}
	}
	
	
	/*
	*If the User clicks Back button, it opens the User HomePage page
	 */
	public void backClicked(ActionEvent event)
	{
		try {
			btnBack.getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/UserHomePage.fxml").openStream());

			UserHomePageController userHomePageController = loader.getController();	
			mainClient.setUserHomePageController(userHomePageController);
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
	}
}
