package com.testapp.xmlparser.xml;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceInfo implements Serializable {
    private String name;

    private String id;

    private ScreenInfo screenInfo;

    private OsInfo osInfo;

    private AppInfo appInfo;
}
