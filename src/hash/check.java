package hash;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

import util.CryptoTools;

/**
 * This class computes the HMAC of given message and secret key. 
 * One has to make sure to pad the key on the right with 0's to make it 64 byte long for SHA-1. 
 * 
 * One also has to make sure that the given keys for hashing, iPad and oPad are properly padded repeatedly in the 64 byte array.
 * 
 *  We then define two 64-byte arrays called oPadKey = secretKey^oPad and iPadkey = secretKey^iPad
 *  
 *  Upon the first run, we concatenate the message with iPadKey and hash it with SHA-1. 
 *  We then concatenate this with oPadKey and hash it once again to get the final hash. 
 * @author owner
 *
 */
public class check {
	public static void main(String[] args) throws Exception{
		
		//Part1: Getting the messages
		byte[] message = "Cleared to land, Delta 86".getBytes();
		
		
		byte[] rawKey = "This is an ultra secret key".getBytes();
		
		//Here we are padding the given key to match the given block size. 
		//Padding always happens to the right i.e. the left part contains the real deal. 
		ByteArrayOutputStream padding = new ByteArrayOutputStream();
		for(int i=0;i<rawKey.length;i++) {
			padding.write(rawKey[i]);
		}
		for(int i=0;i<64-rawKey.length;i++) {
			padding.write(0);
		}
		
		byte[] secretKey = padding.toByteArray();
		System.out.println(secretKey.length);
		
		//Part2: Padding the iPad and the oPad
		byte[] rawIpad = CryptoTools.hexToBytes("36");
		System.out.println(rawIpad.length);
		byte[] iPad = new byte[64];
		for(int i=0;i<64;i++) {
			iPad[i] = rawIpad[0];
		}
		
		byte[] rawOpad = CryptoTools.hexToBytes("5c");
		byte[] oPad = new byte[64];
		for(int i=0;i<64;i++) {
			oPad[i] = rawOpad[0];
		}
		
		byte[] iPadKey = new byte[64];
		byte[] oPadKey = new byte[64];
		
		for(int i=0;i<64;i++) {
			iPadKey[i] = (byte) (secretKey[i]^iPad[i]);
			oPadKey[i] = (byte) (secretKey[i]^oPad[i]);
		}
		
		//Part3: Concatenating the iKeyPad and the message
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		for(int i=0;i<iPadKey.length;i++) {
			bos.write(iPadKey[i]);
		}
		for(int k=0;k<message.length;k++) {
			bos.write(message[k]);
		}
		byte[] levelOnePT = bos.toByteArray();
	
		
		
		
		//Part4: Hashing the levelOnePT
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] innerHash = md.digest(levelOnePT);
		//byte[] finalHash = md.digest(finalConcat);
		
		//Part5: Concatenating the innerHash with the oPadKey
		
		ByteArrayOutputStream outerBos = new ByteArrayOutputStream();
		for(int i=0;i<oPadKey.length;i++) {
			outerBos.write(oPadKey[i]);
		}
		for(int k=0;k<innerHash.length;k++) {
			outerBos.write(innerHash[k]);
		}
		byte[] levelTwoPT = outerBos.toByteArray();
		
		//Part6: Hashing the levelTwoPT
		MessageDigest finalHash = MessageDigest.getInstance("SHA-1");
		byte[] outerHash = finalHash.digest(levelTwoPT);
		
		System.out.println("Answer: "+CryptoTools.bytesToHex(outerHash));
		
		
	}

}
