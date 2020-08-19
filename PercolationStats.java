import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int noOfTimes;
    private final double[] fraction;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Illegal arguments");
        this.noOfTimes = trials;
        Percolation per = new Percolation(n);
        fraction = new double[noOfTimes];
        int row, col;
        for (int i = 0; i < noOfTimes; i++) {
            while (!per.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                per.open(row, col);

            }
            fraction[i] = per.numberOfOpenSites() * 1.0 / (n * n);
        }


    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fraction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(noOfTimes);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(noOfTimes);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.printf("mean                    = %.16f \n", ps.mean());
        StdOut.printf("stddev                  = %.16f \n", ps.stddev());
        StdOut.printf("95%% confidence interval = [%.16f, %.16f] \n", ps.confidenceLo(), ps.confidenceHi());


    }
}
