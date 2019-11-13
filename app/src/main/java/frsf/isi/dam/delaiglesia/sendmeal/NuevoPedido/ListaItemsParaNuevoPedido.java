package frsf.isi.dam.delaiglesia.sendmeal.NuevoPedido;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import frsf.isi.dam.delaiglesia.sendmeal.Dao.Repository;
import frsf.isi.dam.delaiglesia.sendmeal.R;
import frsf.isi.dam.delaiglesia.sendmeal.domain.ItemPedido;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;

public class ListaItemsParaNuevoPedido extends AppCompatActivity{

    private Context context;
    private RecyclerView mRecyclerView;
    private AdaptadorItemParaNuevoPedido miAdaptador;

    private ArrayList<Plato>  listaDataSetCompleta;
    private ArrayList<Plato> listaCompleta;

    private String[] listaCantidades;

    private Button botonAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_items);

        context = this;

        //mostramos el boton: Aceptar selección de platos"
        botonAceptar = findViewById(R.id.buttonAceptarSeleccionPlatos);
        botonAceptar.setVisibility(View.VISIBLE);

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
        miAdaptador = new AdaptadorItemParaNuevoPedido(listaCompleta, context);
        mRecyclerView.setAdapter(miAdaptador);
        miAdaptador.notifyDataSetChanged();

        botonAceptar = findViewById(R.id.buttonAceptarSeleccionPlatos);
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaCantidades = miAdaptador.getListaCantidades();

                ArrayList<ItemPedido> itemsPedido = new ArrayList<>();

                for (int i=0; i<listaCantidades.length; i++){
                    if (listaCantidades[i]!="0") {
                        ItemPedido itemPedido = new ItemPedido();
                        itemPedido.setPlato(listaCompleta.get(i));
                        itemPedido.setCantidad(Integer.parseInt(listaCantidades[i]));
                        itemPedido.setPrecio(listaCompleta.get(i).getPrecio());
                        itemPedido.setIdPlato(listaCompleta.get(i).getId());
                        itemsPedido.add(itemPedido);
                    }
                }
                Intent i = new Intent(context, NuevoPedido.class);
                i.putExtra("itemsPedido", itemsPedido );
                startActivity(i);
            }
        });
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
                    miAdaptador.setTamanioListaCantidades(listaCompleta.size());
                    break;

                case Repository._BUSQUEDA_PLATO:
                    ArrayList<Plato> listaDataSetBusqueda = Repository.getInstance().getListaPlatosBusqueda();
                    if(!listaDataSetBusqueda.isEmpty()) {
                        Intent i2 = new Intent(context, ListaBusquedaParaNuevoPedido.class);
                        i2.putExtra("platos", listaDataSetBusqueda);
                        //enviamos la lista de listaCantidades para seguir mostrando las cantidades que ya seleccionó
                        listaCantidades = miAdaptador.getListaCantidades();

                        for (int i=0; i<listaDataSetBusqueda.size(); i++){
                            for (int j=0; j<listaCompleta.size(); j++) {
                                if (!listaCompleta.get(j).equals(listaDataSetBusqueda.get(i))) {
                                    for (int k = 0; k < listaCantidades.length; k++) {
                                        listaCantidades[k] = listaCantidades[k + 1];
                                    }
                                }
                            }
                        }
                        i2.putExtra("listaCantidades", listaCantidades);
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
                onBackPressed();
                return true;
            case R.id.action_buscar_item:

                Toast.makeText(context, "No es posible realizar búsquedas en este momento. Intente más tarde",Toast.LENGTH_SHORT).show();
                /*
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


                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }


}
