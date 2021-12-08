package com.firstapp.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddQuestionDB extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler
    private EditText questionEdt, option1Edt, option2Edt, option3Edt,correctansnoEdt;
    private Button addEdt;
    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question_db);

        // initializing all our variables.
       questionEdt=findViewById(R.id.idquestion);
       option1Edt=findViewById(R.id.idquestionoption1);
       option2Edt=findViewById(R.id.idquestionoption2);
       option3Edt=findViewById(R.id.idquestionoption3);
       correctansnoEdt=findViewById(R.id.idCorrectAnswerNo);
       addEdt=findViewById(R.id.idAddQuestionBtn);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(AddQuestionDB.this);


        // below line is to add on click listener for our add course button.
        addEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String question = questionEdt.getText().toString();
                String option1 = option1Edt.getText().toString();
                String option2 = option2Edt.getText().toString();
                String option3 = option3Edt.getText().toString();
                String correctansno = correctansnoEdt.getText().toString();

                // validating if the text fields are empty or not.
                if (question.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || correctansno.isEmpty()) {
                    Toast.makeText(AddQuestionDB.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addNewQuestion(question,option1,option2,option3,correctansno);


                // after adding the data we are displaying a toast message.
                Toast.makeText(AddQuestionDB.this, "Question has been added.", Toast.LENGTH_SHORT).show();
                questionEdt.setText("");
                option3Edt.setText("");
                option2Edt.setText("");
                option1Edt.setText("");
                correctansnoEdt.setText("");
            }
        });
    }
}
