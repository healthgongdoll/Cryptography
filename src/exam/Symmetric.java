package exam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class Symmetric {
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		OTPLock();
		aesDecryptionPKCS5(CryptoTools.hexToBytes("F38ADBA8A7B4CC613578355032205D50"),
				CryptoTools.hexToBytes("20FC19123087BF6CAC8D0F1254123004"),
				CryptoTools.hexToBytes("9F0DCEDB322F3C6873F9256E01376BA4"));
		aesDecryptionPKCS5(CryptoTools.hexToBytes("3188073EA5DB3F5C05B6307B3595607135F5D4B22F2C3EB710AA31377F78B997"),
				CryptoTools.hexToBytes("20FC19123087BF6CAC8D0F1254123004"), "DO NOT TELL EVE!".getBytes());
		byte[] pt = desDecryptionECBNoPadding(desDecryptionECBNoPadding(CryptoTools.hexToBytes(
				"8A9FF0E2CD27DA4DC7F0C810E73D0E3B3B27CA03762BAE85597995997E625BDF0FEC655994EDD4B0851D7955B3F66717A52F83D01D73ABD9C593DA8C8CCBB073BB19E78442D9AA6D13B307EC0E8EA191E6A21897A82F1A643DC3BE0E12854D01C6006AA1D0EB1B94CAC573908018F284"),
				not("FACEBOOK".getBytes())), "FACEBOOK".getBytes());

		for (int i = 0; i < pt.length; i++) {
			System.out.print((char) pt[i]);
		}
		System.out.println();

		SAEModeOperation();
		OTPcrack();
		byte[] key = {-1,-1,-1,-1,-1,-1,-1,-1}; // Ãë¾àÅ° 
		System.out.println(CryptoTools.bytesToHex(desEncryptionECBNoPadding(desEncryptionECBNoPadding(CryptoTools.hexToBytes("F38ADBA8A7B4CC613578355032205D50"),key),key)));
	}

	/*
	 * OTP Problem from Test2 Alice and Bob use OTP to exchange 16-byte English
	 * Message using shared 16 B key
	 * 
	 * CT 1 = 3D48044D421349564A1541054204131C CT 2 =
	 * 3D54024D531442454C0941175404150A
	 * 
	 * 
	 */
	public static void OTPcrack() {
		byte[] ct1 = CryptoTools.hexToBytes("3D48044D421349564A1541054204131C");
		byte[] ct2 = CryptoTools.hexToBytes("3D54024D531442454C0941175404150A");

		byte[] pxor = ct1.clone();

		for (int i = 0; i < ct1.length; i++) {
			pxor[i] = (byte) (ct1[i] ^ ct2[i]);
		}
		byte[] word = "bridge".getBytes();

		for (int i = 0; i < pxor.length; i++) {
			byte[] block = Arrays.copyOfRange(pxor, i, i + word.length);
			byte[] newText = new byte[block.length];
			for (int j = 0; j < block.length; j++) {
				newText[j] = (byte) (word[j] ^ block[j]);
			}
			System.out.println(new String(newText));
		}
	}

	/*
	 * SAE Mode Operation Test 2 Operation
	 * 
	 */
	public static void SAEModeOperation() throws IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] key = CryptoTools.hexToBytes("4F75725269676874");
		byte[] iv = CryptoTools.hexToBytes("496E566563746F72");
		byte[] ciphertext = CryptoTools.hexToBytes("7AA38A029E773CBBC188A9FCEADAE99DA560B784C99AFEF2");
		byte[] modciphertext = ciphertext.clone();
		for (int i = 0; i < 8; i++)
		{
			modciphertext[i] = (byte) (modciphertext[i] ^ iv[i]);
		}
		int index = 8;
		for (int i = index; i < 16; i++)
		{
			modciphertext[i] = (byte) (ciphertext[i] ^ ciphertext[i - 8]);
		}
		int index1 = 16;
		for (int i = index1; i < 24; i++)
		{
			modciphertext[i] = (byte) (ciphertext[i] ^ ciphertext[i - 8]);
		}
		// answer ----------------
		byte[] plaintext = desDecryptionECBNoPadding(modciphertext, key);
	}

	/*
	 * York U ModeOperation
	 */
	public static void YorkModeOperation(byte[] ct, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		// 437DBAB5607137A5 CFC1031114634087
		System.out.println(ct.length);
		byte[] part = new byte[8];
		byte[] pt = ct.clone();
		int length = ct.length;
		int counter = ct.length;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				length = length - 1;
				part[j] = ct[length];

			}

			byte[] ptpart = desDecryptionECBNoPadding(part, key);
			byte[] prev = new byte[8];
			if (i == 2) {
				byte[] iv = CryptoTools.hexToBytes("6976466F724D4F50");
				for (int j = 0; j < 8; j++) {
					counter = counter - 1;
					pt[counter] = (byte) (ptpart[j] ^ iv[j]);

				}

			} else {
				for (int j = 0; j < 8; j++) {

					prev[i] = ct[--length];
				}
				for (int j = 0; j < 8; j++) {
					counter = counter - 1;
					pt[counter] = (byte) (ptpart[j] ^ prev[j]);

				}
			}
		}

		for (int i = 0; i < pt.length; i++) {
			System.out.print((char) pt[i]);
		}
		System.out.println();

	}

	public static void OTPLock() {
		byte[] alicelock = CryptoTools.hexToBytes("0A4F0C08003503492F247442105B5757");
		byte[] bobalicelock = CryptoTools.hexToBytes("5E2769286B507A69494B066252343579");
		byte[] boblock = CryptoTools.hexToBytes("170708454B1116002A2E2111725F5000");
		byte[] alicekey = alicelock.clone();
		for (int i = 0; i < boblock.length; i++) {
			alicekey[i] = (byte) (bobalicelock[i] ^ boblock[i]);
		}
		byte[] plaintext = alicekey.clone();
		for (int i = 0; i < plaintext.length; i++) {
			plaintext[i] = (byte) (alicekey[i] ^ alicelock[i]);
		}
		for (int i = 0; i < plaintext.length; i++) {
			System.out.print((char) plaintext[i]);
		}
		System.out.println();
	}

	// --------------------------------------------------------------------------------------------------------------------------------
	public static byte[] desDecryptionECBNoPadding(byte[] text, byte[] key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secret = new SecretKeySpec(key, "DES");
		Cipher method = Cipher.getInstance("DES/ECB/NoPadding");
		method.init(Cipher.DECRYPT_MODE, secret);
		byte[] decrypt = method.doFinal(text);
		for (int i = 0; i < decrypt.length; i++) {
			System.out.print((char) decrypt[i]);
		}

		System.out.println();
		return decrypt;
	}
	//----------------------------DES encryption--------------
	public static byte[] desEncryptionECBNoPadding(byte[] text, byte[] key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secret = new SecretKeySpec(key, "DES");
		Cipher method = Cipher.getInstance("DES/ECB/NoPadding");
		method.init(Cipher.ENCRYPT_MODE, secret);
		byte[] decrypt = method.doFinal(text);
		for (int i = 0; i < decrypt.length; i++) {
			System.out.print((char) decrypt[i]);
		}

		System.out.println();
		return decrypt;
	}

	public static byte[] desDecryptionCBCNoPadding(byte[] text, byte[] iv, byte[] key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secret = new SecretKeySpec(key, "DES");
		Cipher method = Cipher.getInstance("DES/CBC/NoPadding");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);
		method.init(Cipher.DECRYPT_MODE, secret, aps);
		byte[] decrypt = method.doFinal(text);

		for (int i = 0; i < decrypt.length; i++) {
			System.out.print((char) decrypt[i]);
		}

		System.out.println();
		return decrypt;
	}

	public static byte[] desEncryptionPKCS5(byte[] text, byte[] iv, byte[] key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secret = new SecretKeySpec(key, "DES");
		Cipher method = Cipher.getInstance("DES/CBC/PKCS5Padding");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);
		method.init(Cipher.ENCRYPT_MODE, secret, aps);
		byte[] encrypt = method.doFinal(text);

		return encrypt;
	}

	public static byte[] desDecryptionPKCS5(byte[] text, byte[] iv, byte[] key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secret = new SecretKeySpec(key, "DES");
		Cipher method = Cipher.getInstance("DES/CBC/PKCS5Padding");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);
		method.init(Cipher.DECRYPT_MODE, secret, aps);
		byte[] decrypt = method.doFinal(text);

		return decrypt;
	}

	public static byte[] aesEncryptionPKCS5(byte[] text, byte[] iv, byte[] key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secret = new SecretKeySpec(key, "AES");
		Cipher method = Cipher.getInstance("AES/CBC/PKCS5Padding");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);
		method.init(Cipher.ENCRYPT_MODE, secret, aps);
		byte[] encrypt = method.doFinal(text);

		return encrypt;
	}

	public static void aesDecryptionPKCS5(byte[] text, byte[] iv, byte[] key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secret = new SecretKeySpec(key, "AES");
		Cipher method = Cipher.getInstance("AES/CBC/PKCS5Padding");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);
		method.init(Cipher.DECRYPT_MODE, secret, aps);
		byte[] decrypt = method.doFinal(text);

		for (int i = 0; i < decrypt.length; i++) {
			System.out.print((char) decrypt[i]);
		}

		System.out.println();
	}

	public static byte[] not(byte[] text) {
		for (int i = 0; i < text.length; i++) {
			text[i] = (byte) ~text[i];
		}

		return text;
	}
}
