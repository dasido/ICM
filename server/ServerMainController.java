package server;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import server.EchoServer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
//import serverFunctionality.mysqlConnection;


// TODO: Auto-generated Javadoc
/**
 * The Class ServerMainController.
 */
public class ServerMainController {

	/** The clients cnt. */
	private int clientsCnt;

	/** The host name TF. */
	@FXML
	private TextField hostnameTF;

	/** The schema TF. */
	@FXML
	private TextField schemaTF;

	/** The user name TF. */
	@FXML
	private TextField usernameTF;

	/** The password TF. */
	@FXML
	private TextField passwordTF;

	/** The server ip TF. */
	@FXML
	private TextField serverIpTF;

	/** The port TF. */
	@FXML
	private TextField portTF;

	/** The status TF. */
	@FXML
	private TextField statusTF;

	/** The clients TF. */
	@FXML
	private TextField clientsTF;

	/** The Run server btn. */
	@FXML
	private Button RunServerBtn;

	/** The Stop server btn. */
	@FXML
	private Button StopServerbtn;

	/** The exit btn. */
	@FXML
	private Button exitbtn;

	/** The Txtlogs. */
	@FXML
	private TextArea Txtlogs;

	/** The aaa. */
	@FXML
	private TextArea aaa;

	/** The Connection. */
	mysqlConnection Connection;

	/**
	 * Initialize.
	 */
	@FXML
	void initialize() {
		statusTF.setText("OFF");
		statusTF.setStyle("-fx-font-weight: bold;\n " + "-fx-text-fill: red;\n " + "-fx-background-color: #353535;\n "
				+ "-fx-border-color: red;\n" + "-fx-border-width: 1;\n");
		clientsCnt = 0;
		clientsTF.setText("-");
		serverIpTF.setText(GetMyIp());
		// StopServerbtn.setDisable(true);
		// RunServerBtn.setDisable(false);
		// portTF.setDisable(false);

	}

	/**
	 * Open server.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@FXML
	void OpenServer() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		statusTF.setText("ON");
		statusTF.setStyle("-fx-font-weight: bold;\n " + "-fx-text-fill: white;\n " + "-fx-background-color: #32CD32;\n "
				+ "-fx-border-color: white;\n" + "-fx-border-width: 1;\n");
		serverMainWindow.openserver = new EchoServer(serverMainWindow.DEFAULT_PORT);
		String[] args = new String[1];
		args[0] = "5555";
		serverMainWindow.openserver.main(args);
		try {
			Connection = mysqlConnection.GetConnection();
			serverMainWindow.openserver.callConnectTodDB(this); //send the ServerMainController object to the echoserver object.
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Stopserver.
	 */
	@FXML
	void Stopserver() {
		System.exit(0);
		// logText.setText(Main.openserver.getreplay(););
	}

	/**
	 * Client connected.
	 *
	 * @param msg the msg
	 */
	public void clientConnected(String msg) {
		clientsCnt++;
		clientsTF.setText(Integer.toString(clientsCnt));
	}

	/**
	 * Client disconnected.
	 *
	 * @param msg the msg
	 */
	public void clientDisconnected(String msg) {
		clientsCnt--;
		clientsTF.setText(Integer.toString(clientsCnt));
	}

	/**
	 * Gets the my ip.
	 *
	 * @return the string
	 */
	private String GetMyIp() {
		try (final DatagramSocket socket = new DatagramSocket()) {
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			return socket.getLocalAddress().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

}
