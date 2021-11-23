import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] numberOfOpenSites;
    private final int trials;
    private static final double CONFIDENCE_95 = 1.96;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        numberOfOpenSites = new double[trials];
        this.trials = trials;
        for (int i = 0; i < trials; i++) {
            // Create a new percolation instance
            final Percolation percolation = new Percolation(n);
            // While the system is not percolated
            while (!percolation.percolates()) {
                // Generate a random int
                int siteToOpen = StdRandom.uniform(n * n) + 1;
                // Open the site
                int row = ((siteToOpen - 1) / n) + 1;
                int col = siteToOpen % n;
                if(col == 0) col = n;
                // Open the site
                percolation.open(row, col);
                percolation.isFull(row, col);
            }
            numberOfOpenSites[i] = ((double)percolation.numberOfOpenSites() / (n * n));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
       return StdStats.mean(numberOfOpenSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if(trials == 1) return 0;
        return StdStats.stddev(numberOfOpenSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (CONFIDENCE_95 * this.stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (CONFIDENCE_95 * this.stddev()) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = 0, trials = 0;
        try {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex){
            StdOut.println(ex);
        }

        PercolationStats percolationStats = new PercolationStats(n, trials);
        StdOut.printf("mean %20s %s\n" , "=", percolationStats.mean());
        StdOut.printf("stddev %18s %s\n", "=", percolationStats.stddev());
        StdOut.printf("95%% confidence interval = [%s, %s]\n", percolationStats.confidenceLo(),percolationStats.confidenceHi());
    }
}
