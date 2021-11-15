package hash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import util.CryptoTools;

public class pa1 {
	public static void main(String[]args) throws NoSuchAlgorithmException
	{
		
		/*
		 * Use them to sign the message
		 * 	'Meet me at 5 pm tomorrow'
		 * Using 512-bit SHA2 Afterward, show that if you send the message (in clear plaintext) and signature to Alice then she can indeed verify that it came from you and that its content is intact 
		 * 
		 * encryption FROM Asymmetric 
		 * 
		 * y = x^e mod n
		 * x = y^d mod n
		 * 
		 * we know that n,e is the public known factor e -> public key 
		 * 
		 * d is private key 
		 * 
		 */
		BigInteger n = new BigInteger("94587468335128982981605019776781234618384857805657005686084562260910788622013722070926491690843853690071248130134427832324966728582532832363221542231787068203763027067400082835394459857525017707284768411819006776211493735326500782954621660256501187035611332577696332459049538105669711385995976912007767106063");
		BigInteger e = new BigInteger("74327");
		BigInteger d = new BigInteger("7289370196881601766768920490284861650464951706793000236386405648425161747775298344104658393385359209126267833888223695609366844098655240542152017354442883676634193191857568369042999854440242050353181703706753485749165295123694487676952198090537385200990850805837963871485320168470788328336240930212290450023");
		
		byte[] message = "Meet me at 5 pm tomorrow".getBytes();
		
		//Hash the message 
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		byte[]hash = md.digest(message);
		
		BigInteger msgHash = new BigInteger(hash);
		
		BigInteger y = msgHash.modPow(d, n);  // Signature with private key 
		
		
		// send (x,y) -> message and y signature
		
		//Alice received x 
		
		byte[] aliceHash = md.digest(message);
		
		System.out.println(new String(CryptoTools.bytesToHex(aliceHash)));
		System.out.println(new String(CryptoTools.bytesToHex(y.toByteArray())));
		
		BigInteger decryptHash = y.modPow(e, n);
		
		System.out.println(new String(CryptoTools.bytesToHex(decryptHash.toByteArray())));
		
		

		
		
		
		
		
		
	
	}

}
