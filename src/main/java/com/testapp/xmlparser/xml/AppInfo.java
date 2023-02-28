package com.testapp.xmlparser.xml;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppInfo implements Serializable {

    private String newspaperName;

    private double version;
}
