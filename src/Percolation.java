import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final boolean[][] grid;
    private int numberOfOpenSites;
    private final int topVirtualSite, bottomVirtualSite;
    private final WeightedQuickUnionUF weightedQuickUnionUF;

    // creates an n by n grid with all the sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        this.numberOfOpenSites = 0;
        final int ufSize = n * n + 2;
        grid = new boolean[n][n]; // Filled with zeros by default
        // 2 is added to cater for the virtual nodes
        weightedQuickUnionUF = new WeightedQuickUnionUF(ufSize); // UF structure
        this.topVirtualSite = 0;
        this.bottomVirtualSite = ufSize - 1;
        // Connect top & bottom sites to the virtual sites
        this.connectSitesToVirtualNodes();
    }

    // opens a site (row,column) if it is yet to be open
    public void open(int row, int col) {
        if (row > n || col > n || row <= 0 || col <= 0) throw new IllegalArgumentException();
        // Check if it is open first
        if (grid[row - 1][col - 1]) {
            return;
        }

        // 1 shows that a site is open
        grid[row - 1][col - 1] = true;
        // Attempt to connect the open site to it's open neighbours
        connectSiteToNeighbours(row, col);
        // Increase the number of open sites
        this.numberOfOpenSites ++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > n || col > n || row <= 0 || col <= 0) throw new IllegalArgumentException();
        return grid[row -1][col - 1] == true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > n || col > n || row <= 0 || col <= 0) throw new IllegalArgumentException();
        // Check if the site is open first (a full site must first be an open site)
        if (!isOpen(row, col)) return false;
        // Get the site index into the UF Structure
        int siteIndex = this.generateUFIndex(row, col);
        // A site is full if it is reachable (connected) from the top-row (connected to it)
        return weightedQuickUnionUF.find(topVirtualSite) == weightedQuickUnionUF.find(siteIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // The system percolates when any of the bottom cells are full
        return weightedQuickUnionUF.find(this.topVirtualSite) == weightedQuickUnionUF.find(this.bottomVirtualSite);
    }

    /*
        Connect the top site to the topmost virtual node and bottom sites to
        bottom-most virtual site.
    */
    private void connectSitesToVirtualNodes() {
        // Connect top sites to top virtual site (at index 0)
        for (int i = 1; i <= n; i++) {
            weightedQuickUnionUF.union(0, i);
        }

        // Connect bottom sites to bottom virtual site (at the last index).
        for (int i = (bottomVirtualSite - 1); i >= (bottomVirtualSite - n); i--) {
            weightedQuickUnionUF.union(bottomVirtualSite, i);
        }
    }

    // Generate an index into the UF Structure
    private int generateUFIndex(int row, int col){
        return (row - 1) * this.n + col;
    }

    // Connect a site to its neighbours upon opening it
    private void connectSiteToNeighbours(int row, int col) {
        // Get the site index for the UF structure
        int siteIndex = this.generateUFIndex(row, col);
        // Get the top, left, bottom and right neighbours of this site
        // Top site union
        unionWithNeighbour(row - 1, col, siteIndex);
        // Bottom site union
        unionWithNeighbour(row + 1, col, siteIndex);
        // Left site union
        unionWithNeighbour(row, col - 1, siteIndex);
        // Right site union
        unionWithNeighbour(row, col + 1, siteIndex);
    }

    // Perform union operation between a site and it's valid open neighbour
    private void unionWithNeighbour(int neighbourRow, int neighbourCol, int siteIndex) {
        if (isNeighbourValid(neighbourRow, neighbourCol)) {
            // Check if neighbour is open
            if (isOpen(neighbourRow, neighbourCol)) {
                // Get the neighbour's index
                int neighbourIndex = this.generateUFIndex(neighbourRow, neighbourCol);
                // Perform a union operation
                weightedQuickUnionUF.union(siteIndex, neighbourIndex);
            }
        }
    }

    // Check neighbour's site validity
    private boolean isNeighbourValid(int row, int col) {
        return (row > 0 && row <= this.n) && (col > 0 && col <= this.n);
    }

    // testing client
    public static void main(String...args) {
        final Percolation percolation = new Percolation(10);
        percolation.percolates();
    }
}
