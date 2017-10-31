package id.mil.tni.android.pendataananggota.activity.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;

import id.mil.tni.android.pendataananggota.MainActivity;
import id.mil.tni.android.pendataananggota.R;
import id.mil.tni.android.pendataananggota.helper.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private SessionManager session;
    private LinearLayout btnLogin;
    private EditText etUsername;
    private EditText etPassword;

    private String name;
    private String email;
    private String noMobil;
    private String noSim;
    private String pengalaman;
    private String keterampilan;
    private String password;
    private String nrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SessionManager(LoginActivity.this);
        HashMap<String, String> user = session.getUserDetails();
        // get user data from session
        user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);
        noMobil = user.get(SessionManager.KEY_NO_MOBIL);
        noSim = user.get(SessionManager.KEY_NO_SIM);
        pengalaman = user.get(SessionManager.KEY_PENGALAMAN);
        keterampilan = user.get(SessionManager.KEY_KETERAMPILAN);
        password = user.get(SessionManager.KEY_PASSWORD);
        nrp = user.get(SessionManager.KEY_NRP);


        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (LinearLayout) findViewById(R.id.lv_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etUsername.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Masukkan username", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etPassword.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Masukkan password", Toast.LENGTH_SHORT).show();
                } else if (!etUsername.getText().toString().equals(email) || !etPassword.getText().toString().equals(password)){
                    Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                } else {
                    session.createLoginSession(name, email, password, nrp, noMobil, noSim, pengalaman, keterampilan, true);
                    session.setLoginSession(true);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });





    }
}
