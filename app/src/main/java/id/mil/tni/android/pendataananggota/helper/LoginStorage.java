package id.mil.tni.android.pendataananggota.helper;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import id.mil.tni.android.pendataananggota.data.User;

public class LoginStorage extends AbstractStorage {
    private static final String FILENAME = "pendataan:storage:login-user";
    private static final String KEY_USER = "user";
    private static final String KEY_TOKEN = "token";

    public User user;
    public String token;

    public LoginStorage(Context context) {
        super(context);
    }

    public boolean isUserLogin() {
        /*SILog.d("check is user login?");
        SILog.d("user = " + user);
        SILog.d("ink token = " + inkToken);*/
        return user != null && !TextUtils.isEmpty(user.getName()) &&
                !TextUtils.isEmpty(token);
    }

    /*public boolean isStylish() {
        if (user != null && user.getUserType() != null && !TextUtils.isEmpty(user.getUserType().getId()) && !user.getUserType().getId().equals("10")) {
            return true;
        }
        return false;
    }*/


    @Override
    protected String getStorageKey() {
        return FILENAME;
    }

    @Override
    protected void onParseData(JSONObject obj) throws JSONException {
        //Log.d("on load login storage = " + obj.toString());

        if (!obj.isNull(KEY_USER)) {
            JSONObject cObj = obj.getJSONObject(KEY_USER);
            user = new User();
            user.parse(cObj);
        }

        if (!obj.isNull(KEY_TOKEN)) {
            token = obj.getString(KEY_TOKEN);
        }
    }

    @Override
    protected JSONObject onSaveData() throws JSONException {
        JSONObject obj = new JSONObject();
        if (user != null) {
            obj.put(KEY_USER, new JSONObject(user.toString()));
        }

        if (token != null) {
            obj.put(KEY_TOKEN, token);
        }
        return obj;
    }
}
