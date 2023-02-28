package com.testapp.xmlparser.xml;

import lombok.Data;

import java.io.Serializable;

@Data
public class EpaperRequest implements Serializable {
    private DeviceInfo deviceInfo;

    private GetPages getPages;
}
