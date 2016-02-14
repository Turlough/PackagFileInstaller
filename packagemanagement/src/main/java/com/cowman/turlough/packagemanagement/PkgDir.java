package com.cowman.turlough.packagemanagement;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by turlough on 13/02/16.
 */
public class PkgDir {
    @Getter @Setter String packageId;
    @Getter @Setter PkgFile[] files;


}
