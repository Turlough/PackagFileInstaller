package com.cowman.turlough.packagemanagement;

import android.content.Context;

import com.cowman.turlough.packagemanagement.pojo.FileType;
import com.cowman.turlough.packagemanagement.pojo.PkgFile;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;

/**
 * Created by turlough on 13/02/16.
 */
public class DeliverySystem  {
    Context context;
    @Getter
    private File defaultDir;

    FileSystem system;

    public DeliverySystem(Context context) {
        this.context = context;
        system= new FileSystem();
        system.init(context);
        setDir( system.getExtracted(), "1234");
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

        List<File> fileSet = new ArrayList<>();
        fileSet.add(file);
        pkgFile.setFiles(fileSet);

        pkgFile.setMd5("md5");
    }

    public void setDir(File root, String pkgId){
        defaultDir = new File(root, pkgId);
        defaultDir.mkdirs();
    }
}