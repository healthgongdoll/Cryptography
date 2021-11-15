package hash;

import java.math.BigInteger;

public class pa2 {
	
	public static void main(String[]args)
	{
		
		/*
		 * nA = 171024704183616109700818066925197841516671277
  		   eA = 1571

  		    pB = 98763457697834568934613
            qB = 8495789457893457345793
            eB = 87697
     Alice wants to send a message m to Bob. To ensure sender integrity, she signs the message iteself using her private key and obtains the signature s. (Side note: one typically signs the hash of the message, not the message iteself, but since her message is pretty short, this works too.) And to keep the contents of the message confidential, she encrypts both m and s using Bob's public key and sends the two ciphertexts m' and s' to Bob. He receives:
      m' = 418726553997094258577980055061305150940547956
     s' = 749142649641548101520133634736865752883277237
        Help him determine the message that Alice sent and assure him of its origin integrity.
        
		 */
		
		/*
		 * Alice -> Send m to Bob 
		 * Alice Signs hash(m) with her private key -> s
		 * 
		 * Alice encrypt both m and s using Bob's public key and sends the two ciphertexts m' and s; to Bob 
		 * 
		 * 
		 */
		
		// Alice 
		BigInteger nA = new BigInteger("171024704183616109700818066925197841516671277");
		BigInteger eA = new BigInteger("1571");
		
		//Bob Received and message is encrypted with Bob's public key -> which means it can be decrypted by Bob's private Key 
		
		BigInteger mB = new BigInteger("418726553997094258577980055061305150940547956");
		BigInteger sB = new BigInteger("749142649641548101520133634736865752883277237");
		
		//Bob's Known Factor 
		BigInteger pB = new BigInteger("98763457697834568934613");
		BigInteger qB = new BigInteger("8495789457893457345793");
		BigInteger eB = new BigInteger("87697");
		
		/*
		 * Key Generation Algorithm 
		 * Choose two large primes p,q
		 * Compute n = p * q 
		 * Compute phi(n) = (p-1)*(q-1)
		 * Select the public exponent e = {0,1,...,phi(n)-1} such that gcd(e,phi(n)) = 1
		 * Compute the private key d such that d * e = 1 mod phi(n) -> e^-1 mod phi(n)
		 * Return pubk = (n,e), privk = d
		 */
		
		BigInteger nB = pB.multiply(qB);
		BigInteger phiB = pB.subtract(BigInteger.ONE).multiply(qB.subtract(BigInteger.ONE));
		BigInteger dB = eB.modInverse(phiB);
		
		//Original Message Computed decrypt by using bob's Private key 
		BigInteger originalM = mB.modPow(dB, nB);
		BigInteger originalS = sB.modPow(dB, nB);
		
		System.out.println("OriginalMessage: "+originalM); //hashed message
		System.out.println("OriginalSignature: " + originalS); //Signature 
		
		BigInteger decrypt = originalS.modPow(eA, nA); //decrypt Signature and see if it is hased message;
		
		System.out.println("DecryptSignature: "+ decrypt);
		
		
		
		
	}

}
