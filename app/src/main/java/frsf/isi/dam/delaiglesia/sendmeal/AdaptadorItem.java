package frsf.isi.dam.delaiglesia.sendmeal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;

import static frsf.isi.dam.delaiglesia.sendmeal.Home._PLATOS;

public class AdaptadorItem extends RecyclerView.Adapter<AdaptadorItem.ItemViewHolder>{
    private static final int CODIGO_EDITAR_ITEM = 20;
    Context context;
    private CallbackInterface mCallback;

    public AdaptadorItem(List<Plato> productoItemList, Context context) {
        _PLATOS = productoItemList;
        this.context = context;

        // .. Attach the interface
        try{
            mCallback = (CallbackInterface) context;
        }catch(ClassCastException ex){
            //.. should log the error or throw and exception
            Log.e("MyAdapter","Must implement the CallbackInterface in the Activity", ex);
        }

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
        holder.txtPlatoNombre.setText(_PLATOS.get(position).getTitulo());
        holder.txtPlatoPrecio.setText(Double.toString(_PLATOS.get(position).getPrecio()));

    }

    //Método necesario
    @Override
    public int getItemCount() {
        if(_PLATOS != null)
            return _PLATOS.size();
        else
            return 0;
    }

    //interface que usa ListaItems para enviar el startActovitiyForResult a NuevoItem
    public interface CallbackInterface {
        //este metodo se sobreescribe en ListaItems y se usan los parametros que se le pasan para armar el Intent
        void onHandleSelection(int position, ArrayList<Plato> listaPlatos);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        //Defino las variables que van a referenciarse con los objetos en pantalla
        TextView txtPlatoNombre;
        TextView txtPlatoPrecio;
        Button buttonOferta;
        Button buttonEditar;
        Button buttonEliminar;


        public ItemViewHolder(View view) {
            super(view);
            //obtengo datos de la pantalla y los asigno a mis variables
            txtPlatoNombre=view.findViewById(R.id.textViewNombrePlato);
            txtPlatoPrecio = view.findViewById(R.id.textViewPrecioPlato);
            buttonOferta = view.findViewById(R.id.buttonOferta);
            buttonEditar = view.findViewById(R.id.buttonEditar);
            buttonEliminar = view.findViewById(R.id.buttonQuitar);



            buttonEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //obtengo la fila donde se clickeo el botón
                    Integer fila = getAdapterPosition();
                    //se llama a la función que se ejecutará en ListaItems para enviar el intent a NuevoItem
                    mCallback.onHandleSelection(fila, (ArrayList<Plato>) _PLATOS);


                }
            });

        }
    }
}