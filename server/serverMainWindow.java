package server;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
//import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import server.EchoServer;

// TODO: Auto-generated Javadoc
/**
 * The Class serverMainWindow.
 */
public class serverMainWindow extends Application {
	
	
	/** The main stage. */
	private static Stage mainStage;
	
	/** The server controller. */
	private static ServerMainController serverController;
	/**
	   * The default port to listen on.
	   */
	final public static int DEFAULT_PORT = 5555;
	  
	/** The openserver. */
	public static EchoServer openserver;
	
	
	/**
	 * Gets the server controller.
	 *
	 * @return the server controller
	 */
	public static ServerMainController getServerController() {
		return serverController;
	}

	/**
	 * Sets the server controller.
	 *
	 * @param serverController the new server controller
	 */
	public static void setServerController(ServerMainController serverController) {
		serverMainWindow.serverController = serverController;
	}

	/**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//the method that starts the gui windows//
	@Override
	public void start(Stage primaryStage) throws IOException{
		try {
			mainStage = primaryStage;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerWindow.fxml"));
			StackPane pane = (StackPane)loader.load();
			Scene scene = new Scene(pane);
			serverController = (ServerMainController)loader.getController();
			// setting the stage
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/server/icm_logo.png")));
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("ICM SERVER:");
			primaryStage.show();
		} 
		catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	/**
	 * Gets the main stage.
	 *
	 * @return the main stage
	 */
	//get returns the call object//
	public static Stage getMainStage() {
		return mainStage;
	}

	

	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	//the main that runs when the jar opens//
	public static void main(String[] args) {
		launch(args);
	}

}
