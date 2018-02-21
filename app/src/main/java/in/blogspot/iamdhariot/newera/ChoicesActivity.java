package in.blogspot.iamdhariot.newera;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ChoicesActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     *  UI Stuffs Declaration
     * */

    private Button questionMode,testMode;
    private ImageButton nav_menu;
    private LinearLayout profile,share,feedback,about,signout;

    /**
     *  Firebase Stuffs Declaration
     * */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        /**
         *  UI Stuffs reference
         * */

        questionMode  =(Button) findViewById(R.id.questionAns);
        testMode  =(Button)findViewById(R.id.onlineTest);
        nav_menu = (ImageButton)findViewById(R.id.nav_menu);
        about = (LinearLayout)findViewById(R.id.about);
        share = (LinearLayout)findViewById(R.id.share);
        feedback = (LinearLayout)findViewById(R.id.feedback);
        profile = (LinearLayout)findViewById(R.id.profile);
        signout = (LinearLayout)findViewById(R.id.signout);







        /**
         *  firebase Stuffs reference
         * */
        mAuth =  FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser()!=null){
                    // user already signed up


                }else{
                    // user is not signed in
                    // redirect to signin page
                    startActivity(new Intent(ChoicesActivity.this,StartActivity.class));
                    finishAffinity();
                }

            }
        };

        questionMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoicesActivity.this,QuestionCategoriesActivity.class));
                finish();
            }
        });

        testMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoicesActivity.this,TestCategoriesActivity.class));
                finish();

            }
        });


        nav_menu.setOnClickListener(this);
        share.setOnClickListener(this);
        feedback.setOnClickListener(this);
        about.setOnClickListener(this);
        profile.setOnClickListener(this);
        signout.setOnClickListener(this);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.nav_menu :
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.END);
                break;
            case R.id.share :
                //share link
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                intent1.putExtra(Intent.EXTRA_SUBJECT,"new era : learning like game ");
                intent1.putExtra(Intent.EXTRA_TEXT,"You've got to check out the new era : learning like game app!. It's awesome.\nDownload : http://iamdhariot.blogspot.in");
                startActivity(Intent.createChooser(intent1,"Share via :"));


            break;
            case R.id.feedback :
                String mailTo = "mailto:dhar12997@outlook.com";

                //intent for mail application with order summary
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse(mailTo)); //only email app should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, ""+mAuth.getCurrentUser().getEmail());
                intent.putExtra(Intent.EXTRA_SUBJECT, "New Era App Feedback.");
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);
                break;
            case R.id.about :
                startActivity(new Intent(ChoicesActivity.this,AboutActivity.class));
                break;
            case R.id.profile :
                startActivity(new Intent(ChoicesActivity.this,ProfileActivity.class));
                break;
                case R.id.signout :
                    mAuth.signOut();
                    startActivity(new Intent(ChoicesActivity.this,StartActivity.class));
                    finish();
                break;

        }

    }
}
