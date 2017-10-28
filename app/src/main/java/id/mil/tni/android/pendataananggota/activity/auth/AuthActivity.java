package id.mil.tni.android.pendataananggota.activity.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import id.mil.tni.android.pendataananggota.R;

public class AuthActivity extends AppCompatActivity {

    private LinearLayout btnLogin;
    private LinearLayout btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnLogin = (LinearLayout) findViewById(R.id.lv_login);
        btnDaftar = (LinearLayout) findViewById(R.id.lv_daftar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( AuthActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( AuthActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
