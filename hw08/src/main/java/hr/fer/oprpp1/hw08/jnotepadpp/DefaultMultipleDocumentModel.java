package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * A class representing a tabbed realization of a multiple document model.
 * @author Fani
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 6990108768370128054L;
	
	/**
	 * Reference to the currently selected document model.
	 */
	private SingleDocumentModel current;
	
	/**
	 * Documents which this model holds.
	 */
	private List<DefaultSingleDocumentModel> docs;
	
	
	/**
	 * Listeners of this multiple document model.
	 */
	private List<MultipleDocumentListener> listeners;
	
	private ImageIcon modified = null;
	private ImageIcon saved = null;
	
	/**
	 * Creates a new multiple document model.
	 */
	public DefaultMultipleDocumentModel() {
		this.listeners = new ArrayList<>();
		this.docs = new ArrayList<>();
		
		IconProvider provider = new IconProvider();
		try {
			modified = provider.getModIcon();
			saved = provider.getSavIcon();
		} catch(IOException exc) {
			
		}
		
		this.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(DefaultMultipleDocumentModel.this.getTabCount() == 0)
					return;
				
				if(e.getSource() instanceof DefaultMultipleDocumentModel) {
					var src = (DefaultMultipleDocumentModel) e.getSource();
					
					var prev = current;
					current = getDocument(src.getSelectedIndex());
					
					for(MultipleDocumentListener l : listeners) {
						l.currentDocumentChanged(prev, src.getCurrentDocument());
					}
				}
			}
		});
	}

	/**
	 * Creates a new document.
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel doc = new DefaultSingleDocumentModel(null, "");
		
		docs.add((DefaultSingleDocumentModel) doc);
		this.addTab("(unnamed)", doc.getTextComponent());
		this.setSelectedComponent(doc.getTextComponent());
		
		doc.addSingleDocumentListener(listenerFactory());
		
		doc.setModified(false);
		
		//notify listeners
		for(MultipleDocumentListener l : listeners) {
			l.documentAdded(doc);
		}
		
		return doc;
	}

	/**
	 * Loads an existing document from the disk.
	 * @param path the path of the target file which is loaded to this multiple document model
	 * @return the model of a loaded document.
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		
		//if there is such document already opened
		for (SingleDocumentModel model : docs) {
			if(path.equals(model.getFilePath())) {
				this.setSelectedIndex(docs.indexOf(model));
				return model;
			}
		}
		
		//if there is no such document already opened
		try {
			SingleDocumentModel loadedDoc = new DefaultSingleDocumentModel(path, Files.readString(path));
			docs.add((DefaultSingleDocumentModel) loadedDoc);
			
			this.addTab(loadedDoc.getFilePath().getFileName().toString(), loadedDoc.getTextComponent());
			this.setSelectedComponent(loadedDoc.getTextComponent());
			
			setToolTipTextAt(this.getSelectedIndex(), loadedDoc.getFilePath().toString());
			setTitleAt(this.getSelectedIndex(), loadedDoc.getFilePath().getFileName().toString());
			
			loadedDoc.addSingleDocumentListener(listenerFactory());
			loadedDoc.setModified(false);
			return loadedDoc;
			
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Saves the given document to the given target path.
	 * @param model the model of the document that is saved in the specified disk location
	 * @param newPath the specified new disk location
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		try {
			Files.write(
					newPath != null ? newPath : model.getFilePath(),
					model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8)
					);
			
			model.setFilePath(newPath);
			model.setModified(false);
			
			for(MultipleDocumentListener l : listeners) {
				l.currentDocumentChanged(null, current);
			}
			
		} catch (IOException e) {
			throw new NotepadException("Could not write the file to the disk.");
		}
	}

	/**
	 * Closes the specified document.
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		//case when only an unedited new file is open
		if(model == null) {
			this.remove(this.getSelectedIndex());
		//other cases
		} else {
			this.remove(model.getTextComponent());
		}
	
		docs.remove(model);
		
		//notify listeners
		for(MultipleDocumentListener l : listeners) {
			l.documentRemoved(model);
		}
	}

	
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Returns the current number of documents in the model.
	 * @return  the current number of documents in the model.
	 */
	@Override
	public int getNumberOfDocuments() {
		return docs.size();
	}

	/**
	 * Returns the document at a specified index in the model.
	 * @return the document at a specified index in the model.
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		return docs.get(index);
	}

	/**
	 * Returns the currently slected document in the model.
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}
	
	private int getIndexOfDocument(SingleDocumentModel model) {
		return this.indexOfComponent(model.getTextComponent());
	}
	
	private SingleDocumentListener listenerFactory() {
		return new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				int index = getIndexOfDocument(model);
				if(model.isModified()) {
					DefaultMultipleDocumentModel.this.setIconAt(index, modified);
				} else {
					DefaultMultipleDocumentModel.this.setIconAt(index, saved);
				}
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				
				int index = getIndexOfDocument(model);
				DefaultMultipleDocumentModel.this.setToolTipTextAt(index, model.getFilePath().toString());
				DefaultMultipleDocumentModel.this.setTitleAt(index, model.getFilePath().getFileName().toString());
			}
		};
	}
	
}
