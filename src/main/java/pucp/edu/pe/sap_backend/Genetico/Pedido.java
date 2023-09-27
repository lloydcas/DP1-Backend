package pucp.edu.pe.sap_backend.Genetico;

import jakarta.persistence.*;
import pucp.edu.pe.sap_backend.Ruta.Cell;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Pedido implements Comparable{
    private int x;
    private int y;
    private int idPedido;
    private int id;
    private int amount;
    private int assigned;
    private String estado;
    private Cell cell;
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Pedido() {
    }
    public Pedido(int idPedido, int id, int x, int y, int cantidad){
        this.x=x;
        this.y=y;
        this.idPedido=idPedido;
        this.id=id;
        this.amount=cantidad;
        this.assigned=0;
        this.cell = new Cell(x, y);
    }

    public Pedido(Pedido pedido){
        this.x=pedido.getX();
        this.y=pedido.getY();
        this.idPedido=pedido.getIdPedido();
        this.id=pedido.getId();
        this.amount=pedido.getAmount();
        this.assigned=0;

    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }


    public int getAssigned() {
        return assigned;
    }

    public void setAssigned(int assigned) {
        this.assigned = assigned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public double distance(double xOther,double yOther){
        return Math.sqrt(((this.x-xOther)*(this.x-xOther)))+ Math.sqrt(((this.y-yOther)*(this.y-yOther)));
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public Cell getCell() {
        return cell;
    }
}