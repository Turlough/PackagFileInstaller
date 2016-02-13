package com.cowman.turlough.packagemanagement.packageprocessor;

import android.util.Log;

import com.cowman.turlough.packagemanagement.ProcessingException;

import java.io.File;
import java.io.IOException;

/**
 * Created by turlough on 13/02/16.
 */
public class ApkProcessor implements PackageFileProcessor {
    private static String TAG = ApkProcessor.class.getSimpleName();
    @Override
    public void process(File file) throws ProcessingException, IOException {
        //TODO
        Log.i(TAG, "installed the apk");
    }
}
