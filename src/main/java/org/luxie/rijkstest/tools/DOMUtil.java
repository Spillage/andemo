package org.luxie.rijkstest.tools;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class DOMUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DOMUtil.class.getName());

    @SneakyThrows
    public String parseXMLStringAndReturnElementValue(String xmlString, String elementName) {
        String result = "";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            NodeList nodeList = doc.getElementsByTagName(elementName);
            if (nodeList.getLength() > 0) {
                result = nodeList.item(0).getTextContent();
            }
        } catch (Exception e) {
            LOGGER.error("Parse XML String Fail: " + e);
        } finally {
            return result;
        }
    }

    @SneakyThrows
    public int parseXMLStringAndReturnElementCount(String xmlString, String elementName) {
        int result = 0;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            NodeList nodeList = doc.getElementsByTagName(elementName);
            result = nodeList.getLength();
        } catch (Exception e) {
            LOGGER.error("Parse XML String Fail: " + e);
        } finally {
            return result;
        }
    }
}
