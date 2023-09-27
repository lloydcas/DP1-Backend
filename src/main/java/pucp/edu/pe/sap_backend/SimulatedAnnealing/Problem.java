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
        List<Pedido> remainingPedidos = new ArrayList<>(pedidos); // Create a copy of all orders

        for (Vehiculo vehicle : vehiculo) {
            clonedVehiculos.add(new Vehiculo(vehicle));

            // Use a simple heuristic to assign orders to each vehicle
            LinkedList<Pedido> ordersForVehicle = assignOrdersHeuristically(vehicle, remainingPedidos);

            // Set the vehicle's route to the assigned orders
            vehicle.setOrder(ordersForVehicle);
            vehicle.generateRoute(blocks, almacenX, almacenY);

            // Remove the assigned orders from the list of remaining orders
            remainingPedidos.removeAll(ordersForVehicle);
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
        List<Pedido> availablePedidos = state.getPedidos();

        for (Vehiculo vehicle : vehiculos) {
            // Determine how to assign orders to each vehicle
            // For example, you can evenly distribute the orders among vehicles.
            LinkedList<Pedido> ordersForVehicle = new LinkedList<>();

            for (int i = 0; i < availablePedidos.size(); i++) {
                if (i % vehiculos.size() == vehiculos.indexOf(vehicle)) {
                    ordersForVehicle.add(availablePedidos.get(i));
                }
            }

            // Set the vehicle's route to the assigned orders
            vehicle.setOrder(ordersForVehicle);
            vehicle.generateRoute(state.getBlocks(), state.getAlmacenX(), state.getAlmacenY());

            // Remove the assigned orders from the list of available orders
            availablePedidos.removeAll(ordersForVehicle);
        }
    }

    public double calculateEnergy(State state) {
        double totalCost = 0.0;

        for (Vehiculo vehiculo : state.getVehiculo()) {
            // Use the Route class to calculate the cost of each vehicle's route
            Route route = new Route(vehiculo.getOrder().toArray(new Pedido[0]), state.getBlocks());
            totalCost += route.getCost();
        }

        return totalCost;
    }

    public double getFinalCost(State state) {
        return calculateEnergy(state);
    }
    public LinkedList<Pedido> assignOrdersHeuristically(Vehiculo vehicle, List<Pedido> orders) {
        LinkedList<Pedido> assignedOrders = new LinkedList<>();
        int remainingCapacity = vehicle.getCapacity();

        for (Pedido order : orders) {
            if (remainingCapacity >= order.getAmount()) {
                assignedOrders.add(order);
                remainingCapacity -= order.getAmount();
            }
        }

        return assignedOrders;
    }
}