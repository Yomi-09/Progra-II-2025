package com.ugb.miprimeraaplicacion;

public class productos {

    private String nombre;
    private String urlFoto;  // URL de la imagen del producto
    private double precio;
    private double costo;
    private double ganancia;

    // Constructor con todos los parámetros
    public productos(String nombre, String urlFoto, double precio, double costo) {
        this.nombre = nombre;
        this.urlFoto = urlFoto;
        this.precio = precio;
        this.costo = costo;
        this.ganancia = calcularGanancia();  // La ganancia se calcula automáticamente
    }

    // Método para calcular la ganancia (diferencia entre precio y costo)
    private double calcularGanancia() {
        return this.precio - this.costo;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
        this.ganancia = calcularGanancia();  // Se recalcula la ganancia al cambiar el precio
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
        this.ganancia = calcularGanancia();  // Se recalcula la ganancia al cambiar el costo
    }

    public double getGanancia() {
        return ganancia;
    }

    // Método toString para representar el producto de manera legible
    @Override
    public String toString() {
        return "Producto: " + nombre + "\n" +
                "Precio: " + precio + "\n" +
                "Costo: " + costo + "\n" +
                "Ganancia: " + ganancia + "\n" +
                "Foto: " + urlFoto;
    }
}
