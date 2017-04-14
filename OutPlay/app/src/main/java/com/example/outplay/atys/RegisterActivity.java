package com.example.outplay.atys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.outplay.R;
import com.example.outplay.ld.MyUser;
import com.example.outplay.utils.updateImage;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterActivity extends AppCompatActivity {
    Button get_check_code;
    Button register;
    EditText username;
    EditText password;
    EditText phonenumber;
    EditText checkcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);

        register = (Button) findViewById(R.id.register);
        get_check_code = (Button) findViewById(R.id.get_check_code);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        phonenumber = (EditText) findViewById(R.id.phonenumber);
        checkcode=(EditText)findViewById(R.id.check_code);

        final CountDownTimer countDownTimer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                get_check_code.setText(millisUntilFinished/1000+"秒后重发");
            }

            @Override
            public void onFinish() {
                get_check_code.setClickable(true);
                get_check_code.setText("获取验证码");
            }
        };

        get_check_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content_phonenumber = phonenumber.getText().toString();
                if (TextUtils.isEmpty(content_phonenumber)) {
                    Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_LONG).show();
                }else {
                    get_check_code.setClickable(false);
                    countDownTimer.start();
                    BmobSMS.requestSMSCode(phonenumber.getText().toString(), "test", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(RegisterActivity.this, "验证码发送成功", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(RegisterActivity.this, "验证码发送失败"+e.getMessage(), Toast.LENGTH_LONG).show();
                                countDownTimer.cancel();
                            }
                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobSMS.verifySmsCode(phonenumber.getText().toString(), checkcode.getText().toString(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(RegisterActivity.this, "验证成功", Toast.LENGTH_LONG).show();
                            userSubmit(username, password, phonenumber);
                        }
                    }
                });
            }
        });

    }

    public void userSubmit(EditText str1, EditText str2, EditText str3) {
        String content_username = str1.getText().toString();
        String content_password = str2.getText().toString();


        if (TextUtils.isEmpty(content_username)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(content_password)) {
            Toast.makeText(this, "密码内容不能为空", Toast.LENGTH_LONG).show();
        }


        MyUser myUser = new MyUser();
        myUser.setUsername(content_username);
        myUser.setPassword(content_password);
        myUser.setMobilePhoneNumber(str3.getText().toString());

        myUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser1 , BmobException e) {
                if (e == null) {
                    String id=myUser1.getObjectId();
                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.logo);
                    updateImage.update(bitmap, id);

                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, ShowActivity.class));
                    finish();
                    WelcomeActivity.replaceInstance.finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册错误信息为" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
