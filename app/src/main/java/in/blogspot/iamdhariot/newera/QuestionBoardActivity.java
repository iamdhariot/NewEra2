package in.blogspot.iamdhariot.newera;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionBoardActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     *  UI Stuffs Declaration
     * */
    private TextView a,b,c,d,choice1,choice2,choice3,choice4,quit,question,questionType,answer,answerText,rightAnswer,explanation,explanationText,waitText,questionNumber;
    private LinearLayout layout;

    private ImageButton backArrowBtn;
    private ImageButton nav_menu;
    private LinearLayout profile,share,feedback,about,signout;


    /**
     *  Firebase Stuffs Declaration
     * */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDBReference;
    private FirebaseDatabase mFireDB;

    /**
     * progress bar
     * */
    private ProgressBar progressBar1;


    /*
    * for logic
    * */
    public int category;
   public String questionCategory="";
   public int mQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_board);

        /**
         *  UI Stuffs reference
         * */
        a = (TextView)findViewById(R.id.a);
        b = (TextView)findViewById(R.id.b);
        c= (TextView)findViewById(R.id.c);
        d = (TextView)findViewById(R.id.d);

        choice1 = (TextView)findViewById(R.id.choice1);
        choice2 = (TextView)findViewById(R.id.choice2);
        choice3 = (TextView)findViewById(R.id.choice3);
        choice4 = (TextView)findViewById(R.id.choice4);

        questionNumber = (TextView)findViewById(R.id.questionNumber);
        quit = (TextView)findViewById(R.id.quit);
        answer = (TextView)findViewById(R.id.answer);
       waitText= (TextView)findViewById(R.id.waitText);
        answerText = (TextView)findViewById(R.id.answerText);
        rightAnswer = (TextView)findViewById(R.id.rightAnswer);
        explanation = (TextView)findViewById(R.id.explanation);
        question = (TextView)findViewById(R.id.question);
        questionType = (TextView)findViewById(R.id.questionType);
        progressBar1 = (ProgressBar)findViewById(R.id.progressBar1);
        layout = (LinearLayout)findViewById(R.id.layout);
        explanationText = (TextView)findViewById(R.id.explanationText);

        backArrowBtn =(ImageButton)findViewById(R.id.backArrowBtn);
        nav_menu = (ImageButton)findViewById(R.id.nav_menu);
        about = (LinearLayout)findViewById(R.id.about);
        share = (LinearLayout)findViewById(R.id.share);
        feedback = (LinearLayout)findViewById(R.id.feedback);
        profile = (LinearLayout)findViewById(R.id.profile);
        signout = (LinearLayout)findViewById(R.id.signout);




        /**
         *  getting extra intent
         * */

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String str =(String)  bundle.get("category");
        category= Integer.parseInt(str);


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
                   updateQuestion();

                }else{
                    // user is not signed in
                    // redirect to signin page
                    startActivity(new Intent(QuestionBoardActivity.this,StartActivity.class));
                    finishAffinity();

                }

            }
        };




        quit.setOnClickListener(this);
        answer.setOnClickListener(this);
        backArrowBtn.setOnClickListener(this);
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

            case R.id.answer:
                progressBar1.setVisibility(View.GONE);
                waitText.setVisibility(View.GONE);
                TransitionManager.beginDelayedTransition(layout);

               answer.setVisibility(View.VISIBLE);
               answerText.setVisibility(View.VISIBLE);
               rightAnswer.setVisibility(View.VISIBLE);
               explanationText.setVisibility(View.VISIBLE);
               explanation.setVisibility(View.VISIBLE);
               quit.setVisibility(View.VISIBLE);


                break;
                /**
                 * Question is quit and next question arrive till all question are not arrive
                 * */
            case R.id.quit:
                mQuestionNumber+=1;
                updateQuestion();
                break;

            case R.id.backArrowBtn:
                if(category<4){
                    startActivity(new Intent(QuestionBoardActivity.this,QuestionCategoriesActivity.class));
                    finish();

                }else if(category>3 && category<14){
                    startActivity(new Intent(QuestionBoardActivity.this,QuestionProgramingCataActivity.class));
                    finish();
                }
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
                startActivity(new Intent(QuestionBoardActivity.this,AboutActivity.class));
                break;
            case R.id.profile :
                startActivity(new Intent(QuestionBoardActivity.this,ProfileActivity.class));
                break;
            case R.id.signout :
                mAuth.signOut();
                startActivity(new Intent(QuestionBoardActivity.this,StartActivity.class));
                finish();
                break;

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        }else if(category<4){
            startActivity(new Intent(QuestionBoardActivity.this,QuestionCategoriesActivity.class));
            finish();

        }else if(category>3 && category<14){
            startActivity(new Intent(QuestionBoardActivity.this,QuestionProgramingCataActivity.class));
            finish();
        }

    }
    private void updateQuestion(){


        TransitionManager.beginDelayedTransition(layout);
        questionNumber.setVisibility(View.GONE);
        questionType.setVisibility(View.GONE);
        question.setVisibility(View.GONE);
        a.setVisibility(View.GONE);
        b.setVisibility(View.GONE);
        c.setVisibility(View.GONE);
        d.setVisibility(View.GONE);
        choice1.setVisibility(View.GONE);
        choice2.setVisibility(View.GONE);
        choice3.setVisibility(View.GONE);
        choice4.setVisibility(View.GONE);
        quit.setVisibility(View.GONE);
        answer.setVisibility(View.GONE);
        answerText.setVisibility(View.GONE);
        rightAnswer.setVisibility(View.GONE);
        explanation.setVisibility(View.GONE);
        explanationText.setVisibility(View.GONE);

        progressBar1.setVisibility(View.VISIBLE);
        waitText.setVisibility(View.VISIBLE);


        if(category==1){
            questionType.setText("( Mixed )");
            questionCategory = "all";

        }else if(category==2){
            questionType.setText("( Aptitude )");
            questionCategory="aptitude";

        }else if(category==3){
            questionType.setText("( Reasoning )");
            questionCategory ="reason";

        }else if(category==4){
            questionType.setText("( C Programming)");
            questionCategory = "c";

        }else if(category==5){
            questionType.setText("(C++ Programming)");
            questionCategory="cpp";

        }else if(category==6){
            questionType.setText("( C# )");
            questionCategory ="csharp";

        }else if(category==7){
            questionType.setText("( CSS )");
            questionCategory = "css";

        }else if(category==8){
            questionType.setText("( JavaScript )");
            questionCategory="javaScript";

        }else if(category==9){
            questionType.setText("( Java )");
            questionCategory ="java";

        }else if(category==10){
            questionType.setText("( HTML )");
            questionCategory = "html";

        }else if(category==11){
            questionType.setText("( MySQL )");
            questionCategory="mySQL";

        }else if(category==12){
            questionType.setText("( Python )");
            questionCategory ="python";

        }else if(category==13){
            questionType.setText("( Android )");
            questionCategory ="android";

        }


                mDBReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        try {
                            // reading data from database
                            QuizQuestion mQuestion = new QuizQuestion();
                            mQuestion.setQuestion(dataSnapshot.child("questionAnswer").child(questionCategory).child(""+mQuestionNumber+"").getValue(QuizQuestion.class).getQuestion());
                            // for button option
                            mQuestion.setChoice1(dataSnapshot.child("questionAnswer").child(questionCategory).child(""+mQuestionNumber+"").getValue(QuizQuestion.class).getChoice1());
                            mQuestion.setChoice2(dataSnapshot.child("questionAnswer").child(questionCategory).child(""+mQuestionNumber+"").getValue(QuizQuestion.class).getChoice2());
                            mQuestion.setChoice3(dataSnapshot.child("questionAnswer").child(questionCategory).child(""+mQuestionNumber+"").getValue(QuizQuestion.class).getChoice3());
                            mQuestion.setChoice4(dataSnapshot.child("questionAnswer").child(questionCategory).child(""+mQuestionNumber+"").getValue(QuizQuestion.class).getChoice4());
                            mQuestion.setAnswer(dataSnapshot.child("questionAnswer").child(questionCategory).child(""+mQuestionNumber+"").getValue(QuizQuestion.class).getAnswer());
                            mQuestion.setExplanation(dataSnapshot.child("questionAnswer").child(questionCategory).child(""+mQuestionNumber+"").getValue(QuizQuestion.class).getExplanation());



                            //setting question data on UI stuffs

                            questionNumber.setText("Q."+(mQuestionNumber+1)+".");
                            question.setText(mQuestion.getQuestion());
                            choice1.setText(mQuestion.getChoice1());
                            choice2.setText(mQuestion.getChoice2());
                            choice3.setText(mQuestion.getChoice3());
                            choice4.setText(mQuestion.getChoice4());
                            rightAnswer.setText(mQuestion.getAnswer());
                            explanation.setText(mQuestion.getExplanation());

                            TransitionManager.beginDelayedTransition(layout);
                            progressBar1.setVisibility(View.GONE);
                            waitText.setVisibility(View.GONE);
                            questionNumber.setVisibility(View.VISIBLE);
                            question.setVisibility(View.VISIBLE);
                            questionType.setVisibility(View.VISIBLE);
                            a.setVisibility(View.VISIBLE);
                            b.setVisibility(View.VISIBLE);
                            c.setVisibility(View.VISIBLE);
                            d.setVisibility(View.VISIBLE);
                            choice1.setVisibility(View.VISIBLE);
                            choice2.setVisibility(View.VISIBLE);
                            choice3.setVisibility(View.VISIBLE);
                            choice4.setVisibility(View.VISIBLE);
                            answer.setVisibility(View.VISIBLE);
                            quit.setVisibility(View.VISIBLE);



                        }catch (Exception e){
                            if(dataSnapshot.child(questionCategory).child(""+mQuestionNumber+"").exists()){}
                            else{
                                TransitionManager.beginDelayedTransition(layout);
                                questionType.setVisibility(View.GONE);
                                a.setVisibility(View.GONE);
                                b.setVisibility(View.GONE);
                                c.setVisibility(View.GONE);
                                d.setVisibility(View.GONE);
                                 choice1.setVisibility(View.GONE);
                                choice2.setVisibility(View.GONE);
                                choice3.setVisibility(View.GONE);
                                choice4.setVisibility(View.GONE);
                                answer.setVisibility(View.GONE);
                                answerText.setVisibility(View.GONE);
                                rightAnswer.setVisibility(View.GONE);
                                explanationText.setVisibility(View.GONE);
                                explanation.setVisibility(View.GONE);
                                quit.setVisibility(View.GONE);
                                question.setText("Well done. You reached to all questions. More questions are available soon...");
                                progressBar1.setVisibility(View.GONE);
                                waitText.setVisibility(View.GONE);


                            }

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


}
