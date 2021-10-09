package foundation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import util.CryptoTools;

public class V_Encrypt {
	public static void main(String[]args) throws Exception
	{
		byte[] pt = CryptoTools.fileToBytes("C:\\\\Users\\\\Jay Chung\\\\eclipse-workspace\\\\EECS3481\\\\data\\\\MSG1.pt.txt");
		pt = CryptoTools.clean(pt);
		File file = new File("C:\\Users\\Jay Chung\\eclipse-workspace\\EECS3481\\data\\output1.txt");   
		FileWriter fw = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(fw);
		int keylength = 4;
		int [] key = {1,2,3,4}; // 
		
		
		for(int i = 0; i<keylength;i++)
		{
			for(int j = 0; j<pt.length;j++)
			{
				if(j % keylength == i)
				pt[j] = (byte) (((pt[j]-'A')+key[i])%26 + 'A');
			}
		}
		
		for(int i = 0; i<pt.length;i++)
		{
			writer.write(pt[i]);
		}
		
		writer.close();
		
	}
}
