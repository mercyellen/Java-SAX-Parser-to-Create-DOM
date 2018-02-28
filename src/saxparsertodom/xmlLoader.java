/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saxparsertodom;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author qiwenguo
 */
public class xmlLoader {
    public static Node load(File file) throws Exception {
        Node initNode = new Node();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                int depth = 0;
                ArrayList<Node> nodes = new ArrayList<Node>();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    Node node = null;
                    if (depth == 0) {
                        node = initNode;
                    } else {
                        node = new Node();
                        nodes.get(depth - 1).contains.add(node);
                    }
                    nodes.add(depth, node);
                    node.tag = qName;
                    depth += 1;
                    for (int i = 0; i < attributes.getLength(); i++) {
                        Attr attr = new Attr();
                        attr.id = attributes.getLocalName(i);
                        attr.value = attributes.getValue(i);
                        node.attrs.add(attr);
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    depth -= 1;
                    nodes.remove(depth);
                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    nodes.get(depth - 1).content = new String(ch, start, length);
                }
            };

            saxParser.parse(file.getAbsoluteFile(), handler);

        } catch (Exception ex) {
            throw ex;
        }

        return initNode;
    }
}

