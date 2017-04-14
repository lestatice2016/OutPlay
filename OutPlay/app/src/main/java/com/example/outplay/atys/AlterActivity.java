package com.example.outplay.atys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.outplay.R;
import com.example.outplay.ld.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class AlterActivity extends AppCompatActivity {
EditText new_username;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_alter);

        new_username=(EditText)findViewById(R.id.new_username);
        submit=(Button)findViewById(R.id.alter_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUser mycurrentuser= BmobUser.getCurrentUser(MyUser.class);
                MyUser myUser=new MyUser();
                myUser.setUsername(new_username.getText().toString());
                myUser.update(mycurrentuser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            Toast.makeText(AlterActivity.this,"信息更新成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AlterActivity.this,"信息更新失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}
