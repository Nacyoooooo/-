package com.jieduixiangmu;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;

import java.io.File;

/**
 * 定义文件操作相关
 */
public class FileUtilss {
    /**
     * 追加内容到指定路径的文件中
     *
     * @param path,content
     * @return
     */
    public static boolean append(String path,//路径
                                 String content//内容
    ){
        try {
            return new FileWriter(path).append(content).exists();
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 读取指定路径下的文件的内容
     *
     * @param path
     * @return
     */
    public static String read(String path){
        try {
            return new FileReader(path).readString();
        } catch (Exception e) {
            return "";
        }
    }
}
