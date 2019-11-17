package frsf.isi.dam.delaiglesia.sendmeal;

import android.app.IntentService;
import android.content.Intent;

import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;

public class MyIntentService extends IntentService {

    public static final String ACTION_FIN = "frsf.isi.dam.delaiglesia.sendmeal.intent.action.FIN";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(10000);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Plato plato = (Plato) intent.getSerializableExtra("plato");

        Intent bcIntent = new Intent();
        bcIntent.setAction(ACTION_FIN);
        bcIntent.putExtra("plato", plato);
        sendBroadcast(bcIntent);
    }
}
