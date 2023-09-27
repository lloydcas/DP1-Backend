package pucp.edu.pe.sap_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pucp.edu.pe.sap_backend.Genetico.Genetico;
import pucp.edu.pe.sap_backend.Genetico.Pedido;
import pucp.edu.pe.sap_backend.Genetico.Vehiculo;
import pucp.edu.pe.sap_backend.Ruta.BFS;
import pucp.edu.pe.sap_backend.Ruta.BlockMap;
import pucp.edu.pe.sap_backend.Ruta.Blocknode;
import pucp.edu.pe.sap_backend.Ruta.Cell;
import pucp.edu.pe.sap_backend.SimulatedAnnealing.*;
import pucp.edu.pe.sap_backend.SimulatedAnnealing.AnotherApproach.S.A.Time;

import java.util.*;

@SpringBootApplication
public class SapBackEndApplication {

    public static void main(String[] args) {

        //ESTANDAR BASICO
        //Genetico genetico = new Genetico(1, 12, 8);

        //Posiciones aleatorias:
        Random r = new Random();
        int low = 0; int high = 70;

        List<Almacen>almacenes = new ArrayList<>();
        almacenes.add(new Almacen(12,8));
        almacenes.add(new Almacen(42,42));
        almacenes.add(new Almacen(63,3));

        //PRIMER CUADRANTE
        Pedido pedido1 = new Pedido(123, 0, 50, 30, 2);
        Pedido pedido2 = new Pedido(124, 1, 1, 39, 22);
        Pedido pedido3 = new Pedido(125, 2, 21, 33, 2);
        Pedido pedido4 = new Pedido(126, 3, 7, 30, 2);
        Pedido pedido5 = new Pedido(127, 4, 70, 27, 1);
        Pedido pedido6 = new Pedido(128, 5, 54, 14, 9);
        LinkedList<Pedido> listaPedidos = new LinkedList<>();
        listaPedidos.add(pedido1);
        listaPedidos.add(pedido2);
        listaPedidos.add(pedido3);
        listaPedidos.add(pedido4);
        listaPedidos.add(pedido5);
        listaPedidos.add(pedido6);

        Pedido pedido7 = new Pedido(123, 6, 29, 35, 2);
        Pedido pedido8 = new Pedido(124, 7, 5, 32, 2);
        Pedido pedido9 = new Pedido(125, 8, 24, 7, 3);
        Pedido pedido10 = new Pedido(126, 9, 69, 50, 8);
        Pedido pedido11 = new Pedido(127, 10, 10, 50, 5);
        Pedido pedido12 = new Pedido(128, 11, 1, 31, 16);
        listaPedidos.add(pedido7);
        listaPedidos.add(pedido8);
        listaPedidos.add(pedido9);
        listaPedidos.add(pedido10);
        listaPedidos.add(pedido11);
        listaPedidos.add(pedido12);
        //genetico.setOrders(listaPedidos);

        Vehiculo vehiculo = new Vehiculo(1, 5, 0,
                r.nextInt(high-low) + low, r.nextInt(high-low) + low,almacenes);//(0,0) es donde parte)
        Vehiculo vehiculo1 = new Vehiculo(2, 5, 0,
                r.nextInt(high-low) + low,r.nextInt(high-low) + low,almacenes);//(0,0) es donde parte)
        Vehiculo vehiculo2 = new Vehiculo(3, 5, 0,
                r.nextInt(high-low) + low, r.nextInt(high-low) + low,almacenes);//(0,0) es donde parte)
        Vehiculo vehiculo3 = new Vehiculo(4, 5, 0,
                r.nextInt(high-low) + low, r.nextInt(high-low) + low,almacenes);//(0,0) es donde parte)
        Vehiculo vehiculo4 = new Vehiculo(5, 5, 0,
                r.nextInt(high-low) + low, r.nextInt(high-low) + low,almacenes);//(0,0) es donde parte)
        Vehiculo vehiculo5 = new Vehiculo(6, 5, 0,
                r.nextInt(high-low) + low, r.nextInt(high-low) + low,almacenes);//(0,0) es donde parte)
        Vehiculo vehiculo6 = new Vehiculo(6, 5, 0,
                r.nextInt(high-low) + low, r.nextInt(high-low) + low,almacenes);//(0,0) es donde parte)
        Vehiculo vehiculo7 = new Vehiculo(6, 5, 0,
                r.nextInt(high-low) + low, r.nextInt(high-low) + low,almacenes);//(0,0) es donde parte)
        Vehiculo vehiculo8 = new Vehiculo(6, 5, 0,
                r.nextInt(high-low) + low, r.nextInt(high-low) + low,almacenes);//(0,0) es donde parte)
        Vehiculo vehiculo9 = new Vehiculo(6, 5, 0,
                r.nextInt(high-low) + low, r.nextInt(high-low) + low,almacenes);//(0,0) es donde parte)
        ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();
        listaVehiculos.add(vehiculo);
        listaVehiculos.add(vehiculo1);
        listaVehiculos.add(vehiculo2);
        listaVehiculos.add(vehiculo3);
        listaVehiculos.add(vehiculo4);
        listaVehiculos.add(vehiculo5);
        listaVehiculos.add(vehiculo6);
        listaVehiculos.add(vehiculo7);
        listaVehiculos.add(vehiculo8);
        listaVehiculos.add(vehiculo9);
        //genetico.setCars(listaVehiculos);
        //genetico.setOrders(listaPedidos);

        //Sin bloqueos
        //BFS blocks = new BFS(new BlockMap(100,100));
        //genetico.setBlocks(blocks);


        //CON BLOQUEOS
        BlockMap map = new BlockMap(100, 100);

        //bloquear  un camino
        map.getMap()[3][0] = new Blocknode();
        //map.getMap()[3][0].setBloqueado(false);
        map.getMap()[4][0] = new Blocknode();
        //map.getMap()[4][0].setBloqueado(false);
        map.getMap()[5][0] = new Blocknode();
        //map.getMap()[5][0].setBloqueado(false);
        //genetico.setBlocks(new BFS(map));

        //genetico.executeAlgorithm();

        Problem problem = new Problem(listaVehiculos, new BFS(map),almacenes, listaPedidos);
        State initialState = problem.getInitialState();
        System.out.println("\nSimulated annealing: \n ");

        ////////////////////////////// Annealing////////////////////////

        /*pedido1 = new Pedido(123,0,5,5,5);
        pedido2 = new Pedido(124,1,10,12,10);
        pedido3 = new Pedido(125,2,15,19,5);
        listaPedidos = new LinkedList<>();
        listaPedidos.add(pedido1);
        listaPedidos.add(pedido2);
        listaPedidos.add(pedido3);*/

        Map<Integer, Double> schedule = new HashMap<>();
        schedule.put(0, 1000.0);
        schedule.put(20, 900.0);
        schedule.put(40, 800.0);
        schedule.put(80, 700.0);
        schedule.put(100, 600.0);
        schedule.put(200, 400.0);
        schedule.put(300, 250.00);
        schedule.put(400, 100.00);
        schedule.put(500, 50.0);
        schedule.put(600, 30.0);
        schedule.put(700, 20.0);
        schedule.put(800, 14.0);
        schedule.put(900, 12.0);
        schedule.put(1000, 10.0);
        int maxIterations = 1000;

        /*Map<Integer,Double> schedule = new HashMap<>();
        double coolingRate = 0.995;
        double initialTemp = 100.0;
        int maxIterations = 100;
        Map<Integer,Double> coolingSchedule = SimulatedAnnealing.generateCoolingSchedule(initialTemp,coolingRate,
                                                                                         maxIterations);*/


        //System.out.println("Initial state: " + initialState.toString());
        SimulatedAnnealing annealing = new SimulatedAnnealing();
        State finalState = annealing.solution(problem, schedule, maxIterations);
        //State finalState = annealing.solution(problem,coolingSchedule,maxIterations);
        finalState.twoOptLocalSearch(finalState);

        System.out.println("Final state: " + finalState);


        //System.out.println("Final state: " + problem.calculateEnergy(finalState));

    }



        //SpringApplication.run(SapBackEndApplication.class, args);


}
