package com.cowman.turlough.packagemanagement;

/**
 * Created by turlough on 14/02/16.
 */
public class PkgPublisher {
    private static PkgPublisher ourInstance = new PkgPublisher();

    public static PkgPublisher getInstance() {
        return ourInstance;
    }

    private PkgPublisher() {
    }
}
