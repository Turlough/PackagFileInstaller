package com.cowman.turlough.packagemanagement.packageprocessor;

import android.util.Log;

import com.cowman.turlough.packagemanagement.FileSystem;
import com.cowman.turlough.packagemanagement.PackageFile;
import com.cowman.turlough.packagemanagement.FileType;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

/**
 * Created by turlough on 13/02/16.
 */
public class PackageFileAuthority {

    private final static String TAG = PackageFileAuthority.class.getSimpleName();

    FileSystem system = new FileSystem();
    @Getter private  Set<PackageFile> definitions = new HashSet<>();

    @Getter private PackageFile apk;
    @Getter private PackageFile svg;
    @Getter private PackageFile config;
    @Getter private PackageFile systemUpdate;
    @Getter private PackageFile ftm;
    @Getter private PackageFile cdset;
    @Getter private PackageFile reader1;
    @Getter private PackageFile hotUpdate;


    public PackageFileAuthority() {

        addQuickDefinitions();

        addHotUpdateDefinitions();

        addCardReaderDefinitions();

    }

    private void addQuickDefinitions() {

        apk = new PackageFile(){{
            setFileType(FileType.APK);
            setFilter((file) -> file.getName().toLowerCase().endsWith(".apk"));
            setProcessor(new ApkProcessor());
        }};
        definitions.add(apk);

        config = new PackageFile(){{
            setFileType(FileType.CONFIG);
            setFilter(file -> file.getName().equals("config.xml"));
            setProcessor(file -> Log.d(TAG, file.getName() + " is not implemented"));
        }};
        definitions.add(config);

        ftm = new PackageFile(){{
            setFileType(FileType.CONFIG);
            setFilter(file -> file.getName().equals("ftm.xml"));
            setProcessor(file -> Log.d(TAG, file.getName() + " is not implemented"));
        }};
        definitions.add(ftm);

        systemUpdate = new PackageFile(){{
            setFileType(FileType.SYSTEM);
            setFilter((file) -> file.getName().toLowerCase().endsWith("ota-update.zip"));
            setProcessor(file -> Log.d(TAG, file.getName() + " is not implemented"));
        }};
        definitions.add(systemUpdate);

        svg = new PackageFile(){{
            setFileType(FileType.SVG);
            setFilter((file) -> file.getName().toLowerCase().endsWith(".svg"));
            setProcessor((file) -> system.moveFile(file, system.getRuntime()));
        }};
        definitions.add(svg);
    }

    private void addHotUpdateDefinitions() {
        hotUpdate = new PackageFile(){{
            setFileType(FileType.HOTUPDATE);
            setFilter((file) -> file.getName().toLowerCase().endsWith(".svg"));
            setProcessor(file -> Log.d(TAG, file.getName() + " is not implemented"));
        }};
        definitions.add(hotUpdate);
    }

    private void addCardReaderDefinitions() {
        cdset = new PackageFile(){{
            setFileType(FileType.CARDREADER);
            setFilter((file) -> file.getName().matches("^C-*\\.dat$"));
            setProcessor(file -> Log.d(TAG, file.getName() + " is not implemented"));
        }};
        definitions.add(cdset);

        reader1 = new PackageFile(){{
            setFileType(FileType.CARDREADER);
            setFilter((file) -> file.getName().matches("^D-*\\.csv$"));
            setProcessor(file -> Log.d(TAG, file.getName() + " is not implemented"));
        }};
        definitions.add(reader1);
    }

}
