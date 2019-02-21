package com.example.maanas.listacomprasqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Grabar extends AppCompatActivity {
    TextView tv_producto,tv_lugar,tv_cantidad;
    EditText et_producto,et_cantidad;
    Spinner spn_lugar;
    Button boton_añadir,boton_listar;
    AccesoBD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__grabar);
        cargarVistas();
    }

    private void cargarVistas() {
        tv_producto=findViewById(R.id.tv_producto);
        tv_cantidad=findViewById(R.id.tv_cantidad);
        tv_lugar=findViewById(R.id.tv_lugar);
        et_producto=findViewById(R.id.et_producto);
        et_cantidad=findViewById(R.id.et_cantidad);
        spn_lugar=findViewById(R.id.spn_super);
        String [] opciones={"Seleccione lugar","Carrefour","Mercadona","Lidl","Alcampo","AhorraMas","Dia","Aldi"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,opciones);
        spn_lugar.setAdapter(adapter);
        boton_añadir=findViewById(R.id.btn_añadir);
        //boton_añadir.setBackgroundColor(Color.rgb(14,204,210)); Para cambiar color desde codigo
        boton_listar=findViewById(R.id.btn_listar);
        bd=new AccesoBD(this,1);
        boton_añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String producto=et_producto.getText().toString();
                String cantidad= et_cantidad.getText().toString();
                String lugar= String.valueOf(spn_lugar.getSelectedItem());
                if (producto.equals("") || cantidad.equals("") || lugar.equals(""))
                {//Si algún campo no se ha rellenado, salata el toast
                    Toast.makeText(Activity_Grabar.this, "Por favor, rellene todos los campos", Toast.LENGTH_LONG).show();
                }
                else //Están rellenos todos los campos
                {
                    //Pongo 0 en id porque realmento no se usa en la insercion ya que id lo crea SQLite
                    Compra c=new Compra(producto, Integer.parseInt(cantidad), lugar,0);
                    AccesoBD bd=new AccesoBD(Activity_Grabar.this, 1);
                    bd.añadirProductos(producto, Integer.parseInt(cantidad),lugar);
                }
                textoProductoAñadido();
                limpiarCampos();
            }
        });
        boton_listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarActivity();
            }
        });
    }

    private void textoProductoAñadido() {
        Toast notificacion=Toast.makeText(this,"Producto añadido",Toast.LENGTH_LONG);
        notificacion.show();
    }

    private void limpiarCampos() {
        et_producto.setText("");
        et_cantidad.setText("");
        spn_lugar.setSelection(0);
    }

    private void cambiarActivity() {
        Intent i=new Intent(this, Activity_Listar.class);
        startActivity(i);
    }
}
