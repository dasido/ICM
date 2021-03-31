package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import client.EchoClient;
import entities.Request;
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

public class ApproveClosingARequestController implements Initializable{

	private EchoClient mainClient;
	ObservableList<String> list;
	
	@FXML
	private ComboBox<String> closingCBX;
	
	@FXML
	private Text txtError;
	
	@FXML
	private TextField txtReasonForClosing;
	
	@FXML
	private Text txtSuccessMsg;
	
	@FXML
	private Text txtFail;
	
	@FXML
	private Pane okPain;
	
	@FXML
	private Button okButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainClient = loginWindowController.mainClient;
		System.out.println("Sent to server case 4: "+ loginWindowController.user.getuserID());
		mainClient.handleMessageFromClientUI("4#" + loginWindowController.user.getuserID());
	}
	/*
	 *Fill the ComboBox with the requests that still need to be approved for closing by the user
	 */
	public void setApproveClosingARequestCBX(ArrayList<String> msgFromServer)
	{
		UserHomePageController userHomePageController = mainClient.getUserHomePageController();
		Vector<Request> requestsvector = userHomePageController.getRequestsVector();
		int i;
		for (i=0;i< msgFromServer.size();i=i+12){
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
		}
		ArrayList<String> requestNumberList = new ArrayList<>();
		for (i=0;i< msgFromServer.size()-1;i=i+12){
			requestNumberList.add(msgFromServer.get(i) + " " + msgFromServer.get(i+2));
			list = FXCollections.observableArrayList(requestNumberList);
		}
		closingCBX.setItems((ObservableList<String>) list);
	}
	/*
	*If the user clicked the ComboBox and didn't choose a request a message will pop-up and tell him to choose a request
	*When the user choosing a request from ComboBox it gets the request number thats fit to it and send it to server to get the reason for closing
	*/
	public void getDetailsOfUserRequestToCloseCBX(ActionEvent event) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		System.out.println("searchClosingCBX");
		UserHomePageController userHomePageController = mainClient.getUserHomePageController();
		Vector<Request> requestsvector = userHomePageController.getRequestsVector();
		if(closingCBX.getValue()==null) {
			this.txtError.setText("Please choose a Request Number from the list.");
		}
		else
		{
			for(Request r : requestsvector) {
				if((r.getRNumber()+ " " +r.getInformationSystemName()).equals(closingCBX.getValue().toString()))
					mainClient.handleMessageFromClientUI("6#" + r.getRNumber());
			}		
		}
	}
	
	//Gets a message from server with the reason for closing about the request the user chose in ComboBox
	public void getUserRequestDetails(ArrayList<String> msgFromServer) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.out.println(msgFromServer.get(0));
		txtReasonForClosing.setText(msgFromServer.get(0));
	}
	/*
	*When the user is clicking Approve it sends to the server the request number that fits to the request number that the user chose in the ComboBox to approve closing
	*to update the user approval field and change the request status if the inspector approved the closing of the same request as well
	*/
	public void userApprovedARequestToClose(ActionEvent event)
	{
		System.out.println("User Clicked Approve");
		UserHomePageController userHomePageController = mainClient.getUserHomePageController();
		Vector<Request> requestsvector = userHomePageController.getRequestsVector();
		for(Request r : requestsvector) {
			if(closingCBX.getValue()!=null && (r.getRNumber()+ " " +r.getInformationSystemName()).equals(closingCBX.getValue().toString()))
				mainClient.handleMessageFromClientUI("7#" + r.getRNumber());
		}
		if(closingCBX.getValue()==null)
			this.txtFail.setText("Please choose a request to close first!");
	}
	/*
	 *After the user clicked approved getting a message from server if the request status of the request the user chose in the ComboBox changed, and if it got changed update it in the request class
	 *and also pop-ups to the user a message that approve succeeded 
	 */
	public void updateUserRequestStatus(ArrayList<String> msgFromServer)
	{
		System.out.println(msgFromServer.get(0));
		UserHomePageController userHomePageController = mainClient.getUserHomePageController();
		Vector<Request> requestsvector = userHomePageController.getRequestsVector();
		for(Request r : requestsvector) {
			if((r.getRNumber()+ " " +r.getInformationSystemName()).equals(closingCBX.getValue().toString()))
				if(!(msgFromServer.get(0).equals(r.getStatus())))
				{
					r.setStatus(msgFromServer.get(0));
					System.out.println(r.getStatus());
				}
		}
		
		txtSuccessMsg.setText("Approve succeeded!");
		this.okPain.setVisible(true);
	}
	/*
	*After the user clicked approved and saw the message of success that popped-up and clicked "Ok" button it reopens the ApproveClosingARequest page and delete the specific request from ComboBox
	*/
	public void okButtonClicked(ActionEvent event)
	{
		try {
			txtSuccessMsg.getScene().getWindow().hide(); // hiding primary window
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
	*If the user decided to go back to User HomePage and pressed Cancel
	*/
	public void cancelClicked(ActionEvent event)
	{
		try {
			txtError.getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/UserHomePage.fxml").openStream());

			UserHomePageController userHomePageController = loader.getController();	
			mainClient.setUserHomePageController(userHomePageController);
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
	}
}
