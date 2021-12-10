package exam;

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

public class mac {
	
	public static void main(String[]args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		byte[]  m = "Why do we tell actors to break a leg? because every play has a cast".getBytes();
		
		byte[] k = CryptoTools.hexToBytes("34567abcdef0321134567abcdef03211");
		byte[] iv = CryptoTools.hexToBytes("44668abddef1321134568abcdef13221");

		m = Arrays.copyOf(m, 80);
		System.out.println(k.length);
		byte[] mac = new byte[16];
		int index = 0;
		for(int i = 0; i<5; i++)
		{
			byte[] part = Arrays.copyOfRange(m, index, index+16);
		
				mac = aesDecryptionCBCNoPadding(part,iv,k);
				iv = mac;
				index += 16;
		
		}
		
		System.out.println(CryptoTools.bytesToHex(mac));
		
	}
	public static byte[] aesDecryptionCBCNoPadding(byte[] text, byte[] iv, byte[] key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secret = new SecretKeySpec(key, "AES");
		Cipher method = Cipher.getInstance("AES/CBC/NoPadding");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);
		method.init(Cipher.ENCRYPT_MODE, secret, aps);
		byte[] decrypt = method.doFinal(text);

		for (int i = 0; i < decrypt.length; i++) {
			System.out.print((char) decrypt[i]);
		}

		System.out.println();
		return decrypt;
	}


}
