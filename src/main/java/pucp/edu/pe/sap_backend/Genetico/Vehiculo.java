package pucp.edu.pe.sap_backend.Genetico;

import pucp.edu.pe.sap_backend.Ruta.BFS;
import pucp.edu.pe.sap_backend.Ruta.Cell;
import lombok.Getter;
import lombok.Setter;
import pucp.edu.pe.sap_backend.SimulatedAnnealing.Almacen;
import pucp.edu.pe.sap_backend.SimulatedAnnealing.AnotherApproach.S.A.Time;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class Vehiculo {
    private int id;

    private int numberOrders;

    private int stock;

    private int physicStock;

    private int capacity;

    private int type;

    private int lastMovement;

    private int x;

    private int y;

    private String placa;

    private String conductor;

    private String velocidad;

    private String kilometraje;

    private String estado;

    // @Transient ES PARA QUE NO SE GUARDE EN LA BD - FALTA CONVERSAR ESTO
    private LinkedList<Pedido> order;

    private LinkedList<Pedido> totalOrders;

    private LinkedList<Cell> route;

    private LinkedList<Cell> firstRoute;

    private LinkedList<Cell> movement;

    private LinkedList<Pedido> deliveredOrder;

    private int primeraRuta;
    //CantEstatico es la cantidad de movimientos para que se averie el vehiculo

    private int cantAveria;
    //movEstatico es la cantidad de movimientos que se mantiene en un mismo punto

    private int movEstatico;

    private int movReaundar;
    //Turnos que permanece averiado
    private int turnoAveriado;

    private int xInicial;

    private int yInicial;

    private Almacen nearestWarehouse;
    private List<Almacen> almacenes;

    public Vehiculo(int id, int capacity, int type, int x, int y) {
        this.id = id;
        this.capacity = capacity;
        this.stock = capacity;
        this.physicStock = capacity;
        this.type = type;
        this.x = x;
        this.y = y;
        this.xInicial = x;
        this.yInicial = y;
        this.numberOrders = 0;
        this.lastMovement = 0;
        this.order = new LinkedList<>();
        this.totalOrders = new LinkedList<>();
        this.route = new LinkedList<>();
        this.movement = new LinkedList<>();
        this.deliveredOrder = new LinkedList<>();
        this.firstRoute = new LinkedList<>();
        this.primeraRuta = 0;
        this.movEstatico = -1;
        this.turnoAveriado = 0;
        this.estado = "Disponible";
    }

    public Vehiculo(int id, int capacity, int type, int x, int y,List<Almacen>almacenes) {
        this.id = id;
        this.capacity = capacity;
        this.stock = capacity;
        this.physicStock = capacity;
        this.type = type;
        this.x = x;
        this.y = y;
        this.xInicial = x;
        this.yInicial = y;
        this.numberOrders = 0;
        this.lastMovement = 0;
        this.order = new LinkedList<>();
        this.totalOrders = new LinkedList<>();
        this.route = new LinkedList<>();
        this.movement = new LinkedList<>();
        this.deliveredOrder = new LinkedList<>();
        this.firstRoute = new LinkedList<>();
        this.primeraRuta = 0;
        this.movEstatico = -1;
        this.turnoAveriado = 0;
        this.estado = "Disponible";
        this.nearestWarehouse =  findNearestWarehouse(almacenes);
    }


    public Vehiculo(LinkedList<Pedido> order, int numberOrders, int id) {
        this.order = order;
        this.numberOrders = numberOrders;
        this.id = id;
        this.totalOrders = new LinkedList<>();
        this.deliveredOrder = new LinkedList<>();
        this.route = new LinkedList<>();
        this.movement = new LinkedList<>();
        this.firstRoute = new LinkedList<>();
    }

    public Vehiculo(Vehiculo other, List<Almacen> almacenes) {
        this.id = other.id;
        this.numberOrders = other.numberOrders;
        this.stock = other.stock;
        this.physicStock = other.physicStock;
        this.capacity = other.capacity;
        this.type = other.type;
        this.lastMovement = other.lastMovement;
        this.x = other.x;
        this.y = other.y;
        this.placa = other.placa;
        this.conductor = other.conductor;
        this.velocidad = other.velocidad;
        this.kilometraje = other.kilometraje;
        this.estado = other.estado;

        // Clone the linked lists to create deep copies
        this.order = new LinkedList<>(other.order);
        this.totalOrders = new LinkedList<>(other.totalOrders);
        this.route = new LinkedList<>(other.route);
        this.firstRoute = new LinkedList<>(other.firstRoute);
        this.movement = new LinkedList<>(other.movement);
        this.deliveredOrder = new LinkedList<>(other.deliveredOrder);

        this.primeraRuta = other.primeraRuta;
        this.cantAveria = other.cantAveria;
        this.movEstatico = other.movEstatico;
        this.movReaundar = other.movReaundar;
        this.turnoAveriado = other.turnoAveriado;
        this.xInicial = other.xInicial;
        this.yInicial = other.yInicial;
        this.almacenes = almacenes; // Add the list of static warehouses
    }

    public Vehiculo(int id, LinkedList<Pedido> pedidos){
        this.id = id;
        this.order = pedidos;
    }


    public LinkedList<Pedido> getOrder() {
        return order;
    }

    public void setOrder(LinkedList<Pedido> order) {
        this.order = order;
    }
    public LinkedList<Pedido> getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(LinkedList<Pedido> totalOrders) {
        this.totalOrders = totalOrders;
    }

    public LinkedList<Cell> getRoute() {
        return route;
    }

    public void setRoute(LinkedList<Cell> route) {
        if(route == null) {
            this.route.clear();
            return;
        }
        this.route = route;
    }

    public LinkedList<Pedido> getDeliveredOrder() {return deliveredOrder;}

    public void setDeliveredOrder(LinkedList<Pedido> deliveredOrder) {this.deliveredOrder = deliveredOrder;}

    public LinkedList<Cell> getFirstRoute() {return firstRoute;}

    public void setFirstRoute(LinkedList<Cell> firstRoute) {this.firstRoute = firstRoute;}

    public LinkedList<Cell> getMovement() {
        return movement;
    }

    public void setMovement(LinkedList<Cell> movement) {
        this.movement = movement;
    }

    public int getPrimeraRuta() {return primeraRuta;}

    public void setPrimeraRuta(int primeraRuta) {this.primeraRuta = primeraRuta;}

    public int getCantAveria() {return cantAveria;}

    public void setCantAveria(int cantAveria) {this.cantAveria = cantAveria;}

    public void newOrder (Pedido[] route, int amountNew,int idNewOrder){
        this.order.clear();
        //this.order = new Order[numberOrders];
        for(int i = 1; i < route.length; i++){
            this.order.add(new Pedido(route[i].getIdPedido(),route[i].getId(),route[i].getX(),route[i].getY(),route[i].getAmount()));
            if(this.order.get(i-1).getIdPedido()== idNewOrder ) this.order.get(i-1).setAmount(amountNew);
            idNewOrder = -1;
            this.order.get(i-1).setAssigned(1);
        }
    }
    public double distance(double xOther,double yOther){
        return Math.sqrt(((this.x-xOther)*(this.x-xOther)))+ Math.sqrt(((this.y-yOther)*(this.y-yOther)));
    }

    public void generateRoute(BFS blocks, int almX, int almY) {
        int[] auxstart = { this.x, this.y };
        int almacenX = almX;
        int almacenY = almY;
        int[] auxEnd = { almacenX, almacenY };
        this.route.clear();
        LinkedList<Cell> auxPath;
        int speed = this.type * 30;
        Cell auxCell = null;

        for (Pedido order : this.order) {
            auxEnd[0] = order.getX();
            auxEnd[1] = order.getY();
            auxPath = blocks.shortestPath(auxstart, auxEnd, this.type, auxCell);

            // Add cells to the route, but stop at any warehouse
            for (Cell cell : auxPath) {
                this.route.add(cell);
                if (cell.isWarehouse()) {
                    // Stop at the warehouse
                    break;
                }
            }

            auxstart[0] = order.getX();
            auxstart[1] = order.getY();
            if (!auxPath.isEmpty() && auxPath.getLast().blocked) {
                auxPath.getLast().prev.prev = null;
                auxCell = auxPath.getLast().prev;
            } else {
                auxCell = null;
            }
        }

        auxEnd[0] = almacenX;
        auxEnd[1] = almacenY;
        auxPath = blocks.shortestPath(auxstart, auxEnd, this.type, null);

        // Add cells to the route, but stop at any warehouse
        for (Cell cell : auxPath) {
            this.route.add(cell);
            if (cell.isWarehouse()) {
                // Stop at the warehouse
                break;
            }
        }
    }
    public void addRoute(LinkedList<Cell> route) {
        this.route.clear();
        for (Cell c:route){
            c.prev=null;
        }
        this.route = route;
    }

    public int remainingCapacity(){
        return capacity - order.stream().mapToInt(order -> order.getAmount()).sum();
    }

    public void imprimirUltimaRuta(){
        for (Cell cell : route) {
            //System.out.print("(X=" + cell.getX() + " , Y=" + cell.getY()+ " ) ;");
            System.out.print("(" +cell.getX() + ", " + cell.getY()+ ") ; ");
        }
        System.out.println();

    }

    public Almacen getNearestWarehouse() {
        return nearestWarehouse;
    }

    public void setNearestWarehouse(Almacen nearestWarehouse) {
        this.nearestWarehouse = nearestWarehouse;
    }

    private Almacen findNearestWarehouse(List<Almacen> warehouses) {
        double minDistance = Double.MAX_VALUE;
        Almacen nearestWarehouse = null;

        int vehicleX = this.x;
        int vehicleY = this.y;

        for (Almacen warehouse : warehouses) {
            int warehouseX = warehouse.getX();
            int warehouseY = warehouse.getY();

            // Calculate the distance between the vehicle and the warehouse
            double distance = Math.sqrt(Math.pow(warehouseX - vehicleX, 2) + Math.pow(warehouseY - vehicleY, 2));

            if (distance < minDistance) {
                minDistance = distance;
                nearestWarehouse = warehouse;
            }
        }

        return nearestWarehouse;
    }

}
