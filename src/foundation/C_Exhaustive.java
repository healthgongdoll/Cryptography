package foundation;

import util.CryptoTools;

public class C_Exhaustive {

	public static void main(String[] args) throws Exception {
		byte[] ciphertext = CryptoTools
				.fileToBytes("data/q3v12.txt");
		int[] letterfreq;
		double dotproduct = 0;
		double max = 0; 
		int answer = 0; 
		int key = 1;
		for (int k = 0; k < 26; k++) {
				dotproduct = 0;
			for (int i = 0; i < ciphertext.length; i++) {

				ciphertext[i] = (byte) (((ciphertext[i] - 'A') + key) % 26 + 'A');
			}

			letterfreq = CryptoTools.getFrequencies(ciphertext);
			double sumA = 0,sumB = 0;
			for(int i = 0; i<26; i++)
			{
				sumA += (letterfreq[i] * letterfreq[i]);
				sumB += (CryptoTools.ENGLISH[i] * CryptoTools.ENGLISH[i]);
				dotproduct = dotproduct + ((double) letterfreq[i]) * (CryptoTools.ENGLISH[i]);
				
			}
			dotproduct = dotproduct / Math.sqrt(sumA) / Math.sqrt(sumB);
			if(dotproduct > max)
			{
				max = dotproduct;
				answer = k;
			}
		
		}
		System.out.println(max);
	}

}
