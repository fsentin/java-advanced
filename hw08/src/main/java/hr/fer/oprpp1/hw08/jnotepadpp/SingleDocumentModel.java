package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * SingleDocumentModel represents a model of single document, having information about file path
 * from which document was loaded (can be null for new document), document modification status
 * and reference to Swing component which is used for editing (each document has its own editor component).
 * @author Fani
 *
 */
public interface SingleDocumentModel {
	/**
	 * Returns the reference to the document's text area.
	 * @return the reference to the document's text area.
	 */
	JTextArea getTextComponent();
	
	/**
	 * Returns the file path of the document.
	 * @return the file path of the document.
	 */
	Path getFilePath();
	
	/**
	 * Sets the file path to a new path.
	 * @param path the new file path, must not be null
	 * @throws NullPointerException if the new path is null
	 */
	void setFilePath(Path path);
	
	/**
	 * Returns the status of the document. 
	 * @return true if it has been modified, false otherwise
	 */
	boolean isModified();
	
	/**
	 * Sets the status of the document. Notifies the listeners. 
	 * @param modified the new status
	 */
	void setModified(boolean modified);
	
	/**
	 * Adds a new listener.
	 * @param l listener which is added 
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Removes the specified listener.
	 * @param l listener which is removed
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
