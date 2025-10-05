package com.sergisa.inventorymanager.db;

import android.graphics.Bitmap;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

import android.graphics.BitmapFactory;
import android.util.Base64;

@ProvidedTypeConverter
public class ImageBitmapString {
    @TypeConverter
    public static String BitmapToString(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        if (temp == null) {
            return null;
        } else return temp;
    }

    @TypeConverter
    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            if (bitmap == null) {
                return null;
            } else {
                return bitmap;
            }
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
