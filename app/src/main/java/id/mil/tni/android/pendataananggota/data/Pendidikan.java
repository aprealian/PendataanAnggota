package id.mil.tni.android.pendataananggota.data;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aprilian Nur on 11/9/2017.
 */

public class Pendidikan{

    public static final String KEY_YEAR = "year";
    public static final String KEY_LEVEL = "level";

    private String year;
    private String level;

    public Pendidikan(String year, String level) {
        this.level = level;
        this.year = year;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void parse(JSONObject obj) {
        if (obj == null) return;

        try {
            if (!obj.isNull(KEY_LEVEL)) {
                setLevel(obj.getString(KEY_LEVEL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!obj.isNull(KEY_YEAR)) {
                setYear(obj.getString(KEY_YEAR));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        JSONObject obj = new JSONObject();

        try {
            if (!TextUtils.isEmpty(getYear())) {
                obj.put(KEY_YEAR, getYear());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!TextUtils.isEmpty(getLevel())) {
                obj.put(KEY_LEVEL, getLevel());
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
