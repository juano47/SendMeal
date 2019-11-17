package frsf.isi.dam.delaiglesia.sendmeal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.DBPedido;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.PedidoDao;
import frsf.isi.dam.delaiglesia.sendmeal.NuevoPedido.NuevoPedido;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Pedido;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences prefs = getSharedPreferences("TOKEN_PREF", MODE_PRIVATE);
        final String token = prefs.getString("token", "");

        Log.e("NEW_INACTIVITY_TOKEN", token);

        if (TextUtils.isEmpty(token)) {
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Home.this, new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String newToken = instanceIdResult.getToken();
                    Log.e("newToken", newToken);
                    SharedPreferences.Editor editor = getSharedPreferences("TOKEN_PREF", MODE_PRIVATE).edit();
                    if (token!=null){
                        editor.putString("token", newToken);
                        editor.apply();
                    }
                }
            });
        }
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

    //si la aplicación está en 2° plano, al enviar un mensaje push no se activa el método onMessageReceived y siempre se abre
    //esta actividad. Por lo que verificamos si en el intent hay extras con las keys "idPedido" y "nuevoEstado" .
    // y abrimos la actividad DetallePedido en caso afirmativo
    @Override
    protected void onResume() {
        super.onResume();
        final Pedido[] pedido = new Pedido[1];
        if (getIntent().getExtras() != null) {
            final Bundle b = getIntent().getExtras();
                final Intent i = new Intent(Home.this, DetallePedido.class);
                if(b.getString("idPedido")!=null&&b.getString("nuevoEstado")!=null) {

                    final Runnable hiloUpdateLista = new Runnable() {
                        @Override
                        public void run() {
                            //solo cambiamos el estado del pedido en memoria para mostrarlo, se debería actualizar el pedido en la DB local
                            //y en el servidor REST.
                            pedido[0].setEstado(Integer.valueOf(b.getString("nuevoEstado")));
                            i.putExtra("pedido", pedido[0]);
                            startActivity(i);
                        }
                    };

                    final Runnable cargarPedidos = new Runnable() {
                        @Override
                        public void run() {
                            PedidoDao dao = DBPedido.getInstance(Home.this).getSendMealDB().pedidoDao();
                            pedido[0] = dao.getPedido(Integer.valueOf(b.getString("idPedido")));
                            runOnUiThread(hiloUpdateLista);
                        }
                    };
                    Thread t1 = new Thread(cargarPedidos);
                    t1.start();
                }
        }
    }
}
