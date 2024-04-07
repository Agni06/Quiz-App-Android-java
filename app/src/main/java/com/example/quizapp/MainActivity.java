package com.example.quizapp;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submit;

    int score =0;
    int totalQuestion = qAndA.question.length;
    int currentQuestionIndex =0 ;
    String selectedAnswer = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        totalQuestionsTextView = findViewById(R.id.totalQuestions);
        questionTextView = findViewById(R.id.question);
        ansA  = findViewById(R.id.ans_a);
        ansB  = findViewById(R.id.ans_b);
        ansC  = findViewById(R.id.ans_c);
        ansD  = findViewById(R.id.ans_d);
        submit = findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submit.setOnClickListener(this);

        totalQuestionsTextView.setText("Total question : "+totalQuestion);
        loadNewQuestion();




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    void loadNewQuestion()
    {

        if(currentQuestionIndex == totalQuestion)
        {
            finishQuiz();
            return ;
        }
        questionTextView.setText(qAndA.question[currentQuestionIndex]);
        ansA.setText(qAndA.choices[currentQuestionIndex][0]);
        ansB.setText(qAndA.choices[currentQuestionIndex][1]);
        ansC.setText(qAndA.choices[currentQuestionIndex][2]);
        ansD.setText(qAndA.choices[currentQuestionIndex][3]);

    }
    @Override
    public void onClick(View v) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clcikedButton =  (Button) v;
        if(clcikedButton.getId()==R.id.submit_btn)
        {
            if(selectedAnswer.equals(qAndA.crtAns[currentQuestionIndex]))
            {
                score++;
            }

            currentQuestionIndex++;
            loadNewQuestion();




        }
        else {
            selectedAnswer = clcikedButton.getText().toString();
            clcikedButton.setBackgroundColor(Color.MAGENTA);
        }


    }
    void finishQuiz()
    {
        String passStatus = "";
        if(score> totalQuestion*0.60)
        {
            passStatus = "Pass";
        }
        else
        {
            passStatus = "Fail";
        }
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " +score+ " out of " + totalQuestion)
                .setPositiveButton("Restart", (dialogInterface,i ) -> restartQuiz())
                .setCancelable(false)
                .show();


    }
    void restartQuiz()
    {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}