package com.example.cashflow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NOMBRE = "cashflow.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_GASTO = "gasto";
    private static final String COLUMN_ID = "id_gasto";
    private static final String COLUMN_NOMBRE = "nombre_gasto";
    private static final String COLUMN_MONTO = "monto_gasto";
    private static final String COLUMN_FECHA = "fecha_gasto";
    private static final String COLUMN_CATEGORIA = "categoria_gasto";

    // Constructor
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_GASTO+"(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_MONTO + " INTEGER NOT NULL, " +
                COLUMN_FECHA + " DATE NOT NULL," +
                COLUMN_CATEGORIA + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE "+ TABLE_GASTO);
    }
}

