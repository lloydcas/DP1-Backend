package pucp.edu.pe.sap_backend.Genetico;

import pucp.edu.pe.sap_backend.Ruta.BFS;
import pucp.edu.pe.sap_backend.Ruta.Cell;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
public class Genetico{

    private LinkedList<Pedido> orders;
    private LinkedList<Pedido> totalOrders;
    private BFS blocks;
    private ArrayList<Vehiculo> cars;

    private LocalDateTime currentTime;
    private LocalDateTime stopTime;
    private int duracion;
    private int almacenX;
    private int almacenY;
    private int autos;
    //Tipo de ejecucion: 1, dia a dia. 6, simulacion semanal, 12, simulacion colapso
    private int limit;

    //private PedidoRepository pedidoService;

    private int turnoAnt;

    public Genetico( Integer autos, Integer almacenX, Integer almacenY) {
        //Por ahora nada mas
        this.cars = new ArrayList<>();

        for(int i = 1; i<autos+1;i++){
            this.cars.add(new Vehiculo(i, 25, 1, almacenX, almacenY));
        }
        this.autos = autos;
        this.totalOrders = new LinkedList<>();
        this.orders = new LinkedList<>();
        this.limit = 1;
        this.turnoAnt = -1;
        this.almacenX = almacenX;
        this.almacenY = almacenY;
        this.duracion= 0;

    }

    public Genetico(LocalDateTime currentTime, Integer autos, Integer motos, int limit, Integer almacenX, Integer almacenY) {
        this.cars = new ArrayList<>();


        for(int i = 1; i<autos+1;i++){
            this.cars.add(new Vehiculo(i, 25, 1, almacenX, almacenY));
        }
        this.autos = autos;
        this.limit = limit;
        this.currentTime  = currentTime;
        this.totalOrders = new LinkedList<>();
        this.orders = new LinkedList<>();
        this.turnoAnt = -1;
        this.almacenX = almacenX;
        this.almacenY = almacenY;
        this.duracion =0;
    }

    public LinkedList<Pedido> getOrders() {
        return orders;
    }

    public void setOrders(LinkedList<Pedido> orders) {
        this.orders = orders;
    }

    public LinkedList<Pedido> getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(LinkedList<Pedido> totalOrders) {
        this.totalOrders = totalOrders;
    }

    public BFS getBlocks() {
        return blocks;
    }

    public void setBlocks(BFS blocks) {
        this.blocks = blocks;
    }

    public ArrayList<Vehiculo> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Vehiculo> cars) {
        this.cars = cars;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }

    public int getDuracion() {return duracion;}

    public void setDuracion(int duracion) {this.duracion = duracion;}

    public void executeAlgorithm() {
        //Asginar pedidos a los vehiculos
        LinkedList<Pedido> pedidoCuadrante1 = new LinkedList<>();
        LinkedList<Pedido> pedidoCuadrante2 = new LinkedList<>();
        LinkedList<Pedido> pedidoCuadrante3 = new LinkedList<>();
        LinkedList<Pedido> pedidoCuadrante4 = new LinkedList<>();
        ArrayList<Vehiculo> vehiculosCuadrante1 = new ArrayList<>();
        ArrayList<Vehiculo> vehiculosCuadrante2 = new ArrayList<>();
        ArrayList<Vehiculo> vehiculosCuadrante3 = new ArrayList<>();
        ArrayList<Vehiculo> vehiculosCuadrante4 = new ArrayList<>();

        LinkedList<Pedido> listaPedidos = orders;
        ArrayList<Vehiculo> listaVehiculos = cars;

        for(Pedido ped : orders){
            if(ped.getX() >= 0 && ped.getX()<=35 && ped.getY()>=0 && ped.getY()<=25){ // primer cuadrante
                pedidoCuadrante1.add(ped);
            }
            if(ped.getX() >= 0 && ped.getX()<=  35 && ped.getY()>=26 && ped.getY()<=50){ // segundo cuadrantes
                pedidoCuadrante2.add(ped);
            }
            if(ped.getX() >= 36 && ped.getX()<= 70 && ped.getY()>=26 && ped.getY()<=50){ // tercer cuadrantes
                pedidoCuadrante3.add(ped);
            }
            if(ped.getX() >= 36 && ped.getX()<= 70 && ped.getY()>=0 && ped.getY()<=25){ // cuarto cuadrante
                pedidoCuadrante4.add(ped);
            }
        }
        for(Vehiculo vehiculo : cars){
            if(vehiculo.getX() >= 0 && vehiculo.getX()<=35 && vehiculo.getY()>=0 && vehiculo.getY()<=25){ // primer cuadrante
                vehiculosCuadrante1.add(vehiculo);
            }
            if(vehiculo.getX() >= 0 && vehiculo.getX()<=  35 && vehiculo.getY()>=26 && vehiculo.getY()<=50){ // segundo cuadrantes
                vehiculosCuadrante2.add(vehiculo);
            }
            if(vehiculo.getX() >= 36 && vehiculo.getX()<= 70 && vehiculo.getY()>=26 && vehiculo.getY()<=50){ // tercer cuadrantes
                vehiculosCuadrante3.add(vehiculo);
            }
            if(vehiculo.getX() >= 36 && vehiculo.getX()<= 70 && vehiculo.getY()>=0 && vehiculo.getY()<=25){ // cuarto cuadrante
                vehiculosCuadrante4.add(vehiculo);
            }
        }

        AsisgnarPedidosAVehiculos(pedidoCuadrante1,vehiculosCuadrante1);
        AsisgnarPedidosAVehiculos(pedidoCuadrante2,vehiculosCuadrante2);
        AsisgnarPedidosAVehiculos(pedidoCuadrante3,vehiculosCuadrante3);
        AsisgnarPedidosAVehiculos(pedidoCuadrante4,vehiculosCuadrante4);
        //AHORA FUNCION PARA ORDENAR PEDIDOS POR DISTANCIA / FECHA

        orders = new LinkedList<>();
        orders.addAll(pedidoCuadrante1);
        orders.addAll(pedidoCuadrante2);
        orders.addAll(pedidoCuadrante3);
        orders.addAll(pedidoCuadrante4);

        for(Pedido ped: orders){
            System.out.print(ped.getEstado() + " ");
        }
        System.out.println();
        //ELIMINAR ELEMENTOS QUE FUERON ASIGNADOS


//        listaPedidos.removeAll(pedidoCuadrante1);
//        listaPedidos.removeAll(pedidoCuadrante2);
//        listaPedidos.removeAll(pedidoCuadrante3);
//        listaPedidos.removeAll(pedidoCuadrante4);

        //Verificar vehiculos en todos los sectores que esten libres y o sin pedidos o que puedan tener tu posicion


        //ordenar pedidos por fecha
        // ordenarlos por distancia
        //ver bloqueos
        // ver lo de los pedidos faltantes con los vehiculos faltantes
        this.cars = new ArrayList<>();
        this.cars.addAll(vehiculosCuadrante1);
        this.cars.addAll(vehiculosCuadrante2);
        this.cars.addAll(vehiculosCuadrante3);
        this.cars.addAll(vehiculosCuadrante4);


        if (!orders.isEmpty()) genetic_algorithm(orders, this.cars, blocks);
    }



    public void updatePosition(ArrayList<Vehiculo> cars, BFS blocks) {
        int auxX,auxY,i,auxTipo,almacenX = this.almacenX,almacenY=this.almacenY;
        int auxDist;
        LinkedList<Cell> auxRoute;
        LinkedList<Cell> auxRecorrido;
        LinkedList<Pedido> auxOrder;
        Pedido auxDO;
        for(Vehiculo car: cars){
            auxTipo= car.getType()== 1? 2 : 1;
            auxRoute = car.getRoute();
            auxOrder = car.getOrder();
//            if(car.getPrimeraRuta()==-1 && (!car.getDeliveredOrder().isEmpty() || !auxOrder.isEmpty())) {
//                car.setPrimeraRuta(auxRoute.size());
//            }
            while(!auxOrder.isEmpty()){
                auxX=auxOrder.get(0).getX();
                auxY=auxOrder.get(0).getY();
                if(car.getX()==auxX && car.getY()==auxY){
                    car.getTotalOrders().add(new Pedido (auxOrder.get(0)));
                    car.setPhysicStock(car.getPhysicStock()-auxOrder.get(0).getAmount());
                    auxOrder.remove(0);
                }else{
                    break;
                }
            }
            i=0;
            while(i<5*car.getType()){
                if(auxRoute.isEmpty()) {
                    auxDist = car.getMovement().isEmpty()? 0:car.getMovement().getLast().dist;
                    car.getMovement().add(new Cell(car.getX(),car.getY(),auxDist,null));
                    i++;
                    continue;
                }
                //Car moving
                car.setX(auxRoute.getFirst().x);
                car.setY(auxRoute.getFirst().y);
                if(car.getMovEstatico() == -1) car.setMovEstatico(car.getMovement().size());
                auxRoute.remove();
                i++;
                car.getMovement().add(new Cell(car.getX(),car.getY(),car.getMovement().size()==0? 0: car.getMovement().getLast().dist,null));
                while(!auxOrder.isEmpty()){
                    auxX=auxOrder.get(0).getX();
                    auxY=auxOrder.get(0).getY();
                    if(car.getX()==auxX && car.getY()==auxY){
                        car.setPhysicStock(car.getPhysicStock()-auxOrder.get(0).getAmount());
                        auxDO = auxOrder.remove(0);
                        auxDO.setAssigned(0);
                        car.getDeliveredOrder().add(auxDO);
                        car.getMovement().getLast().dist +=1;
                    }else{
                        break;
                    }
                }
                if(car.getX()==almacenX && car.getY()==almacenY){
                    car.setStock(car.getCapacity());
                    car.setPhysicStock(car.getCapacity());
                }
            }
        }
    }
    private void genetic_algorithm(LinkedList<Pedido> listaDePedidos, ArrayList<Vehiculo> cars, BFS blocks) {
        int populationSize = 45;
        double mutationRate = 0.05;
        double crossoverRate = 0.8;
        int numberOfGenerations = 0;
        int stopAt = 0;
        Population pop;

        for (Vehiculo car : cars) {
            if (car.getStock() <= 0) continue;

            LinkedList<Pedido> pedidosAsignados = car.getOrder(); // Obtener pedidos asignados a este vehículo

            for (Pedido pedido : pedidosAsignados) {
                int numOrdersAux = pedidosAsignados.size() + 2;
                Path route = new Path(numOrdersAux, car, pedido, blocks, 12, 8);
                pop = new Population(populationSize, numOrdersAux, route, crossoverRate, mutationRate, blocks, 12, 8);
                pop.FitnessOrder();

                while (numberOfGenerations != stopAt) {
                    while (pop.Mate() == false) ;
                    for (int i = 0; i < pop.getPopulation().length; i++) {
                        pop.getNextGen()[i].setPath(pop.Mutation(pop.getNextGen()[i].getPath()), 12, 8);
                    }

                    pop.setPopulation(pop.getNextGen());
                    pop.setDone(0);
                    pop.FitnessOrder();
                    numberOfGenerations++;
                }

                int aux = pop.getPopulation().length - 1;
                if (pop.getPopulation()[aux].getCost() < 10000) {
                    if (pedido.getAmount() - car.getStock() > 0) {
                        int auxAmount = pedido.getAmount();
                        pedido.setAmount(car.getStock());
                        car.newOrder(pop.getPopulation()[aux].getPath(), car.getStock(), pedido.getIdPedido());
                        pedido.setAmount(auxAmount - car.getStock());
                        car.setStock(0);
                        car.generateRoute(blocks, 12, 8);
                    } else {
                        car.setStock(car.getStock() - pedido.getAmount());
                        car.newOrder(pop.getPopulation()[aux].getPath(), pedido.getAmount(), pedido.getIdPedido());
                        pedidosAsignados.remove(pedido);
                        car.generateRoute(blocks, 12, 8);
                        System.out.print("VEHICULO " + car.getId() + "  ");
                        car.imprimirUltimaRuta();
                        break;
                    }
                }
            }
        }


//        for (int k = 0; !listaDePedidos.isEmpty(); k++) {
//            if (k >= listaDePedidos.size()) break;
//
//            for (Vehiculo car : cars) {
//                if (car.getStock() <= 0) continue;
//
//                int numOrdersAux = car.getOrder().size() + 2;
//                Path route = new Path(numOrdersAux, car, listaDePedidos.get(k), blocks,12,8);
//                pop = new Population(populationSize, numOrdersAux, route, crossoverRate, mutationRate, blocks,12,8);
//                pop.FitnessOrder();
//
//                while (numberOfGenerations != stopAt) {
//                    while (pop.Mate() == false) ;
//                    for (int i = 0; i < pop.getPopulation().length; i++) {
//                        pop.getNextGen()[i].setPath(pop.Mutation(pop.getNextGen()[i].getPath()),12,8);
//                    }
//
//                    pop.setPopulation(pop.getNextGen());
//                    pop.setDone(0);
//                    pop.FitnessOrder();
//                    numberOfGenerations++;
//                }
//
//                int aux = pop.getPopulation().length - 1;
//                if (pop.getPopulation()[aux].getCost() < 10000) {
//                    if (listaDePedidos.get(k).getAmount() - car.getStock() > 0) {
//                        int auxAmount = listaDePedidos.get(k).getAmount();
//                        listaDePedidos.get(k).setAmount(car.getStock());
//                        car.newOrder(pop.getPopulation()[aux].getPath(), car.getStock(), listaDePedidos.get(k).getIdPedido());
//                        listaDePedidos.get(k).setAmount(auxAmount - car.getStock());
//                        car.setStock(0);
//                        car.generateRoute(blocks,12,8);
//                    } else {
//                        car.setStock(car.getStock() - listaDePedidos.get(k).getAmount());
//                        car.newOrder(pop.getPopulation()[aux].getPath(), listaDePedidos.get(k).getAmount(), listaDePedidos.get(k).getIdPedido());
//                        listaDePedidos.remove(k);
//                        car.generateRoute(blocks,12,8);
//                        k--;
//                        break;
//                    }
//                }
//            }
//        }




    }

    private int distancia(int startX,int startY,int endX,int endY ){
        return Math.abs(startX - endX) + Math.abs(startY - endY);
    }



    private void AsisgnarPedidosAVehiculos(LinkedList<Pedido> Pedidos, ArrayList<Vehiculo> Vehiculos) {

        Vehiculos.sort((v1, v2) -> v2.getCapacity() - v1.getCapacity());

        Pedidos = new LinkedList<>(Pedidos.stream().sorted((o1, o2) -> o2.getAmount() - o1.getAmount()).collect(Collectors.toList()));
        for (Vehiculo vehicle : Vehiculos) {
//            boolean canAssignMore = true;
//            while (canAssignMore && Pedidos.size() > 0) {
//                canAssignMore = false;
//                for (Pedido order : new ArrayList<>(Pedidos)) {
//                    if (vehicle.remainingCapacity() >= order.getAmount()) {
//                        order.setEstado("Asignado");
//                        vehicle.getOrder().add(order);
//                        Pedidos.remove(order);
//                        canAssignMore = true;
//                        break;
//                    }else{
//                        order.setEstado("No asignado");
//                    }
//                }
//            }
            Iterator<Pedido> iterator = Pedidos.iterator();
            while (iterator.hasNext()) {
                Pedido order = iterator.next();

                if (vehicle.remainingCapacity() >= order.getAmount()) {
                    order.setEstado("Asignado");
                    vehicle.getOrder().add(order);
                    iterator.remove(); // Remover el pedido de la lista de pedidos.
                } else {
                    order.setEstado("No asignado");
                }
            }
        }
    }
}

