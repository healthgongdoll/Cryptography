package foundation;

import util.CryptoTools;

public class test_prep1 {
	public static void main(String[]args) throws Exception
	{
		byte[] ciphertext = CryptoTools.fileToBytes("data/MSG2.ct.txt");
		
		double max = 0;
		int key = 1;
		int r = 0;
		for(int i = 0; i<26;i++)
		{
			for(int j = 0; j<ciphertext.length;j++)
			{
				ciphertext[j] = (byte) (((ciphertext[j]-'A')+key) % 26 + 'A');
				System.out.print((char) ciphertext[j]);
			}
			System.out.println();
			int[]freq = CryptoTools.getFrequencies(ciphertext);
			
			double sumA = 0;
			double sumB = 0;
			double dotproduct = 0;
			for(int j = 0; j<freq.length;j++)
			{
				sumA += freq[j] * freq[j];
				sumB += CryptoTools.ENGLISH[j] * CryptoTools.ENGLISH[j];
				dotproduct += freq[j] * CryptoTools.ENGLISH[j];
			}
			
			dotproduct = dotproduct / Math.sqrt(sumA) / Math.sqrt(sumB);
			System.out.println(dotproduct);
			if(max < dotproduct)
			{
				max = dotproduct;
				r = i;
			}
			
		}
		System.out.println("Key is: " + (char) (r +'A'));
		
	}

}
