package com.ugb.miprimeraaplicacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "yomi";
    private static final int DATABASE_VERSION = 2;

    // La tabla ahora tiene idproducto como AUTOINCREMENT
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE productos (idproducto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "precio REAL, " +
                    "costo REAL, " +
                    "ganancia REAL, " +
                    "urlFoto TEXT)";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Aquí creamos la tabla con idproducto como AUTOINCREMENT
        String sql = "CREATE TABLE IF NOT EXISTS productos (" +
                "idproducto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "precio REAL, " +
                "costo REAL, " +
                "ganancia REAL, " +
                "urlFoto TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS productos");
        onCreate(db);
    }

    public String administrar_productos(String accion, String[] datos) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            switch (accion) {
                case "nuevo":
                    // Ahora SQLite maneja el idproducto automáticamente, no lo pasamos
                    db.execSQL("INSERT INTO productos (nombre, urlFoto, precio, costo, ganancia) VALUES (?, ?, ?, ?, ?)", datos);
                    break;
                case "modificar":
                    db.execSQL("UPDATE productos SET nombre=?, urlFoto=?, precio=?, costo=?, ganancia=? WHERE idproducto=?",
                            new String[]{datos[1], datos[2], datos[3], datos[4], datos[5], datos[0]});
                    break;
                case "eliminar":
                    db.execSQL("DELETE FROM productos WHERE idproducto=?", new String[]{datos[0]});
                    break;
            }
            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            db.close();
        }
    }
}
