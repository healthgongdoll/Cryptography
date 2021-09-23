package foundation;

import util.CryptoTools;

public class ExampleIC {
	public static void main(String[]args) throws Exception
	{
		//몬티콜로 시뮬레이션
		//create a simulation that has random component to it and perform the operation that has randomness init 
		//average result would be the answer 
		
		byte[] raw = CryptoTools.fileToBytes("data/Election.txt");
		byte[] pt = CryptoTools.clean(raw);
	//	double ic = CryptoTools.getIC(pt);
		int trials = 1500;
		int count = 0; 
		for(int i  = 0; i<=trials; i++)
		{
			int pos2; 
			int pos1 = (int) (pt.length * Math.random());
			do
			{
				pos2 = (int)(pt.length * Math.random()); //random position selection
				
			}while(pos2 == pos1);
			if(pt[pos1] == pt[pos2]) count++;
			System.out.println(count/(double)i);
		
		}
		CryptoTools.getMIC(pt);

		
	}
}
