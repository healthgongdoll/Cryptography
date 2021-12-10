package symmetric;

import java.math.BigInteger;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class activity5 {
	public static void main(String[] args) throws Exception {
		byte[] key = "FACEBOOK".getBytes();

 // HEX
		
		byte[]ct = CryptoTools.hexToBytes("8A9FF0E2CD27DA4DC7F0C810E73D0E3B3B27CA03762BAE85597995997E625BDF0FEC655994EDD4B0851D7955B3F66717A52F83D01D73ABD9C593DA8C8CCBB073BB19E78442D9AA6D13B307EC0E8EA191E6A21897A82F1A643DC3BE0E12854D01C6006AA1D0EB1B94CAC573908018F284");
		
		
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
