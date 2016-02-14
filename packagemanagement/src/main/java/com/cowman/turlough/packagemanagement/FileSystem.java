package com.cowman.turlough.packagemanagement;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.Getter;

/**
 * Created by turlough on 13/02/16.
 */

public class FileSystem {

    @Getter
    private File root, incoming, runtime, extracted;

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void init(Context context) {
        root = new File(context.getApplicationContext().getFilesDir(), "Avego");
        root.mkdirs();
        incoming = new File(root, "incoming");
        incoming.mkdirs();
        extracted = new File(root, "extracted");
        extracted.mkdirs();
        runtime = new File(root, "runtime");
        runtime.mkdirs();

    }

    public void delete(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                delete(child);

        fileOrDirectory.delete();

    }

    public void copy(File sourceFile, File destDir) throws IOException {

        InputStream in = new FileInputStream(sourceFile);
        OutputStream out = new FileOutputStream(new File(destDir, sourceFile.getName()));

        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }

        in.close();
        out.flush();
        out.close();
    }

}
