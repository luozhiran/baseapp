package com.itg.util_lib;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {


    public static boolean isSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * @param path
     * @param content
     */
    public static void write(String path, String content, boolean append) {
        File file = new File(path);
        FileOutputStream out = null;
        BufferedOutputStream bufIo = null;
        if (createNewFile(file)) {
            try {
                out = new FileOutputStream(file, append);
                bufIo = new BufferedOutputStream(out);
                byte[] buffer = content.getBytes("UTF-8");
                bufIo.write(buffer, 0, buffer.length);
                bufIo.flush();
                bufIo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != bufIo) {
                    try {
                        bufIo.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 文件保存到android/data/data/package/file
     *
     * @param name
     * @param content
     */
    public static void writeToData(String name, String content) {
        File file = ToolConfig.app().getFilesDir();
        File createFile = new File(file, name);
        FileOutputStream out = null;
        BufferedOutputStream bufIo = null;
        if (createNewFile(createFile)) {
            try {
                out = new FileOutputStream(file);
                bufIo = new BufferedOutputStream(out);
                byte[] buffer = content.getBytes("UTF-8");
                bufIo.write(buffer, 0, buffer.length);
                bufIo.flush();
                bufIo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != bufIo) {
                    try {
                        bufIo.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * 读取文件android/data/data/package/file
     *
     * @param name
     */
    public static String readFromData(String name) {
        StringBuilder content = new StringBuilder();
        File file = ToolConfig.app().getFilesDir();
        File contentFile = new File(file, name);
        if (!contentFile.exists()) return "";
        BufferedInputStream bufferIn = null;

        try {
            bufferIn = new BufferedInputStream(new FileInputStream(contentFile));
            byte[] buffer = new byte[2048];
            int flag = 0;
            String str = "";
            while ((flag = bufferIn.read(buffer)) != -1) {
                content.append(new String(buffer, 0, flag, "UTF-8"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferIn != null) {
                try {
                    bufferIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    public static String readAssets(String name) {
        AssetManager assetManager = ToolConfig.app().getAssets();
        BufferedInputStream bufferIn = null;
        StringBuilder content = new StringBuilder();
        try {
            InputStream is = assetManager.open(name);
            bufferIn = new BufferedInputStream(is);
            byte[] buffer = new byte[2048];
            int flag = 0;
            String str = "";
            while ((flag = bufferIn.read(buffer)) != -1) {
                content.append(new String(buffer, 0, flag, "UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferIn != null) {
                try {
                    bufferIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }


    public static void deleteFile(String name) {
        File file = ToolConfig.app().getFilesDir();
        File contentFile = new File(file, name);
        if (contentFile.exists()) {
            contentFile.delete();
        }
    }

    private static boolean createNewFile(File file) {
        boolean result = true;
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                result = file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
