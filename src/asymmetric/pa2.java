package asymmetric;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class pa2 {
	public static void main(String[]args) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		/**
		 * Key Generation Algorithm 
		 * Choose two large primes p,q
		 * Compute n = p * q 
		 * Compute phi(n) = (p-1)*(q-1)
		 * Select the public exponent e = {0,1,...,phi(n)-1} such that gcd(e,phi(n)) = 1
		 * Compute the private key d such that d * e = 1 mod phi(n) 
		 * Return pubk = (n,e), privk = d
		 * 
		 * Question --------
		 * e is given 
		 * n is given 
		 * p is given
		 * ct is given
		 * 
		 *  n = p * ?
		 *  ? = n/p 
		 *  phi(n) = (p-1) * (? -1)
		 * select a number from gcd (e, phi(n)) = 1 
		 * compute the private key d such that d * e = 1 mod phi(n)
		 */
		BigInteger e = new BigInteger("74327");
		BigInteger n = new BigInteger("94587468335128982981605019776781234618384857805657005686084562260910788622013722070926491690843853690071248130134427832324966728582532832363221542231787068203763027067400082835394459857525017707284768411819006776211493735326500782954621660256501187035611332577696332459049538105669711385995976912007767106063");
		BigInteger p  = new BigInteger("10358344307803887695931304169230543785620607743682421994532795393937342395753127888522373061586445417642355843316524942445924294144921649080401518286829171");
		BigInteger ct = new BigInteger("10870101966939556606443697147757930290262227730644958783498257036423105365610629529910525828464329792615002602782366786531253275463358840412867833406256467153345139501952173409955322129689670345445632775574301781800376545448990332608558103266831217073027652061091790342124418143422318965525239492387183438956");
		BigInteger one = new BigInteger("1");
		BigInteger q = n.divide(p);
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		assert e.gcd(phi).equals(one);
	
		BigInteger d = e.modInverse(phi);

		print(RSAde(e,n,d,ct));
		
		
	}
	
	public static byte[] RSAde(BigInteger e, BigInteger n, BigInteger d, BigInteger ct) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(n,e);
		RSAPrivateKeySpec privSpec = new RSAPrivateKeySpec(n,d);
		PublicKey pub = keyFactory.generatePublic(pubSpec);
		PrivateKey priv = keyFactory.generatePrivate(privSpec);
		Cipher eg = Cipher.getInstance("RSA/ECB/NoPadding");
		eg.init(Cipher.DECRYPT_MODE,priv);
		byte[] pt = eg.doFinal(ct.toByteArray());
		
		return pt;
	}
	
	public static void print(byte[]arr)
	{
		for(int i = 0; i<arr.length;i++)
		{
			System.out.print((char)arr[i]);
		}
	}

}
