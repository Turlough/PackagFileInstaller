package com.cowman.turlough.packagemanagement;

import android.util.Log;

import com.cowman.turlough.packagemanagement.packageprocessor.ApkProcessor;
import com.cowman.turlough.packagemanagement.pojo.FileType;
import com.cowman.turlough.packagemanagement.pojo.PkgFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import rx.Observable;

/**
 * Created by turlough on 13/02/16.
 */
public class PkgFileRegister {

    private final static String TAG = PkgFileRegister.class.getSimpleName();

    FileSystem system = new FileSystem();
    @Getter private  Set<PkgFile> definitions = new HashSet<>();
    private  Set<PkgFile> runtimeFiles = new HashSet<>();
    private  Set<PkgFile> runtimeRecord = new HashSet<>();

    @Getter private PkgFile apk;
    @Getter private PkgFile svg;
    @Getter private PkgFile config;
    @Getter private PkgFile systemUpdate;
    @Getter private PkgFile ftm;
    @Getter private PkgFile cdset;
    @Getter private PkgFile reader1;
    @Getter private PkgFile hotUpdate;


    public PkgFileRegister() {

        addQuickDefinitions();

        addHotUpdateDefinitions();

        addCardReaderDefinitions();

    }

    private void addQuickDefinitions() {

        apk = new PkgFile(){{
            setFileType(FileType.APK);
            setFilter((file) -> file.getName().toLowerCase().endsWith(".apk"));
            setProcessor(new ApkProcessor());
        }};
        definitions.add(apk);

        config = new PkgFile(){{
            setFileType(FileType.CONFIG);
            setFilter(file -> file.getName().equals("config.xml"));
            setProcessor(file -> Log.d(TAG, file.getName() + " is not implemented"));
        }};
        definitions.add(config);

        ftm = new PkgFile(){{
            setFileType(FileType.CONFIG);
            setFilter(file -> file.getName().equals("ftm.xml"));
            setProcessor(file -> Log.d(TAG, file.getName() + " is not implemented"));
        }};
        definitions.add(ftm);

        systemUpdate = new PkgFile(){{
            setFileType(FileType.SYSTEM);
            setFilter((file) -> file.getName().toLowerCase().endsWith("ota-update.zip"));
            setProcessor(file -> Log.d(TAG, file.getName() + " is not implemented"));
        }};
        definitions.add(systemUpdate);

        svg = new PkgFile(){{
            setFileType(FileType.SVG);
            setFilter((file) -> file.getName().toLowerCase().endsWith(".svg"));
            setProcessor((file) -> system.moveFile(file, system.getRuntime()));
        }};
        definitions.add(svg);
    }

    private void addHotUpdateDefinitions() {
        hotUpdate = new PkgFile(){{
            setFileType(FileType.HOTUPDATE);
            //setFilter(file -> file.getName().startsWith("hu"));
            setFilter(file -> file.getName().matches("hu(.*)\\.dat"));
            setProcessor(file -> Log.d(TAG, file.getName() + " is not implemented"));
        }};
        definitions.add(hotUpdate);
    }

    private void addCardReaderDefinitions() {
        cdset = new PkgFile(){{
            setFileType(FileType.CARDREADER);
            setFilter(file -> file.getName().matches("^C-*\\.dat$"));
            setProcessor(file -> Log.d(TAG, file.getName() + " is not implemented"));
        }};
        definitions.add(cdset);

        reader1 = new PkgFile(){{
            setFileType(FileType.CARDREADER);
            setFilter((file) -> file.getName().matches("^D-*\\.csv$"));
            setProcessor(file -> Log.d(TAG, file.getName() + " is not implemented"));
        }};
        definitions.add(reader1);
    }

    public List<PkgFile> subset(FileType type){
        Set<PkgFile> all = getDefinitions();
        List<PkgFile> result = new ArrayList<>();

        Observable
                .from(all)
                .filter(f -> f.getFileType().equals(type))
                .subscribe(x -> result.add(x));
        return result;
    }

}
