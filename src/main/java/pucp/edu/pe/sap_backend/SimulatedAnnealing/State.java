package pucp.edu.pe.sap_backend.SimulatedAnnealing;

import pucp.edu.pe.sap_backend.Genetico.Path;
import pucp.edu.pe.sap_backend.Genetico.Pedido;
import pucp.edu.pe.sap_backend.Genetico.Vehiculo;
import pucp.edu.pe.sap_backend.Ruta.BFS;

import java.util.List;

public class State {
    private List<Vehiculo> vehiculo;
    private List<Pedido> pedidos;
    private Problem problem;



    private BFS blocks;

    private int almacenX;
    private int almacenY;



    public State(List<Vehiculo> vehiculo, BFS blocks, int almacenX, int almacenY,List<Pedido>pedidos) {
        this.vehiculo = vehiculo;
        this.blocks = blocks;
        this.almacenX = almacenX;
        this.almacenY = almacenY;
        this.pedidos = pedidos;
    }
    public State(List<Vehiculo> vehiculo, Problem problem) {
        this.vehiculo = vehiculo;
        this.problem = problem;
    }
    public State(List<Vehiculo> vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<Vehiculo> getVehiculo() {
        return vehiculo;
    }

    public void setVehicles(List<Vehiculo> vehiculo) {
        this.vehiculo = vehiculo;
    }

    public double calculateTotalEnergy(BFS blocks, int almacenX, int almacenY) {
        double totalCost = 0.0;
        for (Vehiculo vehicle : vehiculo) {
            totalCost += calculateEnergyForEach(vehicle, blocks, almacenX, almacenY);
        }
        return totalCost;
    }
    public double calculateEnergyForEach(Vehiculo vehicle, BFS blocks, int almacenX, int almacenY) {
        List<Pedido> pedidos = vehicle.getOrder();

        // Check if the pedidos list is empty before processing
        if (pedidos.isEmpty()) {
            return 0.0; // Return 0 energy if there are no pedidos
        }

        Pedido[] pedidosArray = pedidos.toArray(new Pedido[0]);
        Path path = new Path(pedidosArray, blocks, almacenX, almacenY);
        return path.getCost();
    }

    //  get the value (total energy) here
    public double getValue(BFS blocks, int almacenX, int almacenY) {
        return calculateTotalEnergy(blocks, almacenX, almacenY);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("State Information:\n");

        for (int i = 0; i < vehiculo.size(); i++) {
            Vehiculo vehicle = vehiculo.get(i);
            sb.append("Vehicle ").append(i + 1).append(" Route: ").append(vehicle.getRoute()).append("\n");
            sb.append("Cost for Vehicle ").append(i + 1).append(": ").append(calculateEnergyForEach(vehicle, blocks, almacenX, almacenY)).append("\n");
        }

        sb.append("Total Cost: ").append(calculateTotalEnergy(blocks, almacenX, almacenY)).append("\n");

        return sb.toString();
    }
    public BFS getBlocks() {
        return blocks;
    }
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public int getAlmacenX() {
        return almacenX;
    }

    public int getAlmacenY() {
        return almacenY;
    }

}
