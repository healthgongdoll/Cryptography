package foundation;

import util.CryptoTools;

public class V_Exhaustive {
	public static void main(String[] args) throws Exception {
		int key_length = 2;
		int count = 0;
		byte[] ciphertext = CryptoTools
				.fileToBytes("data/message.txt");

		double eIC = 0.0667; //ENglish Index of Coincidence
		double ic = 0;
		double avgIc = 0;
		double dotproduct = 0;
		double[] saveavg = new double[101];

		for (int key = 2; key <= 100; key++) { //loop through the key
			for (int i = 0; i < key; i++) {
				byte[] decrypt = new byte[ciphertext.length];
				for (int j = 0; j < ciphertext.length; j++) {
					if (j % key == i) {
						decrypt[j] = ciphertext[j];
						
					}
				}

				decrypt = CryptoTools.clean(decrypt);
				ic += CryptoTools.getIC(decrypt);
				
			}
			
			avgIc = ic / key;
			saveavg[key] = avgIc;
			System.out.println(avgIc);
			ic = 0;
		}
		
		double minIndex = Integer.MAX_VALUE;
		int index = 0; 
		for (int i = 0; i < saveavg.length; i++) {
			double abs = Math.abs(saveavg[i] - eIC);
			if (saveavg[i]/eIC > 0.90 && saveavg[i]/eIC <1.05) {
				minIndex = abs;
				count++;
				index = i;
				break;
			}
		}
		
	
		System.out.println("Key Length:" +index); // key length 9 
	//------------------------------------------------------------------------Found the Key Length ----------------------------
		
		//break the segment again
		for(int i =0; i<index; i++)
		{
			byte[]decrypt = new byte[ciphertext.length];
			for(int j = 0; j<ciphertext.length;j++)
			{
				if(j%index == i)
				{
					decrypt[j] = ciphertext[j];
				}
			}
			decrypt = CryptoTools.clean(decrypt);
			for(int j = 0; j < decrypt.length;j++)
			{
				System.out.print((char)decrypt[j]);
				
			}
			System.out.println();
			double max =0;
			int subkey = 0;
			//caesar cipher for each segment
			for(int x = 0; x<26; x++)
			{
				dotproduct = 0; 
				for(int y = 0; y<decrypt.length;y++)
				{
					decrypt[y] = (byte) (((decrypt[y]-'A')+1)%26 + 'A');
				}
				int[] freq = CryptoTools.getFrequencies(decrypt);
				double sumA = 0, sumB = 0; 
				
				for(int k = 0; k<26;k++)
				{
					sumA +=(freq[k] * freq[k]);
					sumB +=(CryptoTools.ENGLISH[k] * CryptoTools.ENGLISH[k]);
					dotproduct = dotproduct + ((double) freq[k]) * (CryptoTools.ENGLISH[k]);
				}
				dotproduct = dotproduct / Math.sqrt(sumA) / Math.sqrt(sumB);
				if(max < dotproduct)
				{
					max = dotproduct;
					subkey = x;
				}
			}
			subkey = subkey+1;
			//get the key for the segment
			System.out.println(max);
			System.out.println((char) (26-subkey +'A'));
			
			int di = 0;
			
			for(int x = 0; x<ciphertext.length;x++)
			{
				if(x%index == i) {
					ciphertext[x] = (byte) (((decrypt[di] - 'A') + subkey) % 26 + 'A'); 
					di++;
				}
			}
		}
		
		System.out.println("DECRYPTION: ");
		for(int i = 0; i<ciphertext.length;i++)
		{
			System.out.print((char) ciphertext[i]);
		}
		System.out.println();

	}

}
