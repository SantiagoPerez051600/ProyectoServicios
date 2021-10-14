package com.example.proyectoservicios;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class Notificaciones<context> {
    public final static String canal_id="NOTIFICACION";
    private final static int idNotificacion=0;
    private Context mContext;

    public Notificaciones(Context context) {

        mContext = context;
    }
    public void EnviarNotificacion(String fecha,String nombreServicio){
        createNotificationChannel();
        GenerarNotificacion(fecha,nombreServicio);

    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(canal_id, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager =(NotificationManager)mContext.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    public void GenerarNotificacion(String fecha,String nombreServicio){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext.getApplicationContext(), canal_id);
        builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
        builder.setContentTitle("SOLUCITUD DE AGENDAMIENTO");
        builder.setContentText("Usted realizo una solicitud de agendamiento para el servicio:"+nombreServicio+
                "\npara el dia:"+fecha);
        builder.setColor(Color.parseColor("#3E64FF"));
        builder.setPriority(Notification.PRIORITY_DEFAULT);
        builder.setVibrate(new long[]{1000});
        //builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mContext.getApplicationContext());
        notificationManagerCompat.notify(idNotificacion, builder.build());
    }

}
