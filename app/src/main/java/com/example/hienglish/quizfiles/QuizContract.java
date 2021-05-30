package com.example.hienglish.quizfiles;

import android.provider.BaseColumns;

public final class QuizContract {

    //private constructor to provoke creating an instance of this class
    private QuizContract(){}

    public static class CategoriesTable implements BaseColumns {
        public static final String table_name = "quiz_categories";
        public static final String column_name = "name";
    }

    public static class QuestionTable implements BaseColumns {
        public static final String table_name ="quiz_questions";
        public static final String question_column ="question";
        public static final String option1_column ="option1";
        public static final String option2_column ="option2";
        public static final String option3_column ="option3";
        public static final String option4_column ="option4";
        public static final String answerNb_column ="answerNb";
        public static final String column_category_id = "category_id";
    }
}
