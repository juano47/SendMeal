package frsf.isi.dam.delaiglesia.sendmeal.Dao;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.widget.TextView;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.delaiglesia.sendmeal.Dao.Rest.PlatoRest;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlatoRepository {

    public static String _SERVER = "http://192.168.42.253:5000/";
    private ArrayList<Plato> listaPlatosCompleta;
    private ArrayList<Plato> listaPlatosBusqueda;

    public static final int _ALTA_PLATO =1;
    public static final int _UPDATE_PLATO =2;
    public static final int _BORRADO_PLATO =3;
    public static final int _CONSULTA_PLATO =4; //TODOS LOS PLATOS
    public static final int _BUSQUEDA_PLATO =5; //búsqueda de platos en base a filtros
    public static final int _ERROR_PLATO =9;

    private static PlatoRepository _INSTANCE;

    private PlatoRepository(){}

    public static PlatoRepository getInstance(){
        if(_INSTANCE==null){
            _INSTANCE = new PlatoRepository();
            _INSTANCE.configurarRetrofit();
            _INSTANCE.listaPlatosCompleta = new ArrayList<>();
            _INSTANCE.listaPlatosBusqueda = new ArrayList<>();
        }
        return _INSTANCE;
    }

    private Retrofit rf;

    private PlatoRest platoRest;

    private void configurarRetrofit(){
        this.rf = new Retrofit.Builder()
                .baseUrl(_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("APP","INSTANCIA CREADA");

        this.platoRest = this.rf.create(PlatoRest.class);
    }

    public void crearPlato(Plato o, final Handler h){
        Call<Plato> llamada = this.platoRest.crear(o);
        llamada.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                Log.d("APP_2","Despues que ejecuta"+ response.isSuccessful());
                Log.d("APP_2","Codigo"+ response.code());

                if(response.isSuccessful()){
                    Log.d("APP_2","EJECUTO");
                    listaPlatosCompleta.add(response.body());
                    Message m = new Message();
                    m.arg1 = _ALTA_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_PLATO;
                h.sendMessage(m);
            }
        });

    }

    public void actualizarPlato(final Plato plato, final Handler h) {
        Call<Plato> llamada = this.platoRest.actualizar(plato.getId(),plato);
        llamada.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                Log.d("APP_2","Despues que ejecuta"+ response.isSuccessful());
                Log.d("APP_2","COdigo"+ response.code());

                if(response.isSuccessful()){
                    Log.d("APP_2","EJECUTO");
                    listaPlatosCompleta.remove(plato);
                    listaPlatosCompleta.add(response.body());
                    Message m = new Message();
                    m.arg1 = _UPDATE_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }

    public void listarPlatos(final Handler h) {
        Call<List<Plato>> llamada = this.platoRest.buscarTodas();
        llamada.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                if(response.isSuccessful()){
                    listaPlatosCompleta.clear();
                    listaPlatosCompleta.addAll(response.body());
                    Message m = new Message();
                    m.arg1 = _CONSULTA_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }

    public ArrayList<Plato> getListaPlatosCompleta() { return listaPlatosCompleta; }

    public ArrayList<Plato> getListaPlatosBusqueda() {
        return listaPlatosBusqueda;
    }

    public void buscarPlatos(String nombre, Double min, Double max, final Handler h) {
        Call<List<Plato>> llamada = this.platoRest.buscarTodas2(nombre, min, max);
        llamada.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                if(response.isSuccessful()){
                    listaPlatosBusqueda.clear();
                    listaPlatosBusqueda.addAll(response.body());
                    Message m = new Message();
                    m.arg1 = _BUSQUEDA_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }

    public void borrarPlato(final Plato o, final Handler h){
        Call<Void> llamada = this.platoRest.borrar(o.getId());
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("APP_2","Despues que ejecuta"+ response.isSuccessful());
                Log.d("APP_2","COdigo"+ response.code());

                if(response.isSuccessful()){
                    Log.d("APP_2","EJECUTO");
                    for(Plato o : listaPlatosCompleta){
                        Log.d("APP_2","Obra "+o.getId());
                    }
                    Log.d("APP_2","BORRA Obra "+o.getId());
                    listaPlatosCompleta.remove(o);
                    for(Plato o : listaPlatosCompleta){
                        Log.d("APP_2","Obra "+o.getId());
                    }
                    Message m = new Message();
                    m.arg1 = _BORRADO_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }
}
