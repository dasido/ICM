package controllers;

import java.util.ArrayList;

import client.EchoClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class ExtraInfoController {

	EchoClient mainClient = loginWindowController.mainClient;
	@FXML
	private Button btnBack;
	
	@FXML
	private Button btnSend;
	
	@FXML
	private Text textHeadLine;
	
	@FXML
	private Text textExtraInfo;
	
	@FXML
	private TextArea textAreaInfo;
	
	/*
	 * Hide buttons for chairman entrance.
	 */
	public void setForChairman() {
		textExtraInfo.setVisible(false);
	}
	
	/*
	 * Send extra info request to server. it should get to the assessor that assessed this request.
	 */
	public void sendExtraInfo (ActionEvent event) {
		if(textAreaInfo.getText().equals(""))
			textExtraInfo.setVisible(true);
		else
		{
			textExtraInfo.setVisible(false);
			mainClient.handleMessageFromClientUI("41#"+mainClient.getMemberOfCommitteeOrChairmanHomepageController().getRNumber()+"#"+textAreaInfo.getText());
		}
	}
	
	/*
	 * Gets approval from the server about the more info request sending.
	 */
	public void getAskMoreInfoApproval(ArrayList<String> msgFromServer) {
		if(msgFromServer.get(0).equals("true"))
			{
			mainClient.getloginWindowController().okMsgCaller("More information request sent to Assessor.");
			Platform.runLater(
					  () -> {
			btnBack.getScene().getWindow().hide();
					  });
			}	
		else mainClient.getloginWindowController().okMsgCaller("More information request didn't send, please try agian");
	}

	/*
	 * Gets back to the chairman home page.
	 */
	public void getBack(ActionEvent event) {
		btnBack.getScene().getWindow().hide();
	}
}
