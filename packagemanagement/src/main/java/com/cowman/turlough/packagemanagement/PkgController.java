package com.cowman.turlough.packagemanagement;

import android.content.Context;

import com.cowman.turlough.packagemanagement.packageprocessor.PkgFileRegister;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import rx.Observable;

/**
 * Created by turlough on 13/02/16.
 */
public class PkgController {
    private static PkgController ourInstance = new PkgController();
    private PkgDir incomingPackage;
    private Context context;
    FileSystem system = new FileSystem();
    private PkgFileRegister register = new PkgFileRegister();

    public static PkgController getInstance() {
        return ourInstance;
    }

    private PkgController() {
    }

    public File checkForPackages(File dir){
        File[] packages = dir.listFiles();
        if (packages.length < 1)
            return null;
        else
            return packages[0];
    }

    public Set<File> fromDirectory(File dir, Set<FileFilter> filters){

        Set<File> fileList = new HashSet<>();

        File packageDir = checkForPackages(dir);
        if (packageDir != null){
            for (FileFilter filter : filters)
                Collections.addAll(fileList, packageDir.listFiles(filter));
        }

        return fileList;

    }

    public Set<FileFilter> filters(){

        Set<FileFilter> result = new HashSet<>();

        Observable
                .from(register.getDefinitions())
                .map(f -> f.getFilter())
                .subscribe(x -> result.add(x));

        return result;
    }


    public Set filters(FileType category) {
        Set<FileFilter> result = new HashSet<>();

        Observable
                .from(register.getDefinitions())
                .filter(f -> f.getFileType().equals(category))
                .map(f -> f.getFilter())
                .subscribe(x -> result.add(x));

        return result;
    }

    public Set<PkgFile> fromDirectory(File dir) {

        Set<PkgFile> fileSet = register.getDefinitions();
        Set<PkgFile> result = new HashSet<>();

        for(PkgFile f: fileSet) {
            //TESTING ONLY
            boolean isHotUpdate = f.getFileType().equals(FileType.HOTUPDATE);
            f.loadFiles(dir);
            if(f.hasFiles())
                result.add(f);
        }

        return result;

    }


}
