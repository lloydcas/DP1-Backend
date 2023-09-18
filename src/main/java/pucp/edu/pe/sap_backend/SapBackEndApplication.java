package pucp.edu.pe.sap_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pucp.edu.pe.sap_backend.Genetico.Genetico;
import pucp.edu.pe.sap_backend.Genetico.Pedido;
import pucp.edu.pe.sap_backend.Genetico.Vehiculo;
import pucp.edu.pe.sap_backend.Ruta.BFS;
import pucp.edu.pe.sap_backend.Ruta.BlockMap;
import pucp.edu.pe.sap_backend.Ruta.Blocknode;
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
        Pedido pedido1 = new Pedido(123, 0, 20, 12, 5);
        Pedido pedido2 = new Pedido(124, 1, 10, 12, 10);
        Pedido pedido3 = new Pedido(125, 2, 15, 19, 5);

        LinkedList<Pedido> listaPedidos = new LinkedList<>();
        listaPedidos.add(pedido1);
        listaPedidos.add(pedido2);
        listaPedidos.add(pedido3);

        Pedido pedido4 = new Pedido(123,4,26,26,5);
        Pedido pedido5 = new Pedido(124,5,30,30,10);
        Pedido pedido6 = new Pedido(125,6,30,40,5);

        listaPedidos.add(pedido4);
        listaPedidos.add(pedido5);
        listaPedidos.add(pedido6);

        genetico.setOrders(listaPedidos);
        Vehiculo vehiculo = new Vehiculo(1,100,0,25,10);//(0,0) es donde parte)
        Vehiculo vehiculo1 =   new Vehiculo(2,150,0,20,10);//(0,0) es donde parte)
        ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();
        listaVehiculos.add(vehiculo);
        listaVehiculos.add(vehiculo1);

        genetico.setCars(listaVehiculos);
        genetico.setOrders(listaPedidos);

        //Sin bloqueos
        BFS blocks = new BFS(new BlockMap(100,100));
        genetico.setBlocks(blocks);

        Problem problem = new Problem(listaVehiculos,blocks,10,10,listaPedidos);
        State initialState = problem.getInitialState();

        /* //CON BLOQUEOS
        BlockMap map = new BlockMap(100,100);

        //bloquear  un camino
        map.getMap()[3][0] = new Blocknode();
        //map.getMap()[3][0].setBloqueado(false);
        map.getMap()[4][0] = new Blocknode();
        //map.getMap()[4][0].setBloqueado(false);
        map.getMap()[5][0] = new Blocknode();
        //map.getMap()[5][0].setBloqueado(false);
        genetico.setBlocks(new BFS(map));*/

        genetico.executeAlgorithm();
        System.out.println("\nSimulated annealing: \n ");

        ////////////////////////////// Annealing////////////////////////

        /*pedido1 = new Pedido(123,0,5,5,5);
        pedido2 = new Pedido(124,1,10,12,10);
        pedido3 = new Pedido(125,2,15,19,5);
        listaPedidos = new LinkedList<>();
        listaPedidos.add(pedido1);
        listaPedidos.add(pedido2);
        listaPedidos.add(pedido3);*/

        Map<Integer,Double> schedule = new HashMap<>();
        schedule.put(0, 5000.0);
        schedule.put(100, 4000.0);
        schedule.put(200, 3000.0);
        schedule.put(300, 2000.0);
        schedule.put(400, 1500.0);
        schedule.put(500, 1000.0);
        schedule.put(600, 800.0);
        schedule.put(700, 600.0);
        schedule.put(800, 400.0);
        schedule.put(900, 200.0);
        schedule.put(1000, 100.0);
        int maxIterations = 1000;
        System.out.println("Initial state: "+ initialState.toString());
        SimulatedAnnealing annealing = new SimulatedAnnealing();
        State finalState = annealing.solution(problem,schedule,maxIterations);
        finalState.twoOptLocalSearch(finalState);
        System.out.println("Final state: "+ finalState);
        System.out.println("Final state: "+ problem.calculateEnergy(finalState));






        //SpringApplication.run(SapBackEndApplication.class, args);
    }

}
