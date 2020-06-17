package com.yk.baseapp.test;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class DpiUtils {

    public static void save(Bitmap bitmap, File file, int dpi) {
        try {
            ByteArrayOutputStream imageByteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageByteArray);
            byte[] imageData = imageByteArray.toByteArray();
            imageByteArray.close();

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(setDpi(imageData, dpi));
            fileOutputStream.close();
            imageData = null;
            Log.e("aaa", "saved");
        } catch (Exception e) {
            Log.e("aaa", "Wrong in Class 'BitmapToPng'");
            Log.e("aaa", e.getMessage());
        }
    }
    private static byte[] setDpi(byte[] imageData, int dpi) {
        byte[] imageDataCopy = new byte[imageData.length + 21];
        System.arraycopy(imageData, 0, imageDataCopy, 0, 33);
        System.arraycopy(imageData, 33, imageDataCopy, 33 + 21, imageData.length - 33);

        int[] pHYs = new int[]{0, 0, 0, 9, 112, 72, 89, 115, 0, 0, 23, 18, 0, 0, 23, 18, 1, 103, 159, 210, 82};

        for (int i = 0; i < 21; i++) {
            imageDataCopy[i + 33] = (byte) (pHYs[i] & 0xff);
        }

        dpi = (int) (dpi / 0.0254);
        imageDataCopy[41] = (byte) (dpi >> 24);
        imageDataCopy[42] = (byte) (dpi >> 16);
        imageDataCopy[43] = (byte) (dpi >> 8);
        imageDataCopy[44] = (byte) (dpi & 0xff);

        imageDataCopy[45] = (byte) (dpi >> 24);
        imageDataCopy[46] = (byte) (dpi >> 16);
        imageDataCopy[47] = (byte) (dpi >> 8);
        imageDataCopy[48] = (byte) (dpi & 0xff);

//        for (int i = 0; i < 30; i++) {
//            String line = "";
//            for (int j = 0; j < 16; j++) {
//                int temp = imageDataCopy[16 * i + j] & 0xFF;
//                line += Integer.toHexString(temp) + " ";
//            }
//            Log.e(i + "", line);
//        }
        return imageDataCopy;
    }

}
