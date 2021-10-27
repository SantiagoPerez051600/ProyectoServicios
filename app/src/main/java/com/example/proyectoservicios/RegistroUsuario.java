package com.example.proyectoservicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegistroUsuario extends AppCompatActivity {
    TextView nuevoUsuario, bienvenido, continuar;
    TextInputLayout correo,contraseña;
    TextInputEditText nombre,correo1, telefono, direccion, cedula, contraseña1, confcontraseña;
    MaterialButton iniciosesion;
    ImageView imageView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        imageView= findViewById(R.id.imagen);
        imageView.setColorFilter(Color.parseColor("#3E64FF"), PorterDuff.Mode.SRC_IN);


        bienvenido= findViewById(R.id.bienvenidoslabel);
        continuar=findViewById(R.id.continuarlabel);
        nombre=findViewById(R.id.nombre1);
        correo=findViewById(R.id.CorreoUsuario);
        correo1=findViewById(R.id.correo1);
        telefono=findViewById(R.id.telefono1);
        direccion=findViewById(R.id.direccion1);
        cedula=findViewById(R.id.cedula1);
        confcontraseña=findViewById(R.id.ConfContraseña1);
        contraseña=findViewById(R.id.ContraseñaUsuario);
        contraseña1=findViewById(R.id.contraseña1);
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
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }
    public void validate(){
        String Semail = correo1.getText().toString();
        String Snombre = nombre.getText().toString();
        String Stelefono = telefono.getText().toString();
        String Sdireccion = direccion.getText().toString();
        String Scedula = cedula.getText().toString();
        String Scontraseña = contraseña1.getText().toString();
        String Sconfcontraseña = confcontraseña.getText().toString();


        if(Semail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Semail).matches()){
            correo1.setError("Correo Invalido");
            return;
        }else{
            correo1.setError(null);
        }
        if (Snombre.isEmpty()){
            nombre.setError("Nombre Invalido");
            return;
        }
        if (Stelefono.isEmpty()){
            telefono.setError("Telefono Invalido");
            return;
        }
        if (Sdireccion.isEmpty()){
            direccion.setError("Direccion Invalida");
            return;
        }
        if (Scedula.isEmpty()){
            cedula.setError("Cedula Invalida");
            return;
        }
        if (Scontraseña.isEmpty()){
            contraseña1.setError("Contraseña Invalida");
            return;
        } else if(Scontraseña.length() < 8){
            contraseña1.setError("Se necesitan mas de 8 caracteres");
            return;
        }else if(!Pattern.compile("[0-9]").matcher(Scontraseña).find()){
            contraseña1.setError("Almenos un numero");
            return;
        }else{
            contraseña1.setError(null);
        }
        if (Sconfcontraseña.isEmpty()){
            confcontraseña.setError("Contraseña Invalida");
            return;
        }else if(!Sconfcontraseña.equals(Scontraseña)){
            confcontraseña.setError("Deben ser iguales");
            return;
        }else {
            registrar(Semail,Snombre, Stelefono, Sdireccion, Scedula, Scontraseña);
        }

    }
    public void registrar(String Semail, String Snombre, String Stelefono, String Sdireccion, String Scedula, String Scontraseña){

        mAuth.createUserWithEmailAndPassword(Semail, Scontraseña).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    Log.d("Registro", userID);
                    DocumentReference documentReference = fStore.collection("Users").document(userID);

                    Map<String,Object> userData = new HashMap<>();
                    userData.put("correo",Semail);
                    userData.put("nombre",Snombre);
                    userData.put("telefono",Stelefono);
                    userData.put("direccion",Sdireccion);
                    userData.put("cedula",Scedula);

                    documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("Registro", "llego");
                            Toast.makeText(RegistroUsuario.this , "Se registro correctamente", Toast.LENGTH_SHORT).show();
                            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification();
                            Intent intent = new Intent(RegistroUsuario.this, loginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistroUsuario.this , "Fallo en registrarse", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(RegistroUsuario.this , "Fallo en registrarse", Toast.LENGTH_SHORT).show();
                }
            }
        });
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