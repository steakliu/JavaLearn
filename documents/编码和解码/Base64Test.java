package 编码和解码;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/9/7 0007 21:25
 */
public class Base64Test {

    public static void main(String[] args) {
        String str = "LIU";
        String s = Base64.getEncoder().encodeToString(str.getBytes());
        System.out.println(s);
        //String imageBase64 = "iVBORw0KGgoAAAANSUhEUgAABTUAAAZHCAYAAAC1i5bCAAAAAXNSR0IArs4c6QAAAARzQklUCAgICHwIZIgAACAASURBVHic7N1pk+NG0rXp4x4AM0vVUvfMO8/Y/Pb5f/M+S0utpXIhgQj3+RABkJmVtahXoXVfZqolkwBBkGlWduSLnf7fTAEAAAAAAADAQfi/+gIAAAAAAAAA4Ncg1AQAAAAAAABwKISaAAAAAAAAAA6FUBMAAAAAAADAoRBqAgAAAAAAADgUQk0AAAAAAAAAh0KoCQAAAAAAAOBQCDUBAAAAAAAAHAqhJgAAAAAAAIBDIdQEAAAAAAAAcCiEmgAAAAAAAAAOhVATAAAAAAAAwKEQagIAAAAAAAA4FEJNAAAAAAAAAIdCqAkAAAAAAADgUAg1AQAAAAAAABwKoSYAAAAAAACAQyHUBAAAAAAAAHAohJoAAAAAAAAADoVQEwAAAAAAAMChEGoCAAAAAAAAOBRCTQAAAAAAAACHQqgJAAAAAAAA4FAINQEAAAAAAAAcCqEmAAAAAAAAgEMh1AQAAAAAAABwKISaAAAAAAAAAA6FUBMAAAAAAADAoRBqAgAAAAAAADgUQk0AAAAAAAAAh0KoCQAAAAAAAOBQCDUBAAAAAAAAHAqhJgAAAAAAAIBDIdQEAAAAAAAAcCiEmgAAAAAAAAAOhVATAAAAAAAAwKEQagIAAAAAAAA4FEJNAAAAAAAAAIdCqAkAAAAAAADgUAg1AQAAAAAAABwKoSYAAAAAAACAQyHUBAAAAAAAAHAohJoAAAAAAAAADoVQEwAAAAAAAMChEGoCAAAAAAAAOBRCTQAAAAAAAACHQqgJAAAAAAAA4FAINQEAAAAAAA";
        byte[] bytes = Base64.getDecoder().decode(s.getBytes());
        String s1 = new String(bytes, Charset.forName("utf-8"));
        System.out.println(s1);
    }
}
