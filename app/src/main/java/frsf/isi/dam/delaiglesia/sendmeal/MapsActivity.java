package frsf.isi.dam.delaiglesia.sendmeal;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.DBPedido;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM.PedidoDao;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Pedido;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng latLng2;
    List<Pedido> listaPedidosDataset;
    Marker marker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //mover camara a Santa Fe y acercar
        LatLng santaFe = new LatLng(-31.6354434, -60.7063567);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(santaFe));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));

        Pedido pedido = (Pedido) getIntent().getSerializableExtra("pedido");
        String selector = getIntent().getStringExtra("selector");

        Button botonAceptarCoordenadas = (Button) findViewById(R.id.bottomAceptarCoordenadasEnMapa);

        //verificamos si se pidió el mapa desde el Alta de pedido
        if (pedido==null&&selector==null) {
            //permitimos agregar un marcador con click largo
            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    //borramos el marcador anterior en caso de que el usuario cambie la ubicación del marcador
                    if(marker!=null)
                        marker.remove();
                    //agregamos un marcador donde el usuario haga un click prolongado
                    marker = mMap.addMarker(new MarkerOptions().position(latLng));
                    latLng2 = latLng;

                }
            });
        }

        //si se llamó la actividad desde Detalle del pedido:
        //no permitimos agregar marcadores
        else if (selector==null){
            //ocultamos el boton de Aceptar coordenadas
           botonAceptarCoordenadas.setVisibility(View.GONE);
           //agregamos un marcador en la ubicación guardada en el pedido
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(pedido.getLat(), pedido.getLng()))
                    .title(pedido.getId().toString()));
        }

        //si se llamó la actividad desde "Ver pedidos en mapa":
        else {
            //ocultamos el boton de Aceptar coordenadas
            botonAceptarCoordenadas.setVisibility(View.GONE);
            //obtenemos todos los pedidos
            final Runnable hiloUpdateLista = new Runnable() {
                @Override
                public void run() {
                    //mostramos un marcador por pedido
                    for (int i = 0; i < listaPedidosDataset.size(); i++) {
                        //seteamos distintos colores en base al estado del pedido
                        switch (listaPedidosDataset.get(i).getEstado()){
                            case 1: //Estado: Pendiente

                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(listaPedidosDataset.get(i).getLat(), listaPedidosDataset.get(i).getLng()))
                                        .title	(listaPedidosDataset.get(i).getId().toString())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));;
                                break;
                            case 2: //Estado: Enviado
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(listaPedidosDataset.get(i).getLat(), listaPedidosDataset.get(i).getLng()))
                                        .title	(listaPedidosDataset.get(i).getId().toString())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));;
                                break;
                        }
                        //mostramos un marcador por pedido
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(listaPedidosDataset.get(i).getLat(), listaPedidosDataset.get(i).getLng()))
                                .title	(listaPedidosDataset.get(i).getId().toString())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));;
                    }
                }
            };
            final Runnable cargarPedidos = new Runnable() {
                @Override
                public void run() {
                    PedidoDao dao = DBPedido.getInstance(MapsActivity.this).getSendMealDB().pedidoDao();
                    listaPedidosDataset = dao.getAll();
                    runOnUiThread(hiloUpdateLista);
                }
            };
            Thread t1 = new Thread(cargarPedidos);
            t1.start();
        }


        botonAceptarCoordenadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latLng2==null){
                    Toast.makeText(MapsActivity.this, "Selecciona la ubicación del pedido presionando un lugar en el mapa", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intentResultado = new Intent();
                    intentResultado.putExtra("latitud", latLng2.latitude);
                    intentResultado.putExtra("longitud", latLng2.longitude);
                    setResult(Activity.RESULT_OK, intentResultado);
                    finish();
                }
            }
        });


    }
}