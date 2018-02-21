package in.blogspot.iamdhariot.newera;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
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

public class ProfileActivity extends AppCompatActivity {

    // ui stuffs
    private TextView name,email,mobileNo,dob,waitText;

    private ProgressBar progressBar;

    private LinearLayout layout;
    /**
     *  Firebase Stuffs Declaration
     * */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDBReference;
    private FirebaseDatabase mFireDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        /**
         *  firebase Stuffs reference
         * */
        mAuth =  FirebaseAuth.getInstance();
        mFireDB = FirebaseDatabase.getInstance();
        mDBReference=mFireDB.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser()!=null){
                    // user already signed up


                }else{
                    // user is not signed in
                    // redirect to signin page
                    startActivity(new Intent(ProfileActivity.this,StartActivity.class));
                    finishAffinity();

                }

            }
        };
        // ui stuffs reference
        name = (TextView)findViewById(R.id.name);
        email = (TextView)findViewById(R.id.email);
        dob = (TextView)findViewById(R.id.dob);
        mobileNo = (TextView)findViewById(R.id.mobile);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        waitText = (TextView)findViewById(R.id.wait);
        layout = (LinearLayout)findViewById(R.id.layout);

        /**
         * call each time when user call the profile activity
         * */


        mDBReference.addValueEventListener(new ValueEventListener() {



            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /**This method is called once with the initial value and again \
                 * whenever data at this location is updated*/

                //reading data from database
                FirebaseUser user = mAuth.getCurrentUser();
                UserData userData = new UserData();
                userData.setName(dataSnapshot.child("users").child(user.getUid()).getValue(UserData.class).getName());
                userData.setEmail(dataSnapshot.child("users").child(user.getUid()).getValue(UserData.class).getEmail());
                userData.setDob(dataSnapshot.child("users").child(user.getUid()).getValue(UserData.class).getDob());
                userData.setMobileNum(dataSnapshot.child("users").child(user.getUid()).getValue(UserData.class).getMobileNum());

                TransitionManager.beginDelayedTransition(layout);
                progressBar.setVisibility(View.GONE);
                waitText.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                name.setText(userData.getName());
                email.setText(userData.getEmail());
                dob.setText(userData.getDob());
                mobileNo.setText(userData.getMobileNum());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.i("Database error : ",databaseError.toString().trim());


            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuth!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
