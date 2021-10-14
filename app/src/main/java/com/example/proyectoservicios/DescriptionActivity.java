package com.example.proyectoservicios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.proyectoservicios.models.Servicios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_fecha,btn_hora,btn_agendar;
    EditText txt_fecha,txt_hora;
    TextView tv_precio,tv_descripcion;
    public int dia,mes,anio,hora,minuto;
    public DatabaseReference Database;
    public Context c=DescriptionActivity.this;
    EnvioDeCorreos ec = new EnvioDeCorreos();
    String fecha,nombreServicio,CorreoUsuario;
    Notificaciones n = new Notificaciones(c);
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        btn_fecha=findViewById(R.id.btn_fecha);
        btn_hora= findViewById(R.id.btn_hora);
        btn_agendar= findViewById(R.id.btn_agendar);
        txt_fecha= findViewById(R.id.txt_fecha);
        txt_hora=findViewById(R.id.txt_hora);
        tv_precio= findViewById(R.id.tv_precio);
        tv_descripcion=findViewById(R.id.tv_descripcion);
        Database = FirebaseDatabase.getInstance().getReference();
        btn_fecha.setOnClickListener( this);
        btn_hora.setOnClickListener(this);
        imageButton= findViewById(R.id.whatsap);
        imageButton.setColorFilter(Color.parseColor("#3E64FF"), PorterDuff.Mode.SRC_IN);


        Servicios servicio1 = (Servicios) getIntent().getSerializableExtra("Servicio");
        tv_precio.setText(servicio1.getPrecio().toString());
        tv_descripcion.setText(servicio1.getDescripcion().toString());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        btn_agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast1 = Toast.makeText(getApplicationContext(), "Se agendo su cita para " + txt_fecha.getText().toString(), Toast.LENGTH_SHORT);
                toast1.show();
                fecha = txt_fecha.getText().toString();
                fecha= fecha+"-"+txt_hora.getText().toString();
                nombreServicio=servicio1.getNombre().toString();
                CorreoUsuario=user.getEmail().toString();

                ec.enviarCorreo(fecha,nombreServicio,CorreoUsuario);
                n.EnviarNotificacion(fecha,nombreServicio);


            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _url ="https://wa.me/573232732116?text=Hola%20necesito%20informacion";
                Uri _link = Uri.parse(_url);
                Intent i = new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v==btn_fecha){
            final Calendar C = Calendar.getInstance();
            dia=C.get(Calendar.DAY_OF_MONTH);
            mes=C.get(Calendar.MONTH);
            anio=C.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,new  DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfYear){
                    txt_fecha.setText(dayOfYear+"/"+(monthOfYear+1)+"/"+year);
                }
            },dia,mes,anio);
            datePickerDialog.show();
        }
        if(v==btn_hora){
            final Calendar C = Calendar.getInstance();
            hora = C.get(Calendar.HOUR_OF_DAY);
            minuto = C.get(Calendar.MINUTE);

            TimePickerDialog tp = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int HourOfDay, int minute) {
                    txt_hora.setText(HourOfDay+":"+minute);
                }
            },hora,minuto,false);
            tp.show();
        }
    }

}