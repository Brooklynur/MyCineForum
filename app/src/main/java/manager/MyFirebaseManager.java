package manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lore_depa.mycineforum.MainActivity;
import com.example.lore_depa.mycineforum.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by SSBS_SELECT on 20/11/2017.
 * conterrà tutti i metodi di lettura e scrittura in momdo che siano accessibili da ogni Activity del progetto
 *
 */

public class MyFirebaseManager {

    private DatabaseReference mDbReference;

    public MyFirebaseManager() {
        mDbReference = FirebaseDatabase.getInstance().getReference();
    }

    public void insertUserCredential(FirebaseUser user , String nick , String mail){
        mDbReference.child("user").child(nick).child("nick").setValue(nick); //Si deve ricostruire il path corretto per arrivare all'oggetto voluto per poi settarne il valore
        mDbReference.child("user").child(nick).child("mail").setValue(mail);
        mDbReference.child("user").child(nick).child("id").setValue(user.getUid());
    }


    public boolean checkCredenziali(TextView mailText, TextView passwordText, TextView nickName){
        boolean valid = true;

        String email = mailText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mailText.setError("Required."); //crea un punto esclamativo nella TextView rilevata vuota
            valid = false;
        } else {
            valid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            mailText.setError(null);
        }

        String password = passwordText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordText.setError("Required.");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        String nick = nickName.getText().toString();
        if (TextUtils.isEmpty(nick)){
            passwordText.setError("Required.");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    public boolean checkCredenziali(TextView mailText, TextView passwordText){
        boolean valid = true;

        String email = mailText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mailText.setError("Required.");
            valid = false;
        } else {
            valid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            mailText.setError(null);
        }

        String password = passwordText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordText.setError("Required.");
            valid = false;
        } else {
            passwordText.setError(null);
        }
        return valid;
    }

}
