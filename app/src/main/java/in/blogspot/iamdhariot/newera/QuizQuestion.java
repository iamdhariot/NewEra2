package in.blogspot.iamdhariot.newera;


/**
 * Created by Dhariot on 10-Dec-17.
 */

public class QuizQuestion {

    public String question;
    public String questionType;
    public String choice1;
    public String choice2;
    public String choice3;
    public String choice4;
    public String answer;
    public String explanation;
    public String qNo;

    public String getqNo() {
        return qNo;
    }

    public void setqNo(String qNo) {
        this.qNo = qNo;
    }

    public QuizQuestion(){

    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public QuizQuestion(String question, String choice1, String choice2, String choice3, String choice4, String answer, String explanation, String qNo,String questionType) {
        this.question = question;
        this.questionType = questionType;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.answer = answer;
        this.explanation = explanation;
        this.qNo = qNo;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}