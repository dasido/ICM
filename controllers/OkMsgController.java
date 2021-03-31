package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class OkMsgController {


	@FXML
	private Text textOk;
	
	@FXML
	private Button btnOk;
	/*
	 * Closing the massage window.
	 */
	public void btnOkFunc(ActionEvent event) {
		btnOk.getScene().getWindow().hide(); // hiding primary window

	}
	/*
	 * Sets the massage we want to show to user.
	 */
	public void setTextMsg(String msg) {
		textOk.setText(msg);
	}
}
