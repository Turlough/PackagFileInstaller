package com.cowman.turlough.packagemanagement.packageprocessor;

import com.cowman.turlough.packagemanagement.ProcessingException;

import java.io.File;
import java.io.IOException;

/**
 * Created by turlough on 13/02/16.
 */
public interface PackageFileProcessor {
    void process(File file) throws ProcessingException, IOException;
}
