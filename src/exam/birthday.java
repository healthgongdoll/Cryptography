package exam;

import java.math.BigInteger;
import java.util.Random;

public class birthday {
	
	static double result = 1;
	static double counter = 1;
	/*
	 * 
	 * 	X people in a room 
	 *  S -> least two of them have the same birth day 
	 */
	
	public static void main(String[]args)
	{
		
		double n = 100;
		if(n < 1.177 *Math.sqrt(365))
		{
			System.out.println("N value is less than: " + 1.177 *Math.sqrt(365) );
		}
		double p = 1 - Math.pow(Math.E, -1 * ((n*n)/(2*365))); // Two of them will share the birthday 
		double s =  Math.pow(Math.E, -1 * ((n*n)/(2*365))); // Two of them Will not share birthday
		System.out.println(p);
		System.out.println(p);
		Random random = new Random();

		
		double[] stats = new double[366];
		
		for(int i = 1; i<=366; i++)
		{
			double es = 1 - Math.pow(Math.E, -1 * (((double)i*i)/(2*365)));
			double ex =productCal(i);
			System.out.println(i);
			System.out.println("approx: " + es);
			System.out.println("exactc: " + ex);
			
			stats[i-1] = Math.abs(es-ex);
			System.out.println();
		}
		
		double min = Integer.MAX_VALUE;
		int index = 0;
		for(int i = 0; i<stats.length;i++)
		{
			System.out.println("index " + (i+1) +" is " +stats[i]);
			if(min > stats[i])
			{
				min = stats[i];
				index = i;
			}
		}
	//	System.out.println(findPeople(0.391));
	//	System.out.println(index);
	
		
	
	}
	
	
	
	public static double productCal(double n)
	{
		double result = 1;
		for(int i = 1; i<n; i++)
		{
			result =result * ((double)1- ((double)i/(double)365));
		}
		return 1- result; // two of them will not share birthday
	}
	
	
	
	
	public static double findPeople(double p)
	{
	    return Math.ceil(Math.sqrt(2 * 10000 * Math.log(1 / (1 - p))));
	}

}
