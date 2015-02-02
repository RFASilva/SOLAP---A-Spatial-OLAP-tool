package solap.utils;

import java.io.Serializable;

public interface IPair<F extends Comparable<F>, S extends Comparable<S>> extends Comparable<IPair<F,S>>, Serializable {
        
        public F getFirst();
        public S getSecond();
        
        public void setFirst(F first);
        public void setSecond(S second);
}
