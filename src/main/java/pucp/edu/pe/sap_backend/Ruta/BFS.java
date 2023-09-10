package pucp.edu.pe.sap_backend.Ruta;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.LinkedList;
@ToString
@EqualsAndHashCode
public class BFS {

    private BlockMap map;

    public BFS(BlockMap map) {
        this.map = map;
    }

    //BFS, Time O(n^2), Space O(n^2)
    public LinkedList<Cell> shortestPath(int[] start, int[] end,int type, Cell last) {
        int sx = start[0], sy = start[1];
        int dx = end[0], dy = end[1];

        //initialize the cells
        int m = map.getMap().length;
        int n = map.getMap()[0].length;
        int recorrido_tiempo = Math.abs(start[0] - end[0]) + Math.abs(start[1] - end[1]) + 30;
        //int recorrido_tiempo = Integer.MAX_VALUE;
        Cell[][] cells = new Cell[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //Accept up to 10 minutes after shortest distance between points
                cells[i][j] = new Cell(i, j, recorrido_tiempo, null);
            }
        }
        //breadth first search
        Cell p;
        LinkedList<Cell> queue = new LinkedList<>();
        try{
            Cell src = cells[sx][sy];
            src.dist = 0;
            queue.add(src);
            if(last!=null){
                src= cells[last.x][last.y];
                p=queue.poll();
                src.dist = 1;
                src.prev=p;
                queue.add(src);
            }
        }catch (Exception e){
            System.out.print(e);
            return null;
        }

        Cell dest = null;

        int coord_x = 0;
        int coord_y = 0;
        while ((p = queue.poll()) != null) {
            //find destination
            coord_x = p.x;
            coord_y = p.y;
            if (coord_x == dx && coord_y == dy) {
                dest = p;
                break;
            }
            // moving up
            visit(cells, queue, coord_x - 1, coord_y, p,map,dx,dy);
            // moving down
            visit(cells, queue, coord_x + 1, coord_y, p,map,dx,dy);
            // moving left
            visit(cells, queue, coord_x, coord_y - 1, p,map,dx,dy);
            //moving right
            visit(cells, queue, coord_x, coord_y + 1, p,map,dx,dy);
        }

        //compose the path if path exists
        if (dest == null) {
            return null;
        } else {
            LinkedList<Cell> path = new LinkedList<>();
            p = dest;
            do {
                path.addFirst(p);
            } while ((p = p.prev) != null);
            return path;
        }
    }

    //function to update cell visiting status, Time O(1), Space O(1)
    private void visit(Cell[][] cells, LinkedList<Cell> queue, int x, int y, Cell parent, BlockMap map, int dx, int dy) {
        //out of boundary
        if (x < 0 || x >= cells.length || y < 0 || y >= cells[0].length || cells[x][y] == null) {
            return;
        }

        //update distance, and previous node
        int dist = parent.dist + 1;
        Cell p = cells[x][y];

        if (dist < p.dist) {
            p.dist = dist;
            p.prev = parent;
            queue.add(p);
        }
    }

    public BlockMap getMap() {
        return map;
    }

    public void setMap(BlockMap map) {
        this.map = map;
    }
}
