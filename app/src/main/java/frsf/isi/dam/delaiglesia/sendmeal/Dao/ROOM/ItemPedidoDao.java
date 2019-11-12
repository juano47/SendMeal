package frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM;

import android.content.Intent;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import frsf.isi.dam.delaiglesia.sendmeal.domain.ItemPedido;

@Dao
public interface ItemPedidoDao {
    @Query("SELECT * FROM itemPedido")
    List<ItemPedido> getAll();

    @Query("SELECT * FROM itemPedido WHERE idPedido = :idPedido")
    List<ItemPedido> getAll(Integer idPedido);

    @Insert
    void insert(ItemPedido itemPedido);

    @Insert
    void insertAll(ItemPedido... itemPedidos);

    @Delete
    void delete(ItemPedido itemPedido);

    @Update
    void actualizar(ItemPedido itemPedido);
}
