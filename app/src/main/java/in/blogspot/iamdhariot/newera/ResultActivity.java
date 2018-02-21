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

public class ResultActivity extends AppCompatActivity  implements View.OnClickListener{

    /**
     *  UI Stuffs Declaration
     * */
    private TextView totalMarks,totalQuestions,totalAnswered,totalUnAnswered;
    private Button answersView;
    private ImageButton nav_menu;
    private LinearLayout profile,share,feedback,about,signout;



    /**
     *  Firebase Stuffs Declaration
     * */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public int category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);



        totalMarks  =(TextView)findViewById(R.id.marks);
        totalAnswered  =(TextView)findViewById(R.id.totalAnswered);
        totalQuestions  =(TextView)findViewById(R.id.totalQuestions);
        totalUnAnswered =(TextView)findViewById(R.id.totalUnAnswered);
        nav_menu = (ImageButton)findViewById(R.id.nav_menu);
        about = (LinearLayout)findViewById(R.id.about);
        share = (LinearLayout)findViewById(R.id.share);
        feedback = (LinearLayout)findViewById(R.id.feedback);
        profile = (LinearLayout)findViewById(R.id.profile);
        signout = (LinearLayout)findViewById(R.id.signout);


        answersView =(Button) findViewById(R.id.answerView);


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
                    startActivity(new Intent(ResultActivity.this,StartActivity.class));
                    finishAffinity();

                }

            }
        };

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            /**
             * retrive the extra values with intent and set the to UI stuffs
             */

            String score = extras.getString("score");

            String answered = extras.getString("answered");
            String unanswered = extras.getString("unanswered");
            String totalQuestion = extras.getString("question");

            totalQuestions.setText(totalQuestion);
            totalAnswered.setText(answered);
            totalUnAnswered.setText(unanswered);
            totalMarks.setText("Marks : "+score+"/"+totalQuestion);
            String str =(String)  extras.get("category");
            category= Integer.parseInt(str);



        }


        answersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this,QuestionAnsActivity.class);
                i.putExtra("category", "" + category);
                startActivity(i);

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

    @Override
    public void onClick(View v) {switch (v.getId()){

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
            startActivity(new Intent(ResultActivity.this,AboutActivity.class));
            break;
        case R.id.profile :
            startActivity(new Intent(ResultActivity.this,ProfileActivity.class));
            break;
        case R.id.signout :
            mAuth.signOut();
            startActivity(new Intent(ResultActivity.this,StartActivity.class));
            finish();
            break;

    }


    }
}
