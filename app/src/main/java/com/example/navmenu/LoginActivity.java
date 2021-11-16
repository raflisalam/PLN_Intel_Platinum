package com.example.navmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navmenu.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnLogin;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (email.isEmpty()) {
            inputEmail.setError("Masukkan email anda");
            inputEmail.requestFocus();
            return;
        }

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Masukkan email yang valid!");
            inputEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            inputPassword.setError("Masukkan password anda");
            inputPassword.requestFocus();
            return;
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("hehe", "error" + task);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "Login anda gagal! Tolong masukkan email dan password yang benar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//    @Override
//    public void onClick(View view) {
//
//        String strUsername = inputUsername.getText().toString();
//        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
//        mainIntent.putExtra("username", strUsername);
//        startActivity(mainIntent);
//
//
//    }
}