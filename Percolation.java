import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:              Evgeny Strelnikov
 *  Coursera User ID:
 *  Last modified:     11/19/2022
 **************************************************************************** */

public class Percolation {
    private int n; // initialized size of the grid side;
    private int topNode; // virtual node at the top of the grid. It is full by default.
    private int bottomNode; // virtual bottom node.
    private WeightedQuickUnionUF grid; // initialized 2D grid.
    private boolean[] openSites; // grid with site states. True means open site.
    private int count; // open sites counter.


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        count = 0;
        int size = n * n;
        grid = new WeightedQuickUnionUF(size + 2);
        openSites = new boolean[size + 2];
        topNode = 0;
        bottomNode = size + 1;
    }

    private void validate(int row, int col) {
        if ((row < 1) || (col < 1) || (row > n) || (col > n)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int flatten(int row, int col) {
        int index = (row - 1) * n + col;
        return index;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            int p = flatten(row, col);
            openSites[p] = true;
            this.count++;
            if (row == 1) {
                // if (grid.find(flatten(2,col)) == bottomNode)
                grid.union(topNode, p);
            }
            if (row == n) grid.union(bottomNode, p);

            if ((row > 1) && isOpen(row - 1, col)) {
                grid.union(p, (flatten(row - 1, col)));
            }
            if ((row < n) && isOpen(row + 1, col)) {
                grid.union(p, (flatten(row + 1, col)));
            }
            if ((col > 1) && isOpen(row, col - 1)) {
                grid.union(p, (flatten(row, col - 1)));
            }
            if ((col < n) && isOpen(row, col + 1)) {
                grid.union(p, (flatten(row, col + 1)));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openSites[flatten(row, col)];

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int p = flatten(row, col);
        return grid.find(p) == grid.find(topNode);

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.find(topNode) == grid.find(bottomNode);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 5;
        int row;
        int col;
        Percolation percolation = new Percolation(n);
        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(2, 4);
        percolation.open(3, 4);
        percolation.open(4, 4);
        percolation.open(4, 3);
        percolation.open(5, 3);
        System.out.println(percolation.percolates());
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                row = i;
                col = j;
                if (percolation.isOpen(row, col)) System.out.print("   ");
                else System.out.print(" * ");
            }
            System.out.println();
        }
    }
}
