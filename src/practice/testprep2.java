package practice;

import util.CryptoTools;

public class testprep2 {
	
	public static void main(String[]args)
	{
		byte[] ct = CryptoTools.hexToBytes("7AA38A029E773CBBC188A9FCEADAE99DA560B784C99AFEF2");
		
		for(int i = 0; i<ct.length;i++)
		{
			System.out.print(ct[i]+" ");
		}
	}

}
