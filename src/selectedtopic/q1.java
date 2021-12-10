package selectedtopic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class q1 {
	/*
	 * Examine the following symmetric key distribution (SKD) protocol in which A
	 * generates a session key KS and shares it with B:
	 * 
	 * A -> KDC: IDA || IDB || E(KA, KS) KDC -> A: IDB || T where T = E(KB, IDA ||
	 * KS) A -> B: T Now that A and B share KS, show how they can use it to:
	 * authenticate each other. exchange confidential messages. ensure the content
	 * integrity of their communication.
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, IOException {

		// KDC
		byte[] aliceKey = "This is AliceKey".getBytes();
		byte[] bobKey = "This is Bobs Key".getBytes();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		/*
		 * A-> KDC IDA || IDB || E(KA,KS)
		 */
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256);
		SecretKey secretKey = keyGen.generateKey();
		byte[] SessionKey = secretKey.getEncoded();
		byte[] aliceId = "This is from Alice".getBytes();
		byte[] bobId = "This is from Bob".getBytes();
		// Generated Session Key
		System.out.println("Generated Session Key is:" + new String(CryptoTools.bytesToHex(SessionKey)));

		// Encryption Session Key with Alice Key
		// AES allows 128, 192 or 256 bit key length
		SecretKeySpec secretA = new SecretKeySpec(aliceKey, "AES");
		Cipher ec = Cipher.getInstance("AES/ECB/PKCS5Padding");
		ec.init(Cipher.ENCRYPT_MODE, secretA);
		byte[] encryptedKey = ec.doFinal(SessionKey);
		System.out.println("Generated Encrypted Key is:" + new String(CryptoTools.bytesToHex(encryptedKey)));
		
		/*
		 * KDC Receives AliceID,BobID,EncryptedSessionKey must Decrypt Encrypted Session
		 * Key to get Session Key must Encrypt the Session Key with Bob's Key which
		 * Alice's ID KDC -> A: IDB || T where T = E(KB, IDA || KS)
		 * 
		 * Known Fact: KDC has all the User's Key
		 * 
		 */
			
		//Decrypt Session Key by using Alice Key 
		ec.init(Cipher.DECRYPT_MODE,secretA);
		byte[] decryptedSessionKey = ec.doFinal(encryptedKey);
		System.out.println("KDC Decrypted Session Key is:" + new String(CryptoTools.bytesToHex(decryptedSessionKey)));
		
		// IDA || Session Key
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		os.write(aliceId);
		os.write(decryptedSessionKey);
		byte[] aIDandSkey = os.toByteArray();
		
		//Generate T = Encryption with Bob's Key IDA || KS
		SecretKeySpec secretB = new SecretKeySpec(bobKey, "AES");
		ec.init(Cipher.ENCRYPT_MODE, secretB);
		byte[] ecryptAS = ec.doFinal(aIDandSkey);
		
		//Alice Send it to the Bob 
		
		/*
		 * Bob receives the T from the Alice 
		 * 
		 * Decrypts the T and get Session Key with Alice Key attached 
		 */
		
		ec.init(Cipher.DECRYPT_MODE,secretB);
		byte[] authentication = ec.doFinal(ecryptAS);
		byte[] sessionKey = Arrays.copyOfRange(authentication, 18, authentication.length);
		byte[] aIDcheck = Arrays.copyOfRange(authentication, 0, 17);
		System.out.println("Bob Decrypted the T is:" + new String(aIDcheck) +"Session Key: "+ new String(CryptoTools.bytesToHex(sessionKey)));
		
		
		
		
		
	}

}
