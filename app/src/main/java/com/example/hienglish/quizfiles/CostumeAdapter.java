package com.example.hienglish.quizfiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hienglish.R;
import com.example.hienglish.classes.Category;
import com.example.hienglish.*;

import java.util.ArrayList;

public class CostumeAdapter extends ArrayAdapter<QuizCategory> {

    public CostumeAdapter(Context context, ArrayList<QuizCategory> categories) {
        super(context, 0, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        QuizCategory currentCategory = (QuizCategory) getItem(position);

        TextView categoryNameTxt = listItemView.findViewById(R.id.category_name);
       // TextView scoreTxt = listItemView.findViewById(R.id.score_text_view);

        categoryNameTxt.setText(currentCategory.getmName());
        //scoreTxt.setText(currentCategory.getmScore()+"");

        return listItemView;
    }
}
