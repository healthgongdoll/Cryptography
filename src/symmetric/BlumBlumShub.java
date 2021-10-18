package symmetric;

import util.CryptoTools;

public class BlumBlumShub {
	public static void main(String[] args) throws Exception {
		int p = 11;
		int q = 19;
		int x0 = 100;

		byte[] plainText = CryptoTools.fileToBytes("data/test.txt");

		printText(plainText);
		blumblumshub(p,q,x0,plainText);
		
	}
	
	public static void blumblumshub(int p, int q, int x0,byte[]arr)
	{
		byte[]cipherText = new byte[arr.length];
		int[]randomNum = new int[arr.length];
		int m = p * q;
		randomNum[0] = x0;
		int prevX = x0;
		// Generate Random Number Sequence 
		for(int i = 1; i<randomNum.length;i++)
		{
			int xi = (prevX * prevX) % m;
			randomNum[i] = xi;
			prevX = xi;
			System.out.print(randomNum[i]+" ");
		}
		System.out.println();
		// BitSequence Transformation
		for(int i = 0; i<randomNum.length;i++)
		{
			randomNum[i] = randomNum[i] % 2;
			System.out.print(randomNum[i]+ " ");
		}
		System.out.println();
		for(int i = 0; i<cipherText.length;i++)
		{
			cipherText[i] = (byte) (arr[i] ^ randomNum[i]);
			System.out.print((char) cipherText[i]+" ");
		}
		
	}

	public static void printText(byte[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print((char) arr[i]);
		}
		System.out.println();
	}
	
}
