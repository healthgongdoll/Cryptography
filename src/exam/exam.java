package exam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import util.CryptoTools;

public class exam {
	public static void main(String[]args) throws IOException, NoSuchAlgorithmException
	{
		final String n =
			      "945874683351289829816050197767812346183848578056570056860845622609107886220137" +
			      "220709264916908438536900712481301344278323249667285825328323632215422317870682" +
			      "037630270674000828353944598575250177072847684118190067762114937353265007829546" +
			      "21660256501187035611332577696332459049538105669711385995976912007767106063";
		final String e = "74327";
		final String d = "7289370196881601766768920490284861650464951706793000236386405648425161747775298" +
			      "3441046583933853592091262678338882236956093668440986552405421520173544428836766" +
			      "3419319185756836904299985444024205035318170370675348574916529512369448767695219" +
			      "8090537385200990850805837963871485320168470788328336240930212290450023";
		
		BigInteger nBI = new BigInteger(n);
		BigInteger eBI = new BigInteger(e);
		BigInteger dBI = new BigInteger(d);
		
		final String message = "I am in love with Cryptography";
		
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] sign = md.digest(message.getBytes());
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec RSAPubKeySpec = new RSAPublicKeySpec(nBI, eBI);
			RSAPrivateKeySpec RSAPrivKeySpec = new RSAPrivateKeySpec(nBI, dBI);
			PublicKey pubKey = keyFactory.generatePublic(RSAPubKeySpec);
			PrivateKey privKey = keyFactory.generatePrivate(RSAPrivKeySpec);
			
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			
			cipher.init(Cipher.ENCRYPT_MODE, privKey);
			
			byte[] ctSign = cipher.doFinal(sign);
			byte[] ct = cipher.doFinal(message.getBytes());
			System.out.println("signature: " + new String(sign));
			System.out.println("Encrypted signature: " + new String(CryptoTools.bytesToHex(ctSign)));
			System.out.println("Encrypted pt: " + new String(ct));
			
			cipher.init(Cipher.DECRYPT_MODE, pubKey);
			
			byte[] ptSign = cipher.doFinal(ctSign);
			byte[] pt = cipher.doFinal(ct);
			
			byte[] signedPt = md.digest(pt);
			System.out.println("decrypted signature: " + new String(ptSign));
			System.out.println("decrypted pt: " + new String(pt));
			System.out.println("sign from d pt: " + new String(CryptoTools.bytesToHex(signedPt)));
			
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}

}
