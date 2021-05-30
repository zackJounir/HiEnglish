package com.example.hienglish.quizfiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.hienglish.classes.Category;
import com.example.hienglish.classes.Question;
import com.example.hienglish.classes.VocabsArray;
import com.example.hienglish.classes.Word;
import com.example.hienglish.quizfiles.QuizContract.CategoriesTable;
import com.example.hienglish.quizfiles.QuizContract.QuestionTable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase quizDatabase;

    ArrayList<Word> words;

    public QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.quizDatabase = db;

        fillWordsArray();

        final String create_categories_table = "CREATE TABLE "+CategoriesTable.table_name
                +"("+CategoriesTable._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +CategoriesTable.column_name +" TEXT)";

        final String create_question_table = "CREATE TABLE "+ QuestionTable.table_name
                +"("+QuestionTable._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +QuestionTable.question_column +" TEXT, "
                +QuestionTable.option1_column +" TEXT, "
                +QuestionTable.option2_column +" TEXT, "
                +QuestionTable.option3_column +" TEXT, "
                +QuestionTable.option4_column +" TEXT, "
                +QuestionTable.answerNb_column +" INTEGER ,"
                +QuestionTable.column_category_id +" INTEGER, "
                +"FOREIGN KEY ("+QuestionTable.column_category_id+") REFERENCES "+CategoriesTable.table_name+"("+CategoriesTable._ID+") ON DELETE CASCADE"
                +" )";

        db.execSQL(create_categories_table);
        db.execSQL(create_question_table);
        fillCategoriesTable();
        fillQuestionTable();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+CategoriesTable.table_name);
        db.execSQL("DROP TABLE IF EXISTS "+QuestionTable.table_name);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillWordsArray(){
        VocabsArray vocabsArray = new VocabsArray();
        words = vocabsArray.getCrimeWords();
        words.addAll(vocabsArray.getEducationWords());
        words.addAll(vocabsArray.getEnvironmentWords());
        words.addAll(vocabsArray.getJobsWords());
        words.addAll(vocabsArray.getMedicalWords());
        words.addAll(vocabsArray.getRestaurantWords());
        words.addAll(vocabsArray.getScienceWords());
        words.addAll(vocabsArray.getSportWords());
        words.addAll(vocabsArray.getTravelWords());
        words.addAll(vocabsArray.getComputerWords());

    }

    private void fillCategoriesTable(){
        Category category1 = new Category("crime");
        addCategories(category1);
        Category category2 = new Category("education");
        addCategories(category2);
        Category category3 = new Category("environment");
        addCategories(category3);
        Category category4 = new Category("jobs");
        addCategories(category4);
        Category category5 = new Category("medical");
        addCategories(category5);
        Category category6 = new Category("restaurant");
        addCategories(category6);
        Category category7 = new Category("Science");
        addCategories(category7);
        Category category8 = new Category("sport");
        addCategories(category8);
        Category category9 = new Category("travel");
        addCategories(category9);
        Category category10 = new Category("computer");
        addCategories(category10);
    }


    private void fillQuestionTable(){
        int max = 4;
        int min = 1;
        int wrongOptionMax = words.size()-1;
        int wrongOptionMin = 0;
            for (int i = 0 ; i < words.size() ;i++){

                Question question;
                int correctAnswerIndex = (int)Math.floor(Math.random()*(max-min+1)+min);
                int wrongOptionIndex1 = (int)Math.floor(Math.random()*(wrongOptionMax-wrongOptionMin));
                int wrongOptionIndex2 = (int)Math.floor(Math.random()*(wrongOptionMax-wrongOptionMin));
                int wrongOptionIndex3 = (int)Math.floor(Math.random()*(wrongOptionMax-wrongOptionMin));

                while (wrongOptionIndex1 == correctAnswerIndex){
                    wrongOptionIndex1 = (int)Math.floor(Math.random()*(wrongOptionMax-wrongOptionMin+1)+wrongOptionMin);
                }

                while(wrongOptionIndex2 == correctAnswerIndex || wrongOptionIndex2 == wrongOptionIndex1){
                    wrongOptionIndex2 = (int)Math.floor(Math.random()*(wrongOptionMax-wrongOptionMin+1)+wrongOptionMin);
                }

                while(wrongOptionIndex3 == correctAnswerIndex || wrongOptionIndex3 == wrongOptionIndex2 || wrongOptionIndex3 == wrongOptionIndex1){
                    wrongOptionIndex3 = (int)Math.floor(Math.random()*(wrongOptionMax-wrongOptionMin+1)+wrongOptionMin);
                }


                switch (correctAnswerIndex){
                    case 1:
                        question = new Question(words.get(i).getmTranslation(),words.get(i).getmWord(),words.get(wrongOptionIndex1).getmWord(),words.get(wrongOptionIndex2).getmWord(),words.get(wrongOptionIndex3).getmWord(),1,getCategory(i));
                    break;
                    case 2:
                        question = new Question(words.get(i).getmTranslation(),words.get(wrongOptionIndex1).getmWord(),words.get(i).getmWord(),words.get(wrongOptionIndex2).getmWord(),words.get(wrongOptionIndex3).getmWord(),2,getCategory(i));
                        break;
                    case 3:
                        question = new Question(words.get(i).getmTranslation(),words.get(wrongOptionIndex1).getmWord(),words.get(wrongOptionIndex2).getmWord(),words.get(i).getmWord(),words.get(wrongOptionIndex3).getmWord(),3,getCategory(i));
                        break;
                    case 4:
                        question = new Question(words.get(i).getmTranslation(),words.get(wrongOptionIndex1).getmWord(),words.get(wrongOptionIndex2).getmWord(),words.get(wrongOptionIndex3).getmWord(),words.get(i).getmWord(),4,getCategory(i));
                        break;

                    default:question = new Question(words.get(1).getmTranslation(),words.get(1).getmWord(),words.get(1).getmWord(),words.get(1).getmWord(),words.get(1).getmWord(),1,getCategory(i));
                }
                addQuestion(question);

            }
    }

    private int getCategory(int i){
        int CATEGORY;
        if(i >= 0 && i < 17){
            CATEGORY = Category.CRIME;
        }
        else if(i >= 17 && i < 39){
            CATEGORY = Category.EDUCATION;
        }
        else if(i >= 39 && i < 67){
            CATEGORY = Category.ENVIRONMENT;
        }
        else if(i >= 67 && i < 95){
            CATEGORY = Category.JOBS;
        }
        else if(i >= 95 && i < 122){
            CATEGORY = Category.MEDICAL;
        }
        else if(i >= 122 && i < 148){
            CATEGORY = Category.RESTAURANT;
        }
        else if(i >= 148 && i < 176){
            CATEGORY = Category.SCIENCE;
        }
        else if(i >= 176 && i < 198){
            CATEGORY = Category.SPORT;
        }
        else if(i >= 198 && i < 226){
            CATEGORY = Category.TRAVEL;
        }
        else{
            CATEGORY = Category.COMPUTER;
        }
        return CATEGORY;
    }
    private void addCategories(Category category){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CategoriesTable.column_name,category.getmName());
        quizDatabase.insert(CategoriesTable.table_name,null,contentValues);
    }
    private void addQuestion(Question question){
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionTable.question_column,question.getQuestion());
        contentValues.put(QuestionTable.option1_column,question.getOption1());
        contentValues.put(QuestionTable.option2_column,question.getOption2());
        contentValues.put(QuestionTable.option3_column,question.getOption3());
        contentValues.put(QuestionTable.option4_column,question.getOption4());
        contentValues.put(QuestionTable.answerNb_column,question.getAnswerNb());
        contentValues.put(QuestionTable.column_category_id,question.getCategoryID());
        quizDatabase.insert(QuestionTable.table_name,null,contentValues);
    }


    public List<Question> getCategoryQuestions(int id) {
        List<Question> questionList = new ArrayList<>();
        quizDatabase = getReadableDatabase();
        Cursor cursor = quizDatabase.rawQuery("SELECT * FROM " + QuestionTable.table_name+" WHERE "+QuestionTable.column_category_id+ " LIKE "+id, null);

        if (cursor.moveToNext()){
            do {
                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndex(QuestionTable._ID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionTable.question_column)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(QuestionTable.option1_column)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(QuestionTable.option2_column)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(QuestionTable.option3_column)));
                question.setOption4(cursor.getString(cursor.getColumnIndex(QuestionTable.option4_column)));
                question.setAnswerNb(cursor.getInt(cursor.getColumnIndex(QuestionTable.answerNb_column)));
                question.setCategoryID(cursor.getInt(cursor.getColumnIndex(QuestionTable.column_category_id)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }
}
