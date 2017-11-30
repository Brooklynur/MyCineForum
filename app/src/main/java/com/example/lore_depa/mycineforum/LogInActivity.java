package com.example.lore_depa.mycineforum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import manager.IoCrypto;
import manager.MyFirebaseManager;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView emailText;
    private TextView passwordText;
    private MyFirebaseManager FBMan;
    private FirebaseUser user;
    private IoCrypto enigma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        FBMan = new MyFirebaseManager();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        emailText = (TextView) findViewById(R.id.MailTextLogIn);
        passwordText = (TextView) findViewById(R.id.PasswordTextLogIn);

        enigma = new IoCrypto();
    }

    public void logIn(View v){
        if(!FBMan.checkCredenziali(emailText, passwordText)){
            Toast.makeText(this, "Inserire i campi richiesti", Toast.LENGTH_LONG );
            return;
        }

        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){
                    user = mAuth.getCurrentUser();
                    if(user.isEmailVerified()) {
                        salvaCredenziali(email, password);
                        Toast.makeText(LogInActivity.this, "Login effettuato", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LogInActivity.this, MembersActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LogInActivity.this, "Verifica la mail", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(LogInActivity.this, "L'utenza non esiste", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void salvaCredenziali(String email, String password){
        try {
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.user), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("email", enigma.encrypt(email));
            editor.putString("password", enigma.encrypt(password));
            editor.apply();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }
}
