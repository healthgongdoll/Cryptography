package asymmetric;
import java.math.BigInteger;

public class XGCD {

    // Assumes a and b are positive
    public static BigInteger[] xgcd(BigInteger a, BigInteger b) {
	BigInteger x = a, y=b;
	BigInteger[] qrem = new BigInteger[2];
	BigInteger[] result = new BigInteger[3];
	BigInteger x0 = BigInteger.ONE, x1 = BigInteger.ZERO;
	BigInteger y0 = BigInteger.ZERO, y1 = BigInteger.ONE;
	while (true){
	    qrem = x.divideAndRemainder(y); x = qrem[1];
	    x0 = x0.subtract(y0.multiply(qrem[0]));
	    x1 = x1.subtract(y1.multiply(qrem[0]));
	    if (x.equals(BigInteger.ZERO)) {result[0]=y; result[1]=y0; result[2]=y1; return result;};
	    qrem = y.divideAndRemainder(x); y = qrem[1];
	    y0 = y0.subtract(x0.multiply(qrem[0]));
	    y1 = y1.subtract(x1.multiply(qrem[0]));
	    if (y.equals(BigInteger.ZERO)) {result[0]=x; result[1]=x0; result[2]=x1; return result;};
	}
    }

    public static void main(String[] args) {
	BigInteger a = new BigInteger("9");
	BigInteger b = new BigInteger("13");
	BigInteger[] result = new BigInteger[3];
	System.out.println("a = " + a.toString() + ";   b = " + b.toString());
	result = xgcd(a,b);
	System.out.println("xgcd = " + result[0].toString() + 
			   "  [" + result[1].toString() + ", " + result[2].toString() + "]");
    }
}

/////PART 1 ENDS//////////////////////////////////////////////////////////////////////////////////////////////

		//We can implement 2RSA by choosing a modulus n and two public exponents e1 and e2. In this case,
		//encryption is done using: c1 = me1 (mod n) followed by c2 = c1e2 (mod n). Is 2RSA stronger than 1RSA? Juystify your answer. 
		// Answer: no difference since (m^e1)^e2 == M^(e1*e2)
	
	////PART 3 ENDS///////////////////////////////////////////////////////////////////////////////////////////////
		//The exponents e = 1 and e = 2 should not be used in RSA. Why?
			//If we use e = 1 in RSA and compute c = m1
			//(mod n), then c is the
			//plaintext!
			//In the case of e = 2, We know that ¥õ(n) = (p-1)(q-1)
			// then GCD(e,¥õ(n))!= 1) since 2 is not prime
	
	///PART 4/////////////////////////////////////////////////////////////////////////////////////////////////////
	//If they are not equal and not relatively prime they can be attacked, and if they are not prime factoring them would be easier
	// as there exists multiple products that give the same result.