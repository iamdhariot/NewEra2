package in.blogspot.iamdhariot.newera;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     *  UI Stuffs Declaration
     * */
   private Button signinBtn,signupBtn;
    /**
     *  Firebase Stuffs Declaration
     * */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        /**
         *  UI Stuffs declaration
         * */

       signinBtn = (Button)findViewById(R.id.signinBtn);
       signupBtn = (Button)findViewById(R.id.signupBtn);



        /**
         *  firebase Stuffs reference
         * */
        mAuth =  FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser()!=null){
                    // user already signed up
                    startActivity(new Intent(StartActivity.this,CheckDatabaseHaveUserData.class));
                    finish();



                }else{
                    // user is not signed in
                    // redirect to signin page

                }


            }
        };

        signupBtn.setOnClickListener(this);
        signinBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signinBtn:
                startActivity(new Intent(StartActivity.this,SignInActivity.class));
                finish();

                break;

            case R.id.signupBtn:
                startActivity(new Intent(StartActivity.this,SignUpActivity.class));
                finish();
                break;

        }

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
