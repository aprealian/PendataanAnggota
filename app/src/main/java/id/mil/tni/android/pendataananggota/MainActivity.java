package id.mil.tni.android.pendataananggota;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import id.mil.tni.android.pendataananggota.activity.auth.LoginActivity;
import id.mil.tni.android.pendataananggota.helper.Helper;
import id.mil.tni.android.pendataananggota.helper.SessionManager;
import id.mil.tni.android.pendataananggota.http.PAUpdateRequest;

public class MainActivity extends AppCompatActivity {

    private SessionManager session;
    private ProgressDialog dialog;
    private LinearLayout btnSimpan;
    private TextView tvNama;
    private ImageView ivSignOut;
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
        initSession();
        initView();
        initData();
        initControl();
    }

    private void initSession() {
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
    }

    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");

        ivSignOut = (ImageView) findViewById(R.id.iv_signout);
        btnSimpan = (LinearLayout) findViewById(R.id.lv_simpan);
        tvNama = (TextView) findViewById(R.id.tv_nama);
        etEmail = (EditText) findViewById(R.id.et_email);
        etNomobil = (EditText) findViewById(R.id.et_nomobil);
        etNosim = (EditText) findViewById(R.id.et_nosim);
        etPengalaman = (EditText) findViewById(R.id.et_pengalaman);
        etKeterampilan = (EditText) findViewById(R.id.et_keterampilan);
    }

    private void initData() {
        if (name != null && !name.equals("null") && !TextUtils.isEmpty(name)){
            tvNama.setText(name);
        } else {
            tvNama.setText("-");
        }
        if (email != null && !email.equals("null") && !TextUtils.isEmpty(email)) etEmail.setText(email);
        if (noMobil != null && !noMobil.equals("null") && !TextUtils.isEmpty(noMobil)) etNomobil.setText(noMobil);
        if (noSim  != null && !noSim.equals("null") && !TextUtils.isEmpty(noSim)) etNosim.setText(noSim);
        if (pengalaman  != null && !pengalaman.equals("null") && !TextUtils.isEmpty(pengalaman)) etPengalaman.setText(pengalaman);
        if (keterampilan  != null && !keterampilan.equals("null") && !TextUtils.isEmpty(keterampilan)) etKeterampilan.setText(keterampilan);
    }

    private void initControl() {
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNomobil.getText().toString()) || TextUtils.isEmpty(etNosim.getText().toString()) || TextUtils.isEmpty(etPengalaman.getText().toString()) || TextUtils.isEmpty(etKeterampilan.getText().toString())){
                    Toast.makeText(MainActivity.this, "Silahkan isi form yang masih kosong", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(MainActivity.this, "Pendafatran berhasil disimpan", Toast.LENGTH_SHORT).show();
                    //session.createLoginSession(name, email, password, nrp, etNomobil.getText().toString(), etNosim.getText().toString(), etPengalaman.getText().toString(), etKeterampilan.getText().toString());
                    dialog.show();
                    noMobil = etNomobil.getText().toString();
                    noSim = etNosim.getText().toString();
                    pengalaman = etPengalaman.getText().toString();
                    keterampilan = etKeterampilan.getText().toString();
                    new onUpdateRequest(getApplicationContext(), getString(R.string.api_path_update_profile), noMobil, noSim, pengalaman, keterampilan).execute();
                }
            }
        });

        ivSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(MainActivity.this);
                }
                builder.setTitle("Log out")
                        .setMessage("Apakah Anda ingin keluar ?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                session.logoutUser();
                                finish();
                            }
                        })
                        .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();

            }
        });

    }



    private class onUpdateRequest extends PAUpdateRequest {

        public onUpdateRequest(Context context, String apiPath, String carNum, String simNum, String orgExp, String skills) {
            super(context, apiPath, carNum, simNum, orgExp, skills);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        public JSONObject responseObject() throws JSONException {

            return super.responseObject();
        }

        @Override
        public JSONObject responseSuccess() throws JSONException {
            if (dialog != null) dialog.dismiss();

            try{
                session.updateProfile(noMobil, noSim, pengalaman, keterampilan);
                JSONObject jsonObject = new JSONObject(getJson());
                Helper.handlePopupMessage(MainActivity.this, getMsg(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }, true);
            } catch (JSONException e){
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Terjadi kesalahan pada sistem", Toast.LENGTH_SHORT).show();
            }

            return super.responseSuccess();
        }

        @Override
        public JSONObject responseFailed() throws JSONException {
            if (dialog != null) dialog.dismiss();

            Helper.handlePopupMessage(MainActivity.this, getMsg(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }, true);
            return super.responseFailed();
        }

        @Override
        public String responseError() {
            if (dialog != null) dialog.dismiss();
            Helper.handlePopupMessage(MainActivity.this, "Please, check your internet connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }, true);
            return super.responseError();
        }
    }


}
