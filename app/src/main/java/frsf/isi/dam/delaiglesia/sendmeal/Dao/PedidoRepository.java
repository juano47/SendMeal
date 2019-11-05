package frsf.isi.dam.delaiglesia.sendmeal.Dao;

import java.util.ArrayList;

import frsf.isi.dam.delaiglesia.sendmeal.domain.Pedido;

public class PedidoRepository {

    private static PedidoRepository _INSTANCE;

    //private PedidoRepository(){}

    public static PedidoRepository getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new PedidoRepository();
        }
        return _INSTANCE;
    }


}
