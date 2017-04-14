package com.example.outplay.atys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.outplay.R;

public class SearchActivity extends AppCompatActivity {
    EditText editText;
    ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_search);


        editText=(EditText)findViewById(R.id.edit_search);
        button=(ImageButton)findViewById(R.id.btn_3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(editText.getText().toString())){
                    Intent intent=new Intent(SearchActivity.this,ShowSearchActivity.class);
                    intent.putExtra("content", editText.getText().toString());
                startActivity(intent);
                }else {
                    Toast.makeText(SearchActivity.this,"请输入内容再搜索",Toast.LENGTH_LONG).show();
                }
            }
        });
        
}

}
