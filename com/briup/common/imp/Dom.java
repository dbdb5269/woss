package com.briup.common.imp;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by dubo on 16/11/14.
 */

public class Dom {
    public String parse(String str,String str2) throws Exception{
        SAXReader reader = new SAXReader();
        Document document = reader.read("./src/config.xml");
        Element root = document.getRootElement();
        List<Element> elements = root.elements();

        for (Element element : elements) {

            if (element.getName().equals(str)){
                List<Element> childs = element.elements();
                for (Element element2 : childs) {
                    if (element2.getName().equals(str2)){
                        return element2.getTextTrim();
                    }
                }
            }


        }
        return null;
    }
    public String parse_attribute(String str) throws Exception{
        SAXReader reader = new SAXReader();
        Attribute attribute=null;
        Document document = reader.read("./src/config.xml");
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            if (element.getName().equals(str)){
                attribute=element.attribute("class");

            }
        }

        return attribute.getText();
    }

    public void parse_all(String str){
        SAXReader reader = new SAXReader();
        //2.解析
        Document document = null;
        try {
            document = reader.read("./src/config.xml");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取根元素
        Element root = document.getRootElement();

        List<Element> elements = root.elements();
        for (Element element : elements) {

            if (element.getName().equals(str)){
                List<Element> childs = element.elements();
                for (Element element2 : childs) {
                    List<Attribute> attributes = element2.attributes();
                    System.out.print(element2.getName() + "=");
                    System.out.println(element2.getTextTrim());

                }
            }

        }
    }


}
