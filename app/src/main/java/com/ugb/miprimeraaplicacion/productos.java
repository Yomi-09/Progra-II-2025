package com.ugb.miprimeraaplicacion;

public class productos {
    private String idproducto;
    private String nombre;
    private double precio;
    private double costo;
    private double ganancia;
    private String urlFoto;

    public productos(String idproducto, String nombre, double precio, double costo, double ganancia, String urlFoto) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.costo = costo;
        this.ganancia = ganancia;
        this.urlFoto = urlFoto;
    }



    // Getters
    public String getIdproducto() {
        return idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public double getCosto() {
        return costo;
    }

    public double getGanancia() {
        return ganancia;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

       @Override
    public String toString() {
        return nombre + " (ID: " + idproducto + ")";
    }
}
