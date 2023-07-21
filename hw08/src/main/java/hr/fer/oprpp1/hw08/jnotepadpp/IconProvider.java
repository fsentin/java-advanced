package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

public class IconProvider {
	
	public ImageIcon getModIcon() throws IOException {
		
		InputStream is = this.getClass().getResourceAsStream("icons/modified.png");
		
		if(is == null) 
			throw new NullPointerException();
		
		byte[] bytes = is.readAllBytes();
		
		is.close();
		return new ImageIcon(bytes);
	}
	
	public ImageIcon getSavIcon() throws IOException {
		
		InputStream is = this.getClass().getResourceAsStream("icons/saved.png");
		
		if(is == null) 
			throw new NullPointerException();
		
		byte[] bytes = is.readAllBytes();
		
		is.close();
		return new ImageIcon(bytes);
	}
}	
