package practice;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class test {
	
	public static void main(String[]args) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		BigInteger phi = new BigInteger("8584037913642434144111279062847405921823163865842701785008602377400681495147541519557274092429073976252689387304835782258785521935078205581766754116919200");
		BigInteger q = new BigInteger("87020952829623092932322362936864583897972618059974315662422560067745889600571");
		BigInteger e = new BigInteger("65537");
		BigInteger c = new BigInteger("2860343511650624855536801423229241360270155261818891412133738950638333577677444735030337910529513416732869176248688449871162754270700440751893288037477549");
		
		BigInteger d = e.modInverse(phi);
		
		BigInteger qSubOne = q.subtract(BigInteger.ONE);
		BigInteger pSubOne = phi.divide(qSubOne);
		BigInteger p = pSubOne.add(BigInteger.ONE);
		BigInteger n = p.multiply(q);
		
		print(RSAde(e,n,d,c));
	
	}
	public static void print(byte[]arr)
	{
		for(int i = 0; i<arr.length;i++)
		{
			System.out.print((char)arr[i]);
		}
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
}
