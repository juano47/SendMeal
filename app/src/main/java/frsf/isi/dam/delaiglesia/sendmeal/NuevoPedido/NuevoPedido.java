package frsf.isi.dam.delaiglesia.sendmeal.NuevoPedido;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.DBPedido;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.ItemPedidoDao;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.PedidoDao;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.Repository;
import frsf.isi.dam.delaiglesia.sendmeal.Home;
import frsf.isi.dam.delaiglesia.sendmeal.ListaItems;
import frsf.isi.dam.delaiglesia.sendmeal.MapsActivity;
import frsf.isi.dam.delaiglesia.sendmeal.NuevoItem;
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

    private Pedido pedido;
    Double latitud;
    Double longitud;

    private static final int CODIGO_SELECCIONAR_COORDENADAS = 20;

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

        //deshabilitamos el boton "Enviar pedido" mientras no se haya guardado el pedido localmente
        botonEnviarPedido.setEnabled(false);

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

                final TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.item_tabla_resumen_pedido, null);

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("",  ((TextView) row.findViewById(R.id.idNombreProducto)).getText().toString());
                    }
                });
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
                Intent i = new Intent(context, MapsActivity.class);
                startActivityForResult(i, CODIGO_SELECCIONAR_COORDENADAS);
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

                //verificamos que se haya seleccionado al menos un plato
                if (itemsPedido==null){
                    Toast.makeText(context,"Agregá al menos un plato a tu pedido", Toast.LENGTH_SHORT).show();
                }
                //verificamos que haya seleccionado la ubicacion del pedido
                else if(latitud==null){
                    Toast.makeText(context,"Indica la ubicación de tu pedido", Toast.LENGTH_SHORT).show();
                }
                else {
                    //crear pedido
                    pedido = new Pedido();
                    //seteamos los valores restantes de itemsPedido y creamos el pedido
                    pedido.setEstado(1);
                    pedido.setLat(latitud);
                    pedido.setLng(longitud);
                    pedido.setItems(itemsPedido);
                    pedido.setFecha(new Date());

                    //completar los valores de itemsPedido
                    for (int i = 0; i < itemsPedido.size(); i++) {
                        ItemPedido itemPedido;
                        itemPedido = itemsPedido.get(i);
                        itemsPedido.set(i, itemPedido);
                    }

                    GuardarPedido tareaGuardarPedido = new GuardarPedido();
                    tareaGuardarPedido.execute(pedido);
                }
            }
        });

        botonEnviarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedido.setEstado(2);
                GuardarPedido tareaGuardarPedido = new GuardarPedido();
                tareaGuardarPedido.execute(pedido);
                Repository.getInstance().crearPedido(pedido, miHandler);
            }
        });
    }

    class GuardarPedido extends AsyncTask<Pedido, Void, Void> {

        @Override
        protected Void doInBackground(Pedido... pedidos) {
            PedidoDao pedidoDao = DBPedido.getInstance(NuevoPedido.this).getSendMealDB().pedidoDao();
            if(pedidos[0].getId() != null && pedidos[0].getId() >0) {
                pedidoDao.actualizar(pedidos[0]);
            }else {
                pedidoDao.insert(pedidos[0]);
            }

            //obtenemos el id del pedido despues de guardarlo en la BD que es donde recien se lo asigna
            int idPedido = pedidoDao.getAll().get(pedidoDao.getAll().size()-1).getId();
            Log.e("idPedido ", String.valueOf(idPedido));
            ItemPedidoDao itemPedidoDao = DBPedido.getInstance(NuevoPedido.this).getSendMealDB().itemPedidoDao();
            for (int i=0; i<pedidos[0].getItems().size(); i++){
                //recien aca podemos asignar el idPedido a cada item del pedido dado que solo tenemos el id despues que el pedido
                //se guarda en la BD
                if(pedidos[0].getId() != null && pedidos[0].getId() >0) {
                    pedidos[0].getItems().get(i).setIdPedido(idPedido);
                    itemPedidoDao.actualizar(pedidos[0].getItems().get(i));
                    Log.e("guarda item 1", String.valueOf(i));
                }else {
                    pedidos[0].getItems().get(i).setIdPedido(idPedido);
                    itemPedidoDao.insert(pedidos[0].getItems().get(i));
                    Log.e("guarda item 2", String.valueOf(pedidos[0].getItems().get(i).getIdPedido()));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           // pedido = null;

            Toast.makeText(context, "Su pedido se ha creado con éxito!", Toast.LENGTH_SHORT).show();
            botonEnviarPedido.setEnabled(true);
        }
    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            Log.d("APP_2","Vuelve al handler"+msg.arg1);

            switch (msg.arg1 ){
                case Repository._ALTA_PEDIDO:
                    Toast.makeText(context, "Su pedido se ha enviado al servidor con éxito!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(NuevoPedido.this, Home.class);
                    startActivity(i);
                    break;
            }
        }
    };

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Nos fijamos de que actividad viene el resultado
        if (requestCode == CODIGO_SELECCIONAR_COORDENADAS) {
            // Verificamos que el request tuvo éxito
            if (resultCode == RESULT_OK) {
                //obtenemos las coordenadas seleccionadas
                latitud = data.getDoubleExtra("latitud", 0);
                longitud = data.getDoubleExtra("longitud", 0);
            }
        }
    }
}
