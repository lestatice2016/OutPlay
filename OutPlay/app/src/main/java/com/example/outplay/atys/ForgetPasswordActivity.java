package com.example.outplay.atys;

import android.content.Intent;
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

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText phonenumber;
    EditText check_code;
    EditText password_new;
    Button get_check_code;
    Button complete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_forget_password);

        phonenumber=(EditText)findViewById(R.id.phonenumber);
        check_code=(EditText)findViewById(R.id.check_code);
        get_check_code=(Button)findViewById(R.id.get_check_code);
        complete=(Button)findViewById(R.id.complete);
        password_new=(EditText)findViewById(R.id.password_new);

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
                String content_phonenumber=phonenumber.getText().toString();
                if (TextUtils.isEmpty(content_phonenumber)){
                    Toast.makeText(ForgetPasswordActivity.this, "手机号不能为空", Toast.LENGTH_LONG).show();
                }else {
                    get_check_code.setClickable(false);
                    countDownTimer.start();
                    BmobSMS.requestSMSCode(content_phonenumber, "test", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPasswordActivity.this, "验证码发送成功", Toast.LENGTH_LONG).show();
                            }else{
                                countDownTimer.cancel();
                                Toast.makeText(ForgetPasswordActivity.this, "错误信息为"+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUser.resetPasswordBySMSCode(check_code.getText().toString(), password_new.getText().toString(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(ForgetPasswordActivity.this, "密码修改成功", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "错误信息为" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
