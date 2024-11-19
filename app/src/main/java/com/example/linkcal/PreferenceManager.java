package com.example.linkcal;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "LinkCalPrefs";
    private static final String KEY_USER_TOKEN = "userId";
    private static final String KEY_USER_EMAIL = "userEmail";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public static String getUserEmail(Context context) {
        return getPrefs(context).getString(KEY_USER_EMAIL, null);
    }

    public static void saveUserSession(Context context, String token, String email) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(KEY_USER_TOKEN, token);
        editor.putString(KEY_USER_EMAIL, email);
        editor.apply();
    }

    public static boolean isLoggedIn(Context context) {
        return getPrefs(context).getString(KEY_USER_TOKEN, null) != null;
    }

    public static void clearSession(Context context) {
        getPrefs(context).edit().clear().apply();
    }
}