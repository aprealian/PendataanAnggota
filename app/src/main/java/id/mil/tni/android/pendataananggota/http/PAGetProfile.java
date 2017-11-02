package id.mil.tni.android.pendataananggota.http;

import android.content.Context;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Aprilian Nur on 11/2/2017.
 */

public class PAGetProfile extends BasicRequest {

    private Context context;

    public PAGetProfile(Context context, String apiPath) {
        super(context, apiPath);

        this.context = context;

        RequestBody formBody = new FormBody.Builder()
                .add("", "")
                .build();

        addQuery(formBody);

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }

}