package id.mil.tni.android.pendataananggota.data;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aprilian Nur on 10/31/2017.
 */

public class User {

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NRP = "nrp";
    public static final String KEY_NO_MOBIL = "no_mobil";
    public static final String KEY_NO_SIM = "no_sim";
    public static final String KEY_PENGALAMAN = "pengalaman";
    public static final String KEY_KETERAMPILAN = "keterampilan";


    private String name;
    private String email;
    private String password;
    private String nrp;
    private String no_mobil;
    private String no_sim;
    private String pengalaman;
    private String keterampilan;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNrp() {
        return nrp;
    }

    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    public String getNo_mobil() {
        return no_mobil;
    }

    public void setNo_mobil(String no_mobil) {
        this.no_mobil = no_mobil;
    }

    public String getNo_sim() {
        return no_sim;
    }

    public void setNo_sim(String no_sim) {
        this.no_sim = no_sim;
    }

    public String getPengalaman() {
        return pengalaman;
    }

    public void setPengalaman(String pengalaman) {
        this.pengalaman = pengalaman;
    }

    public String getKeterampilan() {
        return keterampilan;
    }

    public void setKeterampilan(String keterampilan) {
        this.keterampilan = keterampilan;
    }


    public void parse(JSONObject obj) {
        if (obj == null) return;

        try {
            if (!obj.isNull(KEY_NAME)) {
                setName(obj.getString(KEY_NAME));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!obj.isNull(KEY_EMAIL)) {
                setEmail(obj.getString(KEY_EMAIL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!obj.isNull(KEY_PASSWORD)) {
                setPassword(obj.getString(KEY_PASSWORD));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!obj.isNull(KEY_NRP)) {
                setNrp(obj.getString(KEY_NRP));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!obj.isNull(KEY_NO_MOBIL)) {
                setNo_mobil(obj.getString(KEY_NO_MOBIL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!obj.isNull(KEY_NO_SIM)) {
                setNo_sim(obj.getString(KEY_NO_SIM));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!obj.isNull(KEY_PENGALAMAN)) {
                setPengalaman(obj.getString(KEY_PENGALAMAN));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!obj.isNull(KEY_KETERAMPILAN)) {
                setKeterampilan(obj.getString(KEY_KETERAMPILAN));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        JSONObject obj = new JSONObject();

        try {
            if (!TextUtils.isEmpty(getName())) {
                obj.put(KEY_NAME, getName());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!TextUtils.isEmpty(getEmail())) {
                obj.put(KEY_EMAIL, getEmail());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!TextUtils.isEmpty(getPassword())) {
                obj.put(KEY_PASSWORD, getPassword());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!TextUtils.isEmpty(getNrp())) {
                obj.put(KEY_NRP, getNrp());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!TextUtils.isEmpty(getNo_mobil())) {
                obj.put(KEY_NO_MOBIL, getNo_mobil());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!TextUtils.isEmpty(getNo_sim())) {
                obj.put(KEY_NO_SIM, getNo_sim());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!TextUtils.isEmpty(getNo_sim())) {
                obj.put(KEY_NO_SIM, getNo_sim());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            return obj.toString(3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.toString();

    }



}
