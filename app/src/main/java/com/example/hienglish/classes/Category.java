package com.example.hienglish.classes;

public class Category {
    public static final int CRIME = 1;
    public static final int EDUCATION = 2;
    public static final int ENVIRONMENT = 3;
    public static final int JOBS = 4;
    public static final int MEDICAL = 5;
    public static final int RESTAURANT = 6;
    public static final int SCIENCE = 7;
    public static final int SPORT = 8;
    public static final int TRAVEL = 9;
    public static final int COMPUTER = 10;

    private int id;
    private String mName;

    public Category() {
    }
    public Category(String mName) {
        this.mName = mName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getmName() {
        return mName;
    }
    public void setmName(String mName) {
        this.mName = mName;
    }
}
