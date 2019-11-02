package frsf.isi.dam.delaiglesia.sendmeal.Auxiliares;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import frsf.isi.dam.delaiglesia.sendmeal.AdaptadorItem;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs,
                                   RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder != null){
            View foregroundView = ((AdaptadorItem.ItemViewHolder) viewHolder).layoutMostrar;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }


    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foregroundView = ((AdaptadorItem.ItemViewHolder) viewHolder).layoutMostrar;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        View foregroundView = ((AdaptadorItem.ItemViewHolder) viewHolder).layoutMostrar;
        View layoutEditar = ((AdaptadorItem.ItemViewHolder) viewHolder).layoutEditar;
        View layoutEliminar = ((AdaptadorItem.ItemViewHolder) viewHolder).layoutEliminar;

        if (dX>0){
            layoutEditar.setVisibility(View.VISIBLE);
            layoutEliminar.setVisibility(View.INVISIBLE);
        }
        else {
            layoutEliminar.setVisibility(View.VISIBLE);
            layoutEditar.setVisibility(View.INVISIBLE);
        }

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);

    }


    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwipe(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }


}
