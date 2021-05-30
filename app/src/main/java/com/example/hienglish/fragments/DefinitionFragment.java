package com.example.hienglish.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hienglish.DictionaryRequest;
import com.example.hienglish.R;

import java.util.Objects;

public class DefinitionFragment extends Fragment {

    String url;
    EditText searchWord;
    TextView definitionTxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.definition_fragment,container,false);
    }

    private String dictionaryEntries() {
        final String language = "en-gb";
        final String word = searchWord.getText().toString();
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchWord = view.findViewById(R.id.search_word);
        definitionTxt = view.findViewById(R.id.definition);

        searchWord.setSingleLine(true);
        searchWord.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        searchWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(isConnected()){
                    definitionTxt.setText("....");
                    if(actionId == EditorInfo.IME_ACTION_SEARCH){
                        sendRequestOnClick();
                        return true;
                    }
                }else {
                    showAlert();
                }

                return false;
            }
        });
    }

    public void sendRequestOnClick(){
        DictionaryRequest dr = new DictionaryRequest(getContext(),definitionTxt);
        url = dictionaryEntries();
        dr.execute(url);
    }
    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) Objects.requireNonNull(getContext()).getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    public void showAlert(){
        new AlertDialog.Builder(getContext())
                .setMessage("CHECK YOUR INTERNET CONNECTION!")
                .setNegativeButton("Ok", (dialog, which) -> {
                    dialog.cancel();
                })
                .show();
    }
}
