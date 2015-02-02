package solap.utils;

public class Pair<F extends Comparable<F>,
    S extends Comparable<S>> implements IPair<F,S>  {
    
    private F first;
    private S second;
    
    public Pair() {}

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
    
    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public int compareTo(IPair<F, S> other) {
        int result1 = this.first.compareTo(other.getFirst());
        int result2 = this.second.compareTo(other.getSecond());
        
        if(result1 != 0)
            return result1;
        else if(result2 != 0)
            return result2;
        else
            return 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Pair)) {
            return false;
        }
        final Pair other = (Pair)object;
        if (!(first == null ? other.first == null : first.equals(other.first))) {
            return false;
        }
        if (!(second == null ? other.second == null : second.equals(other.second))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((first == null) ? 0 : first.hashCode());
        result = PRIME * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }
}
