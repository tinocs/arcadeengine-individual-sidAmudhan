package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public abstract class World extends Pane {

	// Instance Variables
	private AnimationTimer timer;
	private boolean timerIsRunning;
	private Set<KeyCode> keysCurrentlyPressed;
	private boolean widthHasBeenSet;
	private boolean heightHasBeenSet;
	private boolean onDimInitCalled;
	
	// Constructor
	public World () {
		widthHasBeenSet = false;
		heightHasBeenSet = false;
		onDimInitCalled = false;
		keysCurrentlyPressed = new HashSet<KeyCode>();
		timerIsRunning = false;
		widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > 0) {
					widthHasBeenSet = true;
				}
				if (widthHasBeenSet && heightHasBeenSet && !onDimInitCalled) {
					onDimensionsInitialized();
					onDimInitCalled = true;
				}
				
			}
			
		});
		
		heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > 0) {
					heightHasBeenSet = true;
				}
				if (widthHasBeenSet && heightHasBeenSet && !onDimInitCalled) {
					onDimensionsInitialized();
					onDimInitCalled = true;
				}
				
			}
			
		});
		
		sceneProperty().addListener(new ChangeListener<Scene>() {

			@Override
			public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
				// TODO Auto-generated method stub
				requestFocus();
			}
			
		});
		
		setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				keysCurrentlyPressed.add(e.getCode());
			}
			
		});
		
		setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				keysCurrentlyPressed.remove(e.getCode());
			}
			
		});
		
		timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// First the world world (us) takes its turn
				act(now);
				
				// Then each Actor takes a turn
				for (Actor n : getObjects(Actor.class)) {
					if (n.getWorld() != null) {
						n.act(now);
					}
				}
			}
		};
	}
	
	// This method is called every frame once start has been called.
	public abstract void act(long now);
	// Adds the given actor to the world and then calls the addedToWorld() method on the actor that was added.
	public void	add(Actor actor) {
		getChildren().add(actor);
		actor.addedToWorld();
	}
	// Returns a list of all the actors in the world of the given class.
	public <A extends Actor>  java.util.List<A>	getObjects(java.lang.Class<A> cls) {
		List<A> list = new ArrayList<A>();
		
		for (Node n : getChildren()) {
			if (cls.isInstance(n)) {
				list.add(cls.cast(n));
			}
		}
		return list;
	}
	// Returns a list of all actors of the given class containing the given x, y
	public <A extends Actor> java.util.List<A>	getObjectsAt(double x, double y, java.lang.Class<A> cls) {
		List<A> allActors = getObjects(cls);
		List<A> actors = new ArrayList<A>();
		for (A a : allActors) {
			if (a.getBoundsInParent().contains(x, y)) {
				actors.add(a);
			}
		}
		return actors;
	}
	// Returns true if the given key is pressed and false otherwise.
	public boolean isKeyPressed(javafx.scene.input.KeyCode code) {
		return keysCurrentlyPressed.contains(code);
	}
	// Returns whether or not the world's timer is stopped
	public boolean isStopped() {
		return timerIsRunning == false;
	}
	// Subclasses should override this.
	public abstract void onDimensionsInitialized();
	// Removes the given actor from the world.
	public void	remove(Actor actor) {
		getChildren().remove(actor);
	}
	// STARTS the timer that calls the act method on the world and on each Actor in the world each frame.
	public void start() {
		timer.start();
		timerIsRunning = true;
	}
	// STOPS the timer that calls the act method on the world and on each Actor in the world each frame.
	public void	stop() {
		timer.stop();
		timerIsRunning = false;
	}
	
}
