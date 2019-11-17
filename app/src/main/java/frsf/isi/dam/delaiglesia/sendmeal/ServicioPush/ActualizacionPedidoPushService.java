package frsf.isi.dam.delaiglesia.sendmeal.ServicioPush;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import frsf.isi.dam.delaiglesia.sendmeal.Home;
import frsf.isi.dam.delaiglesia.sendmeal.R;

public class ActualizacionPedidoPushService extends FirebaseMessagingService {

    public static int BUSCAR_Y_EDITAR = 5;
    public ActualizacionPedidoPushService() {
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

// el mensaje envia el ID del pedido y el ID del nuevo estado.
// lo que debera hacer es generar una notificacion indicando de dicha actualizacion
// y cuando dicha notificacion se abre, se muestra el detalle del pedido




        Intent destino = new Intent(ActualizacionPedidoPushService.this, Home.class); //enviamos el intent para que se encargue de
                                                                                                    //buscar el pedido en la DB y recien ahi abrir
                                                                                                    //la actividad DetallePedido
        destino.putExtra("idPedido", remoteMessage.getData().get("idPedido"));
        destino.putExtra("nuevoEstado", remoteMessage.getData().get("nuevoEstado"));
        destino.setAction("0"); //necesario accion ficticia para que se envien los datos agregados en el intent (plato)

        int dummyuniqueInt = new Random().nextInt(543254);
        //al pendingIntent le pasamos un numero aleatorio para que interprete cada PendingIntent como uno distinto y no envie los mismos
        //extras (mismo plato) al enviar varias notificaciones ya que no interpreta el cambio en los extras como un intent distinto y siempre
        //devuelve un puntero al primer intent enviado
        PendingIntent pendingIntent =
                PendingIntent.getActivity(ActualizacionPedidoPushService.this,dummyuniqueInt , destino, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ActualizacionPedidoPushService.this, Notification.CATEGORY_PROMO)
                        .setSmallIcon(R.drawable.ic_sentiment_very_satisfied)
                        .setContentTitle("SendMeal")
                        .setContentText("El pedido " + remoteMessage.getData().get("idPedido") + " cambió al estado " + remoteMessage.getData().get("nuevoEstado"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(ActualizacionPedidoPushService.this);
        notificationManager.notify(new Random().nextInt(543254), mBuilder.build());

    }


}
