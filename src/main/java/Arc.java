

public class Arc {
    private int src;
    private int dest;
    private int poids;

    Arc(int s, int d, int p){
        this.src = s;
        this.dest = d;
        this.poids = p;
    }

    public int getSrc() {
        return src;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public int compareTo(Arc compareEdge){
        return this.poids - compareEdge.poids;
    }
    
}