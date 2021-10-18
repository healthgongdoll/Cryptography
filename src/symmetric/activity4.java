package symmetric;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class activity4 {
	public static void main(String[]args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] key = CryptoTools.hexToBytes("6B79466F724D4F50");
		byte[] iv = CryptoTools.hexToBytes("6976466F724D4F50");
		byte[] cipherText = CryptoTools.hexToBytes("437DBAB5607137A5CFC1031114634087");
		
		Key secret = new SecretKeySpec(key,"DES");
		Cipher ct = Cipher.getInstance("DES/CBC/NoPadding");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);
		ct.init(Cipher.DECRYPT_MODE, secret,aps);
		
		byte[] plaintext = ct.doFinal(cipherText);
		
		for(int i = 0; i<plaintext.length;i++)
		{
			plaintext[i] = (byte) ~plaintext[i];
		}
		
		for(int i = 0; i<plaintext.length;i++)
		{
			System.out.print((char)plaintext[i]);
		}
	}
	

}
