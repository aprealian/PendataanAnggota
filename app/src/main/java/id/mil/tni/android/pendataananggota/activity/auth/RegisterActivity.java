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

import id.mil.tni.android.pendataananggota.R;
import id.mil.tni.android.pendataananggota.helper.SessionManager;
import id.mil.tni.android.pendataananggota.helper.UserDetailStorage;

public class RegisterActivity extends AppCompatActivity {

    private SessionManager session;
    private LinearLayout btnDaftar;
    private EditText etNama;
    private EditText etNrp;
    private EditText etPassword;
    private EditText etVerifPassword;
    private EditText etEmail;
    private String name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Session Manager
        session = new SessionManager(RegisterActivity.this);
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);
        password = user.get(SessionManager.KEY_PASSWORD);

        UserDetailStorage userDetailStorage = new UserDetailStorage(RegisterActivity.this);

        btnDaftar = (LinearLayout) findViewById(R.id.lv_daftar);
        etNrp = (EditText) findViewById(R.id.et_nrp);
        etNama = (EditText) findViewById(R.id.et_nama);
        etPassword = (EditText) findViewById(R.id.et_password);
        etVerifPassword = (EditText) findViewById(R.id.et_verif_password);
        etEmail = (EditText) findViewById(R.id.et_email);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get user data from session


                if (TextUtils.isEmpty(etNrp.getText().toString()) || TextUtils.isEmpty(etNama.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString()) || TextUtils.isEmpty(etVerifPassword.getText().toString()) || TextUtils.isEmpty(etEmail.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Silahkan isi form yang masih kosong", Toast.LENGTH_SHORT).show();
                } else if (!etPassword.getText().toString().equals(etVerifPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Verifikasi password salah", Toast.LENGTH_SHORT).show();
                } else {
                    session.createLoginSession(etNama.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString(), etNrp.getText().toString(), null, null, null,null, false);

                    /*User userr = new User();
                    userr.setName();
                    userr.setNrp();
                    userr.setPassword();
                    userr.setEmail(email);*/

                    Toast.makeText(RegisterActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, AuthActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }
}
