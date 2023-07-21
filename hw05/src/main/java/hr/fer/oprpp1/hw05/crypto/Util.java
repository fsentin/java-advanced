package hr.fer.oprpp1.hw05.crypto;

/**
 * Class which calculates conversions from hexadecimal to byte and vice versa.
 * @author Fani
 *
 */
public class Util {
	/**
	 * Converts the given string to an array of bytes.
	 * @param keyText text from which the byte array is calculated
	 * @return byte array representing the given string.
	 */
	public static byte[] hextobyte(String keyText) {
		byte[] res = new byte[keyText.length()/2];
		
		for (int i = 0; i < res.length; i++) 
	         res[i] = (byte) Integer.parseInt(keyText.substring(i * 2, i * 2 + 2), 16);
		return res;
	}
	
	/**
	 * Converts the given byte array to a hexadecimal string representation.
	 * @param bytearray array of bytes from which the string representation is calculated
	 * @return hexadecimal representation of the given byte array.
	 */
	public static String  bytetohex(byte[] bytearray) {
		StringBuffer buf = new StringBuffer();
		
		for(int i = 0; i < bytearray.length; i++){
		    buf.append(Character.forDigit((bytearray[i] >> 4) & 0xF, 16));
		    buf.append(Character.forDigit((bytearray[i] & 0xF), 16));
		}
		return buf.toString();
	}
	
}
