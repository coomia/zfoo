package com.zfoo.net.protocol.model.xml;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonPropertyOrder({"id", "location"})
public class XmlProtocolDefinition {
    @JacksonXmlProperty(isAttribute = true, localName = "id")
    private short id;
    @JacksonXmlProperty(isAttribute = true, localName = "location")
    private String location;

    public short getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }
}
