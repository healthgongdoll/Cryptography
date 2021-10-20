package symmetric;

public class Stream_Cipher {
	public static void main(String[]args)
	{
		byte[] plaintext = "SENDMOREMONEY".getBytes();
		byte[] key =" JABHXPVOLLCIJ".getBytes();
		
		byte[]ciphertext = plaintext.clone();
		
		for(int i = 0; i<plaintext.length;i++)
		{
			ciphertext[i] = (byte) (plaintext[i] ^ key[i]);
		}
		
		
		for(int i = 0; i<ciphertext.length;i++)
		{
			System.out.print((char)ciphertext[i]);
		}
		
		for(int i = 0; i<plaintext.length;i++)
		{
			ciphertext[i] = (byte) (ciphertext[i] ^ key[i]);
		}
		System.out.println();
		for(int i = 0; i<ciphertext.length;i++)
		{
			System.out.print((char)ciphertext[i]);
		}
	}
}
