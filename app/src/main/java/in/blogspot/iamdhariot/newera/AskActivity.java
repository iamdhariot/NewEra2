package in.blogspot.iamdhariot.newera;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

public class AskActivity extends AppCompatActivity {
    /**
     * UI Stuffs Declaration
     */

    private Button saveBtn;
    private EditText nameETView, mobileETView;
    private TextView dobSelectBtn,errorTextName,errorTextNumber;
    private DatePickerDialog datePickerDialog;
    private RelativeLayout layout;

    /**
     * Firebase Stuffs Declaration
     */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDBReference;

    public String dateBirth="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ask);


        /**
         *  UI Stuffs reference
         * */
        dobSelectBtn = (TextView) findViewById(R.id.dobSelect);
      errorTextName = (TextView) findViewById(R.id.errorTextName);
        errorTextNumber = (TextView) findViewById(R.id.errorTextNumber);
        nameETView = (EditText) findViewById(R.id.nameETView);
        mobileETView = (EditText) findViewById(R.id.mobileNum);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        layout  =(RelativeLayout)findViewById(R.id.layout);


        /**
         *  firebase Stuffs reference
         * */

        mAuth = FirebaseAuth.getInstance();
        mDBReference = FirebaseDatabase.getInstance().getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() != null) {
                    // user already signed up


                } else {
                    // user is not signed in
                    // redirect to signin page
                    startActivity(new Intent(AskActivity.this, StartActivity.class));
                    finishAffinity();
                }

            }
        };

        dobSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calender class's instance and get current date,month and year from calender
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth  =calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);



                //Date piker dialog

                datePickerDialog = new DatePickerDialog(AskActivity.this, THEME_DEVICE_DEFAULT_LIGHT ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // set day of month, month and year value in the textView

                        dateBirth = dayOfMonth+" / "+(month+1)+" /"+year;
                       dobSelectBtn.setText(dateBirth);

                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();

            }
        });


    }
    private void saveUserInformation() {
        String nameStr = nameETView.getText().toString().trim();
        String email = mAuth.getCurrentUser().getEmail();
        String mobileNum = mobileETView.getText().toString().trim();
        String dob = dateBirth;
        if (TextUtils.isEmpty(dob)) {
            dob = "1 / 1 / 2000";

        }

        /**
         *  Putting the validation on name
         * */
        View focusView = null;
        boolean cancel = false;
        // checking for the valid full name
        if (TextUtils.isEmpty(nameStr)) {
            cancel = true;
            focusView = nameETView;

            TransitionManager.beginDelayedTransition(layout);
            errorTextName.setVisibility(View.VISIBLE);
            nameETView.setBackground(getResources().getDrawable(R.drawable.border_layout_red));
            errorTextName.setText(getString(R.string.error_field_required));



        } else if (!isNameValid(nameStr)) {
            cancel = true;
            focusView = nameETView;
            TransitionManager.beginDelayedTransition(layout);
            errorTextName.setVisibility(View.VISIBLE);
            nameETView.setBackground(getResources().getDrawable(R.drawable.border_layout_red));
            errorTextName.setText(getString(R.string.error_invalid_fullname));



        }else{
            TransitionManager.beginDelayedTransition(layout);
            errorTextName.setVisibility(View.GONE);
            nameETView.setBackground(getResources().getDrawable(R.drawable.editviewbackground_two));
        }

        // mobile number required
        if(TextUtils.isEmpty(mobileNum)){
            focusView = mobileETView;
            cancel =true;

            TransitionManager.beginDelayedTransition(layout);
            errorTextNumber.setVisibility(View.VISIBLE);
            mobileETView.setBackground(getResources().getDrawable(R.drawable.border_layout_red));
            errorTextNumber.setText(getString(R.string.error_field_required));



        }
        else if(!isMobileNoValid(mobileNum)){

            focusView = mobileETView;
            cancel = true;
            TransitionManager.beginDelayedTransition(layout);
            errorTextNumber.setVisibility(View.VISIBLE);
            mobileETView.setBackground(getResources().getDrawable(R.drawable.border_layout_red));
            errorTextNumber.setText(getString(R.string.error_invalid_mobilenumber));


        }else{
            TransitionManager.beginDelayedTransition(layout);
            errorTextNumber.setVisibility(View.GONE);
            mobileETView.setBackground(getResources().getDrawable(R.drawable.editviewbackground_two));
        }
        if (cancel) {
            // There was an error; don't accept email  and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else {
            TransitionManager.beginDelayedTransition(layout);
            errorTextName.setVisibility(View.GONE);
            errorTextNumber.setVisibility(View.GONE);

            nameETView.setBackground(getResources().getDrawable(R.drawable.editviewbackground_two));
            mobileETView.setBackground(getResources().getDrawable(R.drawable.editviewbackground_two));
            UserData userData = new UserData(nameStr, email, mobileNum, dob);
            FirebaseUser user = mAuth.getCurrentUser();


            mDBReference.child("users").child(user.getUid()).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {


                        Toast.makeText(AskActivity.this, "Your data are successfully saved. Thank you.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AskActivity.this, ChoicesActivity.class);
                        //   intent.putExtra("name",nameStr);
                        startActivity(intent);
                        finishAffinity();


                    } else {

                        Toast.makeText(AskActivity.this, "Failed to save your data. Please Try again later...", Toast.LENGTH_SHORT).show();
                    }

                }
            });

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
        if (mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private boolean isNameValid(String nameStr) {
        boolean nameB = false;

        if (nameStr.length() >= 2) {
            nameB = true;
        }
        return nameB;

    }

    private boolean isMobileNoValid(String mobileNum){
        //name mobile number check
        Boolean num=false;
        if(mobileNum.length()==10){
            num=true;

        }
        return num;
    }

}
