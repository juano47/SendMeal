package frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import frsf.isi.dam.delaiglesia.sendmeal.domain.ItemPedido;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Pedido;


@Database(entities = {Pedido.class, ItemPedido.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class SendMealDB extends RoomDatabase {
    public abstract PedidoDao pedidoDao();
    public abstract ItemPedidoDao itemPedidoDao();
}
