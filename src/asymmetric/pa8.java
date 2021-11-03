package asymmetric;

import java.math.BigInteger;

public class pa8 {
	/*
	 * Write a Java program that demonstrates for any given n and e (public key) in RSA Digital Signature Scheme, 
	 * Eve can come up with a message "X" and signature of "X" (denoted as S) that would pass the verification step.  
	 */

	public static void main(String[]args)
	{
		BigInteger n = new BigInteger("33");  //p = 3 * 11;
		BigInteger e = new BigInteger("3");
		BigInteger p = new BigInteger("3");
		BigInteger q = new BigInteger("11");
		BigInteger phi = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
		BigInteger d = e.modInverse(phi);
		System.out.println(d);
		//Generate Message x and signature
		byte[] x= "Hello This is Me".getBytes();
		BigInteger s = new BigInteger(x);
		BigInteger bX = new BigInteger(x);
		System.out.println(bX);
		s = bX.modPow(d, n);
		
		//Eve Comes up 
		// Choose Signature 
		// let's say 22
		s = new BigInteger("12");
		//Compute message
		bX = s.modPow(e, n);
		
		
		//Alice Signature determination 
		BigInteger xA = s.modPow(e, n);
		if(xA.equals(bX))
		{
			System.out.println("Valid Signature");
		}else
		{
			System.out.println("Not Valid Signature");
		}
		
		
		
		
		
	}
}
