package asymmetric;

import java.math.BigInteger;

//Java program to demonstrate working of extended
//Euclidean Algorithm

import java.util.*;
import java.lang.*;

public class ConceptualQ {
		public static void main(String[]args)
		{
			BigInteger n = new BigInteger("179");
			BigInteger eA = new BigInteger("9");
			BigInteger eB = new BigInteger("13");
//			BigInteger exA = new BigInteger("32");
//			BigInteger exB = new BigInteger("127");
			BigInteger cA = new BigInteger("32");
			BigInteger cB = new BigInteger("127");
			
			BigInteger [] result = extendedEuclidean(eA,eB);
			System.out.println(result[1]);
			System.out.println(result[2]);
			BigInteger i = cB.modInverse(n);
//			System.out.println(i);
			
			BigInteger m = (cA.pow(result[1].intValue()).multiply(i.pow(result[2].intValue()*-1))).mod(n);
			
			System.out.println(m);
			
			byte[] pt = m.toByteArray();
			
			for(int j = 0; j<pt.length;j++)
			{
				System.out.print((char)pt[j]);
			}
			
		}
		static BigInteger[] extendedEuclidean(BigInteger p, BigInteger q) {
		    BigInteger[] val = new BigInteger[3];

		    if (q.equals(BigInteger.ZERO)) {
		        val[0] = p;
		        val[1] = BigInteger.ONE;
		        val[2] = BigInteger.ZERO;
		    } else {
		        BigInteger[] val2 = extendedEuclidean(q, p.mod(q));
		        val[0] = val2[0];
		        val[1] = val2[2];
		        val[2] = val2[1].subtract(p.divide(q).multiply(val2[2]));
		    }
		    System.out.println(val[0]);
		    System.out.println(val[1]);
		    System.out.println(val[2]);
		    return val;
		}
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
	}
