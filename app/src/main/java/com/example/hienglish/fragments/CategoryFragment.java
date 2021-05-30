package com.example.hienglish.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.hienglish.R;
import com.example.hienglish.classes.Category;
import com.example.hienglish.wordsActivitys.TopicActivity;

public class CategoryFragment extends Fragment {

    Intent categorieIntent;
    public static final String EXTRAS_NAME ="extras_name";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_fragment,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView cardView1 = view.findViewById(R.id.card1);
        CardView cardView2 = view.findViewById(R.id.card2);
        CardView cardView3 = view.findViewById(R.id.card3);
        CardView cardView4 = view.findViewById(R.id.card4);
        CardView cardView5 = view.findViewById(R.id.card5);
        CardView cardView6 = view.findViewById(R.id.card6);
        CardView cardView7 = view.findViewById(R.id.card7);
        CardView cardView8 = view.findViewById(R.id.card8);
        CardView cardView9 = view.findViewById(R.id.card9);
        CardView cardView10 = view.findViewById(R.id.card10);

        cardView1.setOnClickListener(clickListener);
        cardView2.setOnClickListener(clickListener);
        cardView3.setOnClickListener(clickListener);
        cardView4.setOnClickListener(clickListener);
        cardView5.setOnClickListener(clickListener);
        cardView6.setOnClickListener(clickListener);
        cardView7.setOnClickListener(clickListener);
        cardView8.setOnClickListener(clickListener);
        cardView9.setOnClickListener(clickListener);
        cardView10.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = v -> {
        categorieIntent = new Intent(getContext(), TopicActivity.class);
        int extras;
        switch (v.getId()){
            case R.id.card1:
                extras = Category.SPORT;
                break;
            case R.id.card2:
                extras = Category.SCIENCE;
                break;
            case R.id.card3:
                extras = Category.RESTAURANT;
                break;
            case R.id.card4:
                extras = Category.MEDICAL;
                break;
            case R.id.card5:
                extras = Category.JOBS;
                break;
            case R.id.card6:
                extras = Category.TRAVEL;
                break;
            case R.id.card7:
                extras = Category.ENVIRONMENT;
                break;
            case R.id.card8:
                extras = Category.CRIME;
                break;
            case R.id.card9:
                extras = Category.EDUCATION;
                break;
            case R.id.card10:
                extras = Category.EDUCATION;
                break;
            default:extras = Category.SPORT;
        }
        categorieIntent.putExtra(EXTRAS_NAME,extras);
        startActivity(categorieIntent);
    };
    
}
