package asymmetric;

import java.math.BigInteger;

//Java program to demonstrate working of extended
//Euclidean Algorithm

import java.util.*;
import java.lang.*;

public class ConceptualQ {
		public static void main(String[]args)
		{
			BigInteger n = new BigInteger("136745826084894079896407801910110038124479691221364763961671283913316027446990472604166612638266533599236499833462893193203370069481216752405013210606536832449253647053485706549372755012732494721775959042565139073644140943011453312866103727053029250591408277684758918149145313790583917977714246616161591211867");
			BigInteger exN = new BigInteger("179");
			BigInteger eA = new BigInteger("7");
			BigInteger eB = new BigInteger("11");
			BigInteger exA = new BigInteger("32");
			BigInteger exB = new BigInteger("127");
			BigInteger cA = new BigInteger("96877134584306777318146328481686066222141330594741430174446869322397957214185808322840439483745941838089581856276435139923985154286304605247720570032111445774456843140029117473338511849604175899567577575869260936284515510510996731062938376853679783133818780455950141113597757693159944213099453748070224766330");
			BigInteger cB = new BigInteger("23444046879417396807258781473983446335419912082084569500429331818137930632660557096072074135726913203064466954036513216225141761504938430253703707788298770147822214014349806771971766671953205102486778576265971021057237610662398273682920299760392069897739849342885155671199969974517817986247611249755737698500");
			
			BigInteger [] result = xgcd(eA,eB);
			System.out.println(result[1]);
			System.out.println(result[2]);
			BigInteger i = cA.modInverse(n);
//			System.out.println(i);
			
			BigInteger m = (i.pow(result[1].intValue()*-1).multiply(cB.pow(result[2].intValue()))).mod(n);
			
			System.out.println(m);
			
			byte[] pt = m.toByteArray();
			
			for(int j = 0; j<pt.length;j++)
			{
				System.out.print((char)pt[j]);
			}
			
		}
	    public static BigInteger[] xgcd(BigInteger a, BigInteger b) {
	    	BigInteger x = a, y=b;
	    	BigInteger[] qrem = new BigInteger[2];
	    	BigInteger[] result = new BigInteger[3];
	    	BigInteger x0 = BigInteger.ONE, x1 = BigInteger.ZERO;
	    	BigInteger y0 = BigInteger.ZERO, y1 = BigInteger.ONE;
	    	while (true){
	    	    qrem = x.divideAndRemainder(y); x = qrem[1];
	    	    x0 = x0.subtract(y0.multiply(qrem[0]));
	    	    x1 = x1.subtract(y1.multiply(qrem[0]));
	    	    if (x.equals(BigInteger.ZERO)) {result[0]=y; result[1]=y0; result[2]=y1; return result;};
	    	    qrem = y.divideAndRemainder(x); y = qrem[1];
	    	    y0 = y0.subtract(x0.multiply(qrem[0]));
	    	    y1 = y1.subtract(x1.multiply(qrem[0]));
	    	    if (y.equals(BigInteger.ZERO)) {result[0]=x; result[1]=x0; result[2]=x1; return result;};
	    	}
	        }
	}
