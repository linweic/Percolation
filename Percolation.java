public class Percolation {
    private boolean[][] site;
    private WeightedQuickUnionUF uf;
    private int size;
    private int IndexCal(int i, int j){
        if ( i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException("index " + i + " or " + j + " is not greater than 1");
        }
        else {
            return ((i-1) * size + j);
        }
    }
    private void validate(int i, int j){
        if (i < 1 || i > size || j < 1 || j > size){
            throw new IndexOutOfBoundsException("index " + i + " or " + j + " is not between 1 and " + size);
        }
    }
    public Percolation(int N) { // create N-by-N grid, with all sites blocked
        if (N <= 0){
            throw new IllegalArgumentException("size " + N + " is less than 0 ");
        }
        site = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N*N + 2);
        size = N;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++)
                site[i][j] = false;
        }
    }
    public void open(int i, int j) { // open site (row i, column j) if it is not open already
        validate(i, j);
        int a = i - 1;
        int b = j - 1;
        if (site[a][b]) return;
        else {
            site[a][b] = true;
            int x = IndexCal(i, j);
            if (b > 0 && site[a][b-1]){
                    int y = IndexCal(i, (j-1));
                    if (!uf.connected(x, y)){
                        uf.union(x, y); 
                    }
                }
            if (b < (size-1) && site[a][b + 1]){
                    int y = IndexCal(i, (j+1));
                    if (!uf.connected(x, y)){
                        uf.union(x, y);
                    }
                }
            if (a > 0 && site[a-1][b]){
                    int y = IndexCal((i-1), j);
                    if (!uf.connected(x, y)){
                        uf.union(x, y);
                    }
                }
            if (a < (size-1) && site[a+1][b]){
                    int y = IndexCal((i+1), j);
                    if (!uf.connected(x, y)){
                        uf.union(x, y);
                    }
                }
            if (i == 1) {uf.union(0,x);}
            if (i == size) {uf.union(size*size + 1,x);}
            return;
        }
    }
    public boolean isOpen(int i, int j) { // is site (row i, column j) open?
        validate(i, j);
        i = i-1;
        j = j-1;
        return site[i][j];
    }
    public boolean isFull(int i, int j) { // is site (row i, column j) full?
        validate(i, j);
        int c = IndexCal(i,j);
        if (uf.connected(0,c)) return true;
        else return false;
    }
        
    public boolean percolates() { // does the system percolate?
        if(uf.connected(size*size + 1, 0)) return true;
        else return false;
    }
    

    public static void main(String[] args) {}  // test client (optional)
        
}
