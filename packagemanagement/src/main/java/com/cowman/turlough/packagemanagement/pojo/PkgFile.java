package com.cowman.turlough.packagemanagement.pojo;

import com.cowman.turlough.packagemanagement.packageprocessor.PkgFileProcessor;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by turlough on 13/02/16.
 */
public class PkgFile {

    @Getter
    @Setter
    private FileFilter filter;
    @Getter
    @Setter
    private File file;
    @Getter
    @Setter
    private Date lastModified;
    @Getter
    @Setter
    private String md5;
    @Getter
    @Setter
    private FileType fileType;
    @Getter
    @Setter
    private PkgFileProcessor processor;


    public boolean loadFile(File file) {

        if (filter.accept(file)) {
            setFile(file);
            return true;
        }
        return false;

    }

    public boolean hasFile() {
        return file != null;
    }

}
