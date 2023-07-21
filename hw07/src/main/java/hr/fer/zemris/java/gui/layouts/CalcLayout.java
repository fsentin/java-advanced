package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CalcLayout implements LayoutManager2 {
	
	private int gap;

	private Map<Component, RCPosition> components;
	private Set<RCPosition> positions;
	
	
	private static final int ROW = 5;
	private static final int COL = 7;
	private static final RCPosition FIRST = new RCPosition(1,1);
	
	
	public CalcLayout() {
		this(0);
	}
	
	public CalcLayout(int gap) {
		this.gap = gap;
		this.components = new HashMap<>();
		this.positions = new HashSet<>();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		positions.remove(components.get(comp));
		components.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return helperLayoutSize(parent, (c) -> c.getPreferredSize());
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return helperLayoutSize(parent, (c) -> c.getMinimumSize());
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return helperLayoutSize(target, (c) -> c.getMaximumSize());
	}
	
	
	private Dimension helperLayoutSize(Container parent, Command cmd) {
		Dimension dim = componentDim(cmd);
		int x = dim.width;
		int y = dim.height;
		
		Insets ins = parent.getInsets();
		x = ins.left + COL * x + (COL - 1) * gap + ins.right;
		y = ins.top + ROW * y + (ROW - 1) * gap + ins.bottom;
		
		return new Dimension(x, y);
	}
	
	private Dimension componentDim(Command cmd) {
		int x, y;
		x = y = 0;
		
		for(Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Component comp = entry.getKey();
			RCPosition pos = entry.getValue();
			Dimension dim = cmd.assign(comp);
			if(dim != null) {
				y = Math.max(y, dim.height);
				if(pos.equals(FIRST)) { 
					continue; 
				}
				x = Math.max(x, dim.width);
			}
		}
		
		return new Dimension(x, y);
	}
	

	@Override
	public void layoutContainer(Container parent) {
		Dimension dim = componentDim(c -> c.getPreferredSize());

		double ratioX = (double) parent.getWidth() / preferredLayoutSize(parent).getWidth();
		double ratioY = (double) parent.getHeight() / preferredLayoutSize(parent).getHeight();

		dim.setSize(ratioX * dim.getWidth(),  ratioY * dim.getHeight());

		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			var pos = entry.getValue();
			var comp = entry.getKey();
			if (pos.equals(FIRST)) {
				comp.setBounds(0, 0, 5 * dim.width + 4 * gap, dim.height);
				continue;
			}
			comp.setBounds((pos.getColumn() - 1) * (dim.width + gap),(pos.getRow() - 1) * (dim.height + gap), dim.width, dim.height);
		}
		
	}
	

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null || constraints == null) {
			throw new NullPointerException();
		}
		
		RCPosition pos = null;
		if(constraints instanceof RCPosition) {
			pos = (RCPosition) constraints;
		} else if(constraints instanceof String) {
			try {
				pos = RCPosition.parse((String) constraints);
			} catch(NumberFormatException | ArrayIndexOutOfBoundsException exc) {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
		
		
		if(pos.getRow() < 1 || pos.getRow() > ROW  ) {
			throw new CalcLayoutException("Wrong row position!");
		} else if(pos.getColumn() < 1 || pos.getColumn() > COL) {
			throw new CalcLayoutException("Wrong column position!");
		} else if(pos.getRow() == 1 && (pos.getColumn() > 1 && pos.getColumn() < 6)) {
			throw new CalcLayoutException("Wrong first row position!");
		} else if(positions.contains(pos)) {
			throw new CalcLayoutException("This position is already taken!");
		}
		
		positions.add(pos);
		components.put(comp, pos);
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		
	}

}
