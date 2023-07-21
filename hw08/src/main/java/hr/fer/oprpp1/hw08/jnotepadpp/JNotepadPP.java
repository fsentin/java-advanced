package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import hr.fer.oprpp1.hw08.jnotepadpp.local.*;

import java.awt.event.WindowAdapter;

/**
 * A simple notepad program.
 * 
 * @author Fani
 *
 */
public class JNotepadPP extends JFrame {
	
	/**
	 * Constant for serialization.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The tabbed pane of the frame.
	 */
	private DefaultMultipleDocumentModel tabbedPane;
	
	private StatusBar bar;
	
	private ILocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * Constructs a new frame.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(50, 50);
		setSize(600, 600);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		
		initGUI();
	}
	
	/**
	 * Initializes the graphic user interface.
	 */
	private void initGUI() {
		tabbedPane = new DefaultMultipleDocumentModel();
			                 
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new JScrollPane(tabbedPane), BorderLayout.CENTER);
		
		
		initMenu();
		initToolbar();
		initShortCuts();
		
		
		tabbedPane.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				
				String title = currentModel.getFilePath() == null ? "(unnamed)" : currentModel.getFilePath().toString();
				JNotepadPP.this.setTitle(title + " - JNotepad++");
				
				bar.updateStats(currentModel.getTextComponent());
				currentModel.getTextComponent().addCaretListener(new CaretListener() {
					
					@Override
					public void caretUpdate(CaretEvent e) {
						bar.updateStats(currentModel.getTextComponent());
					}
				});
			}
		});
		
		tabbedPane.createNewDocument();
		JNotepadPP.this.setTitle(flp.getString("languagePrompt"));
	}

	/**
	 * Initializes a menu.
	 */
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu toolsMenu = new JMenu("Tools");
		JMenu langMenu = new JMenu("Languages");
		
		JMenuItem newFile = new JMenuItem(newFileAction);
		JMenuItem open = new JMenuItem(openAction);
		JMenuItem save = new JMenuItem(saveAction);
		JMenuItem saveAs = new JMenuItem(saveAsAction);
		JMenuItem close = new JMenuItem(closeTabAction);
		JMenuItem copy = new JMenuItem(copyAction);
		JMenuItem cut = new JMenuItem(cutAction);
		JMenuItem paste = new JMenuItem(pasteAction);
		JMenuItem stats = new JMenuItem(statsAction);
		
		JMenuItem i1 = new JMenuItem("en"); 
        i1.addActionListener((l) -> {
        	LocalizationProvider.getInstance().setLanguage("en");
        	var comp = tabbedPane.getCurrentDocument();
        	if(comp != null) bar.updateStats(comp.getTextComponent());
        });
        JMenuItem i2 = new JMenuItem("hr"); 
        i2.addActionListener((l) -> {
        	LocalizationProvider.getInstance().setLanguage("hr");
        	var comp = tabbedPane.getCurrentDocument();
        	if(comp != null) bar.updateStats(comp.getTextComponent());
        });
     
		fileMenu.add(newFile);
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.add(close);
		
		editMenu.add(copy);
		editMenu.add(cut);
		editMenu.add(paste);
		
		toolsMenu.add(stats);
		
		langMenu.add(i1);
		langMenu.add(i2);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolsMenu);
		menuBar.add(langMenu);
		
		this.setJMenuBar(menuBar);
		
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				newFile.setText(flp.getString("new"));
				open.setText(flp.getString("open"));
				save.setText(flp.getString("save"));
				saveAs.setText(flp.getString("saveAs"));
				close.setText(flp.getString("close"));
				copy.setText(flp.getString("copy"));
				cut.setText(flp.getString("cut"));
				paste.setText(flp.getString("paste"));
				stats.setText(flp.getString("stats"));
			}
		});
	}

	/**
	 * Initializes a toolbar.
	 */
	private void initToolbar() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);
		
		JButton newFile = new JButton(newFileAction);
		JButton open = new JButton(openAction);
		JButton save = new JButton(saveAction);
		JButton saveAs = new JButton(saveAsAction);
		JButton close = new JButton(closeTabAction);
		JButton copy = new JButton(copyAction);
		JButton cut = new JButton(cutAction);
		JButton paste = new JButton(pasteAction);
		JButton stats = new JButton(statsAction);
		
		bar = new StatusBar(flp);
		
		toolBar.add(newFile);
		
		toolBar.addSeparator();
		toolBar.add(open);
		toolBar.add(save);
		toolBar.add(saveAs);
		
		toolBar.addSeparator();
		toolBar.add(copy);
		toolBar.add(cut);
		toolBar.add(paste);
		
		toolBar.addSeparator();
		toolBar.add(stats);
		toolBar.add(close);
		
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
		this.getContentPane().add(bar, BorderLayout.PAGE_END);
		
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				newFile.setText(flp.getString("new"));
				open.setText(flp.getString("open"));
				save.setText(flp.getString("save"));
				saveAs.setText(flp.getString("saveAs"));
				close.setText(flp.getString("close"));
				copy.setText(flp.getString("copy"));
				cut.setText(flp.getString("cut"));
				paste.setText(flp.getString("paste"));
				stats.setText(flp.getString("stats"));
			}
		});
	}
	
	private void initShortCuts() {
		newFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N")); 
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O")); 
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S")); 
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S")); 
		closeTabAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W")); 
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift W"));
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		statsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
	}
	
	/**
	 * OPEN ACTION.
	 */
	private Action openAction = new LocalizableAction("open", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//choosing the file:
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("openDialog"));
			//cancel
			if(fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			//chosen file and path
			File file = fc.getSelectedFile();
			Path filePath = file.toPath();
			
			//error in access
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						file.getAbsolutePath() + flp.getString("failedReading"), 
						flp.getString("errorMsg"), 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//loading
			try {
				tabbedPane.loadDocument(filePath);
			} catch (RuntimeException exception) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						flp.getString("failedLoading"),
						flp.getString("errorMsg"), 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		}
	};
	
	/**
	 * SAVE ACTION.
	 */
	private Action saveAction = new LocalizableAction("save", flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(tabbedPane.getTabCount() == 0) {
				return;
			}
			var model = tabbedPane.getCurrentDocument();
			saveDocument(model, false);
		}
	};
	
	/**
	 * SAVE AS ACTION.
	 */
	private Action saveAsAction = new LocalizableAction("saveAs", flp){
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(tabbedPane.getTabCount() == 0) {
				return;
			}
			var model = tabbedPane.getCurrentDocument();
			saveDocument(model, true);
		}
	};
	
	/**
	 * CLOSE INDIVIDUAL TAB ACTION.
	 */
	private Action closeTabAction = new LocalizableAction("close", flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(tabbedPane.getTabCount() == 0) {
				return;
			}
			closeDocument(tabbedPane.getCurrentDocument());
		}
	};
	
	/**
	 * NEW FILE ACTION.
	 */
	private Action newFileAction = new LocalizableAction("new", flp){
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			tabbedPane.createNewDocument();
		}
	};
	
	private Action copyAction = new LocalizableAction("copy", flp) {
		private static final long serialVersionUID = -7819997092896022891L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(tabbedPane.getTabCount() == 0) {
				return;
			}
			
			tabbedPane.getCurrentDocument().getTextComponent().copy();
		}
	};
	
	private Action cutAction = new LocalizableAction("cut", flp) {
		private static final long serialVersionUID = -7819997092896022891L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(tabbedPane.getTabCount() == 0) {
				return;
			}
			
			tabbedPane.getCurrentDocument().getTextComponent().cut();
		}
			
	};
	
	private Action pasteAction = new LocalizableAction("paste", flp) {
		private static final long serialVersionUID = -7819997092896022891L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(tabbedPane.getTabCount() == 0) {
				return;
			}
			
			tabbedPane.getCurrentDocument().getTextComponent().paste();
		}
	};
	
	private Action statsAction = new LocalizableAction("stats", flp){
		private static final long serialVersionUID = -7819997092896022891L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(tabbedPane.getTabCount() == 0) {
				return;
			}
			var textArea = tabbedPane.getCurrentDocument().getTextComponent();
			var text = textArea.getText();
			
			int length = text.length();
			int lines = textArea.getLineCount();
			int characters = text.replaceAll("\\s+", "").length();
			
			JOptionPane.showMessageDialog(JNotepadPP.this,
					String.format(flp.getString("statisticsInfo"),
							length, characters, lines), 
					flp.getString("statistics"),
					JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	/**
	 * EXIT ACTION.
	 */
	private Action exitAction = new LocalizableAction("exit", flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			exit();
		}
	};
	
	/**
	 * Checks if any documents have been modified and asks the user 
	 * if he'd like to save them before completely exiting the 
	 * whole program.
	 */
	private void exit() {
		for(Component doc : tabbedPane.getComponents()) {
			closeDocument(tabbedPane.indexOfComponent(doc));
		}
		dispose();
	}
	
	private void closeDocument(int index) {
		closeDocument(tabbedPane.getDocument(index));
	}
	
	private void closeDocument(SingleDocumentModel model) {
		if(model != null && model.isModified()) {
			int result = JOptionPane.showConfirmDialog(this, flp.getString("savePrompt") + " " +  model.getFilePath() +"?");
			//do nothing
			if(result == JOptionPane.CANCEL_OPTION) {
				return;
			//save it
			} else if(result == JOptionPane.YES_OPTION) {
				saveDocument(model, false);
			}
		}
		
		tabbedPane.closeDocument(model);
	}
	

	private void saveDocument(SingleDocumentModel model, boolean saveAs) {
		var filePath = model.getFilePath();
		if(filePath == null || saveAs) {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle(flp.getString("saveMsg"));
			
			if(chooser.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						flp.getString("cancelSaving"), 
						flp.getString("warningMsg"),
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			filePath = chooser.getSelectedFile().toPath();
		}
		
		if(saveAs && Files.exists(filePath)) {
			int result = JOptionPane.showConfirmDialog(this, 
					filePath + " " + flp.getString("overwriteMsg"),
					flp.getString("warningMsg"),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (result != JOptionPane.YES_OPTION) {
				return;
			}
		}
		
		//saving
		try {
			tabbedPane.saveDocument(tabbedPane.getCurrentDocument(), filePath);
			
		//unsuccesful save
		} catch (NotepadException e) {
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					flp.getString("failedSaving"),
					flp.getString("errorMsg"),
					JOptionPane.ERROR_MESSAGE
					);
			return;
		}
		
		//succesful save
		JOptionPane.showMessageDialog(
				JNotepadPP.this, 
				flp.getString("successSaving"), 
				flp.getString("infoMsg"),
				JOptionPane.INFORMATION_MESSAGE
				);
	}
	
	/**
	 * Starting class of the whole notepad program.
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}
