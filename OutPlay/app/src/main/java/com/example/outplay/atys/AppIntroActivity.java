package com.example.outplay.atys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.outplay.R;
import com.example.outplay.frgs.SlideFragment;
import com.github.paolorotolo.appintro.AppIntro;

public class AppIntroActivity extends AppIntro {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(SlideFragment.newInstance(R.layout.fragment_first_intro));
        addSlide(SlideFragment.newInstance(R.layout.fragment_second_intro));
        addSlide(SlideFragment.newInstance(R.layout.fragment_third_intro));


        View view_bottom =findViewById(R.id.bottom);
        view_bottom.getBackground().setAlpha(00);

        View view_separator=findViewById(R.id.bottom_separator);
        view_separator.getBackground().setAlpha(00);
        setDoneText("Enter");

//        setVibrate(true);//需要权限，不然要崩
//        setVibrateIntensity(30);
                  showSkipButton(false);
                  showDoneButton(true);
     }
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this,WelcomeActivity.class));
        finish();
    }

}
