package id.mil.tni.android.pendataananggota.http;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Aprilian Nur on 11/2/2017.
 */

public class PALoginRequest extends BasicRequest {

    private Context context;

    public PALoginRequest(Context context, String apiPath, String email, String password) {
        super(context, apiPath);

        this.context = context;

        /*RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        addQuery(formBody);*/


        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addRawJSON(json.toString());

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


    }


}