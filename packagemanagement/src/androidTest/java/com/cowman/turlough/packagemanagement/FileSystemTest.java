package com.cowman.turlough.packagemanagement;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.io.File;
import java.io.IOException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class FileSystemTest extends ApplicationTestCase<Application> {

    FileSystem system = new FileSystem();
    DeliverySystem helper;
    PkgFileRegister register;
    private File extracted;


    public FileSystemTest() {
        super(Application.class);
    }

    public void setUp(){
        system.init(getContext());
        extracted = system.getExtracted();
        helper = new DeliverySystem(getContext());


    }

    public void tearDown(){
        system.delete(system.getRoot());
    }
    public void testReadableWritableStorage(){
        assertTrue("Storage is not readable", FileSystem.isExternalStorageReadable());
        assertTrue("Storage is not writable", FileSystem.isExternalStorageWritable());
    }

    public void testDirectoriesExist(){
        assertTrue("Root does not exist", system.getRoot().exists());
        assertTrue("Incoming does not exist", system.getIncoming().exists());
        assertTrue("Extracted does not exist", extracted.exists());
        assertTrue("Runtime does not exist", system.getRuntime().exists());
    }

    public void testCreatePackage() throws IOException {
        helper.touchFiles(extracted, "1234", "test.svg");
        File result = new File(extracted, "1234");
        assertTrue("Failed to create directory", result.exists());
        result = new File(result, "test.svg");
        assertTrue("Failed to create file", result.exists());
    }
}