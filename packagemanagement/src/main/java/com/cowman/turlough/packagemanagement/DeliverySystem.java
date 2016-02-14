package com.cowman.turlough.packagemanagement;

import android.content.Context;

import com.cowman.turlough.packagemanagement.exception.PackageException;
import com.cowman.turlough.packagemanagement.pojo.FileType;
import com.cowman.turlough.packagemanagement.pojo.PkgFile;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import rx.Observable;

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

    public int createPackage(String pkgId, PkgFile... pkgFiles) {
        File dir = new File(system.getIncoming(), pkgId);
        dir.mkdirs();
        List<PkgFile> result = new ArrayList<>();
        Observable
                .from(pkgFiles)
                .filter(pf -> pf.hasFile())
                .subscribe(pf -> result.add(pf));
        return result.size();
    }

    public void putInDefaultDir(PkgFile pkgFile, String name) throws IOException, PackageException {

        File file = new File(defaultDir, name);
        if(! pkgFile.getFilter().accept(file))
            return;

        file.createNewFile();
        write(file, "Franco ha muerto, VIVA!");
        pkgFile.setFile(file);
        pkgFile.setLastModified(new Date(file.lastModified()));
        pkgFile.setMd5("md5");
    }

    public void setDir(File root, String pkgId){
        defaultDir = new File(root, pkgId);
        defaultDir.mkdirs();
    }

    public void write(File file, String content) throws IOException {
        FileOutputStream stream = new FileOutputStream(file);
        try {
            stream.write(content.getBytes());
        } finally {
            stream.close();
        }
    }

    public Observable<Boolean> getExtractionObservable(){

        return null;
    }
}