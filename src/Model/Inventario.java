package Model;

import DB.InventarioDAO;

import java.util.ArrayList;
import java.util.Date;

public class Inventario implements InventarioDAO {

    private int id;
    private int quantity;
    private Date expirationDate;
    private int productoID;
    private Productos producto;

    public Inventario() {
    }

    public Inventario(int id, int quantity, Date expirationDate, int productoID) {
        this.id = id;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.productoID = productoID;
    }

    public ArrayList<Inventario> showInventario(){
        Inventario inventario = new Inventario();
        return inventario.getInventario();
    }

    public void printInventario(){
        ArrayList<Inventario> inventarios = showInventario();
        System.out.println("::INVENTARIO::");
        //mediante utilizacion de lambdas, de 3 lineas de codigo a 1, mismo resultado
        inventarios.forEach(i -> System.out.println(i.getId()+" "+i.getQuantity()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getProductoID() {
        return productoID;
    }

    public void setProductoID(int productoID) {
        this.productoID = productoID;
    }
}
