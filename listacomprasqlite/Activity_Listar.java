package com.example.maanas.listacomprasqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class Activity_Listar extends AppCompatActivity {
    ListView lv_lista;
    ImageButton btn_borracheck;
    MiAdaptadorPersonalizado adaptador=null;
    Context contexto;
    Spinner spn_filtro;
    ArrayList<Compra> lista_productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__listar);
        cargarVistas();
    }

    private void cargarVistas() {
        contexto=this;
        lv_lista = findViewById(R.id.lv_lista);
        btn_borracheck=findViewById(R.id.btn_borrachk);
        //Creo spinner para filtrar por comercio
        spn_filtro=findViewById(R.id.spn_filtrar);
        String [] opciones={"Seleccione lugar","Carrefour","Mercadona","Lidl","Alcampo","AhorraMas","Dia","Aldi"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,opciones);
        spn_filtro.setAdapter(adapter);
        AccesoBD bd = new AccesoBD(this, 1);
        lista_productos = bd.listarProductos();
        //ArrayAdapter adaptador=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,lista_lugar);
       // ArrayAdapter adapter=new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,lista);
        adaptador=new MiAdaptadorPersonalizado(lista_productos,this);
        lv_lista.setAdapter(adaptador);
        //Pido que me liste los productos por comercio elegido
        spn_filtro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spn_filtro.getSelectedItemPosition()!=0)
                {
                    String lugar = (String) spn_filtro.getSelectedItem();
                    AccesoBD bd = new AccesoBD(contexto, 1);
                    lista_productos = bd.listarPorComercio(lugar);
                    adaptador.setLista_productos(lista_productos);
                    adaptador.notifyDataSetChanged();
                }
                else
                {
                    AccesoBD bd = new AccesoBD(contexto, 1);
                    ArrayList<Compra> lista_productos = bd.listarProductos();
                    adaptador.setLista_productos(lista_productos);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Boton borrar los que están chequeados
        btn_borracheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enterarse de qué productos se han chequeado.
                final ArrayList<Long> lista_chequeados=adaptador.getLista_productos_chequeados();
                AccesoBD bd=new AccesoBD(contexto, 1);
                //adaptador.setLista_productos(lista_productos[0]);
                //adaptador.notifyDataSetChanged();
                //Hago AlertDialog para preguntar si quiere borrar
                AlertDialog.Builder builder=new AlertDialog.Builder(contexto);
                builder.setTitle("Borrado");
                builder.setMessage("¿Esta seguro que quiere borrar los productos?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AccesoBD bd=new AccesoBD(contexto,1);
                        bd.borrar(lista_chequeados);
                        //Actualizamos la lista, volvemos a cargar la lista y hago notify
                        adaptador.setLista_productos(lista_productos);
                        adaptador.notifyDataSetChanged();
                        spn_filtro.setSelection(0);
                    }
                });
                builder.setNegativeButton("Cancelar",null);
                AlertDialog alerta=builder.create();
                alerta.show();
                lv_lista.setAdapter(adaptador);
            }
        });

    }

}
