package asymmetric;

import java.math.BigInteger;

/**
 * CRT decryption works like this: 
 * Obtain the following values: 
 * dP = (1/e) mod(p-1)
 * dQ = (1/e) mod(q-1)
 * qInv = (1/q)mod p
 * 
 * To obtain the message m given c: 
 * m1 = (c^dP)%p
 * m2 = (c^dQ)%q
 * h = qInv.(m1-m2)%p
 * m = m2 + hq
 * 
 * @author owner
 *
 */
public class Pratice {
	
	public static void main(String[] args) {
		BigInteger p = new BigInteger("137");
		BigInteger pMinus1 = p.subtract(BigInteger.ONE);
		BigInteger q = new BigInteger("131");
		BigInteger qMinus1 = q.subtract(BigInteger.ONE);
		BigInteger n = p.multiply(q); //17947
		BigInteger e = new BigInteger("3");
		BigInteger d = new BigInteger("11787");
		
		//Ciphertext
		BigInteger c = new BigInteger("8363");
		
		//To decrypt c, we would have to compute c^11787 %17947 and frankly, we don't wish to do that. So we apply the CRT approach. 
		
		BigInteger dP = e.modInverse(pMinus1);
		BigInteger dQ = e.modInverse(qMinus1);
		BigInteger qInv = q.modInverse(p);
		
		//Let us now do the decryption thing
		
		BigInteger m1 = c.modPow(dP, p);
		BigInteger m2 = c.modPow(dQ, q);
		BigInteger m1Minusm2 = m1.subtract(m2);
		BigInteger h = qInv.multiply((m1Minusm2)).mod(p);
		
		BigInteger m = m2.add(h.multiply(q));
		
		System.out.println(m);
		
	}

}
