package com.example.hienglish.quizfiles;

public class QuizCategory {

    private String mName;
    private int mScore;
    private int mId;


    public QuizCategory(String mName, int mScore ,int id) {
        this.mName = mName;
        this.mScore = mScore;
        this.mId = id;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmScore() {
        return mScore;
    }

    public void setmScore(int mScore) {
        this.mScore = mScore;
    }
}
