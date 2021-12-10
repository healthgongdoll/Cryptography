package symmetric;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class saemode {
	public static void main(String[]args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] ct = CryptoTools.hexToBytes("7AA38A029E773CBBC188A9FCEADAE99DA560B784C99AFEF2");
		byte[] key = CryptoTools.hexToBytes("4F75725269676874");
		byte[] iv = CryptoTools.hexToBytes("496E566563746F72");
		
		//64 bit = 8 byte 3 block
		System.out.println(ct.length);
		
		byte[]original = ct.clone();
		
		for(int i = 16; i<24; i++)
		{
			original[i] = (byte) (ct[i] ^ ct[i-8]);
		}
		
		for(int i = 8; i<16; i++)
		{
			original[i] = (byte) (ct[i] ^ ct[i-8]);
		}
		
		for(int i = 0; i<8; i++)
		{
			original[i] = (byte) (ct[i] ^ iv[i]);
		}
		
		
		desDecryptionECBNoPadding(original,key);
		
		
		
	}
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
}
