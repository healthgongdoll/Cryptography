package practice;



import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class DES_PKCS5Padding_SAE {
	//Question 3 of the practice exam. 
	//SAE mode of operation
	public static void main(String[] args) throws Exception{

			byte[] ky = CryptoTools.hexToBytes("4F75725269676874");
			byte[] iv = CryptoTools.hexToBytes("496E566563746F72");
			Key secret = new SecretKeySpec(ky, "DES");
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			AlgorithmParameterSpec aps = new IvParameterSpec(iv);
			byte[] ct = CryptoTools.hexToBytes("A557E4C89356F55AD012625648BE3F22D5E777DAC5172E09");
			byte[] ecbCT = new byte[ct.length];
			
			//break everything down into 16 char blocks ct1,ct2,ct3
			//l = ct1.length
			/**
			 * Encryption engine 
			 */
			
			
			/**
			 * Decryption Engine
			 */
			for(int i=0;i<8;i++) {
				ecbCT[i] = (byte) (iv[i]^ct[i]);
			}
			 
			
			int second=8;
			int fromFirstBlock = 0;
			while(second<16) {
				ecbCT[second] = (byte) (ct[second]^ct[fromFirstBlock]);
				fromFirstBlock++;
				second++;
			}
	
			
			int third=16;
			int fromSecondBlock = 8;
			while(third<24) {
				ecbCT[third] = (byte) (ct[third]^ct[fromSecondBlock]);
				fromSecondBlock++;
				third++;
			}
			
			
			//System.out.println(ecbCT[23]);
			cipher.init(Cipher.DECRYPT_MODE, secret);
			//byte[] negated = cipher.doFinal(ct);
			//byte[] bk = new byte[negated.length];
			
			/*
			for(int i=0;i<negated.length;i++) {
				bk[i] = (byte) ~negated[i];
			}
			*/
			byte[]bk = cipher.doFinal(ecbCT);
			
			
			System.out.println("BK = " + new String(bk) );
			


	}

}
