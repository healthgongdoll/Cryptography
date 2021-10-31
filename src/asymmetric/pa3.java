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

public class pa3 {
	public static void main(String[]args) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		/**
		 * Alice Bob uses RSA to communicate 
		 * Alice -> 'message' -> Bob 
		 * m is encrypted with using public e, n and obtained 
		 * m can be decrypted?
		 * 
		 * Unknow Facts: let n be x , p be y
		 * Known Facts 
		 *  x = y * q 
		 *  phi(x) = (y-1) * (q-1)
		 *  select e = {0,...,phi(x)-1}
		 *  gcd(e,phi(x)) = 1 ;
		 *  compute d * e = 1 mod phi(x)
		 *  
		 */
		BigInteger phi = new BigInteger("8584037913642434144111279062847405921823163865842701785008602377400681495147541519557274092429073976252689387304835782258785521935078205581766754116919200");
		BigInteger q = new BigInteger("87020952829623092932322362936864583897972618059974315662422560067745889600571");
		BigInteger e = new BigInteger("65537");
		BigInteger ct = new BigInteger("1817487313698347891034157970684926175211834109573277793102901854482611726141560963120214926234448852417078321539316776648109260519063106558303669880226359");
		BigInteger d = e.modInverse(phi);
		
		BigInteger qSubOne = q.subtract(BigInteger.ONE);
		BigInteger pSubOne = phi.divide(qSubOne);
		BigInteger p = pSubOne.add(BigInteger.ONE);
		BigInteger n = p.multiply(q);
		
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
