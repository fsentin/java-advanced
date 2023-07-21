package hr.fer.oprpp1.hw08.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	private boolean connected;
	private ILocalizationProvider parent;
	private ILocalizationListener listener;
	
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
	}
	
	public void disconnect() {
		if(connected) {
			parent.removeLocalizationListener(listener);
			connected = false;
		}
	}
	
	public void connect() {
		if(!connected) {
			this.listener = new ILocalizationListener() {
				
				@Override
				public void localizationChanged() {
					fire();
				}
			};
			
			parent.addLocalizationListener(listener);
		}
		connected = true;
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}
	
}
