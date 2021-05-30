package com.example.hienglish.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hienglish.AlertReceiver;
import com.example.hienglish.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.DateFormat;
import java.util.Calendar;

public class SettingsFragment extends Fragment {

    ImageView img;
    SwitchMaterial switchMaterial,switchMaterial1,switchMaterial2,switchMaterial3;
    int mHour , mMinute;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switchMaterial = view.findViewById(R.id.alarm_view);
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Calendar calendar = Calendar.getInstance();
                    mHour = calendar.get(Calendar.HOUR_OF_DAY);
                    mMinute = calendar.get(Calendar.MINUTE);
                    TimePickerDialog timePicker = new TimePickerDialog(getActivity(),R.style.DialogTheme,timeSetListener,mHour,mMinute, android.text.format.DateFormat.is24HourFormat(getContext()));
                    timePicker.show();

                }else {
                    cancelAlarm();
                    Toast.makeText(getContext(),"reminder turned off",Toast.LENGTH_LONG).show();
                }

            }
        });

        switchMaterial1 = view.findViewById(R.id.alarm_view2);
        switchMaterial1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getContext(),"yyyyyyy",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(),"nnnnnnnn",Toast.LENGTH_LONG).show();
                }

            }
        });

        switchMaterial2 = view.findViewById(R.id.alarm_view3);
        switchMaterial2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getContext(),"arabic on",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(),"arabic of",Toast.LENGTH_LONG).show();
                }

            }
        });

        switchMaterial3 = view.findViewById(R.id.alarm_view4);
        switchMaterial3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
   
                }else {
                    Toast.makeText(getContext(),"arabic of",Toast.LENGTH_LONG).show();
                }

            }
        });
    }



        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
              Calendar calendar = Calendar.getInstance();

              calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
              calendar.set(Calendar.MINUTE,minute);
              calendar.set(Calendar.SECOND,0);

                Toast.makeText(getContext(),"reminder set on "+ DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()),Toast.LENGTH_LONG).show();
              startAlarm(calendar);
            }
        };

    private void startAlarm(Calendar calendar){
        AlarmManager alarmManager = getActivity().getSystemService(AlarmManager.class);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),1,intent,0);
        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE,1);
        }
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }
    private void cancelAlarm(){
        AlarmManager alarmManager = getActivity().getSystemService(AlarmManager.class);
        Intent intent = new Intent(getContext(),AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),1,intent,0);
        alarmManager.cancel(pendingIntent);
    }
}
