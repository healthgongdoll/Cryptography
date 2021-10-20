package symmetric;

public class RC4 {

	public static void main(String[] args) {
		// key scheduling
		int[] s = new int[256];
		boolean[] visited = new boolean[256];
		byte[] key = "EECS".getBytes();
		byte[] keyArr = new byte[256];
		for (int i = 0; i < 256; i++) {
			s[i] = i;
			keyArr[i] = key[i % key.length];
		}
		int j = 0;
		for (int i = 0; i < 256; i++) {
			j = (j + s[i] + key[i % key.length]) % 256;
			swap(i, j, s);
		}

		for (int i = 0; i < 256; i++) {
			System.out.print(s[i] + " ");
		}
		// ----ends
		//pseudo-random generator (PRNG)
		System.out.println();
		int i = 0; 
		int k = 0;
		int count = 0;
		while(count != 256)
		{
			i = (i + 1) % 256;
			k = (k + s[i]) % 256;
			int p = s[(s[i]+s[j]) %256];	
			System.out.print(p + " ");
			count = count + 1;
		}

	}

	public static void swap(int i, int j, int[] arr) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
