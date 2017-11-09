package id.mil.tni.android.pendataananggota.data;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aprilian Nur on 11/8/2017.
 */

public class Matra {

    public static final String KEY_ID = "id";
    public static final String KEY_NAMA = "nama";

    private String id;
    private String nama;

    public Matra(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }


    public void parse(JSONObject obj) {
        if (obj == null) return;

        try {
            if (!obj.isNull(KEY_ID)) {
                setId(obj.getString(KEY_ID));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!obj.isNull(KEY_NAMA)) {
                setNama(obj.getString(KEY_NAMA));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        JSONObject obj = new JSONObject();

        try {
            if (!TextUtils.isEmpty(getId())) {
                obj.put(KEY_ID, getId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!TextUtils.isEmpty(getNama())) {
                obj.put(KEY_NAMA, getNama());
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
