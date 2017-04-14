package com.example.outplay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.outplay.atys.AppIntroActivity;
import com.example.outplay.atys.ShowActivity;
import com.example.outplay.atys.WelcomeActivity;
import com.example.outplay.ld.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bmob初始化
        Bmob.initialize(this, "2e9ddb3cd56abe995e2e3c38c29ba6c6");

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean ifFirstStart=sharedPreferences.getBoolean("firstStart",true);
        if (ifFirstStart) {
            startActivity(new Intent(MainActivity.this, AppIntroActivity.class));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstStart", false);
            //如果对提交的结果关心的话，还是用commit()
            editor.apply();
        }else{
            MyUser myUser=BmobUser.getCurrentUser(MyUser.class);
            if (myUser!=null) {
                startActivity(new Intent(MainActivity.this, ShowActivity.class));
            }else{
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            }
        }
        finish();
    }
}
