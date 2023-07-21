package hr.fer.zemris.lsystems.impl;

public class Vector2D {
	/**
	 * The value of the vector in direction of the x axis.
	 */
	private double x;
	/**
	 * The value of the vector in direction of the y axis.
	 */
	private double y;
	
	/**
	 * Creates a vector with the specified x and y for the notation xi+yj.
	 * @param x the value of the vector in direction of the x axis
	 * @param y the value of the vector in direction of the y axis
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the value of a vector along the x axis.
	 * @return the value of a vector along the x axis
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Returns the value of a vector along the y axis.
	 * @return the value of a vector along the y axis
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Adds the given vector to this vector.
	 * @param offset the vector to be added to this one
	 */
	public void add(Vector2D offset) {
		this.x += offset.getX();
		this.y += offset.getY();
	}
	
	/**
	 * Creates a new vector that is the sum of this vector and the given vector.
	 * @param offset the given vector 
	 * @return a new vector that is the sum of this vector and the given vector.
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * Rotates this vector counterclockwise by the given angle.
	 * @param angle the angle of rotation given in radians.
	 */
	public void rotate(double angle) {
		this.x = x * Math.cos(angle) - y * Math.sin(angle);
		this.y = x * Math.sin(angle) + y * Math.cos(angle);
		
	}
	
	/**
	 * Creates a new vector that is this vector rotated by the given angle.
	 * @param angle
	 * @return the angle of rotation given in radians.
	 */
	public Vector2D rotated(double angle) {
		double xNew = x * Math.cos(angle) - y * Math.sin(angle);
		double yNew = x * Math.sin(angle) + y * Math.cos(angle);
		return new Vector2D(xNew, yNew);
	}
	
	/**
	 * Scales this vector by the given scaler. (changes it's length)
	 * @param scaler the scale factor of scaling
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	/**
	 * Creates a new vector that is this vector scaled by a given scaler.
	 * @param scaler
	 * @return a new vector that is this vector scaled by a given scaler.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}
	
	/**
	 * Returns a new copy of this vector.
	 * @return a new copy of this vector.
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
	
	/**
	 * Prints the string representation of the vector.
	 */
	public String toString() { 
		return String.format("(%f, %f)", this.x, this.y);
	}
		
}
