package com.zfoo.utils;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.zfoo.util.DomUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DomUtilsTest {

    private static final String XML_WITH_HEAD = "" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
            "\n" +
            "<protocols version=\"1.0\" author=\"jaysunxiao\">\n" +
            "\n" +
            "    <protocol id=\"0\" location=\"com.zfoo.test.CM_Int\"/>\n" +
            "    <protocol id=\"1\" location=\"com.zfoo.test.SM_Int\"/>\n" +
            "    <protocol id=\"2\" location=\"com.zfoo.test.CM_Float\"/>\n" +
            "\n" +
            "</protocols>";

    private static final String XML_OF_STANDARD_TEXT =
            "<protocols version=\"1.0\" author=\"jaysunxiao\">\n" +
                    "\n" +
                    "    <protocol id=\"0\" location=\"com.zfoo.test.CM_Int\"/>\n" +
                    "    <protocol id=\"1\" location=\"com.zfoo.test.SM_Int\"/>\n" +
                    "    <protocol id=\"2\" location=\"com.zfoo.test.CM_Float\"/>\n" +
                    "\n" +
                    "</protocols>";

    @Test
    public void testXmlWithHead() {
        Protos protos = DomUtils.string2Object(XML_WITH_HEAD, Protos.class);
        Assert.assertEquals("jaysunxiao", protos.getAuthor());
        Assert.assertEquals("1.0", protos.getVersion());
    }

    @Test
    public void testXmlOfStandardText() {
        Protos protos = DomUtils.string2Object(XML_OF_STANDARD_TEXT, Protos.class);
        Assert.assertEquals("jaysunxiao", protos.getAuthor());
        Assert.assertEquals("1.0", protos.getVersion());
    }

}

@JsonPropertyOrder({"version", "author", "list"})
@JacksonXmlRootElement(localName = "protocols")
class Protos {
    @JacksonXmlProperty(isAttribute = true, localName = "version")
    private String version;
    @JacksonXmlProperty(isAttribute = true, localName = "author")
    private String author;
    @JacksonXmlProperty(localName = "protocol")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Proto> protos;

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }

    public List<Proto> getProtos() {
        return protos;
    }
}

@JsonPropertyOrder({"id", "location"})
class Proto {
    @JacksonXmlProperty(isAttribute = true, localName = "id")
    private int id;
    @JacksonXmlProperty(isAttribute = true, localName = "location")
    private String location;

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }
}


