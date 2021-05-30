package com.example.hienglish.quizfiles;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.hienglish.R;
import com.example.hienglish.classes.Category;
import com.example.hienglish.classes.Question;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.example.hienglish.fragments.QuizFragment.CATEGORY_ID;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_CODE = "extra_code";
    private static final long COUNTDOWN_IN_MILLIS = 20000;

    private CountDownTimer countDownTimer;
    Intent resultIntent;
    private TextView scoreTxt,questionCountTxt,questionTxt;
    private TextView option1, option2, option3 ,option4;

    private List<Question> questionsList;
    private int questionCounter;
    private int questionTotal;
    private Question currentQuestion;
    private int score;
    private int selectedOptionNb;
    private Drawable optionDefaultBackground;
    int category;
    private long timeLeftInMillis;
    private TextView textViewCountDown;
    private ColorStateList textColorDefaultCd;
    private TextToSpeech mTts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        category = intent.getIntExtra(CATEGORY_ID,Category.SPORT);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        scoreTxt = findViewById(R.id.quiz_score);
        questionCountTxt = findViewById(R.id.question_count);
        questionTxt = findViewById(R.id.question);
        textViewCountDown = findViewById(R.id.timer);
        textColorDefaultCd = textViewCountDown.getTextColors();

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionsList = dbHelper.getCategoryQuestions(category);

        optionDefaultBackground = option1.getBackground();
        questionTotal = questionsList.size();

        //to change the order of the list items
        Collections.shuffle(questionsList);

        showNextQuestion();

        mTts = new TextToSpeech(this,onInitListener);

        option1.setOnClickListener(clickListener);
        option2.setOnClickListener(clickListener);
        option3.setOnClickListener(clickListener);
        option4.setOnClickListener(clickListener);

    }
    View.OnClickListener clickListener = v -> {
        switch (v.getId()){
            case R.id.option1:
                selectedOptionNb = 1;
                break;
            case R.id.option2:
                selectedOptionNb = 2;
                 break;
            case R.id.option3:
                selectedOptionNb = 3;
                break;
            case R.id.option4:
                selectedOptionNb = 4;
                break;
        }
        checkAnswer(selectedOptionNb);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showNextQuestion();
            }
        }, 1600);
    };

    TextToSpeech.OnInitListener onInitListener   = new TextToSpeech.OnInitListener() {
        final Voice voiceObj = new Voice("it-it-x-kda#male_2-local",
                Locale.getDefault(), Voice.QUALITY_HIGH,Voice.LATENCY_NORMAL, false, null);
        @Override
        public void onInit(int status) {
            if(status == TextToSpeech.SUCCESS){
                mTts.setVoice(voiceObj);
            }
        }
    };

    private void showNextQuestion(){

        option1.setBackground(optionDefaultBackground);
        option2.setBackground(optionDefaultBackground);
        option3.setBackground(optionDefaultBackground);
        option4.setBackground(optionDefaultBackground);

        if(questionCounter < questionTotal){
            currentQuestion = questionsList.get(questionCounter);

            questionTxt.setText(currentQuestion.getQuestion());
            option1.setText(currentQuestion.getOption1());
            option2.setText(currentQuestion.getOption2());
            option3.setText(currentQuestion.getOption3());
            option4.setText(currentQuestion.getOption4());

            questionCounter++;
            questionCountTxt.setText(getString(R.string.question_nb)+questionCounter +"/"+questionTotal);

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();

        }else{
            finishQuiz();
        }
    }


    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer(selectedOptionNb);
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(QuizActivity.this::showNextQuestion, 1600);
            }
        }.start();
    }
    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDown.setText(timeFormatted);
        if (timeLeftInMillis < 5000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }

    private void checkAnswer(int selectedOptionNb){
        if(selectedOptionNb == currentQuestion.getAnswerNb()){
            score++;
            scoreTxt.setText(R.string.score+score);
        }else {
            switch (selectedOptionNb){
                case 1:
                    option1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.wrong_option_back));
                    break;
                case 2:
                    option2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.wrong_option_back));
                    break;
                case 3:
                    option3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.wrong_option_back));
                    break;
                case 4:
                    option4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.wrong_option_back));
                    break;
            }
        }
        countDownTimer.cancel();
        showAnswer();
    }

    private void  showAnswer(){
        String correctOption = "";
        switch (currentQuestion.getAnswerNb()){
            case 1:
                option1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.correct_option_back));
                correctOption = currentQuestion.getOption1();
                break;
            case 2:
                option2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.correct_option_back));
                correctOption = currentQuestion.getOption2();
                break;
            case 3:
                option3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.correct_option_back));
                correctOption = currentQuestion.getOption3();
                break;
            case 4:
                option4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.correct_option_back));
                correctOption = currentQuestion.getOption4();
                break;
        }

        spell(correctOption);
    }

    private void spell(String txt){
        mTts.speak(txt,TextToSpeech.QUEUE_FLUSH,null,"id");
    }

    private void finishQuiz(){
        resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_CODE,score);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    @Override
    protected void onPause() {
        mTts.stop();
        super.onPause();
    }

    @Override
    protected void onStop() {
        countDownTimer.cancel();
        mTts.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
        mTts.shutdown();
        super.onDestroy();
    }
}