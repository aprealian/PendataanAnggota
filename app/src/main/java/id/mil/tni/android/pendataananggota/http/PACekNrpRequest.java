package id.mil.tni.android.pendataananggota.http;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aprilian Nur on 11/2/2017.
 */

public class PACekNrpRequest extends BasicRequest {

    private Context context;

    public PACekNrpRequest(Context context, String apiPath, String nrp, String uo) {
        super(context, apiPath);

        this.context = context;

        /*RequestBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("nrp", nrp)
                .add("email", email)
                .add("password", password)
                .add("retypePassword", retypePassword)
                .build();

        addQuery(formBody);*/

        JSONObject json = new JSONObject();
        try {
            json.put("nrp", nrp);
            json.put("uo", uo);
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