package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.EchoClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ExtraInfoForRequestForTheCommitteeController implements Initializable {
	private EchoClient mainClient;
	
	@FXML
	private TextArea txtAreaMain;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnSend;
	/**
	 * prepare the fxml to initialize
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		btnSend.setVisible(false);
		mainClient = loginWindowController.mainClient;
		mainClient.handleMessageFromClientUI("60#"+mainClient.getAssessorHomepageController().getRNumber());
		txtAreaMain.setEditable(false);
	}
	
	/**
	 * hide the etra info for the committee window and return to prevoius fxml
	 * @param event
	 */
	public void btnBackFunc(ActionEvent event) {
		btnBack.getScene().getWindow().hide(); // hiding primary window
	}
	
	/**
	 * fill the extra information to the main text in the fxml
	 * @param msgFromServer
	 */
	public void infoToSet(ArrayList<String> msgFromServer) {
		txtAreaMain.setText(msgFromServer.get(0));
	}
	/**\
	 * another back button with func
	 * @param event
	 */
	public void backToAssessor(ActionEvent event) {
		btnBack.getScene().getWindow().hide();
	}
	
	
}
