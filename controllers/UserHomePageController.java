package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import entities.Request;
import client.EchoClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserHomePageController implements Initializable {

	@SuppressWarnings("unused")
	private UserHomePageController UserHomePageController;
	private EchoClient mainClient;
	final public static int DEFAULT_PORT = 5555;
	public static String userOrAssessorOrIt;
	ObservableList<String> list;
	Vector<Request> requestsvector = new Vector<>();
	Request theCorrectChoice;
	
	@FXML
	private ComboBox<String> userCBX;
	
	@FXML
	private Button seniorOptions;
	
	@FXML
	private Text txtWelcome;
	
	@FXML
	private Button logOut;
	
	@FXML
	private Text txtError;
	
	@FXML
	private TextField txtFinishDateEstimation;
	
	@FXML
	private TextField txtCurrentPhase;
	
	@FXML
	private TextField txtStatus;
	
	@FXML
	private Text txtFinishDate;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userOrAssessorOrIt="User";
		this.txtWelcome.setText("Welcome "+loginWindowController.user.getfullName()+"");
		mainClient = loginWindowController.mainClient;
		mainClient.handleMessageFromClientUI("2#" + loginWindowController.user.getuserID());
		if(loginWindowController.user.getEngineer().equals("0"))disableSenior();	
	}
	
	/*
	 * Sets the user ComboBox with all the requests he opened
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setUserCBX(ArrayList<String> msgFromServer) {
		Platform.runLater(
				()->{
		if (msgFromServer.get(0).equals("false")) {
			System.out.println("test1");
		}
		else
		{
			int i;
			ArrayList<String> requestNumberList = new ArrayList<>(); 

			System.out.println("return to CBX:"+msgFromServer.toString());

			for (i=0;i< msgFromServer.size();i=i+12){
				Request r= new Request(msgFromServer.get(i),msgFromServer.get(i+1),msgFromServer.get(i+2),msgFromServer.get(i+3),msgFromServer.get(i+4),msgFromServer.get(i+5),msgFromServer.get(i+6),msgFromServer.get(i+7),msgFromServer.get(i+8),msgFromServer.get(i+9),msgFromServer.get(i+10),msgFromServer.get(i+11));
				requestsvector.add(r);
				requestNumberList.add(r.getRNumber()+ " " +r.getInformationSystemName());
				list = FXCollections.observableArrayList(requestNumberList);

			} 
			System.out.println("requestsvector in CBX:"+requestsvector.toString());	
			userCBX.setItems((ObservableList) list);
		}
				});
	}
	
	/*
	 * Gets the details from the choice that the user made in the ComboBox
	 */
	public void getDetailsOfUserCBX(ActionEvent e1) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		System.out.println("searchCBX");
		getUserRequestDetails(userCBX.getValue().toString());
	}
	
	/*
	 * Gets the details from getDetailsOfUser and save the request that equals to it
	 */
	//Gets the details from getDetailsOfUser and save the request that equals to it
		//Tells the user to choose a request from CB if he chose nothing, otherwise send a message to server to get the details of the request
		public void getUserRequestDetails(String option) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
			System.out.println(option + " option");
			for(Request r : requestsvector) {
				if((r.getRNumber()+ " " +r.getInformationSystemName()).equals(option))
					theCorrectChoice = r;
			}		
			System.out.println("the correct choice:"+theCorrectChoice.toString());
			
			if(userCBX.getValue()==null) {
				this.txtError.setText("Please choose a Request Number from the list.");
				return;
			}
			
			else
				mainClient.handleMessageFromClientUI("3#" + theCorrectChoice.getRNumber());
		}
	
	/*
	 * Tells the user to choose a request from CB if he chose nothing, otherwise send a message to server to get the details of the request
	 */
	public void searchUserInfo(ActionEvent event) throws Exception {   
		if(userCBX.getValue()==null) {
			this.txtError.setText("Please choose a Request Number from the list.");
			return;
		}
		
		else
			mainClient.handleMessageFromClientUI("3#" + theCorrectChoice.getRNumber());
		
	}
	
	/*
	 * Update the specific request class if needed and shows to user the details about the request he chose in ComboBox 
	 */
		public void showUserRequestInfo(ArrayList<String> msgFromServer)
		{
			for(Request r : requestsvector) {
				if((r.getRNumber().equals(msgFromServer.get(0))))
				{
					r.setTreatedBy(msgFromServer.get(7));
					r.setStatus(msgFromServer.get(8));
					r.setCurrentPhase(msgFromServer.get(9));
					r.setStartDate(msgFromServer.get(10));
					r.setFinishDate(msgFromServer.get(11));
				}
			}		
			txtStatus.setText(theCorrectChoice.getStatus());
			txtCurrentPhase.setText(theCorrectChoice.getCurrentPhase());
			if(theCorrectChoice.getFinishDate() != null)
				txtFinishDateEstimation.setText(theCorrectChoice.getFinishDate());
			else
			{
				txtFinishDate.setText("There is no finish date for this phase yet!");
				txtFinishDateEstimation.setText(null);
			}
		}
	
	/*
	 * When Open new change request button clicked opens OpenChangeRequest page
	 */
	public void openNewChangeRequestPage(ActionEvent event) {
		try {
		seniorOptions.getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxmls/OpenChangeRequest.fxml").openStream());
		
		OpenChangeRequestController openChangeRequestController = loader.getController();	
		mainClient.setOpenChangeRequestController(openChangeRequestController);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		}
		catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	/*
	 * When Requests to close button clicked opens ApproveClosingARequest page
	 */
	public void openApproveClosingARequestPage(ActionEvent event) {
		try {
			seniorOptions.getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/ApproveClosingARequest.fxml").openStream());
			
			ApproveClosingARequestController approveClosingARequestController = loader.getController();	
			mainClient.setApproveClosingARequestController(approveClosingARequestController);
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			}
			catch (Exception e3) {
				e3.printStackTrace();
			}
	}
	
	/*
	 * When Logout button clicked opens loginWindow page and send a message to server to update the login status of this user
	 */
	public void logout(ActionEvent e) throws Exception {
		logOut.getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxmls/loginWindow.fxml").openStream());
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		mainClient.handleMessageFromClientUI("5#" + loginWindowController.user.getuserID());
	}
	
	/*
	 * Gets a message from server after login status updated and update the login status of the user class
	 */
	public void updateAfterLogout(ArrayList<String> msgFromServer)
	{
		System.out.println(msgFromServer.get(0));
		if(msgFromServer.get(0).equals("updated"))
			loginWindowController.user.setLogInStatus("0");
		System.out.println(loginWindowController.user.getLogInStatus());
	}
	
	/*
	 * When the user that have permissions to click the button Senior Options clicks it, it opens FieldChoice page
	 */
	public void seniorOptionsClicked(ActionEvent event)
	{
		try {
			seniorOptions.getScene().getWindow().hide(); // hiding primary window
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
	
	/*
	 * Hide the Senior options button for those who doesn't have more than user permissions
	 */
	public void disableSenior() 
	{
		seniorOptions.setVisible(false);
	}

	/*
	 * Setter of requests vector
	 */
	public void setRequestsVector(Vector<Request> requestsvector)
	{
		this.requestsvector = requestsvector;
	}
	
	/*
	 * Getter of request vector
	 */
	public Vector<Request> getRequestsVector()
	{
		return requestsvector;
	}
}