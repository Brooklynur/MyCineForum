package com.example.lore_depa.mycineforum;

import android.content.Intent;
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

import manager.IoCrypto;
import manager.MyFirebaseManager;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView nickName;
    private TextView passwordText;
    private TextView mailText;
    private MyFirebaseManager FBMan;
    private FirebaseUser firebaseUser;
    private IoCrypto enigma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        FBMan = new MyFirebaseManager(); // nostro manager per i metodi legati al nostro DB
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        /*TextView con i campi necessari all'iscrizione*/
        nickName = (TextView) findViewById(R.id.UserText);
        passwordText = (TextView) findViewById(R.id.PasswordText);
        mailText = (TextView) findViewById(R.id.MailText);

        enigma = new IoCrypto();

    }


    //Metodo richiamato alla pressione del bottone di submit
    public void createUser(View v){
        if(!FBMan.checkCredenziali(mailText, passwordText, nickName)){ //checcka se le TextView sono vuote
            Toast.makeText( SignInActivity.this, "Login fallito: inserire campi richiesti", Toast.LENGTH_LONG).show();
            return;
        }

        final String  email = mailText.getText().toString();
        final String password = passwordText.getText().toString();
        final String nick = nickName.getText().toString();

        /*Questo metodo crea l'utenza vera e propria*/
        mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {//Listner sul completamento dell'operazione di iscrizione
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){//verifico se l'iscrizione è andata a buon fine, scrivo i dati sul DB e comunico all'utente la creazione della sua utenza
                    //Comunico la creazione dell'utenza
                    Toast.makeText( SignInActivity.this, "Utenza creata", Toast.LENGTH_SHORT).show();
                    //salvo le informazioni necessarie
                    firebaseUser = mAuth.getCurrentUser();
                    FBMan.insertUserCredential(firebaseUser, nick, email);
                    //invio email di verifica
                    firebaseUser.sendEmailVerification();
                    //parte l'activity successiva
                    Intent intent = new Intent(SignInActivity.this , MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText( SignInActivity.this, "Login fallito", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void alreadySub(View v){ //apro la formo per il Login in caso alla pressione dell scritta "Hai gia un account?"
        Intent intent = new Intent(this , LogInActivity.class );
        startActivity(intent);
        finish();
    }

}
