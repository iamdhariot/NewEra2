package in.blogspot.iamdhariot.newera;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TestBoardActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     *  UI Stuffs Declaration
     * */
    private TextView a,b,c,d,choice1,choice2,choice3,choice4,question,questionType,questionNumText,timeLeft,quit,waitText;
    private LinearLayout layout;
    private ImageButton arrowBackBtn;
    private CheckBox checkBoxA,checkBoxB,checkBoxC,checkBoxD;
    private ImageButton nav_menu;
    private LinearLayout profile,share,feedback,about,signout,startTestLayout;
    private Button startTest;




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
    private int correct=0;
    private int wrong = 0;
    private int mScore=0;
    private int answered=0;
    private int unAnswered=0;

    public String answer="";

    /**
     * For count down
     * 600000*3 = 30 mins
     * */
    private  static  final  long START_TIME_IN_MILLIS = 600000*3;
    private CountDownTimer mCountDownTimer;
    private Long mTimeLeftMillis = START_TIME_IN_MILLIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_borad);

        /**
         *  UI Stuffs reference
         * */
        checkBoxA = (CheckBox)findViewById(R.id.checkboxA);
        checkBoxB = (CheckBox)findViewById(R.id.checkboxB);
        checkBoxC = (CheckBox)findViewById(R.id.checkboxC);
        checkBoxD = (CheckBox)findViewById(R.id.checkboxD);



        a = (TextView)findViewById(R.id.a);
        b = (TextView)findViewById(R.id.b);
        c= (TextView)findViewById(R.id.c);
        d = (TextView)findViewById(R.id.d);

        choice1 = (TextView)findViewById(R.id.choice1);
        choice2 = (TextView)findViewById(R.id.choice2);
        choice3 = (TextView)findViewById(R.id.choice3);
        choice4 = (TextView)findViewById(R.id.choice4);

        quit = (TextView)findViewById(R.id.quit);
        question = (TextView)findViewById(R.id.question);
        questionType = (TextView)findViewById(R.id.questionType);
        progressBar1 = (ProgressBar)findViewById(R.id.progressBar1);
        layout = (LinearLayout)findViewById(R.id.layout);
        arrowBackBtn = (ImageButton)findViewById(R.id.backArrowBtn);
        nav_menu = (ImageButton)findViewById(R.id.nav_menu);
        about = (LinearLayout)findViewById(R.id.about);
        share = (LinearLayout)findViewById(R.id.share);
        feedback = (LinearLayout)findViewById(R.id.feedback);
        profile = (LinearLayout)findViewById(R.id.profile);
        signout = (LinearLayout)findViewById(R.id.signout);
        startTest = (Button)findViewById(R.id.startTest);
        startTestLayout = (LinearLayout)findViewById(R.id.startTestLayout);



        waitText = (TextView)findViewById(R.id.waitText);

        questionNumText = (TextView)findViewById(R.id.questionNumber);

        timeLeft = (TextView)findViewById(R.id.timeLeft);

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


                }else{
                    // user is not signed in
                    // redirect to signin page
                    startActivity(new Intent(TestBoardActivity.this,StartActivity.class));
                    finishAffinity();

                }

            }
        };





        updateCountDownText();

        checkBoxA.setOnClickListener(this);
        checkBoxB.setOnClickListener(this);
        checkBoxC.setOnClickListener(this);
        checkBoxD.setOnClickListener(this);

        quit.setOnClickListener(this);
        nav_menu.setOnClickListener(this);
        share.setOnClickListener(this);
        feedback.setOnClickListener(this);
        about.setOnClickListener(this);
        profile.setOnClickListener(this);
        signout.setOnClickListener(this);
        arrowBackBtn.setOnClickListener(this);
        startTest.setOnClickListener(this);


    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftMillis=millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timeLeft.setText("Time out");
                Intent i = new Intent(TestBoardActivity.this,ResultActivity.class);
                i.putExtra("score",""+mScore);
                i.putExtra("question",""+(mQuestionNumber+1));
                i.putExtra("answered",""+answered);
                i.putExtra("unanswered",""+unAnswered);
                i.putExtra("category",""+category);
                startActivity(i);
                finishAffinity();


            }
        }.start();
    }

    private void updateCountDownText() {
        int hrs = 0;
        int mins = (int)(mTimeLeftMillis/1000)/60;
        int secs = (int)(mTimeLeftMillis/1000)%60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d",hrs,mins,secs);
        timeLeft.setText(timeLeftFormatted);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkboxA:
                if(choice1.getText().equals(answer)){

                    //Toast.makeText(TestBoardActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                    answered = answered+1;
                    correct = correct+1;
                    mScore  = mScore+1;
                    mQuestionNumber+=1;
                    updateQuestion();


                }else{
                   // Toast.makeText(TestBoardActivity.this,"Wrong",Toast.LENGTH_SHORT).show();
                    answered = answered+1;
                    wrong = wrong+1;
                    mQuestionNumber+=1;
                    updateQuestion();

                }
                checkBoxB.setChecked(false);
                checkBoxC.setChecked(false);
                checkBoxD.setChecked(false);
                break;
            case R.id.checkboxB:
                if(choice2.getText().equals(answer)){

                    //Toast.makeText(TestBoardActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                    answered = answered+1;
                    correct = correct+1;
                    mScore  = mScore+1;
                    mQuestionNumber+=1;
                    updateQuestion();

                }else{
                    //Toast.makeText(TestBoardActivity.this,"Wrong",Toast.LENGTH_SHORT).show();
                    answered = answered+1;
                    wrong = wrong+1;
                    mQuestionNumber+=1;
                    updateQuestion();

                }
                checkBoxA.setChecked(false);
                checkBoxC.setChecked(false);
                checkBoxD.setChecked(false);
                break;
            case R.id.checkboxC:
                if(choice3.getText().equals(answer)){

                   // Toast.makeText(TestBoardActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                    answered = answered+1;
                    correct = correct+1;
                    mScore  = mScore+1;
                    mQuestionNumber+=1;
                    updateQuestion();

                }else{
                    //Toast.makeText(TestBoardActivity.this,"Wrong",Toast.LENGTH_SHORT).show();
                    answered = answered+1;
                    wrong = wrong+1;
                    mQuestionNumber+=1;
                    updateQuestion();

                }
                checkBoxA.setChecked(false);
                checkBoxB.setChecked(false);
                checkBoxD.setChecked(false);

                break;
            case R.id.checkboxD:
                if(choice4.getText().equals(answer)){

                    //Toast.makeText(TestBoardActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                    answered = answered+1;
                    mQuestionNumber+=1;
                    updateQuestion();

                }else{
                    //Toast.makeText(TestBoardActivity.this,"Wrong",Toast.LENGTH_SHORT).show();
                    answered = answered+1;
                    mQuestionNumber+=1;
                    updateQuestion();

                }

            checkBoxA.setChecked(false);
            checkBoxB.setChecked(false);
            checkBoxC.setChecked(false);
                break;

            /**
             * Question is quit and next question arrive till all question are not arrive
             * */
            case R.id.quit:
                mQuestionNumber+=1;
                unAnswered = unAnswered+1;
                updateQuestion();
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
                startActivity(new Intent(TestBoardActivity.this,AboutActivity.class));
                break;
            case R.id.profile :
                startActivity(new Intent(TestBoardActivity.this,ProfileActivity.class));
                break;
            case R.id.signout :
                mAuth.signOut();
                startActivity(new Intent(TestBoardActivity.this,StartActivity.class));
                finish();
                break;
            case R.id.backArrowBtn:
                if(category<4){
                    startActivity(new Intent(TestBoardActivity.this,TestCategoriesActivity.class));
                    finish();

                }else if(category>3 && category<14){
                    startActivity(new Intent(TestBoardActivity.this,TestProgramingCataActivity.class));
                    finish();
                }
                break;

            case R.id.startTest:
                TransitionManager.beginDelayedTransition(layout);
                startTimer();
                updateQuestion();
                startTestLayout.setVisibility(View.GONE);
                progressBar1.setVisibility(View.VISIBLE);
                waitText.setVisibility(View.VISIBLE);



        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        }else if(category<4){
            startActivity(new Intent(TestBoardActivity.this,TestCategoriesActivity.class));
            finish();

        }else if(category>3 && category<14){
            startActivity(new Intent(TestBoardActivity.this,TestProgramingCataActivity.class));
            finish();
        }

    }


    private void updateQuestion() {
        TransitionManager.beginDelayedTransition(layout);
        checkBoxA.setChecked(false);
        checkBoxB.setChecked(false);
        checkBoxC.setChecked(false);
        checkBoxD.setChecked(false);
        questionNumText.setVisibility(View.GONE);
        question.setVisibility(View.GONE);
        questionType.setVisibility(View.GONE);
        checkBoxA.setVisibility(View.GONE);
        checkBoxB.setVisibility(View.GONE);
        checkBoxC.setVisibility(View.GONE);
        checkBoxD.setVisibility(View.GONE);

        a.setVisibility(View.GONE);
        b.setVisibility(View.GONE);
        c.setVisibility(View.GONE);
        d.setVisibility(View.GONE);
        choice1.setVisibility(View.GONE);
        choice2.setVisibility(View.GONE);
        choice3.setVisibility(View.GONE);
        choice4.setVisibility(View.GONE);
        quit.setVisibility(View.GONE);
        progressBar1.setVisibility(View.VISIBLE);
        waitText.setVisibility(View.VISIBLE);


        if (category == 1) {
            questionType.setText("( Mixed )");
            questionCategory = "mix";

        } else if (category == 2) {
            questionType.setText("( Aptitude )");
            questionCategory = "aptitude";

        } else if (category == 3) {
            questionType.setText("( Reasoning )");
            questionCategory = "reason";

        } else if (category == 4) {
            questionType.setText("( C Programming)");
            questionCategory = "c";

        } else if (category == 5) {
            questionType.setText("(C++ Programming)");
            questionCategory = "cpp";

        } else if (category == 6) {
            questionType.setText("( C# )");
            questionCategory = "cSharp";

        } else if (category == 7) {
            questionType.setText("( CSS )");
            questionCategory = "css";

        } else if (category == 8) {
            questionType.setText("( JavaScript )");
            questionCategory = "javaScript";

        } else if (category == 9) {
            questionType.setText("( Java )");
            questionCategory = "java";

        } else if (category == 10) {
            questionType.setText("( HTML )");
            questionCategory = "html";

        } else if (category == 11) {
            questionType.setText("( MySQL )");
            questionCategory = "mySQL";

        } else if (category == 12) {
            questionType.setText("( Python )");
            questionCategory = "python";

        } else if (category == 13) {
            questionType.setText("( Android )");
            questionCategory = "android";

        }


        mDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                try {
                    // reading data from database
                    QuizQuestion mQuestion = new QuizQuestion();

                    mQuestion.setQuestion(dataSnapshot.child("test").child(questionCategory).child("" + mQuestionNumber + "").getValue(QuizQuestion.class).getQuestion());
                    // for button option
                    mQuestion.setChoice1(dataSnapshot.child("test").child(questionCategory).child("" + mQuestionNumber + "").getValue(QuizQuestion.class).getChoice1());
                    mQuestion.setChoice2(dataSnapshot.child("test").child(questionCategory).child("" + mQuestionNumber + "").getValue(QuizQuestion.class).getChoice2());
                    mQuestion.setChoice3(dataSnapshot.child("test").child(questionCategory).child("" + mQuestionNumber + "").getValue(QuizQuestion.class).getChoice3());
                    mQuestion.setChoice4(dataSnapshot.child("test").child(questionCategory).child("" + mQuestionNumber + "").getValue(QuizQuestion.class).getChoice4());
                    mQuestion.setAnswer(dataSnapshot.child("test").child(questionCategory).child("" + mQuestionNumber + "").getValue(QuizQuestion.class).getAnswer());



                    //setting question data on UI stuffs
                    questionNumText.setText("Q." + (mQuestionNumber + 1) + ".");
                    question.setText(mQuestion.getQuestion());
                    choice1.setText(mQuestion.getChoice1());
                    choice2.setText(mQuestion.getChoice2());
                    choice3.setText(mQuestion.getChoice3());
                    choice4.setText(mQuestion.getChoice4());
                    answer = mQuestion.getAnswer();

                    TransitionManager.beginDelayedTransition(layout);
                    progressBar1.setVisibility(View.GONE);
                    waitText.setVisibility(View.GONE);
                    questionNumText.setVisibility(View.VISIBLE);
                    question.setVisibility(View.VISIBLE);
                    questionType.setVisibility(View.VISIBLE);

                    checkBoxA.setVisibility(View.VISIBLE);
                    checkBoxB.setVisibility(View.VISIBLE);
                    checkBoxC.setVisibility(View.VISIBLE);
                    checkBoxD.setVisibility(View.VISIBLE);

                    a.setVisibility(View.VISIBLE);
                    b.setVisibility(View.VISIBLE);
                    c.setVisibility(View.VISIBLE);
                    d.setVisibility(View.VISIBLE);
                    choice1.setVisibility(View.VISIBLE);
                    choice2.setVisibility(View.VISIBLE);
                    choice3.setVisibility(View.VISIBLE);
                    choice4.setVisibility(View.VISIBLE);
                    quit.setVisibility(View.VISIBLE);


                } catch (Exception e) {
                    if (dataSnapshot.child(questionCategory).child("" + mQuestionNumber + "").exists()) {
                    } else {
                        TransitionManager.beginDelayedTransition(layout);
                        questionNumText.setVisibility(View.GONE);
                        question.setVisibility(View.GONE);
                        questionType.setVisibility(View.GONE);
                        checkBoxA.setVisibility(View.GONE);
                        checkBoxB.setVisibility(View.GONE);
                        checkBoxC.setVisibility(View.GONE);
                        checkBoxD.setVisibility(View.GONE);

                        a.setVisibility(View.GONE);
                        b.setVisibility(View.GONE);
                        c.setVisibility(View.GONE);
                        d.setVisibility(View.GONE);
                        choice1.setVisibility(View.GONE);
                        choice2.setVisibility(View.GONE);
                        choice3.setVisibility(View.GONE);
                        choice4.setVisibility(View.GONE);
                        quit.setVisibility(View.GONE);
                        progressBar1.setVisibility(View.GONE);
                        waitText.setVisibility(View.GONE);

                        Intent i = new Intent(TestBoardActivity.this, ResultActivity.class);
                        i.putExtra("score", "" + mScore);
                        i.putExtra("question", "" + (answered+unAnswered));
                        i.putExtra("answered", "" + answered);
                        i.putExtra("unanswered", "" + unAnswered);
                        i.putExtra("category", "" + category);
                        startActivity(i);
                        finishAffinity();


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
