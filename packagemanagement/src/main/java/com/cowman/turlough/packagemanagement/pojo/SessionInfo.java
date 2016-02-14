package com.cowman.turlough.packagemanagement.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by turlough on 13/02/16.
 */
public class SessionInfo {
    @Getter @Setter private SessionState state = SessionState.STARTUP;
}
