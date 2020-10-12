package task1;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

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
		if (isNumeric(idTextField.getText())) {
			int id = Integer.parseInt(idTextField.getText());
			String query = "SELECT * FROM staff WHERE ID=" + id;
			Statement statement = null;
			ResultSet results = null;
			try {
				statement = connection.prepareStatement(query);
				results = statement.executeQuery(query);

				if (results.next()) {
					informationLabel.setText("RECORD FOUND!!!");
					idTextField.setEditable(false);
					idTextField.setText(results.getString("id"));
					firstNameTextField.setText(results.getString("first_name"));
					lastNameTextField.setText(results.getString("last_name"));
					initialTextField.setText(results.getString("initial"));
					addressTextField.setText(results.getString("address"));
					cityTextField.setText(results.getString("city"));
					stateTextField.setText(results.getString("state"));
					telephoneTextField.setText(results.getString("telephone"));
				} else {
					idTextField.setEditable(true);
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
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please make sure ID is numeric only");
			alert.show();
		}

	}

	/**
	 * Will clear the fields if there is data in it otherwise it will prompt a
	 * message and ask user to explore more of the program
	 */
	public void executeClearButton() {
		idTextField.setEditable(true);
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
			alert.setContentText("Nothing to clear, Feel Free to view or insert users to the database");
			alert.showAndWait();
		} else {
			idTextField.setEditable(true);
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

	/**
	 * updates query also check if telephone is numeric as well not empty.
	 */
	public void executeUpdateQuery() {
		idTextField.setEditable(false);
		int id = Integer.parseInt(idTextField.getText());
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		String initial = initialTextField.getText();
		String address = addressTextField.getText();
		String city = cityTextField.getText();
		String state = stateTextField.getText();
		String telephone = telephoneTextField.getText();

		if (firstName.isBlank() || lastName.isBlank() || initial.isBlank() || address.isBlank() || city.isBlank()
				|| state.isBlank() || telephone.isBlank()) {
			informationLabel.setText("RECORD FAILED TO UPDATE");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please make sure that all the fields are filled and not blank");
			alert.show();
		} else if (!isNumeric(telephone)) {
			informationLabel.setText("RECORD FAILED TO UPDATE");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please make sure telephone is Numeric only");
			alert.show();
		} else if (state.length() > 3 || state.length() < 2) {
			informationLabel.setText("RECORD FAILED TO UPDATE");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("State can only be NSW,VIC,QLD,WA,ACT,TAS,NT");
			alert.show();
		} else {
			try {
				PreparedStatement statement = connection.prepareStatement("UPDATE staff SET first_name=?, last_name=?,"
						+ "initial=?, address=?,city=?, state=?, telephone=? WHERE id = ?");
				statement.setString(1, firstName);
				statement.setString(2, lastName);
				statement.setString(3, initial);
				statement.setString(4, address);
				statement.setString(5, city);
				statement.setString(6, state);
				statement.setString(7, telephone);
				statement.setInt(8, id);

				statement.executeUpdate();
				informationLabel.setText("RECORD UPDATED SUCCESSFULLY");
			} catch (SQLException sql) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText(sql.getMessage());
				alert.show();
				informationLabel.setText("RECORD FAILED TO UPDATE");
			}
		}

	}

	/**
	 * Will insert data into database
	 */
	public void executeInsertQuery() {
		idTextField.setEditable(false);
		String id = idTextField.getText();
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		String initial = initialTextField.getText();
		String address = addressTextField.getText();
		String city = cityTextField.getText();
		String state = stateTextField.getText();
		String telephone = telephoneTextField.getText();

		if (firstName.isBlank() || lastName.isBlank() || initial.isBlank() || address.isBlank() || city.isBlank()
				|| state.isBlank() || telephone.isBlank()) {
			informationLabel.setText("FAILED TO INSERT RECORD");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please make sure that all the fields are filled and not blank except id field");
			alert.show();
		} else if (!isNumeric(telephone)) {
			informationLabel.setText("FAILED TO INSERT RECORD");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please make sure telephone is Numeric only");
			alert.show();
		} else if (state.length() > 3 || state.length() < 2) {
			informationLabel.setText("FAILED TO INSERT RECORD");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("State can only be NSW,VIC,QLD,WA,ACT,TAS,NT");
			alert.show();
		} else {
			if (id.isEmpty()) {
				String insertStaff = ("INSERT INTO staff (last_name, first_name, initial, address, city, state, telephone) VALUES('"
						+ lastName + "', '" + firstName + "', " + "'" + initial + "', '" + address + "', '" + city
						+ "', '" + state + "', '" + telephone + "')");
				try {
					Statement statement = connection.prepareStatement(insertStaff);
					statement.execute(insertStaff);
					String query = "SELECT id FROM staff ORDER BY id DESC LIMIT 1";
					Statement updateID = connection.prepareStatement(query);
					ResultSet rset = updateID.executeQuery(query);
					while (rset.next()) {
						idTextField.setText(rset.getString(1));
					}
					idTextField.setEditable(false);
					informationLabel.setText("SUCCESFULLY INSERTED NEW RECORD");
				} catch (SQLException sql) {
					informationLabel.setText("FAILED TO INSERT RECORD");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText(sql.getMessage());
					alert.show();
				}
			} else {
				informationLabel.setText("ID FIELD MUST BE EMPTY");
				idTextField.setText("");
			}
		}

	}
	
	/**
	 * Will lock the id field on key pressed
	 */
	public void lockIDFieldOnKeyPressed() {
		idTextField.setEditable(false);
	}
	
	/**
	 * Will lock the id field on key pressed
	 */
	public void unlockIDFieldOnHover() {
		idTextField.setEditable(true);
	}
	

	/**
	 * 
	 * @param strNum String character to be checked
	 * @return Boolean if text is numeric or not
	 */
	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
