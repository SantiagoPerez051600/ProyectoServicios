package com.example.proyectoservicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class Servicio extends AppCompatActivity implements View.OnClickListener {
    Button btn_fecha;
    Button btn_hora;
    Button btn_agendar;
    EditText txt_fecha,txt_hora;
    TextView tv_precio,tv_descripcion;
    private int dia,mes,anio,hora,minuto;
    private DatabaseReference Database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);
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
        Bundle parametros = this.getIntent().getExtras();
        String key = parametros.getString("key");
        Database.child("servicios").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    tv_precio.setText(snapshot.child("precio").getValue().toString());
                    tv_descripcion.setText(snapshot.child("Descripcion").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Se agendo su cita para " + txt_fecha.getText().toString(), Toast.LENGTH_SHORT);

                toast1.show();
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
                    txt_fecha.setText(dayOfYear+"/"+monthOfYear+"/"+year);
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