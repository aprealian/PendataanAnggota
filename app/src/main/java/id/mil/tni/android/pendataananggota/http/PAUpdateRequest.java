package id.mil.tni.android.pendataananggota.http;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import id.mil.tni.android.pendataananggota.helper.SessionManager;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Aprilian Nur on 11/2/2017.
 */

public class PAUpdateRequest extends BasicRequest {

    private Context context;

    public PAUpdateRequest(Context context, String apiPath, String carNum, String simNum, String orgExp, String skills) {
        super(context, apiPath);

        this.context = context;

        /*RequestBody formBody = new FormBody.Builder()
                .add("car_num", carNum)
                .add("sim_num", simNum)
                .add("org_exp", orgExp)
                .add("skills", skills)
                .build();

        addQuery(formBody);*/


        JSONObject json = new JSONObject();
        try {
            json.put("car_num", carNum);
            json.put("sim_num", simNum);
            json.put("org_exp", orgExp);
            json.put("skills", skills);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addRawJSON(json.toString());

        SessionManager sessionManager = new SessionManager(context);
        addToken(sessionManager.getToken());


    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }

}