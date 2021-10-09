package foundation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;

import util.CryptoTools;

public class A_Exhaustive_new {
	public static void main(String[]args) throws Exception
	{
		byte [] cipherText  = CryptoTools.fileToBytes("data/test.txt");
		
		cipherText = CryptoTools.clean(cipherText);
		double max = 0;
		int alp =0;
		int bet =0;
		byte [] decrypt = cipherText.clone();
		for(int a = 1; a<26;a++)
		{
			
			if(gcd(a,26) != 1)
			{
				continue;
			}
			for(int b = 0; b<=26;b++)
			{
				double dotproduct = 0;
				BigInteger alpha = BigInteger.valueOf(a);
				BigInteger mod = BigInteger.valueOf(26);
				
				for(int i = 0; i<cipherText.length;i++)
				{
					decrypt[i] = (byte) (((cipherText[i] - b + 'A') * alpha.modInverse(mod).intValue())%26+'A');
				}
				int[] letterfreq = CryptoTools.getFrequencies(decrypt);
				double sumA = 0,sumB = 0;
				
			
				
				
				for(int i = 0; i<25; i++)
				{
					sumA += (letterfreq[i] * letterfreq[i]);
					sumB += (CryptoTools.ENGLISH[i] * CryptoTools.ENGLISH[i]);
					dotproduct = dotproduct + ((double) letterfreq[i]) * (CryptoTools.ENGLISH[i]);
					
				}
				dotproduct = dotproduct / Math.sqrt(sumA) / Math.sqrt(sumB);
				if(dotproduct > max)
				{
					max = dotproduct;
					alp = a;
					bet = b;
				
				}
		
			}
		}

		 System.out.println(max);
		 System.out.println(alp);
		 System.out.println(bet);
		
		
		
	}
	
	public static int gcd(int a, int b){ if(b == 0) return a; return gcd(b, a % b); }

}
