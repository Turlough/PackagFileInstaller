package com.cowman.turlough.packagemanagement;

import android.content.Context;

import com.cowman.turlough.packagemanagement.packageprocessor.PackageFileAuthority;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import rx.Observable;

/**
 * Created by turlough on 13/02/16.
 */
public class PackageController {
    private static PackageController ourInstance = new PackageController();
    private IncomingPackage incomingPackage;
    private Context context;
    FileSystem system = new FileSystem();
    private PackageFileAuthority authority = new PackageFileAuthority();

    public static PackageController getInstance() {
        return ourInstance;
    }

    private PackageController() {
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
                .from(authority.getDefinitions())
                .map(f -> f.getFilter())
                .subscribe(x -> result.add(x));

        return result;
    }


    public Set filters(FileType category) {
        Set<FileFilter> result = new HashSet<>();

        Observable
                .from(authority.getDefinitions())
                .filter(f -> f.getFileType().equals(category))
                .map(f -> f.getFilter())
                .subscribe(x -> result.add(x));

        return result;
    }

    public Set<PackageFile> fromDirectory(File dir) {

        Set<PackageFile> fileSet = authority.getDefinitions();
        Set<PackageFile> result = new HashSet<>();

        for(PackageFile f: fileSet) {
            f.loadFiles(dir);
            if(f.hasFiles())
                result.add(f);
        }

        return result;

    }


}
