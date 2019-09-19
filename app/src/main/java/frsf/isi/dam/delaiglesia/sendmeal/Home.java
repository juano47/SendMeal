package frsf.isi.dam.delaiglesia.sendmeal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;

public class Home extends AppCompatActivity {

    private static final int CODIGO_CREAR_ITEM = 10;

    private List<Plato> listaPlatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        listaPlatos = new ArrayList<>();
        Plato plato = new Plato(1, "Plato 1", "descripcion", 22.50, 2);
        listaPlatos.add(plato);
        plato = new Plato(1, "Plato 2", "descripcion", 120.00, 2);
        listaPlatos.add(plato);
        plato = new Plato(1, "Plato 1", "descripcion", 20.50, 2);
        listaPlatos.add(plato);plato = new Plato(1, "Plato 3", "descripcion", 520.55, 2);
        listaPlatos.add(plato);plato = new Plato(1, "Plato 4", "descripcion", 70.40, 2);
        listaPlatos.add(plato);
        plato = new Plato(1, "Plato 5", "descripcion", 220.90, 2);
        listaPlatos.add(plato);
        plato = new Plato(1, "Plato 6", "descripcion", 55.00, 2);
        listaPlatos.add(plato);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_registrarse:
                Intent i1 = new Intent(this, Registrarse.class);
                startActivity(i1);
                return true;
            case R.id.action_crear_item:
                Intent i2 = new Intent(this,Nuevo_item.class);
                startActivityForResult(i2,CODIGO_CREAR_ITEM);
                return true;
            case R.id.action_ver_lista:
                Intent i3 = new Intent(this, ListaItems.class);
                i3.putExtra("listaPlatos", (Serializable) listaPlatos);
                startActivity(i3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Nos fijamos de que actividad viene el resultado
        if (requestCode == CODIGO_CREAR_ITEM) {
            // Verificamos que el request tuvo éxito
            if (resultCode == RESULT_OK) {
                Plato plato;
                Bundle objetoRecibido = data.getExtras();
                plato = (Plato) objetoRecibido.getSerializable("plato");
                listaPlatos.add(plato);

            }
        }

    }
}
