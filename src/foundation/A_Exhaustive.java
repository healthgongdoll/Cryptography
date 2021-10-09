package foundation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;

import util.CryptoTools;

public class A_Exhaustive {
	
	public static void main(String[]args) throws Exception
	{
		byte [] cipherText  = CryptoTools.fileToBytes("data/test.txt");
		File file = new File("C:\\Users\\Jay Chung\\eclipse-workspace\\EECS3481\\data\\output.txt");   
		FileWriter fw = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(fw);

		cipherText = CryptoTools.clean(cipherText);
		byte[] decrypt;
		   int []letterfreq;
		   double dotproduct = 0;
		      for (int a = 1; a <= 25; a++) {
		         if (gcd(a, 26) != 1) {
		            continue;
		         }

		         BigInteger alpha = BigInteger.valueOf(a);
		         BigInteger num = BigInteger.valueOf(26);
		         BigInteger inverse = alpha.modInverse(num);
		   
		       //  System.out.println(alpha + " " + num +  " " + inverse);
		         
		         for (int b = 0; b <= 26; b++) {
		            decrypt = new byte[cipherText.length];
		            dotproduct = 0;
		            for (int i = 0; i < cipherText.length; i++) {
		            	byte next = (byte)((cipherText[i] - 'A') - b);
		            	  if(next < 0) {
		                      next += 26;
		                   }

		            	 decrypt[i] = (byte) (((next * inverse.intValue()) % 26)+'A');

		            
		            	 writer.write((char) ((decrypt[i])));
		               System.out.print((char) ((decrypt[i])));
		            }
		            writer.newLine();
		            letterfreq = CryptoTools.getFrequencies(decrypt);
		            double sumA = 0, sumB = 0;
		            
		            for(int i = 0; i<26;i++)
		            {
		            	sumA += (letterfreq[i] * letterfreq[i]);
		            	sumB += (CryptoTools.ENGLISH[i] * CryptoTools.ENGLISH[i]);
		            	dotproduct = dotproduct + ((double) letterfreq[i]) *(CryptoTools.ENGLISH[i]);
		            }
		            dotproduct = dotproduct/ Math.sqrt(sumA) / Math.sqrt(sumB);
		            writer.write(String.valueOf(dotproduct));
		            writer.newLine();
		         }

		      }
		      writer.close();
		     
	}
	
	public static int gcd(int a, int b){ if(b == 0) return a; return gcd(b, a % b); }



}

/*
 * byte[] temp = cipherText.clone();
					//System.out.print("cipher:" + (char)(cipherText[i]));//- 'A') + " ");
					//BigInteger big = new BigInteger();
					BigInteger cb = BigInteger.valueOf(((cipherText[i] - 'A') - b + 26) % 26);
					BigInteger big = BigInteger.valueOf(a);
					BigInteger two = BigInteger.valueOf(26);
					BigInteger cal = cb.multiply(big.modInverse(two));
					//System.out.print("cb:" + cb);
					//BigInteger con = BigInteger.valueOf(26);
					temp[i] = (byte)(cal.intValue() + 'A');
					//cipherText[i] = (byte) ( (((cipherText[i]-'A')-b)* big.modInverse(con).intValue()) + 'A' );
					System.out.print((char)temp[i]);
 */

