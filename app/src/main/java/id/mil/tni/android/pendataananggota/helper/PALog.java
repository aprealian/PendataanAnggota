package id.mil.tni.android.pendataananggota.helper;

import android.util.Log;

/**
 * Created by Aprilian Nur on 11/2/2017.
 */

public class PALog  {
    public static void e(Object obj){
        if(obj == null){
            obj = "null";
        }
        Log.e("Pendataan Angggota - ", obj.toString());
    }

    public static void d(Object obj){
        if(obj == null){
            obj = "null";
        }
        Log.d("Pendataan Angggota - ", obj.toString());
    }

    public static void w(Object obj){
        if(obj == null){
            obj = "null";
        }
        Log.w("Pendataan Angggota - ", obj.toString());
    }
}
