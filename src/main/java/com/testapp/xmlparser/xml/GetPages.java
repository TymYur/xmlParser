package com.testapp.xmlparser.xml;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetPages implements Serializable {

    private int editionDefId;

    private String publicationDate;
}
