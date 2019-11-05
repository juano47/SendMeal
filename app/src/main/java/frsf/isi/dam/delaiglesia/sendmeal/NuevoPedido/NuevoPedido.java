package frsf.isi.dam.delaiglesia.sendmeal.NuevoPedido;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.DBPedido;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.PedidoDao;
import frsf.isi.dam.delaiglesia.sendmeal.Home;
import frsf.isi.dam.delaiglesia.sendmeal.ListaItems;
import frsf.isi.dam.delaiglesia.sendmeal.R;
import frsf.isi.dam.delaiglesia.sendmeal.domain.ItemPedido;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Pedido;

public class NuevoPedido extends AppCompatActivity {

    private Context context;

    private Button botonSeleccionarUbicacion;
    private Button botonSeleccionarPlatos;
    private Button botonCrearPedido;
    private Button botonEnviarPedido;

    private TextView txtTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);

        context = this;

        //define la flecha para volver en la actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        botonSeleccionarUbicacion = findViewById(R.id.buttonSeleccionarUbicacion);
        botonSeleccionarPlatos = findViewById(R.id.buttonSeleccionarPlatos);
        botonCrearPedido = findViewById(R.id.buttonCrearPedido);
        botonEnviarPedido = findViewById(R.id.buttonEnviarPedido);

        txtTotal =findViewById(R.id.idTotal);
        txtTotal.setText("");

        //checkeamos si la actividad se volvió a llamar despues de haber seleccionado los platos
        if(getIntent().getSerializableExtra("itemsPedido")!=null) {

            //obtenemos la lista de itemsPedido
            ArrayList<ItemPedido> itemsPedido = (ArrayList<ItemPedido>) getIntent().getSerializableExtra("itemsPedido");


            //cargamos los platos y las cantidades en la tabla
            TableLayout table = (TableLayout) this.findViewById(R.id.idTablaResumenPedido);
            Double total = 0.00;
            for (int i = 0; i < itemsPedido.size(); i++) {

                TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.item_tabla_resumen_pedido, null);

                ((TextView) row.findViewById(R.id.idNombreProducto)).setText(itemsPedido.get(i).getPlato().getTitulo());
                ((TextView) row.findViewById(R.id.idCantidadProducto)).setText(itemsPedido.get(i).getCantidad().toString());
                Double precio = itemsPedido.get(i).getPlato().getPrecio();
                int cantidad = itemsPedido.get(i).getCantidad();
                total = total + precio * cantidad;
                String subtotal = String.valueOf(precio * cantidad);
                ((TextView) row.findViewById(R.id.idSubtotalProducto)).setText("$ " + subtotal);

                table.addView(row);
            }

            txtTotal.setText("$ " + String.valueOf(total));
        }

        botonSeleccionarUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //a implementar en lab05
            }
        });

        botonSeleccionarPlatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ListaItemsParaNuevoPedido.class);
                startActivity(i);
            }
        });

        botonCrearPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //obtenemos la lista de itemsPedido
                ArrayList<ItemPedido> itemsPedido = (ArrayList<ItemPedido>) getIntent().getSerializableExtra("itemsPedido");

                //seteamos los valores restantes de itemsPedido y creamos el pedido

                //crear pedido
                Pedido pedido = new Pedido();
                pedido.setEstado(1);
                pedido.setLat(0.0);
                pedido.setLng(0.0);
                pedido.setItems(itemsPedido);
                pedido.setFecha(new Date());


                //completar los valores de itemsPedido
                for (int i=0; i<itemsPedido.size(); i++){
                    ItemPedido itemPedido;
                    itemPedido = itemsPedido.get(i);
                    itemPedido.setPedido(pedido);
                    itemPedido.setPrecio(itemPedido.getPlato().getPrecio());
                    itemsPedido.set(i,itemPedido);
                }

                GuardarPedido tareaGuardarObra = new GuardarPedido();
                tareaGuardarObra.execute(pedido);


            }
        });
    }

    class GuardarPedido extends AsyncTask<Pedido, Void, Void> {

        @Override
        protected Void doInBackground(Pedido... pedidos) {
            PedidoDao dao = DBPedido.getInstance(NuevoPedido.this).getSendMealDB().pedidoDao();
            if(pedidos[0].getId() != null && pedidos[0].getId() >0) {
                dao.actualizar(pedidos[0]);
            }else {
                dao.insert(pedidos[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           // pedido = null;
            //Intent i = new Intent(NuevoPedido.this, ObraListActivity.class);
           // startActivity(i);
        }
    }

    class AllPedidos extends AsyncTask<Pedido, Void, Void> {

        @Override
        protected Void doInBackground(Pedido... pedidos) {
            PedidoDao dao = DBPedido.getInstance(NuevoPedido.this).getSendMealDB().pedidoDao();
            ArrayList<Pedido> listaPedidos = (ArrayList<Pedido>) dao.getAll();
            
            /*if(pedidos[0].getId() != null && pedidos[0].getId() >0) {
                dao.actualizar(pedidos[0]);
            }else {
                dao.insert(pedidos[0]);
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // pedido = null;
            //Intent i = new Intent(NuevoPedido.this, ObraListActivity.class);
            // startActivity(i);
        }
    }

    //flecha volver en la actionbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(context, Home.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
