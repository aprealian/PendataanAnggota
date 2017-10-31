package id.mil.tni.android.pendataananggota.activity.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

import id.mil.tni.android.pendataananggota.MainActivity;
import id.mil.tni.android.pendataananggota.R;
import id.mil.tni.android.pendataananggota.helper.SessionManager;

public class AuthActivity extends AppCompatActivity {

    private SessionManager session;
    private LinearLayout btnLogin;
    private LinearLayout btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        session = new SessionManager(AuthActivity.this);
        //Toast.makeText(this, String.valueOf(session.isLoggedIn()), Toast.LENGTH_SHORT).show();

        /*if (session.isLoggedIn()){
            Intent intent = new Intent( AuthActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }*/

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
                //finish();
            }
        });

    }
}
