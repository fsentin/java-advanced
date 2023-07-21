package hr.fer.zemris.lsystems.impl.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.impl.Vector2D;

public class Vector2DTest {
	
	@Test
	public void constructorAndGetters() {
		Vector2D v = new Vector2D(3, 4);
		assertEquals(3, v.getX());
		assertEquals(4, v.getY());
	}
	
	
	@Test
	public void addToThis() {
		Vector2D vThis = new Vector2D(3, 4);
		Vector2D vOther = new Vector2D(3, 4);
		vThis.add(vOther);
		assertEquals(6, vThis.getX());
		assertEquals(8, vThis.getY());
	}
	
	@Test
	public void addCreateNew() {
		Vector2D vThis = new Vector2D(3, 4);
		Vector2D vOther = new Vector2D(3, 4);
		Vector2D vNew = vThis.added(vOther);
		
		Vector2D vResult = new Vector2D(6, 8);

		assertEquals(vResult.getX(), vNew.getX());
		assertEquals(vResult.getY(), vNew.getY());
	}
	
	@Test
	public void rotateThis() {
		var vector = new Vector2D(8, 0);
        vector.rotate(Math.PI / 2);

        assertTrue(Math.abs(vector.getX()) < 1E-8);
        assertTrue(8 - Math.abs(vector.getY()) < 1E-8);
	}
	
	@Test
	public void rotateCreateNew() {
		var vector = new Vector2D(8, 10);
        var newVector = vector.rotated(Math.PI);

        assertNotSame(newVector, vector);
        assertTrue(8 - Math.abs(newVector.getX()) < 1E-8);
        assertTrue(10 - Math.abs(newVector.getY()) < 1E-8);
	}
	
	@Test
	public void scaleThis() {
		Vector2D vThis = new Vector2D(3, 4);
		vThis.scale(5);
		assertEquals(15, vThis.getX());
		assertEquals(20, vThis.getY());
	}
	
	@Test
	public void scaleCreateNew() {
		Vector2D vThis = new Vector2D(3, 4);
		Vector2D vNew = vThis.scaled(5);
		Vector2D vResult = new Vector2D(15, 20);
		
		assertEquals(vResult.getX(), vNew.getX());
		assertEquals(vResult.getY(), vNew.getY());
	}
	
	@Test
	public void copy() {
		var vector = new Vector2D(13.0, 14);
        var newVector = vector.copy();

        assertNotSame(newVector, vector);
	}

}
