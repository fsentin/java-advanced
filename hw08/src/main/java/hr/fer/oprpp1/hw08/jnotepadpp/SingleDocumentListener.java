package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Listener of a single document.
 * @author Fani
 *
 */
public interface SingleDocumentListener {
	/**
	 * Notifies this listener that the document has been modified.
	 * @param model the model of the document
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Notifies this listener that the document file path has been changed.
	 * @param model the model of the document
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
