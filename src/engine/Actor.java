package engine;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView {

	public Actor() {
		;
	}
	
	// This method is called every frame once start has been called on the world.
	public abstract void act(long now);
	// This method is called when an actor is added to the world and should be overridden in subclasses as desired.
	public void	addedToWorld() {
		;
	}
	// Returns a list of the actors of a given type intersecting this actor.
	public <A extends Actor> java.util.List<A>	getIntersectingObjects(java.lang.Class<A> cls) {
		List<A> actors = getWorld().getObjects(cls);
		List<A> intersectingActors = new ArrayList<A>();
		for (A a : actors) {
			if (a != this && a.getBoundsInParent().intersects(this.getBoundsInParent())) {
				intersectingActors.add(a);
			}
		}
		return intersectingActors;
	}
	// Returns one actor of the given class that is intersecting this actor.
	public <A extends Actor> A	getOneIntersectingObject(java.lang.Class<A> cls) {
		List<A> list = getIntersectingObjects(cls);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	// Returns The width of the current image of this actor.
	public double getWidth() {
		return getBoundsInParent().getWidth();
	}
	// Returns The height of the current image of this actor.
		public double getHeight() {
			return getBoundsInParent().getHeight();
		}
	// returns the world this actor is in, or null if it is not in a world.
	public World getWorld() {
		return (World)getParent();
	}
	// Moves this actor by the given dx and dy.
	public void	move(double dx, double dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}
	
	
	
}
