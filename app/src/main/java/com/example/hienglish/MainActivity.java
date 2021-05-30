package com.example.hienglish;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.hienglish.R.id;
import com.example.hienglish.fragments.CategoryFragment;
import com.example.hienglish.fragments.DefinitionFragment;
import com.example.hienglish.fragments.QuizFragment;
import com.example.hienglish.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Fragment selectedFragment = null;
    private static final String CHANEL_ID = "chanel1";
    NotificationManagerCompat notificationManagerCompat;
    Intent sendIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(id.fragments_container,new CategoryFragment()).commit();

        NavigationView navView = findViewById(R.id.nav_layout);
        BottomNavigationView bottomNavView = findViewById(R.id.botton_nav);

        navView.setNavigationItemSelectedListener(navListener);
        bottomNavView.setOnNavigationItemSelectedListener(bottomNavListener);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        creatChanelId();
         sendIntent = new Intent();

    }

    //SuppressLint to get rid of the warnings
    @SuppressLint("NonConstantResourceId")
    NavigationView.OnNavigationItemSelectedListener navListener = item -> {
        switch (item.getItemId()){

            case id.settings:
                selectedFragment = new SettingsFragment();
                break;
            case id.pay:
                selectedFragment = new QuizFragment();
                break;
            case id.share:
                sendMessage();
                break;
            case id.rate_review:
                openPlayStore();
                break;
            case id.about:
                openPlayStore();
                break;

        }
        if(item.getItemId() != id.rate_review && item.getItemId() != id.share){
            getSupportFragmentManager().beginTransaction().replace(id.fragments_container,selectedFragment).commit();
            closeNavDrawer();
        }

        return true;
    };

    private void openPlayStore(){
        Toast.makeText(this, "open play store", Toast.LENGTH_SHORT).show();
    }
    private void sendMessage(){
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Let's enjoy learning english together <LINK>");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

    }
    private void creatChanelId(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID,"chanel1", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.setDescription("this is chanel 1");
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(notificationChannel);
        }
    }
    private void sendNotification(){
            Notification notification =  new NotificationCompat.Builder(this,CHANEL_ID)
                .setSmallIcon(R.drawable.ic_about)
                .setContentTitle("Let's learn a new word")
                .setPriority(Notification.PRIORITY_HIGH)
                .build();

        notificationManagerCompat.notify(1,notification);
    }
    //SuppressLint to get rid of the warnings
    @SuppressLint("NonConstantResourceId")
    BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = item -> {
        switch (item.getItemId()){
            case id.category:
                selectedFragment = new CategoryFragment();
                break;
            case id.quiz:
                selectedFragment = new QuizFragment();
                break;
            case id.translate:
                selectedFragment = new DefinitionFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(id.fragments_container,selectedFragment).commit();
        return true;
    };

    private void closeNavDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeNavDrawer();
        }
        else if(count > 0){
            getSupportFragmentManager().beginTransaction().replace(id.fragments_container,new CategoryFragment()).commit();
        }
        else {
            super.onBackPressed();
        }
    }

}