package hash;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import util.CryptoTools;

public class ddd {

	/**
	 * IN this class, we establish the integrity of a message that Alice sent. 
	 * She signed the message with her private key, obtained s and then encrypted s with Bob's public key to get s'. 
	 * She did the same thing with her message to get mPrime. 
	 * 
	 * The approach is to decrypt the message and sPrime using Bob's private key. That would give us m and s. 
	 * Then you basically decrypt s using Alice's public key and if it matches m, you gucci. 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		//Alice's Message which she did not hash. 
		//mPrime is the message encrypted using Bob's public key. 
		//Sprime is the signature encrypted using Bob's public key. 
		BigInteger mPrime = new BigInteger("418726553997094258577980055061305150940547956");
		BigInteger sPrime = new BigInteger("749142649641548101520133634736865752883277237");
		
		//Alice's public parameters
		BigInteger nA = new BigInteger("171024704183616109700818066925197841516671277");
		BigInteger eA = new BigInteger("1571");
		
		
		
		//Bob's Parameters
	
		BigInteger pB = new BigInteger("98763457697834568934613");
		BigInteger qB = new BigInteger("8495789457893457345793");
		BigInteger eB = new BigInteger("87697");
		BigInteger nB = pB.multiply(qB);
		BigInteger pMinus1 = pB.subtract(BigInteger.ONE);
		BigInteger qMinus1 = qB.subtract(BigInteger.ONE);
		BigInteger phiB = pMinus1.multiply(qMinus1);
		BigInteger dB = eB.modInverse(phiB);
		
		
		//Part1: Decrypt the message mPrime using Bob's private key
		
		BigInteger messagePt = mPrime.modPow(dB,nB);
		System.out.println("Decrypted Message: "+messagePt);
		
		//Part2: Decrypt the signature using Bob's private key, this will give us s.
		BigInteger signatureFirstLayer = sPrime.modPow(dB, nB);
	
		System.out.println("Decrypted Signature: "+signatureFirstLayer);
		
		//Part3: Decrypting signatureFirstLayer using Alice's public key
		BigInteger unsignedMessage = signatureFirstLayer.modPow(eA, nA);
		
		System.out.println("Unsigned Message: "+unsignedMessage);
		
		
		
	}
}
