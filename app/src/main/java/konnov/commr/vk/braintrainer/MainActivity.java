package konnov.commr.vk.braintrainer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView timerTextView;
    private TextView questionTextView;
    private TextView resultTextView;
    private TextView scoreTextView;
    private Button firstAnswerButton;
    private Button secondAnswerButton;
    private Button thirdAnswerButton;
    private Button fourthAnswerButton;
    private Button playAgainButton;
    private int locationOfAnswer;
    private int[] answers;
    private int a;
    private int b;
    private int maxValue = 10;
    private int score = 0;
    private int questionNumber = 1;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
    }



    private void startTimer(){
        new CountDownTimer(60100, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                gameHasFinished();
            }
        }.start();
    }


    private void gameHasFinished(){
        timerTextView.setText("0");
        playAgainButton.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.INVISIBLE);
        resultTextView.setText(String.format(getString(R.string.result_output), score));
        resultTextView.animate().translationY(-300).setDuration(2500);
        voiceOutput();
    }



    public void answerButtonClicked(View view){
        if(view.getTag().toString().equals(String.valueOf(locationOfAnswer))){
            resultTextView.setText(R.string.correct);
            score++;
        }
        else
            resultTextView.setText(R.string.incorrect);
        scoreTextView.setText(String.format(getString(R.string.score_output), score, questionNumber));
        questionNumber++;
        maxValue += 10;
        generateQuestion();
    }




    public void goButtonClicked(View view){
        RelativeLayout startScreenRelativeLayout = findViewById(R.id.startScreenRelativeLayout);
        RelativeLayout gameRelativeLayout = findViewById(R.id.gameRelativeLayout);
        startScreenRelativeLayout.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);
        generateQuestion();
        startTimer();

    }




    public void playAgainButtonClicked(View view){
        gridLayout.setVisibility(View.VISIBLE);
        resultTextView.animate().translationY(50).setDuration(500);
        playAgainButton.setVisibility(View.INVISIBLE);
        score = 0;
        questionNumber = 0;
        scoreTextView.setText(String.format(getString(R.string.score_output), score, questionNumber));
        timerTextView.setText(R.string.default_timer_value);
        resultTextView.setText("");
        maxValue = 10;
        generateQuestion();
        startTimer();
    }




    private void generateQuestion(){
        for(int i = 0; i < 4; i++){
            answers[i] = 0;
            System.out.println("answers[ + " + i + "] = " + answers[i]);
        }
        int tempRandValue;
        Random rand = new Random();
        a = rand.nextInt(maxValue);
        b = rand.nextInt(maxValue);
        int c = a + b;
        System.out.println("a = " + a + "   b = " + b + "   c = " + c);
        locationOfAnswer = rand.nextInt(4);
        System.out.println("locationOfAnswer = " + locationOfAnswer);
        for(int i = 0; i < 4; i++){
            System.out.println("for(int i = 0; i < 4; i++){ where i = " + i);
            if(i == locationOfAnswer) {
                System.out.println("if(i == locationOfAnswer) { when i = " + i);
                answers[i] = c;
                System.out.println("answers[i] = " + answers[i]);
            }
            else {
                 do{
                     System.out.println("else{ do{");
                    tempRandValue = rand.nextInt(11 - (-10)) - 10;
                     System.out.println("tempRandValue = " + tempRandValue);
                }while(tempRandValue+ c == answers[0] || tempRandValue+ c == answers[1] ||
                         tempRandValue+ c == answers[2] || tempRandValue+ c == answers[3] || tempRandValue == 0);
                System.out.println("END OF WHILE");
                answers[i] = c + tempRandValue;
                System.out.println("answers[i] = " + answers[i]);
            }
        }
        System.out.println("END OF FOR");
        setQuestion();
    }




    private void setQuestion(){
        questionTextView.setText(String.format(getString(R.string.question_string), a, b));
        firstAnswerButton.setText(String.valueOf(answers[0]));
        secondAnswerButton.setText(String.valueOf(answers[1]));
        thirdAnswerButton.setText(String.valueOf(answers[2]));
        fourthAnswerButton.setText(String.valueOf(answers[3]));
    }


    private void voiceOutput(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.loser);
        mediaPlayer.start();

    }

    private void initialization(){
        answers = new int[4];
        timerTextView = findViewById(R.id.timerTextView);
        questionTextView =  findViewById(R.id.questionTextView);
        resultTextView =  findViewById(R.id.resultTextView);
        scoreTextView =  findViewById(R.id.scoreTextView);
        firstAnswerButton = findViewById(R.id.firstAnswerButton);
        secondAnswerButton = findViewById(R.id.secondAnswerButton);
        thirdAnswerButton = findViewById(R.id.thirdAnswerButton);
        fourthAnswerButton = findViewById(R.id.fourthAnswerButton);
        playAgainButton = findViewById(R.id.playAgainButton);
        gridLayout = findViewById(R.id.gridLayout);
    }
}
