package com.cowman.turlough.packagemanagement;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.cowman.turlough.packagemanagement.packageprocessor.PkgFileRegister;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Set;

/**
 * Created by turlough on 13/02/16.
 */
public class PkgControllerTest extends ApplicationTestCase<Application> {

    FileSystem system = new FileSystem();
    DeliverySystem helper;
    PkgController controller;
    File extracted, runtime, incoming;

    PkgFileRegister register;


    public PkgControllerTest() {
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

    public void testFromDirectory() throws IOException {
        helper.touchFiles(extracted, "1234", "not me", "config.xml");
        File dir = new File(extracted,"1234");

        Set<PkgFile> result = controller.fromDirectory(dir);
        assertEquals(1, result.size());
    }

    public void testCreateHotUpdates() throws IOException {
        PkgFile p1 = register.getHotUpdate();
        helper.setDir(runtime, "1234");
        helper.putInDefaultDir( p1, "hu-1.dat");
        assertTrue(new File(helper.getDefaultDir(), "hu-1.dat").exists());
    }

    public void testLoadHotUpdates() throws IOException {
        PkgFile p1 = register.getHotUpdate();
        helper.setDir(runtime, "1234");
        helper.putInDefaultDir(p1, "hu-1.dat");
        Set<PkgFile> files = controller.fromDirectory(helper.getDefaultDir());

        assertEquals(1, files.size());
    }

    public void testFileList() throws IOException {
        helper.touchFiles(extracted, "1234", "not me", "config.xml");
        File dir = new File(extracted,"1234");
        assertEquals(2, dir.list().length);
    }

    public void testConfigFilter() throws IOException {
        PkgFileRegister register = new PkgFileRegister();
        FileFilter filter = register.getConfig().getFilter();
        assertTrue(filter.accept(new File(extracted,"config.xml")));
        assertFalse(filter.accept(new File(extracted, "ftm.xml")));
    }

    public void testListFilesWithConfigFilter() throws IOException {
        PkgFileRegister register = new PkgFileRegister();
        helper.touchFiles(extracted, "1234", "not me", "config.xml");
        File dir = new File(extracted,"1234");
        FileFilter filter = register.getConfig().getFilter();

        File[] files = dir.listFiles(filter);
        assertEquals(1, files.length);
    }

    public void testCreatePackage() throws IOException {
        helper.touchFiles(extracted, "1234", "ignore me");
        assertNotNull(controller.checkForPackages(extracted));
    }

    public void testGetAllFilters(){
        //if it fails, check if you added or removed a PkgFile definition
        assertEquals(8, controller.filters().size());
    }

    public void testGetHotupdateFilter(){
        //if it fails, check if you added or removed a PkgFile definition
        assertEquals(1, controller.filters(FileType.HOTUPDATE).size());
    }

    public void testGetConfigFilter(){
        //if it fails, check if you added or removed a PkgFile definition
        assertEquals(2, controller.filters(FileType.CONFIG).size());
    }

    public void testGetCardReaderFilter(){
        //if it fails, check if you added or removed a PkgFile definition
        assertEquals(2, controller.filters(FileType.CARDREADER).size());
    }

    public void testGetApkFilter(){
        //if it fails, check if you added or removed a PkgFile definition
        assertEquals(1, controller.filters(FileType.APK).size());
    }

    public void testGetSystemFilter(){
        //if it fails, check if you added or removed a PkgFile definition
        assertEquals(1, controller.filters(FileType.SYSTEM).size());
    }

    public void testGetSvgFilter(){
        //if it fails, check if you added or removed a PkgFile definition
        assertEquals(1, controller.filters(FileType.SVG).size());
    }


}