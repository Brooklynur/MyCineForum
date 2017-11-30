package com.example.lore_depa.mycineforum;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import manager.CustomAdapter;
import manager.IoCrypto;
import manager.SASUser;

/*
    Una volta capito come estrarre i dati necessari racchiuderà una lista con gli utenti già presenti
    e si potranno vedere i dettagli dei loro profili
 */

public class MembersActivity extends AppCompatActivity {

    private DatabaseReference mDBReference;
    private ListView listViewSasUser;
    private List<SASUser> listSasUser;
    private CustomAdapter adapter;
    private FirebaseAuth mAuth;
    private IoCrypto enigma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        mAuth = FirebaseAuth.getInstance();

        listViewSasUser = (ListView) findViewById(R.id.SASList);
        listSasUser = new ArrayList<>();

        mDBReference = FirebaseDatabase.getInstance().getReference("user");

        enigma = new IoCrypto();

        String credenziali[] = getCredenziali().split(";");

        mAuth.signInWithEmailAndPassword(credenziali[0], credenziali[1]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MembersActivity.this , "Caricamento dati..." , Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MembersActivity.this , "Impossibile caricare" , Toast.LENGTH_LONG).show();
                }
            }
        });

        mDBReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SASUser currentUser = new SASUser();
                currentUser.setNick(dataSnapshot.getValue(SASUser.class).getNick());
                currentUser.setMail(dataSnapshot.getValue(SASUser.class).getMail());
                listSasUser.add(currentUser);
                if(listSasUser.size() > 0){
                    adapter = new CustomAdapter(getApplicationContext(), listSasUser);
                    listViewSasUser.setAdapter(adapter);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                SASUser currentUser = dataSnapshot.getValue(SASUser.class);
                listSasUser.add(currentUser);
                if(listSasUser.size() > 0){
                    adapter = new CustomAdapter(getApplicationContext(), listSasUser);
                    listViewSasUser.setAdapter(adapter);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                SASUser currentUser = dataSnapshot.getValue(SASUser.class);
                listSasUser.add(currentUser);
                if(listSasUser.size() > 0){
                    adapter = new CustomAdapter(getApplicationContext(), listSasUser);
                    listViewSasUser.setAdapter(adapter);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                SASUser currentUser = dataSnapshot.getValue(SASUser.class);
                listSasUser.add(currentUser);
                if(listSasUser.size() > 0){
                    adapter = new CustomAdapter(getApplicationContext(), listSasUser);
                    listViewSasUser.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getCredenziali(){
        String passwordValue="";
        String emailValue="";
        try {
            SharedPreferences getsharedPref = getSharedPreferences(getString(R.string.user), Context.MODE_PRIVATE);
            emailValue = enigma.decypt(getsharedPref.getString("email", ""));
            passwordValue = enigma.decypt(getsharedPref.getString("password", ""));
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
        return emailValue+";"+passwordValue;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
