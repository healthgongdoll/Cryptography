package symmetric;

import java.math.BigInteger;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class activity5 {
	public static void main(String[] args) throws Exception {
		byte[] key = "FACEBOOK".getBytes();

		String ctHex = new String(CryptoTools.fileToBytes("data/test.txt")); // HEX
		
		byte[]ct = CryptoTools.hexToBytes(ctHex);
		
		
		//String ib = changeBit(binaryKey);
		//byte[] inverseKey = byteRemover(new BigInteger(ib,2).toByteArray());
		
		byte[] inverseKey = key.clone();
		
		for(int i = 0; i<inverseKey.length;i++)
		{
			System.out.print(~inverseKey[i]);
			inverseKey[i] = (byte)~inverseKey[i]; 
		}
		System.out.println();
		Key secret = new SecretKeySpec(inverseKey, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, secret);

		byte[] sct = cipher.doFinal(ct); //first 

		Key secret2 = new SecretKeySpec(key, "DES");
		Cipher cipher2 = Cipher.getInstance("DES/ECB/NoPadding");
		cipher2.init(Cipher.DECRYPT_MODE, secret2);

		byte[] plaintext = cipher2.doFinal(sct);

		for (int i = 0; i < plaintext.length; i++) {
			System.out.print((char) plaintext[i]);
		}
	}

	public static String changeBit(String str) {
		String bits = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '1') {
				bits += "0";
			} else if (str.charAt(i) == '0') {
				bits += "1";
			}
		}
		return bits;
	}
	public static byte[] byteRemover(byte[] extraByte){
		byte[] bt = new byte[8];
		for(int i=0; i< 8; i++ ){
			bt[i] = extraByte[i+1];
		}
		return bt;
	}
}
