package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class LocalizableAction extends AbstractAction {
	
	private static final long serialVersionUID = 3267512084486781893L;
	private String key;
	private ILocalizationListener listener;
	private ILocalizationProvider prov;
	
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.prov = lp;
		//this.putValue(key, lp.getString(key));
		this.listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(key, lp.getString(key));
			}
		};
		
		lp.addLocalizationListener(listener);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.putValue(key, prov.getString(key));
	}

}
