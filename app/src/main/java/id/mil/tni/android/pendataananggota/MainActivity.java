package id.mil.tni.android.pendataananggota;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

import id.mil.tni.android.pendataananggota.activity.auth.AuthActivity;
import id.mil.tni.android.pendataananggota.activity.auth.RegisterActivity;
import id.mil.tni.android.pendataananggota.helper.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager session;
    private LinearLayout btnSimpan;
    private EditText etEmail;
    private EditText etNomobil;
    private EditText etNosim;
    private EditText etPengalaman;
    private EditText etKeterampilan;
    private HashMap<String, String> user;

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
        setContentView(R.layout.activity_main);

        // Session Manager
        session = new SessionManager(MainActivity.this);
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

        /*Log.e("cek data nama ", name);
        Log.e("cek data email ", email);*/


        btnSimpan = (LinearLayout) findViewById(R.id.lv_simpan);
        etEmail = (EditText) findViewById(R.id.et_email);
        etNomobil = (EditText) findViewById(R.id.et_nomobil);
        etNosim = (EditText) findViewById(R.id.et_nosim);
        etPengalaman = (EditText) findViewById(R.id.et_pengalaman);
        etKeterampilan = (EditText) findViewById(R.id.et_keterampilan);

        etEmail.setText(email);
        etNomobil.setText(noMobil);
        etNosim.setText(noSim);
        etPengalaman.setText(pengalaman);
        etKeterampilan.setText(keterampilan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNomobil.getText().toString()) || TextUtils.isEmpty(etNosim.getText().toString()) || TextUtils.isEmpty(etPengalaman.getText().toString()) || TextUtils.isEmpty(etKeterampilan.getText().toString())){
                    Toast.makeText(MainActivity.this, "Silahkan isi form yang masih kosong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Pendafatran berhasil disimpan", Toast.LENGTH_SHORT).show();
                    session.createLoginSession(name, email, password, nrp, etNomobil.getText().toString(), etNosim.getText().toString(), etPengalaman.getText().toString(), etKeterampilan.getText().toString(), true);
                }
            }
        });

    }
}
