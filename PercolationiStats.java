public class PercolationStats {
    private int size;
    private int times;
    private double[] results;
    private double computation(Percolation perco){
        double count = 0;
        while(!perco.percolates()){
            int i = StdRandom.uniform(1, size+1);
            int j = StdRandom.uniform(1, size+1);
            if (perco.isOpen(i,j)) continue;
            perco.open(i, j);
            count++;
        }
        return count/(size*size);
    }
    /*
    private int IndexCal(int i, int j){
        if ( i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException("index " + i + " or " + j + " is not greater than 1");
        }
        else {
            return ((i-1) * size + j - 1);
        }
    }
    */
    public PercolationStats(int N, int T){     // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0){
            throw new IllegalArgumentException("argument " + N + " or " + T + " is less than 0");
        }
        size = N;
        times = T;
        results = new double[T];
        for (int i = 0; i < T; i++){
            Percolation Perco = new Percolation(N);
            results[i] = computation(Perco);
        }
    }
    public double mean(){                      // sample mean of percolation threshold
        double sum = 0;
        for (int i = 0; i < times; i++){
            sum += results[i];
        }
        return sum/times;
    }
    public double stddev(){                    // sample standard deviation of percolation threshold
        double sum = 0;
        for (int i = 0; i < times; i++){
            sum += Math.pow((results[i] - mean()), 2);
        }
        return Math.sqrt(sum/(times-1));
    }
    public double confidenceLo(){              // low  endpoint of 95% confidence interval
        double low = mean() - 1.96 * Math.sqrt(stddev()/times);
        return low;
    }
    public double confidenceHi(){              // high endpoint of 95% confidence interval
        double high = mean() + 1.96 * Math.sqrt(stddev()/times);
        return high;
    }
    
    public static void main(String[] args){    // test client (described below)
        //int N = Integer.parseInt(args[0]);
        //int T = Integer.parseInt(args[1]);
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        //StdOut.println(N);
        //StdOut.println(T);
        //StdOut.println(N/T);
        PercolationStats PercoStats = new PercolationStats(N, T);
        StdOut.println("mean\t=" + PercoStats.mean());
        StdOut.println("stddev\t=" + PercoStats.stddev());
        StdOut.println("95% confidence interval\t=" + PercoStats.confidenceLo() + ", " + PercoStats.confidenceHi());
        //Percolation Perco = new Percolation(N);    
    }
}
