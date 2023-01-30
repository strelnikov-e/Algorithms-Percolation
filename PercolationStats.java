/* *****************************************************************************
 *  Name:              Evgeny Strelnikov
 *  Coursera User ID:
 *  Last modified:     11/19/2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] sampleMean;
    private final double CONSTANT = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if ((n <= 0) || (trials <= 0)) throw new IllegalArgumentException();
        sampleMean = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int p = StdRandom.uniformInt(1, n + 1);
                int q = StdRandom.uniformInt(1, n + 1);
                percolation.open(p, q);
            }
            sampleMean[i] = (percolation.numberOfOpenSites() / Math.pow(n, 2));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (double sample : sampleMean) {
            sum += sample;
        }
        return (sum / sampleMean.length);
    }

    // sample standard deviation of percolation threshold
    // s2=(x1−x)2+(x2−x)2+⋯+(xT−x)**2/T−1
    public double stddev() {
        double mean = mean();
        double numerator = 0.0;
        for (double sample : sampleMean) {
            double x = Math.pow((sample - mean), 2);
            numerator += x;
        }
        return Math.sqrt(numerator / (sampleMean.length - 1));
    }

    // low endpoint of 95% confidence interval
    // [x− (1.96s/T√,x⎯⎯⎯+1.96sT‾‾√]
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        double root = Math.sqrt(sampleMean.length);
        return mean - ((stddev * CONSTANT) / root);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        double root = Math.sqrt(sampleMean.length);
        return (mean + ((stddev * CONSTANT) / root));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]),
                                                                 Integer.parseInt(args[1]));
        // PercolationStats percolationStats = new PercolationStats(30, 1000);
        System.out.println("Mean\t\t\t\t\t = " + percolationStats.mean());
        System.out.println("stddev\t\t\t\t\t = " + percolationStats.stddev());
        System.out.println("95% confidence interval\t = [" + percolationStats.confidenceLo() +
                                   ", " + percolationStats.confidenceHi() + "]");
    }

}
