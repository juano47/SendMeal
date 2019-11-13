package frsf.isi.dam.delaiglesia.sendmeal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

import frsf.isi.dam.delaiglesia.sendmeal.NuevoPedido.NuevoPedido;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_home, menu);
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
                Intent i2 = new Intent(this, NuevoItem.class);
                return true;
            case R.id.action_ver_lista_platos:
                Intent i3 = new Intent(this, ListaItems.class);
                startActivity(i3);
                return true;
            case R.id.action_ver_lista_pedidos:
                Intent i4 = new Intent(this, ListaPedidos.class);
                startActivity(i4);
                return true;
            case R.id.action_crear_pedido:
                Intent i5 = new Intent(this, NuevoPedido.class);
                startActivity(i5);
                return true;
            case R.id.action_ver_pedidos_mapa:
                Intent i6 = new Intent(this, MapsActivity.class);
                i6.putExtra("selector", "home");
                startActivity(i6);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
