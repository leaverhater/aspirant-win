package ru.bmstu.aspirant;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 30.08.13
 * Time: 19:51
 */
public class XmlProc {
    public static ArrayList<String> getStringsFromNode (Node node, String tagName) {
        ArrayList<String> strings = new ArrayList<String>();
        Element element = (Element) node;
        NodeList tempNodeList = element.getElementsByTagName(tagName);
        for (int i = 0; i < tempNodeList.getLength(); i++) {
            strings.add(((Element)tempNodeList.item(i)).getTextContent());
        }
        return strings;
    }
}
