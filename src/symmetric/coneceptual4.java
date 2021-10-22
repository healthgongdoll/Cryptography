package symmetric;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class coneceptual4 {
	public static void main(String[]args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] plaintext = "Facebook".getBytes();
		byte[] key = {-1,-1,-1,-1,-1,-1,-1,-1};
		printArr(key);
		
		byte[] dt1 = desDecryptionECBNoPadding(plaintext,key);
			
		printArr(dt1);
		
		byte[] dt2 = desDecryptionECBNoPadding(dt1,key);
		
		printArr(dt2);
	}
	public static void printArr(byte[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print((char)(arr[i]));
		}
		System.out.println();
	}
	public static byte[] desDecryptionECBNoPadding(byte[] text, byte[] key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secret = new SecretKeySpec(key, "DES");
		Cipher method = Cipher.getInstance("DES/ECB/NoPadding");
		method.init(Cipher.ENCRYPT_MODE, secret);
		byte[] decrypt = method.doFinal(text);

		return decrypt;
	}
}
