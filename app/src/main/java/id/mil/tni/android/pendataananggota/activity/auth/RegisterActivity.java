package id.mil.tni.android.pendataananggota.activity.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import id.mil.tni.android.pendataananggota.R;
import id.mil.tni.android.pendataananggota.data.Matra;
import id.mil.tni.android.pendataananggota.helper.Helper;
import id.mil.tni.android.pendataananggota.helper.SessionManager;
import id.mil.tni.android.pendataananggota.helper.UserDetailStorage;
import id.mil.tni.android.pendataananggota.http.PACekNrpRequest;
import id.mil.tni.android.pendataananggota.http.PARegisterRequest;

public class RegisterActivity extends AppCompatActivity {

    private SessionManager session;
    private MatraAdapter adapter;
    private ProgressDialog dialog;
    private LinearLayout btnDaftar;
    private TextView btnCekNrp;
    private Spinner spMatra;
    private EditText etNama;
    private EditText etNrp;
    private TextView tvKetNrp;
    private EditText etPassword;
    private EditText etVerifPassword;
    private EditText etEmail;
    private EditText etTglLahirHari;
    private EditText etTglLahirBulan;
    private EditText etTglLahirTahun;
    private String name, nrp, email, password, retypePassword, matra, tgllahir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initSession();
        initView();
        initControl();
        initData();
    }

    private void initData() {
        ArrayList<Matra> data = new ArrayList<Matra>();
        data.add(new Matra("2", "Mabes TNI"));
        data.add(new Matra("3", "Angkatan Darat"));
        data.add(new Matra("4", "Angkatan Laut"));
        data.add(new Matra("5", "Angkatan Udara"));

        MatraAdapter adapter = new MatraAdapter(this,R.layout.view_list_matra, data);
        spMatra.setAdapter(adapter);
    }

    private void initControl() {
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get user data from session
                if (TextUtils.isEmpty(etNrp.getText().toString()) || TextUtils.isEmpty(etNama.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString()) || TextUtils.isEmpty(etVerifPassword.getText().toString()) || TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etTglLahirHari.getText().toString()) || TextUtils.isEmpty(etTglLahirBulan.getText().toString()) || TextUtils.isEmpty(etTglLahirTahun.getText().toString()) ){
                    Toast.makeText(RegisterActivity.this, "Silahkan isi form yang masih kosong", Toast.LENGTH_SHORT).show();
                } else if (!Helper.isValidEmaillId(etEmail.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Email tidak valid", Toast.LENGTH_SHORT).show();
                } else if (etPassword.getText().length() < 6 ){
                    Toast.makeText(RegisterActivity.this, "Panjang password minimal 6 karakter", Toast.LENGTH_SHORT).show();
                } else if (!etPassword.getText().toString().equals(etVerifPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Verifikasi password salah", Toast.LENGTH_SHORT).show();
                } else {
                    //session.createLoginSession(etNama.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString(), etNrp.getText().toString(), null, null, null,null, false);

                    /*User userr = new User();
                    userr.setName();
                    userr.setNrp();
                    userr.setPassword();
                    userr.setEmail(email);*/

                   /* Toast.makeText(RegisterActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, AuthActivity.class);
                    startActivity(intent);
                    finish();*/
                    dialog.show();
                    name = etNama.getText().toString().toUpperCase();
                    email = etEmail.getText().toString();
                    nrp = etNrp.getText().toString();
                    password = etPassword.getText().toString();
                    retypePassword = etVerifPassword.getText().toString();
                    Matra m = (Matra) spMatra.getSelectedItem();
                    matra = m.getId();
                    //Toast.makeText(RegisterActivity.this, spMatra.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    tgllahir = etTglLahirHari.getText().toString()+"-"+etTglLahirBulan.getText().toString()+"-"+etTglLahirTahun.getText().toString();
                    new onRegsiterRequest(getApplicationContext(), getString(R.string.api_path_register), name, nrp, email, password, retypePassword, matra, tgllahir).execute();
                }

            }
        });

        btnCekNrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNrp.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "NRP tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    nrp = etNrp.getText().toString();
                    new onCekNRPRequest(getApplicationContext(), getString(R.string.api_path_cek_nrp), nrp).execute();
                }
            }
        });

    }

    private void initView() {
        spMatra = (Spinner) findViewById(R.id.sp_matra);
        btnDaftar = (LinearLayout) findViewById(R.id.lv_daftar);
        etNrp = (EditText) findViewById(R.id.et_nrp);
        etNama = (EditText) findViewById(R.id.et_nama);
        etPassword = (EditText) findViewById(R.id.et_password);
        etVerifPassword = (EditText) findViewById(R.id.et_verif_password);
        etEmail = (EditText) findViewById(R.id.et_email);
        etTglLahirHari = (EditText) findViewById(R.id.et_tgllahir_hari);
        etTglLahirBulan = (EditText) findViewById(R.id.et_tgllahir_bulan);
        etTglLahirTahun = (EditText) findViewById(R.id.et_tgllahir_tahun);
        btnCekNrp = (TextView) findViewById(R.id.btn_cek_nrp);
        tvKetNrp = (TextView) findViewById(R.id.tv_ket_nrp);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");

        disableForm();
    }

    private void disableForm() {
        spMatra.setEnabled(false);
        etNama.setEnabled(false);
        etPassword.setEnabled(false);
        etVerifPassword.setEnabled(false);
        etEmail.setEnabled(false);
        etTglLahirHari.setEnabled(false);
        etTglLahirBulan.setEnabled(false);
        etTglLahirTahun.setEnabled(false);
        btnDaftar.setEnabled(false);
    }

    private void enableForm() {
        spMatra.setEnabled(true);
        etNama.setEnabled(true);
        etPassword.setEnabled(true);
        etVerifPassword.setEnabled(true);
        etEmail.setEnabled(true);
        etTglLahirHari.setEnabled(true);
        etTglLahirBulan.setEnabled(true);
        etTglLahirTahun.setEnabled(true);
        btnDaftar.setEnabled(true);
    }


    private void initSession() {
        // Session Manager
        session = new SessionManager(RegisterActivity.this);
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);
        password = user.get(SessionManager.KEY_PASSWORD);

        UserDetailStorage userDetailStorage = new UserDetailStorage(RegisterActivity.this);
    }


    private class onRegsiterRequest extends PARegisterRequest {

        public onRegsiterRequest(Context context, String apiPath, String name, String nrp, String email, String password, String retypePassword, String matra, String tgllahir) {
            super(context, apiPath, name, nrp, email, password, retypePassword, matra, tgllahir);
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
            //session.createLoginSession(name, email, password, nrp, noMobil, noSim, pengalaman, keterampilan, true);
            Helper.handlePopupMessage(RegisterActivity.this, getMsg(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(RegisterActivity.this, AuthActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }, false);
            return super.responseSuccess();
        }

        @Override
        public JSONObject responseFailed() throws JSONException {
            if (dialog != null) dialog.dismiss();
            Helper.handlePopupMessage(RegisterActivity.this, getMsg(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }, true);
            return super.responseFailed();
        }

        @Override
        public String responseError() {
            if (dialog != null) dialog.dismiss();
            Helper.handlePopupMessage(RegisterActivity.this, "Please, check your internet connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }, true);
            return super.responseError();
        }

    }





    private class onCekNRPRequest extends PACekNrpRequest {

        public onCekNRPRequest(Context context, String apiPath, String nrp) {
            super(context, apiPath, nrp);
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
            //session.createLoginSession(name, email, password, nrp, noMobil, noSim, pengalaman, keterampilan, true);
            tvKetNrp.setText(getMsg());
            enableForm();
            //Toast.makeText(RegisterActivity.this, getJson(), Toast.LENGTH_SHORT).show();
            JSONObject objA = new JSONObject(getJson());
            JSONObject obj = objA.getJSONObject("data");
            //Toast.makeText(RegisterActivity.this, obj.toString(), Toast.LENGTH_SHORT).show();
            etNama.setText(obj.getString("nama"));
            etTglLahirHari.setText(obj.getString("tgllahir").substring(0,2));
            etTglLahirBulan.setText(obj.getString("tgllahir").substring(2,4));
            etTglLahirTahun.setText(obj.getString("tgllahir").substring(4,8));
            return super.responseSuccess();
        }

        @Override
        public JSONObject responseFailed() throws JSONException {
            if (dialog != null) dialog.dismiss();
            Helper.handlePopupMessage(RegisterActivity.this, getMsg(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    tvKetNrp.setText(getMsg());
                    disableForm();
                }
            }, true);
            return super.responseFailed();
        }

        @Override
        public String responseError() {
            if (dialog != null) dialog.dismiss();
            disableForm();
            Helper.handlePopupMessage(RegisterActivity.this, "Please, check your internet connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }, true);
            return super.responseError();
        }

    }


}
