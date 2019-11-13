package frsf.isi.dam.delaiglesia.sendmeal.Dao.Rest;

import java.util.List;

import frsf.isi.dam.delaiglesia.sendmeal.domain.Pedido;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PedidoRest {
    @GET("pedidos/")
    Call<List<Pedido>> findAll();

    @DELETE("pedidos/{id}")
    Call<Void> borrar(@Path("id") Integer id);

    @PUT("pedidos/{id}")
    Call<Pedido> actualizar(@Path("id") Integer id, @Body Pedido pedido);

    @POST("pedidos/")
    Call<Pedido> crear(@Body Pedido pedido);
}
