package com.example.cashflow.entidades;

public class Gasto {
    private int id_gasto;
    private String nombre_gasto, monto_gasto, fecha_gasto;

    public Gasto(Integer id_gasto, String nombre_gasto, String monto_gasto, String fecha_gasto) {
        this.setId_gasto(id_gasto);
        this.setNombre_gasto(nombre_gasto);
        this.setMonto_gasto(monto_gasto);
        this.setFecha_gasto(fecha_gasto);
    }

    public Gasto() {

    }

    public Integer getId_gasto() {
        return id_gasto;
    }

    public void setId_gasto(Integer id_gasto) {
        this.id_gasto = id_gasto;
    }

    public String getNombre_gasto() {
        return nombre_gasto;
    }

    public void setNombre_gasto(String nombre_gasto) {
        this.nombre_gasto = nombre_gasto;
    }

    public String getMonto_gasto() {
        return monto_gasto;
    }

    public void setMonto_gasto(String monto_gasto) {
        this.monto_gasto = monto_gasto;
    }

    public String getFecha_gasto() {
        return fecha_gasto;
    }

    public void setFecha_gasto(String fecha_gasto) {
        this.fecha_gasto = fecha_gasto;
    }
}
