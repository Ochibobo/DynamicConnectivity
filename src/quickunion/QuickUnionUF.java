package quickunion;

// A Lazy Approach to Algorithm Design
// Only do work when you have to
// Each entry contains a pointer to its parent
// Trees can get too tall
public class QuickUnionUF {
    private int [] id;

    public QuickUnionUF(int N){
        id = new int[N];
        for(int i = 0; i < N; i++) id[i] = i;
    }

    // Find the root element
    private int root(int i){
        // If i isn't it's parent, set it to it's parent
        while(i != id[i]) i = id[i];
        // Return the root of the subset
        return i;
    }

    // Find checks if the p and q have the same root
    public boolean connected(int p, int q){
        return root(p) == root(q);
    }

    // To merge p & q, set the id of p's root to the id of q's root (union involves changing only 1 entry)
    public void union(int p, int q){
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }
}
