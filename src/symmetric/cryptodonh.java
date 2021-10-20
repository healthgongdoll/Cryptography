package symmetric;

import util.CryptoTools;

public class cryptodonh {
	public static void main(String[]args)
	{
		byte[] key = CryptoTools.hexToBytes("6B79466F724D4F50");
		byte[] IV = CryptoTools.hexToBytes("6976466F724D4F50");
		byte[] ciphertext = CryptoTools.hexToBytes("437DBAB5607137A5CFC1031114634087"); // 32 
		
		
		
		
		for(int i = 0; i<ciphertext.length; i++)
		{
			System.out.print(ciphertext[i] + " ");
		}
		
		
	}
	
	

}
