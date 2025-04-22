package com.ugb.miprimeraaplicacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "yomi";
    private static final int DATABASE_VERSION = 1;

    // Tabla de productos
    private static final String SQLdb = "CREATE TABLE javier (" +
            "idproducto TEXT PRIMARY KEY, " +
            "nombre TEXT, " +
            "precio TEXT, " +
            "costo TEXT, " +
            "ganancias TEXT, " +
            "urlFoto TEXT)";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLdb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes manejar las actualizaciones de base de datos si es necesario
        db.execSQL("DROP TABLE IF EXISTS javier");
        onCreate(db);
    }

    public String administrar_javier(String accion, String[] datos) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            String mensaje = "ok";
            ContentValues values = new ContentValues();

            switch (accion) {
                case "nuevo":
                    values.put("idproducto", datos[0]);
                    values.put("nombre", datos[1]);
                    values.put("precio", datos[2]);
                    values.put("costo", datos[3]);
                    values.put("ganancias", datos[4]);
                    values.put("urlFoto", datos[5]);
                    db.insertOrThrow("javier", null, values);
                    break;
                case "modificar":
                    values.put("nombre", datos[1]);
                    values.put("precio", datos[2]);
                    values.put("costo", datos[3]);
                    values.put("ganancias", datos[4]);
                    values.put("urlFoto", datos[5]);
                    db.update("javier", values, "idproducto = ?", new String[]{datos[0]});
                    break;
                case "eliminar":
                    db.delete("javier", "idproducto = ?", new String[]{datos[0]});
                    break;
                default:
                    return "Acción no reconocida";
            }
            db.setTransactionSuccessful();  // Marcar la transacción como exitosa
            return mensaje;
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        } finally {
            if (db != null) {
                db.endTransaction();  // Finalizar transacción
                db.close();
            }
        }
    }

    public Cursor lista_javier() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM javier", null);
    }
}
