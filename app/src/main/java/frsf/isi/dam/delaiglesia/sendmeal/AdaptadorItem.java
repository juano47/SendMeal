package frsf.isi.dam.delaiglesia.sendmeal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

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

        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View productoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item, parent, false);

        //definir la altura del layoutEliminar igual a la del layoutMostrar, teniendo en cuenta que el layoutMostrar puede cambiar su medida
        //si se cambia su diseño
        //**************************
        ConstraintLayout layoutMostrar = productoView.findViewById(R.id.idLayoutMostrar);
        // Gets the layout params that will allow you to resize the layout
        int alturaLayoutMostrar = layoutMostrar.getLayoutParams().height;

        ConstraintLayout layoutEliminar = productoView.findViewById(R.id.idLayoutEliminar);
        layoutEliminar.setMinHeight(alturaLayoutMostrar);
        layoutEliminar.requestLayout();
        //***************************

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

        ConstraintLayout layoutMostrar;
        ConstraintLayout layoutEditar;
        ConstraintLayout layoutEliminar;

        public ItemViewHolder(View view) {
            super(view);
            //obtengo datos de la pantalla y los asigno a mis variables
            txtPlatoNombre=view.findViewById(R.id.textViewNombrePlato);
            txtPlatoPrecio = view.findViewById(R.id.textViewPrecioPlato);
            buttonOferta = view.findViewById(R.id.buttonOferta);
            layoutMostrar = view.findViewById(R.id.idLayoutMostrar);
            layoutEditar = view.findViewById(R.id.idLayoutEditar);
            layoutEliminar = view.findViewById(R.id.idLayoutEliminar);

            buttonOferta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //cambiamos la bandera enOferta
                    _PLATOS.get(getAdapterPosition()).setEnOferta(true);

                    //enviamos un intent con el plato a un hilo de tipo IntentService
                    Intent nuevoServicio = new Intent(context, MyIntentService.class);
                    Plato plato = _PLATOS.get(getAdapterPosition());
                    nuevoServicio.putExtra("plato", plato);
                    context.startService(nuevoServicio);
                }
            });

        }
    }

    public void editarPlato(int fila){
        //se llama a la función que se ejecutará en ListaItems para enviar el intent a NuevoItem
        mCallback.onHandleSelection(fila, (ArrayList<Plato>) _PLATOS);
        notifyItemChanged(fila);
    }

    public void eliminarPlato(final int fila) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Realmente desea eliminar el plato?")
                .setTitle("Quitar plato")
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlgInt, int i) {
                                _PLATOS.remove(fila);
                                notifyItemRemoved(fila);
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dlgInt, int i) {
                            notifyItemChanged(fila);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}