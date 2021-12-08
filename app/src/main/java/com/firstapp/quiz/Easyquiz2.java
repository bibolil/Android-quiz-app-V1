package com.firstapp.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Easyquiz2 extends AppCompatActivity {
    private QuestionModel currentQuestion;
    public static final String EXTRA_MESSAGE = "com.firstapp.quizMESSAGE";
    /* creation of question array of lists */
    private List<QuestionModel> questionList;
    /* variables to stock the maps */
    private TextView tvQuestion,tvScore,tvQuestionNo,tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1,rb2,rb3;
    private Button btnNext;

    int totalQuestions;
    int qCounter=0;
    int score;
    ColorStateList dfRbColor;
    boolean answered;
    CountDownTimer  countDownTimer;
    private ArrayList<QuestionModel> questionModelArrayList;
    private DBHandler dbHandler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easyquiz2);
        /* Creation of question array of lists */
        questionList= new ArrayList<>();
        /* Mapping the elements by id */
        dbHandler = new DBHandler(Easyquiz2.this);
        tvQuestion=findViewById(R.id.textQuestion);
        tvScore=findViewById(R.id.textScore);
        tvQuestionNo=findViewById(R.id.textQuestionNo);
        tvTimer=findViewById(R.id.textTimer);
        radioGroup=findViewById(R.id.radioGroup);
        rb1=findViewById(R.id.rb1);
        rb2=findViewById(R.id.rb2);
        rb3=findViewById(R.id.rb3);
        btnNext=findViewById(R.id.btnNext);

        dfRbColor= rb1.getTextColors();

        /* method to create the question */
        addQuestions();
        /* number of questions */
        totalQuestions=questionList.size();
        /* method to play the questions dynamically */
        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answered==false) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        checkAnswer();
                        countDownTimer.cancel();
                    } else {
                        Toast.makeText(Easyquiz2.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                        showNextQuestion();
                    }


            }
        });
        
    }

    private void checkAnswer() {
        answered=true;
        RadioButton rebSelected= findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rebSelected)+1;
        if(answerNo==currentQuestion.getCorrectAnsNo())
        {
            score++;
            tvScore.setText("Score: "+score);
        }
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getCorrectAnsNo()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);

        }
        if(qCounter<totalQuestions){
            btnNext.setText("Next");
        }else{
            btnNext.setText("Finish");

        }
    }

    private void showNextQuestion() {
        timer();
        radioGroup.clearCheck();
        rb1.setTextColor(dfRbColor);
        rb2.setTextColor(dfRbColor);
        rb3.setTextColor(dfRbColor);


        if(qCounter<totalQuestions) {
            currentQuestion= questionList.get(qCounter); /* get the question from the list with qCounter index */
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            qCounter++;
            btnNext.setText("Submit");
            tvQuestionNo.setText("Question : "+qCounter+"/"+totalQuestions);
            answered=false;
        }
        else {
            finish();
            Intent intent = new Intent(this, Scoreview.class);
            String text =String.valueOf(score);
            intent.putExtra(EXTRA_MESSAGE,text);
            startActivity(intent);
        }


    }

    private void timer() {
        countDownTimer= new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("00:" +  millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                showNextQuestion();

            }
        }.start();
    }

    private void addQuestions() {

        questionModelArrayList=dbHandler.readQuestion();

        for (int i=0;i<questionModelArrayList.size();i++)
            System.out.println(questionModelArrayList.get(i));

        System.out.println(questionModelArrayList.size());



        questionList.add(new QuestionModel("1", "Q 1 - How to kill an activity in Android?","Finish()","kill()", "None of above", 1));
        questionList.add(new QuestionModel("2", "Q 2 - How many broadcast receivers are available in android?", "sendIntent()", "sendBroadcast(),sendOrderBroadcast(),sendStickyBroadcast().", "onRecieve()", 2));
        questionList.add(new QuestionModel("3", "Q 3 -What is JNI in android?", "Java interface", "Image editable tool", "Java native interface.", 3));
        questionList.add(new QuestionModel("4", "Q 4 - What is ADB in android?", "Android Debug Bridge", "Development tool", "Image tool", 1));
        questionList.add(new QuestionModel("5", "Q 5- Is it possible activity without UI in android?", "No, it's not possible", "Yes,it's possible", "We can't say", 2));
        System.out.println(questionList.get(1));
    }
}