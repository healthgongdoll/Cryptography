package practice;

import util.CryptoTools;

public class test_q3 {
	public static void main(String[]args)
	{
		byte[] ct1 = CryptoTools.hexToBytes("3D48044D421349564A1541054204131C"); //alice key + plaintext
		byte[] ct2 = CryptoTools.hexToBytes("3D54024D531442454C0941175404150A"); //alice key + palintext 
		byte[] pt1contain = "bridge".getBytes(); 
		
		
		byte[] plaintextxor = xor(ct1,ct2); // plaintext1 ^ plaintext2 
		System.out.println(plaintextxor.length);
		System.out.println(pt1contain.length);
		
	}
	public static byte[] xor(byte[] arr1, byte[] arr2) {
		byte[] arr3 = arr1.clone();
		for (int i = 0; i < arr1.length; i++) {
			arr3[i] = (byte) (arr1[i] ^ arr2[i]);
		}

		return arr3;
	}
}
