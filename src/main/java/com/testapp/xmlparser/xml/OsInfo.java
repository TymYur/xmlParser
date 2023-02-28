package com.testapp.xmlparser.xml;

import lombok.Data;

import java.io.Serializable;

@Data
public class OsInfo implements Serializable {
    private String name;

    private double version;
}
