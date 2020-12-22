package com.example.navigationapp;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Ques extends AppCompatActivity implements View.OnClickListener{

    private TextView que;
    private  TextView qcount,timer;
    private Button op1,op2,op3,op4;
    private List<Question> questionList;
    private int quesNum=0;
    private CountDownTimer countDown;
    private  int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques);
        que=findViewById(R.id.q1);
        qcount=findViewById(R.id.ques_num);
        timer= findViewById(R.id.counter);
        op1= findViewById(R.id.op1);
        op2= findViewById(R.id.op2);
        op3= findViewById(R.id.op3);
        op4= findViewById(R.id.op4);
        op1.setOnClickListener(this);
        op2.setOnClickListener(this);
        op3.setOnClickListener(this);
        op4.setOnClickListener(this);

        getQuestionList();
        score=0;

    }

    private void getQuestionList(){
        questionList= new ArrayList<>();
        questionList.add(new Question("capital of Pakistan is","Karachi","Islamabad","Lahore","Multan",2));
        questionList.add(new Question("No of provinces of Pakistan","1","2","3","4",4));
        questionList.add(new Question("Quaid-e-Azam is _____ of Pakistan","Leader","National poet","Founder","Guide",3));
        questionList.add(new Question("How many continents are there in this world","7","3","10","8",1));
        questionList.add(new Question("National sports of Pakistan","Hockey","Cricket","Football","tennis",1));
        questionList.add(new Question("Who is our National poet?","Ghalib","Allama Iqbal","Both","None of these",2));
        questionList.add(new Question("Our National language is","english","farsi","Urdu","Punjabi",3));
        questionList.add(new Question("Pakistan is a/an","islamic country","liberal","both","none of these",1));
        questionList.add(new Question("Orange is a","fruit","color","both","none of these",3));
        questionList.add(new Question("Rainbow has colours?","6","7","8","9",2));
        setQuestion();

    }

    public  void setQuestion(){
        timer.setText(String.valueOf(60));
        que.setText(questionList.get(0).getQue());
        op1.setText(questionList.get(0).getOp1());
        op2.setText(questionList.get(0).getOp2());
        op3.setText(questionList.get(0).getOp3());
        op4.setText(questionList.get(0).getOp4());

        qcount.setText(String.valueOf(1)+"/"+String.valueOf(questionList.size()));
        startTimer();
        quesNum=0;
    }

    private  void startTimer(){
        countDown = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

                changeQuestion();
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        int selectedOption=0;
        switch (v.getId()){
            case R.id.op1:
                selectedOption=1;
                break;
            case R.id.op2:
                selectedOption=2;
                break;
            case R.id.op3:
                selectedOption=3;
                break;
            case R.id.op4:
                selectedOption=4;
                break;
            default:
        }
        countDown.cancel();
        checkAnswer(selectedOption, v);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private  void checkAnswer(int selectedOption, View view){
        //right ans
        /*if(selectedOption==questionList.get(quesNum).getCorrectop()) {
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score++;
        }

        else{
            //wrong ans
            //((Button) view).setBackgroundColor(Color.RED);
            switch (questionList.get(quesNum).getCorrectop()){
                case 1:
                    //op1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                   // op2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    op3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    op4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
            }
        }*/


        changeQuestion();
    }

    private void changeQuestion(){
        if(quesNum<questionList.size()-1){
            quesNum++;

            playAnim(que,0,0);
            playAnim(op1,0,1);
            playAnim(op2,0,2);
            playAnim(op3,0,3);
            playAnim(op4,0,4);

            qcount.setText(String.valueOf(quesNum)+"/"+String.valueOf(questionList.size()));
            timer.setText(String.valueOf(60));
            startTimer();

        }
        else{
            //go to score activity
            Intent intent = new Intent(Ques.this,score.class);
            intent.putExtra("SCORE",String.valueOf(score)+"/"+String.valueOf(questionList.size()));
            startActivity(intent);
            Ques.this.finish();
        }
    }

    private  void playAnim(final View view, final int value, final int viewNum){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        if(value==0){
                            switch (viewNum){
                                case 0:
                                    ((TextView)view).setText(questionList.get(quesNum).getQue());
                                    break;
                                case 1:
                                    ((Button)view).setText(questionList.get(quesNum).getOp1());
                                    break;
                                case 2:
                                    ((Button)view).setText(questionList.get(quesNum).getOp2());
                                    break;
                                case 3:
                                    ((Button)view).setText(questionList.get(quesNum).getOp3());
                                    break;
                                case 4:
                                    ((Button)view).setText(questionList.get(quesNum).getOp4());
                                    break;

                            }

                            if(viewNum!=0){
                                ((Button)view).setBackgroundColor(Color.WHITE);
                            }

                            playAnim(view,1,viewNum);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }


                });
    }
}