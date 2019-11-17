package frsf.isi.dam.delaiglesia.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.delaiglesia.sendmeal.Dao.Repository;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.DBPedido;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.ItemPedidoDao;
import frsf.isi.dam.delaiglesia.sendmeal.domain.ItemPedido;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Pedido;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;

import static java.lang.Thread.sleep;

public class DetallePedido extends AppCompatActivity {

    List<ItemPedido> listaItemsPedidoDataset;
    private ArrayList<Plato>  listaDataSetPlatos;

    private TextView idPedido;
    private TextView fechaPedido;
    private TextView estadoPedido;
    private Button buttonnVerUbicacionPedido;
    private Pedido pedido;
    TableLayout table;
    private TextView txtTotal;
    private Runnable hiloUpdateLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        pedido = (Pedido) getIntent().getSerializableExtra("pedido");
        if (getIntent().getSerializableExtra("pedido")!=null)
        Log.e("2", String.valueOf(pedido.getId()));
        //referencias a TextViews
        idPedido = findViewById(R.id.textViewDetallePedidoId);
        fechaPedido = findViewById(R.id.textViewDetallePedidoFecha);
        estadoPedido = findViewById(R.id.textViewDetallePedidoEstado);

        table = this.findViewById(R.id.idTablaResumenPedido);
        txtTotal =findViewById(R.id.idTotal);
        txtTotal.setText("");

        idPedido.setText(pedido.getId().toString());
        fechaPedido.setText(pedido.getFecha().toString());
        switch (pedido.getEstado()){
            case 1:
                estadoPedido.setText("Pendiente");
                break;
            case 2:
                estadoPedido.setText("Enviado");
                break;
            case 3:
                estadoPedido.setText("Aceptado");
                break;
            case 4:
                estadoPedido.setText("Rechazado");
                break;
            case 5:
                estadoPedido.setText("En preparación");
                break;
            case 6:
                estadoPedido.setText("En envío");
                break;
            case 7:
                estadoPedido.setText("Entregado");
                break;
            case 8:
                estadoPedido.setText("Cancelado");
                break;
        }

        final TableLayout table = (TableLayout) this.findViewById(R.id.idTablaResumenPedido);

        hiloUpdateLista = new Runnable() {
            @Override
            public void run() {
                //cargamos los platos y las cantidades en la tabla

                Double total = 0.00;
                //a la lista de itemsPedido obtenida le agregamos los datos del plato que corresponda
                Log.e("listaPlatos tamaño ", String.valueOf(listaDataSetPlatos.size()));
                Log.e("listaItems tamaño ", String.valueOf(listaItemsPedidoDataset.size()));
                for (int i=0; i<listaItemsPedidoDataset.size(); i++){
                    for (int j=0; j<listaDataSetPlatos.size(); j++){
                        Log.e("plato busqueda id ", String.valueOf( listaDataSetPlatos.get(j).getId()));
                        Log.e("pedido id  22", String.valueOf(listaItemsPedidoDataset.get(i).getIdPedido()));
                        if (listaItemsPedidoDataset.get(i).getIdPlato()==listaDataSetPlatos.get(j).getId()){
                            listaItemsPedidoDataset.get(i).setPlato(listaDataSetPlatos.get(j));
                        }
                    }
                }

                for (int i = 0; i < listaItemsPedidoDataset.size(); i++) {

                    final TableRow row = (TableRow) LayoutInflater.from(DetallePedido.this).inflate(R.layout.item_tabla_resumen_pedido, null);
                    ((TextView) row.findViewById(R.id.idNombreProducto)).setText(listaItemsPedidoDataset.get(i).getPlato().getTitulo());
                    ((TextView) row.findViewById(R.id.idCantidadProducto)).setText(listaItemsPedidoDataset.get(i).getCantidad().toString());
                    Double precio = listaItemsPedidoDataset.get(i).getPlato().getPrecio();
                    int cantidad = listaItemsPedidoDataset.get(i).getCantidad();
                    total = total + precio * cantidad;
                    String subtotal = String.valueOf(precio * cantidad);
                    ((TextView) row.findViewById(R.id.idSubtotalProducto)).setText("$ " + subtotal);

                    table.addView(row);
                }
                txtTotal.setText("$ " + String.valueOf(total));

                //Boton "Ver ubicación del pedido"
                buttonnVerUbicacionPedido = findViewById(R.id.buttonVerUbicacionPedido);
                buttonnVerUbicacionPedido.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(DetallePedido.this,MapsActivity.class);
                        i.putExtra("pedido", pedido);
                        startActivity(i);
                    }
                });
            }
        };


        final Runnable cargarPlatos = new Runnable() {
            @Override
            public void run() {

                ItemPedidoDao dao = DBPedido.getInstance(DetallePedido.this).getSendMealDB().itemPedidoDao();
                listaItemsPedidoDataset = dao.getAll(pedido.getId());
                Log.e("tamaño listaaa", String.valueOf(listaItemsPedidoDataset.size()));
                //solicitamos la lista de platos guardada en el servidor
                Repository.getInstance().listarPlatos(miHandler);


            }
        };
        Thread t1 = new Thread(cargarPlatos);
        t1.start();


    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.arg1 ) {
                case Repository._CONSULTA_PLATO:
                    listaDataSetPlatos = Repository.getInstance().getListaPlatosCompleta();
                    Log.e("0", "entra");
                    runOnUiThread(hiloUpdateLista);
                    break;

                case Repository._ERROR_PLATO:
                    Log.e("1", "se va por error");
                    break;
            }
        }
    };
}
