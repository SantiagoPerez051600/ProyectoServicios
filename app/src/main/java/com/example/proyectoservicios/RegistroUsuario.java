package com.example.proyectoservicios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistroUsuario extends AppCompatActivity {
    TextView nuevoUsuario, bienvenido, continuar;
    TextInputLayout correo,contraseña;
    //TextInputEditText nombre,correo1, telefono, direccion, cedula, contraseña1, confcontraseña;
    MaterialButton iniciosesion;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        imageView= findViewById(R.id.imagen);
        imageView.setColorFilter(Color.parseColor("#3E64FF"), PorterDuff.Mode.SRC_IN);


        bienvenido= findViewById(R.id.bienvenidoslabel);
        continuar=findViewById(R.id.continuarlabel);
        //nombre=findViewById(R.id.nombre1);
        correo=findViewById(R.id.CorreoUsuario);
        //correo1=findViewById(R.id.correo1);
        //telefono=findViewById(R.id.telefono1);
        //direccion=findViewById(R.id.direccion1);
        //cedula=findViewById(R.id.cedula1);
        //confcontraseña=findViewById(R.id.ConfContraseña1);
        contraseña=findViewById(R.id.ContraseñaUsuario);
        //contraseña1=findViewById(R.id.contraseña1);
        iniciosesion=findViewById(R.id.registrar);
        nuevoUsuario=findViewById(R.id.nuevoUsuario);

        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transBack();
            }
        });
        iniciosesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }
    public void validate(){
        //String email = correo1.getText().toString();
    }
    @Override
    public void onBackPressed(){
        transBack();
    }
    public void transBack(){
        Intent intent = new Intent(RegistroUsuario.this, loginActivity.class);
        Pair[] pairs= new Pair[7];
        pairs[0]= new Pair<View, String>(imageView, "logoImagenTrans");
        pairs[1]= new Pair<View, String>(bienvenido, "textTrans");
        pairs[2]= new Pair<View, String>(continuar, "continuarTrans");
        pairs[3]= new Pair<View, String>(correo , "correoTrans");
        pairs[4]= new Pair<View, String>(iniciosesion, "inicioTrans");
        pairs[5]= new Pair<View, String>(nuevoUsuario, "nuevoTrans");
        pairs[6]= new Pair<View, String>(contraseña, "contraseñaTrans");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
            startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
            finish();
        }


    }
}