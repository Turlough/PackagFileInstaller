package com.cowman.turlough.packagemanagement;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.cowman.turlough.packagemanagement.packageprocessor.PackageFileAuthority;

import java.io.File;
import java.io.IOException;

/**
 * Created by turlough on 13/02/16.
 */
public class PackageFileTest  extends ApplicationTestCase<Application> {

    FileSystem system = new FileSystem();
    MockFileSystem helper;
    PackageFileAuthority authority;


    public PackageFileTest() {
        super(Application.class);
    }

    public void setUp() {
        system.init(getContext());
        helper = new MockFileSystem(getContext());
        authority = new PackageFileAuthority();
    }

    public void tearDown() {
        system.delete(system.getRoot());
    }

    public void testLoadFiles() throws IOException {
        helper.createExtractedPackage("1234", "config.xml");
        File dir = new File(system.getExtracted(), "1234");
        PackageFile file = authority.getConfig();
        file.loadFiles(dir);

        assertEquals(1, file.getFiles().size());
    }

    public void testDoesntLoadInvalidFiles() throws IOException {
        helper.createExtractedPackage("1234", "config.xml", "ignore", "zippyzippyzingzing");
        File dir = new File(system.getExtracted(), "1234");
        PackageFile file = authority.getConfig();
        file.loadFiles(dir);

        assertEquals(1, file.getFiles().size());
    }
}
