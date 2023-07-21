package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimDemo extends JFrame {
	
	private static final long serialVersionUID = 2552614965736703542L;
	
	public PrimDemo() {
		setLocation(100, 100);
		setTitle("Prime numbers");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	static class PrimListModel implements ListModel<Integer>{
		private List<Integer> elements = new ArrayList<>();
		private List<ListDataListener> listeners = new ArrayList<>();
		
		private Integer currentPrime;
		
		public PrimListModel() {
			this.currentPrime = 1;
			elements.add(currentPrime);
		}
		
		@Override
		public int getSize() {
			return elements.size();
		}

		@Override
		public Integer getElementAt(int index) {
			return elements.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			listeners.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);
		}
		
		public void next() {
			if(currentPrime == 1) {
				currentPrime = 2;
			} else if(currentPrime == 2) {
				currentPrime = 3;
			} else {
				do {
					currentPrime += 2;
				} while((!isPrime(currentPrime)));
			}
			
			int pos = elements.size();
			elements.add(currentPrime);
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
			for(ListDataListener l : listeners) {
				l.intervalAdded(event);
			}
		}
		
		private boolean isPrime(Integer num) {
			for(int i = 2; i < num; i++) {
				if(num % i == 0) 
					return false;
			}
			return true;
		}
		
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JPanel bottomPanel = new JPanel();

		JButton next = new JButton("Next");
		bottomPanel.add(next);
		
		next.addActionListener(e -> {
			model.next();
		});
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		panel.add(new JScrollPane(list1));
		panel.add(new JScrollPane(list2));
		
		cp.add(panel, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.PAGE_END);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->{
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});

	}

}
