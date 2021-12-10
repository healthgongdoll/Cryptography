package exam;

import java.math.BigInteger;

public class Shamir {
	static int[] point;
	static int[] points = {172,223,274,326};
	/*
	 * Shamir (t,n) scheme
	 */
	public static void main(String[] args) {
	
		reconstruction(2,1);

		
	}

	public static void reconstruction(double a, double b)
	{

		double l0 = (-b) / ((a-b));
		System.out.println("l0:"+l0);
		double l1 = (-a) /((b-a));
		System.out.println("l1:"+l1);
		
		double secret = points[(int)a-1]*l0 + points[(int)b-1]*l1;
		
		System.out.println("Secret is: "+(int)secret);
	}
	
	
}
