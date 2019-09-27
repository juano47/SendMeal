package frsf.isi.dam.delaiglesia.sendmeal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import java.util.ArrayList;


import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;

public class ListaItems extends AppCompatActivity implements AdaptadorItem.CallbackInterface {

    private static final int CODIGO_EDITAR_ITEM = 20;
    private RecyclerView mRecyclerView;
    private AdaptadorItem miAdaptador;
    private ArrayList<Plato> listaPlatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_items);

        //define la flecha para volver en la actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listaPlatos = (ArrayList<Plato>)getIntent().getSerializableExtra("listaPlatos");

        //getting the recyclerview from xml
        mRecyclerView = (RecyclerView) findViewById(R.id.reciclerViewListaItems);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set adapter to recyclerview
        miAdaptador = new AdaptadorItem(listaPlatos,this);
        mRecyclerView.setAdapter(miAdaptador);

        miAdaptador.notifyDataSetChanged();
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
    }

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
}
