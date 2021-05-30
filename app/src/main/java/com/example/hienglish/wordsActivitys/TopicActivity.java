package com.example.hienglish.wordsActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hienglish.PagerAdapter;
import com.example.hienglish.R;
import com.example.hienglish.classes.Category;
import com.example.hienglish.classes.VocabsArray;
import com.example.hienglish.classes.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.hienglish.fragments.CategoryFragment.EXTRAS_NAME;


public class TopicActivity extends AppCompatActivity {

    ImageView wordSpeech,meaningSpeech;
    ImageView nextTxtView ,prevTxtView,translateImg;
    public ArrayList<Word> sportWords;
    ViewPager2 viewPager;
    TextToSpeech mTts0,mTts;
    String wordToSpell ="";
    String meaningToSpell="";
    int mNextPosition = 0,mPrevPosition = 0;
    int nb = 1;
    VocabsArray vocabsArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        Intent intent = getIntent();
        int extras = intent.getIntExtra(EXTRAS_NAME, Category.SPORT);

        nextTxtView = findViewById(R.id.next);
        prevTxtView = findViewById(R.id.previous);
        viewPager = findViewById(R.id.words_pager);
        wordSpeech = findViewById(R.id.spell_word);
        meaningSpeech = findViewById(R.id.spell_meaning);
        translateImg = findViewById(R.id.translate_word);

        vocabsArray = new VocabsArray();
        sportWords = getArrauList(extras);

        PagerAdapter adapter = new PagerAdapter(this, sportWords, viewPager);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                (findViewById(R.id.default_language)).setVisibility(View.GONE);
                wordToSpell = adapter.mWordList.get(position).getmWord();
                meaningToSpell = adapter.mWordList.get(position).getmTranslation();
                mNextPosition = position+1;
                mPrevPosition = position-1;
                spell(mTts0,wordToSpell);
            }


        });

        mTts0 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            final Voice voiceObj = new Voice("it-it-x-kda#male_2-local",
                    Locale.getDefault(), Voice.QUALITY_HIGH,Voice.LATENCY_NORMAL, false, null);
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    mTts.setVoice(voiceObj);
                }
            }
        });
        mTts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            final Voice voiceObj = new Voice("it-it-x-kda#male_2-local",
                    Locale.getDefault(), Voice.QUALITY_HIGH,Voice.LATENCY_NORMAL, false, null);
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    mTts.setVoice(voiceObj);
                    mTts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            Log.v("-----------------------","start");
                        }

                        @Override
                        public void onDone(String utteranceId) {
                            Log.v("-----------------------","done");
                            meaningSpeech.setImageResource(R.drawable.ic_play);
                            nb--;
                        }

                        @Override
                        public void onError(String utteranceId) {
                            Log.v("-----------------------","error");
                        }
                    });
                }
            }
        });

        wordSpeech.setOnClickListener(v -> spell(mTts0,wordToSpell));

        meaningSpeech.setOnClickListener(v -> {
                    if(nb ==1){
                        spell(mTts,meaningToSpell);
                        meaningSpeech.setImageResource(R.drawable.ic_pause);
                        nb++;
                    }else {
                        mTts.stop();
                        meaningSpeech.setImageResource(R.drawable.ic_play);
                       nb--;
                    }

                }
              );


        nextTxtView.setOnClickListener(v -> viewPager.setCurrentItem(mNextPosition));
        prevTxtView.setOnClickListener(v -> viewPager.setCurrentItem(mPrevPosition));

        translateImg.setOnClickListener(v -> {
            TextView defaultLang = findViewById(R.id.default_language);
            if(defaultLang.getVisibility() == View.INVISIBLE){
                defaultLang.setVisibility(View.VISIBLE);
            }else {
                defaultLang.setVisibility(View.INVISIBLE);
            }

        });
    }
    private ArrayList<Word> getArrauList(int extras){

        switch (extras){
            case Category.SPORT:return vocabsArray.getSportWords();
            case Category.SCIENCE:return vocabsArray.getScienceWords();
            case Category.RESTAURANT:return vocabsArray.getRestaurantWords();
            case Category.MEDICAL:return vocabsArray.getMedicalWords();
            case Category.JOBS:return vocabsArray.getJobsWords();
            case Category.TRAVEL:return vocabsArray.getTravelWords();
            case Category.ENVIRONMENT:return vocabsArray.getEnvironmentWords();
            case Category.CRIME:return vocabsArray.getCrimeWords();
            case Category.EDUCATION:return vocabsArray.getEducationWords();
            case Category.COMPUTER:return vocabsArray.getComputerWords();
            default:return vocabsArray.getSportWords();
        }
    }
    private void spell(TextToSpeech tts , String data){
        tts.speak( data,TextToSpeech.QUEUE_FLUSH,null,"id");
    }

    @Override
    protected void onPause() {
        mTts.stop();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mTts.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mTts.shutdown();
        super.onDestroy();

    }

}