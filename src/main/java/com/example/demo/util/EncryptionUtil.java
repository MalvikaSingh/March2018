package com.example.demo.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class EncryptionUtil {

	private static Cipher ecipher;
	private static Cipher dcipher;
	private static final String ALGO = "AES";
	private static String filePath;

	// 8-byte initialization vector
	private static byte[] iv = { (byte) 0xB2, (byte) 0x12, (byte) 0xD5, (byte) 0xB2, (byte) 0x44, (byte) 0x21,
			(byte) 0xC3, (byte) 0xC3, (byte) 0x34, (byte) 0x21, (byte) 0x53, (byte) 0xC3, (byte) 0x44, (byte) 0x61,
			(byte) 0xC3, (byte) 0x23

	};

	private static byte[] getKeyBytes(String secret) {
		return secret.getBytes();
	}

	/**
	 * Encrypt a string with AES algorithm.
	 *
	 * @param data is a string
	 * @return the encrypted string
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String encrypt(String data) throws IllegalBlockSizeException, BadPaddingException {
		byte[] encVal = ecipher.doFinal(data.getBytes());
		return DatatypeConverter.printBase64Binary(encVal);
	}
	/**
	 * Decrypt a string with AES algorithm.
	 *
	 * @param encryptedData is a string
	 * @return the decrypted string
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String decrypt(String encryptedData) throws IllegalBlockSizeException, BadPaddingException {
		byte[] decordedValue = DatatypeConverter.parseBase64Binary(encryptedData);
		byte[] decValue = dcipher.doFinal(decordedValue);
		return new String(decValue);
	}
	private static SecretKeySpec generateKey(String secret) {
		byte[] keyValue = getKeyBytes(secret);
		return new SecretKeySpec(keyValue, ALGO);
	}
	public static void setCiphers(String secret, String writeFileLocation) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		SecretKeySpec key = generateKey(secret);
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		filePath = writeFileLocation;
	}
	public static void writeToFile(String data) throws FileNotFoundException, IOException {
		//setCiphers(secret);
		InputStream is = new ByteArrayInputStream(data.getBytes());
		encrypt(is, new FileOutputStream(filePath + "encrypted.dat"));
	}
	public static String readFromFile(String secret, String fileLocation) throws FileNotFoundException, IOException {
		String out = null;
		//setCiphers(secret);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		decrypt(new FileInputStream(filePath + "encrypted.dat"), os);
		out = os.toString();
		return out;
	}
	public static void main(String[] args) {
		try {
			setCiphers("difficultpasswo1", "filepath");
			String cipher = encrypt("malvika");
			String plainText = decrypt("oon+QniaFyEapuT11RoGjw==");
			System.out.println("cipher" + cipher);
			System.out.println("printing user name" + plainText);
			//encrypt(new FileInputStream(filePath+ "cleartext.txt"), new FileOutputStream(filePath + "encrypted.dat"));
			//decrypt(new FileInputStream(filePath+ "encrypted.dat"), new FileOutputStream(filePath+ "cleartext-reversed.txt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void encrypt(InputStream is, OutputStream os) throws IOException {
		byte[] buf = new byte[1024];
		// bytes at this stream are first encoded
		os = new CipherOutputStream(os, ecipher);
		// read in the clear text and write to out to encrypt
		int numRead = 0;
		while ((numRead = is.read(buf)) >= 0) {
			os.write(buf, 0, numRead);
		}
		// close all streams
		os.close();
	}
	private static void decrypt(InputStream is, OutputStream os) throws IOException {
		byte[] buf = new byte[1024];
		// bytes read from stream will be decrypted
		CipherInputStream cis = new CipherInputStream(is, dcipher);
		// read in the decrypted bytes and write the clear text to out
		int numRead = 0;
		while ((numRead = cis.read(buf)) >= 0) {
			os.write(buf, 0, numRead);
		}
		// close all streams
		cis.close();
		is.close();
		os.close();
	}
}