package com.example.outplay.atys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.outplay.R;

public class MyselfActivity extends AppCompatActivity {
 TextView alter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_myself);

        alter=(TextView)findViewById(R.id.alter_username);
        alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyselfActivity.this,AlterActivity.class));
            }
        });
    }

}


