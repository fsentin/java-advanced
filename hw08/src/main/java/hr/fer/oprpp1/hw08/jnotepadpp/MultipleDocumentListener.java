package hr.fer.oprpp1.hw08.jnotepadpp;
/**
 * A listener of multiple documents.
 * @author Fani
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Notifies the listener that the selected document has changed.
	 * @param previousModel the previous model
	 * @param currentModel the newly selected model
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	void documentAdded(SingleDocumentModel model);
	void documentRemoved(SingleDocumentModel model);
}
