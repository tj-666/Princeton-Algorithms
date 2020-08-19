import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] grid;
    private final int virtualTop, virtualBot;
    private final WeightedQuickUnionUF uf;
    private final int n;
    private int count = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Illegal");
        this.n = n;
        virtualTop = n * n;
        virtualBot = n * n + 1;
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);


    }

    // connecting if not connected
    private void union(int p, int q) {
        if (!(uf.find(p) == uf.find(q)))
            uf.union(p, q);

    }

    // find the index of id array in WQUF
    private int index(int row, int col) {
        return n * (row - 1) + col - 1;
    }

    // join the open sites
    private void join(int row, int col) {
        if (row == 1)
            union(index(row, col), virtualTop);
        else if (row == n)
            union(index(row, col), virtualBot);
        if (row != 1 && isOpen(row - 1, col))
            union(index(row, col), index(row - 1, col));
        if (row != n && isOpen(row + 1, col))
            union(index(row, col), index(row + 1, col));
        if (col != 1 && isOpen(row, col - 1))
            union(index(row, col), index(row, col - 1));
        if (col != n && isOpen(row, col + 1))
            union(index(row, col), index(row, col + 1));


    }

    // check the bound of input
    private void boundCheck(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("Illegal bound");
    }

    // opens the site (row, col) if it is not already open
    public void open(int row, int col) {
        boundCheck(row, col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            join(row, col);
            count++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        boundCheck(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site(row, col) full?
    public boolean isFull(int row, int col) {
        boundCheck(row, col);
        return uf.find(index(row, col)) == uf.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolates
    public boolean percolates() {
        return uf.find(virtualTop) == uf.find(virtualBot);
    }

}

