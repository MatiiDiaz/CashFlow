package com.example.cashflow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cashflow.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "presupuesto";
    private static final String COLUMN_MONTO = "monto";
    private static final String COLUMN_FECHA = "fecha";

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla en la base de datos
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_MONTO + " INTEGER, " +
                COLUMN_FECHA + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Realizar acciones en caso de actualizaciones de la base de datos
    }

    // Método para insertar los datos del presupuesto y la fecha
    public void insertData(int monto, String fecha) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MONTO, monto);
        values.put(COLUMN_FECHA, fecha);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Otros métodos para actualizar y consultar los datos, según tus necesidades
}

