package pucp.edu.pe.sap_backend.SimulatedAnnealing;

import pucp.edu.pe.sap_backend.Genetico.Path;
import pucp.edu.pe.sap_backend.Genetico.Pedido;
import pucp.edu.pe.sap_backend.Genetico.Vehiculo;
import pucp.edu.pe.sap_backend.Ruta.BFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Problem {
    private List<Vehiculo> vehiculo;
    private BFS blocks;
    private int almacenX;
    private int almacenY;


    public BFS getBlocks() {
        return blocks;
    }

    public int getAlmacenX() {
        return almacenX;
    }

    public int getAlmacenY() {
        return almacenY;
    }
    public Problem(List<Vehiculo> vehiculo, BFS blocks, int almacenX, int almacenY) {
        this.vehiculo = vehiculo;
        this.blocks = blocks;
        this.almacenX = almacenX;
        this.almacenY = almacenY;
    }

    public State getRandomSuccessor(State current) {
        State successor = cloneState(current);
        modifyRoutes(successor);
        return successor;
    }
    public State getInitialState(){
        State initialState = new State(vehiculo, blocks, almacenX, almacenY);
        return initialState;
    }

    private State cloneState(State state){
        List<Vehiculo> clonedVehiculos = new ArrayList<>();
        for (Vehiculo vehicle : state.getVehiculo()) {
            Vehiculo clonedVehicle = new Vehiculo(vehicle);
            clonedVehiculos.add(clonedVehicle);
        }
        return new State(clonedVehiculos);
    }
    private void modifyRoutes(State state){
        List<Vehiculo> vehiculo = state.getVehiculo();

        // Select a random vehicle
        Vehiculo randomVehicle;
        Random random = new Random();
        randomVehicle = vehiculo.get(random.nextInt(vehiculo.size()));
        // Implement logic to modify the route of the random vehicle
        randomVehicle.generateRoute(blocks, almacenX, almacenY);
    }
    public double calculateEnergy(State state) {
        double totalCost = 0.0;
        for (Vehiculo vehiculo : state.getVehiculo()) {
            Pedido[] pedidos = vehiculo.getOrder().toArray(new Pedido[0]);
            Path path = new Path(pedidos, blocks, almacenX, almacenY);
            totalCost += path.getCost();
        }
        return totalCost;
    }

    public double getFinalCost(State state) {
        return calculateEnergy(state);
    }

}
