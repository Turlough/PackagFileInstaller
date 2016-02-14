package com.cowman.turlough.packagemanagement;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.cowman.turlough.packagemanagement.packageprocessor.PkgFileRegister;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import rx.Observable;

/**
 * Created by turlough on 13/02/16.
 */
public class PkgFileRegisterTest extends ApplicationTestCase<Application> {

    FileSystem system = new FileSystem();
    DeliverySystem helper;
    PkgController controller;
    PkgFileRegister register;


    public PkgFileRegisterTest() {
        super(Application.class);
    }

    public void setUp() {
        system.init(getContext());
        helper = new DeliverySystem(getContext());
        controller = PkgController.getInstance();
        register = new PkgFileRegister();
    }

    public void tearDown() {
        system.delete(system.getRoot());
    }

    public void testGetAll() throws IOException {
        assertTrue(register.getDefinitions().size() > 0);
    }

    public void testFilter() {
        Set<PkgFile> result = new HashSet<>();

        Observable
                .from(register.getDefinitions())
                .filter(x -> x.getFileType().equals(FileType.CARDREADER))
                .subscribe(x -> result.add(x));

        assertEquals(2, result.size());
    }
}
