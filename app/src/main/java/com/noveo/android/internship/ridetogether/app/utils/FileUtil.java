package com.noveo.android.internship.ridetogether.app.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public class FileUtil {
    public static File uriToFile(Context context, Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filename = cursor.getString(columnIndex);
            cursor.close();
            return new File(filename);
        }
        return new File(uri.getPath());
    }
}
