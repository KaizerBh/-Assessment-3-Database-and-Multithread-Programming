package task3;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class SampleController implements Initializable, Runnable {
	public final double radius = 20;
	public Thread thread;
	public double x = radius, y = radius;
	public Circle circle;
	public double dx = 1, dy = 1;
	public Pane pane;
	private boolean suspended = false;
	public Label label;
	public double speed = 1.0;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		play();
	}

	public synchronized void play() {
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * pauses the thread, the animation in this case.
	 */
	public synchronized void pause() {
		suspended = true;
	}
	
	/**
	 * resumes the ball by sending notify to the thread.
	 */
	public synchronized void resume() {
		suspended = false;
		notify();
	}

	/** Increases the speed of the ball by 0.1 to a maximum of 10.
     */
	public synchronized void increaseSpeed() {
		speed += 0.1;
		if (speed > 10) {
			speed = 10;
		}
		label.setText("Ball Speed: " + speed);
	}

	/** Decreases the speed of the ball by 0.1 to a minimum of 1.
     */
	public synchronized void decreaseSpeed() {
		speed -= 0.1;
		if (speed < 1) {
			speed = 1;
		}
		
		label.setText("Ball Speed: " + speed);
	}

	/**
	 * Moves the ball based on its current speed and direction.
     * Reverses the direction of the ball when it collides with the edge of the window.
     * Updates the circle object to reflect its current position.
	 */
	protected synchronized void moveBall() {
		if (x < radius || x >= 250 - radius) {
			dx *= -1;
		}

		if (y < radius || y >= 150 - radius) {
			dy *= -1;
		}

		x += dx * speed;
		y += dy * speed;
		circle.setCenterX(x);
		circle.setCenterY(y);
	}
	
	/**
	 * Handles the key press method that will be passed into Sample.FXML
	 * @param event get key pressed event
	 */
	public void handleOnKeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.UP) {
			increaseSpeed();
		} else if (event.getCode() == KeyCode.DOWN) {
			decreaseSpeed();
		}
	}
	
	/**
	 * Overrides the runnable method to use a thread to animate it every 20 millis,
	 */
	@Override
	public void run() {
		Runnable updater = new Runnable() {

			@Override
			public void run() {
				moveBall();
			}
		};
		while (true) {
			try {
				Thread.sleep(10);
				synchronized (this) {
					while (suspended) {
						wait();
					}
				}
				Platform.runLater(updater);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

}
