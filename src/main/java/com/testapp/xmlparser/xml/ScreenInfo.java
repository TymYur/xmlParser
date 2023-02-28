package com.testapp.xmlparser.xml;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScreenInfo implements Serializable {

    private int width;

    private int height;

    private int dpi;
}
