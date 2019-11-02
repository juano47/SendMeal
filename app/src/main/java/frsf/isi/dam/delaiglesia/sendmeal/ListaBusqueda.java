package frsf.isi.dam.delaiglesia.sendmeal;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import frsf.isi.dam.delaiglesia.sendmeal.Auxiliares.RecyclerItemTouchHelper;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.PlatoRepository;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;
import io.apptik.widget.MultiSlider;

import static android.app.Notification.CATEGORY_PROMO;

public class ListaBusqueda extends AppCompatActivity implements AdaptadorItem.CallbackInterface, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private static final int CODIGO_EDITAR_ITEM = 20;
    private RecyclerView mRecyclerView;
    private AdaptadorItem miAdaptador;
    private ArrayList<Plato> lista;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_items);

        context = this;

        //define la flecha para volver en la actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //getting the recyclerview from xml
        mRecyclerView = (RecyclerView) findViewById(R.id.reciclerViewListaItems);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        lista = (ArrayList<Plato>) getIntent().getSerializableExtra("platos");
        //set adapter to recyclerview
        miAdaptador = new AdaptadorItem(lista, context);
        mRecyclerView.setAdapter(miAdaptador);
        miAdaptador.notifyDataSetChanged();


        ItemTouchHelper.SimpleCallback simpleCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT, ListaBusqueda.this);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerView);

        //registramos el broadcast receiver y le asignamos un intentFilter
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyIntentService.ACTION_FIN);
        MyReceiver rcv = new MyReceiver();
        registerReceiver(rcv, filter);
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Nos fijamos de que actividad viene el resultado
        if (requestCode == CODIGO_EDITAR_ITEM) {
            // Verificamos que el request tuvo éxito
            if (resultCode == RESULT_OK) {
                //luego de la edición del plato en la actividad NuevoItem actualizamos la lista
                miAdaptador.notifyDataSetChanged();
            }
        }
    }*/

    //https://codeday.me/es/qa/20190803/1176380.html
    @Override
    public void onHandleSelection(int position, ArrayList<Plato> listaPlatos) {
        //cuando se llama la función desde el adaptador creamos el intent con los datos y lo pasamos a la actividad
        //NuevoItem para editar el plato
        Intent i = new Intent(this, Nuevo_item.class);
        i.putExtra("fila", position);
        i.putExtra("listaPlatos", listaPlatos);
        startActivityForResult(i, CODIGO_EDITAR_ITEM);
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof AdaptadorItem.ItemViewHolder){
            if (direction==ItemTouchHelper.LEFT)
            miAdaptador.eliminarPlato(viewHolder.getAdapterPosition());
            else
                miAdaptador.editarPlato(viewHolder.getAdapterPosition());
        }
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Plato plato = (Plato) intent.getSerializableExtra("plato");
            Intent destino = new Intent(context, Nuevo_item.class);
            destino.putExtra("plato", plato);
            destino.setAction("0"); //necesario accion ficticia para que se envien los datos agregados en el intent (plato)

            int dummyuniqueInt = new Random().nextInt(543254);
            //al pendingIntent le pasamos un numero aleatorio para que interprete cada PendingIntent como uno distinto y no envie los mismos
            //extras (mismo plato) al enviar varias notificaciones ya que no interpreta el cambio en los extras como un intent distinto y siempre
            //devuelve un puntero al primer intent enviado
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(context,dummyuniqueInt , destino, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context, CATEGORY_PROMO)
                            .setSmallIcon(R.drawable.ic_sentiment_very_satisfied)
                            .setContentTitle("Super oferta!!")
                            .setContentText("Solo por 10 minutos! 20% de descuento en " + plato.getTitulo())
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(context);
            notificationManager.notify(new Random().nextInt(543254), mBuilder.build());
            //usamos un numero aleatorio para que se interprete como notificaciones distintas y no reescriba la primera si se envian varias
            //faltaría agruparlas!

        }
    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.arg1 ) {
                case PlatoRepository._BORRADO_PLATO:
                    //accion eliminar
                    break;

                case PlatoRepository._ERROR_PLATO:

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
                Intent i3 = new Intent(this, ListaItems.class);
                startActivity(i3);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
