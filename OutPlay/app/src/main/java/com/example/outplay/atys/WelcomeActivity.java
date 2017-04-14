package com.example.outplay.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.outplay.R;

public class WelcomeActivity extends AppCompatActivity {
    Button show_login;
    Button show_register;
    Button show_temporary;
    public static Activity replaceInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_welcome);

        show_login=(Button)findViewById(R.id.show_login);
        show_register=(Button)findViewById(R.id.show_register);
        show_temporary=(Button)findViewById(R.id.show_temporary);
        replaceInstance=this;

        show_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
            }
        });
        show_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,RegisterActivity.class));
            }
        });
        show_temporary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,ShowActivity.class));
                finish();
            }
        });

    }
}
