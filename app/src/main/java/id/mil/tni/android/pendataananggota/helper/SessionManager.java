package id.mil.tni.android.pendataananggota.helper;

/**
 * Created by Aprilian Nur on 10/28/2017.
 */

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import id.mil.tni.android.pendataananggota.activity.auth.AuthActivity;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "PendataanPref";

    // All Shared Preferences Keys
    public static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_ID_USER = "idUser";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NRP = "nrp";
    public static final String KEY_NO_MOBIL = "no_mobil";
    public static final String KEY_NO_SIM = "no_sim";
    public static final String KEY_PENGALAMAN = "pengalaman";
    public static final String KEY_KETERAMPILAN = "keterampilan";
    public static final String KEY_TOKEN = "token";




    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email, String password, String nrp, String no_mobil, String no_sim, String pengalaman, String keterampilan){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, false);
        // Storing name in pref
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_NRP, nrp);
        editor.putString(KEY_NO_MOBIL, no_mobil);
        editor.putString(KEY_NO_SIM, no_sim);
        editor.putString(KEY_PENGALAMAN, pengalaman);
        editor.putString(KEY_KETERAMPILAN, keterampilan);
        // commit changes
        editor.commit();
    }

    public void createLoginSessionToken(String idUser, String name, String email, String password, String nrp, String no_mobil, String no_sim, String pengalaman, String keterampilan, boolean isLogin, String token){
        // Storing login value as TRUE
        // Storing name in pref
        editor.putString(KEY_ID_USER, idUser);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_NRP, nrp);
        editor.putString(KEY_NO_MOBIL, no_mobil);
        editor.putString(KEY_NO_SIM, no_sim);
        editor.putString(KEY_PENGALAMAN, pengalaman);
        editor.putString(KEY_KETERAMPILAN, keterampilan);
        editor.putString(KEY_KETERAMPILAN, keterampilan);
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.putString(KEY_TOKEN, token);
        // commit changes
        editor.commit();
    }


    public void updateProfile(String no_mobil, String no_sim, String pengalaman, String keterampilan){
        // Storing login value as TRUE
        editor.putString(KEY_NO_MOBIL, no_mobil);
        editor.putString(KEY_NO_SIM, no_sim);
        editor.putString(KEY_PENGALAMAN, pengalaman);
        editor.putString(KEY_KETERAMPILAN, keterampilan);
        editor.putString(KEY_KETERAMPILAN, keterampilan);
        // commit changes
        editor.commit();
    }


    public void setLoginSession(boolean isLogin){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, isLogin);
        // commit changes
        editor.commit();
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, AuthActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }





    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_NRP, pref.getString(KEY_NRP, null));
        user.put(KEY_NO_MOBIL, pref.getString(KEY_NO_MOBIL, null));
        user.put(KEY_NO_SIM, pref.getString(KEY_NO_SIM, null));
        user.put(KEY_PENGALAMAN, pref.getString(KEY_PENGALAMAN, null));
        user.put(KEY_KETERAMPILAN, pref.getString(KEY_KETERAMPILAN, null));

        // return user
        return user;
    }



    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, AuthActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public String getToken(){
        return pref.getString(KEY_TOKEN, null);
    }
}