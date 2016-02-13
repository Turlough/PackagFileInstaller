package com.cowman.turlough.packagemanagement;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.cowman.turlough.packagemanagement.packageprocessor.PackageFileAuthority;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by turlough on 13/02/16.
 */
public class PackageControllerTest extends ApplicationTestCase<Application> {

    FileSystem system = new FileSystem();
    MockFileSystem helper;
    PackageController controller;


    public PackageControllerTest() {
        super(Application.class);
    }

    public void setUp(){
        system.init(getContext());
        helper = new MockFileSystem(getContext());
        controller = PackageController.getInstance();
    }

    public void tearDown(){
        system.delete(system.getRoot());
    }

    public void testFromDirectory() throws IOException {
        helper.createExtractedPackage("1234","not me", "config.xml");
        File dir = new File(system.getExtracted(),"1234");

        Set<PackageFile> result = controller.fromDirectory(dir);
        assertEquals(1, result.size());
    }

    public void testFileList() throws IOException {
        helper.createExtractedPackage("1234","not me", "config.xml");
        File dir = new File(system.getExtracted(),"1234");
        assertEquals(2, dir.list().length);
    }

    public void testConfigFilter() throws IOException {
        PackageFileAuthority authority = new PackageFileAuthority();
        FileFilter filter = authority.getConfig().getFilter();
        assertTrue(filter.accept(new File(system.getExtracted(),"config.xml")));
        assertFalse(filter.accept(new File(system.getExtracted(), "ftm.xml")));
    }

    public void testListFilesWithConfigFilter() throws IOException {
        PackageFileAuthority authority = new PackageFileAuthority();
        helper.createExtractedPackage("1234", "not me", "config.xml");
        File dir = new File(system.getExtracted(),"1234");
        FileFilter filter = authority.getConfig().getFilter();

        File[] files = dir.listFiles(filter);
        assertEquals(1, files.length);
    }

    public void testFillPackageFileFromDir() throws IOException {
        PackageFileAuthority authority = new PackageFileAuthority();
        helper.createExtractedPackage("1234", "not me", "config.xml");
        File dir = new File(system.getExtracted(),"1234");
        PackageFile config = authority.getConfig();
        Set<PackageFile> files = controller.fromDirectory(dir);

        assertEquals(1, files.size());
        assertTrue(files.contains(config));
    }

    public void testCreatePackage() throws IOException {
        helper.createExtractedPackage("1234", "ignore me");
        assertNotNull(controller.checkForPackages(system.getExtracted()));
    }

    public void testGetAllFilters(){
        //if it fails, check if you added or removed a PackageFile definition
        assertEquals(8, controller.filters().size());
    }

    public void testGetHotupdateFilter(){
        //if it fails, check if you added or removed a PackageFile definition
        assertEquals(1, controller.filters(FileType.HOTUPDATE).size());
    }

    public void testGetConfigFilter(){
        //if it fails, check if you added or removed a PackageFile definition
        assertEquals(2, controller.filters(FileType.CONFIG).size());
    }

    public void testGetCardReaderFilter(){
        //if it fails, check if you added or removed a PackageFile definition
        assertEquals(2, controller.filters(FileType.CARDREADER).size());
    }

    public void testGetApkFilter(){
        //if it fails, check if you added or removed a PackageFile definition
        assertEquals(1, controller.filters(FileType.APK).size());
    }

    public void testGetSystemFilter(){
        //if it fails, check if you added or removed a PackageFile definition
        assertEquals(1, controller.filters(FileType.SYSTEM).size());
    }

    public void testGetSvgFilter(){
        //if it fails, check if you added or removed a PackageFile definition
        assertEquals(1, controller.filters(FileType.SVG).size());
    }

}
