package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	public void hexToByte() {
		String a = "01ae22";
		String b = "2486e55080945cc9c5f50fe5ba437c434e6dff07ec6c61600601a63761b91b50";
		String c = "2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598";
		String d = "e52217e3ee213ef1ffdee3a192e2ac7e";
		
		byte[] aRes = new byte[] {1, -82, 34};
		byte[] bRes = new byte[] {36, -122, -27, 80, -128, -108, 92, -55, -59, -11, 15, -27, 
								-70, 67, 124, 67, 78, 109, -1, 7, -20, 108, 97, 96, 6, 1, 
								-90, 55, 97, -71, 27, 80};
		byte[] cRes = new byte[] {46, 123, 58, -111, 35, 90, -41, 44, -73, -25, -10, -89, 33,
								-16, 119, -6, -84, -2, -81, -34, -88, -13, 120, 86, 39, -91,
								36, 91, -22, 17, 37, -104};
		byte[] dRes = new byte[] {-27, 34, 23, -29, -18, 33, 62, -15, -1, -34, -29, -95, -110, -30, -84, 126};
		System.out.println(Arrays.toString(Util.hextobyte(d)));
		assertEquals(Arrays.toString(aRes), Arrays.toString(Util.hextobyte(a)));
		assertEquals(Arrays.toString(bRes), Arrays.toString(Util.hextobyte(b)));
		assertEquals(Arrays.toString(cRes), Arrays.toString(Util.hextobyte(c)));
		assertEquals(Arrays.toString(dRes), Arrays.toString(Util.hextobyte(d)));
	}
	
	@Test
	public void byteToHex() {
		byte[] a = new byte[] {1, -82, 34};
		byte[] b = new byte[] {36, -122, -27, 80, -128, -108, 92, -55, -59, -11, 15, -27,
							-70, 67, 124, 67, 78, 109, -1, 7, -20, 108, 97, 96, 6, 1,
							-90, 55, 97, -71, 27, 80};
		byte[] c = new byte[] {46, 123, 58, -111, 35, 90, -41, 44, -73, -25, -10, -89, 33,
							-16, 119, -6, -84, -2, -81, -34, -88, -13, 120, 86, 39, -91,
							36, 91, -22, 17, 37, -104};
		byte[] d = new byte[] {-27, 34, 23, -29, -18, 33, 62, -15, -1, -34, -29, -95, -110, -30, -84, 126};
		
		String aRes = "01ae22";
		String bRes = "2486e55080945cc9c5f50fe5ba437c434e6dff07ec6c61600601a63761b91b50";
		String cRes = "2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598";
		String dRes = "e52217e3ee213ef1ffdee3a192e2ac7e";
		
		assertEquals(aRes, Util.bytetohex(a));
		assertEquals(bRes, Util.bytetohex(b));
		assertEquals(cRes, Util.bytetohex(c));
		assertEquals(dRes, Util.bytetohex(d));
	}
}
