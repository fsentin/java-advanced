package hr.fer.oprpp1.hw05.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * A program for calculation of digests, encryptions and decryptions.
 * @author Fani
 *
 */
public class Crypto {
	public static void main(String args[]) throws IOException {
		
		if(args.length == 2){
			checksha(Paths.get("./" + args[1]).toFile());
			
		} else if(args.length == 3) {
			File file = Paths.get("./" + args[1]).toFile();
			System.out.println(file.isFile());
			File result = new File("./" + args[2]);
			crypt(args[0], file, result);
			
		} else {
			System.exit(1);
		}
		
	}
	/**
	 * Subprogram which calculates the message digest of a file and compares it to the digest given by the user.
	 * @param file the file from which the digest is calculated
	 * @throws IOException if an error occurs while reading the file
	 */
	private static void checksha(File file) throws IOException {
		System.out.printf("Please provide expected sha-256 digest for %s:\n>", file.toString().substring(2));
		String input = "", digest = "";
		try(Scanner sc = new Scanner(System.in)){
			input = sc.nextLine().trim();
		} catch (Exception e) {
			System.err.println("Error occured while reading the input.");
		}
		
		MessageDigest messageDigest = null;
		
		try(FileInputStream fin = new FileInputStream(file)){
			messageDigest = MessageDigest.getInstance("SHA-256");
			
			byte[] buffer = new byte[4096];
			int numOfBytes = 0;
			while((numOfBytes = fin.read(buffer)) != -1) 
				messageDigest.update(buffer, 0, numOfBytes);
			
			digest = Util.bytetohex(messageDigest.digest());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	
		if(digest.equals(input)) {
			System.out.printf("Digesting completed. Digest of %s matches expected digest.", file.toString().substring(2));
		} else {
			System.out.printf("Digesting completed. Digest of %s does not match the expected digest. Digest"
					+ " was: %s", file.toString().substring(2), digest);
		}
	}
	
	/**
	 * Encrypts or decrypts the given file.
	 * @param mode encrypt or decrypt mode
	 * @param fileOriginal the original file which is processed by either encrypting or decrypting it
	 * @param fileResult the file created by processing (encrypting or decrypting)
	 * @throws IOException caused by Input or Output FileStreams
	 */
	private static void crypt(String mode, File fileOriginal, File fileResult) throws IOException {
		String keyText = "", ivText = "";
		
		try(Scanner sc = new Scanner(System.in)){
			System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n>");
			keyText = sc.nextLine().trim();
			System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):\n>");
			ivText = sc.nextLine().trim();
		} catch (Exception e) {
			System.err.println("Error occured while reading the input.");
		}
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		
		try(FileInputStream fin = new FileInputStream(fileOriginal);
			FileOutputStream fout = new FileOutputStream(fileResult)){
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			
			byte[] buffer = new byte[4096];
			byte[] processed;
			int numOfBytes = 0;
			while((numOfBytes = fin.read(buffer)) != -1) {
				processed = cipher.update(buffer, 0, numOfBytes);
				if(processed != null) fout.write(processed);
			}
			processed = cipher.doFinal();
			fout.write(processed);
			
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException |
				InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} 
		
		System.out.printf("%s completed."
				+ " Generated file %s based on file %s.", mode + "ion",
				fileResult.toString().substring(2), fileOriginal.toString().substring(2));
	}
	
}
