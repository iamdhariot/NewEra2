package in.blogspot.iamdhariot.newera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Dhariot on 12-Feb-18.
 */

public class CheckDatabaseHaveUserData extends Activity {

    /**
     *  firebase stuffs declaration
     * */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFDB;
    private DatabaseReference mDBReference;

    private TextView wait;
    private ImageView logo;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_database_have_user_data);
        mAuth = FirebaseAuth.getInstance();
        mFDB = FirebaseDatabase.getInstance();
        mDBReference = mFDB.getReference();


        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        logo = (ImageView)findViewById(R.id.logo);
        wait = (TextView)findViewById(R.id.wait);


       
        checkData();


    }

    private void checkData() {
        /**
         * call each time when user call the profile activity
         * */

        mDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /**This method is called once with the initial value and again \
                 * whenever data at this location is updated*/
                FirebaseUser user = mAuth.getCurrentUser();
                if (dataSnapshot.child("users").child(user.getUid()).exists()) {
                    startActivity(new Intent(CheckDatabaseHaveUserData.this, ChoicesActivity.class));
                    finishAffinity();

                } else {
                    startActivity(new Intent(CheckDatabaseHaveUserData.this, AskActivity.class));
                    finishAffinity();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Database error : ", databaseError.toString().trim());

            }
        });

    }

}



