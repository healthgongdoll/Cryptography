package asymmetric;

import java.math.BigInteger;

public class DiffieHellman{
	public static void main(String[]args)
	{
		/**
		 * Activity 6
		 * They agree publicly on the algorithm parameter 
		 * p and g 
		 * Alice Private Key = aX
		 * Bob Private Key = bX 
		 * 
		 * Diffie Hellman Key Exchange 
		 * 
		 * Alice  				Bob
		 *  yellow             yellow
		 *  red                 blue
		 *  Orange              Cyan 
		 *  Cyan     Swap      Orange
		 *  red                 blue
		 *  Brown(Secret)      Brown (Secret) 
		 *  
		 *  Alice and Bob are using the Diffie Hellman key agreement protocol to establish a shared key.
		 *   They agree publicly on the algorithm parameters p and g. 
		 *  Alice then picks a private key aX and Bob picks bX. Here is the data
		 *  They then compute and exchange their public keys in order to arrive at a shared session key. 
		 *  Determine that session key and write it as a hex string.
		 *  
		 *  publicaliy known parameters are 
		 *  p and g 
		 *  
		 *  Alice 								Bob
		 *  PrivK = aX						PrivK = bX 
		 *  
		 * A = g^aX mod p 					 B =	g^bx mod p 
		 *  
		 *  				SWAP
		 *  
		 *  Sesion Key 
		 * 	B^aX mod P 						A ^ bX mod P
		 *  
		 *  
		 */
		//Publicly Known Factors 
		BigInteger p = new BigInteger("341769842231234673709819975074677605139");
		BigInteger g = new BigInteger("37186859139075205179672162892481226795");
		//Alice Private
		BigInteger aX = new BigInteger("83986164647417479907629397738411168307");
		//Bob Private 
		BigInteger bX = new BigInteger("140479748264028247931575653178988397140");
		
		//KpubA = A = alpha^a mod (n) = 2^5 (mod 29) = 3
		BigInteger pubKA = g.modPow(aX, p);
		//KpubB = B  = alpha^b mod (n)
		BigInteger pubKB = g.modPow(bX, p);
		
		
		BigInteger Kab = pubKB.modPow(aX,p);
		BigInteger Kba = pubKA.modPow(bX, p);
		
		//Hex String
		System.out.println(Kab.toString(16));
		System.out.println(Kba.toString(16));
		
		
		
	}
}
