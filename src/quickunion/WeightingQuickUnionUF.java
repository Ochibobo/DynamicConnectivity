package quickunion;

// Avoid putting the large tree lower
// Depth of any node is lg(N)
public class WeightingQuickUnionUF {
    private int [] id;
    private int [] sz; // Array to store the sizes
    public WeightingQuickUnionUF(int N){
        id = new int[N];
        sz = new int[N];
        for(int i = 0; i < N; i++){
            id[i] = i;
            sz[i] = 1;
        }
    }

    // Find the root element
    private int root(int i){
//        // Compression to half:: Make each node point to its grandparent
//        while(i != id[i]) {
//            // Path compression -> make node point to its grandparent thus halving path length
//            id[i] = id[id[i]];
//            i = id[i];
//        }
//
//        return i;
        // Compression to point everything to root
        int root = i;
        while(root != id[root]) root = id[root]; // fetch the parent and repeat until you hit the root

        // Set all elements to a root element in its component set
        while(i != root){
            // Get the parent of i
            int next = id[i];
            //Point it to the root
            id[i] = root;
            // Set i to the parent value
            i = next;
        }
        return root;
    }

    // Find checks if the p and q have the same root
    public boolean connected(int p, int q){
        return root(p) == root(q);
    }

    // Union is updated
    public void union(int p, int q){
        int i = root(p);
        int j = root(q);
        if(i == j) return;
        // If the tree size at i > tree size at j, add tree j to i.
        if(sz[i] > sz[j]){
            id[j] = i;
            sz[j] += sz[i];
        } else {
            id[i] = j;
            sz[i] += sz[j];
        }
    }
}
