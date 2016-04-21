package com.xian.patientappv1;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by PTTsiuoLIVIA on 3/18/16.
 */
public class SaveSharedPreference
{
    static final String PREF_USER_NAME= "useruid";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserUid(Context ctx, String userUid)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userUid);
        editor.commit();
    }

    public static String getUserUid(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static boolean removeUserUid(Context ctx)
    {
        SharedPreferences.Editor editor =
        getSharedPreferences(ctx).edit();
        editor.remove(PREF_USER_NAME);
        editor.commit();
        return true;
    }
}
