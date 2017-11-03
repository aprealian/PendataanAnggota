package id.mil.tni.android.pendataananggota.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Aprilian Nur on 11/3/2017.
 */

public class Helper {

    public static final void handlePopupMessage(Context context, String message, DialogInterface.OnClickListener listener, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        listener != null ? listener : new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .setCancelable(cancelable)
                .create()
                .show();
    }


}
