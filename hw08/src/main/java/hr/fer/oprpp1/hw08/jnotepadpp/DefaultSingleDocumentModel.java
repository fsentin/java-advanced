package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 * A class representing a single document.
 * @author Fani
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/**
	 * The area of text of this document model
	 */
	private JTextArea textArea;
	
	/**
	 * The path of the file from which the document model is created.
	 */
	private Path path;
	
	/**
	 * The status of document - true if it has been edited, false otherwise.
	 */
	private boolean modified;
	
	/**
	 * Listeners of the document.
	 */
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Creates a new default single document. 
	 * @param path the path of the document
	 * @param text the text content of the document
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		this.textArea = new JTextArea(text, 0, 0);
		this.path = path;
		this.modified = false;
		this.listeners = new ArrayList<>();
		
		
		textArea.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				DefaultSingleDocumentModel.this.setModified(true);
			}
		});
		
	}

	/**
	 * Returns the reference to the document's text area.
	 * @return the reference to the document's text area.
	 */
	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}
	
	/**
	 * Returns the file path of the document.
	 * @return the file path of the document.
	 */
	@Override
	public Path getFilePath() {
		return path;
	}
	
	/**
	 * Sets the file path to a new path.
	 * @param path the new file path, must not be null
	 * @throws NullPointerException if the new path is null
	 */
	@Override
	public void setFilePath(Path path) {
		if(path == null) throw new NullPointerException();
		this.path = path;
		
		for (SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}
	
	/**
	 * Returns the status of the document. 
	 * @return true if it has been modified, false otherwise
	 */
	@Override
	public boolean isModified() {
		return modified;
	}

	/**
	 * Sets the status of the document. Notifies the listeners. 
	 * @param modified the new status
	 */
	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		
		for (SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
		
	}

	/**
	 * Adds a new listener.
	 * @param l listener which is added 
	 */
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}
	
	/**
	 * Removes the specified listener.
	 * @param l listener which is removed
	 */
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

}
