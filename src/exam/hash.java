package exam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class hash {
	
	public static void main(String[]args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		MACCompute();
		ds();
	}

	/*
	 * Digital Signature
	 */
	
	public static void ds() throws NoSuchAlgorithmException
	{
		//Bob Generate hash -> sign with the signature
		BigInteger n = new BigInteger("94587468335128982981605019776781234618384857805657005686084562260910788622013722070926491690843853690071248130134427832324966728582532832363221542231787068203763027067400082835394459857525017707284768411819006776211493735326500782954621660256501187035611332577696332459049538105669711385995976912007767106063");
		BigInteger e = new BigInteger("74327");
		BigInteger d = new BigInteger("7289370196881601766768920490284861650464951706793000236386405648425161747775298344104658393385359209126267833888223695609366844098655240542152017354442883676634193191857568369042999854440242050353181703706753485749165295123694487676952198090537385200990850805837963871485320168470788328336240930212290450023");
		byte[] m = "Meet me at 5 pm tomorrow".getBytes();
		
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		byte[] hash = md.digest(m);
		BigInteger z = new BigInteger(hash);
		BigInteger signature = z.modPow(d, n);
		
		//Alice Receive message and hash 
		
		System.out.println(z);
		System.out.println(signature.modPow(e, n));
		
	}
	
	/*
	 * Compute the MAC value of the following Message:
	 * 
	 * M = Why do we tell actors to break a leg? because every play has a cast
	 * 
	 * 
	 */
	public static void MACCompute() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		byte[] m = "Why do we tell actors to break a leg? because every play has a cast".getBytes();
		byte[] key = CryptoTools.hexToBytes("34567abcdef0321134567abcdef03211");
		byte[] iv = CryptoTools.hexToBytes("44668abddef1321134568abcdef13221");
		byte[] mac = new byte[16];
		boolean initial = true;
		 m = Arrays.copyOf(m, 80);
		SecretKeySpec secret = new SecretKeySpec(key, "AES");
		Cipher method = Cipher.getInstance("AES/ECB/NoPadding");
		method.init(Cipher.ENCRYPT_MODE, secret);
		//byte[] encrypt = method.doFinal(text);
		
		System.out.println(m.length);
		if(initial == true)
		{
			for(int i = 0; i<16; i++)
			{
				mac[i] = (byte) (m[i] ^ iv[i]);
			}
			mac = method.doFinal(mac);
			initial = false;
		}else
		{
				for(int j = 16; j<32; j++)
				{
					mac[j-16] = (byte) (mac[j-16] ^ m[j]); 
				}
				mac = method.doFinal(mac);
				
				for(int j = 32; j<48; j++)
				{
					mac[j-32] = (byte) (mac[j-32] ^ m[j]); 
				}
				mac = method.doFinal(mac);
				
				for(int j = 48; j<64;j++)
				{
					mac[j-48] = (byte) (mac[j-48] ^ m[j]); 
				}
				mac = method.doFinal(mac);
				
				//padding
				  
			     
				for(int j = 64; j<80; j++)
				{
					mac[j-64] = (byte) (mac[j-64] ^ m[j]); 
				}
				mac = method.doFinal(mac);
		}
		
		System.out.println(new String(CryptoTools.bytesToHex(mac)));
	}

	/*
	 * HMAC
	 *  You have to carefully look at the Block size of SHA algorithm 
	 *  !!!!!!!!
	 *  
	 *  
	 */
	
	public static void HMAC() throws IOException, NoSuchAlgorithmException
	{
		/**
		 * HMAC (K,m) = H((K' XOR opad) || H((K' XOR ipad) || m))
		 * 
		 * K' = H(K) K is larger than block size , otherwise K
		 * 
		 * H is Cryptographic hash function m is the message to be authenticated K is
		 * the secret key K' is the block sized key derived from the secret key K ||
		 * concatenation
		 * 
		 * If the key is longer than 512 bits (64 bytes) we shorten by hashing If the
		 * key is shorter than 512 bits we pad with 0s
		 */
		byte[] m = "Why do tell actors to break a leg? because every play has a cast".getBytes();
		byte[] k = "This is a super secure random key!".getBytes();
		byte[] newKey = new byte[128];

		if (k.length > 64) {
				System.out.println("hi");
		} else {
			for (int i = 0; i < k.length; i++) {
				newKey[i] = k[i];
			}
			for (int i = k.length; i < newKey.length; i++) {
				newKey[i] = 0;
			}
		}

		byte[] opad = CryptoTools.hexToBytes("5c");
		byte[] ipad = CryptoTools.hexToBytes("36");

		byte[] ipadn = new byte[128];
		byte[] opadn = new byte[128];

		for (int i = 0; i < ipadn.length; i++) {
			ipadn[i] = ipad[0];
		}
		for (int i = 0; i < ipadn.length; i++) {
			opadn[i] = opad[0];
		}

		for (int i = 0; i < ipadn.length; i++) {
			ipadn[i] = (byte) (newKey[i] ^ ipadn[i]);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(ipadn);
		outputStream.write(m);

		byte[] inner = outputStream.toByteArray();
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		byte[] hashInner = md.digest(inner);

		for (int i = 0; i < ipadn.length; i++) {
			opadn[i] = (byte) (newKey[i] ^ opadn[i]);
		}
		ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
		outputStream2.write(opadn);
		outputStream2.write(hashInner);
		byte[] outer = outputStream2.toByteArray();

		byte[] finalOutput = md.digest(outer);

		System.out.println(CryptoTools.bytesToHex(finalOutput));
	}
	
	
	
}
