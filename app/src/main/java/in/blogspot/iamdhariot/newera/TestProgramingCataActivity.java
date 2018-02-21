package in.blogspot.iamdhariot.newera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class TestProgramingCataActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     *  UI Stuffs Declaration
     * */
    private TextView c,cpp,csharp,java,python,javaScript,android,mySql,css,html;
    private ImageButton arrowBackBtn;
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
        setContentView(R.layout.activity_programing_cata_test);

        /**
         *  UI Stuffs reference
         * */

        arrowBackBtn  =(ImageButton)findViewById(R.id.backArrowBtn);
        nav_menu = (ImageButton)findViewById(R.id.nav_menu);
        about = (LinearLayout)findViewById(R.id.about);
        share = (LinearLayout)findViewById(R.id.share);
        feedback = (LinearLayout)findViewById(R.id.feedback);
        profile = (LinearLayout)findViewById(R.id.profile);
        signout = (LinearLayout)findViewById(R.id.signout);

        c  = (TextView)findViewById(R.id.c);
        csharp  = (TextView)findViewById(R.id.csharp);
        html  = (TextView)findViewById(R.id.html);
        javaScript  = (TextView)findViewById(R.id.javaScript);
        android  = (TextView)findViewById(R.id.android);



        cpp = (TextView)findViewById(R.id.cpp);
        java = (TextView)findViewById(R.id.java);
        css = (TextView)findViewById(R.id.css);
        python = (TextView)findViewById(R.id.python);
        mySql  = (TextView)findViewById(R.id.mySQL);

        /**
         *  UI Stuffs animation
         * */

        c.startAnimation(AnimationUtils.loadAnimation(this,R.anim.textview_anim_left_right));
        csharp.startAnimation(AnimationUtils.loadAnimation(this,R.anim.textview_anim_left_right));
        html.startAnimation(AnimationUtils.loadAnimation(this,R.anim.textview_anim_left_right));
        javaScript.startAnimation(AnimationUtils.loadAnimation(this,R.anim.textview_anim_left_right));
        android.startAnimation(AnimationUtils.loadAnimation(this,R.anim.textview_anim_left_right));

        cpp.startAnimation(AnimationUtils.loadAnimation(this,R.anim.textview_anim_right_left));
        java.startAnimation(AnimationUtils.loadAnimation(this,R.anim.textview_anim_right_left));
        css.startAnimation(AnimationUtils.loadAnimation(this,R.anim.textview_anim_right_left));
        python.startAnimation(AnimationUtils.loadAnimation(this,R.anim.textview_anim_right_left));
        mySql.startAnimation(AnimationUtils.loadAnimation(this,R.anim.textview_anim_right_left));

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
                    startActivity(new Intent(TestProgramingCataActivity.this,StartActivity.class));
                    finishAffinity();

                }

            }
        };

        c.setOnClickListener(this);
        cpp.setOnClickListener(this);
        java.setOnClickListener(this);
        javaScript.setOnClickListener(this);
        html.setOnClickListener(this);
        css.setOnClickListener(this);
        python.setOnClickListener(this);
        android.setOnClickListener(this);
        mySql.setOnClickListener(this);
        csharp.setOnClickListener(this);
        arrowBackBtn.setOnClickListener(this);
        nav_menu.setOnClickListener(this);
        share.setOnClickListener(this);
        feedback.setOnClickListener(this);
        about.setOnClickListener(this);
        profile.setOnClickListener(this);
        signout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.c:

                Intent cIntent = new Intent(TestProgramingCataActivity.this,TestBoardActivity.class);
                cIntent.putExtra("category","4");
                startActivity(cIntent);
                finish();
                break;
            case R.id.cpp:
                Intent cppIntent = new Intent(TestProgramingCataActivity.this,TestBoardActivity.class);
                cppIntent.putExtra("category","5");
                startActivity(cppIntent);
                finish();
                break;
            case R.id.csharp:
                Intent cShIntent = new Intent(TestProgramingCataActivity.this,TestBoardActivity.class);
                cShIntent.putExtra("category","6");
                startActivity(cShIntent);
                finish();
                break;
            case R.id.css:
                Intent cssIntent = new Intent(TestProgramingCataActivity.this,TestBoardActivity.class);
                cssIntent.putExtra("category","7");
                startActivity(cssIntent);
                finish();
                break;
            case R.id.javaScript:
                Intent javaSIntent = new Intent(TestProgramingCataActivity.this,TestBoardActivity.class);
                javaSIntent.putExtra("category","8");
                startActivity(javaSIntent);
                finish();
                break;
            case R.id.java:
                Intent javaIntent = new Intent(TestProgramingCataActivity.this,TestBoardActivity.class);
                javaIntent.putExtra("category","9");
                startActivity(javaIntent);
                finish();
                break;
            case R.id.html:
                Intent htmlIntent = new Intent(TestProgramingCataActivity.this,TestBoardActivity.class);
                htmlIntent.putExtra("category","10");
                startActivity(htmlIntent);
                finish();
                break;
            case R.id.mySQL:
                Intent myIntent = new Intent(TestProgramingCataActivity.this,TestBoardActivity.class);
                myIntent.putExtra("category","11");
                startActivity(myIntent);
                finish();
                break;
            case R.id.python:
                Intent pyIntent = new Intent(TestProgramingCataActivity.this,TestBoardActivity.class);
                pyIntent.putExtra("category","12");
                startActivity(pyIntent);
                finish();
                break;
            case R.id.android:
                Intent andIntent = new Intent(TestProgramingCataActivity.this,TestBoardActivity.class);
                andIntent.putExtra("category","13");
                startActivity(andIntent);
                finish();
                break;
            case R.id.backArrowBtn:
                startActivity(new Intent(TestProgramingCataActivity.this,TestCategoriesActivity.class));
                finish();
                break;
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
                break;
            case R.id.profile :
                break;
            case R.id.signout :
                mAuth.signOut();
                startActivity(new Intent(TestProgramingCataActivity.this,StartActivity.class));
                finish();
                break;
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        }else {
            super.onBackPressed();
            startActivity(new Intent(TestProgramingCataActivity.this,TestCategoriesActivity.class));
            finish();
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
