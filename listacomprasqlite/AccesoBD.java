package com.example.maanas.listacomprasqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class AccesoBD extends SQLiteOpenHelper {
    public AccesoBD(Context context,  int version) {
        //super(context, "mi_lista_compra", null, version);
        super(context, "mi_lista_compra2", null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_crea_tabla="CREATE TABLE lista_compra(id INTEGER PRIMARY KEY, producto TEXT, cantidad INTEGER, lugar TEXT)";
        db.execSQL(sql_crea_tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void añadirProductos(String producto, int cantidad, String lugar) {
        String sql_añadir="INSERT INTO lista_compra(producto,cantidad,lugar) VALUES ('"+producto+"', '"+cantidad+"','"+lugar+"');";
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(sql_añadir);
    }

    public ArrayList<Compra> listarProductos() {
        ArrayList<Compra> lista_productos=new ArrayList<>();
        String consulta="SELECT * FROM lista_compra ORDER BY lugar";
        SQLiteDatabase sq=getReadableDatabase();
        Cursor c=sq.rawQuery(consulta, null);
        while(c.moveToNext())
        {
            long id=c.getLong(0);
            String producto=c.getString(1);
            int cantidad= Integer.parseInt(c.getString(2));
            String lugar=c.getString(3);
            Compra lista=new Compra(producto,cantidad,lugar,id);
            lista_productos.add(lista);
        }
        return lista_productos;
    }
    //Revisar
    public ArrayList listarPorComercio(String lugar) {
        ArrayList<Compra> lista=new ArrayList<>();
        String consulta="SELECT * FROM lista_compra WHERE lugar=?";
        SQLiteDatabase sq=getReadableDatabase();
        String[] argumentos={lugar};
        Cursor c=sq.rawQuery(consulta, argumentos);
        while(c.moveToNext())
        {
            long id=c.getLong(0);
            String producto=c.getString(1);
            int cantidad= Integer.parseInt(c.getString(2));
            //String lugar=c.getString(3);
            Compra compra=new Compra(producto,cantidad,lugar,id);
            lista.add(compra);
        }
        return lista;
    }

    public void borrar(Compra c) {//Para borrar producto directamente
        String sql_borrar="DELETE FROM lista_compra WHERE producto=? AND cantidad=? AND lugar=?";
        SQLiteDatabase sq=getWritableDatabase();
        Object[] argumentos={c.getProducto(),c.getCantidad(),c.getLugar()};
        sq.execSQL(sql_borrar,argumentos);
    }

    public void borrar(ArrayList<Long> lista_ids) {//Para borrar productos seleccionados
        // DELETE FROM lista_compra WHERE id IN (1, 2, 3, 4);
        for (Long id:lista_ids) {
            SQLiteDatabase sq=getWritableDatabase();
            String sql_borrar="DELETE FROM lista_compra"+" WHERE id="+id;
            sq.execSQL(sql_borrar);

        }
    }


}

