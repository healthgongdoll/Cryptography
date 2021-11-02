package asymmetric;

import java.math.BigInteger;

public class pa5 {
	public static void main(String[] args) {
		/**
		 * Big Integer Primality Test
		 * 
		 * Miller Rabin Primality Test Steps Find n-1 = 2^k * m
		 * https://www.youtube.com/watch?v=qdylJqXCDGs&t=392s&ab_channel=Theoretically
		 * we start to log base 2
		 * 
		 * 
		 */
		BigInteger n = new BigInteger("103393117847605965195486200455");
		BigInteger ex = new BigInteger("103393117847605965195486200455");
		System.out.println(ex.isProbablePrime(20));
		MillerRabin(ex);
	}

	public static void MillerRabin(BigInteger n) {
		BigInteger nSub1 = n.subtract(BigInteger.ONE);
		
		// compute nSub1 / 2^ m is mod != 0
		BigInteger two = BigInteger.TWO;
		BigInteger MONE = new BigInteger("-1");
		BigInteger m = nSub1;
		int count = 1;
		// System.out.println(nSub1.divide(two.pow(count)));
		while (nSub1.mod(two.pow(count)).equals(BigInteger.ZERO)) {
			m = nSub1.divide(two.pow(count));
			count++;
		}
		System.out.println(count-1);
		count = count-1;
		System.out.println(m);
	
		BigInteger b0 = two.modPow(m, n); // T a^m mod n
		System.out.println(b0);
		int state = 0;
		if (b0.equals(BigInteger.ONE) || b0.equals(n.subtract(BigInteger.ONE))) {
			System.out.println("It might be Prime");
			state =1;
		} else {
			
			for (int i = 0; i < count - 1; i++) {
				b0 = b0.modPow(BigInteger.TWO, n);

				if (b0.remainder(n).equals(n.subtract(BigInteger.ONE))) {
					System.out.println("Prime Number");
					state =1;
					break;
				}
			}
			if(state == 0)
			{
				System.out.println("Not a Prime");
			}
			
		}
	}
}