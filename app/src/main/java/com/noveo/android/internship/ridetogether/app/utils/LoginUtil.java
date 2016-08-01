package com.noveo.android.internship.ridetogether.app.utils;


import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import com.noveo.android.internship.ridetogether.app.model.response.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginUtil {
    private static final String PREFS_USERNAME = "PREFS_USERNAME";
    private static final String PREFS_PASSWORD = "PREFS_PASSWORD";
    private static final String IMAGES_PATH = "images";
    private static final String USER_IMAGE_NAME = "userimage";
    private static final String LOG_TAG = "Login";

    public static String getUsername(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PREFS_USERNAME, "Log In");
    }

    public static void storeCredentials(Context context, String username, String password) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(PREFS_USERNAME, username);
        editor.putString(PREFS_PASSWORD, password);
        editor.apply();
    }

    public static File getUserImage(Context context) {
        return new File(String.format("%s/%s", Environment.getExternalStorageDirectory().toString(), IMAGES_PATH),
                USER_IMAGE_NAME);
    }

    public static void storeUserImage(Context context, Bitmap userImage) {
        File dir = new File(String.format("%s/%s",
                Environment.getExternalStorageDirectory().toString(), IMAGES_PATH));
        dir.mkdirs();

        File file = new File(dir, USER_IMAGE_NAME);
        file.delete();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            userImage.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error : couldn't save image");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error : couldn't close stream");
            }
        }
    }

    public static String getPassword(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PREFS_PASSWORD, "");
    }

    public static String createAuthString(String username, String password) {
        return "Basic " + Base64.encodeToString(String.format("%s:%s", username, password).getBytes(), Base64.NO_WRAP);
    }

    public static void deleteCredentials(Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.remove(PREFS_USERNAME);
        editor.remove(PREFS_PASSWORD);

        File file = new File(String.format("%s/%s", Environment.getExternalStorageDirectory().toString(),
                IMAGES_PATH), USER_IMAGE_NAME);
        file.delete();
        editor.apply();
    }
}
