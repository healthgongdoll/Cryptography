package practice;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class test2q1 {
	public static void main(String[]args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] key = CryptoTools.hexToBytes("444F204E4F542054454C4C2045564521");
		byte[] iv = CryptoTools.hexToBytes("20FC19123087BF6CAC8D0F1254123004");
		byte[]ciphertext = CryptoTools.hexToBytes("FB0692B011F74F8BF77EDE2630852C1700C204407EDF2222D965F74A8BCEB236");
		
		byte[] plaintext = aesDecryptionPKCS5(ciphertext,iv,key);
		
		for(int i = 0; i<plaintext.length;i++)
		{
			System.out.print(((char)plaintext[i]) +" ");
		}
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
