package com.zfoo.net.protocol.model.xml;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JsonPropertyOrder({"version", "author", "list"})
@JacksonXmlRootElement(localName = "protocols")
public class XmlProtocols {
    @JacksonXmlProperty(isAttribute = true, localName = "version")
    private String version;
    @JacksonXmlProperty(isAttribute = true, localName = "author")
    private String author;
    @JacksonXmlProperty(localName = "protocol")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<XmlProtocolDefinition> protocols;

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }

    public List<XmlProtocolDefinition> getProtocols() {
        return protocols;
    }
}
