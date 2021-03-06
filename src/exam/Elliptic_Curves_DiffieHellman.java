package exam;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class Elliptic_Curves_DiffieHellman {
	public static void main(String[]args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		//Alice Generating it's public/private keypair
		KeyFactory factory = KeyFactory.getInstance("EC"); //default SunEC
		
		//Alice Public Key
		KeySpec ApublicSpec = new X509EncodedKeySpec(CryptoTools.hexToBytes("3059301306072A8648CE3D020106082A8648CE3D0301070342000450C35C2FB11926C2C91E089CFC743F9D942EE14B8D42E25AE6588C4F93DDFF6ACDF520F74AF3E2500EF2A5E2C346D4DA7E92C1F89AD9FD4F3ED1B97DC3F39DC8"));
		PublicKey ApublicKey = factory.generatePublic(ApublicSpec);
		
		byte[] Apublic = ApublicKey.getEncoded();
		//Alice Private Key 
		KeySpec AprivateSpec = new PKCS8EncodedKeySpec(CryptoTools.hexToBytes("3041020100301306072A8648CE3D020106082A8648CE3D0301070427302502010104200FE89D3070EECF985F971851B088EC97605A08D037F3CF3463FED25BCE0037B5"));
		PrivateKey AprivateKey = factory.generatePrivate(AprivateSpec);
		
		//Bob Public Key 
		KeySpec BpublicSpec = new X509EncodedKeySpec(CryptoTools.hexToBytes("3059301306072A8648CE3D020106082A8648CE3D03010703420004678DF0E72D7FC86006174E506B1729081E5D1201936EBA8A39E8741E4F713F8C29AE2E62038D95B36A585E2A87FEA73BE482611115457A3D3823EA5D79E31154"));
		PublicKey BpublicKey = factory.generatePublic(BpublicSpec);
		
		byte[] Bpublic = BpublicKey.getEncoded();
		
		KeySpec BprivateSpec = new PKCS8EncodedKeySpec(CryptoTools.hexToBytes("3041020100301306072A8648CE3D020106082A8648CE3D030107042730250201010420090145EB296FD96158EDF5E59D20EBB8E7332BBE150784D91900DB2006980127"));
		PrivateKey BprivateKey = factory.generatePrivate(BprivateSpec);
		
		//Alice Performing Received Bob's Public Key  and using her private key to compute ECDH Algorithm to come up secret 
		KeyAgreement ka = KeyAgreement.getInstance("ECDH");
		ka.init(AprivateKey);
		ka.doPhase(BpublicKey, true);
		byte[] shared_secret = ka.generateSecret();
		
		/*
		 * Hash
		 */
		
		MessageDigest hash = MessageDigest.getInstance("SHA-256");
		hash.update(shared_secret);
		List<ByteBuffer> keys = Arrays.asList(ByteBuffer.wrap(Apublic),ByteBuffer.wrap(Bpublic));
		Collections.sort(keys);
		hash.update(keys.get(0));
		hash.update(keys.get(1));
		
		byte[]derivedKey = hash.digest();
		System.out.println(CryptoTools.bytesToHex(derivedKey));
		
		//Bob Performing Received Alice's Public key and using his private key to Compute ECDH Algorithm to come up secret 
		ka = KeyAgreement.getInstance("ECDH");
		ka.init(BprivateKey);
		ka.doPhase(ApublicKey, true);
		byte[] shared_secret2 = ka.generateSecret();
		
		System.out.println("Alice's Computed Secret Key: " + new String(CryptoTools.bytesToHex(shared_secret)));
		System.out.println("Bob's Computed Secret Key: " + new String(CryptoTools.bytesToHex(shared_secret2)));
		
		//Alice can use the shared_secret to create AES instance 
		
		byte[] iv = CryptoTools.hexToBytes("4000000001000000000C00000001000C");
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec secretKey = new SecretKeySpec(shared_secret, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
		
		byte[] ct = CryptoTools.hexToBytes("B1803ED24B595CCB11AA39473DC7B10B");
		
		//Bob perform decryption using shared secret 
		
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey,ivspec);
		byte[] plaintext = cipher.doFinal(ct);
		
		System.out.println("Original Message is: " + new String(plaintext));
		
		
		
		
	}

}
