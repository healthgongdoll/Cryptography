package foundation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;

import util.CryptoTools;

public class A_Exhaustive_new {
	public static void main(String[]args) throws Exception
	{
		byte [] cipherText  = CryptoTools.fileToBytes("C:\\Users\\Jay Chung\\eclipse-workspace\\EECS3481\\data\\MSG3.ct.txt");
		File file = new File("C:\\Users\\Jay Chung\\eclipse-workspace\\EECS3481\\data\\output.txt");   
		FileWriter fw = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(fw);
		
		for(int a = 1; a<26;a++)
		{
			if(gcd(a,26) != 1)
			{
				continue;
			}
			for(int b = 0; b<=26;b++)
			{
				BigInteger alpha = BigInteger.valueOf(a);
				BigInteger mod = BigInteger.valueOf(26);
				
				for(int i = 0; i<cipherText.length;i++)
				{
					cipherText[i] = (byte) (((cipherText[i] - b + 'A') * alpha.modInverse(mod).intValue())%26+'A');
					writer.write((char) cipherText[i]);
				}
				   writer.newLine();
				int[] letterfreq = CryptoTools.getFrequencies(cipherText);
				double sumA = 0,sumB = 0;
				double dotproduct = 0;
				double max = 0;
				for(int i = 0; i<26; i++)
				{
					sumA += (letterfreq[i] * letterfreq[i]);
					sumB += (CryptoTools.ENGLISH[i] * CryptoTools.ENGLISH[i]);
					dotproduct = dotproduct + ((double) letterfreq[i]) * (CryptoTools.ENGLISH[i]);
					
					//System.out.print((double)letterfreq[i]+" ");
				}
				dotproduct = dotproduct / Math.sqrt(sumA) / Math.sqrt(sumB);
				if(dotproduct > max)
				{
					max = dotproduct;
				}
			    writer.write(String.valueOf(dotproduct));
	            writer.newLine();
				System.out.println();
			}
		}
		
		writer.close();
		
	}
	
	public static int gcd(int a, int b){ if(b == 0) return a; return gcd(b, a % b); }

}
