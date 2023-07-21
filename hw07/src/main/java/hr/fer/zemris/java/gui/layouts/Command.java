package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Dimension;
@FunctionalInterface
public interface Command {
	public Dimension assign(Component comp);
}
