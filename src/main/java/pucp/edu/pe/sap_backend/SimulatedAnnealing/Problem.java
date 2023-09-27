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

    private List<Almacen> almacenes;
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
    public Problem(List<Vehiculo> vehiculo, BFS blocks, List<Almacen> almacenes, List<Pedido>pedidos) {
        this.vehiculo = vehiculo;
        this.blocks = blocks;
        this.pedidos = pedidos;
        this.almacenes = almacenes;
    }
    public State getRandomSuccessor(State current) {
        State successor = cloneState(current);
        modifyRoutes(successor);
        return successor;
    }
    public State getInitialState() {
        List<Vehiculo> clonedVehiculos = new ArrayList<>();
        List<Pedido> remainingPedidos = new ArrayList<>(pedidos); // Create a copy of all orders

        for (Vehiculo vehicle : vehiculo) {
            // Clone the vehicle and set its nearest warehouse
            Vehiculo clonedVehicle = new Vehiculo(vehicle, almacenes);
            clonedVehicle.setNearestWarehouse(vehicle.getNearestWarehouse());

            // Use a simple heuristic to assign orders to each vehicle
            LinkedList<Pedido> ordersForVehicle = assignOrdersHeuristically(clonedVehicle, remainingPedidos, clonedVehicle.getNearestWarehouse());

            // Set the vehicle's route to the assigned orders
            clonedVehicle.setOrder(ordersForVehicle);
            clonedVehicle.generateRoute(blocks, clonedVehicle.getNearestWarehouse().getX(), clonedVehicle.getNearestWarehouse().getY());

            // Remove the assigned orders from the list of remaining orders
            remainingPedidos.removeAll(ordersForVehicle);

            clonedVehiculos.add(clonedVehicle);
        }

        return new State(clonedVehiculos, blocks, almacenX, almacenY, pedidos, almacenes);
    }


    private Almacen findNearesrWarehouse( Vehiculo vehicle){
        double shortestDistance = 10000000.00;
        Almacen nearestWarehouse = null;
        for(Almacen almacen : almacenes){
            int almacenx = almacen.getX();
            int almacenY = almacen.getY();
            int vehicleX = vehicle.getXInicial();
            int vehicleY = vehicle.getYInicial();

            // Calculate Euclidean distance between the vehicle and the warehouse
            double distance = Math.sqrt(Math.pow(almacenx - vehicleX, 2) + Math.pow(almacenY - vehicleY, 2));

            // Check if this warehouse is closer than the previous closest one
            if (distance < shortestDistance) {
                shortestDistance = distance;
                nearestWarehouse = almacen;
            }
        }
        return nearestWarehouse;
    }

    private State cloneState(State state) {
        // Clone the state by creating new Vehiculo instances
        List<Vehiculo> clonedVehiculos = new ArrayList<>();
        for (Vehiculo vehicle : state.getVehiculo()) {
            Vehiculo clonedVehicle = new Vehiculo(vehicle, almacenes);
            clonedVehicle.setNearestWarehouse(vehicle.getNearestWarehouse()); // Pass the nearest warehouse
            clonedVehiculos.add(clonedVehicle);
        }
        return new State(clonedVehiculos, state.getBlocks(), state.getAlmacenX(), state.getAlmacenY(), state.getPedidos(), almacenes);
    }
    private void modifyRoutes(State state) {
        List<Vehiculo> vehiculos = state.getVehiculo();
        List<Pedido> availablePedidos = state.getPedidos();

        int numVehiculos = vehiculos.size();
        int ordersPerVehicle = availablePedidos.size() / numVehiculos;
        int extraOrders = availablePedidos.size() % numVehiculos; // Distribute any remaining orders

        int startIndex = 0;

        for (Vehiculo vehicle : vehiculos) {
            LinkedList<Pedido> ordersForVehicle = new LinkedList<>();

            // Assign orders evenly to each vehicle
            int numOrders = ordersPerVehicle + (extraOrders > 0 ? 1 : 0);
            extraOrders--;

            for (int i = 0; i < numOrders; i++) {
                ordersForVehicle.add(availablePedidos.get(startIndex));
                startIndex++;
            }

            // Set the vehicle's route to the assigned orders
            vehicle.setOrder(ordersForVehicle);
            vehicle.generateRoute(state.getBlocks(), vehicle.getNearestWarehouse().getX(),
                    vehicle.getNearestWarehouse().getY());
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
    public LinkedList<Pedido> assignOrdersHeuristically(Vehiculo vehicle, List<Pedido> orders,Almacen nearestWarehouse) {
        LinkedList<Pedido> assignedOrders = new LinkedList<>();
        int remainingCapacity = vehicle.getCapacity();
        int vehicleX = vehicle.getXInicial();
        int vehicleY = vehicle.getYInicial();
        for (Pedido order : orders) {

            // Calculate the distance from the vehicle's initial position to the order's pickup point
            double distanceToOrder = Math.sqrt(Math.pow(order.getX() - vehicleX, 2) + Math.pow(order.getY() - vehicleY, 2));

            // Calculate the distance from the vehicle's initial position to the nearest warehouse
            double distanceToWarehouse = Math.sqrt(Math.pow(nearestWarehouse.getX() - vehicleX, 2) + Math.pow(nearestWarehouse.getY() - vehicleY, 2));

            // Check if the order is closer to the vehicle's initial position than the nearest warehouse
            if (distanceToOrder <= distanceToWarehouse && remainingCapacity >= order.getAmount()) {
                assignedOrders.add(order);
                remainingCapacity -= order.getAmount();
            }

        }

        return assignedOrders;
    }
}