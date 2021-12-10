package hash;

import java.util.Arrays;
import java.util.Random;

public class check {
    public static void main(String[] args) {
        int count = 0;
        int trial = 1000000;
        int x = 23;
        for (int j = 0; j < trial; j++) {
            int[] classroom = new int[x];
            for (int i = 0; i < x; i++) {
                do {
                    classroom[i] = new Random().nextInt(366);
                } while (classroom[i] == 0);
            }
            Arrays.sort(classroom);
            int i = 1;
            for (; i < x; i++) {
                if (classroom[i - 1] == classroom[i]) {
                    break;
                }
            }
            if (i == x) {
                count++;
            }
        }
        double pn = (double) count / trial;
        double p = 1 - pn;
        System.out.println("p (none of people in the room has the same birthday) = " + pn);
        System.out.println("p (at least two of the people in the room have the same birthday) = " + p);
    }
}