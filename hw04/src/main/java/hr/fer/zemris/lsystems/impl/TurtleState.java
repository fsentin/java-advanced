package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

public class TurtleState {
	/**
	 * Current position of the turtle.
	 */
	Vector2D position;
	
	/**
	 * The direction in which the turtle is looking.
	 */
	Vector2D direction;
	
	/**
	 * The color with which the turtle draws.
	 */
	Color color;
	
	/**
	 * The effective length of a unit displacement.
	 */
	double length;

	/**
	 * Constructs a new turtle state with specified postion, direction, color and length
	 * @param position current position of the turtle modeled by <code>Vector2D</code>
	 * @param direction the direction in which the turtle is looking modeled by <code>Vector2D</code>
	 * @param color the color with which the turtle draws
	 * @param length the effective length of a unit displacement
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double length) {
		super();
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.length = length;
	}
	
	/**
	 * Returns current position of the turtle.
	 * @return current <code>Vector2D</code> position of the turtle.
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Sets current position of the turtle.
	 * @param position new <code>Vector2D</code> position of the turtle.
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Returns the direction in which the turtle is looking.
	 * @return the <code>Vector2D</code> direction in which the turtle is looking.
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Sets the direction in which the turtle is looking.
	 * @param direction the <code>Vector2D</code> direction in which the turtle is looking
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Returns the color with which the turtle draws.
	 * @return the color with which the turtle draws.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color with which the turtle draws.
	 * @param color the color with which the turtle draws
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Returns the effective length of a unit displacement.
	 * @return the  <code>double</code> effective length of a unit displacement.
	 */
	public double getLength() {
		return length;
	}

	/**
	 * Sets the effective length of a unit displacement.
	 * @param length a <code>double</code> effective length of a unit displacement.
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * Creates a new instance of the same turtle.
	 * @return
	 */
	public TurtleState copy(){
		return new TurtleState(position, direction, color, length);
	}
	
}
