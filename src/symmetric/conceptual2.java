package symmetric;

import util.CryptoTools;

public class conceptual2 {
	public static void main(String[]args)
	{
		byte[]AliceKey = CryptoTools.hexToBytes("0A4F0C08003503492F247442105B5757");
		byte[]BobAliceKey = CryptoTools.hexToBytes("5E2769286B507A69494B066252343579");
		byte[]BobKey = CryptoTools.hexToBytes("170708454B1116002A2E2111725F5000");
		
		byte[]Alicedk = new byte[BobKey.length];
		byte[]plaintext = new byte[BobKey.length];
		for(int i = 0; i<BobKey.length;i++)
		{
			Alicedk[i] = (byte) (BobAliceKey[i] ^ BobKey[i]);
		}
		
		for(int i = 0; i<BobKey.length;i++)
		{
			plaintext[i] = (byte) (AliceKey[i] ^ Alicedk[i]);
		}
		
		for(int i = 0; i<plaintext.length;i++)
		{
			System.out.print((char)plaintext[i]);
		}
	}

}
