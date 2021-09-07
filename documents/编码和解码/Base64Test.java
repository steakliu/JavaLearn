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
        String s = Base64.getEncoder().encodeToString(str.getBytes(Charset.forName("utf-16")));
        System.out.println(s);
        byte[] bytes = Base64.getDecoder().decode(s.getBytes());
        String s1 = new String(bytes, Charset.forName("utf-16"));
        System.out.println(s1);
    }
}
