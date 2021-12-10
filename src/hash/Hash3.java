
package hash;

import java.math.BigInteger;

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;

import java.util.Arrays;

import util.CryptoTools;

public class Hash3 {

	public static void main(String[] args) throws NoSuchAlgorithmException {

		// TODO Auto-generated method stub

		BigInteger k = new BigInteger("This is a super secure random key!".getBytes());

		BigInteger m = new BigInteger("Why do tell actors to break a leg? because every play has a cast".getBytes());

		// paddings

		byte[] opadA = new byte[128];// CryptoTools.hexToBytes("5c");

		byte[] ipadA = new byte[128]; // CryptoTools.hexToBytes("36");

		for (int i = 0; i < 128; i++)

		{

			opadA[i] = CryptoTools.hexToBytes("5c")[0];

			ipadA[i] = CryptoTools.hexToBytes("36")[0];

		}

		// blocksize= 64 bytes

		// hmac calcs

		// HMAC = H( (K' xor opad) + H(K' xor (ipad + m))

		// sha-1 hash

		MessageDigest md = MessageDigest.getInstance("SHA-512");

		byte[] hk = md.digest(k.toByteArray());

		byte[] kPadded = new byte[128];

		for (int i = 0; i < 128; i++)

		{

			if (i < k.toByteArray().length)

			{

				kPadded[i] = k.toByteArray()[i];

			}

			else

			{

				kPadded[i] = 0;

			}

		}

		// k' is H(k) if k>64, else its k

		BigInteger kPrime = new BigInteger(hk);

		if (k.toByteArray().length < 128)

		{

			kPrime = new BigInteger(kPadded);

		}

		BigInteger outer = kPrime.xor(new BigInteger(opadA));

		BigInteger inner = kPrime.xor(new BigInteger(ipadA));

		inner = new BigInteger(concat(inner.toByteArray(), m.toByteArray()));

		byte[] hp2 = md.digest(inner.toByteArray());

		// concatenate hp1, hp2 then hash them

		byte[] hmac = md.digest(concat(outer.toByteArray(), hp2));

		System.out.println(new String(CryptoTools.bytesToHex(hmac)));

	}

	public static byte[] concat(byte[] a, byte[] b)

	{

		byte[] c = new byte[a.length + b.length];

		for (int i = 0; i < a.length; i++)

		{

			c[i] = a[i];

		}

		for (int j = a.length; j < a.length + b.length; j++)

		{

			c[j] = b[j - a.length];

		}

		return c;

	}

}