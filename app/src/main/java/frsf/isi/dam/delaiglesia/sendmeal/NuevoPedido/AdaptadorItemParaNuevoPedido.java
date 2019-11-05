package frsf.isi.dam.delaiglesia.sendmeal.NuevoPedido;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.delaiglesia.sendmeal.AdaptadorItem;
import frsf.isi.dam.delaiglesia.sendmeal.ListaBusqueda;
import frsf.isi.dam.delaiglesia.sendmeal.R;
import frsf.isi.dam.delaiglesia.sendmeal.domain.ItemPedido;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;

public class AdaptadorItemParaNuevoPedido  extends  RecyclerView.Adapter<AdaptadorItemParaNuevoPedido.ItemViewHolder>{
    Context context;
    private List<Plato> platos;
    private String[] listaCantidades;


    public AdaptadorItemParaNuevoPedido(List<Plato> lista, Context context) {
        platos = lista;
        this.context = context;

    }

    public AdaptadorItemParaNuevoPedido(List<Plato> lista, String[] listaCantidades, Context context) {
        platos = lista;
        this.context = context;
        this.listaCantidades = listaCantidades;
    }

    @Override
    public AdaptadorItemParaNuevoPedido.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View productoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item_para_nuevo_pedido, parent, false);

        AdaptadorItemParaNuevoPedido.ItemViewHolder gvh = new AdaptadorItemParaNuevoPedido.ItemViewHolder(productoView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(AdaptadorItemParaNuevoPedido.ItemViewHolder holder, final int position) {
        holder.txtPlatoNombre.setText(platos.get(position).getTitulo());
        holder.txtPlatoPrecio.setText(Double.toString(platos.get(position).getPrecio()));

      if (listaCantidades!=null) {
            for (int i = 0; i < listaCantidades.length; i++) {
                Log.e(String.valueOf(i), listaCantidades[i].toString());
                if (listaCantidades[i]!="0") {
                        holder.editTextCantidadPlatos.setText(listaCantidades[position]);
                }
            }
      }

    }

    //Método necesario
    @Override
    public int getItemCount() {
        if(platos != null)
            return platos.size();
        else
            return 0;
    }

    public void setTamanioListaCantidades(int size) {
        this.listaCantidades = new String[size];
        //inicializo listaCantidades en cero, para que cuando se pida y el usuario no halla completado nada, no de null
        for (int i=0; i<listaCantidades.length; i++)
            listaCantidades[i] = "0";
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        //Defino las variables que van a referenciarse con los objetos en pantalla
        TextView txtPlatoNombre;
        TextView txtPlatoPrecio;
        EditText editTextCantidadPlatos;

        public ItemViewHolder(View view) {
            super(view);
            //obtengo datos de la pantalla y los asigno a mis variables
            txtPlatoNombre=view.findViewById(R.id.textViewNombrePlato);
            txtPlatoPrecio = view.findViewById(R.id.textViewPrecioPlato);
            editTextCantidadPlatos = view.findViewById(R.id.editTextCantidadPlatos);





            editTextCantidadPlatos.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //guardo la cantidad que escriba en cada posicion del array listaCantidades, conociendo la posicion
                    //donde escribió con getAdapterPosition()
                    if(charSequence.toString()!="")
                        listaCantidades[getAdapterPosition()] = charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
/*
    public ArrayList<ItemPedido> getItemsPedido() {
        return itemsPedido;
    }*/

    public String[] getListaCantidades() {
        return listaCantidades;
    }
}
