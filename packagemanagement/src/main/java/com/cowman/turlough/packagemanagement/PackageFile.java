package com.cowman.turlough.packagemanagement;

import com.cowman.turlough.packagemanagement.packageprocessor.PackageFileProcessor;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by turlough on 13/02/16.
 */
public class PackageFile {

    @Getter @Setter protected FileFilter filter;
    @Getter @Setter private Set<File> files = new HashSet<>();
    @Getter @Setter private Date lastModified;
    @Getter @Setter private String md5;
    @Getter @Setter private FileType fileType;
    @Getter @Setter private PackageFileProcessor processor;

    public void loadFiles(File dir){
        File[] list = dir.listFiles(filter);
        files = new HashSet(Arrays.asList(list));
    }

    public boolean hasFiles(){
        return ! files.isEmpty();
    }

}
