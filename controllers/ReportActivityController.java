package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.EchoClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ReportActivityController implements Initializable {
	private EchoClient mainClient = loginWindowController.mainClient;
	private ObservableList<String> list;
	private LocalDate date1;
	private LocalDate date2;
	private String requestStatus;
	
	@FXML
	private Button btnBack;
	
	@FXML 
	private ComboBox<String> requestsCBX;
	
	@FXML
	private BarChart<CategoryAxis,CategoryAxis> frequencyBarChart;
	
	@FXML
	private CategoryAxis x = new CategoryAxis();
	
	@FXML
	private NumberAxis y = new NumberAxis();
	
	@FXML
	private DatePicker datePicker1;
	
	@FXML
	private DatePicker datePicker2;
	
	@FXML
	private Text textDatePicker1;
	
	@FXML
	private Text textDatePicker2;
	
	@FXML
	private Text textErrorDate1;
	
	@FXML
	private Text textErrorDate2;
	
	@FXML
	private TextField textFieldMedian;
	
	@FXML
	private TextField textFieldStandardDeviation;
	
	@FXML
	private TextField textFieldTotalWorkingDays;
	
	@FXML
	private TextField textFieldTotalRequest;
	
	@FXML
	private Text textTotalWorkingDays;
	
	@FXML
	private Text textReportStatus;
	
	@FXML
	private Text textFirstDate;
	
	@FXML
	private Text textSecondDate;
	
	@FXML
	private Button btnGetActivityReport;
	
	
	
	@SuppressWarnings({ })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializer();
	}
	
	
	@SuppressWarnings({ })
	public void initializer() {
		list  = FXCollections.observableArrayList("Active","Closed","Suspended","Denied");
		requestsCBX.setItems((ObservableList<String>) list);
		datePicker1.setVisible(false);
		datePicker2.setVisible(false);
		textDatePicker1.setVisible(false);
		textDatePicker2.setVisible(false);
		frequencyBarChart.setVisible(false);
		btnGetActivityReport.setVisible(false);
		textFirstDate.setVisible(false);
		textSecondDate.setVisible(false);
		textReportStatus.setVisible(false);
		textTotalWorkingDays.setVisible(false);
		textFieldTotalWorkingDays.setVisible(false);
		
		/////////////////////////

		
	}
	
	
	public void backToIT(ActionEvent event) {
		Platform.runLater(
				()->{
					btnBack.getScene().getWindow().hide();
	});	
	}

	
	public void requestCBXGetter() {
		if(requestsCBX.getSelectionModel().getSelectedItem().toString()!=requestStatus && requestStatus!=null) {
		setAllDisable();
		datePicker1.setVisible(true);
		datePicker1.setValue(null);
		requestStatus=requestsCBX.getSelectionModel().getSelectedItem().toString();
		textDatePicker1.setVisible(true);
		textReportStatus.setVisible(true);
		textReportStatus.setText(requestsCBX.getSelectionModel().getSelectedItem().toString());
		if(requestsCBX.getSelectionModel().getSelectedItem().toString().equals("Workdays")) {
			textTotalWorkingDays.setVisible(true);
			textFieldTotalWorkingDays.setVisible(true);
		}
		}
		else
		{
			requestStatus=requestsCBX.getSelectionModel().getSelectedItem().toString();
			textReportStatus.setVisible(true);
			textReportStatus.setText(requestStatus.toString());
			datePicker1.setVisible(true);
			textDatePicker1.setVisible(true);
			if(requestsCBX.getSelectionModel().getSelectedItem().toString().equals("Workdays"))
			{
				textTotalWorkingDays.setVisible(true);
				textFieldTotalWorkingDays.setVisible(true);
			}
		}
			
	}
	
	
	private void setAllDisable() {
		datePicker1.setVisible(false);
		datePicker2.setVisible(false);
		textDatePicker1.setVisible(false);
		textDatePicker2.setVisible(false);
		frequencyBarChart.setVisible(false);
		textErrorDate1.setVisible(false);
		textErrorDate2.setVisible(false);
		btnGetActivityReport.setVisible(false);
		textFirstDate.setVisible(false);
		textSecondDate.setVisible(false);

	}
	
	
	public void firstDateChoose(ActionEvent event) {
		if(datePicker1.getValue()==null)
			textErrorDate1.setText("Please Choose a date!");
		else
		 if(!currentDateChecker(datePicker1.getValue().toString())) {
			textErrorDate1.setVisible(true);
			textErrorDate1.setText("Please choose a date until today!");
			datePicker2.setVisible(false);
			textDatePicker2.setVisible(false);
			btnGetActivityReport.setVisible(false);
			textFirstDate.setVisible(false);
			textSecondDate.setVisible(false);
		}
			
		else {
			date1=datePicker1.getValue();
			textFirstDate.setText(date1.toString()+" -");
			textFirstDate.setVisible(true);
			textErrorDate1.setVisible(false);
			datePicker2.setVisible(true);
			textDatePicker2.setVisible(true);
			if(date2!=null)
			{
				textSecondDate.setVisible(true);
				secondDateChoose(event);
			}
		}
	}
	
	
	public void secondDateChoose(ActionEvent event) {
		if(!checkBetweenTwoDates(datePicker2.getValue().toString()))
		{
			textErrorDate2.setVisible(true);
			textErrorDate2.setText("Please Choose a date equals or later then the first.");
			btnGetActivityReport.setVisible(false);
			textSecondDate.setVisible(false);
		}
		else {
			date2=datePicker2.getValue();
			textSecondDate.setText(date2.toString());
			textSecondDate.setVisible(true);
			textErrorDate2.setVisible(false);
			btnGetActivityReport.setVisible(true);
		}
	}
	
	
	public void getActivityReport() {
		mainClient.handleMessageFromClientUI("79#"+ requestStatus+"#"+date1.toString()+"#"+date2.toString());
	

			
	}

	/**
	 * Gets activity reports from server.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getActivityReportFromServer(ArrayList<String> msgFromServer) {
		Platform.runLater(
				()->{	
		Integer sum = 0;
		frequencyBarChart.getData().clear();
		y.setLabel("Amount");
		x.setAnimated(false);
		XYChart.Series series = new XYChart.Series();
		series.setName(requestStatus);
		int n = Integer.parseInt(msgFromServer.get(0));
		for(int i=1;i<n+1;i++) {
		series.getData().add(new XYChart.Data<>(msgFromServer.get(i),Integer.parseInt(msgFromServer.get(i+n))));
		 sum =sum + Integer.parseInt(msgFromServer.get(i+n));
		}
		textFieldMedian.setText(msgFromServer.get((n*2)+1));
		textFieldStandardDeviation.setText(msgFromServer.get((n*2)+2));
		textFieldTotalRequest.setText(sum.toString());
		frequencyBarChart.getData().add(series);
		frequencyBarChart.setVisible(true);
				});
	}
	
	

	
	
 	public boolean currentDateChecker(String date2) {
		 date1 = LocalDate.parse(date2);
		if(ChronoUnit.DAYS.between(LocalDate.now(), date1)<=0) 
			return true;
	return false;	
	}
	
	
	public boolean checkBetweenTwoDates(String date3) {
		 date2 = LocalDate.parse(date3);
		if(ChronoUnit.DAYS.between(date1, date2)>=0) 
			return true;
	return false;	
	}
	
	
	
}
