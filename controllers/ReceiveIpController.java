package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ReceiveIpController {

	public static String ip;
	
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private TextField ipTextField;
	
	@FXML
	private Button btnOk;
	
	//Opens ReceiveIp page
	@SuppressWarnings("unused")
	public void start(Stage primaryStage) {
		ReceiveIpController controller;//the screen controller
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxmls/ReceiveIp.fxml"));
			anchorPane = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}		
		Scene scene = new Scene(anchorPane);
		primaryStage.setTitle("Connect to IP address");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	//Gets the ip of the server that the user entered and opens Ok button
	@FXML
	public void receiveIpText(ActionEvent e)
	{
		ip = ipTextField.getText();
		System.out.println(ip);
		btnOk.setDisable(false);
	}
	
	//Opens loginWindow Page
	public void okAction (ActionEvent event) throws IOException
	{
		btnOk.getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/fxmls/loginWindow.fxml"));
			Scene scene = new Scene(root,850,450);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
