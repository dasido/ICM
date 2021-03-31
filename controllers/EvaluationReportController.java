package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EvaluationReportController {
	

	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TextField locationText;	
	@FXML
	private TextArea descriptionText;
	@FXML
	private TextArea adventagesText;
	@FXML
	private TextArea constraintsAndRisksText;
	@FXML
	private TextField durationText;	
	@FXML
	private Button okBtn;
	/**
	 * set up javafx fields to execution leader entrance.
	 * @param location
	 * @param description
	 * @param adventages
	 * @param constraints
	 * @param duration
	 */
	public void setUpFieldsForExecutionLeader(String location,String description,String adventages,String constraints,String duration) {
		locationText.setText(location);
		descriptionText.setText(description);
		adventagesText.setText(adventages);
		constraintsAndRisksText.setText(constraints);
		durationText.setText(duration);
	}
	/**
	 * get back to {@link ExecutionLeaderHomepageController}
	 */
	public void okBtnFunc() {
		okBtn.getScene().getWindow().hide();
	}
}
