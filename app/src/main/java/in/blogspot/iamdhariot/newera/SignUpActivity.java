package in.blogspot.iamdhariot.newera;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     *  UI Stuffs Declaration
     * */
    private TextView signinTVext,errorTextEmail,errorTextPassword,errorTextPasswordRetype;
    private EditText passwordETView,emailETView,passwordRetypeETView;
    private Button signupBtn;

    private RelativeLayout layout;
    private LinearLayout google;


    /**
     * Firebase Stuffs Declaration
     */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //GOOGLE SIGN
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /**
         *  UI Stuffs reference
         * */
        layout  =(RelativeLayout)findViewById(R.id.layout);
        google = (LinearLayout) findViewById(R.id.google);
        signinTVext = (TextView) findViewById(R.id.signinTView);
        errorTextEmail = (TextView) findViewById(R.id.errorTextEmail);
        errorTextPassword= (TextView) findViewById(R.id.errorTextPassword);
        errorTextPasswordRetype =(TextView) findViewById(R.id.errorTextPasswordRetype);
        emailETView =(EditText)findViewById(R.id.emailETView);
        passwordETView =(EditText)findViewById(R.id.passwordETView);
        passwordRetypeETView = (EditText)findViewById(R.id.passwordRetypeETView);
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
                    startActivity(new Intent(SignUpActivity.this,CheckDatabaseHaveUserData.class));
                    finish();



                }else{
                    // user is not signed in
                    // redirect to signin page


                }

            }
        };


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(SignUpActivity.this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(SignUpActivity.this,"Connection Failed. Please try again",Toast.LENGTH_SHORT);

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();



        google.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
        signinTVext.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this,StartActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google:

                signUp();
                break;

            case R.id.signupBtn:
                signUpEmail();
                break;
            case R.id.signinTView:
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                finish();

                break;


        }

    }
    private void signUp() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result  =Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                // google sign in was successful, authenticate with firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            }else{
                // Google Sign In failed, update UI appropriately
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("ID", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Sucesss", "signInWithCredential:success");
                            Toast.makeText(SignUpActivity.this,"Google sign up successful.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this,CheckDatabaseHaveUserData.class));


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("GoogleEx", "signInWithCredential:failure", task.getException());

                            Toast.makeText(SignUpActivity.this,"Google Authentication Failed",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


    private  void signUpEmail(){
        String email = emailETView.getText().toString().trim();
        String password = passwordETView.getText().toString().trim();
        String passwordRetype = passwordRetypeETView.getText().toString().trim();



        View focusView = null;
        boolean cancel = false;
        //check for validate the password,if the user entered one
        if(TextUtils.isEmpty(password)){

            focusView = passwordETView;
            cancel =true;
            TransitionManager.beginDelayedTransition(layout);
            errorTextPassword.setVisibility(View.VISIBLE);
            passwordETView.setBackground(getResources().getDrawable(R.drawable.border_layout_red));
            errorTextPassword.setText(getString(R.string.error_field_required));


        }
        else if(!isPasswordValid(password)){

            focusView = passwordETView;
            cancel = true;
            TransitionManager.beginDelayedTransition(layout);
            errorTextPassword.setVisibility(View.VISIBLE);
            passwordETView.setBackground(getResources().getDrawable(R.drawable.border_layout_red));
            errorTextPassword.setText(getString(R.string.error_invalid_password));


        }else{
            TransitionManager.beginDelayedTransition(layout);
            errorTextPassword.setVisibility(View.GONE);
            passwordETView.setBackground(getResources().getDrawable(R.drawable.editviewbackground_two));
        }
        if(TextUtils.isEmpty(passwordRetype)){
            focusView = passwordRetypeETView;
            cancel =true;
            TransitionManager.beginDelayedTransition(layout);
            errorTextPasswordRetype.setVisibility(View.VISIBLE);
            passwordRetypeETView.setBackground(getResources().getDrawable(R.drawable.border_layout_red));
            errorTextPasswordRetype.setText(getString(R.string.error_field_required));



        }
        else if(!password.equals(passwordRetype)){
            focusView = passwordRetypeETView;
            cancel = true;
            TransitionManager.beginDelayedTransition(layout);
            errorTextPasswordRetype.setVisibility(View.VISIBLE);
            passwordRetypeETView.setBackground(getResources().getDrawable(R.drawable.border_layout_red));
            errorTextPasswordRetype.setText(getString(R.string.error_invalid_passwordRetype));


        }else{
            TransitionManager.beginDelayedTransition(layout);
            errorTextPasswordRetype.setVisibility(View.GONE);
            passwordRetypeETView.setBackground(getResources().getDrawable(R.drawable.editviewbackground_two));


        }

        //Check for the valid email
        if(TextUtils.isEmpty(email)){

            focusView = emailETView;
            cancel =true;
            TransitionManager.beginDelayedTransition(layout);
            errorTextEmail.setVisibility(View.VISIBLE);
            emailETView.setBackground(getResources().getDrawable(R.drawable.border_layout_red));
            errorTextEmail.setText(getString(R.string.error_field_required));


        }
        else if(!isEmailValid(email)){
            focusView = emailETView;
            cancel =true;
            TransitionManager.beginDelayedTransition(layout);
            errorTextEmail.setVisibility(View.VISIBLE);
            emailETView.setBackground(getResources().getDrawable(R.drawable.border_layout_red));
            errorTextEmail.setText(getString(R.string.error_invalid_email));



        }else{

            TransitionManager.beginDelayedTransition(layout);
            errorTextEmail.setVisibility(View.GONE);
            emailETView.setBackground(getResources().getDrawable(R.drawable.editviewbackground_two));

        }



        if(cancel){
            // There was an error; don't attempt signup and focus the first
            // form field with an error.
            focusView.requestFocus();



        }else{

            // perform the user signup attempt.


            /** if validation are ok
             * we will show our progress bar
             **/
            TransitionManager.beginDelayedTransition(layout);
            errorTextPassword.setVisibility(View.GONE);
            errorTextEmail.setVisibility(View.GONE);
            errorTextPasswordRetype.setVisibility(View.GONE);
            emailETView.setBackground(getResources().getDrawable(R.drawable.editviewbackground_two));
            passwordETView.setBackground(getResources().getDrawable(R.drawable.editviewbackground_two));
            passwordRetypeETView.setBackground(getResources().getDrawable(R.drawable.editviewbackground_two));




            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        /**user is successfully signup and signin to app
                         * we will save user data here
                         */
                        sendEmailVerification();

                    }
                    else{
                        Toast.makeText(SignUpActivity.this,"Could not register.Please try again ",Toast.LENGTH_SHORT).show();

                    }

                }
            });



        }
    }
    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this,"Verification email sent to " + user.getEmail()+"\tPlease verify your email.",
                            Toast.LENGTH_SHORT).show();


                    startActivity(new Intent(SignUpActivity.this,AskActivity.class));
                    finish();


                }else{

                    Toast.makeText(SignUpActivity.this,"Failed to send verification email.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this,AskActivity.class));
                    finishAffinity();


                }

            }
        });
    }
    private boolean isPasswordValid(String password) {
        boolean passB = false;
        if (password.length() >= 6) {
            passB = true;

        }
        return passB;
    }

    private  boolean isEmailValid(String email){
        // email validation
        Boolean b;
        if(email.contains(("@gmail.com"))) {
            b=true;
        }
        else if(email.contains(("@outlook.com"))){
            b=true;

        }
        else if(email.contains("@yahoo.com")){
            b=true;
        }

        else if(email.contains("@aol.com")){
            b=true;
        }

        else{
            b=false;
        }


        return b;
        //return email.contains("@");
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
