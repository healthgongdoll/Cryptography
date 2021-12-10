package selectedtopic;

import java.util.HashSet;

import util.Polynomial;

public class polyshar {
	
	private static int[] point;

	public static void main(String[]args)
	{
		creation(3,6,1234);
		reconstruction(6);
		
	}
	
	public static void creation(int t, int n, int secret) {
		// form a function
		// generate the random number
		int a = 94; // (int)(Math.random()*100);
		int b = 166; // (int)(Math.random()*100);

		point = new int[n];

		// find the random point
		for (int i = 1; i <= n; i++) {
			point[i - 1] = a * ((int) Math.pow(i, 2)) + b * i + secret;
		}

		for (int i = 0; i < point.length; i++) {
			System.out.println(point[i]);
		}
	}
	
	public static void reconstruction(int a, int b, int c)
	{
		Polynomial l0x = new Polynomial(1,1);
		Polynomial l0x1 = new Polynomial(a,0);
		int l0denom = (a-b)*(a-c);
		Polynomial l0xSub = l0x.minus(l0x1);
		Polynomial l01 = l0xSub.
	}

}
