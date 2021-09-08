package 编码和解码;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\chenHight\\Desktop\\（刘牌）非密计算机和存储介质台账汇总表20210818.xls");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = IOUtils.toByteArray(fileInputStream);
        String s = Base64.getEncoder().encodeToString(bytes);
       // System.out.println(s);
//        String str = "LIU";
//        String s = Base64.getEncoder().encodeToString(str.getBytes(Charset.forName("utf-8")));
//        System.out.println(s);
//        byte[] bytes = Base64.getDecoder().decode(s.getBytes());
//        String s1 = new String(bytes, Charset.forName("utf-8"));
//        System.out.println(s1);
    }
}
