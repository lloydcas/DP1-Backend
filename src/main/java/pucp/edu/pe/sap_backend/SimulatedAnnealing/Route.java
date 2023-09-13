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

    public Route(Pedido[] pedidos, BFS blocks, int almacenX, int almacenY) {
        this.numOrders = pedidos.length;
        this.blocks = blocks;
        this.path = pedidos; // Set the path to the provided pedidos
        cost = 0;
        this.route = new LinkedList<>();
        calculateCost(almacenX, almacenY); // Remove the pedidos argument from here
        fitness = 0;
    }
    public void calculateCost(int almacenX,int almacenY) {
        if (route.size() <= 1) {
            cost = 0.0; // No cost for empty or single-cell routes
            return;
        }

        cost = 0.0;
        Cell prevCell = null;

        for (Cell cell : route) {
            if (prevCell != null) {
                // Calculate the distance between consecutive cells using BFS
                List<Cell> path = blocks.shortestPath(
                        new int[]{prevCell.getX(), prevCell.getY()},
                        new int[]{cell.getX(), cell.getY()},
                        0,
                        prevCell
                );

                if (path != null) {
                    // Accumulate the distance in the cost
                    for (int i = 0; i < path.size() - 1; i++) {
                        cost += path.get(i).getDist();
                    }
                } else {
                    // Handle the case when no path is found (e.g., set a high cost)
                    cost += 10000.0; // Adjust this according to your requirements
                }
            }

            prevCell = cell;
        }

        // Add the distance from the last cell to the warehouse
        List<Cell> pathToWarehouse = blocks.shortestPath(
                new int[]{prevCell.getX(), prevCell.getY()},
                new int[]{almacenX, almacenY},
                0,
                prevCell
        );

        if (pathToWarehouse != null) {
            cost += pathToWarehouse.get(pathToWarehouse.size() - 1).getDist();
        } else {
            // Handle the case when no path to the warehouse is found
            cost += 10000.0; // Adjust this according to your requirements
        }
    }

    public double getCost(){
        return cost;
    }

}