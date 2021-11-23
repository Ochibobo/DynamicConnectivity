package successordelete;

/**
 * Successor with delete. Given a set of nn integers S={0,1,...,n−1} and a sequence of requests of the following form:
 *
 * Remove x from S
 *
 * Find the successor of x: the smallest y in SS such that y >= x.
 *
 * design a data type so that all operations (except construction)  take logarithmic time or better in the worst case.
 */
public class SuccessorDelete {
    private int[] id;
    private int[] sz;
    private int[] max;

    public SuccessorDelete(int n){
        id = new int[n];
        sz = new int[n];
        max = new int[n];
        for(int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1;
            max[i] = i;
        }
    }

    private int root(int i){
        int root = i;
        while(root != id[root]){
            root = id[root];
        }
        // Path compression
        while(i != root){
            int parent = id[i];
            id[i] = root;
            i = parent;
        }
        return root;
    }

    public int union(int p, int q){
        int i = root(p);
        int j = root(q);

        if(sz[i] >= sz[j]){
            id[j] = i;
            sz[i] += sz[j];
            if(max[j] > max[i]){
                max[i] = max[j];
            }
            return max[i];
        } else {
            id[i] = j;
            sz[j] += sz[i];
            if(max[i] > max[j]){
                max[j] = max[i];
            }
            return max[j];
        }
    }

    public int delete(int p){
        if(p == id.length - 1) return p;
        return union(p, p + 1);
    }

    public int find(int i){
        return max[root(id[i])];
    }
}
