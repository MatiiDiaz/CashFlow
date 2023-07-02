package com.example.cashflow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.cashflow.entidades.Gasto;

import java.util.ArrayList;
import java.util.Date;

public class DBGasto extends DBHelper {

    Context context;
    public DBGasto(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public long insertarGasto(String nombre, String monto, String fecha, String categoria) {
        long id = 0;
        try {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre_gasto", nombre);
            values.put("monto_gasto", monto);
            values.put("fecha_gasto", fecha);
            values.put("categoria_gasto", categoria);

            id = database.insert(TABLE_GASTO, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }
    public ArrayList<Gasto> mostrarGastos(){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ArrayList<Gasto> listaGastos = new ArrayList<>();
        Gasto gasto = null;
        Cursor cursorGasto = null;

        cursorGasto = database.rawQuery("SELECT * FROM "+TABLE_GASTO, null);

        if (cursorGasto.moveToFirst()){
            do {
                gasto = new Gasto();
                gasto.setId_gasto(cursorGasto.getInt(0));
                gasto.setNombre_gasto(cursorGasto.getString(1));
                gasto.setMonto_gasto(cursorGasto.getString(2));
                gasto.setFecha_gasto(cursorGasto.getString(3));
                gasto.setCategoria_gasto(cursorGasto.getString(4));
                listaGastos.add(gasto);
            } while (cursorGasto.moveToNext());
        }
        cursorGasto.close();
        return listaGastos;
    }

    public Gasto verGastos(String id){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Gasto gasto = null;
        Cursor cursorGasto;

        cursorGasto = database.rawQuery("SELECT * FROM "+TABLE_GASTO+" WHERE id_gasto = "+id+" LIMIT 1", null);

        if (cursorGasto.moveToFirst()){
            gasto = new Gasto();
            gasto.setId_gasto(cursorGasto.getInt(0));
            gasto.setNombre_gasto(cursorGasto.getString(1));
            gasto.setMonto_gasto(cursorGasto.getString(2));
            gasto.setFecha_gasto(cursorGasto.getString(3));
            gasto.setCategoria_gasto(cursorGasto.getString(4));
        }
        cursorGasto.close();
        return gasto;
    }

    public boolean editarGasto(String id, String nombre, String monto, String fecha, String categoria) {
        boolean correcto = false;
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            database.execSQL("UPDATE "+TABLE_GASTO+" SET nombre_gasto = '"+nombre+"', monto_gasto = '"+monto+"', fecha_gasto = '"+fecha+"', categoria_gasto = '"+categoria+"' WHERE id_gasto ='"+id+"' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            database.close();
        }
        return correcto;
    }

    public boolean eliminarGasto(String id) {
        boolean correcto = false;
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            database.execSQL("DELETE FROM "+TABLE_GASTO+" WHERE id_gasto = '"+id+"'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            database.close();
        }
        return correcto;
    }
}
