package controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.EchoClient;
import entities.MyFile;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class OpenChangeRequestController implements Initializable {
	private EchoClient mainClient;
	private ObservableList<String> list;
	@FXML
	private TextField txtInformationSystem;
	@FXML
	private TextArea textDescriptionExistSituation;
	@FXML
	private TextArea txtRequestedChange;
	@FXML
	private TextArea txtRequestReason;
	@FXML
	private TextArea txtComment;
	@FXML
	private Button btnAddFile;
	@FXML
	private Button btnCreate;
	@FXML
	private Button btnCancel;
	@FXML
	private Text txtError;
	@FXML
	private Text selectedFile;
	@FXML
	private Pane okPain;
	@FXML
	private Button okButton;
	@FXML
	private Text okText;
	@FXML
	private Text textHeadLine;
	@FXML
	private Text lblChangeRequest;
	@FXML
	private Text txtErrorReason;
	@FXML
	private Text txtWantedChange;
	@FXML
	private Text txtExsistSituation;
	@FXML
	private Label textNameofInformationsystem;
	@FXML
	private Label textInformationCBX;
	@FXML
	private Text errInfor;
	@FXML
	private Button btnOK;
	@FXML
	private ComboBox<String> informationCBX;
	@FXML
	private Button saveFile;
	
	List<String> lstFile;
	File f = null;
	FileChooser fc = null;
	private String RNumber;
	
	private String informationSystemName = ("");
	private String exsistingDescription = ("");
	private String requestedChange = ("");
	private String requestReason = ("");
	private String extraComment = ("");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mainClient = loginWindowController.mainClient;
		errMsgHide();
		lstFile = new ArrayList<>();
		lstFile.add("*.doc");
		lstFile.add("*.docx");
		lstFile.add("*.DOC");
		lstFile.add("*.DOCX");
		if(UserHomePageController.userOrAssessorOrIt.equals("User")) {///////////////////////////////////////////////
			lblChangeRequest.setText("New change request");
			btnCancel.setVisible(true);
			selectedFile.setVisible(true);
			btnAddFile.setVisible(true);
			btnCreate.setVisible(true);
			btnOK.setVisible(false);
			informationCBX.setVisible(true);
			txtInformationSystem.setVisible(false);
			textNameofInformationsystem.setVisible(false);
			textInformationCBX.setVisible(true);
			list =FXCollections.observableArrayList("Moodle", "Library", "Class Computers", "Laboratory", "Farm Computers", "College Website");
			informationCBX.setItems(list);
			
		}
		else if(UserHomePageController.userOrAssessorOrIt.equals("Assessor")){
			mainClient.handleMessageFromClientUI("54#"+mainClient.getAssessorHomepageController().getRNumber());//send message to get details for assessor change request
			System.out.println("message to fill the fields of request number "+mainClient.getAssessorHomepageController().getRNumber()+"has been sent");
			lblChangeRequest.setText("Change request properties:");
			btnCancel.setVisible(false);
			selectedFile.setVisible(false);
			btnAddFile.setVisible(false);
			btnCreate.setVisible(false);
			btnOK.setVisible(true);
		}

	}

	// If the user clicked cancel button goes back to User HomePage page
	public void CancelAndReturnToUser(ActionEvent event) throws Exception {
		try {
			btnCancel.getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/UserHomePage.fxml").openStream());

			UserHomePageController userHomePageController = loader.getController();
			mainClient.setUserHomePageController(userHomePageController);

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	/*
	public void test() {
		mainClient.handleMessageFromClientUI("100#"); //+ rnumber.
	}
	public void getFile(Object msg) {
		System.out.println("test10");
		int fileSize = ((MyFile) msg).getSize();
		System.out.println("Message received: " + msg + " from ");
		System.out.println("length " + fileSize);
		MyFile newMsg = (MyFile) msg;
		
		FileChooser fc=new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("TXT Files", "*.txt"));
		
		
		fc.setInitialFileName(((MyFile)msg).getFileName());
		File f =fc.showSaveDialog(null);
		String path = f.getAbsolutePath();
		System.out.println(((MyFile) msg).ToString());
		try {
			File newFile = new File(path + "" + newMsg.getFileName());
			byte[] mybytearray = newMsg.getMybytearray();
			FileOutputStream fos = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(mybytearray, 0, newMsg.getSize());
			bos.flush();
			fos.flush();
		} catch (Exception e) {
			System.out.println("Error send (Files)msg) to Client");
		}
		
		
		
	}
	*/
	

	public void createNewChangeRequest() throws IOException {
		if(informationCBX.getValue()!=null)
		this.informationSystemName = informationCBX.getValue().toString();
		this.exsistingDescription = textDescriptionExistSituation.getText();
		this.requestedChange = txtRequestedChange.getText();
		this.requestReason = txtRequestReason.getText();
		this.extraComment = txtComment.getText();
		if (extraComment.isEmpty())
			extraComment = " ";
		if (informationCBX.getValue()!=null && (!exsistingDescription.equals("")) && (!requestedChange.equals(""))
				&& (!requestReason.equals(""))) {
			mainClient.handleMessageFromClientUI("9#" + loginWindowController.user.getuserID() + ("#")
					+ this.informationSystemName + "#" + this.exsistingDescription + ("#") + this.requestedChange
					+ ("#") + this.requestReason + ("#") + this.extraComment + ("#"));
			System.out.println("Message was successfully sent to the server");			

		} else {
			ChangeErrVisibility();
		}
	}

	@SuppressWarnings("resource")
	public void fileSend() {
		if (f != null) {
			MyFile msg = new MyFile(f.getName());
			msg.setRnumber(RNumber);
			msg.setUserName(loginWindowController.user.getfullName());
			msg.setType(getFileExtension(f.getName()));
			String LocalfilePath = f.getAbsolutePath();
			try {
				File newFile = new File(LocalfilePath);
				byte[] mybytearray = new byte[(int) newFile.length()];
				FileInputStream fis = new FileInputStream(newFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				msg.initArray(mybytearray.length);
				msg.setSize(mybytearray.length);
				bis.read(msg.getMybytearray(), 0, mybytearray.length);
				System.out.println(msg.ToString());
				mainClient.sendToServer(msg);
			} catch (Exception e) {
				System.out.println("Error send (Files)msg) to Server");
			}
		}
	}
	
	public void okRequestsend(ActionEvent event) {
		try {
			btnCancel.getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxmls/UserHomePage.fxml").openStream());

			UserHomePageController userHomePageController = loader.getController();
			mainClient.setUserHomePageController(userHomePageController);

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public void addFile(ActionEvent event) throws Exception {
		fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("PDF Files", "*.pdf"));
		fc.getExtensionFilters().add(new ExtensionFilter("WORD Files", lstFile));
		fc.getExtensionFilters().add(new ExtensionFilter("TXT Files", "*txt"));
		fc.getExtensionFilters().add(new ExtensionFilter("JPEG Files", "*jpeg"));
		fc.getExtensionFilters().add(new ExtensionFilter("JPG Files", "*jpg"));
		fc.getExtensionFilters().add(new ExtensionFilter("PNG Files", "*png"));
		fc.getExtensionFilters().add(new ExtensionFilter("ZIP Files", "*zip"));
		fc.getExtensionFilters().add(new ExtensionFilter("RAR Files", "*rar"));
		
		f = fc.showOpenDialog(null);
		if (f != null) {
			this.selectedFile.setText("Selected File: " + f.getAbsolutePath());
			this.btnAddFile.setText("Add different file");
		} else
			this.selectedFile.setText("You didn't choose file, Please try again.");
	}

	public void sendApproval(ArrayList<String> msgFromServer) {
		RNumber=msgFromServer.get(1);
		if(msgFromServer.get(0).equals("true") && f != null)
			fileSend();
		else {
			okRequestedAfterFile();
		mainClient.getloginWindowController().okMsgCaller("Request Number "+msgFromServer.get(1)+" was successfully created" );
		}
	}
	
	public void fileConfirmation(ArrayList<String> msgFromServer) {
		Platform.runLater(
				()->{
		if(msgFromServer.get(0).equals("true")) {
			okRequestedAfterFile();
			mainClient.getloginWindowController().okMsgCaller("Request Number " +RNumber+ " was successfully created with the file "+msgFromServer.get(1));
			
		}
		else {
			mainClient.getloginWindowController().okMsgCaller("There was a problem with saving the file of " +RNumber+", Please try again.");
		}
				});
	}
	
	public static String getFileExtension(String fullName) {
	    String fileName = new File(fullName).getName();
	    int dotIndex = fileName.lastIndexOf('.');
	    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}


	public void setUpRequestForIT(String name,String situation,String change,String reason,String comments) {
	txtInformationSystem.setText(name);
	textDescriptionExistSituation.setText(situation);
	txtRequestedChange.setText(change);
	txtRequestReason.setText(reason);
	txtComment.setText(comments);
	textDescriptionExistSituation.setEditable(false);
	txtRequestedChange.setEditable(false);
	txtRequestReason.setEditable(false);
	txtInformationSystem.setEditable(false);
	txtComment.setEditable(false);
	lblChangeRequest.setText("Change request details");
	btnAddFile.setVisible(false);
	btnCancel.setVisible(false);
	btnCreate.setVisible(false);
	btnOK.setDisable(false);
	btnOK.setVisible(true);
	}

	
	public void backToIT(ActionEvent event) {
		btnOK.getScene().getWindow().hide();
	}
	
	/**
	 * open the properties that filled by user
	 * @param msgFromServer
	 */
	public void setFieldsToAssessor(ArrayList<String> msgFromServer) {
			 txtInformationSystem.setText(msgFromServer.get(0));
			 textDescriptionExistSituation.setText(msgFromServer.get(1));
			 txtRequestedChange.setText(msgFromServer.get(2));
			 txtRequestReason.setText(msgFromServer.get(3));
			 txtComment.setText(msgFromServer.get(4));
			 txtInformationSystem.setEditable(false);
			 textDescriptionExistSituation.setEditable(false);
			 txtRequestedChange.setEditable(false);
			 txtRequestReason.setEditable(false);
			 txtComment.setEditable(false);
		 }
	
	
	public void backToAssessor() {
		btnOK.getScene().getWindow().hide();
	}
	
	/**
	 * hide the err messages
	 */
		public void errMsgHide() {//hihing errors for initialize
			errInfor.setVisible(false);
			txtExsistSituation.setVisible(false);
			txtWantedChange.setVisible(false);
			txtErrorReason.setVisible(false);
		}
		
		/**
		 * change the fields that didnt filled correctky messages
		 */
		public void ChangeErrVisibility(){//change error print to the user
			if(informationCBX.getValue()==null)
				errInfor.setVisible(true);
			else
				errInfor.setVisible(false);
			
			if(exsistingDescription.equals(""))
				txtExsistSituation.setVisible(true);
			else
				txtExsistSituation.setVisible(false);
			
			if(requestedChange.equals(""))
				txtWantedChange.setVisible(true);
			else
				txtWantedChange.setVisible(false);
			
			if(requestReason.equals(""))
				txtErrorReason.setVisible(true);
			else
				txtErrorReason.setVisible(false);
			
		}
		
		
		public void okRequestedAfterFile() {
			Platform.runLater(
					()->{
			try {
				btnCancel.getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxmls/UserHomePage.fxml").openStream());

				UserHomePageController userHomePageController = loader.getController();
				mainClient.setUserHomePageController(userHomePageController);

				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
	}
		
}

