package frsf.isi.dam.delaiglesia.sendmeal;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

import frsf.isi.dam.delaiglesia.sendmeal.NuevoPedido.NuevoPedido;

public class Home extends AppCompatActivity {

    private static final int CODIGO_CREAR_ITEM = 10;
    private static final int CODIGO_CREAR_PEDIDO = 20;

   //public static List<Plato> _PLATOS = new ArrayList<>();

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
                startActivityForResult(i2,CODIGO_CREAR_ITEM);
                return true;
            case R.id.action_ver_lista:
                Intent i3 = new Intent(this, ListaItems.class);
                startActivity(i3);
                return true;
            case R.id.action_crear_pedido:
                Intent i4 = new Intent(this, NuevoPedido.class);
                startActivityForResult(i4,CODIGO_CREAR_PEDIDO);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
