package foundation;

import util.CryptoTools;

public class V_Exhaustive {
	public static void main(String[] args) throws Exception {
		int key_length = 2;

		byte[] ciphertext = CryptoTools
				.fileToBytes("C:\\Users\\Jay Chung\\eclipse-workspace\\EECS3481\\data\\MSG4.ct.txt");

		double eIC = 0.0667;
		double ic = 0;
		double avgIc = 0;
		double dotproduct = 0;
		double[] saveavg = new double[101];

		for (int key = 2; key <= 100; key++) {

			for (int i = 0; i < key; i++) {
				byte[] decrypt = new byte[ciphertext.length];
				// 0 번째 .. K번째 까지
				for (int j = 0; j < ciphertext.length; j++) {
					if (j % key == i) {
						decrypt[j] = ciphertext[j];

					}
				}
				// 배열에 다 들어간 상태
				decrypt = CryptoTools.clean(decrypt);
				ic += CryptoTools.getIC(decrypt);
				// 세그먼트마다 ic를 구하고 더한다
			}
			// 해당키값의 avgIC를 구함
			avgIc = ic / key;
			saveavg[key] = avgIc;
	//		System.out.println(avgIc);
			ic = 0;
		}
		double minIndex = Integer.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < saveavg.length; i++) {
			double abs = Math.abs(saveavg[i] - eIC);
			if (abs < minIndex) {
				minIndex = abs;
				index = i;
			}
		}
		
	//	System.out.println(saveavg[9]);
		System.out.println("Key Length:" +index); // key length 9 
	//------------------------------------------------------------------------Key Length 구함 ----------------------------
		
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
			//각 세그먼트의 키값을 구함
			//System.out.println(max);
			System.out.println((char) (26-subkey +'A'));
			
			//사이퍼 텍스트 해당 세그먼트 자리에 shift를 해줘야됨
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
