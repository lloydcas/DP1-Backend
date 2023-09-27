package pucp.edu.pe.sap_backend.SimulatedAnnealing;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pucp.edu.pe.sap_backend.Genetico.Pedido;
import pucp.edu.pe.sap_backend.Genetico.Vehiculo;
import pucp.edu.pe.sap_backend.Ruta.BFS;
import pucp.edu.pe.sap_backend.Ruta.Cell;

import java.util.LinkedList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class Route {
    private Pedido[] path;
    private int numOrders;
    private double cost;
    private int fitness;
    private BFS blocks;
    private LinkedList<Cell> route;

    public Route(Pedido[] pedidos, BFS blocks) {
        this.numOrders = pedidos.length;
        this.blocks = blocks;
        this.path = pedidos; // Set the path to the provided pedidos
        cost = 0;
        this.route = new LinkedList<>();
        calculateCost(); // Remove the pedidos argument from here
        fitness = 0;

    }
    public void calculateCost() {
        if (path.length <= 1) {
            cost = 0.0; // No cost for empty or single-cell routes
            return;
        }

        cost = 0.0;

        for (int i = 0; i < path.length - 1; i++) {
            Cell fromCell = path[i].getCell();
            Cell toCell = path[i + 1].getCell();

            // Calculate the distance between consecutive cells using BFS
            List<Cell> path = blocks.shortestPath(
                    new int[]{fromCell.getX(), fromCell.getY()},
                    new int[]{toCell.getX(), toCell.getY()},
                    0,
                    null // You don't need the previous cell here
            );

            if (path != null) {
                // Accumulate the distance in the cost
                for (int j = 0; j < path.size() - 1; j++) {
                    cost += path.get(j).getDist();
                }
            } else {
                // Handle the case when no path is found (e.g., set a high cost)
                cost += 10000.0; // Adjust this according to your requirements
            }
        }
    }

    public double getCost(){
        return cost;
    }

    public List<Cell>getRoute(){return route;}

    public void initializeRoute(int almacenX, int almacenY){
        route.clear();  // Clear any existing route cells

        // Add the starting warehouse cell to the route
        Cell warehouseCell = new Cell(almacenX, almacenY);
        route.add(warehouseCell);

        // Add cells for each pedido in the path
        for (Pedido pedido : path) {
            Cell cell = new Cell(pedido.getX(), pedido.getY());
            route.add(cell);
        }
    }


}