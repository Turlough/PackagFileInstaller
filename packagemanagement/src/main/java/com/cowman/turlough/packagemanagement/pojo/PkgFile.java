package com.cowman.turlough.packagemanagement.pojo;

import com.cowman.turlough.packagemanagement.packageprocessor.PkgFileProcessor;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by turlough on 13/02/16.
 */
public class PkgFile {

    @Getter @Setter private FileFilter filter;
    @Getter @Setter private List<File> files = new ArrayList<>();
    @Getter @Setter private Date lastModified;
    @Getter @Setter private String md5;
    @Getter @Setter private FileType fileType;
    @Getter @Setter private PkgFileProcessor processor;


    public boolean loadFiles(File dir){
        File[] list = dir.listFiles(filter);
        files = new ArrayList(Arrays.asList(list));
        return files.size()>0;
    }

    public boolean hasFiles(){
        return ! files.isEmpty();
    }

}
