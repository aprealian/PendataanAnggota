package id.mil.tni.android.pendataananggota.http;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Aprilian Nur on 11/2/2017.
 */

public class PARegisterProfile extends BasicRequest {

    private Context context;

    public PARegisterProfile(Context context, String apiPath, String name, String nrp, String email, String password, String retypePassword) {
        super(context, apiPath);

        this.context = context;

        RequestBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("nrp", nrp)
                .add("email", email)
                .add("password", password)
                .add("retypePassword", retypePassword)
                .build();

        addQuery(formBody);

        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("nrp", nrp);
            json.put("email", email);
            json.put("password", password);
            json.put("retypePassword", retypePassword);
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