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
    public int dist;      // distance
    public boolean blocked;
    public Cell prev;     // parent cell in the path
    public boolean isWarehouse; // indicates whether this cell is a warehouse

    public Cell(int x, int y, int dist, Cell prev, boolean isWarehouse) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.prev = prev;
        this.blocked = false;
        this.isWarehouse = isWarehouse;
    }
    public Cell(int x, int y, int dist, Cell prev) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.prev = prev;
        this.blocked=false;
    }
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isWarehouse = false; // Default to not a warehouse
    }

    public int manhattan(Cell node) {
        int distX = Math.abs(node.getX() - x);
        int distY = Math.abs(node.getY() - y);
        return distX + distY;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ") , ";
    }
}