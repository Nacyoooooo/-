package com.jieduixiangmu;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class helloworld {
    public static void main(String[] args) {
        System.out.println(Charset.defaultCharset());
        System.out.println(System.getProperty("file.encoding"));
        String graal="是否乱码";
        graal=new String(graal.getBytes(),Charset.forName("GBK"));
        System.out.println(graal);
    }
}
