package client;

import controllers.ReceiveIpController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class ClientMain extends Application {
	
	public void start(Stage primaryStage) {
		ReceiveIpController receiveIp = new ReceiveIpController();
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/fxmls/icm_logo.png")));

		
		receiveIp.start(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
