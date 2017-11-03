package id.mil.tni.android.pendataananggota.http;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.mil.tni.android.pendataananggota.R;
import id.mil.tni.android.pendataananggota.helper.PALog;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Aprilian Nur on 11/2/2017.
 */

public class BasicRequest  extends AsyncTask<Void, Void, Void> {

    public String header = "Response is : ";
    private RequestBody formBody;
    private Context context;
    private String apiPath;
    private String json;
    private String type;
    private String token = "";
    private String msg;
    RequestBody body;

    public BasicRequest (Context context) {
        this.context = context;
    }

    public BasicRequest (Context context, String apiPath) {
        this.context = context;
        this.apiPath = apiPath;
    }

    public BasicRequest (Context context, String apiPath, RequestBody formBody) {
        this.context = context;
        this.apiPath = apiPath;
        this.formBody = formBody;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        /**
         * Progress Dialog for User Interaction
         */
    }

    @Nullable
    @Override
    protected Void doInBackground(Void... params) {

        try {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(context.getString(R.string.basic_url)+apiPath)
                    .addHeader("access_token", token)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            json = response.body().string();
            PALog.e("basic request "+ json);

        } catch (@NonNull IOException e) {
            Log.e("JSON Parser", "Error parsing data " + e.getLocalizedMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //dialog.dismiss();

        PALog.d("Response is "+ json);

        if (!TextUtils.isEmpty(json)) {

            if(json.trim().charAt(0) == '[') {
                PALog.d(header+"JSONArray");
                try {
                    responseArray();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if(json.trim().charAt(0) == '{') {
                PALog.d(header+"JSONObject");
                try {
                    responseObject();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                PALog.d(header+"ERROR 1");
                responseError();
            }

        } else {
            PALog.e(header+"Empty or NULL");
            responseError();
            //type = "data json error";
        }


    }

    public void addQuery(RequestBody formBody) {
        this.formBody = formBody;
    }

    public void addToken(String token) {
        this.token = token;
    }

    public void addRawJSON(String json) {
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8"); //defines the type of the body
       body = RequestBody.create(JSON, json);
    }




    public String responseError() {
        return "Error, please check your connection";
    }

    public JSONObject responseObject() throws JSONException {

        JSONObject obj = new JSONObject(json);

        if (obj.has("status") && obj.getString("status").equals("success")){
            responseSuccess();
        } else if (obj.has("status") && obj.getString("status").equals("error")){
            responseFailed();
        } else {
            responseError();
        }
        return obj;
    }

    public JSONObject responseSuccess() throws JSONException {
        PALog.d(header+"SUCCESS");
        JSONObject obj = new JSONObject(json);
        msg = obj.getString("msg");
        return obj;
    }

    public JSONObject responseFailed() throws JSONException {
        PALog.d(header+"FAILED");
        JSONObject obj = new JSONObject(json);
        msg = obj.getString("msg");
        return obj;
    }

    public JSONArray responseArray() throws JSONException {

        JSONArray array = new JSONArray(json);
        return array;
    }

    public String getJson() {
        return json;
    }

    public String getMsg() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            msg = jsonObject.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }


}