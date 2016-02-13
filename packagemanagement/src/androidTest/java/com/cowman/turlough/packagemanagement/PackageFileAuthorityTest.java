package com.cowman.turlough.packagemanagement;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.cowman.turlough.packagemanagement.packageprocessor.PackageFileAuthority;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import rx.Observable;

/**
 * Created by turlough on 13/02/16.
 */
public class PackageFileAuthorityTest extends ApplicationTestCase<Application> {

    FileSystem system = new FileSystem();
    MockFileSystem helper;
    PackageController controller;
    PackageFileAuthority authority;


    public PackageFileAuthorityTest() {
        super(Application.class);
    }

    public void setUp() {
        system.init(getContext());
        helper = new MockFileSystem(getContext());
        controller = PackageController.getInstance();
        authority = new PackageFileAuthority();
    }

    public void tearDown() {
        system.delete(system.getRoot());
    }

    public void testGetAll() throws IOException {
        assertTrue(authority.getDefinitions().size() > 0);
    }

    public void testFilter() {
        Set<PackageFile> result = new HashSet<>();

        Observable
                .from(authority.getDefinitions())
                .filter(x -> x.getFileType().equals(FileType.CARDREADER))
                .subscribe(x -> result.add(x));

        assertEquals(2, result.size());
    }
}
