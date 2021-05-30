package com.example.hienglish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hienglish.classes.Word;

import java.util.List;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.ViewHolder> {

    public List<Word> mWordList;
    LayoutInflater mInflater;
    ViewPager2 mViewPager2;

    public PagerAdapter(Context context, List<Word> list , ViewPager2 viewpager){
        this.mInflater = LayoutInflater.from(context);
        this.mWordList = list;
        this.mViewPager2 = viewpager;

    }


    @NonNull
    @Override
    public PagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.words_fragment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagerAdapter.ViewHolder holder, int position) {
        Word vocab = mWordList.get(position);

        holder.wordTxt.setText(vocab.getmWord());
        holder.translationTxt.setText(vocab.getmTranslation());
        if(vocab.getmExample()==null){
            holder.usageTitle.setVisibility(View.INVISIBLE);
        }
        holder.exampleTxt.setText(vocab.getmExample());
        holder.defaultLang.setText(vocab.getmDefaultLang());
        holder.pageCount.setText(position+1+"/"+getItemCount());
    }



    @Override
    public int getItemCount() {
        return mWordList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView wordTxt,translationTxt,usageTitle,exampleTxt,pageCount,defaultLang;

        public ViewHolder(View itemView){
            super(itemView);
            wordTxt = itemView.findViewById(R.id.word_txt_view);
            translationTxt = itemView.findViewById(R.id.translation_txt_view);
            usageTitle = itemView.findViewById(R.id.usage_title);
            exampleTxt = itemView.findViewById(R.id.example_txt_view);
            defaultLang = itemView.findViewById(R.id.default_language);
            pageCount = itemView.findViewById(R.id.page_count);

        }

    }
}
