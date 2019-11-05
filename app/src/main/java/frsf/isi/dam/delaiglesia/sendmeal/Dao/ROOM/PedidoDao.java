package frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


import frsf.isi.dam.delaiglesia.sendmeal.domain.Pedido;

@Dao
public interface PedidoDao {
    @Query("SELECT * FROM pedido")
    List<Pedido> getAll();

    @Insert
    void insert(Pedido pedido);

    @Insert
    void insertAll(Pedido... pedidos);

    @Delete
    void delete(Pedido pedido);

    @Update
    void actualizar(Pedido pedido);
}
