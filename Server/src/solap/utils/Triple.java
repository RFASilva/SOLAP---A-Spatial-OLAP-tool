package solap.utils;

public class Triple<F extends Comparable<F>,
    S extends Comparable<S>,T extends Comparable<T>> implements ITriple<F,S,T> {

    private static final long serialVersionUID = 1L;
    private F first;
    private S second;
    private T third;
    
    public Triple() {
    }
    
    public Triple(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
    
    public F getFirst() {
        return first;
    }
    
    public S getSecond() {
        return second;
    }
    
    public T getThird() {
        return third;
    }
    
    public void setFirst(F first) {
        this.first = first;
    }
    
    public void setSecond(S second) {
        this.second = second;
    }
    
    public void setThird(T third) {
        this.third = third;
    }
    
    public void info() {
        System.out.println("First: " + first + " Second: " + second + " Third: " +third);
    }

    @Override
    public int compareTo(ITriple<F, S, T> other) {
        int result1 = this.first.compareTo(other.getFirst());
        int result2 = this.second.compareTo(other.getSecond());
        int result3 = this.third.compareTo(other.getThird());
        
        if(result1 != 0)
            return result1;
        else if(result2 != 0)
            return result2;
        else if(result3 != 0)
            return result3;
        else
            return 0;
    }
    
    @Override
    public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((first == null) ? 0 : first.hashCode());
            result = prime * result + ((second == null) ? 0 : second.hashCode());
            result = prime * result + ((third == null) ? 0 : third.hashCode());
            return result;
    }

    @Override
    public boolean equals(Object obj) {
            if (this == obj)
                    return true;
            if (obj == null)
                    return false;
            if (!(obj instanceof Triple))
                    return false;
            Triple other = (Triple) obj;
            if (first == null) {
                    if (other.first != null)
                            return false;
            } else if (!first.equals(other.first))
                    return false;
            if (second == null) {
                    if (other.second != null)
                            return false;
            } else if (!second.equals(other.second))
                    return false;
            if (third == null) {
                    if (other.third != null)
                            return false;
            } else if (!third.equals(other.third))
                    return false;
            return true;
    }
}
