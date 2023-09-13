package pucp.edu.pe.sap_backend.SimulatedAnnealing;

import pucp.edu.pe.sap_backend.Genetico.Path;
import pucp.edu.pe.sap_backend.Genetico.Pedido;
import pucp.edu.pe.sap_backend.Genetico.Vehiculo;
import pucp.edu.pe.sap_backend.Ruta.BFS;

import java.util.*;

public class Problem {
    private List<Vehiculo> vehiculo;
    private BFS blocks;
    private int almacenX;
    private int almacenY;

    private List<Pedido> pedidos;
    public BFS getBlocks() {
        return blocks;
    }

    public Problem(List<Vehiculo> vehiculo, BFS blocks, int almacenX, int almacenY,List<Pedido>pedidos) {
        this.vehiculo = vehiculo;
        this.blocks = blocks;
        this.almacenX = almacenX;
        this.almacenY = almacenY;
        this.pedidos = pedidos;
    }

    public State getRandomSuccessor(State current) {
        State successor = cloneState(current);
        modifyRoutes(successor);
        return successor;
    }
    public State getInitialState(){
        List<Vehiculo> clonedVehiculos = new ArrayList<>();
        for (Vehiculo vehicle : vehiculo) {
            clonedVehiculos.add(new Vehiculo(vehicle));
        }
        return new State(clonedVehiculos, blocks, almacenX, almacenY, pedidos);
    }

    private State cloneState(State state){
        // Clone the state by creating new Vehiculo instances
        List<Vehiculo> clonedVehiculos = new ArrayList<>();
        for (Vehiculo vehicle : state.getVehiculo()) {
            clonedVehiculos.add(new Vehiculo(vehicle));
        }
        return new State(clonedVehiculos, state.getBlocks(), state.getAlmacenX(), state.getAlmacenY(), state.getPedidos());
    }
    private void modifyRoutes(State state){
        List<Vehiculo> vehiculos = state.getVehiculo();

        // Select a random vehicle
        Vehiculo randomVehicle = vehiculos.get(new Random().nextInt(vehiculos.size()));

        // Get the available pedidos that have not been assigned to any vehicle
        List<Pedido> availablePedidos = state.getPedidos();

        // You can implement your logic here to create a new route for the selected vehicle.
        // For example, you can randomly select a subset of availablePedidos and assign them to the vehicle.
        // Ensure that you also update the vehicle's route accordingly.
        randomVehicle.getRoute().clear();
        // For this example, let's assign available pedidos to the vehicle in a random order.
        Collections.shuffle(availablePedidos);

        // Create a new route for the vehicle using the selected pedidos
        randomVehicle.setOrder(new LinkedList<>(availablePedidos));

        randomVehicle.generateRoute(state.getBlocks(), state.getAlmacenX(), state.getAlmacenY());

        // Remove the assigned pedidos from the list of available pedidos
        availablePedidos.removeAll(randomVehicle.getOrder());
    }

    public double calculateEnergy(State state) {
        double totalCost = 0.0;

        for (Vehiculo vehiculo : state.getVehiculo()) {
            // Use the Route class to calculate the cost of each vehicle's route
            Route route = new Route(vehiculo.getOrder().toArray(new Pedido[0]), state.getBlocks(), state.getAlmacenX(), state.getAlmacenY());
            totalCost += route.getCost();
        }

        return totalCost;
    }

    public double getFinalCost(State state) {
        return calculateEnergy(state);
    }

}
