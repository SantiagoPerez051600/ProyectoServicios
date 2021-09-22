package com.example.proyectoservicios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class Servicio extends AppCompatActivity implements View.OnClickListener {
    Button btn_fecha;
    Button btn_hora;
    EditText txt_fecha,txt_hora;
    private int dia,mes,anio,hora,minuto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);
        btn_fecha=(Button) findViewById(R.id.btn_fecha);
        btn_hora=(Button) findViewById(R.id.btn_hora);
        txt_fecha= (EditText) findViewById(R.id.txt_fecha);
        txt_hora=(EditText) findViewById(R.id.txt_hora);

        btn_fecha.setOnClickListener( this);
        btn_hora.setOnClickListener(this);

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