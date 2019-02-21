package com.example.maanas.listacomprasqlite;

public class Compra {
    private String producto, lugar;
    private int cantidad;
    private long id;

    public long getId() {
        return id;
    }

    public String getProducto() {
        return producto;
    }

    public String getLugar() {
        return lugar;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Compra(String producto, int cantidad, String lugar,long id) {
        this.producto = producto;
        this.lugar = lugar;
        this.cantidad = cantidad;
        this.id=id;
    }

    @Override
    public String toString() {
        return cantidad+"\t\t"+producto+"\t "+"("+lugar+")";
    }
}
