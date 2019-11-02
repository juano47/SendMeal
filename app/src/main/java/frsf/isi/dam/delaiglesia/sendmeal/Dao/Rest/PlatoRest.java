package frsf.isi.dam.delaiglesia.sendmeal.Dao.Rest;

import java.util.List;

import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlatoRest {
    @GET("platos/")
    Call<List<Plato>> buscarTodas();

    @GET("platos/")
    Call<List<Plato>> buscarTodas2(@Query("titulo_like") String nombre,  @Query("precio_gte") double priceMin, @Query("precio_lte") double priceMax);


    @DELETE("platos/{id}")
    Call<Void> borrar(@Path("id") Integer id);

    @PUT("platos/{id}")
    Call<Plato> actualizar(@Path("id") Integer id, @Body Plato plato);

    @POST("platos/")
    Call<Plato  > crear(@Body Plato plato);
}
