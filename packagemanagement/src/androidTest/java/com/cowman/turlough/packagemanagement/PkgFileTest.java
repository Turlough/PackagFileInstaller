package com.cowman.turlough.packagemanagement;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.cowman.turlough.packagemanagement.pojo.PkgFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by turlough on 13/02/16.
 */
public class PkgFileTest extends ApplicationTestCase<Application> {

    FileSystem system = new FileSystem();
    DeliverySystem helper;
    PkgFileRegister register;
    private File extracted;


    public PkgFileTest() {
        super(Application.class);
    }

    public void setUp() {
        system.init(getContext());
        extracted = system.getExtracted();
        helper = new DeliverySystem(getContext());
        register = new PkgFileRegister();
    }

    public void tearDown() {
        system.delete(system.getRoot());
    }

    public void testLoadFiles() throws IOException {
        helper.touchFiles(extracted, "1234", "config.xml");
        File dir = new File(extracted, "1234");
        PkgFile file = register.getConfig();
        file.loadFiles(dir);

        assertEquals(1, file.getFiles().size());
    }

    public void testDoesntLoadInvalidFiles() throws IOException {
        helper.touchFiles(extracted, "1234", "config.xml", "ignore", "zippyzippyzingzing");
        File dir = new File(extracted, "1234");
        PkgFile file = register.getConfig();
        file.loadFiles(dir);

        assertEquals(1, file.getFiles().size());
    }

    public void testFilter(){
        PkgFile pf = register.getHotUpdate();
        File sample = new File("hu-1.dat");
        assertTrue(pf.getFilter().accept(sample));
    }
}
