package com.cowman.turlough.packagemanagement;

import android.content.Context;

import com.cowman.turlough.packagemanagement.pojo.FileType;
import com.cowman.turlough.packagemanagement.pojo.PkgDir;
import com.cowman.turlough.packagemanagement.pojo.PkgFile;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public List<PkgFile> fromDirectory(File dir, FileType type){

        List<PkgFile> result = new ArrayList<>();
        List<PkgFile> subset = register.subset(type);
        //loadFiles for all matching, filter if no files added
        Observable
                .from(subset)
                .filter(x -> x.loadFiles(dir))//loadFiles returns false if no match
                .subscribe(x -> result.add(x));

        return result;

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

    /**
     * Returns a List of PkgFile found in directory.
     * The List contains only PkdFiles that are defined in the registry,
     * and that have matched a file in the directory.
     *
     * Use this to create a List of PackageFiles that are valid and non-empty
     * e.g. for further processing
     *
     * @param dir  The directory to find matches in
     * @return The list of non-empty valid PkgFiles
     */
    public List<PkgFile> fromDirectory(File dir) {

        List<PkgFile> result = new ArrayList<>();
        Set<PkgFile> subset = register.getDefinitions();
        //loadFiles for all matching, filter if no files added
        Observable
                .from(subset)
                .filter(x -> x.loadFiles(dir))//loadFiles returns false if no match
                .subscribe(x -> result.add(x));

        return result;

    }


}
