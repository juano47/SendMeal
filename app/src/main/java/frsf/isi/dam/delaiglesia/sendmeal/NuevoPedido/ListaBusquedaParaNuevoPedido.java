package frsf.isi.dam.delaiglesia.sendmeal.NuevoPedido;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import frsf.isi.dam.delaiglesia.sendmeal.AdaptadorItem;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.PlatoRepository;
import frsf.isi.dam.delaiglesia.sendmeal.R;
import frsf.isi.dam.delaiglesia.sendmeal.domain.ItemPedido;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;
import io.apptik.widget.MultiSlider;

public class ListaBusquedaParaNuevoPedido extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdaptadorItemParaNuevoPedido miAdaptador;
    private ArrayList<Plato> lista;
    private String[] listaCantidades;
    private Context context;

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

        //mostramos el subtitulo: "Resultados de búsqueda"
        TextView subtitulo = findViewById(R.id.textViewTituloResultadoDeBusqueda);
        subtitulo.setVisibility(View.VISIBLE);

        //getting the recyclerview from xml
        mRecyclerView = (RecyclerView) findViewById(R.id.reciclerViewListaItems);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        lista = (ArrayList<Plato>) getIntent().getSerializableExtra("platos");
        listaCantidades = getIntent().getStringArrayExtra("listaCantidades");

        //set adapter to recyclerview
        miAdaptador = new AdaptadorItemParaNuevoPedido(lista, listaCantidades, context);
        mRecyclerView.setAdapter(miAdaptador);
        miAdaptador.notifyDataSetChanged();

    }

    //flecha volver en la actionbar y acción buscar
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
