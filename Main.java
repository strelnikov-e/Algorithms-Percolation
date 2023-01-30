/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.Out;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int size = 30;
        Out out = new Out("myFile.txt");
        out.println(size);
        for (int i = 0; i < size * size - 1; i++) {
            out.print(random.nextInt(size) + 1);
            out.print(" ");
            out.println(random.nextInt(size) + 1);

        }
    }
}
