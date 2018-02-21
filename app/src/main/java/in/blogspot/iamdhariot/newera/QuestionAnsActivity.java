package in.blogspot.iamdhariot.newera;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionAnsActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     *  Firebase Stuffs Declaration
     * */
    /**
     *  Firebase Stuffs Declaration
     * */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDBReference;
    private FirebaseDatabase mFireDB;

    private ImageButton nav_menu;
    private LinearLayout profile,share,feedback,about,signout;


    public int category;
    public String questionCategory="";
    public String questionType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_ans);

        /**
         *  firebase Stuffs reference
         * */
        mAuth =  FirebaseAuth.getInstance();
        mFireDB = FirebaseDatabase.getInstance();
        mDBReference=mFireDB.getReference();
        nav_menu = (ImageButton)findViewById(R.id.nav_menu);
        about = (LinearLayout)findViewById(R.id.about);
        share = (LinearLayout)findViewById(R.id.share);
        feedback = (LinearLayout)findViewById(R.id.feedback);
        profile = (LinearLayout)findViewById(R.id.profile);
        signout = (LinearLayout)findViewById(R.id.signout);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser()!=null){
                    // user already signed up


                }else{
                    // user is not signed in
                    // redirect to signin page
                    startActivity(new Intent(QuestionAnsActivity.this,StartActivity.class));
                    finishAffinity();

                }

            }
        };


        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            /**
             * retrive the extra values with intent and set the to UI stuffs
             */
            String str =(String)  extras.get("category");
            category= Integer.parseInt(str);

        }
        getQuestionWithAns();


        nav_menu.setOnClickListener(this);
        share.setOnClickListener(this);
        feedback.setOnClickListener(this);
        about.setOnClickListener(this);
        profile.setOnClickListener(this);
        signout.setOnClickListener(this);

    }

    private void getQuestionWithAns() {


        if(category==1){
            questionType="( Mixed )";
            questionCategory = "mix";

        }else if(category==2){
            questionType="( Aptitude )";
            questionCategory="aptitude";

        }else if(category==3){
            questionType="( Reasoning )";
            questionCategory ="reason";

        }else if(category==4){
            questionType="( C Programming)";
            questionCategory = "c";

        }else if(category==5){
            questionType="(C++ Programming)";
            questionCategory="cpp";

        }else if(category==6){
            questionType="( C# )";
            questionCategory ="cSharp";

        }else if(category==7){
            questionType="( CSS )";
            questionCategory = "css";

        }else if(category==8){
            questionType = "( JavaScript )";
            questionCategory="javaScript";

        }else if(category==9){
            questionType = "( Java )";
            questionCategory ="java";

        }else if(category==10){
            questionType ="( HTML )";
            questionCategory = "html";

        }else if(category==11){
            questionType ="( MySQL )";
            questionCategory="mySQL";

        }else if(category==12){
            questionType ="( Python )";
            questionCategory ="python";

        }else if(category==13){
            questionType = "( Android )";
            questionCategory ="android";

        }
        mDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    // reading data from database
                    QuizQuestion mQuestion = new QuizQuestion();
                    ArrayList<QuizQuestion> quizQuestions = new ArrayList<QuizQuestion>();
                    for(int i=0;i<20;i++) {
                        mQuestion.setQuestion(dataSnapshot.child("test").child(questionCategory).child("" + i + "").getValue(QuizQuestion.class).getQuestion());
                        // for button option
                        mQuestion.setChoice1(dataSnapshot.child("test").child(questionCategory).child("" + i + "").getValue(QuizQuestion.class).getChoice1());
                        mQuestion.setChoice2(dataSnapshot.child("test").child(questionCategory).child("" + i + "").getValue(QuizQuestion.class).getChoice2());
                        mQuestion.setChoice3(dataSnapshot.child("test").child(questionCategory).child("" + i + "").getValue(QuizQuestion.class).getChoice3());
                        mQuestion.setChoice4(dataSnapshot.child("test").child(questionCategory).child("" + i + "").getValue(QuizQuestion.class).getChoice4());
                        mQuestion.setAnswer(dataSnapshot.child("test").child(questionCategory).child("" + i + "").getValue(QuizQuestion.class).getAnswer());
                        mQuestion.setExplanation(dataSnapshot.child("test").child(questionCategory).child(""+i+"").getValue(QuizQuestion.class).getExplanation());



                        quizQuestions.add(new QuizQuestion(""+mQuestion.getQuestion()+"",""+mQuestion.getChoice1()+"",""+mQuestion.getChoice2()+"",""+mQuestion.getChoice3()+"",""+mQuestion.getChoice4()+"",""+mQuestion.getAnswer()+"",""+mQuestion.getExplanation()+"",""+(i+1)+"",""+questionType+""));


                    }
                    QuestionAnswerAdapter adapter = new QuestionAnswerAdapter(QuestionAnsActivity.this,quizQuestions);
                    ListView questionListView = (ListView)findViewById(R.id.questionListView);
                    questionListView.setAdapter(adapter);



                }catch (Exception e){


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



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
            break;
        case R.id.profile :
            break;
        case R.id.signout :
            mAuth.signOut();
            startActivity(new Intent(QuestionAnsActivity.this,StartActivity.class));
            finish();
            break;

    }


    }
}
