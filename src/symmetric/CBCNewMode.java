package symmetric;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class CBCNewMode {
	static byte[] prev;
	static byte[] prevC;

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] key = CryptoTools.hexToBytes("6B79466F724D4F50");
		byte[] IV = CryptoTools.hexToBytes("6976466F724D4F50");
		byte[] ciphertext = CryptoTools.hexToBytes("437DBAB5607137A5CFC1031114634087");
		byte[] nIV = negation(IV);
		boolean initial = true;
		// 12345678 9129312903801923890
		printArr(ciphertext);
		for (int i = 0; i < ciphertext.length; i++) {
			if (i % 8 == 0) {
				byte[] parts = new byte[8];
				for (int j = 0; j < 8; j++) {
					parts[j] = ciphertext[j + i];
				}
				if (initial == true) {
					prevC = parts;
					prev = desDecryptionCBCNoPadding(parts, nIV, key);
					printArr(prev); // i get Traditio
					initial = false;
				} else {
					byte[] neg = negation(prevC); // negate the part 67 125 -70 -75 96 113 55 -91 (original ciphertext)
					prev = desDecryptionCBCNoPadding(parts, neg, key);
					printArr(parts);
					prevC = parts;
				}
			}
		}
		for (int i = 0; i < prev.length; i++) {
			System.out.print((char) prev[i]);
		}
	}

	public static byte[] xor(byte[] arr1, byte[] arr2) {
		byte[] arr3 = arr1.clone();
		for (int i = 0; i < arr1.length; i++) {
			arr3[i] = (byte) (arr1[i] ^ arr2[i]);
		}

		return arr3;
	}

	public static void printArr(byte[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print((char)(arr[i]));
		}
		System.out.println();
	}

	public static byte[] negation(byte[] arr) {
		byte[] negationArr = arr.clone();

		for (int i = 0; i < arr.length; i++) {
			negationArr[i] = (byte) ~negationArr[i];
		}

		return negationArr;
	}

	public static byte[] desDecryptionECBNoPadding(byte[] text, byte[] key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secret = new SecretKeySpec(key, "DES");
		Cipher method = Cipher.getInstance("DES/ECB/NoPadding");
		method.init(Cipher.DECRYPT_MODE, secret);
		byte[] decrypt = method.doFinal(text);

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

	public static byte[] aesDecryptionPKCS5(byte[] text, byte[] iv, byte[] key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secret = new SecretKeySpec(key, "AES");
		Cipher method = Cipher.getInstance("AES/CBC/PKCS5Padding");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);
		method.init(Cipher.DECRYPT_MODE, secret, aps);
		byte[] decrypt = method.doFinal(text);

		return decrypt;
	}

}
