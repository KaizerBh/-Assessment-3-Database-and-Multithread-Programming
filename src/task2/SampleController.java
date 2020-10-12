package task2;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SampleController implements Initializable {
	Connection connection;
	String url = "jdbc:mysql://localhost:3306/a3";
	String username = "root";
	String password = "Bahrami!!1";
	public ObservableList<Data> data;
	public PieChart pie = new PieChart();
	/**
	 * Initialize method which run first.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		connectToDatabase();
		populatePie();
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
	 * Will populatePie loop through the query and add it into observable list 
	 * then loop the observable list get the value of pie each pie and add add them up
	 * create another loop to bind the name property and divide the value to get the %
	 * 
	 */
	public void populatePie() {
		data = FXCollections.observableArrayList();
		double total = 0;
		try {
			String query = "SELECT state, COUNT(state) as statecount FROM staff GROUP BY state";
			ResultSet rs = connection.createStatement().executeQuery(query);
			while(rs.next()) {
				data.add(new PieChart.Data(rs.getString(1),rs.getDouble(2)));
			}
			for (Data data2 : data) {
				total += data2.getPieValue();
			}
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.show();
		}
		
		pie.getData().addAll(data);
		for (Data data3 : data) {
			data3.nameProperty().bind(Bindings.concat(data3.getName(), "-", data3.pieValueProperty().divide(total).multiply(100).getValue().intValue(),"%"));
			
		}
		
	}
	
	
	

}
