package com.example.lore_depa.mycineforum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import manager.SASUser;

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
            //Toast.makeText(getApplicationContext(), user.getUid() , Toast.LENGTH_LONG).show();
        }

        final DatabaseReference mDBReference = FirebaseDatabase.getInstance().getReference("user");

        mDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SASUser currentUser = dataSnapshot.getValue(SASUser.class);
                Log.v("UTENTE: ", currentUser.getNick());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                //aprire pagina del sito che forse un giorno avremo.com
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
