
package controllers;


import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.EchoClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class NewEvaluationReportByAssessorController implements Initializable{
	private EchoClient mainClient = loginWindowController.mainClient;
	@FXML
	private Button btnSend;
	@FXML
	private Button btnBack;
	@FXML
	private TextField txtChangeLocation;
	@FXML
	private TextArea txtChangeDescription;
	@FXML
	private TextArea txtChangeAdvantages;
	@FXML
	private TextArea txtChangeConstraintsAndRisks;
	@FXML
	private DatePicker txtExecutionDuration;
	@FXML
	private Text fillChangeLocation;
	@FXML
	private Text fillChangeDescription;
	@FXML
	private Text fillChangeAdvantages;
	@FXML
	private Text fillExecutionDuration;
	@FXML
	private Text fillConsAndRisks;
	
	
	


	
	/**this method is disable error texts that will be enabled in the begining of the stage(in sendEvaluationProperties)
	 * 
	 */
	public void errMsgHide() {
		fillChangeLocation.setVisible(false);
		fillChangeDescription.setVisible(false);
		fillChangeAdvantages.setVisible(false);
		fillConsAndRisks.setVisible(false);
		fillExecutionDuration.setVisible(false);
	}
	
	
	/**
	 * take the fields and send message to the server or put errors messages
	 * @param event
	 * @throws Exception
	 */
	public void sendEvaluationReport() {
		 errMsgHide();
		 if(txtChangeAdvantages.getText().equals(""))
			 fillChangeAdvantages.setVisible(true);
		 if(txtChangeConstraintsAndRisks.getText().equals(""))
			 fillConsAndRisks.setVisible(true);
		 if(txtChangeDescription.getText().equals(""))
			 fillChangeDescription.setVisible(true);
		 if(txtChangeLocation.getText().equals(""))
			 fillChangeLocation.setVisible(true);
		 if(txtExecutionDuration.getValue()==null)
		 {
			 fillExecutionDuration.setVisible(true);
			 fillExecutionDuration.setText("Please choose a valid date");
		 }
		 else if(!currentDateChecker(txtExecutionDuration.getValue().toString()))
		 {
			 fillExecutionDuration.setVisible(true);
			 fillExecutionDuration.setText("Please choose a date from today and on.");
		 }
		 else if(!txtChangeLocation.getText().equals("") && !txtChangeDescription.getText().equals("") && !txtChangeConstraintsAndRisks.getText().equals("")&&!txtChangeAdvantages.getText().equals(""))
		 {
        	mainClient.handleMessageFromClientUI("31#"+mainClient.getAssessorHomepageController().getRNumber()+("#") +txtChangeLocation.getText()+("#")+txtChangeDescription.getText()+("#")+txtChangeAdvantages.getText()+("#")+txtChangeConstraintsAndRisks.getText()+("#")+txtExecutionDuration.getValue().toString());
        	System.out.println("31#"+mainClient.getAssessorHomepageController().getRNumber()+("#") +txtChangeLocation.getText()+("#")+txtChangeDescription.getText()+("#")+txtChangeAdvantages.getText()+("#")+txtChangeConstraintsAndRisks.getText()+("#")+txtExecutionDuration.getValue().toString());
		 }
	 }
                    
					
	
	
	/**
	 * get a message from the server to know if evaluation approved and close evaluation fxml
	 * @param msgFromServer
	 */
	public void getApproveforEvaluation(ArrayList<String> msgFromServer) {
		if (msgFromServer.get(0).equals("true")) {//if the evaluation successfuly sent
			mainClient.getloginWindowController().okMsgCaller("Evaluation successfully sent");
			errMsgHide();
			mainClient.getAssessorHomepageController().initializer();//refresh the table
			System.out.println("\n\nevaluation succeeded");
			Platform.runLater(
					  () -> {
			btnBack.getScene().getWindow().hide();
					  });
		}
		else {//case that send evaluation has corrupted 
			mainClient.getloginWindowController().okMsgCaller("Something went wrong with the evaluation");
			System.out.println("\n\nevaluation didnt succeded");
		}
	}
/**
 * back to the assessor homepage
 * @param event
 * @throws Exception
 */
	public void backToAssessor(ActionEvent event) throws Exception {//back to assessor homepage in case back button has pressed
		btnBack.getScene().getWindow().hide();//close current window
	}
/**
 * pre conditions to the enterence to the fxml
 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		errMsgHide();
		mainClient.handleMessageFromClientUI("59#"+mainClient.getAssessorHomepageController().getRNumber());
		
	}
	
	/**
	 * set fields to the assessor of return evaluation from the committee
	 * @param msgFromServer
	 */
	 public void setFieldsEvaluation(ArrayList<String> msgFromServer) {
		 if(msgFromServer.get(0).equals("true")) {
			 txtChangeLocation.setText(msgFromServer.get(2));
		 txtChangeDescription.setText(msgFromServer.get(3));
		 txtChangeAdvantages.setText(msgFromServer.get(4));
		 txtChangeConstraintsAndRisks.setText(msgFromServer.get(5));
		 txtExecutionDuration.setPromptText(msgFromServer.get(6));////////the change that fails is here changed from set text
		 }
	 }
	/**
	 * compare the date of today and date 2
	 * @param date2
	 * @return
	 */
	 public boolean currentDateChecker(String date2) {
			LocalDate date = LocalDate.parse(date2);
			if(ChronoUnit.DAYS.between(LocalDate.now(), date)>=0) 
				return true;
		return false;
	 }
	 
	 
}
