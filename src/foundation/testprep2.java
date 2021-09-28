package foundation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;

import util.CryptoTools;

public class testprep2 {
	public static void main(String[]args) throws Exception
	{
		byte[] ct = CryptoTools.fileToBytes("data/MSG3.ct.txt");
		File file = new File("C:\\Users\\Jay Chung\\eclipse-workspace\\EECS3481\\data\\output.txt");   
		FileWriter fw = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(fw);
		for(int a = 1; a<25;a++)
		{
			if(gcd(a,26) != 1) continue;
			for(int b = 0; b<26; b++)
			{
				byte[]answer = ct.clone();
				BigInteger a1 = BigInteger.valueOf(a);
				BigInteger ts = BigInteger.valueOf(26);
				BigInteger aInverse = a1.modInverse(ts);
				
				for(int k = 0; k<ct.length;k++)
				{
					byte beta = (byte) (((ct[k]-'A')-b) % 26);
					System.out.println(beta);
					answer[k] = (byte) ((beta * aInverse.intValue()) % 26 + 'A');
					 writer.write((char) ((answer[k])));
				}
				writer.newLine();
				System.out.println();
				
				int[] freq = CryptoTools.getFrequencies(answer);
				 double sumA = 0, sumB = 0;
				 double dotproduct = 0;
		            for(int i = 0; i<26;i++)
		            {
		            	sumA += (freq[i] * freq[i]);
		            	sumB += (CryptoTools.ENGLISH[i] * CryptoTools.ENGLISH[i]);
		            	dotproduct = dotproduct + ((double) freq[i]) *(CryptoTools.ENGLISH[i]);
		            }
		            dotproduct = dotproduct/ Math.sqrt(sumA) / Math.sqrt(sumB);
		            writer.write(String.valueOf(dotproduct));
		            writer.newLine();
			}
			
			
		}
	     writer.close();
	}
	
	public static int gcd(int a, int b)
	{
		if(b == 0)
		{
			return a;
		}else
		{
			return gcd(b,a%b);
		}
	}
}
