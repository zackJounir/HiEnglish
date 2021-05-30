package com.example.hienglish.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hienglish.R;
import com.example.hienglish.classes.Category;
import com.example.hienglish.quizfiles.CostumeAdapter;
import com.example.hienglish.quizfiles.QuizActivity;
import com.example.hienglish.quizfiles.QuizCategory;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.example.hienglish.quizfiles.QuizActivity.EXTRA_CODE;

public class QuizFragment extends Fragment {

    ArrayList<QuizCategory> list;
    private  static final int REQUEST_CODE = 1;
    public static final String CATEGORY_ID = "category_id";
    int currentPosition;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quiz_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = new ArrayList<>();
        list.add(new QuizCategory("Sport",0,Category.SPORT));
        list.add(new QuizCategory("Science",0,Category.SCIENCE));
        list.add(new QuizCategory("Restaurant",0,Category.RESTAURANT));
        list.add(new QuizCategory("Medical",0,Category.MEDICAL));
        list.add(new QuizCategory("Jobs",0,Category.JOBS));
        list.add(new QuizCategory("Travel",0,Category.TRAVEL));
        list.add(new QuizCategory("Environment",0,Category.ENVIRONMENT));
        list.add(new QuizCategory("Crime",0,Category.CRIME));
        list.add(new QuizCategory("Education",0,Category.EDUCATION));
        list.add(new QuizCategory("Computer",0,Category.COMPUTER));


        CostumeAdapter adapter = new CostumeAdapter(getContext(),list);
        ListView listView = view.findViewById(R.id.quiz_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {

            currentPosition = position;
            int categoryId  = list.get(position).getmId();

            Intent intent = new Intent(getContext(), QuizActivity.class);
            intent.putExtra(CATEGORY_ID,categoryId);

            startActivityForResult(intent,REQUEST_CODE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode ==RESULT_OK){
                int scoreValue = data.getIntExtra(EXTRA_CODE,0);
                list.get(currentPosition).setmScore(scoreValue);
            }
        }
    }
}
