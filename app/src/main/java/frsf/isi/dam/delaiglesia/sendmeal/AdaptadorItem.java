package frsf.isi.dam.delaiglesia.sendmeal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;

public class AdaptadorItem extends RecyclerView.Adapter<AdaptadorItem.ItemViewHolder>{
    private List<Plato> platoItemList;
    Context context;

    public AdaptadorItem(List<Plato> productoItemList, Context context) {
        this.platoItemList = productoItemList;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View productoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item, parent, false);
        ItemViewHolder gvh = new ItemViewHolder(productoView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.txtPlatoNombre.setText(platoItemList.get(position).getTitulo());
        holder.txtPlatoPrecio.setText(Double.toString(platoItemList.get(position).getPrecio()));

    }

    //Método necesario
    @Override
    public int getItemCount() {
        if(platoItemList != null)
            return platoItemList.size();
        else
            return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        //Referencio punteros a los elementos en pantalla
        TextView txtPlatoNombre;
        TextView txtPlatoPrecio;


        public ItemViewHolder(View view) {
            super(view);
            //obtengo datos de la pantalla y los asigno a mis variables
            txtPlatoNombre=view.findViewById(R.id.textViewNombrePlato);
            txtPlatoPrecio = view.findViewById(R.id.textViewPrecioPlato);
        }
    }
}