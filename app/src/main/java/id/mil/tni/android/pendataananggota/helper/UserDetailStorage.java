package id.mil.tni.android.pendataananggota.helper;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by dantech on 3/12/17.
 */

public class UserDetailStorage extends AbstractStorage {
    private static final String FILENAME = "pendataan:storage:user-detail";
    private static final String KEY_USER_DETAIL = "user_detail";

    public User userDetail;

    public UserDetailStorage(Context context) {
        super(context);
    }


    @Override
    protected String getStorageKey() {
        return FILENAME;
    }

    @Override
    protected void onParseData(JSONObject obj) throws JSONException {
        //Log.d("on load login storage = " + obj.toString());

        if (!obj.isNull(KEY_USER_DETAIL)) {
            JSONObject cObj = obj.getJSONObject(KEY_USER_DETAIL);
            userDetail = new User();
            userDetail.parse(cObj);
        }
    }

    @Override
    protected JSONObject onSaveData() throws JSONException {
        JSONObject obj = new JSONObject();
        if (userDetail != null) {
            obj.put(KEY_USER_DETAIL, new JSONObject(userDetail.toString()));
        }
        return obj;
    }
}
