package com.example.ina_uzu.saewoo.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ina_uzu.saewoo.R;
import com.example.ina_uzu.saewoo.main.MainActivity;

public class LoginActivity extends Activity {
    String sfName="login";
    boolean saveMode =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText et_id = findViewById(R.id.et_id);
        final EditText et_pwd= findViewById(R.id.et_pwd);
        Button bt_login= findViewById(R.id.bt_login);
        Button bt_save = findViewById(R.id.bt_save);

        SharedPreferences sf =getSharedPreferences(sfName, MODE_PRIVATE);
        String savedId = sf.getString("id", "");
        String savedPwd = sf.getString("pwd", "");

        if(savedId.length()>0) {
            et_id.setText(savedId);
            et_pwd.setText(savedPwd);
        }

        et_pwd.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                String id = et_id.getText().toString();
                String pwd = et_pwd.getText().toString();

                if(saveMode){
                    SharedPreferences sf =getSharedPreferences(sfName, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sf.edit();
                    editor.putString("id", id);
                    editor.putString("pwd", pwd);
                    editor.commit();
                }

                else{
                    SharedPreferences sf =getSharedPreferences(sfName, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sf.edit();
                    editor.clear();
                    editor.commit();
                }

                //ina
                if( id.trim().equals(LoginInfo.getInaID()) && pwd.trim().equals(LoginInfo.getInaPWD()) ) {
                    Toast.makeText(LoginActivity.this, "아농 이나씨",Toast.LENGTH_SHORT).show();
                    LoginInfo.setWho(LoginInfo.ina);
                    startActivity(intent);
                }

                //jaewoo
                else if( id.trim().equals(LoginInfo.getJaewooID()) && pwd.trim().equals(LoginInfo.getJaewooPWD()) ) {
                    Toast.makeText(LoginActivity.this, "아농 새우씨",Toast.LENGTH_SHORT).show();
                    LoginInfo.setWho(LoginInfo.jaewoo);
                    startActivity(intent);
                }

                //Login error
                else{
                    et_id.setText("");
                    et_pwd.setText("");
                    Toast.makeText(LoginActivity.this, "잘못된 아이디 or 비밀번호입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!saveMode)
                    v.setBackground(getResources().getDrawable(R.drawable.nemo_checked));
                else
                    v.setBackground(getResources().getDrawable(R.drawable.nemo));
                saveMode = !saveMode;
            }
        });
    }


}
