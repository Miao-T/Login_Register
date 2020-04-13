package com.example.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.login_register.CloudSQL.DBConnection;

import static com.example.login_register.Utils.ActivityCollector.finishAll;

public class SettingActivity extends AppCompatActivity {

    private Button mBtnCancel;
    private static final String TAG = "DB_tag" ;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String LoginName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mSharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        LoginName = mSharedPreferences.getString("RememberName","");
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnection.DriverConnection();
            }
        }).start();

        mBtnCancel = findViewById(R.id.btn_cancelAccount);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBConnection.DropTable(LoginName);
                        DBConnection.DeleteAccountData(LoginName);
                        mEditor.putBoolean("has_login",false);
                        mEditor.putBoolean("is_remember",false);
                        mEditor.remove("RememberName");
                        mEditor.remove("is_remember");
                        mEditor.remove("MACAddress");
                        mEditor.remove("StepLastTime");
                        mEditor.apply();
                    }
                }).start();
                finishAll();
                Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
