package com.cowman.turlough.packagemanagement;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.cowman.turlough.packagemanagement.exception.PackageException;
import com.cowman.turlough.packagemanagement.pojo.PkgFile;

import java.io.File;
import java.io.IOException;

import rx.Observable;

/**
 * Created by turlough on 14/02/16.
 */
public class DeliverySystemTest  extends ApplicationTestCase<Application> {

    FileSystem system = new FileSystem();
    DeliverySystem helper;
    PkgController controller;
    File extracted, runtime, incoming;

    PkgFileRegister register;


    public DeliverySystemTest() {
        super(Application.class);

    }

    public void setUp() throws IOException {
        system.init(getContext());
        extracted = system.getExtracted();
        runtime = system.getRuntime();
        incoming = system.getIncoming();
        helper = new DeliverySystem(getContext());
        controller = PkgController.getInstance();
        register = new PkgFileRegister();
    }

    public void tearDown(){
        system.delete(system.getRoot());
    }

    public void testCreatePackage() throws IOException, PackageException {

        helper.setDir(system.getIncoming(), "1234");

        PkgFile svg1 = register.getSvg();
        helper.putInDefaultDir(svg1, "header.svg");
        PkgFile svg2 = register.getSvg();
        helper.putInDefaultDir(svg2, "footer.svg");
        PkgFile dot = register.getSvg();
        helper.putInDefaultDir(dot, "dot.dot");

        int count = helper.createPackage("1234", svg1, svg2, dot);

        assertEquals(3, count);
    }

}
