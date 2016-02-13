package com.cowman.turlough.packagemanagement;

import android.content.Context;

import java.io.File;
import java.io.IOException;

/**
 * Created by turlough on 13/02/16.
 */
public class MockFileSystem extends FileSystem {
    Context context;
    public MockFileSystem(Context context) {
        this.context = context;
        init(context);
    }

    public void createExtractedPackage(String packageId, String... filenames) throws IOException {
        File dir = new File (getExtracted(), packageId);
        dir.mkdirs();

        for(String filename : filenames){
            new File(dir, filename).createNewFile();
        }
    }

}
