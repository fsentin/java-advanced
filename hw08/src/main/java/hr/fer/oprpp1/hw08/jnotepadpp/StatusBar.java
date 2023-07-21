package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

public class StatusBar extends JPanel {

	private static final long serialVersionUID = 6857311376550227223L;
	
	private ILocalizationProvider flp;
	
	private JLabel length;
	
	private JLabel details;
	
	private JLabel clock;
	
	public StatusBar(ILocalizationProvider flp) {
		this.flp = flp;
		
		setLayout(new GridLayout(1, 3));
		initGUI();
	}
	
	public void initGUI() {
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY));
		
		length = new JLabel();
		details = new JLabel();
		
		add(length);
		add(details);
		
		initClock();
	}
	
	public void initClock() {
		clock = new JLabel();
		SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/DD hh:mm:ss");
		Timer timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clock.setText(format.format(new Date()));
			}
		});
		
		timer.start();
		add(clock);
	}
	
	public void updateStats(JTextArea text) {
		length.setText(flp.getString("length") + text.toString().length());
		
		Caret caret = text.getCaret();
		int dot = caret.getDot();
		int sel = Math.abs(dot - caret.getMark());
		
		int ln = 0, col = 0;
		try {
			ln = text.getLineOfOffset(dot);
			col = dot - text.getLineStartOffset(ln) + 1;
			ln++;
		} catch (BadLocationException ignorable) {
			
		}

		details.setText(String.format("%s %s %s", 
				flp.getString("lines") + ln, flp.getString("columns") + col, flp.getString("selection") + sel));
	}
}
