package com.zfoo.util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * DOM(Document Object Model)文档对象模型
 * <p>
 * Convenience methods for working with the DOM API,in particular for working with DOM Nodes and DOM Elements.
 * </p>
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-14 16:23
 */
public abstract class DomUtils {

    /**
     * 只返回第一层的孩子节点，不返回第一层孩子节点的孩子节点
     * <p>
     * Retrieves all child elements of the given DOM element
     * </p>
     *
     * @param element the DOM element to analyze
     * @return a List of child {@code org.w3c.dom.Element} instances
     */
    public static List<Element> getChildElements(Element element) {
        AssertionUtils.notNull(element, "Element must not be null");
        NodeList nodeList = element.getChildNodes();
        List<Element> childEles = new ArrayList<Element>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                childEles.add((Element) node);
            }
        }
        return childEles;
    }

    /**
     * Retrieves all child elements of the given DOM element that match any of the given element names.
     * Only looks at the direct child level of the given element; do not go into further depth
     * (in contrast to the DOM API's {@code getElementsByTagName} method).
     *
     * @param element           the DOM element to analyze
     * @param childElementNames the child element names to look for
     * @return a List of child {@code org.w3c.dom.Element} instances
     * @see org.w3c.dom.Element
     * @see org.w3c.dom.Element#getElementsByTagName
     */
    public static List<Element> getChildElementsByTagName(Element element, String... childElementNames) {
        AssertionUtils.notNull(element, "Element must not be null");
        AssertionUtils.notNull(childElementNames, "Element names collection must not be null");
        List<String> childEleNameList = Arrays.asList(childElementNames);
        NodeList childNodes = element.getChildNodes();
        List<Element> elements = new ArrayList<>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node instanceof Element && nodeNameMatch(node, childEleNameList)) {
                elements.add((Element) node);
            }
        }
        return elements;
    }

    public static List<Element> getChildElementsByTagName(Element ele, String childElementName) {
        return getChildElementsByTagName(ele, new String[]{childElementName});
    }

    /**
     * Utility method that returns the first child element identified by its name.
     *
     * @param ele          the DOM element to analyze
     * @param childEleName the child element name to look for
     * @return the {@code org.w3c.dom.Element} instance, or {@code null} if none found
     */
    public static Element getFirstChildElementByTagName(Element ele, String childEleName) {
        AssertionUtils.notNull(ele, "Element must not be null");
        AssertionUtils.notNull(childEleName, "Element name must not be null");
        NodeList nodeList = ele.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element && nodeNameMatch(node, childEleName)) {
                return (Element) node;
            }
        }
        return null;
    }

    /**
     * Namespace-aware equals comparison.
     */
    public static boolean nodeNameEquals(Node node, String desiredName) {
        AssertionUtils.notNull(node, "Node must not be null");
        AssertionUtils.notNull(desiredName, "Desired name must not be null");
        return nodeNameMatch(node, desiredName);
    }

    /**
     * Matches the given node's name and local name against the given desired names.
     */
    private static boolean nodeNameMatch(Node node, Collection<?> desiredNames) {
        return (desiredNames.contains(node.getNodeName()) || desiredNames.contains(node.getLocalName()));
    }

    /**
     * Matches the given node's name and local name against the given desired name.
     */
    private static boolean nodeNameMatch(Node node, String desiredName) {
        return (desiredName.equals(node.getNodeName()) || desiredName.equals(node.getLocalName()));
    }

}
