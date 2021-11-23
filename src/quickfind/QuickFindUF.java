package quickfind;
// Eager Approach to Algorithm Design
public class QuickFindUF {
    // The ID array
    private int [] id;
    public QuickFindUF(int N){
        id = new int[N];
        for(int i = 0; i < N; i++){
            id[i] = i;
        }
    }


    // Check if values are connected
    public boolean connected(int p, int q){
        return id[p] == id[q];
    }


    // The union function
    // Quadratic time if you have N union commands on N objects
    public void union(int p, int q){
        int pid = id[p];
        int qid = id[q];

        // Update all components with the pid to qid
        for(int i = 0; i < id.length; i++){
            if(id[i] == pid) id[i] = qid;
        }
    }
}
