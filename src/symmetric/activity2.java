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

public class activity2 {
	public static void main(String[]args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] key = "DO NOT TELL EVE!".getBytes();
		byte[] iv = CryptoTools.hexToBytes("20FC19123087BF6CAC8D0F1254123004");
		byte[] cipherText = CryptoTools.hexToBytes("3188073EA5DB3F5C05B6307B3595607135F5D4B22F2C3EB710AA31377F78B997");
		
		Key secret = new SecretKeySpec(key,"AES");
		Cipher pt = Cipher.getInstance("AES/CBC/PKCS5Padding");
		AlgorithmParameterSpec aps= new IvParameterSpec(iv);
		pt.init(Cipher.DECRYPT_MODE,secret,aps);
		
		byte[] plaintext = pt.doFinal(cipherText);
		
		for(int i = 0; i<plaintext.length;i++)
		{
			System.out.print((char) plaintext[i]);
		}
		
	}

}
