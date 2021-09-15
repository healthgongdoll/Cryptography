package foundation;

import java.io.FileNotFoundException;

import util.CryptoTools;

public class C_Encrypt {

	public static void main(String[]args) throws Exception
	{
		byte [] plaintext = CryptoTools.fileToBytes("C:\\Users\\Jay Chung\\eclipse-workspace\\EECS3481\\data\\MSG1.pt.txt");
		
		plaintext = CryptoTools.clean(plaintext);
		
		int key = 19;
		
		for(int i = 0; i<plaintext.length;i++)
		{
			plaintext[i] = (byte) (((plaintext[i]-'A')+key) % 26 + 'A');
		}
		
		CryptoTools.bytesToFile(plaintext, "C:\\Users\\Jay Chung\\eclipse-workspace\\EECS3481\\data\\MSG1.clean");
		
		
	}
}
