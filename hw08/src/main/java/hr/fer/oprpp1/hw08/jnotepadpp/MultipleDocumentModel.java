package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

/**
 * MultipleDocumentModel represents a model capable of holding zero, one or more documents,
 * having a concept of current document – the one which is shown to the
 * user and on which the user works.
 * 
 * @author Fani
 *
 */
public interface MultipleDocumentModel {
	/**
	 * Creates a new single document as a part of the multiple document model.
	 * @return reference to the newly created document model.
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Returns the document which is currently shown and opened to the user.
	 * @return  currently shown and opened document.
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Loads an already existing document to this multiple document model.
	 * @param path
	 * @return reference to the model of the loaded document.
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves the contents of a specified document model to the specified path.
	 * @param model the specified  document model
	 * @param newPath the specified path
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Closes the specififed document model.
	 * @param model the specified document model
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Adds a new listener to this multiple document model.
	 * @param l the new listener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Removes a listener from the multiple document model.
	 * @param l the listener that is removed
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Returns the number of documents opened.
	 * @return integer count of documents opened.
	 */
	int getNumberOfDocuments();
	
	/**
	 * Returns the document at the specified index in the multiple document model.
	 * @param index the specified index of a document
	 * @return the document at the specified index in the multiple document model.
	 */
	SingleDocumentModel getDocument(int index);
}
