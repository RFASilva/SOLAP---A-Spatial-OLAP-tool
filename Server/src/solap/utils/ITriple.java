package solap.utils;

import java.io.Serializable;
public interface ITriple<F extends Comparable<F>, S extends Comparable<S>,
        T extends Comparable<T>> extends Comparable<ITriple<F,S,T>>, Serializable {
        
        public F getFirst();
        public S getSecond();
        public T getThird();
        
        public void setFirst(F first);
        public void setSecond(S second);
        public void setThird(T third);
}
