package task1;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SampleController implements Initializable {
	Connection connection;
	String url = "jdbc:mysql://localhost:3306/a3";
	String username = "root";
	String password = "Bahrami!!1";

	public Button viewButton;
	public Button insertButton;
	public Button updateButton;
	public Button clearButton;

	public TextField initialTextField;
	public TextField firstNameTextField;
	public TextField lastNameTextField;
	public TextField idTextField;
	public TextField addressTextField;
	public TextField cityTextField;
	public TextField stateTextField;
	public TextField telephoneTextField;

	public Label informationLabel;

	/**
	 * Initialize method which run first.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		connectToDatabase();

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(
				"Welcome to Database XXX Application, " + "Feel Free to view or insert users to the database");
		alert.showAndWait();
	}

	/**
	 * Void Method that connects to the database and throws error if connection
	 * failed.
	 */
	public void connectToDatabase() {
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException sql) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText(sql.getMessage());
			alert.show();
		}
	}

	/**
	 * 
	 * when view button is pressed this method will execute.
	 */
	public void executeViewQuery() {
		int id = Integer.parseInt(idTextField.getText());
		String query = "SELECT * FROM staff WHERE ID=" + id;
		Statement statement = null;
		ResultSet results = null;
		try {
			statement = connection.prepareStatement(query);
			results = statement.executeQuery(query);

			if (results.next()) {
				informationLabel.setText("RECORD FOUND!!!");

				idTextField.setText(results.getString("id"));
				firstNameTextField.setText(results.getString("first_name"));
				lastNameTextField.setText(results.getString("last_name"));
				initialTextField.setText(results.getString("initial"));
				addressTextField.setText(results.getString("address"));
				cityTextField.setText(results.getString("city"));
				stateTextField.setText(results.getString("state"));
				telephoneTextField.setText(results.getString("telephone"));
			} else {
				informationLabel.setText("RECORD NOT FOUND!!!");
				idTextField.setText("");
				firstNameTextField.setText("");
				lastNameTextField.setText("");
				initialTextField.setText("");
				addressTextField.setText("");
				cityTextField.setText("");
				stateTextField.setText("");
				telephoneTextField.setText("");
			}

		} catch (SQLException sql) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText(sql.getMessage());
			alert.show();
		}
	}

	public void executeClearButton() {
		String id = idTextField.getText();
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		String initial = initialTextField.getText();
		String address = addressTextField.getText();
		String city = cityTextField.getText();
		String state = stateTextField.getText();
		String telephone = telephoneTextField.getText();

		if (id.isEmpty() && firstName.isEmpty() && lastName.isEmpty() && initial.isEmpty() && address.isEmpty()
				&& city.isEmpty() && state.isEmpty() && telephone.isEmpty()) {
			informationLabel.setText("Feel free to play around with Insert and View button");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText(
					"Nothing to clear, Feel Free to view or insert users to the database");
			alert.showAndWait();
		}else {
			informationLabel.setText("Cleared succefully");
			idTextField.setText("");
			firstNameTextField.setText("");
			lastNameTextField.setText("");
			initialTextField.setText("");
			addressTextField.setText("");
			cityTextField.setText("");
			stateTextField.setText("");
			telephoneTextField.setText("");
		}
	}

}
