package com.example.maanas.listacomprasqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MiAdaptadorPersonalizado extends BaseAdapter {
    ArrayList<Compra> lista_productos;
    Context contexto;
    ListView lv_lista;

    public MiAdaptadorPersonalizado(ArrayList<Compra> lista_productos, Context contexto) {
        this.lista_productos = lista_productos;
        this.contexto = contexto;
    }


    @Override
    public int getCount() {
        return lista_productos.size();
    }

    @Override
    public Object getItem(int position) {
        return lista_productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(contexto);
        //Se elige el layout en el que has creado como quieres que se vea la vista
        View v=inflater.inflate(R.layout.my_layout, parent, false);
        //String producto=lista_productos.get(position).getProducto();
        //int cantidad=lista_productos.get(position).getCantidad();
        //String lugar=lista_productos.get(position).getLugar();
        final Compra c=lista_productos.get(position);
        TextView tv_producto=v.findViewById(R.id.tv_producto2);
        TextView tv_cantidad=v.findViewById(R.id.tv_cantidad2);
        TextView tv_lugar=v.findViewById(R.id.tv_lugar2);
        final CheckBox chk_borrar=v.findViewById(R.id.chk_borrar);
        lv_lista=v.findViewById(R.id.lv_lista);
        tv_producto.setText(c.getProducto());
        tv_cantidad.setText(String.valueOf(c.getCantidad()));
        tv_lugar.setText(c.getLugar());
        chk_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean chequeado= chk_borrar.isChecked();
                //Log.d("checkbox",String.valueOf(chequeado) + p.getId());
                if (chequeado==true) {
                    annadirProducto(c.getId());
                }
                else
                {
                    sacarProducto(c.getId());
                }
            }
        });
        ImageButton btn_borrar=v.findViewById(R.id.btn_borrar);
        btn_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccesoBD bd=new AccesoBD(contexto,1);
                bd.borrar(c);
                //Actualizamos la lista, volvemos a cargar la lista y hago notify
                lista_productos=bd.listarProductos();
                notifyDataSetChanged();
                Toast notificacion=Toast.makeText(contexto,"Producto borrado",Toast.LENGTH_LONG);
                notificacion.show();
                /*AlertDialog.Builder builder=new AlertDialog.Builder(contexto);
                builder.setTitle("Borrado");
                builder.setMessage("¿Esta seguro que quiere borrar el producto "+c.getProducto()+"?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AccesoBD bd=new AccesoBD(contexto,1);
                        bd.borrar(c);
                        //Actualizamos la lista, volvemos a cargar la lista y hago notify
                        lista_productos=bd.listarProductos();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancelar",null);
                AlertDialog alerta=builder.create();
                alerta.show();*/
            }
        });
        return v;
    }

    private void sacarProducto(long id) {
        lista_productos_chequeados.remove(id);
    }

    private void annadirProducto(long id) {
        lista_productos_chequeados.add(id);
    }

    ArrayList<Long> lista_productos_chequeados=new ArrayList<>();

    public ArrayList<Long> getLista_productos_chequeados() {
        return lista_productos_chequeados;
    }

    public void setLista_productos(ArrayList<Compra> lista_productos) {
        this.lista_productos = lista_productos;
    }
}
