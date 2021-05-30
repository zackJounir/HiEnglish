package com.example.hienglish.classes;

public class Word {

    private String mWord;
    private String mTranslation;
    private String mExample;
    private String mDefaultLang;

    public Word(String word, String translation){
        this.mWord = word;
        this.mTranslation = translation;
    }

    public Word(String word, String translation, String example){
        this.mWord = word;
        this.mTranslation = translation;
        this.mExample = example;
    }
    public Word(String word, String translation, String example, String defaultLang){
        this.mWord = word;
        this.mTranslation = translation;
        this.mExample = example;
        this.mDefaultLang = defaultLang;
    }

    public String getmWord() {
        return mWord;
    }

    public String getmTranslation() {
        return mTranslation;
    }

    public String getmExample() {
        return mExample;
    }

    public String getmDefaultLang() {
        return mDefaultLang;
    }
}
