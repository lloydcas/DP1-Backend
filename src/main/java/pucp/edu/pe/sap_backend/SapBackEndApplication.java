package pucp.edu.pe.sap_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pucp.edu.pe.sap_backend.Genetico.Genetico;
import pucp.edu.pe.sap_backend.Genetico.Pedido;
import pucp.edu.pe.sap_backend.Genetico.Vehiculo;
import pucp.edu.pe.sap_backend.Ruta.BFS;
import pucp.edu.pe.sap_backend.Ruta.BlockMap;
import pucp.edu.pe.sap_backend.SimulatedAnnealing.Problem;
import pucp.edu.pe.sap_backend.SimulatedAnnealing.SimulatedAnnealing;
import pucp.edu.pe.sap_backend.SimulatedAnnealing.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@SpringBootApplication
public class SapBackEndApplication {

    public static void main(String[] args) {

        //ESTANDAR BASICO
        Genetico genetico = new Genetico(1, 12, 8);


        //PRIMER CUADRANTE
        Pedido pedido1 = new Pedido(123, 0, 5, 5, 5);
        Pedido pedido2 = new Pedido(124, 1, 10, 12, 10);
        Pedido pedido3 = new Pedido(125, 2, 15, 19, 5);

        LinkedList<Pedido> listaPedidos = new LinkedList<>();
        listaPedidos.add(pedido1);
        listaPedidos.add(pedido2);
        listaPedidos.add(pedido3);

        genetico.setOrders(listaPedidos);
        Vehiculo vehiculo = new Vehiculo(1,100,0,12,8);//(0,0) es donde parte)
        Vehiculo vehiculo1 =   new Vehiculo(2,150,0,20,10);//(0,0) es donde parte)
        ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();
        listaVehiculos.add(vehiculo);
        listaVehiculos.add(vehiculo1);
        BFS blocks = new BFS(new BlockMap(100,100));
        genetico.setCars(listaVehiculos);
        genetico.setOrders(listaPedidos);


        genetico.setBlocks(blocks);
        genetico.executeAlgorithm();
        System.out.println("\nSimulated annealing: \n ");

        ////////////////////////////// Annealing////////////////////////

        pedido1 = new Pedido(123,0,5,5,5);
        pedido2 = new Pedido(124,1,10,12,10);
        pedido3 = new Pedido(125,2,15,19,5);
        listaPedidos = new LinkedList<>();
        listaPedidos.add(pedido1);
        listaPedidos.add(pedido2);
        listaPedidos.add(pedido3);
        Problem problem = new Problem(listaVehiculos,blocks,10,10,listaPedidos);
        Map<Integer,Double> schedule = new HashMap<>();
        schedule.put(0,1000.0);
        schedule.put(100,800.0);
        schedule.put(200,650.0);
        schedule.put(300,350.0);
        schedule.put(400,200.0);
        schedule.put(500,50.0);
        int maxIterations = 500;
        SimulatedAnnealing annealing = new SimulatedAnnealing();
        State initialState = problem.getInitialState();
        State finalState = annealing.solution(problem,schedule,maxIterations);
        System.out.println("Initial state: "+ initialState.toString());
        System.out.println("Final state: "+ finalState.toString());
        System.out.println("Final state: "+ problem.calculateEnergy(finalState));






        //SpringApplication.run(SapBackEndApplication.class, args);
    }

}
