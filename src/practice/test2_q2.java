package practice;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class test2_q2 {
	public static void main(String[]args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		byte[]key = CryptoTools.hexToBytes("4F75725269676874");
		byte[]iv = CryptoTools.hexToBytes("496E566563746F72");
		byte[]ciphertext = CryptoTools.hexToBytes("7AA38A029E773CBBC188A9FCEADAE99DA560B784C99AFEF2");
		byte[]modciphertext = ciphertext.clone();
		
		
		
		for(int i = 0; i<8; i++)
		{
			modciphertext[i] = (byte) (modciphertext[i] ^ iv[i]);
		}
		//check
		for(int i = 0; i<8; i++)
		{
			System.out.print(ciphertext[i]+" ");
			System.out.print(modciphertext[i]+ " ");
		}
		System.out.println();
		
		int index = 8; 
		for(int i = index; i<16; i++)
		{
			modciphertext[i] = (byte) (ciphertext[i] ^ ciphertext[i-8]);
		}
		
		//16 check
		for(int i = index; i<16; i++)
		{
			System.out.print(ciphertext[i]+" ");
			System.out.print(modciphertext[i]+ " ");
		}
		System.out.println();
		
		int index1 = 16; 
		for(int i =index1; i<24; i++)
		{
			modciphertext[i] = (byte) (ciphertext[i] ^ ciphertext[i-8]);
		}
		
		//24 check
		for(int i = index1; i<24; i++)
		{
			System.out.print(ciphertext[i]+" ");
			System.out.print(modciphertext[i]+ " ");
		}
		System.out.println();
		
		
		//answer ----------------
		byte[]plaintext = desDecryptionECBNoPadding(modciphertext,key);
		
		
		
		for(int i = 0; i<plaintext.length;i++)
		{
			System.out.print((char)plaintext[i]);
		}
		
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
