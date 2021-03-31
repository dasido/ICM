package controllers;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import client.EchoClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class TimeExtensionController {
	private EchoClient mainClient= loginWindowController.mainClient;
	private String user;
	private String approvedDate;
	@FXML
	private Button btnSend;
	
	@FXML
	private Button btnCancel;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private TextArea textReason;
	
	@FXML
	private Text validDate;
	
	@FXML
	private Text validReason;
	
	/*
	 * Before sending the time extensions this function checks if the details entered by the user are ok.
	 * then it sends the time extension request to server.
	 */
	public void sendTimeExtension(ActionEvent event) {
		validDate.setVisible(false);
		validReason.setVisible(false);
		if(datePicker.getValue()==null) 
		{
			validDate.setVisible(true);
			validDate.setText("Please Pick up a date.");
		}
		if(textReason.getText().equals("")) 
		{
			validReason.setVisible(true);
			validReason.setText("Please enter a reason");
		}
		if(datePicker.getValue()!=null) 
		if(!daysBetweenApproveCalc(datePicker.getValue().toString())) {
			validDate.setVisible(true);
			validDate.setText("You must select a date later than your approved date - "+ approvedDate);
		}
		else {
		if(datePicker.getValue()!=null)
		{
			if(!textReason.getText().equals("")) 
			{
			if(user.equals("Execution"))
				mainClient.handleMessageFromClientUI("24#"+mainClient.getExecutionLeaderHomepageController().getRNumber()+"#"+ datePicker.getValue().toString()+"#"+textReason.getText());
			if(user.equals("Examiner"))
				mainClient.handleMessageFromClientUI("26#"+mainClient.getExaminerHomepageController().getRNumber()+"#"+ datePicker.getValue().toString()+"#"+textReason.getText());
			if(user.equals("Chairman"))
				mainClient.handleMessageFromClientUI("33#"+mainClient.getMemberOfCommitteeOrChairmanHomepageController().getRNumber()+"#"+ datePicker.getValue().toString()+"#"+textReason.getText());
			btnSend.getScene().getWindow().hide();
			if(user.equals("Assessor")) 
				mainClient.handleMessageFromClientUI("61#"+mainClient.getAssessorHomepageController().getRNumber()+"#"+datePicker.getValue().toString()+"#"+textReason.getText());
			}
			else {
				validReason.setVisible(true);
				validReason.setText("Please enter a reason");
			}
			}
		else {
			validDate.setVisible(true);
			validDate.setText("Please Pick up a date.");
		}
		}
	}
	
	/*
	 * Cancel and gets out of the time extension window.
	 */
	public void cancelBtnFunc(ActionEvent event) {
		btnSend.getScene().getWindow().hide();
	}
	
	/*
	 * setting the user who gets into the time extension window.
	 */
	public void setUser(String user) {
		this.user=user;
	}
	
	/*
	 * this function checks that the time extension chosen date is after the approved date given to the  user. 
	 */
	public boolean daysBetweenApproveCalc(String date2) {
		LocalDate date = LocalDate.parse(date2);
		LocalDate date1 = null;
		if(user.equals("Execution"))
		{
				date1 = LocalDate.parse(mainClient.getExecutionLeaderHomepageController().getApprovedDate());
				approvedDate=mainClient.getExecutionLeaderHomepageController().getApprovedDate();
		}
		if(user.equals("Examiner"))
		{
				date1 = LocalDate.parse(mainClient.getExaminerHomepageController().getApprovedDate());
				approvedDate=mainClient.getExaminerHomepageController().getApprovedDate();
		}
		if(user.equals("Chairman"))
		{
				date1= LocalDate.parse(mainClient.getMemberOfCommitteeOrChairmanHomepageController().getApprovedDate());
				approvedDate=mainClient.getMemberOfCommitteeOrChairmanHomepageController().getApprovedDate();
		}
		if(user.equals("Assessor")) {
			date1= LocalDate.parse(mainClient.getAssessorHomepageController().getApprovedDate());
			approvedDate=mainClient.getAssessorHomepageController().getApprovedDate();
		}
		
	if(ChronoUnit.DAYS.between(date1, date)>0) 
			return true;
	return false;
	}
}
