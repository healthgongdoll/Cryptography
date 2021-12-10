package exam;

import java.math.BigInteger;
import java.util.Arrays;

import util.CryptoTools;

public class Classical {

	public static void main(String[] args) throws Exception {
		RailFenceCipher("WECRLTEERDSOEEFEAOCAIVDEN", 3);
		CaesarCipherEncryption("this is jay hello".getBytes(), 7);
		CaesarCipherDecryption("AOPZPZQHFOLSSV".getBytes());
		AffineEncrypt("Hello This is Jay are you sure this is correct tho".getBytes());
		AffineDecrypt("WNIIRGWZDZDCBVBANVRJDJANGWZDZDHRAANHGGWR".getBytes());
		byte[] ct = CryptoTools.fileToBytes("data/q1v1.txt");
		vigenere(ct);
	}

	/*
	 * This is Transposition Ciphers = Plaintext's letters are rearranged
	 * Permutation of the plaintext WECRLTE ERDSOEEFEAOC AIVDEN
	 * 
	 */
	public static void RailFenceCipher(String cipher, int key) {
		String[][] board = new String[key][cipher.length()];

		for (String[] s : board) {
			Arrays.fill(s, "*");
		}
		int count = 0;
		boolean sw = false;
		for (int i = 0; i < cipher.length(); i++) {
			board[count][i] = "-";
			if (count == key - 1) {
				sw = true;
			}
			if (count == 0) {
				sw = false;
			}
			if (sw == false) {
				count++;
			}
			if (sw == true) {
				count--;
			}
		}

		int c = 0;
		for (int i = 0; i < key; i++) {
			for (int j = 0; j < cipher.length(); j++) {
				if (board[i][j].equals("-")) {
					board[i][j] = String.valueOf(cipher.charAt(c));
					c++;
				}
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	/*
	 * This is Shift Cipher Simple Encryption Using Take letter that follows after k
	 * position in the alphabet Example, k = 7 Plaintext = "Attack" Ciphertext =
	 * "haahr"
	 */
	public static void CaesarCipherEncryption(byte[] pt, int key) {
		pt = CryptoTools.clean(pt);
		byte[] ct = new byte[pt.length];

		for (int i = 0; i < pt.length; i++) {
			ct[i] = (byte) ((pt[i] - 'A' + key) % 26 + 'A');
		}

		for (int i = 0; i < pt.length; i++) {
			System.out.print((char) ct[i]);
		}
	}

	/*
	 * This is Shift Cipher Simple Decryption Using key
	 * 
	 */
	public static void CaesarCipherDecryption(byte[] ct) {
		ct = CryptoTools.clean(ct);
		int[] letterfreq;
		double dotproduct = 0;
		double max = 0;
		int key = 1;
		int answer = 0;
		for (int k = 0; k < 26; k++) {
			dotproduct = 0;
			byte[] ciphertext = ct;
			for (int i = 0; i < ct.length; i++) {
				ciphertext[i] = (byte) (((ct[i] - 'A') + key) % 26 + 'A');
			}

			/*
			 * Frequency Analysis What is the common letters? e is the most common letter Do
			 * the Cosine Similarity to compare the frequency if it close to the English it
			 * is higher possibilities of getting the answer
			 * 
			 */
			letterfreq = CryptoTools.getFrequencies(ciphertext);
			double sumA = 0, sumB = 0;
			for (int i = 0; i < 26; i++) {
				sumA += (letterfreq[i] * letterfreq[i]);
				sumB += (CryptoTools.ENGLISH[i] * CryptoTools.ENGLISH[i]);
				dotproduct = dotproduct + ((double) letterfreq[i]) * (CryptoTools.ENGLISH[i]);
			}
			dotproduct = dotproduct / Math.sqrt(sumA) / Math.sqrt(sumB);
			if (dotproduct > max) {
				max = dotproduct;
				answer = k;
			}

		}
		System.out.println();
		System.out.println(max);
		// Since I did + shifting to find key i have to -25
		System.out.println(Math.abs(answer - 25));
	}

	/*
	 * Affine Cipher Encryption -> C = alpha * p + beta mod 26 Decryption -> p =
	 * (c-beta) * a^-1 mod 26 Cipher which is a given key, the ciphertext alphabet
	 * for each plaintext alphabet is fixed throughout the encryption process
	 * 
	 */

	public static void AffineEncrypt(byte[] plain) {
		plain = CryptoTools.clean(plain);
		int alpha = 3;
		int beta = 1;
		byte[] cipher = plain.clone();
		for (int i = 0; i < plain.length; i++) {
			cipher[i] = (byte) ((((plain[i] - 'A') * alpha) + beta) % 26 + 'A');
		}

		for (int i = 0; i < cipher.length; i++) {
			System.out.print((char) cipher[i]);
		}
		System.out.println();
	}

	public static void AffineDecrypt(byte[] cipherText) {
		cipherText = CryptoTools.clean(cipherText);
		double max = 0;
		int alp = 0;
		int bet = 0;

		for (int a = 1; a < 26; a++) {

			if (gcd(a, 26) != 1) {
				continue;
			}
			for (int b = 0; b <= 26; b++) {
				double dotproduct = 0;
				BigInteger alpha = BigInteger.valueOf(a);
				BigInteger mod = BigInteger.valueOf(26);
				byte[] decrypt = cipherText.clone();
				for (int i = 0; i < cipherText.length; i++) {
					decrypt[i] = (byte) (((cipherText[i] - b + 'A') * alpha.modInverse(mod).intValue()) % 26 + 'A');
				}
				int[] letterfreq = CryptoTools.getFrequencies(decrypt);
				double sumA = 0, sumB = 0;

				for (int i = 0; i < 25; i++) {
					sumA += (letterfreq[i] * letterfreq[i]);
					sumB += (CryptoTools.ENGLISH[i] * CryptoTools.ENGLISH[i]);
					dotproduct = dotproduct + ((double) letterfreq[i]) * (CryptoTools.ENGLISH[i]);

				}
				dotproduct = dotproduct / Math.sqrt(sumA) / Math.sqrt(sumB);
				if (dotproduct > max) {
					max = dotproduct;
					alp = a;
					bet = b;

				}

			}
		}

		System.out.println(max);
		System.out.println(alp);
		System.out.println(bet);
	}

	/*
	 * Vigenere Cipher Encryption c = p+k mod 26 where k span K circulary Decryption
	 * p = c-k mod 26 where k span K circulary
	 */

	public static void vigenere(byte[] ciphertext) {
		int key_length = 2;
		int count = 0;
		double eIC = 0.0667;
		double ic = 0;
		double avgIc = 0;
		double dotproduct = 0;
		double[] saveavg = new double[101];

		for (int key = 2; key <= 100; key++) { // loop through the key
			for (int i = 0; i < key; i++) {
				byte[] decrypt = new byte[ciphertext.length];
				for (int j = 0; j < ciphertext.length; j++) {
					if (j % key == i) {
						decrypt[j] = ciphertext[j];
					}
				}
				decrypt = CryptoTools.clean(decrypt);
				ic += CryptoTools.getIC(decrypt);
			}
			avgIc = ic / key;
			saveavg[key] = avgIc;
			System.out.println(avgIc);
			ic = 0;
		}
		double minIndex = Integer.MAX_VALUE;

		int index = 0;
		for (int i = 0; i < saveavg.length; i++) {
			double abs = Math.abs(saveavg[i] - eIC);
			if (saveavg[i] / eIC > 0.90 && saveavg[i] / eIC < 1.05) {
				minIndex = abs;
				count++;
				index = i;
				break;
			}
		}
		System.out.println("Key Length:" + index); // key length 9
		// Foun the key Length
		// break the segment again
		for (int i = 0; i < index; i++) {
			byte[] decrypt = new byte[ciphertext.length];
			for (int j = 0; j < ciphertext.length; j++) {
				if (j % index == i) {
					decrypt[j] = ciphertext[j];
				}
			}
			decrypt = CryptoTools.clean(decrypt);
			for (int j = 0; j < decrypt.length; j++) {
				System.out.print((char) decrypt[j]);
			}
			System.out.println();
			double max = 0;
			int subkey = 0;
			// caesar cipher for each segment
			for (int x = 0; x < 26; x++) {
				dotproduct = 0;
				for (int y = 0; y < decrypt.length; y++) {
					decrypt[y] = (byte) (((decrypt[y] - 'A') + 1) % 26 + 'A');
				}
				int[] freq = CryptoTools.getFrequencies(decrypt);
				double sumA = 0, sumB = 0;
				for (int k = 0; k < 26; k++) {
					sumA += (freq[k] * freq[k]);
					sumB += (CryptoTools.ENGLISH[k] * CryptoTools.ENGLISH[k]);
					dotproduct = dotproduct + ((double) freq[k]) * (CryptoTools.ENGLISH[k]);
				}
				dotproduct = dotproduct / Math.sqrt(sumA) / Math.sqrt(sumB);
				if (max < dotproduct) {
					max = dotproduct;
					subkey = x;
				}
			}
			subkey = subkey + 1;
			// get the key for the segment
			System.out.println(max);

			System.out.println((char) (26 - subkey + 'A'));
			int di = 0;
			for (int x = 0; x < ciphertext.length; x++) {
				if (x % index == i) {
					ciphertext[x] = (byte) (((decrypt[di] - 'A') + subkey) % 26 + 'A');
					di++;
				}
			}
		}
		System.out.println("DECRYPTION: ");
		for (int i = 0; i < ciphertext.length; i++) {
			System.out.print((char) ciphertext[i]);
		}
		System.out.println();
	}

	public static int gcd(int a, int b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}
}
