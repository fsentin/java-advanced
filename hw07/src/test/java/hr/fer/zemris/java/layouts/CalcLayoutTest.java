package hr.fer.zemris.java.layouts;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class CalcLayoutTest {
	@SuppressWarnings("null")
	@Test
	public void addingThrows() {
		CalcLayout l = new CalcLayout();
		
		assertThrows(NullPointerException.class, () -> l.addLayoutComponent(new JLabel(), new  RCPosition((Integer) null, 1)));
		assertThrows(NullPointerException.class, () -> l.addLayoutComponent(new JLabel(), new  RCPosition(1, (Integer) null)));
		
		assertThrows(IllegalArgumentException.class, () -> l.addLayoutComponent(new JLabel(), "11"));
		assertThrows(IllegalArgumentException.class, () -> l.addLayoutComponent(new JLabel(), new Object()));
	
		assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel(), "0,1"));
		assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel(), "6,1"));
		assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel(), "1,0"));
		assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel(), "1,8"));
		assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel(), "1,5"));
		
		l.addLayoutComponent(new JLabel(), "5,4");
		assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel(), "5,4"));
		
	}
}
