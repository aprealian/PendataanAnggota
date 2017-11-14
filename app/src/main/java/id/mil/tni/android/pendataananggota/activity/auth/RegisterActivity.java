package id.mil.tni.android.pendataananggota.activity.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import id.mil.tni.android.pendataananggota.R;
import id.mil.tni.android.pendataananggota.data.Matra;
import id.mil.tni.android.pendataananggota.helper.Helper;
import id.mil.tni.android.pendataananggota.helper.SessionManager;
import id.mil.tni.android.pendataananggota.helper.UserDetailStorage;
import id.mil.tni.android.pendataananggota.http.PACekNrpRequest;
import id.mil.tni.android.pendataananggota.http.PARegisterRequest;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, id.mil.tni.android.pendataananggota.view.DatePicker.IDatePickerListener {

    private SessionManager session;
    private MatraAdapter adapter;
    private ProgressDialog dialog;
    private LinearLayout btnDaftar;
    private TextView tvBtnDaftar;
    private LinearLayout btnCekNrp;
    private TextView tvCekNrp;
    private Spinner spMatra;
    private EditText etNama;
    private EditText etNrp;
    private TextView tvKetNrp;
    private EditText etPassword;
    private EditText etVerifPassword;
    private EditText etEmail;
    private TextView tvTanggalLahir;
    private EditText etTglLahirHari;
    private EditText etTglLahirBulan;
    private EditText etTglLahirTahun;
    private String name, nrp, email, password, retypePassword, matra, tgllahir;
    private DatePickerDialog datePickerDialog;
    private id.mil.tni.android.pendataananggota.view.DatePicker datePickerListDialog;

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
        data.add(new Matra(null, "Pilih Satuan"));
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
                if (TextUtils.isEmpty(etNrp.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "NRP tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etNama.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etTglLahirHari.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Tanggal Lahir tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etEmail.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (!Helper.isValidEmaillId(etEmail.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Email tidak valid", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (etPassword.getText().length() < 6 ){
                    Toast.makeText(RegisterActivity.this, "Panjang password minimal 6 karakter", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etVerifPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Verifikasi Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
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
                Matra m = (Matra) spMatra.getSelectedItem();
                matra = m.getId();
                nrp = etNrp.getText().toString();
                if (TextUtils.isEmpty(matra)){
                    Toast.makeText(RegisterActivity.this, "Silahkan pilih satuan kerja terlebih dahulu", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(nrp)){
                    Toast.makeText(RegisterActivity.this, "NRP tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    new onCekNRPRequest(getApplicationContext(), getString(R.string.api_path_cek_nrp), nrp, matra).execute();
                }
            }
        });

        etTglLahirHari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //datePickerDialog.show();
                datePickerListDialog.show();
            }
        });

    }

    private void initView() {

        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(RegisterActivity.this, RegisterActivity.this, year-17, 8, 17);

        spMatra = (Spinner) findViewById(R.id.sp_matra);
        btnDaftar = (LinearLayout) findViewById(R.id.lv_daftar);
        tvBtnDaftar = (TextView) findViewById(R.id.tv_btn_daftar);
        tvTanggalLahir = (TextView) findViewById(R.id.tv_tgllahir_hari);
        etNrp = (EditText) findViewById(R.id.et_nrp);
        etNama = (EditText) findViewById(R.id.et_nama);
        etPassword = (EditText) findViewById(R.id.et_password);
        etVerifPassword = (EditText) findViewById(R.id.et_verif_password);
        etEmail = (EditText) findViewById(R.id.et_email);
        etTglLahirHari = (EditText) findViewById(R.id.et_tgllahir_hari);
        etTglLahirBulan = (EditText) findViewById(R.id.et_tgllahir_bulan);
        etTglLahirTahun = (EditText) findViewById(R.id.et_tgllahir_tahun);
        btnCekNrp = (LinearLayout) findViewById(R.id.lv_cek_nrp);
        tvCekNrp = (TextView) findViewById(R.id.tv_cek_nrp);
        tvKetNrp = (TextView) findViewById(R.id.tv_ket_nrp);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");

        disableForm();
    }

    private void disableForm() {
        spMatra.setEnabled(true);
        etNrp.setEnabled(true);
        btnCekNrp.setEnabled(true);

        etNama.setEnabled(false);
        etPassword.setEnabled(false);
        etVerifPassword.setEnabled(false);
        etEmail.setEnabled(false);
        etTglLahirHari.setEnabled(false);
        etTglLahirBulan.setEnabled(false);
        etTglLahirTahun.setEnabled(false);
        btnDaftar.setEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvBtnDaftar.setTextColor(getResources().getColor(R.color.colorGrey1, getTheme()));
        }else {
            tvBtnDaftar.setTextColor(getResources().getColor(R.color.colorGrey1));
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //>= API 21
            btnDaftar.setBackground(getResources().getDrawable(R.drawable.btn_reactangle_grey, getApplicationContext().getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            btnDaftar.setBackground(getResources().getDrawable(R.drawable.btn_reactangle_grey));
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            btnDaftar.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_reactangle_grey));
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvCekNrp.setTextColor(getResources().getColor(R.color.colorRed1, getTheme()));
        }else {
            tvCekNrp.setTextColor(getResources().getColor(R.color.colorRed1));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //>= API 21
            btnCekNrp.setBackground(getResources().getDrawable(R.drawable.btn_reactangle_white, getApplicationContext().getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            btnCekNrp.setBackground(getResources().getDrawable(R.drawable.btn_reactangle_white));
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            btnCekNrp.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_reactangle_white));
        }

    }

    private void enableForm() {
        spMatra.setEnabled(false);
        etNrp.setEnabled(false);
        btnCekNrp.setEnabled(false);

        etNama.setEnabled(true);
        etPassword.setEnabled(true);
        etVerifPassword.setEnabled(true);
        etEmail.setEnabled(true);
        etTglLahirHari.setEnabled(true);
        etTglLahirBulan.setEnabled(true);
        etTglLahirTahun.setEnabled(true);
        btnDaftar.setEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvBtnDaftar.setTextColor(getResources().getColor(R.color.colorRed1, getTheme()));
        }else {
            tvBtnDaftar.setTextColor(getResources().getColor(R.color.colorRed1));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //>= API 21
            btnDaftar.setBackground(getResources().getDrawable(R.drawable.btn_reactangle_white, getApplicationContext().getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            btnDaftar.setBackground(getResources().getDrawable(R.drawable.btn_reactangle_white));
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            btnDaftar.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_reactangle_white));
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvCekNrp.setTextColor(getResources().getColor(R.color.colorGrey1, getTheme()));
        }else {
            tvCekNrp.setTextColor(getResources().getColor(R.color.colorGrey1));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //>= API 21
            btnCekNrp.setBackground(getResources().getDrawable(R.drawable.btn_reactangle_grey, getApplicationContext().getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            btnCekNrp.setBackground(getResources().getDrawable(R.drawable.btn_reactangle_grey));
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            btnCekNrp.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_reactangle_grey));
        }

    }


    private void initSession() {
        // Session Manager
        session = new SessionManager(RegisterActivity.this);
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);
        password = user.get(SessionManager.KEY_PASSWORD);

        UserDetailStorage userDetailStorage = new UserDetailStorage(RegisterActivity.this);

        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerListDialog = new id.mil.tni.android.pendataananggota.view.DatePicker(RegisterActivity.this, year-17, 8, 17);
        datePickerListDialog.setDatePickerListener(RegisterActivity.this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        tvTanggalLahir.setVisibility(View.VISIBLE);
        etTglLahirHari.setText(String.valueOf(dayOfMonth+"/"+monthOfYear+"/"+year));
    }

    @Override
    public void onClear() {

    }

    @Override
    public void onOK(int year, int month, int day) {
        tvTanggalLahir.setVisibility(View.VISIBLE);
        etTglLahirHari.setText(String.valueOf(day)+"-"+String.valueOf(month)+"-"+String.valueOf(year));
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

        public onCekNRPRequest(Context context, String apiPath, String nrp, String uo) {
            super(context, apiPath, nrp, uo);
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
            //tvKetNrp.setText(getMsg());
            enableForm();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                btnCekNrp.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.btn_reactangle_grey));
            }







            //Toast.makeText(RegisterActivity.this, getJson(), Toast.LENGTH_SHORT).show();
            //etTglLahirHari.setText(obj.getString("tgllahir").substring(0,2));
            //etTglLahirBulan.setText(obj.getString("tgllahir").substring(2,4));
            //etTglLahirTahun.setText(obj.getString("tgllahir").substring(4,8));
            //Toast.makeText(RegisterActivity.this, obj.toString(), Toast.LENGTH_SHORT).show();

            JSONObject objA = new JSONObject(getJson());
            JSONObject obj = objA.getJSONObject("data");
            etNama.setText(obj.getString("nama"));

            enableForm();

            return super.responseSuccess();
        }

        @Override
        public JSONObject responseFailed() throws JSONException {
            if (dialog != null) dialog.dismiss();
            Helper.handlePopupMessage(RegisterActivity.this, getMsg(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //tvKetNrp.setText(getMsg());
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
