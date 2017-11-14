package id.mil.tni.android.pendataananggota;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import id.mil.tni.android.pendataananggota.activity.auth.LoginActivity;
import id.mil.tni.android.pendataananggota.data.Pendidikan;
import id.mil.tni.android.pendataananggota.helper.Helper;
import id.mil.tni.android.pendataananggota.helper.PALog;
import id.mil.tni.android.pendataananggota.helper.SessionManager;
import id.mil.tni.android.pendataananggota.http.PAUpdateRequest;

public class MainActivity extends AppCompatActivity {

    private static String TAG_YEAR = "year";
    private static String TAG_LEVEL = "level";

    private SessionManager session;
    private ProgressDialog dialog;
    private LinearLayout container;
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

        container = (LinearLayout) findViewById(R.id.ll_container);
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
        if (pengalaman  != null && !pengalaman.equals("null") && !TextUtils.isEmpty(pengalaman)){
            try {
                JSONArray jsonArray = new JSONArray(pengalaman);
                addPendidikan(jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //etPengalaman.setText(pengalaman);
        if (keterampilan  != null && !keterampilan.equals("null") && !TextUtils.isEmpty(keterampilan)) etKeterampilan.setText(keterampilan);
    }

    private void initControl() {
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //JSONArray array = new JSONArray();
                ArrayList<String> pendidikanArrayList = new ArrayList<String>();
                for (int i = 0; i < container.getChildCount(); i++) {
                    LinearLayout view = (LinearLayout) container.getChildAt(i);

                    EditText pendidikan = (EditText) view.getChildAt(0);
                    EditText tahun = (EditText) view.getChildAt(1);

                    if (!TextUtils.isEmpty(pendidikan.getText().toString()) && !TextUtils.isEmpty(tahun.getText().toString())){
                        //JSONObject obj = new JSONObject();
                        Pendidikan pendidikanOb = null;
                        //obj.put(TAG_YEAR, tahun.getText().toString());
                        //obj.put(TAG_LEVEL, pendidikan.getText().toString());
                        //array.put(obj);
                        pendidikanOb = new Pendidikan(tahun.getText().toString(), pendidikan.getText().toString());
                        pendidikanArrayList.add(pendidikanOb.toString());
                    }

                    /*for (int j = 0; j < view.getChildCount(); j++) {
                        View myView = view.getChildAt(0);
                        *//*View view = container.getChildAt(1);
                        if (v instanceof TextView) {
                            Toast.makeText(MainActivity.this, view.getClass().getName(), Toast.LENGTH_SHORT).show();
                        }*//*
                        // Do something with v.
                        // …
                        Toast.makeText(MainActivity.this, String.valueOf(container.getChildCount())+"-"+String.valueOf(view.getChildCount()), Toast.LENGTH_SHORT).show();
                    }*/
                }

                if (TextUtils.isEmpty(etNomobil.getText().toString()) || TextUtils.isEmpty(etNosim.getText().toString()) || TextUtils.isEmpty(etKeterampilan.getText().toString())){
                    Toast.makeText(MainActivity.this, "Silahkan isi form yang masih kosong", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(MainActivity.this, "Pendafatran berhasil disimpan", Toast.LENGTH_SHORT).show();
                    //session.createLoginSession(name, email, password, nrp, etNomobil.getText().toString(), etNosim.getText().toString(), etPengalaman.getText().toString(), etKeterampilan.getText().toString());
                    dialog.show();
                    noMobil = etNomobil.getText().toString();
                    noSim = etNosim.getText().toString();
                    //pengalaman = etPengalaman.getText().toString();
                    pengalaman = pendidikanArrayList.toString();
                    PALog.e("APA INI "+pengalaman);
                    keterampilan = etKeterampilan.getText().toString();
                    new onUpdateRequest(getApplicationContext(), getString(R.string.api_path_update_profile), noMobil, noSim, pendidikanArrayList, keterampilan).execute();
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

        public onUpdateRequest(Context context, String apiPath, String carNum, String simNum, ArrayList<String> pendidikanArrayList, String skills) {
            super(context, apiPath, carNum, simNum, pendidikanArrayList, skills);
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


    public  void addPendidikan(JSONArray jsonArray){
        LayoutInflater linflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (jsonArray.length() > 0) {
            //int pos = 0;
            int pos = 0;
            while (pos < jsonArray.length())
            {
                View myView = linflater.inflate(R.layout.view_pendidikan_informal, null); //here item is the the layout you want to inflate
                myView.setId(pos);

                Pendidikan item = null;

                try {
                    JSONObject obj = jsonArray.getJSONObject(pos);
                    item = new Pendidikan(obj.getString(TAG_YEAR), obj.getString(TAG_LEVEL));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (item != null){
                    // This stores a reference to the actual item in the view
                    myView.setTag(item);

                    //MARGIN LAYOUT
                    //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //layoutParams.setMargins(0, 0, 0, GKHelper.integerToDP(getApplicationContext(), 10));
                    //myView.setLayoutParams(layoutParams);

                    TextView tvPendidikan = (TextView) myView.findViewById(R.id.tv_pendidikan);
                    TextView tvTahun = (TextView) myView.findViewById(R.id.tv_tahun);
                    ImageView btnAdd = (ImageView) myView.findViewById(R.id.iv_add);
                    ImageView btnRemove = (ImageView) myView.findViewById(R.id.iv_delete);

                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    btnRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    tvPendidikan.setText(item.getLevel());
                    tvTahun.setText(item.getYear());

                    pos++;
                    container.addView(myView);
                }
            }
        } else {

            createFormPendidikan(linflater);

        }


    }


    private void createFormPendidikan(final LayoutInflater linflater){
        final View myView = linflater.inflate(R.layout.view_pendidikan_informal, null); //here item is the the layout you want to inflate
        myView.setId(0);

        final TextView tvPendidikan = (TextView) myView.findViewById(R.id.tv_pendidikan);
        TextView tvTahun = (TextView) myView.findViewById(R.id.tv_tahun);
        final ImageView btnAdd = (ImageView) myView.findViewById(R.id.iv_add);
        final ImageView btnRemove = (ImageView) myView.findViewById(R.id.iv_delete);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tvPendidikan.getText().toString())){
                    Toast.makeText(MainActivity.this, "Kolom Pendidikan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(tvPendidikan.getText().toString())){
                    Toast.makeText(MainActivity.this, "Kolom Tahun tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    btnAdd.setVisibility(View.GONE);
                    btnRemove.setVisibility(View.VISIBLE);
                    createFormPendidikan(linflater);
                }
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (container.getChildCount()>1){
                    container.removeView(myView);
                }

            }
        });

        container.addView(myView);
    }

}
