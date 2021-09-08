package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import 编码和解码.Base64Test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Xml解析工具类
 */
public class XmlParseUtil {
    private static DocumentBuilderFactory dbFactory;

    public static Document instance(String xmlStr){
        Document document;
        try {
            if (dbFactory == null){
                dbFactory = DocumentBuilderFactory.newInstance();
            }
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlStr.getBytes());
            document = dBuilder.parse(inputStream);
        }catch (Exception e){
            throw new RuntimeException("xml文件解析失败");
        }
        return document;
    }

    public static Document instance(InputStream inputStream){
        Document document;
        try {
            if (dbFactory == null){
                dbFactory = DocumentBuilderFactory.newInstance();
            }
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(inputStream);
        }catch (Exception e){
            throw new RuntimeException("xml解析失败");
        }
        return document;
    }

    public static NodeList getElementByTag(Document document,String tag){
        return document.getElementsByTagName(tag);
    }

    /**
     * 根据标签获取值（只存在单个标签）
     * @param element
     * @param tag
     * @return
     */
    public static String getValueByTagOne(Element element , String tag){
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }

    /**
     * 根据标签获取值（存在多个标签）
     * @param element
     * @param tag
     * @return
     */
    public static List<String> getValueByTagMore(Element element , String tag){
        NodeList nodeList = element.getElementsByTagName(tag);
        List<String> arrayList = new ArrayList<>();
        for (int i = 0 ; i < nodeList.getLength() ; i++){
            arrayList.add(nodeList.item(i).getTextContent());
        }
        return arrayList;
    }

    public static <T> T xmlToBean(Class<T> clazz , Node node){
        return null;
    }


    public static void main(String[] args) {
        Class<Base64Test> clazz = Base64Test.class;
        System.out.println(clazz);
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
//                "<result>\n" +
//                "<sxq>手写签可以有多个\n" +
//                "\t\t<id> R10sadfasdf11111236</id>------手写签id\n" +
//                "\t\t<fydm>R00</fydm>------法院代码\n" +
//                "\t\t<userid> 360000000 </userid>-------承办人编码\n" +
//                "\t\t<title>姓名</title>---------------名称\n" +
//                "\t\t<title>姓名2</title>---------------名称\n" +
//                "\t\t<title>姓名3</title>---------------名称\n" +
//                "\t\t<password> admin</password>--------密码\n" +
//                "\t\t<imgpath> 01_1626312953956.png</imgpath>----图片\n" +
//                "\t\t<imgdata>图片文件</imgdata>---文件流\n" +
//                "</sxq>\n" +
//                "<sxq>\n" +
//                "\t\t<id>R10sadfasdf11111234</id>------手写签id\n" +
//                "\t\t<fydm>R01</fydm>------法院代码\n" +
//                "\t\t<userid> 360000000 </userid>-------承办人编码\n" +
//                "\t\t<title>张三手写签1</title>---------------名称\n" +
//                "\t\t<password> admin</password>--------密码\n" +
//                "\t\t<imgpath> 01_1626312953956.png</imgpath>----手写签图片\n" +
//                "\t\t<imgdata>图片文件流</imgdata>---文件流\n" +
//                "</sxq>\n" +
//                "</result>";
//        Document instance = XmlParseUtil.instance(xml);
//        NodeList nodeList = XmlParseUtil.getElementByTag(instance, "result");
//        Node item = nodeList.item(0);
//        List<String> title = XmlParseUtil.getValueByTagMore((Element) item, "title");
//        System.out.println(title);
    }
}
