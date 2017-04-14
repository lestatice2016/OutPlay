package com.example.outplay.atys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.outplay.R;
import com.example.outplay.ld.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends AppCompatActivity {
    EditText phonenumber;
    EditText password;
    Button login_usual;
    TextView forget_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);

        phonenumber = (EditText) findViewById(R.id.phonenumber);
        password = (EditText) findViewById(R.id.password);
        forget_password=(TextView)findViewById(R.id.forget_password);
        login_usual = (Button) findViewById(R.id.login_usual);



        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                finish();
            }
        });

        login_usual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSubmit(phonenumber, password);
            }
        });

    }

    public void userSubmit(EditText str1, EditText str2) {
        String content_phone = str1.getText().toString();
        String content_password = str2.getText().toString();

        if (TextUtils.isEmpty(content_phone)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(content_password)) {
            Toast.makeText(this, "密码内容不能为空", Toast.LENGTH_LONG).show();
        }

        MyUser.loginByAccount(content_phone, content_password, new LogInListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (myUser != null) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, ShowActivity.class));
                    finish();
                    //结束欢迎页，避免退出键后还会停在登录页
                    WelcomeActivity.replaceInstance.finish();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}

