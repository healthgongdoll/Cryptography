package asymmetric;

import java.math.BigInteger;

public class pa4 {
	public static void main(String[]args)
	{
		// Find the number x!
		// Use Chinese Remainder Theorem 
		// Each pair of modulo must not have a common factor 
		// GCD must be 1 
		/*
		 * x = 3 mod 5
		 * x = 1 mod 7
		 * x = 6 mod 8
		 * 
		 * gcd(5,7) = 1; 
		 * gcd(5,8) = 1;
		 * ...
		 * N = n1*n2*n3
		 * bi(remainder)        Ni(N/Ni)        xi                     biNiXi
		 * 3                     7*8           56 * x1 = 1 (mod 5)     3 * 56 * x1    
		 * 1                     5*8           40 * x2 = 1 (mod 7)
		 * 6                     5*7           35 * x3 = 1 (mod 8)
		 *  
		 *  
		 *  in Our Example
		 *  
		 *  bi(remainder)     Ni (N/Ni)         xi
		 *  r1                  num2           num2 *x1 = 1 (mod num1)
		 *  r2                  num1           num1 *x2 = 1 (mod num2) 
		 *  
		 *  x = summnation (biNixi)  mod N
		 */
		
		BigInteger num1 = new BigInteger("1055827021987");
		BigInteger num2 = new BigInteger("973491987203");
		BigInteger r1 = new BigInteger("365944767426");
		BigInteger r2 = new BigInteger("698856040412");
		
		BigInteger x1 = num2.modInverse(num1);
		BigInteger x2 = num1.modInverse(num2);
		
		BigInteger b1n2x1 = r1.multiply(x1).multiply(num2);
		BigInteger b2n1x2 = r2.multiply(num1).multiply(x2);
		
		BigInteger x = b1n2x1.add(b2n1x2).mod(num1.multiply(num2));
		
		System.out.println(x);
		
	}
}
