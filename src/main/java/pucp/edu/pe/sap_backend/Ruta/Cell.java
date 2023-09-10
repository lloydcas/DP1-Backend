package pucp.edu.pe.sap_backend.Ruta;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Cell {
    public int x;
    public int y;
    public int dist;      //distance
    public boolean blocked;
    public Cell prev;  //parent cell in the path
    public Cell(int x, int y, int dist, Cell prev) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.prev = prev;
        this.blocked=false;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ") , ";
    }
}
