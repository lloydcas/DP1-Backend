package pucp.edu.pe.sap_backend.SimulatedAnnealing.AnotherApproach.S.A;

public class Tuple<T,N> {
    public T first;
    public N second;

    public Tuple(){
        first = null;
        second = null;
    }

    public Tuple(T first, N second){
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || obj.getClass() != Tuple.class) return false;

        var tuple = (Tuple<?, ?>)obj;
        return tuple.first.equals(this.first) && tuple.second.equals(second);
    }
}
