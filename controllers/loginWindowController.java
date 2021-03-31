package controllers;

import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.EchoClient;
import entities.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class loginWindowController implements Initializable {

	@SuppressWarnings("unused")
	private loginWindowController loginWindowController;
	public static EchoClient mainClient;
	final public static int DEFAULT_PORT = 5555;
	public static User user;
	private String username;
	private String password;
	@FXML
	private Text txtError;
	@FXML
	private Button logIn;
	@FXML
	private TextField userNameTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML 
	private Text txtValidPassword;
	@FXML 
	private Text txtValidUsername;
	

	//Exits the system when X clicked
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("Exit");
		System.exit(0);
	}

	/**Gets the UserName and Password that the user entered and send it to server for approval
	 * or change error messages to the screen
	 * @param event
	 */
	public void logInToSystem(ActionEvent event) {
		username = userNameTextField.getText();
		System.out.println(username);
		password = passwordTextField.getText();
		System.out.println(password);
		if(username.equals("")||password.equals("")) {
			if(username.equals(""))
				txtValidUsername.setVisible(true);
			else
				txtValidUsername.setVisible(false);
			if(password.equals(""))
				txtValidPassword.setVisible(true);
			else 
				txtValidPassword.setVisible(false);
		}
		else
		mainClient.handleMessageFromClientUI("1#" + username + "#" + password);
	}

	/**Gets a message from server about the login info entered 
	 * 
	 * @param msgFromServer
	 */
	public void checkLogIn(ArrayList<String> msgFromServer) {
		//If UserName or Password is incorrect let the user know about it and ask to try again
		if (msgFromServer.get(0).equals("false")) 
		{
			txtValidPassword.setVisible(false);
			txtValidUsername.setVisible(false);
			this.txtError.setText("UserName or Password is incorrect, Please try again!");
		} 
		
		//If the user is already logged in let the user know about it
		else if (msgFromServer.get(8).equals("1"))
		{
			this.txtError.setText("This User is already logged in");
		}
		
		//If the UserName and Password is correct and the user is not logged in, login the user and opens the User HomePage page
		else {
			System.out.println(msgFromServer.get(0));
			System.out.println("Successfully connected to the system");
			user = new User(msgFromServer.get(0), msgFromServer.get(1), msgFromServer.get(2), msgFromServer.get(3),
					msgFromServer.get(4), msgFromServer.get(5), msgFromServer.get(6), msgFromServer.get(7),
					msgFromServer.get(8));
			Platform.runLater(()->{
				try {
					userNameTextField.getScene().getWindow().hide();
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					AnchorPane root = loader.load(getClass().getResource("/fxmls/UserHomePage.fxml").openStream());
					
					UserHomePageController userHomePageController = loader.getController();	
					mainClient.setUserHomePageController(userHomePageController);
					
					
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle("ICM");
					primaryStage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

	/**
	 * prepering the fxml to initialize
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
     txtValidPassword.setVisible(false);
     txtValidUsername.setVisible(false);
		try {
			mainClient = new EchoClient(ReceiveIpController.ip, DEFAULT_PORT);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mainClient.setloginWindowController(this);

	}
	
	/**
	 * print to the current user 
	 * @param msg
	 */
	public void okMsgCaller(String msg) {
		Platform.runLater(()->{
			try {
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				AnchorPane root = loader.load(getClass().getResource("/fxmls/OkMsg.fxml").openStream());
			
				OkMsgController okMsgController = loader.getController();	
				mainClient.setOkMsgController(okMsgController);
				okMsgController.setTextMsg(msg);
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Message");
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
