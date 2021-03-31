package controllers;

import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ReportPerformanceController implements Initializable{
	EchoClient mainClient = loginWindowController.mainClient;
	private ObservableList<String> list;
	@FXML
	private Button btnBack;
	
	@FXML
	private BarChart<CategoryAxis,CategoryAxis> frequencyBarChart;
	
	@FXML
	private CategoryAxis x = new CategoryAxis();
	
	@FXML
	private NumberAxis y = new NumberAxis();
	
	@FXML
	private TextField textFieldMedian;
	
	@FXML
	private TextField textFieldStandardDeviation;
	
	@FXML
	private ComboBox<String> informationSystemCBX;
	
	@FXML
	private TextField textFieldAllTimeExtension;
	
	@FXML
	private TextField textFieldAfterAssessment;
	
	@FXML
	private TextField textFieldTotalDelaysDays;
	
	@FXML
	private Text textTotalDelaysDays;
	
	public void backToIT(ActionEvent event) {
		Platform.runLater(
				()->{
					btnBack.getScene().getWindow().hide();
	});	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainClient.handleMessageFromClientUI("82");
		list =FXCollections.observableArrayList("Moodle", "Library", "Class Computers", "Laboratory", "Farm Computers", "College Website");
		informationSystemCBX.setItems(list);
		frequencyBarChart.setVisible(false);
		textTotalDelaysDays.setVisible(false);
		textFieldTotalDelaysDays.setVisible(false);
	}
	
	public void getDelayReport(ArrayList<String> msgFromServer) {
		textFieldAfterAssessment.setText(msgFromServer.get(0));
	}
	
	

	
	
	public void getInformationCBXchoice() {
		mainClient.handleMessageFromClientUI("88#"+informationSystemCBX.getSelectionModel().getSelectedItem());
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setFrequencyBarChart(ArrayList<String> msgFromServer) {
		Platform.runLater(
				()->{
		Integer sum = 0;
		frequencyBarChart.getData().clear();
		y.setLabel("Amount of delays per day");
		x.setAnimated(false);
		XYChart.Series series = new XYChart.Series();
		series.setName("Delays duration in days");
		for(int i=0;i<10;i++) {
			sum = sum + Integer.parseInt(msgFromServer.get(i));
			if(i==9)
				series.getData().add(new XYChart.Data<>("10 Days & above",Integer.parseInt(msgFromServer.get(i))));
			
			if(i==0)
				series.getData().add(new XYChart.Data<>("1 Day",Integer.parseInt(msgFromServer.get(i))));
				else if(i!=9 && i!=0)
		series.getData().add(new XYChart.Data<>((i+1)+" Days",Integer.parseInt(msgFromServer.get(i))));
		}
		textFieldMedian.setText(msgFromServer.get(10));
		textFieldStandardDeviation.setText(msgFromServer.get(11));
		 frequencyBarChart.getData().add(series);
		 textFieldTotalDelaysDays.setText(sum.toString());
		frequencyBarChart.setVisible(true);
		textFieldTotalDelaysDays.setVisible(true);
		textTotalDelaysDays.setVisible(true);
		
	});
	}

}
