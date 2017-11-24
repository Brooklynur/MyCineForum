package com.example.lore_depa.mycineforum;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import manager.SASUser;

/*
    Una volta capito come estrarre i dati necessari racchiuderà una lista con gli utenti già presenti
    e si potranno vedere i dettagli dei loro profili
 */

public class MembersActivity extends AppCompatActivity {

    private DatabaseReference mDBReference;
    private ListView listViewSasUser;
    private List<SASUser> listSasUser;
    private ArrayAdapter<SASUser> adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        mAuth = FirebaseAuth.getInstance();

        listViewSasUser = (ListView) findViewById(R.id.SASList);
        listSasUser = new ArrayList<>();
        adapter = new ArrayAdapter<SASUser>(this, android.R.layout.simple_list_item_1, listSasUser);
        listViewSasUser.setAdapter(adapter);

        mDBReference = FirebaseDatabase.getInstance().getReference("user");

        mAuth.signInWithEmailAndPassword(getIntent().getStringExtra("myMail") , getIntent().getStringExtra("myPass")).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                listSasUser.clear();

                for( DataSnapshot userSnapshot : dataSnapshot.getChildren() ){
                    SASUser currentUser = userSnapshot.getValue(SASUser.class);
                    listSasUser.add(currentUser);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                listSasUser.clear();

                for( DataSnapshot userSnapshot : dataSnapshot.getChildren() ){
                    SASUser currentUser = userSnapshot.getValue(SASUser.class);
                    listSasUser.add(currentUser);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                listSasUser.clear();

                for( DataSnapshot userSnapshot : dataSnapshot.getChildren() ){
                    SASUser currentUser = userSnapshot.getValue(SASUser.class);
                    listSasUser.add(currentUser);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                listSasUser.clear();

                for( DataSnapshot userSnapshot : dataSnapshot.getChildren() ){
                    SASUser currentUser = userSnapshot.getValue(SASUser.class);
                    listSasUser.add(currentUser);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
