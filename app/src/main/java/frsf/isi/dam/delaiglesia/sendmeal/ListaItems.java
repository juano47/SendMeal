package frsf.isi.dam.delaiglesia.sendmeal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


import frsf.isi.dam.delaiglesia.sendmeal.Auxiliares.RecyclerItemTouchHelper;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.Repository;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;
import io.apptik.widget.MultiSlider;

import static android.app.Notification.CATEGORY_PROMO;

public class ListaItems extends AppCompatActivity implements AdaptadorItem.CallbackInterface, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView mRecyclerView;
    private AdaptadorItem miAdaptador;

    private ArrayList<Plato>  listaDataSetCompleta;

    private ArrayList<Plato> listaCompleta;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_items);


        context = this;

        //define la flecha para volver en la actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //solicitamos la lista de platos guardada en el servidor
        Repository.getInstance().listarPlatos(miHandler);

        //getting the recyclerview from xml
        mRecyclerView = (RecyclerView) findViewById(R.id.reciclerViewListaItems);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaCompleta = new ArrayList<>();

        //set adapter to recyclerview
        miAdaptador = new AdaptadorItem(listaCompleta, context);
        mRecyclerView.setAdapter(miAdaptador);
        miAdaptador.notifyDataSetChanged();

        ItemTouchHelper.SimpleCallback simpleCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT, ListaItems.this);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerView);

        //registramos el broadcast receiver y le asignamos un intentFilter
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyIntentService.ACTION_FIN);
        MyReceiver rcv = new MyReceiver();
        registerReceiver(rcv, filter);
    }

    //https://codeday.me/es/qa/20190803/1176380.html
    @Override
    public void onHandleSelection(int position, ArrayList<Plato> listaPlatos) {
        //cuando se llama la función desde el adaptador creamos el intent con los datos y lo pasamos a la actividad
        //NuevoItem para editar el plato
        Intent i = new Intent(this, NuevoItem.class);
        i.putExtra("fila", position);
        i.putExtra("listaPlatos", listaPlatos);
        startActivity(i);
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
            Intent destino = new Intent(context, NuevoItem.class);
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
                case Repository._CONSULTA_PLATO:
                    listaDataSetCompleta = Repository.getInstance().getListaPlatosCompleta();
                    //solo al momento de tener la lista de platos desde el servidor la seteamos en pantalla
                    listaCompleta.addAll(listaDataSetCompleta);
                    miAdaptador.notifyDataSetChanged();
                    break;

                case Repository._BUSQUEDA_PLATO:
                    ArrayList<Plato> listaDataSetBusqueda = Repository.getInstance().getListaPlatosBusqueda();
                    if(!listaDataSetBusqueda.isEmpty()) {
                        Intent i2 = new Intent(context, ListaBusqueda.class);
                        i2.putExtra("platos", listaDataSetBusqueda);
                        startActivity(i2);
                    }
                    else {
                        // Crear un builder y vincularlo a la actividad que lo mostrará
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        //Configurar las características
                        builder.setMessage("No se han encontrado resultados");
                        //Obtener una instancia de cuadro de dialogo
                        AlertDialog dialog = builder.create();
                        //Mostrarlo
                        dialog.show();
                    }


                case Repository._ERROR_PLATO:

                    break;

            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_lista_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //flecha volver en la actionbar y acción buscar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i3 = new Intent(this, Home.class);
                startActivity(i3);
                return true;
            case R.id.action_buscar_item:
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();
                // Inflar y establecer el layout para el dialogo
                // Pasar nulo como vista principal porque va en el diseño del diálogo
                final View v = inflater.inflate(R.layout.dialogo_busqueda, null);

                final EditText txtnombre = v.findViewById(R.id.editTextNombreItemABuscar);
                MultiSlider multiSlider = v.findViewById(R.id.range_slider);
                final TextView txtmin = v.findViewById(R.id.textViewPrecioMin);
                final TextView txtmax = v.findViewById(R.id.textViewPrecioMax);

                multiSlider.setMax(1000);

                txtmin.setText(String.valueOf(multiSlider.getThumb(0).getValue()));
                txtmax.setText(String.valueOf(multiSlider.getThumb(1).getValue()));

                multiSlider.setOnThumbValueChangeListener(new MultiSlider.SimpleChangeListener() {
                    @Override
                    public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int
                            thumbIndex, int value) {
                        if (thumbIndex == 0) {
                            txtmin.setText(String.valueOf(value));
                        } else {
                            txtmax.setText(String.valueOf(value));
                        }
                    }
                });


                // Crear un builder y vincularlo a la actividad que lo mostrará
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //Configurar las características
                builder
                        .setPositiveButton("Buscar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        String nombre = txtnombre.getText().toString();
                                        Double min = Double.valueOf(txtmin.getText().toString());
                                        Double max = Double.valueOf(txtmax.getText().toString());
                                        Repository.getInstance().buscarPlatos(nombre, min , max, miHandler);

                                    }
                                })
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        //para cancelar no hace falta hacer nada, por default se cierra la ventana de dialogo
                                    }
                                })
                .setView(v);

                //Obtener una instancia de cuadro de dialogo
                AlertDialog dialog = builder.create();
                //Mostrarlo
                dialog.show();


                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
