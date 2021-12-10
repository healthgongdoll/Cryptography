package hash;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import util.CryptoTools;

public class pa3 {
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

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
