package com.example.digitacioncaja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper  {

    public static String DBNAME = "local";
    public static  int DBVER = 1;
    public DBHelper(Context context) {
        super(context, DBNAME, null,DBVER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql;
        strSql = "CREATE TABLE IF NOT EXISTS PobladosDigitacion ("
                + " coddes INTEGER PRIMARY KEY, "
                + " desdes TEXT, "
                + " codage TEXT, "
                + " codpai TEXT, "
                + " diaser TEXT)";
        db.execSQL(strSql);


        strSql = "CREATE TABLE IF NOT EXISTS piloto ("
                + " codigo_piloto TEXT,"
                + " nombre_piloto TEXT,"
                + " contrasena TEXT,"
                + " ruta_piloto TEXT,"
                + " tipo_usuario SMALLINT,"
                + " unidad TEXT,"
                + " id_asignacion TEXT,"
                + " hub TEXT,"
                + " duenio_ruta TEXT,"
                + " baremp TEXT,"
                + " nombre_ruta TEXT,"
                + " tipo_login SMALLINT,"
                + " cod_empleado TEXT,"
                + " clasificacion_ruta TEXT,"
                + " poblado_origen TEXT)";
        db.execSQL(strSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strSql = "DROP TABLE IF EXISTS PobladosDigitacion";
        db.execSQL(strSql);
        strSql = "CREATE TABLE IF NOT EXISTS PobladosDigitacion ("
                + " coddes INTEGER PRIMARY KEY, "
                + " desdes TEXT, "
                + " codage TEXT, "
                + " codpai TEXT, "
                + " diaser TEXT)";
        db.execSQL(strSql);

        strSql = "DROP TABLE IF EXISTS piloto";
        db.execSQL(strSql);
        strSql = "CREATE TABLE IF NOT EXISTS piloto ("
                + " codigo_piloto TEXT,"
                + " nombre_piloto TEXT,"
                + " contrasena TEXT,"
                + " ruta_piloto TEXT,"
                + " tipo_usuario SMALLINT,"
                + " unidad TEXT,"
                + " id_asignacion TEXT,"
                + " hub TEXT,"
                + " duenio_ruta TEXT,"
                + " baremp TEXT,"
                + " nombre_ruta TEXT,"
                + " tipo_login SMALLINT,"
                + " cod_empleado TEXT,"
                + " clasificacion_ruta TEXT,"
                + " poblado_origen TEXT)";
        db.execSQL(strSql);
    }

    public Cursor getPoblados(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM PobladosDigitacion";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public String InsertarPoblado(Integer coddes, String desdes, String codage, String codpai, String diaser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("coddes",coddes);
        values.put("desdes",desdes);
        values.put("codage",codage);
        values.put("codpai",codpai);
        values.put("diaser",diaser);
        db.insert("PobladosDigitacion",null,values);
        db.close();
        return "Poblado insertado correctamente";
    }

    public String InsertarPiloto(Integer coddes, String desdes, String codage, String codpai, String diaser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("codigo_piloto",coddes);
        values.put("nombre_piloto",desdes);
        values.put("contrasena",codage);
        values.put("ruta_piloto",codpai);
        values.put("tipo_usuario",diaser);
        values.put("unidad",diaser);
        values.put("id_asignacion",diaser);
        values.put("hub",diaser);
        values.put("duenio_ruta",diaser);
        values.put("baremp",diaser);
        values.put("nombre_ruta",diaser);
        values.put("tipo_login",diaser);
        values.put("cod_empleado",diaser);
        values.put("clasificacion_ruta",diaser);
        values.put("poblado_origen",diaser);
        db.insert("piloto",null,values);
        db.close();
        return "Piloto insertado correctamente";
    }
}
