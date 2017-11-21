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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth; //oggetto per le credenziali FireBase
    private FirebaseUser user;  //oggetto per l'utenza di firebase
    private TextView startText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*inizializzo le variabili firebase per poter verificare l'utenza o ofare chiamate al BD*/
        mAuth = FirebaseAuth.getInstance();
        user =  mAuth.getCurrentUser(); /*Se user risulta null vuol dire che un nuovo utente sta aprendo l'app o che è stato eseguito il logout*/

        startText = (TextView)findViewById(R.id.start);

        /*Comunico all'utente se la sua utenza risulta verificata*/
        if(user != null && !user.isEmailVerified()  ) {
            Toast.makeText(getApplicationContext(), "Email non verificata" , Toast.LENGTH_LONG).show(); //I Toast sono dei brevi messaggi che compaiono a schermo
        }else if( user != null && user.isEmailVerified() ){
            Toast.makeText(getApplicationContext(), "Email verificata" , Toast.LENGTH_LONG).show();
        }
    }

    public void switchMainActivity(View v){
        switch (v.getId()){ /*In base alla situazione dell'utente lo reindirizzo alla form necessaria*/
            case R.id.start:
                if(user != null && !user.isEmailVerified()){ //Se la mail non è verificata viene aperta la pagina di login
                    Intent intent = new Intent(this, LogInActivity.class );
                    startActivity(intent);
                }
                else if(user != null && user.isEmailVerified()){ //se la mail è verificata viene aperta la pagina dei membri
                    Intent intent = new Intent(this, MembersActivity.class );
                    startActivity(intent);
                }
                else{ //se nessuna delle precedenti è verificata viene aperta la pagina di iscrizione
                    Intent intent = new Intent(this, SignInActivity.class );
                    startActivity(intent);
                }
                break;
            case R.id.crediti:
                //aprire pagina del sito che forse un giorno avremo
                Toast.makeText(getApplicationContext(), "Coming soon", Toast.LENGTH_LONG ).show();
                break;
            case R.id.LogOutText: /*Se clicca sul testo di logout*/
                logOut();
        }
    }

    public void logOut(){ //Eseguo il logout
        mAuth.signOut();
        user = mAuth.getCurrentUser();
        Toast.makeText(this, "Logout eseguito" , Toast.LENGTH_SHORT).show();
        startText.setClickable(true);
    }
}
