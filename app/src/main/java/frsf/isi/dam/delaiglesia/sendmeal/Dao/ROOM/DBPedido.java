package frsf.isi.dam.delaiglesia.sendmeal.Dao.ROOM;

import android.content.Context;

import androidx.room.Room;

public class DBPedido {
    private static DBPedido DB = null;

    private SendMealDB sendMealDB;

    private DBPedido(Context ctx){
        sendMealDB = Room.databaseBuilder(ctx,
                SendMealDB.class, "sendmeal-db").allowMainThreadQueries().build();
    }

    public synchronized static DBPedido getInstance(Context ctx){
        if(DB==null){
            DB = new DBPedido(ctx);
        }
        return DB;
    }

    public SendMealDB getSendMealDB() {
        return sendMealDB;
    }
}
