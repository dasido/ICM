package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.EchoClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChangeEstimatedFinishDateByInspectorController implements Initializable{
	
	private EchoClient mainClient;
	
	@FXML
	private TextField txtRequestNumber;
	
	@FXML
	private TextField txtFullName;
	
	@FXML
	private TextField txtCurrentPhase;
	
	@FXML
	private TextField currentEstimatedFinishDate;
	
	@FXML
	private TextField txtTodayDate;
	
	@FXML
	private TextArea txtChangeCauseDescription;
	
	@FXML
	private Text txtCurrentEstimatedFinishDate;
	
	@FXML
	private Text txtMustFill;
	
	@FXML
	private Text txtValidDate;
	
	@FXML
	private DatePicker newEstimatedFinishDate;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mainClient = loginWindowController.mainClient;
		txtRequestNumber.setText(InspectorHomepageController.rNumber);
		txtFullName.setText(loginWindowController.user.getfullName());
		txtRequestNumber.setEditable(false);
		txtFullName.setEditable(false);
		txtTodayDate.setText(LocalDate.now().toString());
		txtTodayDate.setEditable(false);
		mainClient.handleMessageFromClientUI("58#" + InspectorHomepageController.rNumber);
		currentEstimatedFinishDate.setEditable(false);
		txtCurrentPhase.setEditable(false);
	}

	//After the user clicked "Open request details" button at Inspector HomePage at Request Management tab, it Sets the needed fields in ChangeEstimatedFinishDateByInspector page
	public void getCurrentPhaseAndFinishDate(ArrayList<String> msgFromServer)
	{
		txtCurrentPhase.setText(msgFromServer.get(0));
		if (msgFromServer.get(1).equals("false")) {
			System.out.println("test58");
			txtCurrentEstimatedFinishDate.setText("There is no current estimated finish date!");
		}
		else
		{
			currentEstimatedFinishDate.setText(msgFromServer.get(1));
			currentEstimatedFinishDate.setEditable(false);
		}
	}
	
	//When the date picker clicked and the user chose a new estimated finish date there
	public void datePickerClicked(ActionEvent event)
	{
		if(newEstimatedFinishDate.getValue() != null)
		{
			String newDate = newEstimatedFinishDate.getValue().toString();
			if(!currentDateChecker(newDate))
			{
				txtValidDate.setText("Enter valid date!");
				newEstimatedFinishDate.setValue(null);
			}
			else txtValidDate.setText("");
		}
		else txtValidDate.setText("Enter valid date!");
	}
	
	//When "Save" button clicked send to the server all the required info the inspector filled to update the database
	public void saveButtonClicked(ActionEvent event)
	{
		if(txtChangeCauseDescription.getText() == null || txtChangeCauseDescription.getText().isEmpty() || newEstimatedFinishDate.getValue() == null)
			txtMustFill.setText("Please fill all the fields with *");
		else
			mainClient.handleMessageFromClientUI("64#" + txtRequestNumber.getText() + "#" + txtFullName.getText() + "#" + txtChangeCauseDescription.getText() + "#" + newEstimatedFinishDate.getValue().toString() + "#" + LocalDate.now());
	}
	
	//After "Save" button clicked and the data saved, gets a success message from the server
	public void saveSucceeded(ArrayList<String> msgFromServer)
	{
		System.out.println("test 64 has " + msgFromServer.get(0));
		mainClient.getloginWindowController().okMsgCaller("The data has been saved successfully!");
	}
	
	//When "Back" button clicked goes back to the Inspector HomePage
	public void backButtonClicked(ActionEvent event)
	{
		try {
			currentEstimatedFinishDate.getScene().getWindow().hide(); // hiding primary window
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
	
	public boolean currentDateChecker(String date2) {
		LocalDate date = LocalDate.parse(date2);
		if(ChronoUnit.DAYS.between(LocalDate.now(), date)>=0) 
			return true;
		return false;
	}
}
