package com.cowman.turlough.packagemanagement;

import com.cowman.turlough.packagemanagement.pojo.SessionInfo;
import com.cowman.turlough.packagemanagement.pojo.SessionState;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SessionInfoTest {

    SessionInfo info;
    @Before
    public void setUp(){
        info = new SessionInfo();
    }

    @Test
    public void testDefaultState(){
        assertEquals(SessionState.STARTUP, info.getState());
    }

    @Test
    public void gettersAndSetters() throws Exception {
        info.setState(SessionState.SELLING);
        assertEquals(SessionState.SELLING, info.getState());
    }
}