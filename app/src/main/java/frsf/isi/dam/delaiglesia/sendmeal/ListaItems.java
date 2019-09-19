package frsf.isi.dam.delaiglesia.sendmeal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;

public class ListaItems extends AppCompatActivity {

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
