/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.method.hotfixdemo_01;

/**
 * Created by sunpengfei on 15/11/4.
 */
public class LoadBugClass {

    public String getBugString() {
        BugClass bugClass = new BugClass();
        return bugClass.bug();
    }
}
