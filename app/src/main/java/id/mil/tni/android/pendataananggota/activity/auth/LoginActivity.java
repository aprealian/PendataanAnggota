package id.mil.tni.android.pendataananggota.activity.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.regex.Matcher;

import id.mil.tni.android.pendataananggota.MainActivity;
import id.mil.tni.android.pendataananggota.R;
import id.mil.tni.android.pendataananggota.helper.Helper;
import id.mil.tni.android.pendataananggota.helper.SessionManager;
import id.mil.tni.android.pendataananggota.http.PALoginRequest;

public class LoginActivity extends AppCompatActivity {

    private static String TAG_ID_USER = "idUser";
    private static String TAG_NRP = "nrp";
    private static String TAG_EMAIL = "email";
    private static String TAG_NAME = "name";
    private static String TAG_CAR_NUM = "car_num";
    private static String TAG_SIM_NUM = "sim_num";
    private static String TAG_EDUCATION = "education";
    private static String TAG_SKILLS = "training";
    private static String TAG_TOKEN = "token";

    private SessionManager session;
    private ProgressDialog dialog;
    private LinearLayout btnLogin;
    private EditText etEmail;
    private EditText etPassword;

    private String token;
    private String idUser;
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

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");

        // Session Manager
        session = new SessionManager(LoginActivity.this);
        /*HashMap<String, String> user = session.getUserDetails();
        // get user data from session
        user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);
        noMobil = user.get(SessionManager.KEY_NO_MOBIL);
        noSim = user.get(SessionManager.KEY_NO_SIM);
        pengalaman = user.get(SessionManager.KEY_PENGALAMAN);
        keterampilan = user.get(SessionManager.KEY_KETERAMPILAN);
        password = user.get(SessionManager.KEY_PASSWORD);
        nrp = user.get(SessionManager.KEY_NRP);*/


        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (LinearLayout) findViewById(R.id.lv_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(etEmail.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (!Helper.isValidEmaillId(etEmail.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Email tidak valid", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etPassword.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } /*else if (!etUsername.getText().toString().equals(email) || !etPassword.getText().toString().equals(password)){
                    Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                } */else {

                    dialog.show();
                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                    new onLoginRequest(getApplicationContext(), getString(R.string.api_path_login), email, password).execute();

                    /*session.createLoginSession(name, email, password, nrp, noMobil, noSim, pengalaman, keterampilan, true);
                    session.setLoginSession(true);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();*/

                    /*session.createLoginSessionToken("191919", "Aprilian Nur", "aprilian@gmail.com", "password", "NRP010101", "AE 22002 BE", "SIM-191919", "penaa ahahah ", "menggambar", true, "tokenku");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();*/

                }
            }
        });

    }



    private class onLoginRequest extends PALoginRequest {

        public onLoginRequest(Context context, String apiPath, String email, String password) {
            super(context, apiPath, email, password);
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
                JSONObject jsonObject = new JSONObject(getJson());
                JSONObject userObj = jsonObject.getJSONObject("data");
                idUser = userObj.getString(TAG_ID_USER);
                nrp = userObj.getString(TAG_NRP);
                email = userObj.getString(TAG_EMAIL);
                name = userObj.getString(TAG_NAME);
                noMobil = userObj.getString(TAG_CAR_NUM);
                noSim = userObj.getString(TAG_SIM_NUM);
                //pengalaman = userObj.getString(TAG_EDUCATION);
                pengalaman = userObj.getJSONArray(TAG_EDUCATION).toString();

                /*JSONArray jsonArray = userObj.getJSONArray(TAG_EDUCATION);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonArray.getJSONObject(i).getString("year");
                    jsonArray.getJSONObject(i).getString("level");
                }*/

                keterampilan = userObj.getString(TAG_SKILLS);
                token = userObj.getString(TAG_TOKEN);

                session.createLoginSessionToken(idUser, name, email, password, nrp, noMobil, noSim, pengalaman, keterampilan, true, token);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } catch (JSONException e){
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "Terjadi kesalahan pada sistem", Toast.LENGTH_SHORT).show();
            }

            return super.responseSuccess();
        }

        @Override
        public JSONObject responseFailed() throws JSONException {
            if (dialog != null) dialog.dismiss();

            Helper.handlePopupMessage(LoginActivity.this, getMsg(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }, true);
            return super.responseFailed();
        }

        @Override
        public String responseError() {
            if (dialog != null) dialog.dismiss();
            Helper.handlePopupMessage(LoginActivity.this, "Please, check your internet connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }, true);
            return super.responseError();
        }
    }


}
