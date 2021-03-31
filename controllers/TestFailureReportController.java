package controllers;

import client.EchoClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class TestFailureReportController {
	
	private EchoClient mainClient = loginWindowController.mainClient;
	@FXML
	private Button btnOk;
	
	@FXML
	private Button btnBack;

	@FXML
	private Button btnSave;
	
	@FXML
	private TextArea textAreaTest;
	
	@FXML
	private Text textHead;
	
	@FXML
	private Text textFailure;
	
	@FXML
	private Text textError;
	/*
	 * Sets the page for execution leader.
	 */
	public void testForExecutionLeader(String msg) {
		btnBack.setVisible(false);
		btnSave.setVisible(false);
		textAreaTest.setText(msg);
		textHead.setText("Test failure report");
	}
	/*
	 * Sets the page for execution Examiner
	 */
	public void testForExaminer() {
		btnOk.setVisible(false);
		textFailure.setText("Please enter Failure description:");
	}
	/*
	 * Returns to the previous page.
	 */
	public void btnOkFunc(ActionEvent event) {
		btnSave.getScene().getWindow().hide();
	}
	/*
	 * gets back to the examiner page without do anything.
	 */
	public void btnBackFunc(ActionEvent event) {
		btnSave.getScene().getWindow().hide();
	}
	/*
	 * Examiner Sends test failure to server back to the execution leader.
	 */
	public void btnSaveFunc(ActionEvent event) {
		if(textAreaTest.getText().equals(""))
			textError.setVisible(true);
		else {
			mainClient.handleMessageFromClientUI("27#"+mainClient.getExaminerHomepageController().getRNumber()+"#"+textAreaTest.getText());
			btnSave.getScene().getWindow().hide();
			 }
	}
}
