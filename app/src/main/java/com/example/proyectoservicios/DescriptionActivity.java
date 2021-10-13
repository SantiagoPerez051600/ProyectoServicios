package com.example.proyectoservicios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.proyectoservicios.models.Servicios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


import android.os.Bundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_fecha;
    Button btn_hora;
    Button btn_agendar;
    EditText txt_fecha,txt_hora;
    TextView tv_precio,tv_descripcion;
    private int dia,mes,anio,hora,minuto;
    private DatabaseReference Database;
    String correo1="santiago.grosso051600@gmail.com";
    String contraseña2="tatiana051600";
    Session session;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        btn_fecha=(Button) findViewById(R.id.btn_fecha);
        btn_hora=(Button) findViewById(R.id.btn_hora);
        btn_agendar=(Button) findViewById(R.id.btn_agendar);
        txt_fecha= (EditText) findViewById(R.id.txt_fecha);
        txt_hora=(EditText) findViewById(R.id.txt_hora);
        tv_precio=(TextView) findViewById(R.id.tv_precio);
        tv_descripcion=(TextView) findViewById(R.id.tv_descripcion);
        Database = FirebaseDatabase.getInstance().getReference();
        btn_fecha.setOnClickListener( this);
        btn_hora.setOnClickListener(this);
        imageButton= findViewById(R.id.whatsap);
        imageButton.setColorFilter(Color.parseColor("#3E64FF"), PorterDuff.Mode.SRC_IN);

        Servicios servicio1 = (Servicios) getIntent().getSerializableExtra("Servicio");


        tv_precio.setText(servicio1.getPrecio().toString());
        tv_descripcion.setText(servicio1.getDescripcion().toString());


        btn_agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast1 = Toast.makeText(getApplicationContext(), "Se agendo su cita para " + txt_fecha.getText().toString(), Toast.LENGTH_SHORT);
                toast1.show();
                enviarCorreo();

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _url ="https://wa.me/573197467815?text=Hola%20necesito%20informacion";
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

    public void enviarCorreo(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties p = new Properties();
        p.put("mail.smtp.host","smtp.googlemail.com");
        p.put("mail.smtp.socketFactory.port","465");
        p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.auth","true");
        p.put("mail.smtp.port","465");

        try {
            session=Session.getDefaultInstance(p,new Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(correo1,contraseña2);
                }
            });
            if(session!= null){
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correo1));
                message.setSubject("Solucitud De Agendamiento");
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("zebaz1999@outlook.es"));
                message.setContent("aqui va el mensaje","text/html; charset=utf-8");
                Transport.send(message);

            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }


}