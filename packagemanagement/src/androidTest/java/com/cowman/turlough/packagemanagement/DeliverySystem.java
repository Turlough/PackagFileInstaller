package com.cowman.turlough.packagemanagement;

import android.content.Context;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

import lombok.Getter;

/**
 * Created by turlough on 13/02/16.
 */
public class DeliverySystem extends FileSystem {
    Context context;
    @Getter
    private File defaultDir;

    public DeliverySystem(Context context) {
        this.context = context;
        init(context);
        setDir( getExtracted(), "1234");
    }

    public void touchFiles(File dir, String packageId, String... filenames) throws IOException {
        File pkgDir = new File(dir, packageId);
        pkgDir.mkdirs();

        for (String filename : filenames) {
            new File(pkgDir, filename).createNewFile();
        }
    }

    public void createPackage(File dir, PkgFile... pkgFiles) {

    }

    public PkgFile createPkgFile(File dir, FileFilter filter, FileType fileType, String name, Date date, String md5) throws IOException {
        return new PkgFile() {{
            setFilter(filter);
            setFileType(fileType);
            setLastModified(date);
            setMd5(md5);
            new File(dir, name).createNewFile();
        }};
    }


    public void putInDefaultDir(PkgFile pkgFile, String name) throws IOException {
        File file = new File(defaultDir, name);
        file.createNewFile();
        pkgFile.setLastModified(new Date(file.lastModified()));

        HashSet<File> fileSet = new HashSet<>();
        fileSet.add(file);
        pkgFile.setFiles(fileSet);

        pkgFile.setMd5("md5");
    }

    public void setDir(File root, String pkgId){
        defaultDir = new File(root, pkgId);
        defaultDir.mkdirs();
    }
}