## XML解析  
使用DocumentBuilderFactory对xml进行操作  

code  
```
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
}   
```
