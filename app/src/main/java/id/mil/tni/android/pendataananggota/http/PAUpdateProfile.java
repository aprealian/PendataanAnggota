package id.mil.tni.android.pendataananggota.http;

import android.content.Context;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Aprilian Nur on 11/2/2017.
 */

public class PAUpdateProfile extends BasicRequest {

    private Context context;

    public PAUpdateProfile(Context context, String apiPath, String name, String nrp, String email, String password) {
        super(context, apiPath);

        this.context = context;

        RequestBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("nrp", nrp)
                .add("email", email)
                .add("password", password)
                .add("retypePassowrd", password)
                .build();

        addQuery(formBody);

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }

}