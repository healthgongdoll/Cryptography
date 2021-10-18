package symmetric;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class conceptual3 {
	public static void main(String[]args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		byte[]plainText = "Facebook".getBytes(); // 2^8 = 256 bits 
		byte[]key = "universe".getBytes();
		
		Cipher encrypt = Cipher.getInstance("DES/ECB/NoPadding");
		Key ky = new SecretKeySpec(key,"DES");
		encrypt.init(Cipher.ENCRYPT_MODE,ky);
		byte[] cipherText = encrypt.doFinal(plainText);
		
		
		
		int random = (int) ((Math.random()*(63-0))+0);
		//String binInt = Integer.toBinaryString(random);
		//String binIntWithPadding = String.format("%64s",binInt).replaceAll(" ","0");
		//System.out.println(random);
		//System.out.println(binIntWithPadding);
		
		String ptBin = CryptoTools.bytesToBin(plainText);
		System.out.println("PB:" + ptBin);
		
		//Generated Random # and Binary bit
		//System.out.println(random);
		String flipedBit = flipBit(ptBin,0);
		
		System.out.println("AF:" + flipedBit);
		System.out.println("CB:" + CryptoTools.bytesToBin(cipherText));
		
		byte[] fbp = binaryToByte(flipedBit);
		
		for(int i = 0; i<fbp.length;i++)
		{
			System.out.print(fbp[i] + " ");
		}
		System.out.println();
		Cipher encrypt2 = Cipher.getInstance("DES/ECB/NoPadding");
		Key ky2 = new SecretKeySpec(key,"DES");
		encrypt2.init(Cipher.ENCRYPT_MODE,ky2);
		byte[] cbp = encrypt.doFinal(fbp);
		
		
		System.out.println("CF:" + CryptoTools.bytesToBin(cbp));
		
		int changebits = countFlip(CryptoTools.bytesToBin(cipherText),CryptoTools.bytesToBin(cbp));
		System.out.println("Change Bits: " + changebits);
		System.out.println("Change Bits Avg:" + (double) changebits/CryptoTools.bytesToBin(cipherText).length());
	}
	public static int countFlip(String s1, String s2)
	{
		int count = 0;
		for(int i = 0; i< s1.length();i++)
		{
			if(s1.charAt(i) != s2.charAt(i))
			{
				count++;
			}
		}
		return count; 
	}
	public static byte[] binaryToByte(String s1)
	{
		byte[] array = new BigInteger(s1,2).toByteArray();
		if(array[0] == 0 && s1.charAt(0) == '1') array = Arrays.copyOfRange(array, 1, array.length);
		return array;
	}
	public static String flipBit(String s1, int num)
	{
		char[] arr = s1.toCharArray();
		System.out.println(num);
		System.out.print("BF:");
		for(int i = 0; i<arr.length;i++)
		{
			System.out.print(arr[i]);
		}
		System.out.println();
		if(arr[num] == '1')
		{
		//	System.out.println("Hello");
			arr[num] = '0';
		}
		else 
			if(arr[num] == '0')
		{
		//	System.out.println("HI");
			arr[num] = '1';
		}
		
		return String.copyValueOf(arr);
	}

}
