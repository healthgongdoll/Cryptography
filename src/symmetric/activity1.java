package symmetric;

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

public class activity1 {
	public static void main(String[]args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] key = CryptoTools.hexToBytes("9F0DCEDB322F3C6873F9256E01376BA4");
		byte[] iv = CryptoTools.hexToBytes("20FC19123087BF6CAC8D0F1254123004");
		byte[] ct = CryptoTools.hexToBytes("F38ADBA8A7B4CC613578355032205D50");
		
		SecretKeySpec secret = new SecretKeySpec(key,"AES");
		Cipher pt = Cipher.getInstance("AES/CBC/PKCS5Padding");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);
		pt.init(Cipher.DECRYPT_MODE, secret,aps);
		
		System.out.println(pt.doFinal(ct));
		byte[]ptext = pt.doFinal(ct);
		
		for(int i = 0; i<ptext.length;i++)
		{
			
			System.out.print((char)ptext[i]);
		}
	}
}
