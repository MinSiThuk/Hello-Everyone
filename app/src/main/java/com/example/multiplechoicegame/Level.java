package com.example.multiplechoicegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Level extends AppCompatActivity {
    TextView txtLevel;
    TextView Timer;
    TextView txtScore;
    TextView question;
    TextView questionNumber;
    RadioGroup RG;
    RadioButton choice1;
    RadioButton choice2;
    RadioButton choice3;
    RadioButton choice4;
    Button Next;
    String level;
    CountDownTimer countdowntimer;
    int questionIndex=1;
    int num1=0;
    int num2=0;
    int operatorNumber=0;
    int answer=0;
    int score=0;
    String operator="";
    ArrayList<String>optionList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        level= getIntent().getStringExtra("Level");
        txtLevel=(TextView) findViewById(R.id.idlevel);
        Timer=(TextView) findViewById(R.id.idtime);
        txtScore=(TextView) findViewById(R.id.idscore);
        question=(TextView) findViewById(R.id.idQuestion);
        questionNumber = (TextView) findViewById(R.id.idQNum);
        RG=(RadioGroup) findViewById(R.id.idRG);
        choice1=(RadioButton) findViewById(R.id.idchoice1);
        choice2=(RadioButton) findViewById(R.id.idchoice2);
        choice3=(RadioButton) findViewById(R.id.idchoice3);
        choice4=(RadioButton) findViewById(R.id.idchoice4);
        Next=(Button) findViewById(R.id.idnext);
        txtLevel.setText("Level"+level);
        if(level.equals("0")){
            Timer.setVisibility(View.INVISIBLE);
        }
        else if (level.equals("1")){
            Timer.setVisibility(View.VISIBLE);

        }
        else if (level.equals("2")){
            Timer.setVisibility(View.VISIBLE);

        }

        setUpQuestion();
        setUpOption();
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
                if(questionIndex>10){
                    Toast.makeText(Level.this,"Congratulation",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Level.this,congratulation.class);
                    startActivity(intent);

                }
                else {
                    setUpQuestion();
                }

            }
        });


    }
    public void setTimer(){
        long miliSecond=0;
         if (level.equals("1")){
             miliSecond=21000;
        }
        else if (level.equals("2")){
             miliSecond=11000;
        }
        countdowntimer=new CountDownTimer(miliSecond,1000) {
            @Override
            public void onTick(long l) {
                Timer.setText("Timer:00:" +l/1000);

            }

            @Override
            public void onFinish() {
                questionIndex++;
                setUpQuestion();

            }
        }.start();
    }
    public int randomNumber(int max, int min){
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;
        return rand;
    }
    public void setUpQuestion() {
        RG.clearCheck();
        if(level.equals("1")||level.equals("2")){
            setTimer();

        }
        num1 = randomNumber(12, 1);
        num2 = randomNumber(12, 1);
        operatorNumber = randomNumber(4, 1);
        if (operatorNumber == 1) {
            answer = num1 + num2;
            operator="+";
        } else if (operatorNumber == 2){
            if(num1>num2){
                answer =num1-num2;
                operator="-";
            }
            else{
                setUpQuestion();
            }
     }
        else if(operatorNumber==3){
            answer=num1*num2;
            operator="*";
        }
        else if(operatorNumber==4){
            answer=num1/num2;
            operator="/";

        }
        question.setText(num1+operator+num2+" will be");
        questionNumber.setText("Question"+questionIndex+"/10");
        txtScore.setText("Score:"+score);
        setUpOption();
    }
    public void setUpOption(){
        optionList=new ArrayList<>();
        optionList.add(String.valueOf(answer));
        for(int i=0;i<3;i++){
           int randomOption=randomNumber(12,1);
           while(optionList.contains(randomOption)){
               randomOption=randomNumber(12,1);
           }
            optionList.add(String.valueOf(randomNumber(12,1)));
        }
        Collections.shuffle(optionList);
        choice1.setText(optionList.get(0));
        choice2.setText(optionList.get(1));
        choice3.setText(optionList.get(2));
        choice4.setText(optionList.get(3));
    }
    public void checkAnswer(){
        if(choice1.isChecked()||choice2.isChecked()||choice3.isChecked()||choice4.isChecked()){
            countdowntimer.cancel();
            RadioButton rdClicked=findViewById(RG.getCheckedRadioButtonId());
            if(String.valueOf(answer).equals(rdClicked.getText().toString())){
                score++;
                txtScore.setText("Score:"+score);
            }
            else {
                Toast.makeText(Level.this,"Wrong",Toast.LENGTH_LONG).show();
            }
            questionIndex++;

        }
        else{
            Toast.makeText(Level.this,"Please choose one",Toast.LENGTH_LONG).show();
        }
    }
}