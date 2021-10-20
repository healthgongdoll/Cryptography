package symmetric;

public class conceptualQ1 {
	public static void main(String[]args)
	{
		byte[] plaintext = "SENDMOREMONEY".getBytes();
		byte[] key = "JABHXPVOLLCIJ".getBytes();
		byte[]ciphertext = plaintext.clone();
		
		
		for(int i = 0; i<plaintext.length;i++)
		{
			ciphertext[i] = (byte) (((plaintext[i] + key[i] ) % 26)+'A');
		}
		
		for(int i = 0; i<plaintext.length;i++)
		{
			System.out.print((char)ciphertext[i]);
		}
	}

}
