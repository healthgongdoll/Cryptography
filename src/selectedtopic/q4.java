package selectedtopic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class q4 {
	
	/*
	 * This is for PKI Asymmetric Protocol 
	 * 
	 * CA-> issue certificate for participants 
	 * 
	 * Why Eve cant not do mim attack
	 * 
	 * Cert(A) = P,A, Sig(P,A)
	 * 
	 * Eve = P,A' , there is no way to generate Sig(P,A')
	 * 
	 */
	public static void main(String[]args) throws IOException
	{
				//KCA Private Key 
				BigInteger KCApri = new BigInteger("7289370196881601766768920490284861650464951706793000236386405648425161747775298344104658393385359209126267833888223695609366844098655240542152017354442883676634193191857568369042999854440242050353181703706753485749165295123694487676952198090537385200990850805837963871485320168470788328336240930212290450023");
				
				
				//Publicly Known Factors 
				BigInteger alpha = new BigInteger("341769842231234673709819975074677605139");
				BigInteger p = new BigInteger("37186859139075205179672162892481226795");
				
				//Publickey KCA
				BigInteger KCApub = alpha.modPow(KCApri, p);
				
				//Alice Private
				BigInteger AlicePri = new BigInteger("83986164647417479907629397738411168307");
				//Bob Private 
				BigInteger BobPri = new BigInteger("140479748264028247931575653178988397140");
				
				//Alice Public key
				BigInteger publicAlice = alpha.modPow(AlicePri, p);
				System.out.println("Alice Public Key: " + publicAlice);
				byte[] publicAliceByte = publicAlice.toByteArray();
				byte[] aliceID = "This is Alice".getBytes();
				
				//(A,AID)
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				outputStream.write(publicAliceByte);
				outputStream.write(aliceID);
				byte[] AandIDA = outputStream.toByteArray();
				BigInteger AandAID = new BigInteger(AandIDA);
				
				
				//CA compute signature (A,AID)
				BigInteger sigAlice = alpha.modPow(KCApri, p);
				
				
				//Bob Public Key 
				BigInteger publicBob = alpha.modPow(BobPri, p);
				byte[] bobID = "This is Bob".getBytes();
				
				
				//KCA Cert(Alice) encrypts with Private key 
				
				
				
				
				
				
	}
}
