package symmetric;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class CBCNewModeDoubleArray {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] key = CryptoTools.hexToBytes("6B79466F724D4F50");
		byte[] iv = CryptoTools.hexToBytes("6976466F724D4F50");
		byte[] ciphertext = CryptoTools.hexToBytes("437DBAB5607137A5CFC1031114634087");
		ArrayList<String> answer = new ArrayList<>();

		int length = ciphertext.length / 8;
		byte[][] arr = new byte[length][8];
		//Separate the block
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = ciphertext[i * 8 + j];
			}
		}
		// Decryption 
		for (int i = arr.length - 1; i > 0; i--) {
			byte[] dt = desDecryptionECBNoPadding(arr[i], key);
			byte[] neg = negation(arr[i - 1]);
			byte[] pt = xor(neg, dt);
			String newString = toString(pt);
			answer.add(newString);
		}
		
		byte[] dt = desDecryptionECBNoPadding(arr[0],key);
		byte[] neg = negation(iv);
		byte[] pt = xor(neg,dt);
		String firstSeg = toString(pt);
		answer.add(firstSeg);
		
		for(int i = answer.size()-1;i>=0 ; i--)
		{
			System.out.print(answer.get(i));
		}
	}

	public static void printArr(byte[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print((char) (arr[i]));
		}
		System.out.println();
	}

	public static String toString(byte[] arr) {
		String answer = "";
		for(int i = 0; i< arr.length;i++)
		{
			answer += (char) arr[i];
		}
		
		return answer;
	}

	public static byte[] xor(byte[] arr1, byte[] arr2) {
		byte[] arr3 = arr1.clone();
		for (int i = 0; i < arr1.length; i++) {
			arr3[i] = (byte) (arr1[i] ^ arr2[i]);
		}

		return arr3;
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

}
