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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class loginActivity extends AppCompatActivity {
     ImageView imageView;
    TextView nuevoUsuario, bienvenido, continuar;
    TextInputLayout correo, contraseña;
    MaterialButton iniciosesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageView= findViewById(R.id.imagen);
        imageView.setColorFilter(Color.parseColor("#3E64FF"), PorterDuff.Mode.SRC_IN);
        bienvenido= findViewById(R.id.bienvenido);
        continuar=findViewById(R.id.continuar);
        correo=findViewById(R.id.usuario);
        contraseña=findViewById(R.id.contraseña);
        iniciosesion=findViewById(R.id.inicio);
        nuevoUsuario=findViewById(R.id.nuevo);
        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, RegistroUsuario.class);
                Pair[] pairs= new Pair[7];
                pairs[0]= new Pair<View, String>(imageView, "logoImagenTrans");
                pairs[1]= new Pair<View, String>(bienvenido, "textTrans");
                pairs[2]= new Pair<View, String>(continuar, "continuarTrans");
                pairs[3]= new Pair<View, String>(correo , "correoTrans");
                pairs[4]= new Pair<View, String>(iniciosesion, "inicioTrans");
                pairs[5]= new Pair<View, String>(nuevoUsuario, "nuevoTrans");
                pairs[6]= new Pair<View, String>(contraseña, "contraseñaTrans");

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(loginActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                }else{
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}