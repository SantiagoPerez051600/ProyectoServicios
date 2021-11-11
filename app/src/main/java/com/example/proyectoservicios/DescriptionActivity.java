package com.example.proyectoservicios;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.proyectoservicios.models.Servicios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Calendar;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_fecha,btn_hora,btn_agendar;
    EditText txt_fecha,txt_hora;
    TextView tv_precio,tv_descripcion, tv_nombre;
    ImageView imagenServicio;
    public int dia,mes,anio,hora,minuto;
    public int diaV,mesV,anioV,horaV;
    public DatabaseReference Database;
    public Context c=DescriptionActivity.this;
    EnvioDeCorreos ec = new EnvioDeCorreos();
    String fecha,nombreServicio,CorreoUsuario;
    Notificaciones n = new Notificaciones(c);
    ImageButton imageButton;
    Agendar agendar = new Agendar();
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
        tv_nombre = findViewById(R.id.tv_nombre);
        imagenServicio = findViewById(R.id.iw_imagenServicio);
        Database = FirebaseDatabase.getInstance().getReference();
        btn_fecha.setOnClickListener(this);
        btn_hora.setOnClickListener(this);
        imageButton= findViewById(R.id.whatsapp);
        imageButton.setColorFilter(Color.parseColor("#3E64FF"), PorterDuff.Mode.SRC_IN);
        Servicios servicio1 = (Servicios) getIntent().getSerializableExtra("Servicio");
        tv_precio.setText(servicio1.getPrecio());
        tv_descripcion.setText(servicio1.getDescripcion());
        tv_nombre.setText(servicio1.getNombre().toUpperCase());
        Glide.with(this).load(servicio1.getURLfoto()).centerCrop().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(imagenServicio);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        btn_agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(validarFechar(diaV,mesV,anioV)==true && !txt_hora.getText().toString().isEmpty()){

                        Toast toast1 = Toast.makeText(getApplicationContext(), "Se agendo su cita para " + txt_fecha.getText().toString(), Toast.LENGTH_SHORT);
                        toast1.show();
                        fecha = txt_fecha.getText().toString();
                        fecha= fecha+"-"+txt_hora.getText().toString();
                        nombreServicio=servicio1.getNombre().toString();
                        CorreoUsuario=user.getEmail().toString();
                        ec.enviarCorreo(fecha,nombreServicio,CorreoUsuario);
                        n.EnviarNotificacion(fecha,nombreServicio);
                        fecha = txt_fecha.getText().toString();
                        agendar.crearCita(fecha,txt_hora.getText().toString(),servicio1.getNombre(),user.getEmail());
                        txt_fecha.setText("");
                        txt_hora.setText("");
                    }else{
                        Toast toast1 = Toast.makeText(getApplicationContext(), "Verifique la fecha por favor", Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                }catch (Exception e){
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Verifique los campos", Toast.LENGTH_SHORT);
                    toast1.show();
                }
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
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,new  DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfYear){
                    txt_fecha.setText(dayOfYear+"/"+(monthOfYear+1)+"/"+year);
                    diaV = dayOfYear;
                    mesV = monthOfYear+1;
                    anioV=year;
                }
            },anio,mes,dia);
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

    public boolean validarFechar(int dia,int mes,int año){
        boolean bandera = true;
        Time hoy = new Time(Time.getCurrentTimezone());
        hoy.setToNow();
        int diaA = hoy.monthDay;
        int mesA = hoy.month;
        int añoA = hoy.year;
        mesA = mesA+1;

        if(año==añoA){
            if(mes==mesA){
                if(dia>diaA){
                    bandera = true;
                }else{
                    bandera = false;
                }
            }else if(mes<mesA){
                bandera = false;
            }else if(mes>mesA){
                bandera = true;
            }
        }else if(año>añoA){
            bandera = true;
        }else if(año<añoA){
            bandera = false;
        }
    return bandera;
    }


}