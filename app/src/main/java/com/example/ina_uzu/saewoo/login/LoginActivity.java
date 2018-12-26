package com.example.ina_uzu.saewoo.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ina_uzu.saewoo.calendar.CalendarActivity;
import com.example.ina_uzu.saewoo.R;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et_id = findViewById(R.id.et_id);
        final EditText et_pwd= findViewById(R.id.et_pwd);
        Button bt_login= findViewById(R.id.bt_login);

        et_pwd.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CalendarActivity.class);
                String id = et_id.getText().toString();
                String pwd = et_pwd.getText().toString();

                //ina
                if( id.trim().equals(LoginInfo.getInaID()) && pwd.trim().equals(LoginInfo.getInaPWD()) ) {
                    LoginInfo.setWho(LoginInfo.ina);
                    startActivity(intent);
                }

                //jaewoo
                else if( id.trim().equals(LoginInfo.getJaewooID()) && pwd.trim().equals(LoginInfo.getJaewooPWD()) ) {
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

    }
}
