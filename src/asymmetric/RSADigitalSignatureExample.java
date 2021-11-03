package asymmetric;

import java.security.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import util.CryptoTools;

public class RSADigitalSignatureExample {
	
	public static void main(String[]args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		//Bob Generate Public & Private Key Pairs 
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024); // Key Size
		KeyPair kp = kpg.generateKeyPair(); 
		PublicKey public_key = kp.getPublic();
		PrivateKey private_key = kp.getPrivate();
		
		byte[] message = "Hello This is Bob".getBytes();
		Cipher cipher_bob = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher_bob.init(Cipher.ENCRYPT_MODE, private_key);
		byte[] signature = cipher_bob.doFinal(message); // generate the signature
		
		//Bob sends (message,signature)
		
		//Alice Decryption using bob's public key 
		
		Cipher cipher_alice = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher_alice.init(Cipher.DECRYPT_MODE, public_key);
		byte[] message_p = cipher_alice.doFinal(signature);
		
		if(CryptoTools.bytesToHex(message).equals(CryptoTools.bytesToHex(message_p))) {
			System.out.println("Signature Verified");
		}else
		{
			System.out.println("Signature Failed!");
		}
		System.out.println(CryptoTools.bytesToHex(message));
		System.out.println(CryptoTools.bytesToHex(message_p));
		
	}
	
	
	

}
