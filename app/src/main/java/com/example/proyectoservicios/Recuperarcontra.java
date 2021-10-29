package com.example.proyectoservicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Recuperarcontra extends AppCompatActivity {

    MaterialButton Recuperar;
    TextInputEditText  correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperarcontra);

        Recuperar = findViewById(R.id.inicio);
        correo = findViewById(R.id.correoEdit);

        Recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

    }
    public void validate(){
        String cor= correo.getText().toString().trim();
        if (cor.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(cor).matches()){
            correo.setError("Correo Invalido");
            return;
        }
        enviarCorreo(cor);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent( Recuperarcontra.this, loginActivity.class);
        startActivity(intent);
        finish();
    }

    public void enviarCorreo(String cor){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email= cor;
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Recuperarcontra.this,"Correo enviado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Recuperarcontra.this, loginActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(Recuperarcontra.this,"Correo invalido", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}