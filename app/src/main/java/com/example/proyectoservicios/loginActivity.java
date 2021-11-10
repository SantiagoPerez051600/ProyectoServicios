

package com.example.proyectoservicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;


public class loginActivity extends AppCompatActivity {
    ImageView imageView;
    TextView nuevoUsuario, bienvenido, continuar,olvidastecontra;
    TextInputLayout correo, contraseña;
    MaterialButton iniciosesion;
    TextInputEditText correoEdit, contraseñaEdit;
    public FirebaseAuth mAuth;


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
        correoEdit= findViewById(R.id.correoEdit);
        contraseñaEdit=findViewById(R.id.contraseñaEdit);
        olvidastecontra=findViewById(R.id.olvideContraseña);

        mAuth=FirebaseAuth.getInstance();
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

        iniciosesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        olvidastecontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent c = new Intent(loginActivity.this,Recuperarcontra.class);
                startActivity(c);
                finish();
            }
        });

    }

    public void validate(){
        String Semail = correoEdit.getText().toString();
        String Scontraseña = contraseñaEdit.getText().toString();

        if(Semail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Semail).matches()){
            correoEdit.setError("Correo Invalido");
            return;
        }else{
            correoEdit.setError(null);
        }

        if (Scontraseña.isEmpty()){
            contraseñaEdit.setError("Contraseña Invalida");
            return;
        } else if(Scontraseña.length() < 8){
            contraseñaEdit.setError("Se necesitan mas de 8 caracteres");
            return;
        }else if(!Pattern.compile("[0-9]").matcher(Scontraseña).find()){
            contraseñaEdit.setError("Almenos un numero");
            return;
        }else{
            contraseñaEdit.setError(null);
        }
       iniciarsesion(Semail, Scontraseña);

    }
    public void iniciarsesion(String Semail, String Scontraseña){
        mAuth.signInWithEmailAndPassword(Semail, Scontraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        if(task.isSuccessful()){
                            if(user.isEmailVerified()) {
                                if(user.getEmail().equals("santiago.grosso051600@gmail.com")){
                                    Intent intent = new Intent(loginActivity.this, pruebaAdmin.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Intent intent = new Intent(loginActivity.this, Listado.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }else{
                                Toast.makeText(loginActivity.this,  "Correo no autenticado", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(loginActivity.this,  "Credenciales Invalidas", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}