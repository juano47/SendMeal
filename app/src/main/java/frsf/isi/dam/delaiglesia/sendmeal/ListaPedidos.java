package frsf.isi.dam.delaiglesia.sendmeal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.DBPedido;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.PedidoDao;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Pedido;

public class ListaPedidos extends AppCompatActivity {

    List<Pedido> listaPedidosDataset;
    Context context;
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        //define la flecha para volver en la actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        context = this;

        table = this.findViewById(R.id.idTablaResumenPedido);


        final Runnable hiloUpdateLista = new Runnable() {
            @Override
            public void run() {
            for (int i = 0; i < listaPedidosDataset.size(); i++) {
                final int k;
                k=i;
                final TableRow row = (TableRow) LayoutInflater.from(context).inflate(R.layout.item_tabla_resumen_pedido, null);

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("",  ((TextView) row.findViewById(R.id.idNombreProducto)).getText().toString());
                        Intent i1 = new Intent(context, DetallePedido.class);
                        i1.putExtra("pedido", listaPedidosDataset.get(k));
                        startActivity(i1);
                    }
                });
                ((TextView) row.findViewById(R.id.idNombreProducto)).setText(listaPedidosDataset.get(i).getId().toString());
                ((TextView) row.findViewById(R.id.idCantidadProducto)).setText(listaPedidosDataset.get(i).getEstado().toString());
                ((TextView) row.findViewById(R.id.idSubtotalProducto)).setText(listaPedidosDataset.get(i).getFecha().toString().substring(0,16));
                table.addView(row);
            }
            }
        };
        final Runnable cargarPedidos = new Runnable() {
            @Override
            public void run() {
                PedidoDao dao = DBPedido.getInstance(ListaPedidos.this).getSendMealDB().pedidoDao();
                listaPedidosDataset = dao.getAll();
                runOnUiThread(hiloUpdateLista);
            }
        };
        Thread t1 = new Thread(cargarPedidos);
        t1.start();
    }

    //flecha volver en la actionbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
