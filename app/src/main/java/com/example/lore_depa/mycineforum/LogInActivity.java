package com.example.lore_depa.mycineforum;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView emailText;
    private TextView passwordText;
    private MyFirebaseManager FBMan;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        FBMan = new MyFirebaseManager();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        emailText = (TextView) findViewById(R.id.MailTextLogIn);
        passwordText = (TextView) findViewById(R.id.PasswordTextLogIn);
    }

    public void logIn(View v){
        if(!FBMan.checkCredenziali(emailText, passwordText)){
            Toast.makeText(this, "Inserire i campi richiesti", Toast.LENGTH_LONG );
            return;
        }

        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();

        if(user.isEmailVerified()){ //se l'email è verificata procedo con la login
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task){
                    if(task.isSuccessful()){
                        Toast.makeText( LogInActivity.this , "Login effettuato", Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(LogInActivity.this , MembersActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LogInActivity.this, "L'utenza non esiste", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Toast.makeText(this, "Verifica la mail", Toast.LENGTH_LONG).show();
        }

    }
}
