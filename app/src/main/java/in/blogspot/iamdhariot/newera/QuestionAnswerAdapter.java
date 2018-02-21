package in.blogspot.iamdhariot.newera;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dhariot on 20-Feb-18.
 */

public class QuestionAnswerAdapter extends ArrayAdapter<QuizQuestion> {


    // questions id


    public QuestionAnswerAdapter (Activity context, ArrayList<QuizQuestion> questions){
        super(context,0,questions);


    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View   content_question_ans_view = convertView;
        if( content_question_ans_view== null) {
            content_question_ans_view = LayoutInflater.from(getContext()).inflate(
                    R.layout.content_question_ans, parent, false);
        }

        QuizQuestion currentQuestion = getItem(position);
        // accessing all the layout views
        TextView questionNumber = (TextView)content_question_ans_view.findViewById(R.id.questionNumber);
        TextView question = (TextView)content_question_ans_view.findViewById(R.id.question);
        TextView questionType = (TextView)content_question_ans_view.findViewById(R.id.questionType);
        TextView a = (TextView)content_question_ans_view.findViewById(R.id.a);
        TextView b = (TextView)content_question_ans_view.findViewById(R.id.b);
        TextView c = (TextView)content_question_ans_view.findViewById(R.id.c);
        TextView d = (TextView)content_question_ans_view.findViewById(R.id.d);
        TextView choiceA = (TextView)content_question_ans_view.findViewById(R.id.choice1);
        TextView  choiceB = (TextView)content_question_ans_view.findViewById(R.id.choice2);
        TextView choiceC = (TextView)content_question_ans_view.findViewById(R.id.choice3);
        TextView choiceD  = (TextView)content_question_ans_view.findViewById(R.id.choice4);
        TextView answerText = (TextView)content_question_ans_view.findViewById(R.id.answerText);
        TextView rightAnswer  = (TextView)content_question_ans_view.findViewById(R.id.rightAnswer);
        TextView explanationText = (TextView)content_question_ans_view.findViewById(R.id.explanationText);
        TextView explanation  = (TextView)content_question_ans_view.findViewById(R.id.explanation);

        questionNumber.setText("Q."+currentQuestion.getqNo()+".");
        question.setText(currentQuestion.getQuestion());
        questionType.setText(currentQuestion.getQuestionType());

        choiceA.setText(currentQuestion.getChoice1());
        choiceB.setText(currentQuestion.getChoice2());
        choiceC.setText(currentQuestion.getChoice3());
        choiceD.setText(currentQuestion.getChoice4());

        rightAnswer.setText(currentQuestion.getAnswer());
        explanation.setText(currentQuestion.getExplanation());


        return content_question_ans_view;


    }


}
